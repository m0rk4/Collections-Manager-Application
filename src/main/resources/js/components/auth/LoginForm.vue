<template>
  <v-card max-width="600px">
    <v-toolbar flat color="primary">
      <v-toolbar-title>{{ $t('login') }}</v-toolbar-title>
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
              @keyup.enter="login"
              v-model="username"
              :label="$t('username') + ' ' + $t('or') + ' E-Mail'"
              required
          ></v-text-field>

          <v-text-field
              @keyup.enter="login"
              v-model="password"
              :label="$tc('password', 1)"
              :append-icon="showPL ? 'mdi-eye' : 'mdi-eye-off'"
              :type="showPL ? 'text' : 'password'"
              @click:append="showPL = !showPL"
              required
          ></v-text-field>

          <v-btn
              color="success"
              rounded
              @click="login"
              block class="mt-1 mb-4">
            {{ $t('enter') }}
          </v-btn>

          <social-links></social-links>

        </v-form>

        <v-row class="justify-center mt-4">
          <v-btn color="warning" @click="showRegisterPage" rounded>{{$t('register')}}</v-btn>
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
          this.$store.dispatch(
              'alert/activateAlertAction',
              {message: res.body.message, type: 'error'}
          )
        } else {
          this.$router.go(0)
          this.$store.dispatch(
              'alert/activateAlertAction',
              {message: 'Successfully Authenticated', type: 'success'}
          )
        }
      })
    },
  }
}
</script>

<style scoped>

</style>