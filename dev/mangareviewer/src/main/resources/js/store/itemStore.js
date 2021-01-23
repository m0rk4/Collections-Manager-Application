import itemApi from "api/itemApi"
import {extractNewTags} from "util/util";

export default {
    namespaced: true,
    state: () => ({
        collectionItems: []
    }),
    getters: {},
    mutations: {
        addNewItemMutation(state, savedItem) {
            state.collectionItems = [
                ...state.collectionItems,
                savedItem
            ]
        },
        setCollectionItemsMutation(state, newItems) {
            state.collectionItems = [
                ...newItems
            ]
            console.log(state.collectionItems)
        }
    },
    actions: {
        addNewItemAction({commit, state}, itemToAdd) {
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
        }
    }
}