import { createRouter, createWebHistory } from 'vue-router'
import Inventory from '../views/Inventory.vue'
import Books from '../views/Books.vue'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import Records from '../views/Records.vue'

const routes = [
    { path: '/', redirect: '/inventory' },
    { path: '/books', component: Books },
    { path: '/inventory', component: Inventory },
    { path: '/records', component: Records },
    { path: '/login', component: Login },
    { path: '/register', component: Register },
]

export default createRouter({ history: createWebHistory(), routes })
