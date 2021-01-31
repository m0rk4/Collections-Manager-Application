<template>
  <v-dialog v-model="active">
    <template v-slot:activator="{ on, attrs }">
      <v-btn
          depressed
          rounded
          v-if="!collectionId"
          v-bind="attrs"
          fab
          small
          v-on="on">
        <v-icon>mdi-plus</v-icon>
      </v-btn>
      <v-btn
          v-else
          rounded
          text
          v-bind="attrs"
          v-on="on"
      >{{$t('update')}}
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