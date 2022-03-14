<template id="uml-editor">
  <div class="uml-editor">
    <h1>
      UML Diagrammer
    </h1>
    <div>
      <button>Save</button>
      <button>Load</button>
      <button>Export</button>
      <select name="left-arrow" id="left-arrow">
        <option value="<--"><--</option>
        <option value="---">---</option>
      </select>
      <select name="right-arrow" id="right-arrow">
        <option value="-->">--></option>
        <option value="---">---</option>
      </select>
      <select name="line-type" id="line-type">
        <option value="-----">-----</option>
        <option value="- - -">- - -</option>
      </select>
    </div>
    <div>
      <button v-on:click='changeMode("square")'>Square</button>
      <!--<button>Class</button>
      <button>Note</button>
      <button>Folder</button>
      <button>Text Box</button>
      <button>Lifeline</button>
      <button>Loop</button>
      <button>User</button>
      <button>Oval</button>-->
    </div>
    <v-stage
        ref="stage"
        :config="configKonva"
        v-on:click="handleCanvasClick"
    >
      <v-layer ref="layer">
        <v-rect
            v-for="item in nodes"
            :config="getConfig(item)"
            @click="toggleSelect"
        ></v-rect>
        <v-text
          :config="{
            text: textToEdit,
            fontSize: 15
          }"
        ></v-text>
      </v-layer>
    </v-stage>
  </div>
</template>

<script src="https://unpkg.com/konva@8/konva.min.js"></script>
<!--2. Link VueKonva Javascript (Plugin automatically installed)-->
<script src="https://unpkg.com/vue-konva/umd/vue-konva.min.js"></script>

<script type="module">

const width = window.innerWidth * 0.5;
const height = window.innerHeight * 0.5;


  Vue.component("uml-editor", {
    template: "#uml-editor",

    data() {
      return {
        configKonva: {
          width: width,
          height: height
        },
        nodes: [],
        mode: 'select',
        textToEdit: 'test text',
      };
    },

    created: function () {
      window.addEventListener('keydown', this.onKeyPress)
    },

    beforeDestroy: function () {
      window.removeEventListener('keydown', this.onKeyPress)
    },

    methods: {
      /**
       * adds new items to the screen when clicking and correct mode selected
       */
      handleCanvasClick: function(){
        if(this.mode !== 'select') {
          let pos = this.$refs.stage.getNode().getPointerPosition();
          this.nodes.push({
            x: pos.x,
            y: pos.y,
            fill: 'black',
          });
        }
        this.mode = 'select';
      },

      /**
       * changes the mode of the program such as selecting or creating
       * @param newMode the name of the new mode
       */
      changeMode: function(newMode){
        this.mode = newMode;
      },

      getConfig: function(node){
        return {
          x: node.x,
          y: node.y,
          width: 40,
          height: 30,
          fill: node.fill,
          draggable: true,
        }
      },

      toggleSelect: function(event){
        let t = event.target;
        t.fill('red');
      },

      onKeyPress: function(event){
        let keyCode = event.keyCode;
        if(keyCode >= 65 && keyCode <= 90 || keyCode >= 48 && keyCode <= 57) {
          this.textToEdit = this.textToEdit.concat(event.key.toString());
        }else if(keyCode === 8){
          this.textToEdit = this.textToEdit.slice(0, -1);
        }
      }


    },

  });

</script>

<style>
.uml-editor {

}
</style>