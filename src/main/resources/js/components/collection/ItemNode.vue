<template>
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
      </v-container>
    </v-card-text>
    <v-divider></v-divider>
    <v-card-actions v-if="profile && (author.id === profile.id || isAdmin)">
      <v-btn icon @click="updateItem(item)">
        <v-icon>mdi-pencil</v-icon>
      </v-btn>
      <v-btn icon @click="deleteItem(item)">
        <v-icon>mdi-delete</v-icon>
      </v-btn>
    </v-card-actions>
    <comment-list
        :itemId="item.id"
        :comments="item.comments"
    ></comment-list>
  </v-card>
</template>

<script>
import CommentList from "components/comment/CommentList.vue"
import {mapGetters, mapState} from "vuex"
import VueMarkdown from "vue-markdown/src/VueMarkdown"
import itemApi from "api/itemApi"

export default {
  props: ['item', 'filteredKeys', 'sortBy', 'updateItem', 'deleteItem', 'currCollection'],
  components: {CommentList, VueMarkdown},
  computed: {
    ...mapState({profile: state => state.auth.profile,}),
    ...mapGetters('auth', ['isAdmin']),
    author() {
      return this.currCollection.user
    },
  },
  methods: {
    processLike(id) {
      if (this.profile) {
        itemApi.likeItem(id).then(res => {
          if (res.ok) {
            console.log('Like processed')
          }
        })
      }
    },
    youLiked(likers) {
      return this.profile && likers.filter(l => l.id === this.profile.id).length
    },
    isKeySupportsMarkDown(key) {
      const fieldId = this.currCollection.fields.findIndex(f => f.text === key)
      if (fieldId > -1) {
        return this.currCollection.fields[fieldId].isMarkDownSupported
      }
      return false
    }
  }
}
</script>

<style scoped>

</style>