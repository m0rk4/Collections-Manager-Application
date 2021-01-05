<template>
  <div>
    <v-btn rounded @click="lockUsers">Lock</v-btn>
    <v-btn rounded @click="unlockUsers">Unlock</v-btn>
    <v-btn rounded @click="deleteUsers">Delete</v-btn>
    <v-btn rounded @click="makeAdmin">Make Admin</v-btn>
    <v-btn rounded @click="unmakeAdmin">Unmake Admin</v-btn>
    <v-data-table
        v-model="selected"
        :headers="headers"
        :items="users"
        :single-select="singleSelect"
        item-key="name"
        show-select
        class="elevation-1"
    >
      <template v-slot:top>
        <v-switch
            v-model="singleSelect"
            label="Single select"
            class="pa-3"
        ></v-switch>
      </template>
    </v-data-table>
  </div>
</template>

<script>
import profileApi from "api/profileApi";

export default {
  data() {
    return {
      singleSelect: false,
      selected: [],
      headers: [
        {
          text: 'Username',
          align: 'start',
          sortable: false,
          value: 'name',
        },
        {text: 'Email', value: 'email'},
        {text: 'Last Visit', value: 'lastVisit'},
        {text: 'Is Non Locked', value: 'nonLocked'},
        {text: 'Roles', value: 'roles'}
      ],
      users: []
    }
  },
  beforeMount() {
    profileApi.getAll().then(res => {
      res.json().then(data => {
        console.log(data)
        this.users = data
      })
    })
  },
  methods: {
    lockUsers() {
      this.processProfilePromise(profileApi.blockUsers(this.selected))
    },
    unlockUsers() {
      this.processProfilePromise(profileApi.unlockUsers(this.selected))
    },
    deleteUsers() {
      this.processProfilePromise(profileApi.deleteUsers(this.selected))
    },
    makeAdmin() {
      this.processProfilePromise(profileApi.makeAdmins(this.selected))
    },
    unmakeAdmin() {
      this.processProfilePromise(profileApi.unmakeAdmins(this.selected))
    },
    processProfilePromise(promise) {
      promise.then(res => {
        res.json().then(data => {
          this.users = data
          this.selected = []
        })
      })
    }
  }
}
</script>

<style scoped>

</style>