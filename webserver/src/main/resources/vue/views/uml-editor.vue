<template id="uml-editor">
  <div class="uml-editor">
    <h1>
      UML Diagrammer
    </h1>
    <div class="editor">
      <div>
        <button v-on:click="vparseJsonFromDiagram()">Save</button>
        <button v-on:click="vloadPage()">Load</button>
        <button v-on:click="vdeletePage()">Delete Page</button>
        <button v-on:click="vreturnToHome()">Return to Home</button>
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
        <button v-on:click="vaddNode('Class', 'classnodes')">Class</button>
        <button v-on:click="vaddNode('Note', 'notenodes')">Note</button>
        <button v-on:click="vaddNode('Folder', 'foldernodes')">Folder</button>
        <button v-on:click="vaddNode('TextBox_Square_Interface', 'textboxnodes')">Text Box</button>
        <button v-on:click="vaddNode('LifeLine', 'lifelinenodes')">Lifeline</button>
        <button v-on:click="vaddNode('Loop', 'loopnodes')">Loop</button>
        <button v-on:click="vaddNode('StickFigure', 'stickfigurenodes')">User</button>
        <button v-on:click="vaddNode('Oval_UseCase', 'ovalnodes')">Oval</button>

      </div>
      <canvas id="c" width ="1200px" height="600px" class="display-canvas"></canvas>
    </div>
    <div class="login">
      <label for="username">First name:</label>
      <input type="text" id="username" name="username"><br><br>
      <label for="password">Last name:</label>
      <input type="text" id="password" name="password"><br><br>
      <button v-on:click="vtryUserLogin()">login</button>
      <button v-on:click="vmakeNewUser()">Make New User</button><br><br>
      <div id="addPageDiv" style="display: none">
        <label for="newPage">New Page Name:</label>
        <input type="text" id="newPage" name="newPage"><br><br>
        <button v-on:click="vcreatePage()">Make New Page</button><br>
        <h2>Load page</h2>
      </div>

      <div id="loadPageButtons">
        <li v-for="(btn, index) in pageBtns">
          <button  @click="vchangeCurrentPage(btn.id)" type="text"> {{ btn.name}}</button>
        </li>
      </div>
    </div>
  </div>

</template>



<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/fabric.js/521/fabric.min.js"></script>

<script type="module">

  let canvas = new fabric.Canvas('c');
  let nodes = [];

  let currentPage = 1;
  let currentUser = {};

  let defaultNode = {
    description: "a loop",
    height: 50,
    width: 50,
    x_coord: 10,
    y_coord: 10,
    name: "name",
    svg_image: "",
    type: "",
    id: -1,
    page_id: -1,
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
    console.log(group);

    if(id == -1){
      let n = {
        description: "Did this make it",
        height: 50,
        width: 50,
        x_coord: xCoord,
        y_coord: yCoord,
        name: name,
        svg_image: svg_image,
        type: type,
        id: -1,
        page_id: currentPage,
      };


      let nodeJson = JSON.stringify(n);
      let pageJson = JSON.stringify({id:currentPage});

      fetch('/createNode/' + nodeJson + "/" + pageJson)
          .then(result => result.json())
          .then((output) => {
            console.log('Output: ', output);
            idText.text = output.id;
          }).catch(err => console.error(err));
    }

  }

  function removeCurrentNode(){

    let object = canvas.getActiveObject()

    let n = {
      description: "Default Description",
      height: Math.floor(object.height),
      width: Math.floor(object.height),
      x_coord: Math.floor(object.left),
      y_coord: Math.floor(object.top),
      name: object._objects[1].text,
      svg_image: object._objects[2].text,
      type: object._objects[3].text,
      id: parseInt(object._objects[4].text),
      page_id: currentPage,
    };

    let nodejson = JSON.stringify(n)
    let pagejson = JSON.stringify({id:currentPage});
    fetch('/deleteNode/' + nodejson + '/' + pagejson)
        .then(result => result.text())
        .then((output) => {
          console.log('Output: ', output);
        }).catch(err => console.error(err));

    canvas.remove(canvas.getActiveObject());
  }

  function parseJsonFromDiagram(){
    let json = '';
    canvas.forEachObject(function(object, index){
      console.log(object);
      let n = {
        description: "Default Description",
        height: Math.floor(object.height),
        width: Math.floor(object.height),
        x_coord: Math.floor(object.left),
        y_coord: Math.floor(object.top),
        name: object._objects[1].text,
        svg_image: object._objects[2].text,
        type: object._objects[3].text,
        id: parseInt(object._objects[4].text),
        page_id: currentPage,
      };
      json = JSON.stringify(n)
      console.log(json);
      fetch('/updateNode/' + json)
          .then(result => result.text())
          .then((output) => {
            console.log(output);
          }).catch(err => console.error(err));

      return json;
    })
  }

function createPage(){

}

function loadPage(pageId){
  canvas.clear();
  //let jsonId = JSON.stringify({pageid:currentPage});
  let jsonId = "{\"id\":\"" + pageId + "\"}";
  fetch('/loadPage/' + jsonId)
      .then(result => result.json())
      .then((output) => {

        let strings = output[0];

        console.log('Output: ', strings);

        strings.forEach((string) => {
          let n = JSON.parse(string);
          console.log(n);

          fetch('/svg/' + n.svg_image)
              .then(result => result.text())
              .then((output) => {
                addNode(output, n.svg_image, n.type, n.name, parseInt(n.x_coord), parseInt(n.y_coord), n.id);

              }).catch(err => console.error(err));

        });

      }).catch(err => console.error(err));
}








  Vue.component("uml-editor", {
    template: "#uml-editor",

    data() {
      return {
        nodes: [],
        pageBtns: [],
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

      vloadNode: function(){

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

      vloadPage: function(){
        loadPage(currentPage);
      },

      vparseJsonFromDiagram: function(){
        parseJsonFromDiagram();
      },

      vcreatePage: function(){
        let userjson = JSON.stringify(currentUser);
        let pagejson = JSON.stringify({id:-1, name:$('#newPage').val()})
        fetch('/createPage/' + pagejson + '/' + userjson)
            .then(result => result.text())
            .then((output) => {
              console.log(output);
              this.vgetUserPages();
            }).catch(err => console.error(err));
      },

      vcreateNode: function(json){
        fetch('/createNode/' + json)
            .then(result => result.json())
            .then((output) => {
              console.log('Output: ', output);
            }).catch(err => console.error(err));

      },

      vtryUserLogin: function(){
        let username = $('#username').val();
        let password = $('#password').val();
        let userjson = "{\"id\":\"" + -1 + "\"" + ",\"name\":\"" + username + "\"" + ",\"password\":\"" + password + "\"}";
        console.log(userjson);
        fetch('/loginUser/' + userjson)
            .then(result => result.json())
            .then((output) => {
              console.log('Output: ', output);
              currentUser = output;
              this.vgetUserPages();
            }).catch(err => {
              console.error(err)
              alert("invalid login")
            });
      },

      vgetUserPages: function(){
        this.pageBtns = [];
        let userjson = JSON.stringify(currentUser);
        fetch('/getUserPages/' + userjson)
            .then(result => result.json())
            .then((output) => {
              $('#addPageDiv').css('display', 'block');
              console.log('Output: ', output);
              output.forEach((out) => {
                let page = JSON.parse(out);
                this.pageBtns.push({
                  name: page.name,
                  id: page.id
                });
              });
            }).catch(err => console.error(err));

      },

      vchangeCurrentPage: function(newPage){
        console.log(newPage);
        $('.editor').css("display", "block");
        $('.login').css("display", "none");
        currentPage = newPage;
        loadPage(currentPage);
      },

      vmakeNewUser: function(){
        let username = $('#username').val();
        let password = $('#password').val();
        let userjson = "{\"id\":\"" + -1 + "\"" + ",\"name\":\"" + username + "\"" + ",\"password\":\"" + password + "\"}";
        fetch('/createUser/' + userjson)
            .then(result => result.json())
            .then((output) => {
              console.log('Output: ', output);
              currentUser = output;
              this.vgetUserPages();
            }).catch(err => console.error(err));

      },

      vdeletePage: function(){
        let pagejson = JSON.stringify({id:currentPage});
        fetch('/deletePage/' + pagejson)
            .then(result => result.text())
            .then((output) => {
              console.log('Output: ', output);
              this.vgetUserPages();
              $('.editor').css("display", "none");
              $('.login').css("display", "block");
            }).catch(err => console.error(err));
      },

      vreturnToHome: function(){
        this.vparseJsonFromDiagram();
        $('.editor').css("display", "none");
        $('.login').css("display", "block");
      }



    },

  });



  document.addEventListener('keyup', (e) => {
    if (e.code === "Delete"){
      removeCurrentNode();
    }
  });

</script>






<style>
.uml-editor {

}

.display-canvas{
  border: 2px solid black;
  margin: 1rem;
}

.editor{
  display: none;
}

.login{
  display: block;
}

</style>