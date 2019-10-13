<template>
  <div id="app">
    <div class="container">
      <div class="row">
        <div class="col s1"></div>
        <div class="col s10">
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
                <img id="tagImagem" src />
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
export default {
  name: "app",
  data() {
    return {
      arquivos: [],
      indice: 0,
      srcSet: ""
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
          const inputCodigo = document.querySelector("#codigoCartao");
          const img = document.querySelector("#tagImagem");
          
          this.arquivos = res.data;
          this.srcSet = "00" + res.data[this.indice].codigo;

          console.log(this.arquivos);
          console.log(this.srcSet);
          img.setAttribute("src", "/static/arquivos/" + this.srcSet);
          inputCodigo.addEventListener("keyup", function(event) {
            if (event.which === 13) {
              if (this.srcSet === this.inputCodigo) {
                alert("codigo certo!!!");
                this.indice ++;
                img.setAttribute("src", "/static/arquivos/" + this.srcSet);
                console.log(this.indice);
                console.log(this.inputCodigo);
              } else {
                alert("Código errado");
              }
              //video.pause();
              //video.setAttribute("src", "arquivos/" + inputCodigo.value);
              //video.load();
              //video.play();
              inputCodigo.value = "";
            }
          });
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