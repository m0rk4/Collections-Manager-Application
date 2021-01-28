import collectionApi from "api/collectionApi"

export default {
    namespaced: true,
    state: () => ({
        allTags: []
    }),
    getters: {
        allTagsAsChips(state) {
            var chips = []
            state.allTags.forEach(t => chips.push({text: t.name, value: t.id}))
            return chips
        }
    },
    mutations: {
        getAllTagsMutation(state, tagsFromDb) {
            state.allTags = [
                ...tagsFromDb
            ]
        },
        addTagsMutation(state, newTags) {
            state.allTags = [
                ...state.allTags,
                ...newTags
            ]
        }
    },
    actions: {
        getAllTagsAction({commit}) {
            collectionApi.getAllTags().then(res => {
                res.json().then(tagsFromDb => {
                    commit('getAllTagsMutation', tagsFromDb)
                })
            })
        }
    }
}