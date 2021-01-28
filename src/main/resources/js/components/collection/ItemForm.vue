<template>
  <v-card>
    <v-card-text>
      <v-container fluid class="pa-2">
        <v-form
            ref="collectionForm"
            v-model="valid"
            lazy-validation
            :disabled="!(profile && ((author && (author.id === profile.id)) || isAdmin))"
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
        </v-form>
      </v-container>
    </v-card-text>
    <v-card-actions v-if="profile && ((author && (author.id === profile.id)) || isAdmin)">
      <v-btn @click="addItem" rounded outlined>Submit</v-btn>
      <v-btn @click="cancelItem" v-if="id" rounded outlined>Cancel</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import {mapGetters, mapState} from "vuex";

export default {
  props: ['currentCollection', 'itemAttr'],
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
      currCollection: {},
      values: [],
      id: null,
      options: {
        duration: 700,
        offset: 75,
        easing: 'easeInOutCubic',
      },
    }
  },

  computed: {
    ...mapGetters('tag', ['allTagsAsChips']),
    ...mapGetters('auth', ['isAdmin']),
    ...mapState({
      profile: state => state.auth.profile,
    }),
    author() {
      return this.currCollection ? this.currCollection.user : {}
    }
  },
  watch: {
    itemAttr: function (newVal) {
      this.id = newVal.id
      this.selectedTags = newVal.tags
      this.title = newVal.title
      this.values = newVal.values
      this.$vuetify.goTo(this.$refs.collectionForm, this.options)
    },
    currentCollection: function (newVal) {
      this.currCollection = newVal
      this.values = newVal.fields.map(f => {
        return {id: f.id, value: ''}
      })
    },
  },
  methods: {
    addItem() {
      if (!this.$refs.collectionForm.validate())
        return

      let tags = [], values = []
      this.selectedTags.forEach(t => {
        if (t.value) tags.push({id: t.value, name: t.text})
        else tags.push({id: null, name: t})
      })

      this.values.forEach(v => {
        var fId = this.currCollection.fields.findIndex(f => f.id === v.id)
        values.push({value: v.value, field: this.currCollection.fields[fId]})
      })

      const item = {
        id: this.id,
        title: this.title,
        tags: tags,
        collection: {id: this.currCollection.id},
        values: values
      }

      if (this.id)
        this.$store.dispatch('item/updateItemAction', item)
      else
        this.$store.dispatch('item/addNewItemAction', item)
      this.id = null
      this.$refs.collectionForm.reset()
    },
    cancelItem() {
      this.id = null
      this.$refs.collectionForm.reset()
    }
  }
}
</script>

<style scoped>

</style>