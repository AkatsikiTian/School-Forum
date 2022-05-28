<template>
  <mavon-editor
    class="me-editor"
    ref="md"
    v-model="editor.value"
    v-bind="editor">
  </mavon-editor>
</template>


<script>

  import {mavonEditor} from 'mavon-editor'
  import 'mavon-editor/dist/css/index.css'


  export default {
    name: 'MarkdownEditor',
    props: {
      editor: Object
    },
    data() {
      return {}
    },
    mounted() {
      this.$set(this.editor, 'ref', this.$refs.md)
    },
    methods: {
    },
    components: {
      mavonEditor
    }
  }
</script>
<style scoped>

  .me-editor {
    z-index: 6 !important;
  }

  .v-note-wrapper.fullscreen {
    top: 60px !important
  }
</style>
