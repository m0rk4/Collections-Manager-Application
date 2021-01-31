<template>
  <v-app>
    <v-app-bar app flat color="primary">

      <v-app-bar-nav-icon @click="moveToMain">
        <v-icon>mdi-home</v-icon>
      </v-app-bar-nav-icon>

      <v-toolbar-title v-if="$vuetify.breakpoint.mdAndUp">{{ $t('appTitle') }}</v-toolbar-title>

      <v-spacer></v-spacer>

      <v-text-field
          dense
          hide-details
          light
          class="mx-2"
          :placeholder="$vuetify.breakpoint.mdAndUp ? $t('searchPlaceholder') + '...' : ''"
          single-line
          solo
          flat
          v-model="query"
          @click:prepend-inner="searchPage"
          @keyup.enter="searchPage"
          prepend-inner-icon="mdi-magnify"
      ></v-text-field>

      <v-divider vertical inset></v-divider>

      <template v-if="!profile">
        <login-dialog></login-dialog>
        <register-dialog></register-dialog>
      </template>

      <template v-else>
        <v-btn-toggle
            class="ml-2"
            rounded>
          <v-btn
              large
              text
              @click="$router.push('/user')"
              :disabled="$router.currentRoute.path === '/user'"
          >
            <v-avatar size="36" :class="$vuetify.breakpoint.smAndUp ? 'mr-1' : ''">
              <img :src="profile.userpic">
            </v-avatar>
            <template v-if="$vuetify.breakpoint.smAndUp">
              {{ profile.name }}
            </template>
          </v-btn>
          <v-btn
              large
              text
              v-if="isAdmin"
              @click="$router.push('/admin')"
          >{{ $t('admin') }}
          </v-btn>
          <v-btn large href="/logout" icon>
            <v-icon>mdi-export</v-icon>
          </v-btn>
        </v-btn-toggle>
      </template>

    </v-app-bar>

    <v-main class="my-4">
      <v-container>
        <v-alert class="ml-auto mr-4"
                 dismissible
                 dense
                 :type="typeAlert"
                 :value="visibilityAlert"
                 transition="scale-transition"
        >
          {{ messageAlert }}
        </v-alert>
        <router-view></router-view>
      </v-container>
    </v-main>

    <v-footer
        color="primary"
        padless
    >
      <v-row class="justify-center">
        <v-col class="align-center">
          <v-card
              color="primary"
              flat
              tile
              class="text-center"
          >
            <v-card-text>
              <v-btn text rounded @click="$vuetify.theme.dark = !$vuetify.theme.dark">
                <v-icon
                    large
                >
                  mdi-theme-light-dark
                </v-icon>
              </v-btn>
              <v-btn
                  fab
                  text
                  @click="changeLocale('ru')"
              >RU
              </v-btn>
              <v-btn
                  fab
                  text
                  @click="changeLocale('en')"
              >EN
              </v-btn>
            </v-card-text>

            <v-card-text class="pt-0 text-center">
              {{$t('appDescription')}}
            </v-card-text>

            <v-divider></v-divider>

            <v-card-text>
              {{ new Date().getFullYear() }} — <strong>{{ $t('appTitle') }}</strong>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </v-footer>
  </v-app>
</template>

<script>
import LoginDialog from "components/auth/LoginDialog.vue";
import RegisterDialog from "components/auth/RegisterDialog.vue";
import {mapState, mapGetters, mapMutations} from 'vuex'
import {addHandler} from "util/ws";

export default {
  components: {LoginDialog, RegisterDialog},
  data() {
    return {
      query: '',
    }
  },
  computed: {
    ...mapState({
      messageAlert: state => state.alert.message,
      typeAlert: state => state.alert.type,
      visibilityAlert: state => state.alert.isVisible,
      profile: state => state.auth.profile,
    }),
    ...mapGetters('auth', [
      'isAdmin',
    ])
  },
  methods: {
    ...mapMutations('item', ['addCommentMutation', 'updateItemMutation']),
    searchPage() {
      if (this.$route.path === 'search') {
        this.$router.push('/')
      }
      this.$router.push({path: '/search', query: {query: this.query}})
    },
    changeLocale(locale) {
      this.$i18n.locale = locale
    },
    moveToMain() {
      if (this.$route.path !== '/') {
        this.$router.push('/')
      }
    }
  },
  watch: {
    '$vuetify.theme.dark': function (newVal) {
      localStorage.setItem('isDarkTheme', newVal)
    },
    '$i18n.locale': function (newVal) {
      localStorage.setItem('appLocale', newVal)
    }
  },
  created() {
    this.$vuetify.theme.dark = localStorage.getItem('isDarkTheme') ?
        localStorage.getItem('isDarkTheme') !== 'false' : false
    this.$i18n.locale = localStorage.getItem('appLocale') ?
        localStorage.getItem('appLocale') : 'en'
    addHandler(data => {
          if (data.objectType === 'COMMENT') {
            switch (data.eventType) {
              case 'CREATE':
                this.addCommentMutation(data.body)
                break
              default:
                console.error(`Incorrect evType: ${data.eventType}`)
            }
          } else if (data.objectType === 'LIKE') {
            switch (data.eventType) {
              case 'CREATE':
                this.updateItemMutation(data.body)
                break
              case 'REMOVE':
                this.updateItemMutation(data.body)
                break
              default:
                console.error(`Incorrect evType: ${data.eventType}`)
            }
          } else {
            console.error(`Incorrect objType: ${data.objectType}`)
          }
        }
    )
  },
}
</script>

<style scoped>

</style>