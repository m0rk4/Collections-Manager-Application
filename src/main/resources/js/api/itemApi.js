import Vue from 'vue'

const item = Vue.resource('/api/item{/id}')

export default {
    findItem: (query, tag) =>
        Vue.http.get('/api/item/search', {params: {query, tag}}),
    page: page =>
        Vue.http.get('/api/item', {params: {page}}),
    addNewItem: itemToAdd =>
        item.save({}, itemToAdd),
    updateItem: itemToUpdate =>
        item.update({id: itemToUpdate.id}, itemToUpdate),
    deleteItem: id =>
        item.remove({id}),
    likeItem: id =>
        Vue.http.post(`/api/item/${id}/like`)
}