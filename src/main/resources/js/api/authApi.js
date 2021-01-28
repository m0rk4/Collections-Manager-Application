import Vue from 'vue'

export default {
    register: (email, login, pass) =>
        Vue.http.post('/signup', {name: login, password: pass, email: email}),
    login: (username, pass) =>
        Vue.http.post('/signin', {username: username, password: pass}),
}