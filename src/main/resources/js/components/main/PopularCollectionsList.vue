<template>
  <v-card class="mb-8">
    <v-card-title class="accent lighten-1">
      Popular Collections
      <v-spacer></v-spacer>
      <collection-dialog
          v-if="currUser"
          :userId="currUser.id"
      ></collection-dialog>
    </v-card-title>

    <v-card-text class="subtitle-1 font-weight-light pt-4">
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
          <v-list-item-group>
            <v-list-item class="accent lighten-2">
              <v-list-item-avatar>
                <v-avatar
                    size="56"
                >
                  <img :src="item.pic">
                </v-avatar>
              </v-list-item-avatar>

              <v-list-item-content>
                <v-list-item-title class="subtitle-1 font-weight-regular">{{ item.title }} -
                  <strong>{{ item.items.length }}</strong></v-list-item-title>
              </v-list-item-content>

              <v-list-item-action>
                <v-btn
                    rounded
                    depressed
                    small
                    @click="$router.push({path: `/collection/${item.id}`})"
                >
                  <template v-if="$vuetify.breakpoint.smAndUp">
                    View Collection
                  </template>
                  <v-icon :class="$vuetify.breakpoint.smAndUp ? 'ml-1' : ''">
                    mdi-open-in-new
                  </v-icon>
                </v-btn>
              </v-list-item-action>
            </v-list-item>
          </v-list-item-group>
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