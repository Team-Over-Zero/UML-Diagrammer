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
      <button v-on:click="addNode('TextBox_Square_Interface')">Square</button>
      <button v-on:click="addNode('Class')">Class</button>
      <button v-on:click="addNode('Note')">Note</button>
      <button v-on:click="addNode('Folder')">Folder</button>
      <button v-on:click="addNode('TextBox_Square_Interface')">Text Box</button>
      <button v-on:click="addNode('LifeLine')">Lifeline</button>
      <button v-on:click="addNode('Loop')">Loop</button>
      <button v-on:click="addNode('StickFigure')">User</button>
      <button v-on:click="addNode('Oval_UseCase')">Oval</button>
    </div>
    <canvas id="c" width="800" height="800" class="display-canvas"></canvas>

  </div>

</template>

<script src="https://unpkg.com/konva@8/konva.min.js"></script>
<script src="https://unpkg.com/vue-konva/umd/vue-konva.min.js"></script>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/fabric.js/521/fabric.min.js"></script>

<script type="module">

  let canvas = new fabric.Canvas('c');
  let nodes = [];

  function registerCanvas(){
    canvas = new fabric.Canvas('c');
    canvas.setDimensions({
      width: '100%',
      height: '100%'
    },{
      cssOnly: true
    });
  }

  function addNode(path){
    let n = {
      description: "Default Description",
      height: 30,
      width: 30,
      x_coord: 30,
      y_coord: 30,
      name: "DefaultName",
      svg_image: "",
      type: "default_nodes",
      id: -1,
    };

    nodes.push(n);

    let shape = new fabric.Path(path, {
      left: 10,
      top: 10
    });





    canvas.add(shape);

  }

  Vue.component("uml-editor", {
    template: "#uml-editor",

    data() {
      return {
        nodes: [],
      };
    },

    mounted: function() {
      registerCanvas();
    },

    methods: {

      addNode: function(file){
        fetch('/svg/' + file + '.svg')
            .then(result => result.text())
            .then((output) => {
              addNode(output)
            }).catch(err => console.error(err));
      },

      testGet: function(){
        fetch('/testGet')
            .then(result => result.json())
            .then((output) => {
              console.log('Output: ', output);

            }).catch(err => console.error(err));
      },

      loadSVG: function(){

      },


    },

  });



</script>






<style>
.uml-editor {

}

.display-canvas{
  display: block;
}
</style>