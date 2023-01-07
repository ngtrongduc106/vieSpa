package com.viespa.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class Invoice {
    public static void print() throws Exception {

        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter("invoice"+"số hóa đơn"+".html"));

            String head = String.format("<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\"><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><meta name=\"viewport\" content=\"width=device-width,initial-scale=1\"><title>Invoice - %s </title>",
                    "số invoice");
            writer.write(head);
            String style = "<style>.container{width:800px;margin:auto;border:2px #000 solid;padding:1em}header{display:flex;justify-content:space-between;align-items:center;border-bottom:2px #000 solid}section{border-bottom:2px #000 solid}.seller>div{display:flex;justify-content:space-between}table{margin-top:1em;width:100%;border-collapse:collapse}td,th{border:1px #000 solid}.sign{display:flex;justify-content:space-around}.sign div:first-child p:first-child{display:block;margin-bottom:8em}</style></head>";
            writer.write(style);
            String header = String.format("<body><div class=\"container\"><header><div>Logo spa</div><div><h1>Hóa đơn dịch vụ</h1><p>Ngày: %s</p></div><div><p>Mẫu số: VIESPA01</p><p style=\"\">Ký hiệu: HD01/23A</p><p>Số: %s </p></div></header>", "ngày xuất hóa đơn",
                    "số hóa đơn vào đây");
            writer.write(header);
            String saler = "<section class=\"seller\"><p>Đơn vị bán hàng: Công ty TNHH nhiều thành viên VieSpa</p><p>Mã số thuế: 00000000000000</p><p>Địa chỉ: Số 265 Đội Cấn - Ba Đình - Hà Nội - Việt Nam</p><div><p>Số điện thoại: 0900000000000</p><p>Số tài khoản: 1234-1234-1234-1234-1234</p></div></section>";
            writer.write(saler);
            String buyer = String.format("<section class=\"buyer\"><p>Họ tên người mua dịch vụ: %s </p><p>Địa chỉ: %s </p><p>Email: %s </p><p>Số điện thoại: %s </p><p>Ghi chú: %s </p></section>",
                    "tên để ở đây",
                    "địa chỉ để ở đây",
                    "email vào đây",
                    "điện thoại vào đây",
                    "Note vào đây");
            writer.write(buyer);
            String table = String.format("<table><thead><td>Tên dịch vụ</td><td>Đơn giá</td><td>VAT</td><td>Thành tiền</td></thead><tbody><tr><td><h4> %s </h4><p> %s </p></td><td> %s </td><td> %s </td><td> %s </td></tr></tbody></table>",
                    "tên course vào đây",
                    "description vào đây",
                    "giá vào đây",
                    "10% giá vào đây",
                    "110% giá vào đây");
            writer.write(table);
            String sign = String.format("<section class=\"sign\"><div><p>Người mua hàng</p><p> %s </p></div><div><p>Người bán hàng</p><div>Cho cái ảnh vào đây</div></div>",
                    "Tên customer vào đây");
            writer.write(sign);
            String footer = String.format("</section><footer><p>Hóa đơn cung cấp bởi công ty TNHH nhiều thành viên VieSpa - https://VieSpa.com - Đẹp là tất cả</p><p>Tra cứu hóa đơn điện tử tại https://viespa.com/invoice/%s. Mã số bí mật: xxxxxxxxxxx</p></footer></div></body></html>",
                    "số transaction vào đây");
            writer.write(footer);

        } catch (Exception e) {
            throw new Exception("FILE NOT FOUND");
        } finally {
            try {
                assert writer != null;
                writer.close();
                System.out.println("\n(ADD DATA TO FILE SUCCESSFULLY)");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}