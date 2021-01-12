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
        }
    },
    actions: {
        getAllUserCollectionsAction({commit}, userId) {
            collectionApi.getAllUserCollections(userId).then(res => {
                res.json().then(allCollections => {
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
        }
    }
}