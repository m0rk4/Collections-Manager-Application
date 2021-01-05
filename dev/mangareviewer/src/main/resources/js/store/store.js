import Vuex from 'vuex'
import Vue from 'vue'
import alertStore from "store/alertStore"
import authStore from "store/authStore"

Vue.use(Vuex)

export default new Vuex.Store({
    modules: {
        alert: alertStore,
        auth: authStore,
    }
})

