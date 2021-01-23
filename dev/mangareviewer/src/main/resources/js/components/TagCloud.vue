<template>
  <v-card>
    <v-toolbar flat>
      <v-toolbar-title>Tags</v-toolbar-title>
    </v-toolbar>
    <v-divider></v-divider>
    <v-card-text>
      <v-container fluid>
        <v-btn
            class="my-2 mx-1"
            rounded
            small
            v-for="tag in allTagsAsChips"
            :key="String(tag.value)"
        >
          {{ tag.text }}
        </v-btn>
      </v-container>
    </v-card-text>
  </v-card>
</template>

<script>
import {mapGetters} from 'vuex'

export default {
  created() {
    this.$store.dispatch('tag/getAllTagsAction')
  },
  computed: {
    ...mapGetters('tag', ['allTagsAsChips'])
  }
}
</script>

<style scoped>

</style>