import Vue from 'vue'

const collection = Vue.resource('/api/collection{/id}')
const theme = Vue.resource('/api/collection/theme{/id}')
const field = Vue.resource('/api/collection/field{/id}')
const tag = Vue.resource('/api/collection/tag{/id}')

export default {
    getAllTags: () => tag.get(),
    getAllThemes: () => theme.get(),
    getAllFields: ()  => field.get(),
    getAllUserCollections: (id) => Vue.http.get(`api/collection/user/${id}`),
    getCollection: id => collection.get({id}),
    addNewCollection: newCollection => collection.save({}, newCollection),
    deleteCollection: id => collection.remove({id}),
    updateCollection: existingCollection => collection.update({id: existingCollection.id}, existingCollection)
}

