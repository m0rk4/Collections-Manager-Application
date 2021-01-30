<template>
  <v-dialog
      v-model="login"
      max-width="450px">
    <template v-slot:activator="{ on, attrs }">
      <v-btn v-if="$vuetify.breakpoint.mdAndUp" class="mx-2" rounded v-bind="attrs" v-on="on">
        Login
      </v-btn>
      <v-btn v-else small class="mx-2" fab v-bind="attrs" v-on="on">
        <v-icon>
          mdi-login
        </v-icon>
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