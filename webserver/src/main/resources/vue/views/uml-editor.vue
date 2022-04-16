<template id="uml-editor">
  <div class="uml-editor">
    <h1>
      UML Diagrammer
    </h1>
    <div>
      <button v-on:click="vparseJsonFromDiagram()">Save</button>
      <button v-on:click="vloadNode()">Load</button>
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
      <button v-on:click="vaddNode('Class', 'class_nodes')">Class</button>
      <button v-on:click="vaddNode('Note', 'note_nodes')">Note</button>
      <button v-on:click="vaddNode('Folder', 'folder_nodes')">Folder</button>
      <button v-on:click="vaddNode('TextBox_Square_Interface', 'text_box_nodes')">Text Box</button>
      <button v-on:click="vaddNode('LifeLine', 'life_line_nodes')">Lifeline</button>
      <button v-on:click="vaddNode('Loop', 'loop_nodes')">Loop</button>
      <button v-on:click="vaddNode('StickFigure', 'stick_figure_nodes')">User</button>
      <button v-on:click="vaddNode('Oval_UseCase', 'oval_nodes')">Oval</button>

    </div>
    <canvas id="c" width ="1200px" height="600px" class="display-canvas"></canvas>

  </div>

</template>

<script src="https://unpkg.com/konva@8/konva.min.js"></script>
<script src="https://unpkg.com/vue-konva/umd/vue-konva.min.js"></script>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/fabric.js/521/fabric.min.js"></script>

<script type="module">

  let canvas = new fabric.Canvas('c');
  let nodes = [];

  let defaultNode = {
    description: "A description",
    height: 50,
    width: 50,
    x_coord: 10,
    y_coord: 10,
    name: "name",
    svg_image: "",
    type: "",
    id: -1,
  };

  function registerCanvas(){
    canvas = new fabric.Canvas('c');
    canvas.setDimensions({
      //width: '600px',
      //height: '600px'
    },{
      cssOnly: true
    });

    canvas.on('mouse:down', function(options) {
      var groupItems;
      if (options.target) {
        var thisTarget = options.target;
        var mousePos = canvas.getPointer(options.e);
        var editTextbox = false;
        var editObject;
        if (thisTarget.isType('group')) {
          var groupPos = {
            x: thisTarget.left,
            y: thisTarget.top
          }
          thisTarget.forEachObject(function(object,i) {
            if(object.type == "textbox"){
              var matrix = thisTarget.calcTransformMatrix();
              var newPoint = fabric.util.transformPoint({y: object.top, x: object.left}, matrix);
              var objectPos = {
                xStart: newPoint.x - (object.width * object.scaleX) / 2,//When OriginX and OriginY are centered, otherwise xStart: newpoint.x - object.width * object.scaleX etc...
                xEnd: newPoint.x + (object.width * object.scaleX) / 2,
                yStart: newPoint.y - (object.height * object.scaleY) / 2,
                yEnd: newPoint.y + (object.height * object.scaleY) / 2
              }
              if ((mousePos.x >= objectPos.xStart && mousePos.x <= objectPos.xEnd) && (mousePos.y >= objectPos.yStart && mousePos.y <= objectPos.yEnd)) {
                function ungroup (group) {
                  groupItems = group._objects;
                  group._restoreObjectsState();
                  canvas.remove(group);
                  for (var i = 0; i < groupItems.length; i++) {
                    if(groupItems[i] != "textbox"){
                      groupItems[i].selectable = false;
                    }
                    canvas.add(groupItems[i]);
                  }
                  canvas.renderAll();
                }
                ungroup(thisTarget);
                canvas.setActiveObject(object);
                object.enterEditing();
                object.selectAll();
                editObject = object;
                var exitEditing = true;
                editObject.on('editing:exited', function (options) {
                  if(exitEditing){
                    var items = [];
                    groupItems.forEach(function (obj) {
                      items.push(obj);
                      canvas.remove(obj);
                    });
                    var grp
                    grp = new fabric.Group(items, {});
                    canvas.add(grp);
                    exitEditing = false;
                  }
                });
              }
            }
          });
        }
      }
    });//allows for editing text in groups


  }

  /*function loadNode(jsonString){
    //let n = JSON.parse(jsonString);
  let n=jsonString;
    fetch('/svg/' + n.svg_image)
        .then(result => result.text())
        .then((output) => {
          addNode(output, n.svg_image, n.type, n.name, n.x_coord, n.y_coord);
        }).catch(err => console.error(err));

    addNode()


  }*/




  function addNode(path, svg_image, type, name = "name", xCoord = 10, yCoord = 10, id = -1){

    let shape = new fabric.Path(path, {
      left: 0,
      top: 0
    });

    let nameText = new fabric.Textbox(name, {
      left: shape.getScaledWidth() * 0.05,
      top: shape.getScaledHeight() * 0.1,
      width: shape.getScaledWidth() * 0.9,
      fontSize: 12,
    });

    let svgText = new fabric.Textbox(svg_image, {
      width: 0,
      fill: 'rgb(0,0,0,0)'
    });

    let typeText = new fabric.Textbox(type, {
      width: 0,
      fill: 'rgb(0,0,0,0)'
    });

    let idText = new fabric.Textbox(id.toString(), {
      width: 0,
      fill: 'rgb(0,0,0,0)'
    });


    let group = new fabric.Group([shape, nameText, svgText, typeText, idText], {
      top: yCoord,
      left: xCoord
    });

    group.setControlsVisibility({
      mt: false, // middle top
      mb: false, // midle bottom
      ml: false, // middle left
      mr: false, // middle right
      mtr: false // rotate
    });

    canvas.add(group);

  }

  function removeCurrentNode(){
    canvas.remove(canvas.getActiveObject());
  }

  function parseJsonFromDiagram(){
    let json = '';
    canvas.forEachObject(function(object, index){
      console.log(object);
      let n = {
        description: "Default Description",
        height: object.height,
        width: object.height,
        x_coord: object.left,
        y_coord: object.top,
        name: object._objects[1].text,
        svg_image: object._objects[2].text,
        type: object._objects[3].text,
        id: parseInt(object._objects[4].text),
      };
      json = JSON.stringify(n)
      console.log(json);
    })
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

      vaddNode: function(file, type){
        fetch('/svg/' + file + '.svg')
            .then(result => result.text())
            .then((output) => {
              addNode(output, file + '.svg', type);
            }).catch(err => console.error(err));
      },

      vtestGet: function(){
        fetch('/testGet')
            .then(result => result.json())
            .then((output) => {
              console.log('Output: ', output);

            }).catch(err => console.error(err));
      },

      vloadNode: function(){
        //let n = {description:"Default Description",height:152,width:152,x_coord:363.82059800664456,y_coord:191.78807947019868,name:"A very good text",svg_image:"TextBox_Square_Interface.svg",type:"text_box_nodes",id:-1};

        fetch('/node/1')
            .then(result => result.text())
            .then((output) => {
              let n = JSON.parse(output);
              console.log(n);
              fetch('/svg/' + n.svg_image)
                  .then(result => result.text())
                  .then((output) => {
                    console.log(output);
                    addNode(output, n.svg_image, n.type, n.name, n.x_coord, n.y_coord);
                  }).catch(err => console.error(err));
            }).catch(err => console.error(err));

      },

      vparseJsonFromDiagram: function(){
        parseJsonFromDiagram();
      }


    },

  });



  document.addEventListener('keyup', (e) => {
    if (e.code === "Delete")        removeCurrentNode();
  });

</script>






<style>
.uml-editor {

}

.display-canvas{
  border: 2px solid black;
  margin: 1rem;
}
</style>