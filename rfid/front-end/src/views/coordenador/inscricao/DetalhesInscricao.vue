<template>
    <v-container fluid grid-list-xl>
        <app-breadcrumbs :items="breadcrumbItems" icon="class" title="Detalhes da Inscrição"></app-breadcrumbs>
        <v-layout row wrap>
            <v-flex xs12>
                <v-widget title="Informações Básicas">
                    <div slot="widget-content">
                        <v-layout row>
                            <v-flex xs12>
                                <span class="title" style="font-weight:bold">{{ inscricao.nome }}</span>
                            </v-flex>
                        </v-layout>
                        <v-divider></v-divider>
                        <v-layout row>
                            <v-flex xs4>
                                <span class="font-weight-bold text-uppercase">Título</span>
                            </v-flex>
                            <v-flex xs4>
                                <span class="font-weight-bold text-uppercase">Fase do exame</span>
                            </v-flex>
                            <v-flex xs4>
                                <span class="font-weight-bold text-uppercase">Data de inscrição</span>
                            </v-flex>
                        </v-layout>
                        <v-layout row>
                            <v-flex xs4>
                                <span class="font-weight-regular">{{ inscricao.titulo }}</span>
                            </v-flex>
                            <v-flex xs4>
                                <span class="font-weight-regular text-uppercase">{{ getLabelFase(inscricao.fase) }}</span>
                            </v-flex>
                            <v-flex xs4>
                                <span class="font-weight-regular">{{ inscricao.dataHoraInscricao | data }}</span>
                            </v-flex>
                        </v-layout>
                        <v-divider></v-divider>
                        <v-layout row>
                            <v-flex xs6>
                                <span class="font-weight-bold text-uppercase">Necessidade Especial: </span>
                                <span class="font-weight-regular text-uppercase">{{ getLabelNecessidade(inscricao.necessidadeEspecial) }}</span>
                            </v-flex>
                            <v-flex xs6>
                                <span class="font-weight-bold text-uppercase">Observação Necessidade: </span>
                                <span class="font-weight-regular">{{ inscricao.observacaoNecessidade }}</span>
                            </v-flex>
                        </v-layout>
                        <v-layout row>
                            <v-flex xs6>
                                <span class="font-weight-bold text-uppercase">PROGRAMA DE PÓS-GRADUAÇÃO: </span>
                                <span class="font-weight-regular">{{ inscricao.programaPos }}</span>
                            </v-flex>
                            <v-flex xs6>
                                <span class="font-weight-bold text-uppercase">Área de concentração: </span>
                                <span class="font-weight-regular">{{ inscricao.areaConcentracao }}</span>
                            </v-flex>
                        </v-layout>
                        <v-layout row>
                            <v-flex xs6>
                                <span class="font-weight-bold text-uppercase">Linha de pesquisa: </span>
                                <span class="font-weight-regular">{{ inscricao.linhaPesquisa }}</span>
                            </v-flex>
                            <v-flex xs6>
                                <span class="font-weight-bold text-uppercase">Instituição: </span>
                                <span class="font-weight-regular">{{ inscricao.instituicao }}</span>
                            </v-flex>
                        </v-layout>

                        <v-layout row>
                            <v-flex xs6>
                                <span class="font-weight-bold text-uppercase">Status da avaliação: </span>
                                <span class="text-uppercase"
                                      v-bind:class="inscricao.statusAvaliacao ? inscricao.statusAvaliacao : ''"
                                >{{ getLabelStatusAvaliacao(inscricao.statusAvaliacao) }}</span>
                            </v-flex>
                            <v-flex xs6>
                                <span class="font-weight-bold text-uppercase">Nota: </span>
                                <span
                                        class="font-weight-regular"
                                >{{ inscricao.reavaliacaoSolicitada ? inscricao.notaReavaliacao : inscricao.notaAvaliacao | nota }}</span>
                            </v-flex>
                        </v-layout>
                    </div>
                </v-widget>
            </v-flex>
        </v-layout>
    </v-container>
</template>


<script>
    import axios from "axios";
    import VWidget from "../../../components/core/VWidget";
    import AppBreadcrumbs from "../../../components/core/AppBreadcrumbs";
    import moment from 'moment';
    import store from "../../../store";

    moment.locale("pt-br");

    export default {
        components: {
            VWidget,
            AppBreadcrumbs,
        },

        data() {
            return {
                inscricaoId: "",
                inscricao: {},
                documentacao: {},
                breadcrumbItems: [
                    {position: 1, text: 'Exames', disabled: false, href: '/coordenador/exames'},
                    
                ]
            };
        },

        filters: {
            data: function (data) {
                return data ? moment(data).format("LLL") : "";
            },
            nota: function (nota) {
                return nota ? Number(nota).toFixed(1) : '';
            }

        },

        mounted: function () {
            this.inscricaoId = this.$route.params.id;
            this.initialize();
        },

        methods: {
            getLabelStatusAvaliacao(status) {
                return store.getters["enums/getLabelStatusAvaliacao"](status);
            },
            getLabelFase(fase) {
                return store.getters["enums/getLabelFase"](fase);
            },
            getLabelNecessidade(necessidade) {
              return store.getters["enums/getLabelNecessidade"](necessidade);
            },
            initialize() {
                axios
                    .get("/inscricoes/" + this.inscricaoId + "/coordenador")
                    .then(res => {
                        this.inscricao = res.data;
                        this.breadcrumbItems.push({
                            position: 2,
                            text: this.inscricao.titulo,
                            href: "/coordenador/exames/" + this.inscricao.exameId
                        });
                        this.breadcrumbItems.push({position: 3, text: this.inscricao.nome, disabled: true})

                    });
            },

        }
    };
</script>

<style>
    h3 {
        color: #3c6781;
        font-weight: 400;
    }

    .APROVADO {
        color: #45e276;
    }

    .REPROVADO {
        color: #ea4c72;
    }

    .EM_AVALIACAO {
        color: #565656;
    }
</style>
