<template>
  <v-dialog v-model="active">
    <template v-slot:activator="{ on, attrs }">
      <v-btn
          rounded
          color="primary"
          v-bind="attrs"
          v-on="on">
        View...
      </v-btn>
    </template>
    <item-node :item="item" :currCollection="collection"></item-node>
  </v-dialog>
</template>

<script>
import ItemNode from "components/collection/ItemNode.vue"

export default {
  props: ['item', 'collection'],
  components: {ItemNode},
  data() {
    return {
      active: false
    }
  }
}
</script>

<style scoped>

</style>