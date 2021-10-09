package com.vti.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

// tương tự
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDto {
    private short id;

    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createDate;

    private AccountDto creator; // cái này k có trong entity đúg k thì mình lm riêng cho cái này
    // a đọc bài trên gitlab trc thấy họ viết kiểu này
    // cái chữ creator kia nó ăn vào creator bên group xog r nó ăn thêm 2 cái field dưới
    private int memberNum;

    // dùng inline class cho tiện
    // muốn bn thì mình thêm đây thôi chứ nếu muốn kiểu kia thì phải viết lại logic à oke anh..em hiểu r
    // cơ bản như vầy là đủ r tại vì mình hay từ entity sang dto ấy ttin bên này k bh lấy full ra
    // nên mình dùng đâu khai báo thêm thôi là đc okeeee a
    // cái lombok đọc thêm nhé còn csai token nãy a thấy db thừa bảng ấy thì mình fix = java
    @Data
    @NoArgsConstructor
    static class AccountDto {   // id vs fullName là account có đúg k
        private int id; // này nx

        private String fullName;    // tự ăn vào đây ra đc 2 thông tin muốn thêm gì bổ sung thôi
        // cứ trùng tên là ok
        private String firstName;

        private String lastName;

        private String role;
    }
}
