import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '../components/HelloWorld'
//import HomePage from '../components/Home'
import CadastroAtividadePage from '../page/CadastroAtividade' 
import CadastroOkPage from '../page/CadastroOk'
//import FirstRoute from '@/components/FirstRoute'
//import FirstRouteChild from '@/components/FirstRouteChild'

Vue.use(Router)

export default [
  {
    path: "/",
    name: "HelloWorld",
    component: HelloWorld
  },
  {
    path: "/cadastro",
    name: "CadastroAtividade",
    component: CadastroAtividadePage
    },
  {
    path: "/cadastro-ok",
    name: "CadastroOk",
    component: CadastroOkPage
  },
];