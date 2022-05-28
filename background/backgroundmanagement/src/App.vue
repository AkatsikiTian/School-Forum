<template>
  <div id="app">
    <router-view/>
    <Gotop></Gotop>
  </div>
</template>
<script>
import Gotop from "@/components/Gotop"
export default {
  name: 'app',
  components: {
    Gotop
  }
}
</script>
