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
            <item-node
                :targetItem="item"
                :sortBy="sortBy"
                :filteredKeys="filteredKeys"
                :deleteItem="deleteItem"
                :updateItem="updateItem"
                :currCollection="currCollection"
                :isDialog="false"
            ></item-node>
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
import {mapState, mapGetters} from "vuex"
import VueMarkdown from "vue-markdown/src/VueMarkdown"
import CommentList from "components/comment/CommentList.vue"
import ItemNode from "components/collection/ItemNode.vue"


export default {
  components: {CommentList, VueMarkdown, ItemNode},
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
    ...mapGetters('auth', ['isAdmin']),
    numberOfPages() {
      return Math.ceil(this.collectionItems.length / this.itemsPerPage)
    },
    filteredKeys() {
      return this.keys.filter(key => key !== 'Name')
    },
    itemsToDisplay() {
      const displayItems = []
      this.collectionItems.forEach(i => {
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
    nextPage() {
      if (this.page + 1 <= this.numberOfPages) this.page += 1
    },
    formerPage() {
      if (this.page - 1 >= 1) this.page -= 1
    },
    updateItemsPerPage(number) {
      this.itemsPerPage = number
    },
  },
}
</script>

<style scoped>

</style>