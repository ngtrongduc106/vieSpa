package com.viespa.utils;

import com.viespa.models.Course;
import com.viespa.models.Customer;
import com.viespa.models.Staff;
import com.viespa.models.Transaction;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class ContractUtil {
    public static void print(Transaction transaction) throws Exception {

        BufferedWriter writer = null;
        Customer customer = Customer.getByName(transaction.getCustomer());
        Course course = Course.getByName(transaction.getCourse());
        Staff staff = Staff.getById(transaction.getStaff());

        try {
            writer = new BufferedWriter(new FileWriter("..\\contracts\\contract_" + transaction.getId() + ".html"));

            String head = String.format("<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\"><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><meta name=\"viewport\" content=\"width=device-width,initial-scale=1\"><title>Contract - %s</title><style>.container{width:800px;margin:auto}.center{text-align:center}.sign{display:flex;justify-content:space-around}.sign p{margin-top:7em}</style></head>",
                    transaction.getId());
            writer.write(head);

            String header = String.format("<body><div class=\"container\"><h3 class=\"center\">CỘNG HOÀ XÃ HỘI CHỦ NGHĨA VIỆT NAM</h3><h4 class=\"center\">Độc lập - Tự do - Hạnh phúc</h4><h1 class=\"center\">HỢP ĐỒNG DỊCH VỤ</h1><p class=\"center\">Số: %s /HĐDV</p><p>Căn cứ Bộ Luật dân sự số 91/2015/QH13 ngày 24/11/2015;</p><p>Căn cứ …</p><p>Căn cứ nhu cầu và khả năng thực tế của các bên trong hợp đồng;</p>",
                    transaction.getId()
            );
            writer.write(header);

            String benA = String.format("<p>Hôm nay, ngày %s , tại ... chúng tôi gồm có:</p><p>Bên sử dụng dịch vụ (sau đây gọi tắt là bên A):</p><p>Họ và tên: %s </p><p>Năm sinh: %s </p><p>Chỗ ở hiện tại: %s </p><p>Điện thoại: %s </p><p>Email: %s </p>",
                    DateUtil.convert(String.valueOf(transaction.bookingProperty().getValue())).getValue(),
                    customer.getFullName(),
                    customer.getDob(),
                    customer.getAddress(),
                    customer.getPhone(),
                    customer.getEmail()
            );
            writer.write(benA);

            assert staff != null;
            String benB = String.format("<p>Bên cung ứng dịch vụ (sau đây gọi tắt là bên B):</p><p>Tên tổ chức: Công ty TNHH nhiều thành viên VieSpa</p><p>Địa chỉ trụ sở: 265 Đội Cấn - Ba Đình - Hà Nội</p><p>Mã số doanh nghiệp: XXXXXXXXXXXXXXX</p><p>Người đại diện theo pháp luật là ông/ bà: %s </p><p>Chức vụ: %s </p><p>Điện thoại: %s </p><p>Email: %s </p><p>Hai bên thoả thuận và đồng ý ký kết hợp đồng dịch vụ với các điều khoản như sau:</p>",
                    staff.getFullname(),
                    staff.getRole(),
                    staff.getPhone(),
                    staff.getEmail()
            );
            writer.write(benB);

            String courseInfo = String.format("<h4>Điều 1. Đối tượng của hợp đồng</h4><p>Theo yêu cầu của bên A về việc thực hiện dịch vụ %s , bên B đảm nhận và thực hiện %s </p><h4>Điều 2. Thời hạn thực hiện hợp đồng</h4><p>Hợp đồng này được thực hiện kể từ ngày %s </p><p>Thời gian dự kiến hoàn thành: là 30 ngày, kể từ ngày %s </p>",
                    course.getName(),
                    course.getDescription(),
                    DateUtil.convert(String.valueOf(transaction.bookingProperty().getValue())),
                    DateUtil.convert(String.valueOf(transaction.bookingProperty().getValue())));
            writer.write(courseInfo);

            String data = "<h4>Điều 3. Quyền, nghĩa vụ của bên A</h4><h5>1. Quyền của Bên A:</h5><p>Yêu cầu bên B thực hiện công việc theo đúng chất lượng, số lượng, thời hạn, địa điểm thỏa thuận tại hợp đồng này.</p><p>Trường hợp bên B vi phạm nghiêm trọng nghĩa vụ thì bên A có quyền đơn phương chấm dứt thực hiện hợp đồng và yêu cầu bồi thường thiệt hại.</p><h5>2. Nghĩa vụ của bên A:</h5><p>Cung cấp cho bên B thông tin, tài liệu và các phương tiện cần thiết để thực hiện công việc, nếu có thỏa thuận hoặc việc thực hiện công việc đòi hỏi.</p><p>Trả tiền dịch vụ cho bên B theo thỏa thuận tại hợp đồng này.</p><h4>Điều 4. Quyền và nghĩa vụ của bên B:</h4><h5>1. Quyền của bên B:</h5><p>Yêu cầu bên A cung cấp thông tin, tài liệu và phương tiện để thực hiện công việc.</p><p>Được thay đổi điều kiện dịch vụ vì lợi ích của bên A mà không nhất thiết phải chờ ý kiến của bên A, nếu việc chờ ý kiến sẽ gây thiệt hại cho bên A, nhưng phải báo ngay cho bên A.</p><p>Yêu cầu bên A trả tiền dịch vụ</p><h5>2. Nghĩa vụ của bên A:</h5><p>Thực hiện công việc đúng chất lượng, số lượng, thời hạn, địa điểm thỏa thuận tại hợp đồng này.</p><p>Không được giao cho người khác thực hiện thay công việc nếu không có sự đồng ý bằng văn bản của bên A.</p><p>Báo ngay cho bên B về việc thông tin, tài liệu không đầy đủ, phương tiện không bảo đảm chất lượng để hoàn thành công việc.</p><p>Giữ bí mật thông tin mà mình biết được trong thời gian thực hiện công việc.</p>";
            writer.write(data);

            String price = "<h4>Điều 5. Tiền dịch vụ và phương thức thanh toán:</h4><p>1. Tiền dịch vụ: Thực hiện công việc tại Điều 1 là: " + transaction.getPay() + ", chưa bao gồm tiền thuế giá trị gia tăng.</p><p>2. Phương thức thanh toán: chuyển khoản 100%</p><h4>Điều 6. Chi phí khác</h4><p>Không được phép phát sinh thêm chi phí khác</p>";
            writer.write(price);

            String data2 = "<h4>Điều 7. Đơn phương chấm dứt thực hiện hợp đồng dịch vụ</h4><p>1. Trường hợp việc tiếp tục thực hiện công việc không có lợi cho bên A thì bên A có quyền đơn phương chấm dứt thực hiện hợp đồng, nhưng phải báo cho bên B biết trước 14 ngày. Bên A phải trả tiền dịch vụ theo phần dịch vụ mà bên B đã thực hiện và bồi thường thiệt hại.</p><p>2. Trường hợp bên A vi phạm nghiêm trọng nghĩa vụ thì bên B có quyền đơn phương chấm dứt thực hiện hợp đồng và yêu cầu bồi thường thiệt hại.</p><h4>Điều 8. Phương thực giải quyết tranh chấp</h4><p>Trong quá trình thực hiện hợp đồng, nếu có vấn đề phát sinh cần giải quyết, thì hai bên tiến hành thỏa thuận và thống nhất giải quyết kịp thời, hợp tình và hợp lý. Trường hợp không thỏa thuận được thì một trong các bên có quyền khởi kiện tại tòa án có thẩm quyền theo quy định của pháp luật.</p><h4>Điều 9. Các thoả thuận khác</h4><p>Bên A và bên B đồng ý đã hiểu rõ quyền, nghĩa vụ, lợi ích hợp pháp của mình và hậu quả pháp lý của việc giao kết hợp đồng này.</p><p>Bên A và bên B đồng ý thực hiện theo đúng các điều khoản trong hợp đồng này và không nêu thêm điều kiện gì khác.</p><p>Hợp đồng này được lập thành 02 bản, có giá trị pháp lý như nhau và được giao cho bên A 01 bản, bên B 01 bản./.</p>";
            writer.write(data2);

            String sign = String.format("<div class=\"sign\"><div><h4>BÊN B</h4><div>LOGO_SPA</div></div><div><h5>Hà Nội, ngày %s </h5><h4 class=\"center\">BÊN A</h4><p class=\"center\">%s</p></div></div></div></body><script>window.print()</script></html>",
                    DateUtil.convert(String.valueOf(transaction.bookingProperty().getValue())),
                    customer.getFullName());
            writer.write(sign);

        } catch (Exception e) {
            throw new Exception("FILE NOT FOUND");
        } finally {
            try {
                assert writer != null;
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
