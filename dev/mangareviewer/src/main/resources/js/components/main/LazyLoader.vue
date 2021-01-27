<template>
  <span></span>
</template>

<script>
export default {
  mounted() {
    window.onscroll = () => {
      const el = document.documentElement
      const isBottomOfScreen = el.scrollTop + window.innerHeight > el.offsetHeight - 100

      if (isBottomOfScreen) {
        this.$store.dispatch('item/loadPageAction')
      }
    }
  },
  beforeDestroy() {
    window.onscroll = null
  }
}
</script>

<style scoped>

</style>