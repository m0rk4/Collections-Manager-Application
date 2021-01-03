import Vue from 'vue'
import VueResource from 'vue-resource'

export default {
    register: (email, login, pass) =>
        Vue.http.post('/register', {name: login, password: pass, email: email}),
    login: (username, pass) =>
        Vue.http.post('/login', {name: username, password: pass}),
}