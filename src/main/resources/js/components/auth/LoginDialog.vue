<template>
  <v-dialog
      v-model="login"
      max-width="450px">
    <template v-slot:activator="{ on, attrs }">
      <v-btn class="mx-2" text v-bind="attrs" v-on="on">
        Login
      </v-btn>
    </template>
   <login-form :isDialog="true" :onClose="onClose"></login-form>
  </v-dialog>
</template>

<script>
import LoginForm from "components/auth/LoginForm.vue";

export default {
  components: {LoginForm},
  data() {
    return {
      login: false
    }
  },
  methods: {
    onClose() {
      this.login = false;
    }
  }
}
</script>

<style scoped>

</style>