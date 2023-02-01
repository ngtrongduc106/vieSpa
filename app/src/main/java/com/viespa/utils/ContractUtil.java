package com.viespa.utils;

import com.viespa.models.Course;
import com.viespa.models.Customer;
import com.viespa.models.Staff;
import com.viespa.models.Transaction;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class ContractUtil {

    private static final String logo = "<svg fill=\"#A31ACB\" version=\"1.1\" id=\"Capa_1\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\"70px\" height=\"70px\" viewBox=\"0 0 959.199 959.199\" xml:space=\"preserve\"><g><path d=\"M788.143,259.2c-16.699-44.2-41.399-81.7-73.399-111.5c-32.7-30.5-73-52.8-120-66.5c0,0,0,0-0.101-0.1\n" +
            "\t\tc-52.3-52.3-121.9-81.1-195.9-81.1s-143.5,28.8-195.8,81.1c-52.3,52.3-81.1,121.9-81.1,195.9c0,18.9,1.9,37.5,5.6,55.7\n" +
            "\t\tc-14.3,41.7-20.8,85.9-19.4,132.101c2.4,78.5,27.9,160.899,75.6,244.899c0.1,0.102,0.2,0.301,0.3,0.4\n" +
            "\t\tc18.6,140.6,138.9,249.1,284.6,249.1c128.3,0,237-84.199,273.8-200.398c9.8-7.5,19.101-15.602,27.9-24.5\n" +
            "\t\tc52.3-52.301,81.1-121.9,81.1-195.9c0-50.9-13.6-99.701-39.1-142.201C812.443,370.3,809.843,316.5,788.143,259.2z M597.643,705.199\n" +
            "\t\tc-33.5,0-60.6-27.1-60.6-60.6s27.1-60.6,60.6-60.6s60.601,27.1,60.601,60.6S631.043,705.199,597.643,705.199z M630.843,435.2h145.9\n" +
            "\t\tc16.2,31.601,24.7,66.701,24.7,103.201c0,50-16.101,97.5-45.801,136.699c0-1,0-2.1,0-3.1\n" +
            "\t\tC755.643,573.699,706.143,486.9,630.843,435.2z M762.143,385.2h-20.3c-76.8,0-153.399-1.2-230.3-1.3\n" +
            "\t\tc-55.8,0-112.901,5-163.901,27.7c-83.399,37.1-133.399,117.099-155.1,203c-1.2-2.9-2.3-5.799-3.5-8.699\n" +
            "\t\tc-19.2-49-29.7-96.801-31.1-142.701c-0.5-15.7,0.1-31.1,1.7-46.2c1.5-14,3.9-27.8,7.2-41.3c3.3-13.6,7.5-26.8,12.6-39.8\n" +
            "\t\tc5.8-14.6,12.7-28.9,20.7-42.7c30.8-52.9,77.3-97.7,134.6-129.4c53.399-29.6,113.399-45.9,168.8-45.9c17.6,0,34.6,1.6,50.6,4.9\n" +
            "\t\tc4.7,1,9.4,2,13.9,3.1c14.7,3.6,28.7,8.2,41.8,13.6c14.4,6,27.9,13,40.3,21.2c41.301,26.9,71.7,65.5,90.801,115.6\n" +
            "\t\tc7,18.4,11.699,36.5,14.899,53.1c2.9,14.9,4.5,28.6,5.3,40.2C761.743,375.2,762.043,380.5,762.143,385.2z M398.842,50\n" +
            "\t\tc31.2,0,61.4,6.3,89.3,18.2c-59.3,2.7-121.7,20.9-177.5,51.8c-55,30.5-101.399,71.7-135.8,120.4c7.5-46.7,29.4-89.7,63.5-123.8\n" +
            "\t\tC281.142,73.6,338.142,50,398.842,50z M342.443,705.199c-33.5,0-60.601-27.1-60.601-60.6c0-18.799,8.5-35.5,21.9-46.6\n" +
            "\t\tc6.7-5.5,14.5-9.6,23.1-11.9c5-1.299,10.2-2,15.5-2c3.3,0,6.5,0.301,9.7,0.801c28.9,4.6,50.9,29.6,50.9,59.799\n" +
            "\t\tc0,11.602-3.301,22.5-8.9,31.701c-4.5,7.299-10.4,13.5-17.4,18.299c-7.1,4.9-15.3,8.301-24.1,9.701\n" +
            "\t\tC349.342,704.9,345.943,705.199,342.443,705.199z M515.143,874.199c-5.899,13.201-23.8,22.801-45.1,22.801\n" +
            "\t\tc-21.3,0-39.3-9.6-45.1-22.801c-2.101-4.6,3-9.299,10.1-9.299h70.201C512.243,864.9,517.243,869.5,515.143,874.199z M505.143,839.5\n" +
            "\t\th-70.2c-7.101,0-12.101-4.699-10.101-9.301c4.9-11.1,14-19.6,30.3-22.1c2-0.299,4,0,5.9,0.9l4.5,2.199c2.8,1.4,6.1,1.4,8.9,0\n" +
            "\t\tl4.399-2.199c1.8-0.9,3.901-1.199,5.901-0.9c16.3,2.4,25.399,11,30.3,22.1C517.243,834.9,512.243,839.5,505.143,839.5z\"/><path d=\"M386.542,665.9c2.1-3.4,2.8-7.701,1.6-11.9c-2-6.9-8.399-11.199-15.199-10.9c-1.101,0.1-2.2,0.201-3.301,0.5\n" +
            "\t\tc-7.1,2-14.699,3.201-22.6,3.5c-1.2,0-2.4,0.1-3.6,0.1c-8.801,0-17.4-1.1-25.301-3.299c-1.399-0.4-2.699-0.801-4-1.201\n" +
            "\t\tc-7.899-2.6-16.3,1.701-18.899,9.5c-2.601,7.9,1.7,16.301,9.5,18.9c9.399,3.1,19.3,5,29.7,5.701c3,0.199,6,0.299,9,0.299\n" +
            "\t\tc6.699,0,13.3-0.5,19.699-1.5c5-0.799,9.9-1.799,14.7-3.199C381.642,671.5,384.642,669.1,386.542,665.9z\"/><path d=\"M625.643,643.801c-8.199,2.299-17,3.5-26.199,3.5c-10.301,0-20.4-1.602-29.301-4.5c-7.899-2.602-16.3,1.699-18.899,9.5\n" +
            "\t\tc-2.601,7.898,1.7,16.299,9.5,18.898c12.1,4,25.1,6,38.7,6c12,0,23.5-1.6,34.399-4.699c8-2.301,12.601-10.6,10.3-18.5\n" +
            "\t\tC641.943,646.1,633.643,641.5,625.643,643.801z\"/></g></svg><p style=\"color:#a31acb;text-align:center\">VieSpa</p>";

    public static void print(Transaction transaction) throws Exception {

        BufferedWriter writer = null;
        Customer customer = Customer.getByName(transaction.getCustomer());
        Course course = Course.getByName(transaction.getCourse());
        Staff staff = Staff.getById(String.valueOf(transaction.getStaff_id()));
        System.out.println(transaction.getStaff());
        try {
            writer = new BufferedWriter(new FileWriter("..\\contracts\\contract_" + transaction.getId() + ".html"));

            String head = String.format("<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\"><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><meta name=\"viewport\" content=\"width=device-width,initial-scale=1\"><title>Contract - %s</title><style>.container{width:800px;margin:auto}.center{text-align:center}.sign{display:flex;justify-content:space-around}</style></head>",
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
                    staff.getRoleName(),
                    staff.getPhone(),
                    staff.getEmail()
            );
            writer.write(benB);

            String courseInfo = String.format("<h4>Điều 1. Đối tượng của hợp đồng</h4><p>Theo yêu cầu của bên A về việc thực hiện dịch vụ %s , bên B đảm nhận và thực hiện %s </p><h4>Điều 2. Thời hạn thực hiện hợp đồng</h4><p>Hợp đồng này được thực hiện kể từ ngày %s </p><p>Thời gian dự kiến hoàn thành: là 30 ngày, kể từ ngày %s </p>",
                    course.getName(),
                    course.getDescription(),
                    DateUtil.convert(String.valueOf(transaction.bookingProperty().getValue())).getValue(),
                    DateUtil.convert(String.valueOf(transaction.bookingProperty().getValue())).getValue());
            writer.write(courseInfo);

            String data = "<h4>Điều 3. Quyền, nghĩa vụ của bên A</h4><h5>1. Quyền của Bên A:</h5><p>Yêu cầu bên B thực hiện công việc theo đúng chất lượng, số lượng, thời hạn, địa điểm thỏa thuận tại hợp đồng này.</p><p>Trường hợp bên B vi phạm nghiêm trọng nghĩa vụ thì bên A có quyền đơn phương chấm dứt thực hiện hợp đồng và yêu cầu bồi thường thiệt hại.</p><h5>2. Nghĩa vụ của bên A:</h5><p>Cung cấp cho bên B thông tin, tài liệu và các phương tiện cần thiết để thực hiện công việc, nếu có thỏa thuận hoặc việc thực hiện công việc đòi hỏi.</p><p>Trả tiền dịch vụ cho bên B theo thỏa thuận tại hợp đồng này.</p><h4>Điều 4. Quyền và nghĩa vụ của bên B:</h4><h5>1. Quyền của bên B:</h5><p>Yêu cầu bên A cung cấp thông tin, tài liệu và phương tiện để thực hiện công việc.</p><p>Được thay đổi điều kiện dịch vụ vì lợi ích của bên A mà không nhất thiết phải chờ ý kiến của bên A, nếu việc chờ ý kiến sẽ gây thiệt hại cho bên A, nhưng phải báo ngay cho bên A.</p><p>Yêu cầu bên A trả tiền dịch vụ</p><h5>2. Nghĩa vụ của bên A:</h5><p>Thực hiện công việc đúng chất lượng, số lượng, thời hạn, địa điểm thỏa thuận tại hợp đồng này.</p><p>Không được giao cho người khác thực hiện thay công việc nếu không có sự đồng ý bằng văn bản của bên A.</p><p>Báo ngay cho bên B về việc thông tin, tài liệu không đầy đủ, phương tiện không bảo đảm chất lượng để hoàn thành công việc.</p><p>Giữ bí mật thông tin mà mình biết được trong thời gian thực hiện công việc.</p>";
            writer.write(data);

            String price = "<h4>Điều 5. Tiền dịch vụ và phương thức thanh toán:</h4><p>1. Tiền dịch vụ: Thực hiện công việc tại Điều 1 là: " + transaction.getPay() + ", chưa bao gồm tiền thuế giá trị gia tăng.</p><p>2. Phương thức thanh toán: chuyển khoản 100%</p><h4>Điều 6. Chi phí khác</h4><p>Không được phép phát sinh thêm chi phí khác</p>";
            writer.write(price);

            String data2 = "<h4>Điều 7. Đơn phương chấm dứt thực hiện hợp đồng dịch vụ</h4><p>1. Trường hợp việc tiếp tục thực hiện công việc không có lợi cho bên A thì bên A có quyền đơn phương chấm dứt thực hiện hợp đồng, nhưng phải báo cho bên B biết trước 14 ngày. Bên A phải trả tiền dịch vụ theo phần dịch vụ mà bên B đã thực hiện và bồi thường thiệt hại.</p><p>2. Trường hợp bên A vi phạm nghiêm trọng nghĩa vụ thì bên B có quyền đơn phương chấm dứt thực hiện hợp đồng và yêu cầu bồi thường thiệt hại.</p><h4>Điều 8. Phương thực giải quyết tranh chấp</h4><p>Trong quá trình thực hiện hợp đồng, nếu có vấn đề phát sinh cần giải quyết, thì hai bên tiến hành thỏa thuận và thống nhất giải quyết kịp thời, hợp tình và hợp lý. Trường hợp không thỏa thuận được thì một trong các bên có quyền khởi kiện tại tòa án có thẩm quyền theo quy định của pháp luật.</p><h4>Điều 9. Các thoả thuận khác</h4><p>Bên A và bên B đồng ý đã hiểu rõ quyền, nghĩa vụ, lợi ích hợp pháp của mình và hậu quả pháp lý của việc giao kết hợp đồng này.</p><p>Bên A và bên B đồng ý thực hiện theo đúng các điều khoản trong hợp đồng này và không nêu thêm điều kiện gì khác.</p><p>Hợp đồng này được lập thành 02 bản, có giá trị pháp lý như nhau và được giao cho bên A 01 bản, bên B 01 bản./.</p>";
            writer.write(data2);

            String sign = String.format("<div class=\"sign\"><div><h4>BÊN B</h4><div>%s</div></div><div><h5>Hà Nội, ngày %s </h5><h4 class=\"center\">BÊN A</h4><p class=\"center\" style=\"margin-top: 7em;\">%s</p></div></div></div></body><script>window.print()</script></html>",
                    logo,
                    DateUtil.convert(String.valueOf(transaction.bookingProperty().getValue())).getValue(),
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
