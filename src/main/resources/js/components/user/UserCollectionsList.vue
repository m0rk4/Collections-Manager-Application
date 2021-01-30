<template>
  <v-card class="mb-8" >
    <v-toolbar flat class="accent lighten-1">
      <v-toolbar-title>User collections</v-toolbar-title>
      <v-spacer></v-spacer>
      <collection-dialog
          v-if="currUser && (userId === currUser.id || isAdmin)"
          :userId="userId"
      ></collection-dialog>
    </v-toolbar>
    <v-card-text>
      <v-container fluid>
        <div class="scroller">
          <div class="scroller-item mr-3 pa-2" v-for="c in userCollections" :key="c.id">
            <div class="scroller-item-props">
              <v-card>
                <v-avatar
                    class="profile"
                    color="grey"
                    width="100%"
                    height="100%"
                    tile
                >
                  <v-img
                      :src="c.pic || 'https://res.cloudinary.com/dr7gxyl6z/image/upload/v1610476635/cwdemo/pic_noimage01_n0hc0r.jpg'"
                  ></v-img>
                </v-avatar>

                <v-card-title
                    @click="$router.push({path: `/collection/${c.id}`})"
                    class="collectionTitle accent lighten-2">
                    {{ c.title }}
                </v-card-title>

                <v-card-text class="mt-2">
                  <v-list>
                    <v-list-item-title>
                      Theme:
                    </v-list-item-title>
                    <v-list-item-title class="font-weight-light">
                      {{ c.theme.text }}
                    </v-list-item-title>
                  </v-list>
                </v-card-text>
                <v-divider></v-divider>
                <v-card-actions v-if="currUser && (userId === currUser.id || isAdmin)">
                      <collection-dialog :collectionId="c.id"></collection-dialog>
                      <v-btn
                          text
                          rounded
                          @click="$store.dispatch('collection/deleteCollectionAction', c)"
                      >Delete</v-btn>
                </v-card-actions>
              </v-card>
            </div>
          </div>
        </div>
      </v-container>
    </v-card-text>
  </v-card>
</template>

<script>
import {mapState, mapGetters} from "vuex";
import CollectionDialog from "components/user/CollectionDialog.vue"

export default {
  props: ['userId'],
  components: {CollectionDialog},
  computed: {
    ...mapState({
      userCollections: state => state.collection.userCollections,
      currUser: state => state.auth.profile,
    }),
    ...mapGetters('auth', ['isAdmin'])
  }
}
</script>

<style scoped>
.collectionTitle {
  cursor: pointer;
}
.scroller {
  overflow-x: auto;
  display: flex;
  flex-wrap: nowrap;
}

.scroller-item {
  flex: 0 0 auto;
}

.scroller-item-props {
  width: 200px;
  position: relative !important;
}
</style>