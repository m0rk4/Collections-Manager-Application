import Vuex from 'vuex'
import Vue from 'vue'
import alertStore from "store/alertStore"
import authStore from "store/authStore"
import collectionStore from "store/collectionStore"
import itemStore from "store/itemStore";

Vue.use(Vuex)

export default new Vuex.Store({
    modules: {
        alert: alertStore,
        auth: authStore,
        collection: collectionStore,
        item: itemStore
    }
})

