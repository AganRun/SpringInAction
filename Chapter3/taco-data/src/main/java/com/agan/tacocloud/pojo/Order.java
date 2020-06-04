package com.agan.tacocloud.pojo;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class Order {

    private Long id;
    private Date placeAt;
    @NotBlank
    private String name;
    @NotBlank
    private String street;
    @NotBlank
    private String city;
    @NotBlank
    private String state;
    @NotBlank
    private String zip;
    @CreditCardNumber
    private String ccNumber;
    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$", message = "Must be formatted MM/YY")
    private String ccExpiration;
    //卡号、有效期和服务约束代码生成的3位或4位数字，一般写在卡片磁条的2磁道用户自定义数据区里面
    @Digits(integer = 3, fraction = 0, message = "非法CVV")
    private String ccCVV;
}
