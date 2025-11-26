# 📱 LoginBasic – Android App (Kotlin + SQLite + ProGuard)

Ứng dụng Android đơn giản hỗ trợ **Đăng ký / Đăng nhập / Đăng xuất**, sử dụng **SQLite** để lưu trữ người dùng và **ProGuard** cho bản release.

---

## 🚀 Tính năng

### ✔ Đăng ký
- Nhập username & password  
- Kiểm tra trùng username  
- Lưu thông tin vào SQLite

### ✔ Đăng nhập
- Kiểm tra username + password  
- Chuyển sang màn hình Home khi đúng  
- Thông báo lỗi khi sai

### ✔ Đăng xuất
- Xoá trạng thái đăng nhập  
- Quay về màn hình Login

### ✔ SQLite
Bảng `users` gồm:
- `id` (INTEGER, Auto Increment)  
- `username` (TEXT, Unique)  
- `password` (TEXT)

### ✔ ProGuard
- Đã bật `minifyEnabled true` và `shrinkResources true` trong build release  
- Có file `proguard-rules.pro`

## 🛠 Công nghệ sử dụng
- **Kotlin**
- **AndroidX**
- **SQLiteOpenHelper**
- **ConstraintLayout**
- **ProGuard**

---

## 🔧 Cấu hình ProGuard (Release Build)

Trong `app/build.gradle`:

```gradle
buildTypes {
    release {
        minifyEnabled true
        shrinkResources true
        proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
    }
}

```
- [Link tải apk](https://drive.google.com/file/d/14wyYNflOGlOmhaDRvktFtjMm_A0DtuI8/view?usp=drive_link)