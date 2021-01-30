<template>
  <v-dialog v-model="active">
    <template v-slot:activator="{ on, attrs }">
      <v-btn
          icon
          rounded
          depressed
          fib
          v-bind="attrs"
          v-on="on">
        <v-icon>mdi-open-in-new</v-icon>
      </v-btn>
    </template>
    <item-node :targetItem="item"
               :currCollection="collection"
               :filteredKeys="collection.fields.map(f => f.text)"
               :isDialog="true"
               :deleteItem="deleteItem"
               :updateItem="updateItem"
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
    },
    updateItem(item) {
      this.$store.commit('item/setModifyItemMutation', item)
      this.$router.push({path: `/collection/${this.collection.id}`})
    }
  }
}
</script>

<style scoped>

</style>