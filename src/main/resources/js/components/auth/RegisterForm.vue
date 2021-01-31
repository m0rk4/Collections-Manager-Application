<template>
  <v-card max-width="600px">
    <v-toolbar flat color="primary">
      <v-toolbar-title>{{$t('register')}}</v-toolbar-title>
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
              @keyup.enter="login"
              v-model="email"
              label="E-mail"
              :rules="emailRules"
              required
          ></v-text-field>

          <v-text-field
              @keyup.enter="login"
              v-model="login"
              :label="$t('username')"
              :rules="loginRules"
              required
          ></v-text-field>

          <v-text-field
              @keyup.enter="login"
              v-model="pass"
              :label="$tc('password', 1)"
              :rules="[passRules.len]"
              :append-icon="showP1 ? 'mdi-eye' : 'mdi-eye-off'"
              :type="showP1 ? 'text' : 'password'"
              @click:append="showP1 = !showP1"
              required
          ></v-text-field>

          <v-text-field
              @keyup.enter="login"
              v-model="passRep"
              :label="$t('repeat') + ' ' + $tc('password', 0)"
              :rules="[passRules.len, passRules.match]"
              :append-icon="showP2 ? 'mdi-eye' : 'mdi-eye-off'"
              :type="showP2 ? 'text' : 'password'"
              @click:append="showP2 = !showP2"
              required
          ></v-text-field>

          <v-btn
              color="success"
              rounded
              @click="register"
              block
              class="mt-1 mb-4">
            {{$t('register')}}
          </v-btn>

          <social-links></social-links>

        </v-form>

        <v-row class="justify-center mt-4">
          <v-btn
              color="warning"
              rounded
              @click="showLoginPage"
          >{{ $t('login') }}
          </v-btn>
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
        e => !!e
            || 'Email ' + this.$t('isRequired')
      ],
      loginRules: [
        v => (v && v.length <= 15 && v.length >= 3)
            || this.$t('loginRules'),
      ],
      passRules: {
        len: v => (v && v.length <= 15 && v.length >= 5)
            || this.$t('passRules'),
        match: () => this.pass === this.passRep
            || this.$t('diffPass')
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
          this.$store.dispatch(
              'alert/activateAlertAction',
              {message: res.body.message, type: 'error'}
          )
        } else {
          if (this.isDialog)
            this.onClick()
          this.$router.push("/login")
          this.$store.dispatch(
              'alert/activateAlertAction',
              {message: 'Successfully registered', type: 'success'}
          )
        }
      })
      this.$refs.registerForm.reset()
    },
  }
}
</script>

<style scoped>

</style>