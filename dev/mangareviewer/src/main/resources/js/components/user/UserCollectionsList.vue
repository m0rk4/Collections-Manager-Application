<template>
  <v-card class="mb-8">
    <v-toolbar flat>
      <v-toolbar-title>User collections</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn @click="$router.push('create-collection')">
        Create New Collection
      </v-btn>
    </v-toolbar>
    <v-divider></v-divider>
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
                <v-divider></v-divider>
                <v-card-title>
                  <v-btn
                      text
                      @click="$router.push({path: `collection/${c.id}`})"
                  >
                    {{ c.title }}
                  </v-btn>
                </v-card-title>
                <v-divider></v-divider>
                <v-card-text>
                  <v-container fluid>
                    <div>Theme: {{ c.theme.text }}</div>
                    <div>Author: {{ }}</div>
                  </v-container>
                </v-card-text>
                <v-card-actions>
                  <v-container>
                    <v-row>
                      <v-btn
                          text
                          color="primary"
                          @click="$router.push({ path: `/update-collection/${c.id}` })"
                      >
                        Update
                      </v-btn>
                      <v-btn
                          text
                          color="primary"
                          @click="$store.dispatch('collection/deleteCollectionAction', c)"
                      >
                        Delete
                      </v-btn>
                    </v-row>
                  </v-container>
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
import {mapState} from "vuex";

export default {
  created() {
    this.$store.dispatch('collection/getAllUserCollectionsAction',
        this.$route.params.id || this.currUser.id)
  },
  computed: {
    ...mapState({
      userCollections: state => state.collection.userCollections,
      currUser: state => state.auth.profile,
    })
  }
}
</script>

<style scoped>
.scroller {
  overflow-x: auto;
  display: flex;
  flex-wrap: nowrap;
}

.scroller-item {
  flex: 0 0 auto;
}

.scroller-item-props {
  width: 158px;
  position: relative !important;
}
</style>