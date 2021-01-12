<template>
  <v-row class="justify-center mt-8">
    <v-col class="align-center">
      <v-card>
        <v-toolbar flat>
          <v-toolbar-title>
            New Collection
          </v-toolbar-title>
        </v-toolbar>
        <v-divider></v-divider>
        <v-card-text>
          <v-container>
            <v-form>

              <v-text-field
                  v-model="title"
                  outlined
                  label="Title"
                  clearable
              ></v-text-field>

              <v-textarea
                  clearable
                  v-model="description"
                  auto-grow
                  outlined
                  label="Description (MarkDown supported)"
                  rows="1"
              ></v-textarea>

              <v-combobox
                  v-model="theme"
                  :items="allThemes"
                  label="Theme"
                  outlined
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
            >
            </v-combobox>
            <v-card class="pa-4">
              <div class="primary text-center"><span class="white--text">Create your field</span></div>
              <v-text-field label="Field Name" v-model="newField">
              </v-text-field>
              <v-checkbox v-model="markDownSupported" label="MarkDown"></v-checkbox>
              <v-btn @click="addField">add</v-btn>
            </v-card>
          </v-container>

          <v-btn
              x-large
              color="success"
              @click="submitCollection"
          >
            Submit
          </v-btn>
        </v-card-text>

      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import collectionApi from "api/collectionApi"

export default {
  data() {
    return {
      markDownSupported: false,
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
      selectedFields: []
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
      if (this.newField) {
        this.selectedFields.push({text: this.newField, markDownSupported: this.markDownSupported})
        this.markDownSupported = false
        this.newField = ''
      }
    },
    submitCollection() {
      const collection = {
        title: this.title,
        description: this.description,
        pic: this.fileUrl,
        theme: this.theme,
        fields: this.selectedFields
      }
      this.$store.dispatch('collection/addCollectionAction', collection)
    }
  }
}
</script>

<style scoped>

</style>