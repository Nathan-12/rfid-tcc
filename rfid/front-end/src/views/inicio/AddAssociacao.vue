<template>
  <v-app id="telaInicial">
    <v-content>
      <div class="organizarLogo">
        <v-btn to="/">Home</v-btn>
      </div>
      <v-container fill-height fluid>
        <v-layout align-center justify-center>
          <v-flex md4 sm8 xs12>
            <v-form>
              <v-layout row>
                <v-flex xs12>
                  <v-text-field
                    :error-messages="errors.collect('nome')"
                    data-vv-name="nome"
                    label="Nome da Atividade*"
                    v-model="nome"
                    v-validate="'required'"
                  ></v-text-field>
                </v-flex>
              </v-layout>

              <v-layout row>
                <v-flex md6>
                  
                </v-flex>
                <v-flex md6>
                  
                </v-flex>
              </v-layout>



              <v-btn :disabled="errors.any()" depressed @click="submit" color="primary">CADASTRAR</v-btn>
              <v-btn to="/coordenador/exames">CANCELAR</v-btn>
            </v-form>
          </v-flex>
        </v-layout>
      </v-container>
    </v-content>
  </v-app>
</template>


<script>
import axios from "axios";

export default {
  data() {
    return {
        nome:null,
    };
  },
  methods: {
    submit() {
          axios
            .post("http://localhost:8090/atividade/add", {
              nome: this.nome,
              
            })
            .then(() => {
                console.log(this.nome);
              this.$router.push("/adicionarAtividade");
            
            })
            .catch(error => {
              alert("Error" + error);
            });
      
    },


    notificar(notificacao) {
      this.mensagem = notificacao.mensagem;
      this.cor = notificacao.cor;
      this.snackbar = true;
    }
  }
};
</script>
<style lang="css" scoped>
#logo {
  background: #1c6eb7;
}

@media all and (max-width: 319px) {
  #telaInicial {
    background: #efefef;
    background-size: 160px 102px;
  }
}

@media all and (max-width: 639px) and (min-width: 320px) {
  #telaInicial {
    background: #efefef;
    background-size: 320px 205px;
  }
}

@media all and (max-width: 799px) and (min-width: 640px) {
  #telaInicial {
    background: #efefef url("/static/back-640.png");
    background-size: 640px 410px;
  }
}

@media all and (max-width: 1023px) and (min-width: 768px) {
  #telaInicial {
    background: #efefef url("/static/back-800.png");
    background-size: 800px 512px;
  }
}

@media all and (max-width: 1279px) and (min-width: 1024px) {
  #telaInicial {
    background: #efefef url("/static/back-1024.png");
    background-size: 1024px 656px;
  }
}

@media all and (max-width: 1561px) and (min-width: 1280px) {
  #telaInicial {
    background: #efefef url("/static/back-1280.png");
    background-size: 1280px 819px;
  }
}

@media all and (min-width: 1562px) {
  #telaInicial {
    background: #efefef url("/static/back.png");
    background-size: 1562px 1000px;
  }
}

#telaInicial {
  background-repeat: no-repeat;
  background-attachment: fixed;
  background-position: center;
}

p {
  margin-top: 24px;
  margin-left: 24px;
}

.organizarLogo {
  width: 98px;
  height: 34px;
  margin-top: 24px;
  margin-left: 24px;
  position: fixed;
}
</style>
