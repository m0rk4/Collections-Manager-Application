<template>
  <v-dialog v-model="active">
    <template v-slot:activator="{ on, attrs }">
      <v-btn
          rounded
          color="primary"
          v-bind="attrs"
          v-on="on">
        {{ collectionId ? 'Update' : 'Create' }}
      </v-btn>
    </template>
    <collection-form
        :toUpdate="collectionId"
        :authorId="userId"
        :onClose="closeDialog"
    ></collection-form>
  </v-dialog>
</template>

<script>
import CollectionForm from "components/user/CollectionForm.vue"

export default {
  props: ['collectionId', 'userId'],
  components: {CollectionForm},
  data() {
    return {
      active: false,
    }
  },
  methods: {
    closeDialog() {
      this.active = false
    }
  }
}
</script>

<style scoped>

</style>