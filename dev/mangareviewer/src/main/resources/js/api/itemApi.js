import Vue from 'vue'

const item = Vue.resource('/api/item{/id}')

export default {
    addNewItem: itemToAdd => item.save({}, itemToAdd),
    updateItem: itemToUpdate => item.update({id: itemToUpdate.id}, itemToUpdate),
    deleteItem: id => item.remove({id}),
    likeItem: id => Vue.http.post(`/api/item/${id}/like`)
}