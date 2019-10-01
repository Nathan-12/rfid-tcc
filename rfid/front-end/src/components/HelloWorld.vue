<template>
  <v-container>
    
    <h1>Cadastro</h1>
    <v-form>
      <template v-slot:activator="{ on }">
                <v-btn
                  absolute
                  color="blue"
                  dark
                  fab
                  right
                  top
                  v-on="on"
                  id="fileInputButton"
                  onclick="document.getElementById('files').click()"
                >
                  <v-icon>note_add</v-icon>
                  <input id="files" ref="files" type="file" accept="application/pdf" style="display:none" v-on:change="selecionarArquivos()" />
                </v-btn>
      </template>
      <v-text-field label="CódigoRfid" v-model="codigo"></v-text-field>
      <v-btn class="primary" flat @click="submit">Salvar</v-btn>
    </v-form>
  </v-container>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      file: null,
      codigo: null
    };
  },
  mounted: function() {
  
  },
  methods: {

  }
};
</script>
