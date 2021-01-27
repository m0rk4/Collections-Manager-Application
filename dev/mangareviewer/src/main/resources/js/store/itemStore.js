import itemApi from "api/itemApi"
import commentApi from "api/commentApi"
import {extractNewTags} from "util/util"

export default {
    namespaced: true,
    state: () => ({
        collectionItems: [],
        items: [],
        currentPage: -1,
        totalPages: 3,
    }),
    getters: {
        sortedItems: state =>
            state.items.sort((a, b) => -(Date.parse(a.creationTime) - Date.parse(b.creationTime)))
    },
    mutations: {
        addNewItemMutation(state, savedItem) {
            state.collectionItems = [
                savedItem,
                ...state.collectionItems,
            ]
        },
        setCollectionItemsMutation(state, newItems) {
            state.collectionItems = [
                ...newItems
            ]
        },
        updateItemMutation(state, updatedItem) {
            const index = state.collectionItems
                .findIndex(i => i.id === updatedItem.id)

            if (index > -1) {
                state.collectionItems = [
                    ...state.collectionItems.slice(0, index),
                    updatedItem,
                    ...state.collectionItems.slice(index + 1)
                ]
            }
        },
        deleteItemMutation(state, deletedItem) {
            const index = state.collectionItems
                .findIndex(i => i.id === deletedItem.id)

            if (index > -1) {
                state.collectionItems = [
                    ...state.collectionItems.slice(0, index),
                    ...state.collectionItems.slice(index + 1)
                ]
            }
        },
        addCommentMutation(state, comment) {
            const indexToUpdate = state.collectionItems
                .findIndex(i => i.id === comment.item.id)
            const item = state.collectionItems[indexToUpdate]

            if (!item.comments.find(it => it.id === comment.id)) {
                state.collectionItems = [
                    ...state.collectionItems.slice(0, indexToUpdate),
                    {
                        ...item,
                        comments: [
                            ...item.comments,
                            comment
                        ]
                    },
                    ...state.collectionItems.slice(indexToUpdate + 1)
                ]
            }
        },
        addItemPageMutation(state, items) {
            const targetItems = state.items
                .concat(items)
                .reduce((res, val) => {
                    res[val.id] = val
                    return res
                }, {})

            state.items = Object.values(targetItems)
        },
        updateTotalPagesMutation(state, totalPages) {
            state.totalPages = totalPages
        },
        updateCurrentPageMutation(state, currPage) {
            state.currentPage = currPage
        }
    },
    actions: {
        addNewItemAction({commit}, itemToAdd) {
            itemApi.addNewItem(itemToAdd).then(res => {
                res.json().then(savedItem => {
                    commit('addNewItemMutation', savedItem)
                    commit('tag/addTagsMutation',
                        extractNewTags(savedItem.tags, itemToAdd.tags), {root: true})
                })
            })
        },
        updateItemAction({commit}, itemToUpdate) {
            itemApi.updateItem(itemToUpdate).then(res => {
                res.json().then(updatedItem => {
                    commit('updateItemMutation', updatedItem)
                })
            })
        },
        deleteItemAction({commit}, itemToDelete) {
            itemApi.deleteItem(itemToDelete.id).then(res => {
                if (res.ok) {
                    commit('deleteItemMutation', itemToDelete)
                }
            })
        },
        addCommentAction({commit}, comment) {
            commentApi.addNewComment(comment).then(res => {
                res.json().then(savedComment => {
                    commit('addCommentMutation', comment)
                })
            })
        },
        loadPageAction({commit, state}) {
            if (state.currentPage !== state.totalPages - 1) {
                itemApi.page(state.currentPage + 1).then(res => {
                    res.json().then(data => {
                        commit('addItemPageMutation', data.items)
                        commit('updateCurrentPageMutation', Math.min(data.currentPage, data.totalPages - 1))
                        commit('updateTotalPagesMutation', data.totalPages)
                    })
                })
            }
        }
    }
}