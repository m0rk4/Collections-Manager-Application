<template>
  <v-card>
    <v-toolbar flat>
      <v-toolbar-title>Last Added Items</v-toolbar-title>
    </v-toolbar>
    <v-divider></v-divider>
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