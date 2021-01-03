<template>
  <v-card max-width="600px">
    <v-toolbar flat>
      <v-toolbar-title>Registration</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn v-if="isDialog" icon @click="onClick">
        <v-icon>mdi-close</v-icon>
      </v-btn>
    </v-toolbar>
    <v-divider></v-divider>
    <v-card-text>
      <v-container>
        <v-form ref="registerForm"
                v-model="valid"
                lazy-validation>

          <v-text-field
              v-model="email"
              label="E-mail"
              :rules="emailRules"
              required
          ></v-text-field>

          <v-text-field
              v-model="login"
              label="Login"
              :rules="loginRules"
              required
          ></v-text-field>

          <v-text-field
              v-model="pass"
              label="Password"
              :rules="[passRules.len]"
              :append-icon="showP1 ? 'mdi-eye' : 'mdi-eye-off'"
              :type="showP1 ? 'text' : 'password'"
              @click:append="showP1 = !showP1"
              required
          ></v-text-field>

          <v-text-field
              v-model="passRep"
              label="Repeat Password"
              :rules="[passRules.len, passRules.match]"
              :append-icon="showP2 ? 'mdi-eye' : 'mdi-eye-off'"
              :type="showP2 ? 'text' : 'password'"
              @click:append="showP2 = !showP2"
              required
          ></v-text-field>

          <v-btn @click="register" block class="mt-1 mb-4">
            Register
          </v-btn>

          <social-links></social-links>

        </v-form>

        <v-row class="justify-center mt-4">
          <v-btn @click="showLoginPage">Login</v-btn>
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
      showP1: false,
      showP2: false,
      valid: true,
      email: '',
      login: '',
      pass: '',
      passRep: '',
      emailRules: [
        e => !!e || 'Email is required'
      ],
      loginRules: [
        v => (v && v.length <= 15 && v.length >= 3) || 'Login must be less than 15 characters and bigger than 3 characters',
      ],
      passRules: {
        len: v => (v && v.length <= 15 && v.length >= 5) || 'Password must be less than 15 characters and bigger than 5 characters',
        match: () => this.pass === this.passRep || 'Different Passwords'
      }
    }
  },
  methods: {
    onClick() {
      this.onClose();
      this.$refs.registerForm.reset()
    },
    showLoginPage() {
      if (this.onClose)
        this.onClose();
      this.$router.push("/login");
    },
    register() {
      if (!this.$refs.registerForm.validate()) return
      authApi.register(this.email, this.login, this.pass).then(res => {
        if (res.body.message) {
          console.log(res.body.message)
        } else {
          this.$router.push("/login");
        }
      })
      this.$refs.registerForm.reset()
    },
  }
}
</script>

<style scoped>

</style>