<template>
  <v-container fluid>
    <v-card rounded outlined shaped class="mb-8">
      <v-card-text>
        <v-container fluid>
          <v-parallax
              height="300"
              dark
              :src="currCollection.pic"
          >
            <v-row
                align="center"
                justify="center"
            >
              <v-col
                  class="text-center"
                  cols="12"
              >
                <h1 class="display-4 font-weight-bold black--text mb-4">
                  {{ currCollection.title }}
                </h1>
              </v-col>
            </v-row>
          </v-parallax>
        </v-container>
      </v-card-text>
    </v-card>

    <v-row>
      <v-col>
        <v-card>
          <v-toolbar flat>
            <v-list-item two-line>
              <v-list-item-content>
                <v-list-item-title class="headline">
                  {{ currCollection.theme.text }}
                </v-list-item-title>
                <v-list-item-subtitle>
                  User: <i>{{ currCollection.user.name }}</i>
                </v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>
          </v-toolbar>
          <v-divider></v-divider>
          <v-card-text>
            <v-container fluid>
              <v-list dense>
                <v-list-item>
                  <vue-markdown :source="currCollection.description"></vue-markdown>
                </v-list-item>
              </v-list>
            </v-container>
          </v-card-text>
        </v-card>
      </v-col>
      <v-col>
        <collection-form
            :currentCollection="currCollection"
            :valuesList="values"
            :allTagsList="allTags"
        ></collection-form>
      </v-col>
    </v-row>

    <v-row>
      <v-col>
        <items-list
            :collection="currCollection"
        ></items-list>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import collectionApi from "api/collectionApi";
import CollectionForm from "components/collection/CollectionForm.vue";
import ItemsList from "components/collection/ItemsList.vue";
import VueMarkdown from "vue-markdown/src/VueMarkdown";

export default {
  components: {CollectionForm, ItemsList, VueMarkdown},
  data() {
    return {
      allTags: [],
      currCollection: {title: '', pic: '', description: '', theme: {text: ''}, user: {name: ''}},
      values: []
    }
  },
  created() {
    collectionApi.getAllTags().then(res => {
      res.json().then(tags => {
        tags.forEach(t => {
          this.allTags.push({text: t.name, value: t.id})
        })
      })
    })
    collectionApi.getCollection(this.$route.params.id).then(res => {
      res.json().then(collection => {
        this.currCollection = collection
        this.currCollection.fields.forEach(field => {
          this.values.push({id: field.id, value: ''})
        })
      })
    })
  },
}
</script>

<style scoped>

</style>