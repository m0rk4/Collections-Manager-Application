import Vue from 'vue'
import VueRouter from 'vue-router'
import MainPage from "pages/main/MainPage.vue"
import UserPage from "pages/user/UserPage.vue"
import RegisterPage from "pages/auth/RegisterPage.vue"
import LoginPage from "pages/auth/LoginPage.vue"
import NotFoundPage from "pages/NotFoundPage.vue"
import AdminPage from "pages/admin/AdminPage.vue"
import CollectionPage from "pages/collection/CollectionPage.vue"
import SearchPage from "pages/SearchPage.vue"

Vue.use(VueRouter)

const routes = [
    {path: '/', component: MainPage},
    {path: '/login', component: LoginPage},
    {path: '/register', component: RegisterPage},
    {path: '/user/:id?', component: UserPage},
    {path: '/admin', component: AdminPage},
    {path: '/collection/:id', component: CollectionPage},
    {path: '/search', component: SearchPage},
    {path: '/*', component: NotFoundPage}
]

export default new VueRouter({
    mode: 'history',
    routes
})