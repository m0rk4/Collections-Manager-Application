<template>
  <div>
    <v-divider></v-divider>

    <v-row class="justify-center mt-2">
      {{$t('socialMedia')}}
    </v-row>

    <v-row class="justify-center mt-4">
      <v-btn href="/oauth2/authorization/facebook" icon>
        <v-icon light color="blue">mdi-facebook</v-icon>
      </v-btn>
      <v-btn href="/oauth2/authorization/github" icon>
        <v-icon light color="red">mdi-git</v-icon>
      </v-btn>
    </v-row>

    <v-divider class="my-4"></v-divider>
  </div>
</template>

<script>
export default {
}
</script>

<style scoped>

</style>