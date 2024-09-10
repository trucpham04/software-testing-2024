# KIỂM THỬ PHẦN MỀM NHÓM 20

| Tên                | Phiên bản        |
| ------------------ | ---------------- |
| Java               | 17.0.12          |
| MySQl              | 8.4              |
| Eclipse            | 2024-06 (4.32.0) |
| NetBeans           | IDE 22           |

## Eclipse

- Vào `File` > `Import`.
- Chọn `Git` > `Projects from Git`, sau đó nhấn `Next`.
- Chọn `Clone URI`, sau đó nhấn `Next`.
- Nhập URI: `https://github.com/PDT04/software-testing-2024.git`, rồi nhấn `Next`.
- Chọn nhánh `develop`, rồi nhấn `Next`.
- Chọn thư mục nơi bạn muốn clone repository, sau đó nhấn `Next`.
- Nhấn `Next` một lần nữa, rồi nhấn `Finish`.

Xem thêm tại: [Stack overflow](https://stackoverflow.com/a/6760785)

## NetBeans

1. Tạo thư mục dự án mới:

   - Trong thư mục này:
     - Clone repository.
     - Tạo một thư mục trống mới để chứa dự án NetBeans.

2. Trong NetBeans:

   - Vào `File` > `Import Project` > `Eclipse Project`.
   - Chọn `Import Project ignoring Project Dependencies`.
     - *Project to Import*: Chọn thư mục chứa repository đã clone.
     - *Destination Folder*: Chọn thư mục trống để chứa dự án NetBeans.
   - Nhấn `Finish`.

Xem thêm tại: [Stack overflow](https://stackoverflow.com/a/44194507)

## Database

**Tên:** qltv

**File cần đổi username, password:**

- DTO / ConnectDatabase.java
- DATA / DataConnection.java
