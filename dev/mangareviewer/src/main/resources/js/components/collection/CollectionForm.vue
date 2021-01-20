<template>
  <v-card>
    <v-card-text>
      <v-container fluid class="pa-2">
        <v-form>
          <v-text-field
              v-model="title"
              label="Title"
              clearable
          ></v-text-field>
          <v-combobox
              v-model="selectedTags"
              :items="allTags"
              label="Tags"
              multiple
              deletable-chips
              chips
          ></v-combobox>
          <v-list>
            <v-list-item>
              <v-list-item-title class="headline">
                Properties
              </v-list-item-title>
            </v-list-item>
            <v-list-item v-for="field in currCollection.fields">
              <v-list-item-content>
                <v-text-field
                    v-if="!field.isMarkDownSupported"
                    v-model="values[values.findIndex(val => val.id === field.id)].value"
                    :label="field.text"
                    :key="String(field.id)"
                ></v-text-field>
                <v-textarea
                    v-else
                    auto-grow
                    clearable
                    rows="1"
                    v-model="values[values.findIndex(val => val.id === field.id)].value"
                    :label="field.text + ' (Markdown)'"
                    :key="String(field.id)"
                ></v-textarea>
              </v-list-item-content>
            </v-list-item>
          </v-list>
          <v-btn @click="addItem" rounded outlined>Submit</v-btn>
        </v-form>
      </v-container>
    </v-card-text>
  </v-card>
</template>

<script>

export default {
  props: ['currentCollection', 'allTagsList', 'valuesList'],
  data() {
    return {
      title: '',
      selectedTags: [],
      allTags: this.allTagsList,
      currCollection: this.currentCollection,
      values: this.valuesList
    }
  },
  watch: {
    currentCollection: function (newVal) {
      this.currCollection = newVal
    },
    allTagsList: function (newVal) {
      this.allTags = newVal
    },
    valuesList: function (newVal) {
      this.values = newVal
    }
  },
  methods: {
    addItem() {
      var tags = []
      this.selectedTags.forEach(t => {
        if (t.value) tags.push({id: t.value, name: t.text})
        else tags.push({id: null, name: t})
      })
      console.log(tags)

      var values = []
      this.values.forEach(v => {
        var fId = this.currCollection.fields.findIndex(f => f.id === v.id)
        values.push({value: v.value, field: this.currCollection.fields[fId]})
      })
      console.log(values)

      const item = {
        title: this.title,
        tags: tags,
        collection: {id: this.currCollection.id},
        values: values
      }
      console.log(item)

      this.$store.dispatch('item/addNewItemAction', item)
    }
  }
}
</script>

<style scoped>

</style>