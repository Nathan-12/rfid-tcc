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
                <th>Nome da atividade</th>
                <th>Tipo da atividade</th>
                <th></th>
                <th></th>
              </tr>
            </thead>

            <tbody>
              <tr v-for="atividade of atividades" :key="atividade.id">
                <td>{{ atividade.id }}</td>
                <td>{{ atividade.nome }}</td>
                <td>{{ atividade.tipoAtividade }}</td>
                <td><button class="waves-effect waves-light btn-small" @click="adicionar(atividade)">Adicionar arquivos<i class="material-icons left">add</i></button></td>
                <td><button class=" waves-effect waves-light btn-small" @click="verFiles(atividade)">Ver arquivos<i class="material-icons left">visibility</i></button></td>
              </tr>
              
              
            </tbody>
          </table>
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
      atividades: []
    };
  },

  created:function() {
    
      this.$http.get("http://localhost:8090/api/listar").then(res => {
        this.atividades = res.data;
      });
    
  },

  methods: {
      adicionar(atividade){
          this.$router.push("/adicionar-arquivo/" + atividade.id);
      },
      verFiles(atividade){
        this.$router.push("/listar-arquivos/" + atividade.id);
        this.$router.go();
      }
  }
};
</script>

<style>
button {
  margin: 1%;
}
</style>