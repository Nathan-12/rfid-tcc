<template>
  <div id="app">
    <div class="container">
      <div class="row">
        <div class="col s1"></div>
        <div class="col s10">
          <table>
            <thead>
              <tr>
                <th>#</th>
                <th>Nome do arquivo</th>
                <th>codigo</th>
              </tr>
            </thead>

            <tbody>
              <tr v-for="arquivo of arquivos" :key="arquivo.id">
                <td>{{ arquivo.id }}</td>
                <td>{{ arquivo.nome }}</td>
                <td>00{{ arquivo.codigo }}</td>
              </tr>
            </tbody>
          </table>
          <div id="container">
            <input id="codigoCartao" type="text" />
          </div>

          <div class="container">
            <div class="row">
              <div class="col-sm"></div>

              <div class="col-sm">
                <!--<video loop id="tagVideo" width="400px" height="400px">
                  <source src="/assets/arquivos/" type="mp4" />
                </video>-->
                <img id="tagImagem" src="/"/>
                
              </div>

              <div class="col-sm"></div>
            </div>
          </div>
        </div>
        <div class="col s1"></div>
      </div>
    </div>
  </div>
</template>

<script>
document.addEventListener("DOMContentLoaded", function() {
  const inputCodigo = document.querySelector("#codigoCartao");
  const img = document.querySelector("#tagImagem");

inputCodigo.focus();
  inputCodigo.addEventListener("keyup", function(event) {
    if (event.which === 13) {
      //video.pause();
      //video.setAttribute("src", "arquivos/" + inputCodigo.value);
      //video.load();
      //video.play();
      img.setAttribute("src", "/static/arquivos/" + inputCodigo.value);
      inputCodigo.value = "";
    }
  });
});

export default {
  name: "app",
  data() {
    return {
      arquivos: []
    };
  },

  created: function() {
    this.atividadeId = this.$route.params.id;
    this.initialize();
  },

  methods: {
    initialize() {
      this.$http
        .get("http://localhost:8090/arquivo/exibir/" + this.atividadeId)
        .then(res => {
          this.arquivos = res.data;
        });
    },
    blockSubmit(event) {
      event.preventDefault();
    }
  }
};
</script>

<style>
button {
  margin: 1%;
}
</style>