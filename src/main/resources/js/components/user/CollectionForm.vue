<template>
  <v-card>
    <v-toolbar flat class="accent lighten-1">
      <v-toolbar-title>
        {{ toUpdate ? $t('update') : $t('add') }}
      </v-toolbar-title>
    </v-toolbar>

    <v-card-text>
      <v-container>
        <v-form
            ref="addForm"
            v-model="valid"
            lazy-validation
        >
          <v-text-field
              v-model="title"
              outlined
              :label="$t('title')"
              clearable
              :rules="titleRules"
              required
          ></v-text-field>

          <v-textarea
              clearable
              v-model="description"
              auto-grow
              outlined
              :label="$t('descMD')"
              rows="1"
              :rules="descRules"
              required
          ></v-textarea>

          <v-combobox
              v-model="theme"
              :items="allThemes"
              :label="$t('theme')"
              outlined
              persistent-hint
              :rules="themeRules"
          ></v-combobox>

          <v-btn @click="picWidget.open()" rounded>{{ $t('upPic') }}</v-btn>
          <div>
            <span class="subtitle-1 font-weight-medium">{{ $t('status') }}:
              <i class="font-weight-light">
                {{ fileStatus }}</i></span>
          </div>
        </v-form>
      </v-container>
    </v-card-text>

    <v-toolbar flat class="accent lighten-1">
      <v-toolbar-title>
        {{ $t('fields') }}
      </v-toolbar-title>
    </v-toolbar>

    <v-card-text>
      <v-container>
        <v-combobox
            v-model="selectedFields"
            :items="allFields"
            :search-input.sync="search"
            hide-selected
            :label="$t('selFields')"
            multiple
            deletable-chips
            persistent-hint
            small-chips
            :rules="[v => v.length > 0 || $t('chOrAdd1F')]"
        >
        </v-combobox>
        <v-card class="pa-4">
          <v-card-title class="accent lighten-2 text-h6 font-weight-regular">
            {{$t('creatUrField')}}
          </v-card-title>
          <v-form
              lazy-validation
              v-model="valid"
              ref="fieldForm">
            <v-text-field
                :label="$t('fieldName')"
                v-model="newField"
                :rules="fieldRules"
            ></v-text-field>
            <v-checkbox
                v-model="isMarkDownSupported"
                label="MarkDown"></v-checkbox>
          </v-form>
          <v-btn rounded @click="addField">{{$t('addField')}}</v-btn>
        </v-card>
      </v-container>

      <v-btn
          x-large
          rounded
          color="success"
          @click="submitCollection"
      >
        {{ toUpdate ? $t('update') : $t('submit') }}
      </v-btn>
    </v-card-text>

  </v-card>
</template>

<script>
import collectionApi from "api/collectionApi"

export default {
  props: ['toUpdate', 'authorId', 'onClose'],
  data() {
    return {
      titleRules: [
        v => !!v || this.$t('titIsReq'),
        v => (v && v.length <= 50) || this.$t('titleLess50'),
      ],
      descRules: [
        v => !!v || this.$t('description') + ' ' + this.$t('isRequired'),
        v => (v && v.length <= 1000) || this.$t('upTo1000'),
      ],
      fieldRules: [
        v => !!v || this.$t('fNIsReq'),
        v => (v && v.length <= 15) || this.$t('f15'),
      ],
      themeRules: [
        v => !!v || this.$t('ch1Th'),
        v => this.allThemes.findIndex(t => t.id === v.id) !== -1 || this.$t('chTFrList')
      ],
      valid: true,
      isMarkDownSupported: false,
      allFields: [],
      newField: '',
      search: null,
      title: '',
      description: '',
      theme: null,
      picWidget: '',
      fileUrl: null,
      fileStatus: this.$t('fileStatusInit'),
      allThemes: [],
      selectedFields: [],
      existingCollection: null,
    }
  },
  created() {
    this.picWidget = cloudinary.createUploadWidget(
        {
          cloud_name: 'dr7gxyl6z',
          upload_preset: 'ymdxp0dl',
          sources: ['local', 'url'],
          multiple: false
        },
        (error, result) => {
          if (result.event === 'success') {
            this.updateImageStatus(result.info.secure_url, 'OK')
          }
        }
    );
  },
  beforeMount() {
    if (this.toUpdate) {
      collectionApi.getCollection(this.toUpdate).then(res => {
        res.json().then(collection => {
          this.existingCollection = collection
          this.title = collection.title
          this.description = collection.description
          this.theme = collection.theme
          if (collection.pic) {
            this.fileUrl = collection.pic
            this.fileStatus = 'OK'
          }
          this.selectedFields = collection.fields
        })
      })

    }
    collectionApi.getAllThemes().then(res => {
      res.json().then(data => {
        data.forEach(item => {
          console.log(item)
          this.allThemes.push(item)
        })
      })
    })
    collectionApi.getAllFields().then(res => {
      res.json().then(data => {
        data.forEach(item => {
          this.allFields.push(item)
        })
      })
    })
  },
  methods: {
    updateImageStatus(url, status) {
      this.fileUrl = url
      this.fileStatus = status
    },
    addField() {
      if (!this.$refs.fieldForm.validate())
        return
      this.selectedFields.push({text: this.newField, isMarkDownSupported: this.isMarkDownSupported})
      this.$refs.fieldForm.reset()
    },
    submitCollection() {
      if (!this.$refs.addForm.validate())
        return
      const collection = {
        id: this.existingCollection ? this.existingCollection.id : null,
        title: this.title,
        user: {id: this.authorId},
        description: this.description,
        pic: this.fileUrl,
        theme: this.theme,
        fields: this.selectedFields
      }
      if (this.toUpdate)
        this.$store.dispatch('collection/updateCollectionAction', collection)
      else
        this.$store.dispatch('collection/addCollectionAction', collection)
      this.$refs.addForm.reset()
      this.$refs.fieldForm.reset()
      this.onClose()
    }
  }
}
</script>

<style scoped>

</style>