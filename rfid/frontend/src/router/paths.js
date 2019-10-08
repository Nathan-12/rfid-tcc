import axios from 'axios'

import HomePage from '../views/Home'


export default [
    
    {
        path: '/home',
        meta: {
            public: true
        },
        props: true,
        name: 'Home',
        component: HomePage
    }
    

];