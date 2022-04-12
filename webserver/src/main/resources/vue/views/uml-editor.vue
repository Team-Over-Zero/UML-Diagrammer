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
      <button v-on:click="addNode">Square</button>
      <button v-on:click="testGet">Class</button>
      <button>Note</button>
      <button>Folder</button>
      <button>Text Box</button>
      <button>Lifeline</button>
      <button>Loop</button>
      <button>User</button>
      <button>Oval</button>
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

  function addNode(){
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

    canvas.add(n.image);

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

      addNode: function(){
        addNode();
      },

      testGet: function(){
        fetch("/api/testGet")
            .then(res => res.json())
            .then(res => alert(res.id))
            .catch(() => alert("Error while fetching users"));
      }


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