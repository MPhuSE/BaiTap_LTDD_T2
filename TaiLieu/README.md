# Tài liệu Bài tập Tuần 2 - Lập trình Di động

## Mục tiêu
- Xây dựng ứng dụng Android sử dụng Jetpack Compose với 4 bài thực hành:
  1. **Bài 1**: Máy tính điện tử (Calculator).
  2. **Bài 2**: Xử lý Nullable và danh sách sinh viên.
  3. **Bài 3**: Phân loại điểm số (If/Else & Color mapping).
  4. **Bài 4**: Tổng hợp điểm số (Scoreboard).

## Kết quả đạt được & Giải thích hàm
- **Máy tính (Calculator)**:
  - Hàm `formatNumber`: Định dạng số thực, loại bỏ số 0 thừa phía sau.
  - Xử lý nhập liệu và thực hiện 4 phép tính cơ bản (+, -, *, /).
- **Xử lý Nullable**:
  - Sử dụng toán tử Elvis `?:` để xử lý giá trị `null` (ví dụ: `student.email ?: "Chưa cập nhật"`).
- **Phân loại điểm số**:
  - Hàm `classify(score: Double)`: Trả về xếp loại (Giỏi, Khá, Trung bình, Yếu, Không hợp lệ).
  - Hàm `classificationColor(score: Double)`: Trả về màu sắc tương ứng với xếp loại.
- **Tổng hợp**:
  - Hiển thị danh sách điểm sinh viên dạng Compose.
