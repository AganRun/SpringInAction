package com.agan.message;

import lombok.Data;

import java.io.Serializable;

@Data
public class MyMessage implements Serializable {

    private String uuid;
    private String name;
}
