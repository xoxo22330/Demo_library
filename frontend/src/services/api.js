import axios from 'axios'

// ❶ 登入狀態（具名匯出給 App.vue / router / 各頁使用）
export const auth = {
    get token(){ return localStorage.getItem('token') },
    set token(t){ t ? localStorage.setItem('token', t) : localStorage.removeItem('token') },
    get user(){ const j = localStorage.getItem('user'); return j ? JSON.parse(j) : null },
    set user(u){ u ? localStorage.setItem('user', JSON.stringify(u)) : localStorage.removeItem('user') },
}

// ❷ API client：開發用 Vite 代理 /api，打包後同源也走 /api
const baseURL = import.meta.env.DEV ? '/api' : (import.meta.env.VITE_API || '/api')
const api = axios.create({ baseURL })

// 自動帶 JWT
api.interceptors.request.use(cfg => {
    if (auth.token) cfg.headers.Authorization = `Bearer ${auth.token}`
    return cfg
})

// 響應攔截器處理錯誤
api.interceptors.response.use(
    response => response,
    error => {
        if (error.response?.status === 401) {
            auth.token = null
            auth.user = null
            window.location.href = '/login'
        }
        return Promise.reject(error)
    }
)

// ❸ 各服務
export const AuthApi = {
    register: (dto) => api.post('/auth/register', dto).then(r => r.data),
    login:    (dto) => api.post('/auth/login', dto).then(r => r.data), // 回 {token, user:{userId,userName}}
}

export const InventoryApi = {
    available: () => api.get('/inventory/available').then(r => r.data),
    all: (params = {}) => api.get('/inventory', { params }).then(r => r.data),
    addCopy:   (isbn) => api.post('/inventory', null, { params:{ isbn } }).then(r => r.data),
}

export const BooksApi = {
    list: (p={}) => api.get('/books', { params: p }).then(r => r.data),
    create: (b) => api.post('/books', b).then(r => r.data),
    remove: (isbn) => api.delete(`/books/${isbn}`),
}

export const BorrowingApi = {
    borrow:     (inventoryId) => api.post('/borrowing/borrow', { inventoryId }).then(r => r.data),
    records:    (userId, onlyOpen=true) => api.get('/borrowing/records', { params:{ userId, onlyOpen } }).then(r => r.data),
    returnBook: (inventoryId) => api.post('/borrowing/return', { inventoryId }).then(r => r.data),
}

export default api
