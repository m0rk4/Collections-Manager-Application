<template>
  <div>
    <v-row class="justify-center ma-8">
      {{ $t('sorryPageNF') }}
    </v-row>
    <v-row class="justify-center ma-8">
      <v-btn
          @click="$router.push('/')"
          rounded>
        {{ $t('mainPage') }}
      </v-btn>
    </v-row>
  </div>
</template>

<script>
export default {
  name: "NotFoundPage"
}
</script>

<style scoped>

</style>