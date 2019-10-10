<template>
  <div id="app">
    <div class="container">
      <div class="row">
        <div class="col s2"></div>
        <div class="col s8">
          <form @submit.prevent="salvar">
              <div class="row">
                  <div class="col s6">
                      
                  </div>
                  <div class="col s6">
                      
                  </div>
              </div>
              <table>
        <thead>
          <tr>
              <th>#</th>
              <th>Escolha o arquivo</th>
              <th>Insira o código rfid</th>
          </tr>
        </thead>

        <tbody>
          <tr>
            <td>1</td>
            <td><input type="file" /></td>
            <td><input type="text" placeholder="Código do cartão" /></td>
          </tr>
          <tr>
              <td>2</td>
            <td><input type="file" /></td>
            <td><input type="text" placeholder="Código do cartão" /></td>
          </tr>
          <tr>
              <td>3</td>
            <td><input type="file" /></td>
            <td><input type="text" placeholder="Código do cartão" /></td>
          </tr>
        </tbody>
      </table>
            <button class="waves-effect green waves-light btn-small">
              Salvar
              <i class="material-icons left">save</i>
            </button>
            <button type="reset" class="waves-effect red waves-light btn-small">
              Limpar
              <i class="material-icons left">delete</i>
            </button>
          </form>
        </div>
        <div class="col s2"></div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "app",
  data() {
    return {
      nome: "",
      tipoAtividade: ""
    };
  },
  mounted() {
    //this.listar()
  },
  methods: {
    salvar() {
      this.$http
        .post("http://localhost:8090/api/adicionar", {
          nome: this.nome,
          tipoAtividade: this.tipoAtividade
        })
        .then(() => {
          this.$router.push("/cadastro-ok");
        })
        .catch(e => {
          alert("erro: " + e);
        });
    }
  }
};
</script>

<style>
button {
  margin: 1%;
}
</style>