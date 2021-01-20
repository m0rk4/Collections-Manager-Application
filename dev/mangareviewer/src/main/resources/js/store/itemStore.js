import itemApi from "api/itemApi"

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
            console.log('here')
            state.collectionItems = [
                ...newItems
            ]
        }
    },
    actions: {
        addNewItemAction({commit}, itemToAdd) {
            itemApi.addNewItem(itemToAdd).then(res => {
                res.json().then(savedItem => {
                    commit('addNewItemMutation', savedItem)
                })
            })
        }
    }
}