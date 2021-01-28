<template>
  <v-row>
    <v-col class="col-12 col-md-8 col-lg-9 col-xl-9" >
      <popular-collections-list/>
      <last-added-items></last-added-items>
    </v-col>
    <v-col class="col-12 col-md-4 col-lg-3 col-xl-3">
      <tag-cloud></tag-cloud>
    </v-col>
  </v-row>
</template>

<script>
import PopularCollectionsList from "components/main/PopularCollectionsList.vue"
import LastAddedItems from "components/main/LastAddedItems.vue"
import TagCloud from "components/main/TagCloud.vue"

export default {
  components: {TagCloud, PopularCollectionsList, LastAddedItems}
}
</script>

<style scoped>

</style>