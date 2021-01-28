<template>
  <v-card>
    <v-avatar
        color="indigo"
        width="100%"
        height="100%"
        tile
    >
      <v-img :src="user.userpic"></v-img>
    </v-avatar>
    <v-divider></v-divider>
    <v-card-title>
      {{ user.name }}
    </v-card-title>
    <v-divider></v-divider>
    <v-card-text>
      <v-container fluid>
        <v-card class="pa-4">
          <div>Registration Date</div>
          <div><i>{{ user.lastVisit }}</i></div>
        </v-card>
      </v-container>
    </v-card-text>
  </v-card>
</template>

<script>
export default {
  props: ['user'],
}
</script>

<style scoped>

</style>