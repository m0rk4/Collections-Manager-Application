import collectionApi from "api/collectionApi"
import {getIndex, getUpdatedCollection, deleteItemFromCollection} from "util/util"


export default {
    namespaced: true,
    state: () => ({
        userCollections: [],
        collections: []
    }),
    getters: {
        sortedCollections: state =>
            state.collections.sort((a, b) => -(a.items.length - b.items.length))
    },
    mutations: {
        addAllUserCollectionsMutation(state, allCollections) {
            state.userCollections = allCollections
        },
        addCollectionMutation(state, newCollection) {
            state.userCollections = [
                newCollection,
                ...state.userCollections,
            ]
            state.collections = [
                ...state.collections,
                newCollection
            ]
        },
        deleteCollectionMutation(state, collection) {
            const indexToDelete = getIndex(state.userCollections, collection)
            const globalIndexToDelete = getIndex(state.collections, collection)

            state.userCollections = deleteItemFromCollection(state.userCollections, indexToDelete)
            state.collections = deleteItemFromCollection(state.collections, globalIndexToDelete)
        },
        updateCollectionMutation(state, updatedCollection) {
            const indexToReplace = getIndex(state.userCollections, updatedCollection)

            state.userCollections = getUpdatedCollection(state.userCollections, updatedCollection, indexToReplace)
        },
        setAllCollectionsMutation(state, collections) {
            state.collections = collections
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
        getAllCollectionsAction({commit}) {
            collectionApi.getAllCollections().then(res => {
                res.json().then(collections => {
                    commit('setAllCollectionsMutation', collections)
                })
            })
        }
    }
}