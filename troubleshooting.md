# Troubleshooting CI/CD Pipeline

## 1. Lỗi Permission Denied — Gradle Wrapper

**Hiện tượng:**

GitHub Actions không thể thực thi lệnh `./gradlew`, thường xuất hiện thông báo `Permission denied`.

**Nguyên nhân:**

File `gradlew` chưa được cấp quyền thực thi trên Linux Runner của GitHub Actions.

**Cách khắc phục:**

Thêm bước sau trước khi thực thi Gradle:

```yaml
- name: Fix Gradle Wrapper permission
  run: chmod +x gradlew
```

Sau đó chạy kiểm thử bằng:

```bash
./gradlew clean test bootJar
```

**Kết quả mong đợi:**

Gradle Wrapper có thể thực thi và các tác vụ build, test được chạy bình thường.

## 2. Lỗi Authentication Failed — Docker Registry

**Hiện tượng:**

Bước đăng nhập Docker Registry thất bại do thông tin xác thực không hợp lệ hoặc không được cung cấp.

**Nguyên nhân:**

Workflow tham chiếu sai tên GitHub Secret hoặc Secret chưa được cấu hình trong repository.

**Cách khắc phục:**

Tạo các repository secrets `REGISTRY_USERNAME` và `REGISTRY_TOKEN` trong Settings → Secrets and variables → Actions.

Sử dụng đúng tên biến:

```yaml
- name: Login to Docker Hub
  uses: docker/login-action@v3
  with:
    username: ${{ secrets.REGISTRY_USERNAME }}
    password: ${{ secrets.REGISTRY_TOKEN }}
```

**Kết quả mong đợi:**

GitHub Actions đăng nhập Docker Hub thành công bằng thông tin xác thực được lưu trong Secrets.

## 3. Lỗi Build Context Error — Dockerfile

**Hiện tượng:**

Docker build không tìm thấy Dockerfile hoặc không tìm thấy các tệp cần thiết để build image.

**Nguyên nhân:**

Đường dẫn Dockerfile hoặc build context không khớp với cấu trúc thư mục của project.

**Cách khắc phục:**

Đặt Dockerfile tại thư mục gốc project và cấu hình:

```yaml
- name: Build and Push Docker Image
  uses: docker/build-push-action@v5
  with:
    context: .
    file: ./Dockerfile
    push: true
    tags: ${{ secrets.REGISTRY_USERNAME }}/springboot-ci-demo:latest
```

**Kết quả mong đợi:**

Docker tìm thấy Dockerfile, sử dụng thư mục gốc làm build context và build/push image thành công.

## 4. Kết luận

Ba lỗi CI/CD được xử lý lần lượt bằng cách cấp quyền thực thi cho Gradle Wrapper, sử dụng đúng Docker Registry Secrets và cấu hình chính xác Docker build context cùng đường dẫn Dockerfile.

Pipeline chỉ được xác nhận hoàn thành khi các job bắt buộc đều thành công và GitHub Actions hiển thị trạng thái tích xanh.
