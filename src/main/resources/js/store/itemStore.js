import itemApi from "api/itemApi"
import commentApi from "api/commentApi"
import {extractNewTags, getIndex, getUpdatedCollection, deleteItemFromCollection, addComment} from "util/util"

export default {
    namespaced: true,
    state: () => ({
        collectionItems: [],
        items: [],
        searchItems: [],
        itemToModify: null,
        currentPage: -1,
        totalPages: 3,
    }),
    getters: {
        sortedItems: state =>
            state.items.sort((a, b) => -(Date.parse(a.creationTime) - Date.parse(b.creationTime)))
    },
    mutations: {
        setModifyItemMutation(state, item) {
            state.itemToModify = item
        },
        addNewItemMutation(state, savedItem) {
            state.collectionItems = [
                savedItem,
                ...state.collectionItems,
            ]
            state.items = [
                savedItem,
                ...state.items
            ]
        },
        setCollectionItemsMutation(state, newItems) {
            state.collectionItems = newItems
        },
        updateItemMutation(state, updatedItem) {
            const index = getIndex(state.collectionItems, updatedItem)
            const globalIndex = getIndex(state.items, updatedItem)
            const searchIndex = getIndex(state.searchItems, updatedItem)

            state.collectionItems = getUpdatedCollection(state.collectionItems, updatedItem, index)
            state.items = getUpdatedCollection(state.items, updatedItem, globalIndex)
            state.searchItems = getUpdatedCollection(state.searchItems, updatedItem, searchIndex)
        },
        deleteItemMutation(state, deletedItem) {
            const index = getIndex(state.collectionItems, deletedItem)
            const globalIndex = getIndex(state.items, deletedItem)
            const searchIndex = getIndex(state.searchItems, deletedItem)

            state.collectionItems = deleteItemFromCollection(state.collectionItems, index)
            state.items = deleteItemFromCollection(state.items, globalIndex)
            state.searchItems = deleteItemFromCollection(state.searchItems, searchIndex)
        },
        addCommentMutation(state, comment) {
            state.collectionItems = addComment(state.collectionItems, comment)
            state.items = addComment(state.items, comment)
            state.searchItems = addComment(state.searchItems, comment)
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
        },
        setSearchItemsMutation(state, items) {
            state.searchItems = items
        }
    },
    actions: {
        addNewItemAction({commit}, itemToAdd) {
            itemApi.addNewItem(itemToAdd).then(res => {
                res.json().then(savedItem => {
                    commit('addNewItemMutation', savedItem)
                    commit(
                        'tag/addTagsMutation',
                        extractNewTags(savedItem.tags, itemToAdd.tags),
                        {root: true}
                    )
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
                  //  commit('addCommentMutation', comment)
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
        },
        searchItemsAction({commit}, bundle) {
            itemApi.findItem(bundle.query, bundle.tag).then(res => {
                res.json().then(items => {
                    commit('setSearchItemsMutation', items)
                })
            })
        }
    }
}