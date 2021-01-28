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
    <item-node :targetItem="item"
               :currCollection="collection"
               :filteredKeys="collection.fields.map(f => f.text)"
               :isDialog="true"
               :deleteItem="deleteItem"
    ></item-node>
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
  },
  methods: {
    deleteItem(item) {
      this.$store.dispatch('item/deleteItemAction', item)
    }
  }
}
</script>

<style scoped>

</style>