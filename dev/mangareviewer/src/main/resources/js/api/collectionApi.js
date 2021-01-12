import Vue from 'vue'

const collection = Vue.resource('api/collection{/id}')
const theme = Vue.resource('api/collection/theme{/id}')
const field = Vue.resource('api/collection/field{/id}')

export default {
    getAllThemes: () => theme.get(),
    getAllFields: () => field.get(),
    addNewCollection: (newCollection) => collection.save({}, newCollection),
    getAllUserCollections: (id) => Vue.http.get(`api/collection/user/${id}`),
}

