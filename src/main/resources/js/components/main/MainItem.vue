<template>
  <v-card
      class="mx-auto mb-2"
  >
    <v-card-title>
      <span class="title font-weight-medium">{{ item.title }}</span>
      <v-spacer></v-spacer>
      <item-dialog :item="item" :collection="item.collection"></item-dialog>
    </v-card-title>

    <v-card-text class="headline font-weight-bold">
      <v-container fluid class="mt-2">
        <v-btn
            class="my-2 mx-1"
            rounded
            small
            v-for="tag in item.tags"
            :key="String(tag.value)"
        >
          {{ tag.name }}
        </v-btn>
      </v-container>
    </v-card-text>

    <v-card-actions>
      <v-list-item class="grow">
        <v-list-item-avatar>
          <v-img
              class="elevation-6"
              alt=""
              :src="item.collection.user.userpic"
          ></v-img>
        </v-list-item-avatar>

        <v-list-item-content>
          <v-list-item-title
              @click="$router.push({path: `/user/${item.collection.user.id}`})"
          >
            {{ item.collection.user.name }}
          </v-list-item-title>
        </v-list-item-content>

        <v-list-item-content>
            <span class="subheading mr-2"
                  @click="$router.push({path: `/collection/${item.collection.id}`})"
            >Collection:
            </span>
            <span class="mr-1" @click="$router.push({path: `/collection/${item.collection.id}`})"
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
  props: ['item']
}
</script>

<style scoped>

</style>