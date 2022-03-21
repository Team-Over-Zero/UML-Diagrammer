<template>
</template>


<script>
Vue.component('uml-node',{
  inject: ['provider'],

  props: {
    x: {
      type: Number,
      default: 0,
    },
    y: {
      type: Number,
      default: 0,
    },
    color: {
      type: String,
      default: '#f00',
    }
  },

  data() {
    return {
      oldBox: {
        x: null,
        y: null,
        w: null,
        h: null,
      }
    };
  },

  computed: {
    calculateBox() {
      const calculated = {
        x: this.x,
        y: this.y,
        w: 50,
        h: 50,
      };

      this.oldBox = calculated;

      return calculated;
    },
  },

  render(){
    if(!this.provider.context) return;

    const ctx = this.provider.context;
    const oldBox = this.oldBox;
    const newBox = this.calculateBox;

    ctx.beginPath();
    ctx.clearRect(oldBox.x, oldBox.y, oldBox.w, oldBox.h);
    ctx.rect(newBox.x, newBox.y, newBox.w, newBox.h);
    ctx.fillStyle = this.color;
    ctx.fill();
  }

  });
</script>

<style scoped>

</style>