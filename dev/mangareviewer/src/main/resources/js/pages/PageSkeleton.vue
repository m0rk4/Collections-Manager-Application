<template>
  <v-app>
    <v-app-bar app>
      <v-app-bar-title>Reviewer</v-app-bar-title>
      <v-spacer></v-spacer>

      <v-btn icon>
        <v-icon>mdi-magnify</v-icon>
      </v-btn>

      <v-divider vertical inset></v-divider>

      <login-dialog></login-dialog>
      <v-btn>
        Register
      </v-btn>
    </v-app-bar>

    <v-main class="my-4">
      <v-container>
        <router-view></router-view>
      </v-container>
    </v-main>

    <v-footer padless>
      <v-col
          class="text-center"
          cols="12"
      >
        {{ new Date().getFullYear() }} — <strong>Reviewer</strong>
      </v-col>
    </v-footer>
  </v-app>
</template>

<script>
import LoginDialog from "components/auth/LoginDialog.vue";

export default {
  components: {LoginDialog},
}
</script>

<style scoped>

</style>