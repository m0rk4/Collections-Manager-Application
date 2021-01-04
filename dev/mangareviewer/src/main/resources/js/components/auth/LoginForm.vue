<template>
  <v-card max-width="600px">
    <v-toolbar flat>
      <v-toolbar-title>Login</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn v-if="isDialog" icon @click="onClick">
        <v-icon>mdi-close</v-icon>
      </v-btn>
    </v-toolbar>
    <v-divider></v-divider>
    <v-card-text>
      <v-container>
        <v-form ref="loginForm">

          <v-text-field
              v-model="username"
              label="Login or E-mail"
              required
          ></v-text-field>

          <v-text-field
              v-model="password"
              label="Password"
              :append-icon="showPL ? 'mdi-eye' : 'mdi-eye-off'"
              :type="showPL ? 'text' : 'password'"
              @click:append="showPL = !showPL"
              required
          ></v-text-field>

          <v-btn @click="login" block class="mt-1 mb-4">
            Enter
          </v-btn>

          <social-links></social-links>

        </v-form>

        <v-row class="justify-center mt-4">
          <v-btn @click="showRegisterPage">Register</v-btn>
        </v-row>

      </v-container>
    </v-card-text>
  </v-card>
</template>

<script>
import SocialLinks from "components/auth/SocialLinks.vue";
import authApi from "api/authApi";

export default {
  components: {SocialLinks},
  props: ['isDialog', 'onClose'],
  data() {
    return {
      showPL: false,
      username: '',
      password: ''
    }
  },
  methods: {
    onClick() {
      this.onClose();
      this.$refs.loginForm.reset()
    },
    showRegisterPage() {
      if (this.onClose)
        this.onClose();
      this.$router.push("/register")
    },
    login() {
      authApi.login(this.username, this.password).then(res => {
        if (res.body.message) {
          console.log(res.body.message)
        } else {
          if (this.isDialog)
            this.onClick();
          if (this.$router.currentRoute.path !== '/')
            this.$router.push('/')
        }
      })
    }
  }
}
</script>

<style scoped>

</style>