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
        <v-form
            ref="form"
        >
          <v-text-field
              label="Login or E-mail"
              required
          ></v-text-field>

          <v-text-field
              label="Password"
              required
          ></v-text-field>

          <v-row class="justify-center mt-1">
            <v-btn>
              Enter
            </v-btn>
          </v-row>

          <v-row class="justify-center mt-8">
            Enter via social media
          </v-row>

          <v-row class="justify-center mt-4">
            <v-btn href="/oauth2/authorization/facebook" icon>
              <v-icon>mdi-facebook</v-icon>
            </v-btn>
            <v-btn href="/oauth2/authorization/github" icon>
              <v-icon>mdi-git</v-icon>
            </v-btn>
          </v-row>
        </v-form>

        <v-divider class="my-4"></v-divider>

        <v-row v-if="!isDialog" class="justify-center mt-4">
          <v-btn href="/register">Register</v-btn>
        </v-row>
        <v-row v-else class="justify-center mt-4">
          <v-btn>Register</v-btn>
        </v-row>

      </v-container>
    </v-card-text>
  </v-card>
</template>

<script>
export default {
  props: ['isDialog', 'onClose'],
  methods: {
    onClick() {
      this.onClose();
    }
  }
}
</script>

<style scoped>

</style>