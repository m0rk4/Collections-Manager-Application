<template>
  <v-row class="justify-center mt-8">
    <v-col align="center">
      <register-form :isDialog="false"></register-form>
    </v-col>
  </v-row>
</template>

<script>
import RegisterForm from "components/auth/RegisterForm.vue";

export default {
  components: {RegisterForm}
}
</script>

<style scoped>

</style>