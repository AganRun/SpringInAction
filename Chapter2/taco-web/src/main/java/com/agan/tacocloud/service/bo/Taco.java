package com.agan.tacocloud.service.bo;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * 配料表
 * @author AganRun
 */
@Data
public class Taco {

    private String name;
    private List<String> ingredients;
}
