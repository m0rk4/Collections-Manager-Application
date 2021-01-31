<template>
  <v-card>
    <v-card-title class="accent lighten-1">
      {{$t('tags')}}
    </v-card-title>
    <v-card-text>
      <v-container fluid>
        <v-btn
            depressed
            class="mt-2 mx-1 info"
            rounded
            small
            v-for="tag in allTagsAsChips"
            :key="String(tag.value)"
            @click="$router.push({path: '/search', query: {tag: tag.text}})"
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