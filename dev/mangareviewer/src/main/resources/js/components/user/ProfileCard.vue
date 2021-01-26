<template>
  <v-card>
    <v-avatar
        color="indigo"
        width="100%"
        height="100%"
        tile
    >
      <v-img :src="profile.userpic"></v-img>
    </v-avatar>
    <v-divider></v-divider>
    <v-card-title>
      {{ profile.name }}
    </v-card-title>
    <v-divider></v-divider>
    <v-card-text>
      <v-container fluid>
        <v-card class="pa-4">
          <div>Last visit</div>
          <div><i>{{ profile.lastVisit }}</i></div>
        </v-card>
      </v-container>
    </v-card-text>
  </v-card>
</template>

<script>
import {mapState} from 'vuex'

export default {
  computed: {
    ...mapState({
      profile: state => state.auth.profile,
    }),
  }
}
</script>

<style scoped>

</style>