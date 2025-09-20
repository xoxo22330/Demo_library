# 圖書館管理系統 (Library Management System)

一個基於 Spring Boot 後端和 Vue.js 前端的現代化圖書館管理系統，提供書籍管理、借閱記錄、用戶認證等功能。

## 技術

### 後端
- **Java 17**
- **Spring Boot 3.5.5**
- **Spring Security** - JWT 認證
- **Spring Data JPA** - 數據持久化
- **MariaDB** - 關係型數據庫
- **Maven** - 依賴管理

### 前端
- **Vue.js 3** - 前端框架
- **Vue Router** - 路由管理
- **Axios** - HTTP 客戶端
- **Vite** - 構建工具

## 功能特色

### 用戶管理
- 用戶註冊與登入
- JWT Token 認證
- 自動登出機制
- 用戶信息顯示

### 書籍管理
- 書籍庫存查看
- 書籍狀態管理（可借/已借）
- ISBN 管理
- 書籍詳細信息

### 借閱系統
- 書籍借閱功能
- 借閱記錄查詢
- 書籍歸還功能
- 借閱狀態追蹤

### 用戶界面
- 響應式設計
- 現代化 UI 組件
- 狀態指示器
- 載入動畫
- 錯誤處理

## 安裝與運行

### 環境要求
- Java 17+
- Node.js 16+
- MariaDB 10.3+
- Maven 3.6+

### 數據庫設置
1. 安裝並啟動 MariaDB
2. 創建數據庫：
```sql
CREATE DATABASE Demo_library;
```

### 後端啟動
```bash
# 在項目根目錄
mvn spring-boot:run
```
後端服務將在 `http://localhost:8080` 啟動

### 前端啟動
```bash
# 進入前端目錄
cd frontend

# 安裝依賴
npm install

# 啟動開發服務器
npm run dev
```
前端應用將在 `http://localhost:5173` 啟動

## 📁 項目結構

```
Demo_library/
├── src/main/java/org/example/demo_library/
│   ├── controller/          # REST API 控制器
│   ├── domain/             # 實體類和 DTO
│   ├── repository/         # 數據訪問層
│   ├── service/           # 業務邏輯層
│   ├── security/          # 安全配置
│   └── web/               # Web 配置
├── frontend/
│   ├── src/
│   │   ├── components/    # Vue 組件
│   │   ├── views/         # 頁面視圖
│   │   ├── services/      # API 服務
│   │   └── router/        # 路由配置
│   └── public/            # 靜態資源
└── DB/                    # 數據庫腳本
```

## 🔧 API 端點

### 認證相關
- `POST /api/auth/register` - 用戶註冊
- `POST /api/auth/login` - 用戶登入

### 庫存管理
- `GET /api/inventory` - 獲取所有庫存
- `GET /api/inventory/available` - 獲取可借書籍
- `POST /api/inventory` - 添加書籍副本

### 借閱管理
- `POST /api/borrowing/borrow` - 借閱書籍
- `POST /api/borrowing/return` - 歸還書籍
- `GET /api/borrowing/records` - 獲取借閱記錄

## 使用指南

### 用戶操作流程
1. **註冊/登入**：首次使用需要註冊帳號
2. **瀏覽書籍**：在「可借清單」頁面查看可用書籍
3. **借閱書籍**：點擊「借書」按鈕進行借閱
4. **查看記錄**：在「我的借閱」頁面查看借閱歷史
5. **歸還書籍**：在借閱記錄中點擊「歸還」按鈕

### 管理員功能
- 添加新書籍到庫存
- 管理用戶借閱記錄
- 查看系統統計信息

## 安全特性

- JWT Token 認證
- 密碼加密存儲
- API 端點保護
- 跨域請求處理
- 輸入驗證

## 故障排除

### 常見問題
1. **端口衝突**：確保 8080 和 5173 端口未被占用
2. **數據庫連接**：檢查 MariaDB 服務是否運行
3. **依賴問題**：運行 `mvn clean install` 和 `npm install`

### 日誌查看
- 後端日誌：控制台輸出
- 前端日誌：瀏覽器開發者工具

## 開發說明

### 程式碼風格
- 後端遵循 Spring Boot 最佳實踐
- 前端使用 Vue 3 Composition API
- 統一的錯誤處理機制

### 數據庫設計
- 用戶表 (users)
- 書籍表 (books)
- 庫存表 (inventory)
- 借閱記錄表 (borrowing_records)

**注意**：本系統僅供學習和演示使用，生產環境使用前請進行充分的安全測試。
