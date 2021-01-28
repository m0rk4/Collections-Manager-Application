<template>
  <v-card>
    <v-toolbar flat>
      <v-toolbar-title>
        {{ toUpdate ? 'Update Collection' : 'New Collection' }}
      </v-toolbar-title>
    </v-toolbar>
    <v-divider></v-divider>
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
              label="Title"
              clearable
              :rules="titleRules"
              required
          ></v-text-field>

          <v-textarea
              clearable
              v-model="description"
              auto-grow
              outlined
              label="Description (MarkDown supported)"
              rows="1"
              :rules="descRules"
              required
          ></v-textarea>

          <v-combobox
              v-model="theme"
              :items="allThemes"
              label="Theme"
              outlined
              persistent-hint
              :rules="[v => !!v || 'Choose one theme']"
          ></v-combobox>

          <v-btn @click="picWidget.open()" color="primary">Upload Picture</v-btn>
          <div>Status: <i>{{ fileStatus }}</i></div>

        </v-form>
      </v-container>
    </v-card-text>

    <v-divider></v-divider>
    <v-toolbar flat>
      <v-toolbar-title>
        Fields
      </v-toolbar-title>
    </v-toolbar>
    <v-divider></v-divider>

    <v-card-text>
      <v-container>
        <v-combobox
            v-model="selectedFields"
            :items="allFields"
            :search-input.sync="search"
            hide-selected
            label="Select Fields"
            multiple
            persistent-hint
            small-chips
            :rules="[v => v.length > 0 || 'Choose or Add at least one field']"
        >
        </v-combobox>
        <v-card class="pa-4">
          <div class="primary text-center"><span class="white--text">Create your field</span></div>
          <v-form lazy-validation v-model="valid" ref="fieldForm">
            <v-text-field
                label="Field Name"
                v-model="newField"
                :rules="fieldRules"
            ></v-text-field>
          </v-form>
          <v-checkbox v-model="isMarkDownSupported" label="MarkDown"></v-checkbox>
          <v-btn @click="addField">add</v-btn>
        </v-card>
      </v-container>

      <v-btn
          x-large
          color="success"
          @click="submitCollection"
      >
        {{ toUpdate ? 'Update' : 'Submit' }}
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
        v => !!v || 'Title is required',
        v => (v && v.length <= 50) || 'Title must be less than 50 characters',
      ],
      descRules: [
        v => !!v || 'Description is required',
        v => (v && v.length <= 1000) || 'Up to 1000 characters',
      ],
      fieldRules: [
        v => !!v || 'Field name is required',
        v => (v && v.length <= 15) || 'Field name must be less than 15 characters',
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
      fileStatus: 'No file',
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