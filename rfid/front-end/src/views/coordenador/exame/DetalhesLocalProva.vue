<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <v-container fluid grid-list-xl>
    <app-breadcrumbs
      :items="breadcrumbItems"
      icon="mdi-map-marker"
      title="Detalhes do Local de prova"
    />
    <v-layout row wrap>
      <v-flex xs12>
        <v-widget title="Informações Básicas" enableActions>
          <div slot="widget-content">
            <v-layout row>
              <v-flex xs12>
                <span class="title" style="font-weight:bold">{{ locais.nome }}</span>
              </v-flex>
            </v-layout>
            <v-divider></v-divider>
            <v-layout row>
              <v-flex xs6>
                <span class="font-weight-bold text-uppercase">Candidatos Alocados :</span>
                <span class="font-weight-regular text-uppercase">{{ locais.candidatosAlocados }}</span>
              </v-flex>
              <v-flex xs6>
                <span class="font-weight-bold text-uppercase">Capacidade :</span>
                <span class="font-weight-regular text-uppercase">{{ locais.capacidade }}</span>
              </v-flex>
            </v-layout>
          </div>
          <div slot="widget-actions">
            <v-layout row>
              <v-flex xs12>
                <v-btn color="indigo" flat>
                  <v-icon small>edit</v-icon>Editar
                </v-btn>
                <v-btn color="error" flat>
                  <v-icon small>delete</v-icon>Deletar
                </v-btn>
              </v-flex>
            </v-layout>
          </div>
        </v-widget>
      </v-flex>
      <v-flex xs12>
        <v-widget title="Lista de Candidatos alocados">
          <div slot="widget-content">
            <v-toolbar flat color="white">
              <v-text-field
                append-icon="search"
                class="pesquisa"
                hide-details
                label="Buscar Usuários"
                single-line
                v-model="search"
              ></v-text-field>
            </v-toolbar>

            <v-data-table
              :headers="headersCandidatos"
              :items="candidatos"
              item-key="idInscricao"
              :search="search"
              hide-actions
              no-data-text="Nenhum candidato alocado"
              :no-results-text="'Nenhum resultado para: ' + search"
            >
              <template v-slot:items="candidatos">
                <td class="body-1">
                  <b>{{ candidatos.item.nomeCandidato }}</b>
                </td>
                <td class="body-1">{{ candidatos.item.email }}</td>
                <td class="body-1 text-uppercase">
                  <b>{{ candidatos.item.telefone | telefone }}</b>
                </td>
                <td class="body-1 text-center">
                  <p>{{ candidatos.item.necessidadeEspecial }}</p>
                </td>
                <div>
                  <v-btn
                    color="cyan"
                    outline
                    round
                    :to="candidatos.item.idExame + '/candidato-inscrito/'+ candidatos.item.idInscricao"
                  >
                    <v-icon small>search</v-icon>Detalhes
                  </v-btn>
                  <v-tooltip top>
                    <template v-slot:activator="{ on }">
                      <v-btn
                        :disabled="mostrarBotaoMoverCandidato(candidatos.item.faseExame)"
                        color="indigo"
                        outline
                        fab
                        small
                        v-on="on">
                        <v-icon>mdi-exit-to-app</v-icon>
                      </v-btn>
                    </template>
                    <span>Mover candidato</span>
                  </v-tooltip>
                </div>
              </template>
            </v-data-table>
          </div>
        </v-widget>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import axios from "axios";
import AppBreadcrumbs from "../../../components/core/AppBreadcrumbs";
import VWidget from "../../../components/core/VWidget";

export default {
  components: {
    VWidget,
    AppBreadcrumbs
  },

  data() {
    return {
      candidatos: [],
      headersCandidatos: [
        { text: "NOME", value: "nomeCandidato" },
        { text: "EMAIL", value: "email" },
        { text: "TELEFONE", value: "telefone" },
        { text: "NECESSIDADE ESPECIAL", value: "necessidadeEspecial" },
        { value: null, sortable: false }
      ],
      locais: [],
      localprovaId: "",
      headersLocais: [
        { text: "LOCAL", value: "nome" },
        { text: "CANDIDATOS ALOCADOS", value: "candidatosAlocados" },
        { text: "CAPACIDADE", value: "capacidade" }
      ],
      exame: [],
      search: "",
      dialog: false,
      breadcrumbItems: [
        {
          position: 1,
          text: "Exames",
          disabled: false,
          href: "/coordenador/exames"
        },
         
      ],
      snackbar: false,
      mensagem: "",
      cor: ""
    };
  },
  mounted: function() {
    this.localprovaId = this.$route.params.id;
    this.initialize();
  },
  filters: {
    telefone: function(telefone) {
      if (!telefone) {
        return "";
      } else if (telefone.length === 11) {
        return telefone.replace(/(\d{2})(\d)(\d{4})(\d{4})/g, "($1) $2.$3-$4");
      } else if (telefone.length === 10) {
        return telefone.replace(/(\d{2})(\d{4})(\d{4})/g, "($1) $2-$3");
      }
    }
  },
  methods: {
    initialize() {
      axios.get("local/" + this.localprovaId).then(res => {
        this.locais = res.data.localProva;
        this.candidatos = res.data.candidatos;
        this.exame = res.data.exame;
        this.breadcrumbItems.push({
          position: 2,
          text: this.exame[0].titulo,
          disabled: false,
          href: "/coordenador/exames/" + this.exame[0].idExame
        });
        this.breadcrumbItems.push({
          position: 3,
          text : this.locais.nome,
          disabled: true
        });
      });
    },
    mostrarBotaoMoverCandidato(faseExame) {
      if (faseExame === "SELECAO" || faseExame === "AVALIACAO") {
        return false;
      } else {
        return true;
      }
    }
  }
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
