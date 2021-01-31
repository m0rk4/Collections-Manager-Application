import Vue from 'vue'
import 'api/resource'
import vuetify from 'plugins/vuetify'
import router from 'router/router'
import store from "store/store"
import PageSkeleton from 'pages/PageSkeleton.vue'
import {connect} from "util/ws"
import {i18n} from "plugins/i18n"

if (profile) {
    connect()
}

new Vue({
    el: '#app',
    vuetify,
    router,
    store,
    i18n,
    render: a => a(PageSkeleton)
})