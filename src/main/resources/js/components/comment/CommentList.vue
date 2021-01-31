<template>
  <v-list two-line>
    <v-list-group
        :value="false"
    >
      <template v-slot:activator>
        <v-list-item-title>{{ $t('comments') }}</v-list-item-title>
      </template>

      <template
          v-for="(item, index) in comments"
      >
        <v-divider
            v-if="index > 0"
            :key="index"
        ></v-divider>

        <comment-item
            :comment="item"
            :key="'item' + index"
        ></comment-item>
      </template>

      <comment-form
          :itemId="itemId"
      ></comment-form>
    </v-list-group>
  </v-list>
</template>

<script>
import CommentItem from "components/comment/CommentItem.vue";
import CommentForm from "components/comment/CommentForm.vue";

export default {
  components: {CommentForm, CommentItem},
  props: ['comments', 'itemId']
}
</script>

<style scoped>

</style>