import Vue from 'vue'

const item = Vue.resource('/api/item{/id}')

export default {
    addNewItem: itemToAdd => item.save({}, itemToAdd)
}