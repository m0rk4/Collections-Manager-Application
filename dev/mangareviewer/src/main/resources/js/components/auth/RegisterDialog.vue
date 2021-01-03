<template>
  <v-dialog
      v-model="register"
      max-width="450px">
    <template class="mx-2" v-slot:activator="{ on, attrs }">
      <v-btn v-bind="attrs" v-on="on">
        Register
      </v-btn>
    </template>
    <register-form :isDialog="true" :onClose="onClose"></register-form>
  </v-dialog>
</template>

<script>
import RegisterForm from "components/auth/RegisterForm.vue";

export default {
  components: {RegisterForm},
  data() {
    return {
      register: false
    }
  },
  methods: {
    onClose() {
      this.register = false;
    }
  }
}
</script>

<style scoped>

</style>