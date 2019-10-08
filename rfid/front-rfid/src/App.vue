<template>
  <div id="app">
    <nav>
      <div class="nav-wrapper blue darken-1">
        <a href="#" class="brand-logo center">Sistema rfid</a>
      </div>
    </nav>

    <div class="container">
      <form @submit.prevent="salvar">
        <label>Nome da atividade</label>
        <input type="text" placeholder="Nome" v-model="atividade.nome" />

        <p>
          <label>
            <input class="with-gap" name="group1" type="radio" value="ASSOCIACAO" v-model="atividade.tipoAtividade" />
            <span>Associação</span>
          </label>
        </p>
        <p>
          <label>
            <input class="with-gap" name="group1" type="radio" disabled="disabled" />
            <span>Atividade2</span>
          </label>
        </p>
        <p>
          <label>
            <input class="with-gap" name="group1" type="radio" disabled="disabled"/>
            <span>Atividade3</span>
          </label>
        </p>
        <button class="waves-effect waves-light btn-small">
          Salvar
          <i class="material-icons left">save</i>
        </button>
      </form>
    </div>
  </div>
</template>

<script>
import Atividade from "./services/sistema";

export default {
  name: "app",
  data() {
    return {
      atividade: {
        nome: "",
        tipoAtividade: ""
      }
    };
  },
  mounted() {
    //this.listar()
  },
  methods: {
    salvar() {
      Atividade.salvar(this.atividade).then(() => {
        alert("ok");
      });
    }
  }
};
</script>

<style>
</style>