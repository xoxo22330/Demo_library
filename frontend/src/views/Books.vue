<template>
  <section>
    <h2>書籍呈現</h2>
    <div style="margin-bottom: 16px; display: flex; gap: 8px; align-items: center;">
      <input v-model="searchQuery" placeholder="搜尋書名" style="padding: 8px; border: 1px solid #ddd; border-radius: 4px; flex: 1;" />
      <input v-model="authorQuery" placeholder="搜尋作者" style="padding: 8px; border: 1px solid #ddd; border-radius: 4px; flex: 1;" />
      <button @click="search" style="padding: 8px 16px; background: #007bff; color: white; border: none; border-radius: 4px;">搜尋</button>
    </div>
    <div v-if="loading" style="text-align: center; padding: 20px;">載入中...</div>
    <div v-else-if="error" style="color: red; padding: 20px;">{{ error }}</div>
    <table v-else border="1" cellpadding="8" style="margin-top:8px;width:100%;border-collapse: collapse;">
      <thead style="background: #f5f5f5;">
        <tr><th>ISBN</th><th>書名</th><th>作者</th><th>簡介</th></tr>
      </thead>
      <tbody>
      <tr v-for="b in rows" :key="b.isbn" style="border-bottom: 1px solid #ddd;">
        <td style="font-family: monospace;">{{ b.isbn }}</td>
        <td style="font-weight: bold;">{{ b.name }}</td>
        <td>{{ b.author }}</td>
        <td style="max-width: 300px; overflow: hidden; text-overflow: ellipsis;">{{ b.introduction || '-' }}</td>
      </tr>
      </tbody>
    </table>
    <div v-if="rows.length === 0 && !loading" style="text-align: center; padding: 20px; color: #666;">
      沒有找到書籍
    </div>
  </section>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { BooksApi } from '../services/api'

const rows = ref([])
const loading = ref(false)
const error = ref('')
const searchQuery = ref('')
const authorQuery = ref('')

const search = async () => {
  loading.value = true
  error.value = ''
  try {
    const r = await BooksApi.list({ 
      q: searchQuery.value, 
      author: authorQuery.value,
      size: 50 
    })
    rows.value = r.content ?? r
  } catch (err) {
    error.value = err.response?.data?.message || '載入失敗'
    console.error('Search error:', err)
  } finally {
    loading.value = false
  }
}

onMounted(search)
</script>
