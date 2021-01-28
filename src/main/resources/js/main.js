import Vue from 'vue'
import 'api/resource'
import vuetify from 'plugins/vuetify'
import router from 'router/router'
import store from "store/store"
import PageSkeleton from 'pages/PageSkeleton.vue'
import {connect} from "util/ws";

if (profile) {
    connect()
}

new Vue({
    el: '#app',
    vuetify,
    router,
    store,
    render: a => a(PageSkeleton)
})