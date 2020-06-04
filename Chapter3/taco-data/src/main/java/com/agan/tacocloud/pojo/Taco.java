package com.agan.tacocloud.pojo;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 配料表
 * @author AganRun
 */
@Data
public class Taco {

    @NotNull
    @Size(min = 5, message = "请至少输入5个字符的名字")
    private String name;
    @NotNull
    @Size(min = 1, message = "请至少选择一个配料")
    private List<String> ingredients;
}
