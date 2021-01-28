<template>
  <v-row class="justify-center mt-8">
    <v-col align="center">
      <login-form :isDialog="false"></login-form>
    </v-col>
  </v-row>
</template>

<script>
import LoginForm from "components/auth/LoginForm.vue";

export default {
  components: {LoginForm}
}
</script>

<style scoped>

</style>