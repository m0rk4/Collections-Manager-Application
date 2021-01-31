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
              :label="$t('title')"
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
              :label="$t('tags')"
              :rules="[v => v.length > 0 || $t('tagIsReq')]"
          ></v-combobox>
          <v-list>
            <v-list-item>
              <v-list-item-title class="headline">
                {{ $t('props') }}
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
      <v-btn @click="addItem"
             class="success lighten-1"
             rounded
             x-large>{{ $t('submit') }}
      </v-btn>
      <v-btn @click="cancelItem"
             class="error lighten-1"
             v-if="id"
             x-large
             rounded>{{ $t('cancel') }}
      </v-btn>
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
        v => !!v || this.$t('titIsReq'),
        v => (v && v.length <= 50) || this.$t('titleLess50'),
      ],
      textMDRules: [
        v => !!v || this.$t('required'),
        v => (v && v.length <= 255) || this.$t('lenLess') + ' 255 ' + this.$t('characters')
      ],
      textRules: [
        v => !!v || this.$t('required'),
        v => (v && v.length <= 50) || this.$t('lenLess') + ' 50 ' + this.$t('characters')
      ],
      valid: true,
      title: '',
      selectedTags: [],
      currCollection: {},
      values: [],
      id: null,
      options: {
        duration: 1000,
        offset: 35,
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
    currentCollection: function (newVal) {
      this.currCollection = newVal
      this.values = newVal.fields.map(f => {
        return {id: f.id, value: ''}
      })
    },
    itemAttr: function (newVal) {
      this.id = newVal.id
      this.selectedTags = newVal.tags
      this.title = newVal.title
      this.values = newVal.values
      this.$vuetify.goTo(this.$refs.collectionForm, this.options)
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