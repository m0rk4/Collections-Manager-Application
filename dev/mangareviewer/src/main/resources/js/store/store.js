import Vuex from 'vuex'
import Vue from 'vue'
import alertModule from "store/alertModule";

Vue.use(Vuex)

export default new Vuex.Store({
    modules: {
        alert: alertModule,
    }
})

