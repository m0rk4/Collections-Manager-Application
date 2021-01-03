import Vue from 'vue'
import 'api/resource'
import vuetify from 'plugins/vuetify'
import router from 'router/router'
import PageSkeleton from 'pages/PageSkeleton.vue'

new Vue({
    el: '#app',
    vuetify,
    router,
    render: a => a(PageSkeleton)
})