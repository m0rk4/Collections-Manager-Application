<template>
  <v-row>
    <v-col class="col-12 col-md-8 col-lg-9 col-xl-9" >
      <popular-collections-list/>
      <manga-list></manga-list>
    </v-col>
    <v-col class="col-12 col-md-4 col-lg-3 col-xl-3">
      <tag-cloud></tag-cloud>
    </v-col>
  </v-row>
</template>

<script>
import PopularCollectionsList from "components/PopularCollectionsList.vue";
import MangaList from "components/MangaList.vue";
import TagCloud from "components/TagCloud.vue";

export default {
  components: {TagCloud, PopularCollectionsList, MangaList}
}
</script>

<style scoped>

</style>