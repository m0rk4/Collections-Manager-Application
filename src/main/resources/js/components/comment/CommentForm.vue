<template>
  <v-row class="ma-2">
    <v-col>
      <v-text-field
          label="New comment"
          placeholder="Type text..."
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
import {mapState} from "vuex";

export default {
  props: ['itemId'],
  data() {
    return {
      text: ''
    }
  },
  computed: {
    ...mapState({
      profile: state => state.auth.profile,
    })
  },
  methods: {
    saveComment() {
      if (this.profile) {
        this.$store.dispatch('item/addCommentAction', {
          text: this.text,
          item: {id: this.itemId}
        })
        this.text = ''
      }
    }
  }
}
</script>

<style scoped>

</style>