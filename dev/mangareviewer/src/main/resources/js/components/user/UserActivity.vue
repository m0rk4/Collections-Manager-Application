<template>
  <v-card
      class="mx-auto text-center">
    <v-toolbar flat>
      <v-toolbar-title>User activity</v-toolbar-title>
    </v-toolbar>
    <v-divider></v-divider>
    <v-card-text>
      <v-sheet color="blue">
        <v-sparkline
            :value="values"
            :labels="labels"
            color="rgba(255, 255, 255, .7)"
            height="100"
            padding="24"
            stroke-linecap="round"
        >
        </v-sparkline>
      </v-sheet>
    </v-card-text>
    <v-card-text>
      <div class="display-1 font-weight-medium">
        Last 5 Days (Items)
      </div>
    </v-card-text>
  </v-card>
</template>

<script>
import {mapState} from "vuex";

export default {
  data() {
    return {
      count: 0,
      values: [],
      labels: []
    }
  },
  watch: {
    userCollections: function (newVal) {
      let labels = [], values = [], i = 5
      this.count = 0
      let now = new Date()
      let currDate = new Date(now.getFullYear(), now.getMonth(), now.getDate())
      while (i-- > 0) {
        let count = 0
        newVal.forEach(c => {
          let strDate = c.creationDate
          let year = +strDate.substring(0, 4)
          let month = +strDate.substring(5, 7) - 1
          let day = +strDate.substring(8, 10)
          if (Date.parse(new Date(year, month, day)) === Date.parse(currDate)) {
            count++
          }
        })
        values.unshift(count)
        labels.unshift(this.formatDate(currDate) + ' (' + values[0] + ')')
        currDate.setDate(currDate.getDate() - 1)
      }
      this.values = values
      this.labels = labels
    }
  },
  methods: {
    formatDate(date) {
      var dd = date.getDate();
      if (dd < 10) dd = '0' + dd;

      var mm = date.getMonth() + 1;
      if (mm < 10) mm = '0' + mm;

      var yy = date.getFullYear() % 100;
      if (yy < 10) yy = '0' + yy;

      return dd + '.' + mm + '.' + yy;
    },
  },
  computed: {
    ...mapState({userCollections: state => state.collection.userCollections}),
  },
}
</script>

<style scoped>

</style>