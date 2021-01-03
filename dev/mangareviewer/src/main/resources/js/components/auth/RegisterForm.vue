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
        <v-form ref="form">

          <v-text-field
              label="E-mail"
              required
          ></v-text-field>

          <v-text-field
              label="Login"
              required
          ></v-text-field>

          <v-text-field
              label="Password"
              required
          ></v-text-field>

          <v-text-field
              label="Repeat Password"
              required
          ></v-text-field>

          <v-btn block class="mt-1 mb-4">
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

export default {
  components: {SocialLinks},
  props: ['isDialog', 'onClose'],
  data() {
    return {}
  },
  methods: {
    onClick() {
      this.onClose();
    },
    showLoginPage() {
      if (this.onClose)
        this.onClose();
      this.$router.push("/login");
    }
  }
}
</script>

<style scoped>

</style>