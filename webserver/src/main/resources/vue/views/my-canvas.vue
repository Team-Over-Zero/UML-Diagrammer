<template id='my-canvas'>
  <div class="my-canvas-wrapper">
    <canvas ref="my-canvas">

      <uml-node
          :x="100"
          :y="50"
      >
      </uml-node>

    </canvas>
    <slot></slot>
  </div>
</template>

<script>
Vue.component('my-canvas',
    {template: '#my-canvas',

      name: "MyCanvas",
      data(){
        return{
          provider: {
            context: null,
          },
        };
      },

      provide(){
        return{
          provider: this.provider,
        };
      },

      mounted(){
        this.provider.context = this.$refs['my-canvas'].getContext('2d');

        this.$refs['my-canvas'].width = this.$refs['my-canvas'].parentElement.clientWidth;
        this.$refs['my-canvas'].height = this.$refs['my-canvas'].parentElement.clientHeight;
      },
    });
</script>

<style>

</style>