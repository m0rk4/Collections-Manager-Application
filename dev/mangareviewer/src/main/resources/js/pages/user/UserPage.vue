<template>
  <v-row class="d-flex flex-column flex-lg-row">
    <v-col class="flex-shrink-0 col-12 col-md-4 col-lg-3 col-xl-3">
      <profile-card :user="user"></profile-card>
    </v-col>
    <v-col class="flex-shrink-0 col-12 col-md-8 col-lg-9 col-xl-9">
      <user-collections-list
          v-if="this.$route.params.id || (this.currUser && this.currUser.id)"
          :userId="this.$route.params.id || this.currUser.id"
      ></user-collections-list>
      <user-activity></user-activity>
    </v-col>
  </v-row>
</template>

<script>
import ProfileCard from "components/user/ProfileCard.vue";
import UserCollectionsList from "components/user/UserCollectionsList.vue";
import UserActivity from "components/user/UserActivity.vue";
import {mapState} from "vuex";
import profileApi from "api/profileApi";

export default {
  components: {ProfileCard, UserCollectionsList, UserActivity},
  data() {
    return {
      user: {}
    }
  },
  computed: {
    ...mapState({
      currUser: state => state.auth.profile,
    })
  },
  watch: {
    $route: function (newValue) {
      this.updateProfileInfo()
    }
  },
  created() {
    this.updateProfileInfo()
  },
  methods: {
    updateProfileInfo() {
      if (this.$route.params.id || (this.currUser && this.currUser.id)) {
        profileApi.getOne(this.$route.params.id || this.currUser.id).then(res => {
          res.json().then(user => {
            this.user = user
          })
        })
        this.$store.dispatch(
            'collection/getAllUserCollectionsAction',
            this.$route.params.id || this.currUser.id
        )
      } else {
        this.$router.push('/')
      }
    }
  }
}
</script>

<style scoped>

</style>