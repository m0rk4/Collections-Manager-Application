<template>
  <v-row>
    <v-col sm="12" md="6" xl="6" lg="6" cols="12">
      <span class="text-h1 text-left font-weight-light">
        {{ $t('resultsFor') }} "{{ query || tag }}":
      </span>
    </v-col>
    <v-col>
      <v-card
          class="mx-auto"
          tile
      >
        <v-list dense class="accent lighten-1">
          <v-subheader
              class="text-h4 font-weight-regular">
            {{$t('searchResults')}}:
          </v-subheader>
          <v-list-item-group>
            <v-list-item
                @click="showItem(item)"
                v-for="(item, i) in items"
                :key="i"
            >
              <v-list-item-content>
                <v-list-item-title
                    class="text-h6 font-weight-light"
                >
                  <span class="font-weight-medium">{{$t('item')}}:</span> {{ item.title }}
                </v-list-item-title>
              </v-list-item-content>
              <v-list-item-content v-if="$vuetify.breakpoint.mdAndUp">
                <v-list-item-subtitle
                    class="text-h6 font-weight-light"
                >
                  <span class="font-weight-medium">{{$t('collection')}}:</span> {{ item.collection.title }}
                </v-list-item-subtitle>
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