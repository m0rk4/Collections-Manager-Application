<template>
  <v-container fluid>
    <v-data-iterator
        :items="itemsToDisplay"
        :items-per-page.sync="itemsPerPage"
        :page="page"
        :search="search"
        :sort-by="sortBy.toLowerCase()"
        :sort-desc="sortDesc"
        hide-default-footer
    >
      <template v-slot:header>
        <v-toolbar
            dark
            color="blue darken-3"
            class="mb-1"
        >
          <v-text-field
              class="mr-1"
              v-model="search"
              clearable
              flat
              solo-inverted
              hide-details
              prepend-inner-icon="mdi-magnify"
              label="Search"
          ></v-text-field>
          <template v-if="$vuetify.breakpoint.smAndUp">
            <v-spacer></v-spacer>
            <v-select
                class="mr-1"
                v-model="sortBy"
                flat
                solo-inverted
                hide-details
                :items="keys"
                prepend-inner-icon="mdi-magnify"
                label="Sort by"
            ></v-select>
            <v-spacer></v-spacer>
            <v-btn-toggle
                v-model="sortDesc"
                mandatory
            >
              <v-btn
                  large
                  depressed
                  color="blue"
                  :value="false"
              >
                <v-icon>mdi-arrow-up</v-icon>
              </v-btn>
              <v-btn
                  large
                  depressed
                  color="blue"
                  :value="true"
              >
                <v-icon>mdi-arrow-down</v-icon>
              </v-btn>
            </v-btn-toggle>
          </template>
        </v-toolbar>
      </template>

      <template v-slot:default="props">
        <v-row>
          <v-col
              v-for="item in props.items"
              :key="item.title"
              cols="12"
              md="6"
              lg="4"
          >
            <v-card>
              <v-card-title class="font-weight-bold">
                <v-btn text rounded>{{ item.title }}</v-btn>
                <v-spacer></v-spacer>
                <v-btn icon v-if="youLiked(item.likers)" @click="processLike(item.id)">
                  <v-icon>mdi-thumb-up</v-icon>
                </v-btn>
                <v-btn icon v-else @click="processLike(item.id)">
                  <v-icon>mdi-thumb-up-outline</v-icon>
                </v-btn>
                {{ item.likers.length }}
              </v-card-title>
              <v-divider></v-divider>
              <v-card-text>
                <v-container fluid>
                  <v-list dense>
                    <v-list-item
                        v-for="(key, index) in filteredKeys"
                        :key="String(index)"
                    >
                      <v-list-item-content :class="{ 'blue--text': sortBy === key }">
                        {{ key }}:
                      </v-list-item-content>
                      <v-list-item-content
                          v-if="!isKeySupportsMarkDown(key)"
                          class="align-end"
                          :class="{ 'blue--text': sortBy === key }"
                      >
                        {{ item[key.toLowerCase()] }}
                      </v-list-item-content>
                      <v-list-item-content
                          v-else
                          class="align-end"
                          :class="{ 'blue--text': sortBy === key }"
                      >
                        <vue-markdown :source="item[key.toLowerCase()]"></vue-markdown>
                      </v-list-item-content>
                    </v-list-item>
                  </v-list>
                  <v-container fluid>
                    <v-btn
                        class="my-2 mx-1"
                        rounded
                        small
                        depressed
                        v-for="tag in item.tags"
                        :key="String(tag.value)"
                    >
                      {{ tag.text }}
                    </v-btn>
                  </v-container>
                  <!--                  <v-combobox-->
                  <!--                      v-model="item['tags']"-->
                  <!--                      label="Tags"-->
                  <!--                      small-chips-->
                  <!--                      chips-->
                  <!--                      multiple-->
                  <!--                      persistent-hint-->
                  <!--                      single-line-->
                  <!--                      readonly-->
                  <!--                      dense-->
                  <!--                  >-->
                  <!--                  </v-combobox>-->
                </v-container>
              </v-card-text>
              <v-divider></v-divider>
              <v-card-actions>
                <v-btn icon @click="updateItem(item)">
                  <v-icon>mdi-pencil</v-icon>
                </v-btn>
                <v-btn icon @click="deleteItem(item)">
                  <v-icon>mdi-delete</v-icon>
                </v-btn>
              </v-card-actions>
              <v-divider></v-divider>

              <comment-list
                  :itemId="item.id"
                  :comments="item.comments"
              ></comment-list>
            </v-card>
          </v-col>
        </v-row>
      </template>

      <template v-slot:footer>
        <v-row
            class="mt-2"
            align="center"
            justify="center"
        >
          <span class="grey--text">Items per page</span>
          <v-menu offset-y>
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                  dark
                  text
                  color="primary"
                  class="ml-2"
                  v-bind="attrs"
                  v-on="on"
              >
                {{ itemsPerPage }}
                <v-icon>mdi-chevron-down</v-icon>
              </v-btn>
            </template>
            <v-list>
              <v-list-item
                  v-for="(number, index) in itemsPerPageArray"
                  :key="index"
                  @click="updateItemsPerPage(number)"
              >
                <v-list-item-title>{{ number }}</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>

          <v-spacer></v-spacer>

          <span
              class="mr-4
            grey--text"
          >
            Page {{ page }} of {{ numberOfPages }}
          </span>
          <v-btn
              fab
              dark
              color="blue darken-3"
              class="mr-1"
              @click="formerPage"
          >
            <v-icon>mdi-chevron-left</v-icon>
          </v-btn>
          <v-btn
              fab
              dark
              color="blue darken-3"
              class="ml-1"
              @click="nextPage"
          >
            <v-icon>mdi-chevron-right</v-icon>
          </v-btn>
        </v-row>
      </template>
    </v-data-iterator>
  </v-container>
</template>

<script>
import {mapState} from "vuex"
import VueMarkdown from "vue-markdown/src/VueMarkdown"
import CommentList from "components/comment/CommentList.vue"
import itemApi from "api/itemApi";


export default {
  components: {CommentList, VueMarkdown},
  props: ['collection', 'updateItem', 'deleteItem'],
  data() {
    return {
      currCollection: this.collection,
      itemsPerPageArray: [4, 8, 12],
      search: '',
      filter: {},
      sortDesc: false,
      page: 1,
      itemsPerPage: 4,
      sortBy: '',
      keys: [],
    }
  },
  watch: {
    collection: function (newVal) {
      this.currCollection = newVal
      this.keys = newVal.fields.map(f => f.text)
      this.$store.commit('item/setCollectionItemsMutation', newVal.items)
    }
  },
  computed: {
    ...mapState({
      collectionItems: state => state.item.collectionItems,
      profile: state => state.auth.profile,
    }),
    numberOfPages() {
      return Math.ceil(this.collectionItems.length / this.itemsPerPage)
    },
    filteredKeys() {
      return this.keys.filter(key => key !== 'Name')
    },
    itemsToDisplay() {
      const displayItems = []
      this.collectionItems.forEach(i => {
        console.log(i)
        var obj = {}
        obj['id'] = i.id
        obj['title'] = i.title
        obj['comments'] = i.comments
        obj['likers'] = i.likers
        obj['tags'] = i.tags.map(t => {
          return {text: t.name, value: t.id}
        })
        this.keys.forEach(k => {
          if (k !== 'Title') {
            const id = i.values.findIndex(val => val.field.text === k)
            obj[k.toLowerCase()] = i.values[id] ? i.values[id].value : ''
          }
        })
        displayItems.push(obj)
      })
      return displayItems
    }
  },
  methods: {
    isKeySupportsMarkDown(key) {
      const fieldId = this.currCollection.fields.findIndex(f => f.text === key)
      if (fieldId > -1) return this.currCollection.fields[fieldId].isMarkDownSupported
      return false
    },
    nextPage() {
      if (this.page + 1 <= this.numberOfPages) this.page += 1
    },
    formerPage() {
      if (this.page - 1 >= 1) this.page -= 1
    },
    updateItemsPerPage(number) {
      this.itemsPerPage = number
    },
    processLike(id) {
      itemApi.likeItem(id).then(res => {
        console.log(res)
      })
    },
    youLiked(likers) {
      return likers.filter(l => l.id === this.profile.id).length
    }
  },
}
</script>

<style scoped>

</style>