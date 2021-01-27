import collectionApi from "api/collectionApi"

export default {
    namespaced: true,
    state: () => ({
        userCollections: null,
    }),
    getters: {},
    mutations: {
        addAllUserCollectionsMutation(state, allCollections) {
            state.userCollections = [
                ...allCollections
            ]
        },
        addCollectionMutation(state, newCollection) {
            state.userCollections = [
                ...state.userCollections,
                newCollection
            ]
        },
        deleteCollectionMutation(state, collection) {
            const indexToDelete = state.userCollections
                .findIndex(el => el.id === collection.id)

            if (indexToDelete > -1) {
                state.userCollections = [
                    ...state.userCollections.slice(0, indexToDelete),
                    ...state.userCollections.slice(indexToDelete + 1)
                ]
            }
        },
        updateCollectionMutation(state, updatedCollection) {
            const indexToReplace = state.userCollections
                .findIndex(el => el.id === updatedCollection.id)

            if (indexToReplace > -1) {
                state.userCollections = [
                    ...state.userCollections.slice(0, indexToReplace),
                    updatedCollection,
                    ...state.userCollections.slice(indexToReplace + 1)
                ]
            }
        }
    },
    actions: {
        getAllUserCollectionsAction({commit}, userId) {
            collectionApi.getAllUserCollections(userId).then(res => {
                res.json().then(allCollections => {
                    console.log(allCollections)
                    commit('addAllUserCollectionsMutation', allCollections)
                })
            })
        },
        addCollectionAction({commit}, rawCollection) {
            collectionApi.addNewCollection(rawCollection).then(res => {
                res.json().then(responseCollection => {
                    commit('addCollectionMutation', responseCollection)
                })
            })
        },
        deleteCollectionAction({commit}, collection) {
            collectionApi.deleteCollection(collection.id).then(res => {
                if (res.ok) {
                    commit('deleteCollectionMutation', collection)
                }
            })
        },
        updateCollectionAction({commit}, collection) {
            collectionApi.updateCollection(collection).then(res => {
                res.json().then(responseCollection => {
                    commit('updateCollectionMutation', responseCollection)
                })
            })
        },
    }
}