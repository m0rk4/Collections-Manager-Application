<template>
  <v-row class="ma-2">
    <v-col>
      <v-text-field
          label="New comment"
          placeholder="Text here..."
          v-model="text"
          @keyup.enter="saveComment"
      ></v-text-field>
      <v-btn @click="saveComment">
        Add
      </v-btn>
    </v-col>
  </v-row>
</template>

<script>
export default {
  props: ['itemId'],
  data() {
    return {
      text: ''
    }
  },
  methods: {
    saveComment() {
      this.$store.dispatch('item/addCommentAction', {
        text: this.text,
        item: {
          id: this.itemId
        }
      })
      this.text = ''
    }
  }
}
</script>

<style scoped>

</style>