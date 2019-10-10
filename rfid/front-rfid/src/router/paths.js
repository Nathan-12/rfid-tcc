import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '../components/HelloWorld'
import HomePage from '../components/Home'
//import FirstRoute from '@/components/FirstRoute'
//import FirstRouteChild from '@/components/FirstRouteChild'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: HelloWorld
    },
    {   
        path: '/home',
        name: 'Home',
        component: HomePage
      },
  ]
})