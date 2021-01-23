<template>
  <v-card>
    <v-card-text>
      <v-container fluid class="pa-2">
        <v-form
            ref="collectionForm"
            v-model="valid"
            lazy-validation
        >
          <v-text-field
              v-model="title"
              label="Title"
              clearable
              :rules="titleRules"
              required
          ></v-text-field>
          <v-combobox
              v-model="selectedTags"
              :items="allTagsAsChips"
              multiple
              deletable-chips
              chips
              label="Tags"
              :rules="[v => v.length > 0 || 'Tag is required']"
          ></v-combobox>
          <v-list>
            <v-list-item>
              <v-list-item-title class="headline">
                Properties
              </v-list-item-title>
            </v-list-item>
            <v-list-item
                v-for="field in currCollection.fields"
                :key="String(field.id)"
            >
              <v-list-item-content>
                <v-text-field
                    v-if="!field.isMarkDownSupported"
                    v-model="values[values.findIndex(val => val.id === field.id)].value"
                    :label="field.text"
                    :rules="textRules"
                    required
                ></v-text-field>
                <v-textarea
                    v-else
                    auto-grow
                    clearable
                    rows="1"
                    v-model="values[values.findIndex(val => val.id === field.id)].value"
                    :rules="textMDRules"
                    :label="field.text + ' (Markdown)'"
                    required
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
import {mapGetters} from "vuex";

export default {
  props: ['currentCollection', 'valuesList'],
  data() {
    return {
      titleRules: [
        v => !!v || 'Title is required',
        v => (v && v.length <= 50) || 'Title must be less than 50 characters',
      ],
      textMDRules: [
          v => !!v || 'Required',
          v => (v && v.length <= 255) || 'Length must be less than 255 characters'
      ],
      textRules: [
        v => !!v || 'Required',
        v => (v && v.length <= 50) || 'Length must be less than 50 characters'
      ],
      valid: true,
      title: '',
      selectedTags: [],
      currCollection: this.currentCollection,
      values: this.valuesList
    }
  },
  created() {
    this.$store.dispatch('tag/getAllTagsAction')
  },
  computed: {
    ...mapGetters('tag', ['allTagsAsChips'])
  },
  watch: {
    currentCollection: function (newVal) {
      this.currCollection = newVal
    },
    valuesList: function (newVal) {
      this.values = newVal
    }
  },
  methods: {
    addItem() {
      if (!this.$refs.collectionForm.validate())
        return
      let tags = []
      this.selectedTags.forEach(t => {
        if (t.value) tags.push({id: t.value, name: t.text})
        else tags.push({id: null, name: t})
      })

      let values = []
      this.values.forEach(v => {
        var fId = this.currCollection.fields.findIndex(f => f.id === v.id)
        values.push({value: v.value, field: this.currCollection.fields[fId]})
      })

      const item = {
        title: this.title,
        tags: tags,
        collection: {id: this.currCollection.id},
        values: values
      }
      console.log('New Item:')
      console.log(item)
      this.$store.dispatch('item/addNewItemAction', item)
      this.$refs.collectionForm.reset()
    }
  }
}
</script>

<style scoped>

</style>