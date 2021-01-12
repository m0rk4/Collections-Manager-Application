import Vue from 'vue'

const profile = Vue.resource('api/profile{/id}')

export default {
    getAll: () => profile.get(),
    getOne: (id) => profile.get({id}),
    blockUsers: (usersToBlock) => Vue.http.post('api/profile/blockUsers', usersToBlock),
    unlockUsers: (usersToUnlock) => Vue.http.post('api/profile/unlockUsers', usersToUnlock),
    deleteUsers: (usersToDelete) => Vue.http.post('api/profile/deleteUsers', usersToDelete),
    makeAdmins: (usersToAdmins) => Vue.http.post('api/profile/makeAdmins', usersToAdmins),
    unmakeAdmins: (usersToNotAdmins) => Vue.http.post('api/profile/unmakeAdmins', usersToNotAdmins),
}