<template>
  <v-container>
    <main-item
        v-for="item in sortedItems"
        :item="item"
        :key="item.id"
    ></main-item>
    <lazy-loader></lazy-loader>
  </v-container>
</template>

<script>
import LazyLoader from "components/main/LazyLoader.vue"
import MainItem from "components/main/MainItem.vue"
import {mapGetters} from "vuex"

export default {
  components: {MainItem, LazyLoader},
  computed: {
    ...mapGetters('item', ['sortedItems'])
  }
}
</script>

<style scoped>

</style>