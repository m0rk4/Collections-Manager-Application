<template>
  <v-row class="d-flex flex-column flex-lg-row">
    <v-col class="flex-shrink-0 col-12 col-md-4 col-lg-3 col-xl-3">
      <profile-card></profile-card>
    </v-col>
    <v-col class="flex-shrink-0 col-12 col-md-8 col-lg-9 col-xl-9">
      <div class="">
        <user-collections-list></user-collections-list>
        <user-activity></user-activity>
      </div>
    </v-col>
  </v-row>
</template>

<script>
import ProfileCard from "components/user/ProfileCard.vue";
import UserCollectionsList from "components/user/UserCollectionsList.vue";
import UserActivity from "components/user/UserActivity.vue";

export default {
  components: {ProfileCard, UserCollectionsList, UserActivity}
}
</script>

<style scoped>

</style>