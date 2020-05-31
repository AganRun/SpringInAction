package com.agan.tacocloud.service.bo;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 配料表
 * @author AganRun
 */
@Data
@RequiredArgsConstructor
public class Ingredient {

    private final String id;
    private final String name;
    private final Type type;

    /*
     * WARP(包、包裹)
     * protein(蛋白)
     * veggies(蔬菜)
     * cheese(奶酪)
     * sauce(调味汁、酱)
     */
    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
