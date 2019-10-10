<template>
  <div id="app">
    <nav>
      <div class="nav-wrapper green darken-1 color" >
        <a href="#" class="brand-logo center">Sistema rfid</a>
      </div>
    </nav>

    <div class="container">
      <form @submit.prevent="salvar">
        
        <input type="text" placeholder="Nome da Atividade" v-model="nome" />
    
        <p>
          <label>
            <input class="with-gap" name="group1" type="radio" value="ASSOCIACAO" v-model="tipoAtividade" />
            <span>Associação</span>
          </label>
        </p>
        <p>
          <label>
            <input class="with-gap" name="group1" type="radio" value="ATIVIDADE@" v-model="tipoAtividade" />
            <span>Atividade 2</span>
          </label>
        </p>
        <p>
          <label>
            <input class="with-gap" name="group1" type="radio" value="ATIVIDADE3" v-model="tipoAtividade" />
            <span>Atividade 3</span>
          </label>
        </p>
        <button class="waves-effect green waves-light btn-small">
          Salvar
          <i class="material-icons left">save</i>
        </button>
      </form>
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
    salvar(){
            this.$http.post('http://localhost:8090/api/adicionar',{
                nome: this.nome,
                tipoAtividade: this.tipoAtividade
            })
            .then(() => {
                this.$router.push("/cadastro-ok");
            })
            .catch(e =>{
                alert("erro: " + e);
            })
        }
  }
};
</script>

<style>

</style>