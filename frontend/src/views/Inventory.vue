<template>
  <section>
    <h2>可借清單</h2>
    <div style="margin-bottom: 16px; display: flex; gap: 8px; align-items: center;">
      <button @click="refresh" :disabled="loading" style="padding: 8px 16px; background: #28a745; color: white; border: none; border-radius: 4px;">
        {{ loading ? '載入中...' : '重新整理' }}
      </button>
      <span v-if="list.length > 0" style="color: #666;">共 {{ list.length }} 本可借書籍</span>
    </div>

    <div v-if="loading" style="text-align: center; padding: 20px;">載入中...</div>
    <div v-else-if="error" style="color: red; padding: 20px;">{{ error }}</div>
    <table v-else border="1" cellpadding="8" style="margin-top:8px;width:100%;border-collapse: collapse;">
      <thead style="background: #f5f5f5;">
      <tr><th>ID</th><th>ISBN</th><th>書名</th><th>狀態</th><th>操作</th></tr>
      </thead>
      <tbody>
      <tr v-for="i in list" :key="i.inventoryId" style="border-bottom: 1px solid #ddd;">
        <td style="font-family: monospace;">{{ i.inventoryId }}</td>
        <td style="font-family: monospace;">{{ i.isbn }}</td>
        <td style="font-weight: bold;">{{ i.bookName }}</td>
        <td>
          <span :style="{
            padding: '4px 8px',
            borderRadius: '4px',
            fontSize: '12px',
            backgroundColor: i.status === 'AVAILABLE' ? '#d4edda' : '#f8d7da',
            color: i.status === 'AVAILABLE' ? '#155724' : '#721c24'
          }">
            {{ i.status === 'AVAILABLE' ? '可借' : i.status }}
          </span>
        </td>
        <td>
          <button
              @click="borrow(i.inventoryId)"
              :disabled="i.status !== 'AVAILABLE' || borrowing"
              :style="{
              padding: '6px 12px',
              border: 'none',
              borderRadius: '4px',
              backgroundColor: i.status === 'AVAILABLE' && !borrowing ? '#007bff' : '#6c757d',
              color: 'white',
              cursor: i.status === 'AVAILABLE' && !borrowing ? 'pointer' : 'not-allowed'
            }"
          >
            {{ !me ? '登入後可借' : (i.status === 'AVAILABLE' ? '借書' : '不可借') }}
          </button>
        </td>
      </tr>
      </tbody>
    </table>
    <div v-if="list.length === 0 && !loading" style="text-align: center; padding: 20px; color: #666;">
      目前沒有可借的書籍
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { InventoryApi, BorrowingApi, auth } from '../services/api'

const router = useRouter(), route = useRoute()
const list = ref([])
const loading = ref(false)
const borrowing = ref(false)
const error = ref('')
const me = computed(() => auth.user)

const refresh = async () => {
  loading.value = true
  error.value = ''
  try {
    list.value = await InventoryApi.available()
  } catch (err) {
    error.value = err.response?.data?.message || '載入失敗'
    console.error('Refresh error:', err)
  } finally {
    loading.value = false
  }
}

const borrow = async (inventoryId) => {
  if (!me.value) return router.push({ path:'/login', query:{ redirect: route.fullPath } })

  borrowing.value = true
  try {
    await BorrowingApi.borrow(inventoryId)
    alert('借書成功')
    await refresh()
  } catch (err) {
    alert(err.response?.data?.message || '借書失敗')
    console.error('Borrow error:', err)
  } finally {
    borrowing.value = false
  }
}

onMounted(refresh)
</script>
