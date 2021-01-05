export default {
    namespaced: true,
    state: () => ({
        profile: profile
    }),
    getters: {
        isAdmin: state => state.profile.roles.includes('ADMIN'),
    }
}