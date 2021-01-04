<template>
  <v-app>
    <v-app-bar app>
      <v-app-bar-title>Reviewer</v-app-bar-title>
      <v-spacer></v-spacer>

      <v-btn href="/" icon>
        <v-icon>mdi-magnify</v-icon>
      </v-btn>

      <v-divider vertical inset></v-divider>

      <login-dialog></login-dialog>
      <register-dialog></register-dialog>
    </v-app-bar>


    <v-main class="my-4">
      <v-container>
        <router-view></router-view>
      </v-container>
    </v-main>

    <v-alert class="ml-auto mr-4"
             dismissible
             dense
             :type="typeAlert"
             :value="visibilityAlert"
             transition="scale-transition"
    >
      {{ messageAlert }}
    </v-alert>

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
import RegisterDialog from "components/auth/RegisterDialog.vue";
import {mapState} from 'vuex'

export default {
  components: {LoginDialog, RegisterDialog},
  computed: {
    ...mapState({
      messageAlert: state => state.alert.message,
      typeAlert: state => state.alert.type,
      visibilityAlert: state => state.alert.isVisible,
    })
  }
}
</script>

<style scoped>

</style>