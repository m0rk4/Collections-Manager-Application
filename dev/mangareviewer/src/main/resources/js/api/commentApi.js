import Vue from 'vue'

const comment = Vue.resource('/api/comment{/id}')

export default {
    addNewComment: commentToAdd => comment.save({}, commentToAdd),
}