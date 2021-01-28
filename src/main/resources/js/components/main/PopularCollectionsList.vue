<template>
  <v-card class="mb-8">
    <v-card-title class="white--text orange darken-4">
      Popular Collections
      <v-spacer></v-spacer>
      <collection-dialog
          v-if="currUser"
          :userId="currUser.id"
      ></collection-dialog>
    </v-card-title>

    <v-card-text class="pt-4">
      Collections with maximum items included
    </v-card-text>

    <v-divider></v-divider>
    <v-card-text>
      <v-virtual-scroll
          :items="sortedCollections"
          :item-height="50"
          height="300"
      >
        <template v-slot:default="{ item }">
          <v-list-item class="mb-4">
            <v-list-item-avatar>
              <v-avatar
                  size="56"
                  class="white--text"
              >
                <img :src="item.pic">
              </v-avatar>
            </v-list-item-avatar>

            <v-list-item-content>
              <v-list-item-title>{{ item.title }}</v-list-item-title>
            </v-list-item-content>
            <v-list-item-content>
              <v-list-item-title>{{ 'Items - ' + item.items.length }}</v-list-item-title>
            </v-list-item-content>


            <v-list-item-action>
              <v-btn
                  depressed
                  small
                  @click="$router.push({path: `/collection/${item.id}`})"
              >
                View Collection
                <v-icon
                    color="orange darken-4"
                    right
                >
                  mdi-open-in-new
                </v-icon>
              </v-btn>
            </v-list-item-action>
          </v-list-item>
        </template>
      </v-virtual-scroll>
    </v-card-text>
  </v-card>
</template>

<script>
import CollectionDialog from "components/user/CollectionDialog.vue"
import {mapGetters, mapState} from "vuex"

export default {
  components: {CollectionDialog},
  computed: {
    ...mapState({
      currUser: state => state.auth.profile,
    }),
    ...mapGetters('collection', ['sortedCollections'])
  },
  created() {
    this.$store.dispatch('collection/getAllCollectionsAction')
  }
}
</script>

<style scoped>

</style>