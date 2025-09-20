<template>
  <section style="max-width:420px;margin:auto;padding:20px">
    <h2 style="text-align:center;margin-bottom:24px">登入</h2>
    <form @submit.prevent="submit" style="display:flex;gap:16px;flex-direction:column">
      <div>
        <label style="display:block;margin-bottom:4px;font-weight:bold;">手機號碼</label>
        <input 
          v-model="phoneNumber" 
          placeholder="請輸入手機號碼" 
          required 
          style="width:100%;padding:12px;border:1px solid #ddd;border-radius:4px;font-size:16px;"
        />
      </div>
      <div>
        <label style="display:block;margin-bottom:4px;font-weight:bold;">密碼</label>
        <input 
          v-model="password" 
          type="password" 
          placeholder="請輸入密碼" 
          required 
          style="width:100%;padding:12px;border:1px solid #ddd;border-radius:4px;font-size:16px;"
        />
      </div>
      <div v-if="error" style="color:red;text-align:center;padding:8px;background:#f8d7da;border-radius:4px;">
        {{ error }}
      </div>
      <button 
        type="submit" 
        :disabled="loading"
        style="width:100%;padding:12px;background:#007bff;color:white;border:none;border-radius:4px;font-size:16px;cursor:pointer;"
        :style="{ backgroundColor: loading ? '#6c757d' : '#007bff' }"
      >
        {{ loading ? '登入中...' : '登入' }}
      </button>
    </form>
    <div style="text-align:center;margin-top:16px;">
      <span style="color:#666;">還沒有帳號？</span>
      <router-link to="/register" style="color:#007bff;text-decoration:none;margin-left:4px;">立即註冊</router-link>
    </div>
  </section>
</template>
<script setup>
import { ref } from 'vue'
import { AuthApi, auth } from '../services/api'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute(), router = useRouter()
const phoneNumber = ref(''), password = ref('')
const loading = ref(false)
const error = ref('')

const submit = async () => {
  loading.value = true
  error.value = ''
  try {
    const res = await AuthApi.login({ phoneNumber: phoneNumber.value, password: password.value })
    auth.token = res.token
    auth.user = res.user
    router.replace(route.query.redirect?.toString() || '/')
  } catch (err) {
    error.value = err.response?.data?.message || '登入失敗，請檢查帳號密碼'
    console.error('Login error:', err)
  } finally {
    loading.value = false
  }
}
</script>
