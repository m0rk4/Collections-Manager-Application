import Vue from 'vue'
import VueRouter from 'vue-router'
import MainPage from "pages/MainPage.vue"
import UserPage from "pages/UserPage.vue";
import RegisterPage from "pages/auth/RegisterPage.vue";
import LoginPage from "pages/auth/LoginPage.vue";
import NotFoundPage from "pages/NotFoundPage.vue";
import AdminPage from "pages/AdminPage.vue";
import CreateCollectionPage from "pages/CreateCollectionPage.vue";

Vue.use(VueRouter)

const routes = [
    {path: '/', component: MainPage},
    {path: '/login', component: LoginPage},
    {path: '/register', component: RegisterPage},
    {path: '/user/:id?', component: UserPage},
    {path: '/admin', component: AdminPage},
    {path: '/create-collection', component: CreateCollectionPage},
    {path: '/*', component: NotFoundPage}
]

export default new VueRouter({
    mode: 'history',
    routes
})