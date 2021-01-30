<template>
  <v-card>
    <v-card-title class="accent lighten-1">
      Last Added Items
    </v-card-title>
    <v-card-text>
      <main-item-list></main-item-list>
    </v-card-text>
  </v-card>
</template>

<script>
import MainItemList from "components/main/MainItemList.vue"

export default {
  components: {MainItemList},
  created() {
    this.$store.dispatch('item/loadPageAction')
  }
}
</script>

<style scoped>

</style>