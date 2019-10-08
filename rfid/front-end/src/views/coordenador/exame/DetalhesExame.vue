<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <v-container fluid grid-list-xl>
    <app-breadcrumbs :items="breadcrumbItems" icon="description" title="Detalhes do Exame" />
    <v-layout row wrap>
      <v-flex xs12>
        <v-widget title="Informações Básicas" enableActions>
          <div slot="widget-content">
            <v-layout row>
              <v-flex xs12>
                <span class="title" style="font-weight:bold">{{ exame.titulo }}</span>
              </v-flex>
            </v-layout>
            <v-divider></v-divider>
            <v-layout row>
              <v-flex xs2>
                <span class="font-weight-bold text-uppercase">Status do exame</span>
              </v-flex>
              <v-flex xs3>
                <span class="font-weight-bold text-uppercase">Início das inscrições</span>
              </v-flex>
              <v-flex xs3>
                <span class="font-weight-bold text-uppercase">Término das inscrições</span>
              </v-flex>
              <v-flex xs2>
                <span class="font-weight-bold text-uppercase">Data da prova</span>
              </v-flex>
              <v-flex xs2>
                <span class="font-weight-bold text-uppercase">Nº de inscritos / Nº de vagas</span>
              </v-flex>
            </v-layout>
            <v-layout row>
              <v-flex xs2>
                <span class="font-weight-regular text-uppercase">{{ getLabelFase(exame.fase) }}</span>
              </v-flex>
              <v-flex xs3>
                <span class="font-weight-regular">{{ exame.inicioInscricoes }}</span>
              </v-flex>
              <v-flex xs3>
                <span class="font-weight-regular">{{ exame.terminoInscricoes }}</span>
              </v-flex>
              <v-flex xs2>
                <span class="font-weight-regular">{{ exame.dataHoraProva }}</span>
              </v-flex>
              <v-flex xs2>
                <span
                  class="font-weight-regular"
                >{{ exame.numeroInscritos + ' / ' + exame.numeroVagas }}</span>
              </v-flex>
            </v-layout>
            <v-divider></v-divider>
            <v-layout row>
              <v-flex xs12>
                <span class="font-weight-bold text-uppercase">Descrição:</span>
                <span class="font-weight-regular">{{ exame.descricao }}</span>
              </v-flex>
            </v-layout>
          </div>
          <div slot="widget-actions">
            <v-layout row>
              <v-flex xs12>
                <v-btn color="indigo" flat :to="'/coordenador/exames/' + exameId + '/editar'">
                  <v-icon small>edit</v-icon>Editar
                </v-btn>
                <v-btn color="error" :disabled="naoPermiteExclusao()" flat @click="dialog = true">
                  <v-icon small>delete</v-icon>Deletar
                </v-btn>               
                <v-btn color="black" v-if="!isHidden" v-show="this.exame.fase !== 'SELECAO'" flat @click="dialogEncerrarInscricoes = true">
                  <v-icon small>mdi-close-circle</v-icon>ENCERRAR INSCRIÇÕES                 
                </v-btn>                
              </v-flex>
            </v-layout>
          </div>
        </v-widget>
      </v-flex>

      <v-flex xs12>
        <v-widget title="Lista de Candidatos">
          <div slot="widget-content">
            <v-text-field
              append-icon="search"
              class="pesquisa"
              hide-details
              label="Buscar Usuários"
              single-line
              v-model="search"
            ></v-text-field>
            <v-data-table
              :headers="headersCandidatos"
              :items="candidatos"
              :search="search"
              hide-actions
              no-data-text="Nenhum candidato inscrito"
              :no-results-text="'Nenhum resultado para: ' + search"
            >
              <template v-slot:items="candidatos">
                <td class="body-1">
                  <b>{{ candidatos.item.nome }}</b>
                </td>
                <td
                  class="body-1 text-uppercase"
                >{{ getLabelStatusInscricao(candidatos.item.statusInscricao) }}</td>
                <td
                  class="body-1 text-uppercase"
                  v-bind:class="candidatos.item.statusAvaliacao ? candidatos.item.statusAvaliacao.value : ''"
                >
                  <b>{{ candidatos.item.statusAvaliacao ? getLabelStatusAvaliacao(candidatos.item.statusAvaliacao.label) : ""}}</b>
                </td>
                <td class="body-1">
                  {{ candidatos.item.reavaliacaoSolicitada ? candidatos.item.notaReavaliacao :
                  candidatos.item.notaAvaliacao}}
                </td>
                <div>
                  <v-btn
                    color="cyan"
                    outline
                    round
                    :to=" exameId + '/candidato-inscrito/'+ candidatos.item.idInscricao"
                  >
                    <v-icon small>search</v-icon>Detalhes
                  </v-btn>
                </div>
              </template>
            </v-data-table>
          </div>
        </v-widget>
      </v-flex>

      <v-flex xs12>
        <v-widget title="Locais de Provas">
          <div slot="widget-content">
            <v-tooltip top>
              <template v-slot:activator="{ on }">
                <v-btn 
                @click="abrirDialog()" 
                :disabled="naoPermiteAdicionarLocal()"
                absolute color="blue" 
                dark 
                fab 
                right 
                top 
                v-on="on">
                  <v-icon>add_location</v-icon>
                </v-btn>
              </template>
              <span>Adicionar Local de Prova</span>
            </v-tooltip>

            <v-data-table :headers="headersLocais" :items="locais" hide-actions no-data-text="Nenhum local de prova cadastrado">
              <template v-slot:items="locais">
                <td class="body-1">
                  <b>{{ locais.item.nome }}</b>
                </td>
                <td class="body-1">
                  <b>{{ locais.item.candidatosAlocados }} / {{ locais.item.capacidade }}</b>
                </td>

                <v-btn
                  color="cyan"
                  outline
                  round
                  v-bind:to="'/coordenador/localprova/'+ locais.item.id"
                >
                  <v-icon small>search</v-icon>Detalhes
                </v-btn>

                <v-tooltip top>
                  <template v-slot:activator="{ on }">
                    <v-btn color="indigo" fab outline small v-on="on">
                      <v-icon>edit</v-icon>
                    </v-btn>
                  </template>
                  <span>Editar Local</span>
                </v-tooltip>

                <v-tooltip top>
                  <template v-slot:activator="{ on }">
                    <v-btn
                      color="red"
                      fab
                      outline
                      small
                      v-on="on"
                      :disabled="naoPermiteExclusaoLocal(locais.item.candidatosAlocados)"
                      flat
                      @click="abrirDialogExcluirLocalProva(locais.item)"
                    >
                      <v-icon>delete</v-icon>
                    </v-btn>
                  </template>
                  <span>Deletar</span>
                </v-tooltip>
              </template>
            </v-data-table>
          </div>
        </v-widget>
      </v-flex>
    </v-layout>

    <v-snackbar :color="cor" right top v-model="snackbar">{{ mensagem }}</v-snackbar>

    <v-dialog max-width="290" v-model="dialog">

            <v-card>
                <v-card-title class="headline">Excluir Exame</v-card-title>

                <v-card-text>Tem certeza que deseja excluir o exame?</v-card-text>

                <v-card-actions>
                    <v-spacer></v-spacer>

                    <v-btn @click="dialog = false" color="silver darken-1" flat="flat">Não</v-btn>
                    <v-btn @click="excluirExame()" color="red darken-1" flat="flat">Sim</v-btn>

                </v-card-actions>
            </v-card>
      </v-dialog>

    <v-dialog max-width="290" v-model="dialogLocal">

      <v-card>
        <v-card-title class="headline">Excluir Local de Prova</v-card-title>

        <v-card-text>Tem certeza que deseja excluir o local de prova?</v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn @click="dialog = false" color="silver darken-1" flat="flat">Não</v-btn>

          <v-btn @click="excluirLocalProva()" color="red darken-1" flat="flat">Sim</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>


    <v-dialog max-width="290" v-model="dialogEncerrarInscricoes">
      <v-card>
        <v-card-title class="headline">Encerrar Inscrições</v-card-title>

        <v-card-text>Tem certeza que deseja encerrar as inscrições?</v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn @click="dialogEncerrarInscricoes = false" color="silver darken-1" flat="flat">Não</v-btn>

          <v-btn @click="encerrarInscricao()" v-on:click="isHidden = true" color="red darken-1" flat="flat">Sim</v-btn>

         </v-card-actions>
             </v-card>
    </v-dialog>

    <v-dialog max-width="500" persistent v-model="dialog1">
      <v-card>
        <v-card-title class="headline">Adicionar Local</v-card-title>
        <v-container grid-list-md>
        <v-layout justify-center wrap>
          <v-flex xs6 sm8 md9>
            <v-text-field
              label="Nome do local*"
              :error-messages="errors.collect('nomeLocal')"
              data-vv-name="nomeLocal"
              v-model="nomeLocal"
              v-validate="'required'"
            ></v-text-field>
          </v-flex>

          <v-flex xs6 sm8 md3>
            <v-text-field
              label="Capacidade*"
              type="number"
              :error-messages="errors.collect('capacidade')"
              v-model="capacidade"
              data-vv-name="capacidade"
              v-validate="'required'"
            ></v-text-field>
          </v-flex>
        </v-layout>
        </v-container>
        <span class="pa-3 caption">*Campos Obrigatórios</span>
        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn @click="limpar()" color="silver darken-1">CANCELAR</v-btn>

          <v-btn @click="submit()" color="primary" >SALVAR</v-btn>

        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
import axios from "axios";
import moment from "moment";
import AppBreadcrumbs from "../../../components/core/AppBreadcrumbs";
import VWidget from "../../../components/core/VWidget";
import store from "../../../store";

export default {
  components: {
    VWidget,
    AppBreadcrumbs,
  },
  
  data() {
    return {
      candidatos: [],
      headersCandidatos: [
        { text: "NOME", value: "nome" },
        { text: "STATUS", value: "status" },
        { text: "RESULTADO", value: "resultado" },
        { text: "NOTA", value: "nota" },
        { value: null, sortable: false }
      ], 
      locais: [],
      headersLocais: [
        { text: "LOCAL", value: "nome" },
        { text: "CANDIDATOS ALOCADOS / CAPACIDADE", value: "capacidade" },
        { value: null, sortable: false }
      ],
      exameId: "",
      exame: {},
      capacidade: null,
      nomeLocal: null,
      search: "",
      usuario: [],
      isHidden: false,
      dialog: false,
      dialog1: false,
      dialogLocal: false,
      dialogEncerrarInscricoes: false,
      breadcrumbItems: [
        {
          position: 1,
          text: "Exames",
          disabled: false,
          href: "/coordenador/exames"
        }
      ],

      snackbar: false,
      mensagem: "",
      cor: "",

      dictionary: {
        custom: {
          nomeLocal: {
            required: () => "O nome do local é obrigatório!"
          },
          capacidade: {
            required: () => "O numero de vagas é obrigatório!"
          }
        }
      }
    };
  },

  mounted: function() {
    this.$validator.localize("pt", this.dictionary);
    this.exameId = this.$route.params.id;
    this.initialize();
    this.limpar();
  },
  methods: {
    limpar() {
      this.dialog1 = false;
      this.capacidade = null;
      this.nomeLocal = null;
      this.errors.clear();
      this.$validator.reset();
    },

    submit() {
      this.$validator.validateAll().then(result => {
        if (result) {
          axios
          .post("local/" + this.exameId, {
            nome: this.nomeLocal,
            capacidade: this.capacidade
            
          }).then(res=> {
            this.locais.push(res.data);
        })
        .catch(error => {
          this.snackbar = true;
          this.mensagem = error.response.data;
          this.cor = "error";
          this.dialog = false;
        });
          this.limpar();
      }
      });
    },

    getLabelStatusInscricao(status) {
      return store.getters["enums/getLabelStatusInscricao"](status);
    },
    getLabelStatusAvaliacao(status) {
      return store.getters["enums/getLabelStatusAvaliacao"](status);
    },
    getLabelFase(fase) {
      return store.getters["enums/getLabelFase"](fase);
    },
    initialize() {
      axios.get("exame/" + this.exameId).then(res => {
        this.exame = res.data;
        this.formatarData();
        this.breadcrumbItems.push({
          position: 3,
          text: this.exame.titulo,
          disabled: true
        });
      });
      axios.get("inscricoes/exame/" + this.exameId).then(res => {
        this.candidatos = res.data;
      });
      axios.get("local/listarLocal/" + this.exameId).then(res => {
        this.locais = res.data;
      });
    },
    formatarData() {
      moment.locale("pt-br");
      let data = this.exame.inicioInscricoes;
      this.exame.inicioInscricoes = moment(data).format("LLL");
      let date = this.exame.terminoInscricoes;
      this.exame.terminoInscricoes = moment(date).format("LLL");
      let Data = this.exame.dataHoraProva;
      this.exame.dataHoraProva = this.exame.dataHoraProva
        ? moment(Data).format("LL")
        : "-";
    },
    naoPermiteExclusao() {
      //return this.exame.fase != "INSCRICAO" || this.exame.numeroInscritos > 0;
      return false;
    },
    naoPermiteExclusaoLocal(local) {
      if (this.exame.fase == "FINALIZADO" || local > 0) {
        return true;
      } else {
        return false;
      }
    },
    excluirExame() {
      axios
        .delete("/exame/" + this.exameId)
        .then(() => {
          this.$router.push({
            name: "CoordenadorExames",
            params: {
              cor: "success",
              snackbar: true,
              mensagem: "Local de prova excluído com sucesso"
            }
          });
        })
        .catch(error => {
          this.snackbar = true;
          this.mensagem = error.response.data;
          this.cor = "error";
          this.dialog = false;
        });
      this.dialog = false;

    },
      naoPermiteAdicionarLocal(){
      if(this.exame.fase == "FINALIZADO"){
        return true;
      } else {
        return false;
      }
    },

    abrirDialog() {
      this.dialog1 = true;
    },

    abrirDialogExcluirLocalProva(locais) {
      if (!this.naoPermiteExclusaoLocal(locais)) {
        this.localProvaDelete = locais;
        this.dialog = true;
      }
    },
    excluirLocalProva() {
     axios.delete("/local/" + this.localProvaDelete.id)
                    .then(() => {
                        this.locais.splice(this.locais.indexOf(this.localProvaDelete), 1);
                        this.localProvaDelete = null;
                        
                        this.snackbar = true;
                        this.mensagem = "Local de prova excluído com sucesso";
                        this.cor = "success";
                    })
                    .catch((error) => {
                        this.snackbar = true;
                        this.mensagem = error.response.data;
                        this.cor = "error";
                    });

                this.dialogLocal = false;
            },
    encerrarInscricao() {    
        axios.put("/exame/" + this.exameId)
            .then(res => {
              this.exame = res.data;
              this.formatarData(); 

              this.snackbar = true;
              this.mensagem = "Inscrições encerradas";
              this.cor = "success";
               
            })
            .catch((error) => {
              this.mensagem = error.response.data;
              this.cor = "error";
              this.snackbar = true;
              this.isHidden = false;
            });

        this.dialogEncerrarInscricoes = false;
   
   }
    
    
  },
};
</script>

<style>
.APROVADO {
  color: #45e276;
  font-weight: 500;
}

.REPROVADO {
  color: #ea4c72;
  font-weight: 500;
}

.EM_AVALIACAO {
  color: #565656;
  font-weight: 500;
}

</style>
