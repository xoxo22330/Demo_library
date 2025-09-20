<template>
  <section style="max-width:420px;margin:auto;padding:20px">
    <h2 style="text-align:center;margin-bottom:24px">註冊</h2>
    <form @submit.prevent="submit" style="display:flex;gap:16px;flex-direction:column">
      <div>
        <label style="display:block;margin-bottom:4px;font-weight:bold;">姓名</label>
        <input 
          v-model="userName" 
          placeholder="請輸入姓名" 
          required 
          style="width:100%;padding:12px;border:1px solid #ddd;border-radius:4px;font-size:16px;"
        />
      </div>
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
      <div v-if="success" style="color:green;text-align:center;padding:8px;background:#d4edda;border-radius:4px;">
        {{ success }}
      </div>
      <button 
        type="submit" 
        :disabled="loading"
        style="width:100%;padding:12px;background:#28a745;color:white;border:none;border-radius:4px;font-size:16px;cursor:pointer;"
        :style="{ backgroundColor: loading ? '#6c757d' : '#28a745' }"
      >
        {{ loading ? '註冊中...' : '註冊' }}
      </button>
    </form>
    <div style="text-align:center;margin-top:16px;">
      <span style="color:#666;">已有帳號？</span>
      <router-link to="/login" style="color:#007bff;text-decoration:none;margin-left:4px;">立即登入</router-link>
    </div>
  </section>
</template>
<script setup>
import { ref } from 'vue'
import { AuthApi } from '../services/api'
import { useRouter } from 'vue-router'

const router = useRouter()
const userName = ref(''), phoneNumber = ref(''), password = ref('')
const loading = ref(false)
const error = ref('')
const success = ref('')

const submit = async () => {
  loading.value = true
  error.value = ''
  success.value = ''
  try {
    await AuthApi.register({ userName: userName.value, phoneNumber: phoneNumber.value, password: password.value })
    success.value = '註冊成功！請登入'
    setTimeout(() => {
      router.push('/login')
    }, 1500)
  } catch (err) {
    error.value = err.response?.data?.message || '註冊失敗，請檢查輸入資料'
    console.error('Register error:', err)
  } finally {
    loading.value = false
  }
}
</script>
