<template>
  <v-card
      class="mx-auto mb-4"
  >
    <v-card-title class="accent lighten-2">
      {{ item.title }}
      <v-spacer></v-spacer>
      <item-dialog
          :item="item"
          :collection="item.collection"
      ></item-dialog>
    </v-card-title>

    <v-card-text class="headline font-weight-bold">
      <v-container fluid class="mt-2">
        <v-btn
            class="mt-2 mx-1 info"
            rounded
            depressed
            small
            v-for="tag in item.tags"
            :key="String(tag.name)"
            @click="$router.push({path: '/search', query: {tag: tag.name}})"
        >
          {{ tag.name }}
        </v-btn>
      </v-container>
    </v-card-text>

    <v-card-actions>
      <v-list-item>
        <v-list-item-avatar>
          <v-img
              style="cursor: pointer"
              @click="$router.push({path: `/user/${item.collection.user.id}`})"
              class="elevation-6"
              alt=""
              :src="item.collection.user.userpic"
          ></v-img>
        </v-list-item-avatar>

        <v-list-item-content>
          <v-list-item-title
              style="cursor: pointer"
              class="text-h6 font-weight-regular"
              @click="$router.push({path: `/user/${item.collection.user.id}`})"
          >
            {{ item.collection.user.name }}
          </v-list-item-title>
        </v-list-item-content>

          <v-list-item-content class="rounded-xl accent lighten-3" style="cursor: pointer">
            <span class="mx-2 text-h6 font-weight-regular"
                  @click="$router.push({path: `/collection/${item.collection.id}`})"
            >Collection:
            </span>
            <span class="mx-2 subtitle-1 font-weight-regular"
                  @click="$router.push({path: `/collection/${item.collection.id}`})"
            >{{ item.collection.title }}
            </span>
          </v-list-item-content>
      </v-list-item>
    </v-card-actions>
  </v-card>
</template>

<script>
import ItemDialog from "components/main/ItemDialog.vue"

export default {
  name: 'MainItem',
  components: {ItemDialog},
  props: ['item'],
}
</script>

<style scoped>

</style>