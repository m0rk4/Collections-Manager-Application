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
    <v-card-title class="accent lighten-1">
      {{ user.name }}
    </v-card-title>
    <v-divider></v-divider>
    <v-card-text>
      <v-list>
        <v-list-item-title class="text-h6 font-weight-regular">
          {{ $t('regDate') }}:
        </v-list-item-title>
        <v-list-item-subtitle class="subtitle-1 font-weight-light font-italic">
         {{ user.lastVisit ? user.lastVisit : $t('unknown') }}
        </v-list-item-subtitle>
      </v-list>
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