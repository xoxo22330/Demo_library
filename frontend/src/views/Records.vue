<template>
  <section>
    <h2>我的借閱記錄</h2>
    <div style="margin-bottom: 16px; display: flex; gap: 8px; align-items: center;">
      <button @click="load" :disabled="loading" style="padding: 8px 16px; background: #007bff; color: white; border: none; border-radius: 4px;">
        {{ loading ? '載入中...' : '重新整理' }}
      </button>
      <label style="display: flex; align-items: center; gap: 4px;">
        <input type="checkbox" v-model="onlyOpen" @change="load" />
        只顯示未歸還
      </label>
      <span v-if="rows.length > 0" style="color: #666;">共 {{ rows.length }} 筆記錄</span>
    </div>
    
    <div v-if="loading" style="text-align: center; padding: 20px;">載入中...</div>
    <div v-else-if="error" style="color: red; padding: 20px;">{{ error }}</div>
    <table v-else border="1" cellpadding="8" style="margin-top:8px;width:100%;border-collapse: collapse;">
      <thead style="background: #f5f5f5;">
        <tr><th>#</th><th>ISBN</th><th>書名</th><th>借閱時間</th><th>歸還時間</th><th>狀態</th><th>操作</th></tr>
      </thead>
      <tbody>
      <tr v-for="r in rows" :key="r.id" style="border-bottom: 1px solid #ddd;">
        <td style="font-family: monospace;">{{ r.id }}</td>
        <td style="font-family: monospace;">{{ r.isbn }}</td>
        <td style="font-weight: bold;">{{ r.bookName }}</td>
        <td>{{ fmt(r.borrowingTime) }}</td>
        <td>{{ r.returnTime ? fmt(r.returnTime) : '-' }}</td>
        <td>
          <span :style="{ 
            padding: '4px 8px', 
            borderRadius: '4px', 
            fontSize: '12px',
            backgroundColor: r.returnTime ? '#d4edda' : '#fff3cd',
            color: r.returnTime ? '#155724' : '#856404'
          }">
            {{ r.returnTime ? '已歸還' : '借閱中' }}
          </span>
        </td>
        <td>
          <button 
            v-if="!r.returnTime"
            @click="returnBook(r.inventoryId)" 
            :disabled="returning"
            style="padding: 4px 8px; background: #dc3545; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 12px;"
            :style="{ backgroundColor: returning ? '#6c757d' : '#dc3545' }"
          >
            {{ returning ? '歸還中...' : '歸還' }}
          </button>
          <span v-else style="color: #6c757d; font-size: 12px;">無</span>
        </td>
      </tr>
      </tbody>
    </table>
    <div v-if="rows.length === 0 && !loading" style="text-align: center; padding: 20px; color: #666;">
      {{ onlyOpen ? '目前沒有未歸還的書籍' : '沒有借閱記錄' }}
    </div>
  </section>
</template>
<script setup>
import { ref, onMounted, computed } from 'vue'
import { BorrowingApi, auth } from '../services/api'

const rows = ref([])
const loading = ref(false)
const returning = ref(false)
const error = ref('')
const onlyOpen = ref(true)

const load = async () => {
  if (!auth.user) {
    error.value = '請先登入'
    return
  }
  
  loading.value = true
  error.value = ''
  try {
    rows.value = await BorrowingApi.records(auth.user.userId, onlyOpen.value)
  } catch (err) {
    error.value = err.response?.data?.message || '載入失敗'
    console.error('Load records error:', err)
  } finally {
    loading.value = false
  }
}

const returnBook = async (inventoryId) => {
  returning.value = true
  try {
    await BorrowingApi.returnBook(inventoryId)
    alert('歸還成功')
    await load() // 重新載入記錄
  } catch (err) {
    alert(err.response?.data?.message || '歸還失敗')
    console.error('Return book error:', err)
  } finally {
    returning.value = false
  }
}

const fmt = s => new Date(s).toLocaleString()

onMounted(load)
</script>
