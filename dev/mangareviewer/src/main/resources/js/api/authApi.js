import Vue from 'vue'
import VueResource from 'vue-resource'

export default {
    register: (email, login, pass) =>
        Vue.http.post('/signup', {name: login, password: pass, email: email}),
    login: (username, pass) =>
        Vue.http.post('/signin', {username: username, password: pass}),
}