<template>
  <v-row>
    <v-col>
      <span class="headline font-weight-bold">
        Results for: {{ query || tag }} - ({{ items.length }}) Items
      </span>
    </v-col>
    <v-col>
      <v-card
          class="mx-auto"
          tile
      >
        <v-list dense>
          <v-subheader>RESULTS</v-subheader>
          <v-list-item-group>
            <v-list-item
                @click="showItem(item)"
                v-for="(item, i) in items"
                :key="i"
            >
              <v-list-item-content>
                <v-list-item-title v-text="item.title"></v-list-item-title>
              </v-list-item-content>
              <v-list-item-content>
                <v-list-item-subtitle v-text="item.collection.title"></v-list-item-subtitle>
              </v-list-item-content>
              <v-list-item-avatar>
                  <v-img
                      :src="item.collection.pic"
                  ></v-img>
              </v-list-item-avatar>
            </v-list-item>
          </v-list-item-group>
        </v-list>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import {mapState} from "vuex";

export default {
  computed: {
    ...mapState({
      items: state => state.item.searchItems
    }),
    query() {
      return this.$route.query.query
    },
    tag() {
      return this.$route.query.tag
    }
  },
  created() {
    this.$store.dispatch('item/searchItemsAction',
        {query: this.query, tag: this.tag})
  },
  watch: {
    $route: function (newVal) {
      this.$store.dispatch('item/searchItemsAction',
          {query: this.query, tag: this.tag})
    }
  },
  methods: {
    showItem(item) {
      this.$router.push({path: `/collection/${item.collection.id}`})
    }
  }
}
</script>

<style scoped>

</style>