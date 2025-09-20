<template>
  <div class="app">
    <nav style="display:flex;gap:16px;padding:16px;border-bottom:1px solid #e5e7eb;background:#f8f9fa;align-items:center;">
      <div style="font-weight:bold;color:#007bff;font-size:18px;">圖書館管理系統</div>
      <RouterLink to="/inventory" style="padding:8px 16px;text-decoration:none;color:#333;border-radius:4px;transition:background 0.2s;" 
                  :style="{ backgroundColor: $route.path === '/inventory' ? '#e9ecef' : 'transparent' }">可借清單</RouterLink>
      <RouterLink to="/books" style="padding:8px 16px;text-decoration:none;color:#333;border-radius:4px;transition:background 0.2s;"
                  :style="{ backgroundColor: $route.path === '/books' ? '#e9ecef' : 'transparent' }">書籍呈現</RouterLink>
      <RouterLink to="/records" style="padding:8px 16px;text-decoration:none;color:#333;border-radius:4px;transition:background 0.2s;"
                  :style="{ backgroundColor: $route.path === '/records' ? '#e9ecef' : 'transparent' }">我的借閱</RouterLink>
      <span style="flex:1"></span>
      <template v-if="me">
        <span style="opacity:.8;margin-right:8px;">Hi, {{ me.userName }}</span>
        <button @click="logout" style="border:1px solid #dc3545;padding:8px 16px;border-radius:4px;background:#fff;color:#dc3545;cursor:pointer;transition:all 0.2s;"
                onmouseover="this.style.backgroundColor='#dc3545';this.style.color='white'"
                onmouseout="this.style.backgroundColor='#fff';this.style.color='#dc3545'">登出</button>
      </template>
      <template v-else>
        <RouterLink to="/login" style="padding:8px 16px;text-decoration:none;color:#007bff;border:1px solid #007bff;border-radius:4px;transition:all 0.2s;"
                    onmouseover="this.style.backgroundColor='#007bff';this.style.color='white'"
                    onmouseout="this.style.backgroundColor='transparent';this.style.color='#007bff'">登入</RouterLink>
        <RouterLink to="/register" style="padding:8px 16px;text-decoration:none;color:#28a745;border:1px solid #28a745;border-radius:4px;transition:all 0.2s;"
                    onmouseover="this.style.backgroundColor='#28a745';this.style.color='white'"
                    onmouseout="this.style.backgroundColor='transparent';this.style.color='#28a745'">註冊</RouterLink>
      </template>
    </nav>
    <main style="padding:24px;max-width:1200px;margin:auto;min-height:calc(100vh - 80px);">
      <RouterView />
    </main>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { auth } from './services/api'

const me = computed(() => auth.user)

const logout = () => { 
  auth.token = null
  auth.user = null
  location.assign('/login') 
}
</script>

