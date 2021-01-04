export default {
    namespaced: true,
    state: () => ({
        type: 'success',
        message: 'Default message',
        isVisible: false,
    }),
    getters: {},
    mutations: {
        updateMessageMutation(state, message) {
            state.message = message
        },
        updateTypeMutation(state, type) {
            state.type = type
        },
    },
    actions: {
        activateAlertAction({commit,  state }, alert) {
            commit('updateMessageMutation', alert.message)
            commit('updateTypeMutation', alert.type)
            state.isVisible = true;
            setTimeout(() => {
               state.isVisible  = false
            }, 3000)
        }
    }
}