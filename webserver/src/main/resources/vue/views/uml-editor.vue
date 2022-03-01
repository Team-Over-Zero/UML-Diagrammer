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
      <button>Square</button>
      <button>Class</button>
      <button>Note</button>
      <button>Folder</button>
      <button>Text Box</button>
      <button>Lifeline</button>
      <button>Loop</button>
      <button>User</button>
      <button>Oval</button>
    </div>
  </div>
</template>
<script>

Vue.component("uml-editor", {template: "#uml-editor"});
</script>
<style>
.uml-editor {

}
</style>