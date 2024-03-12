package com.zzz.producer.enums;

/**
 * @author zhangzhongzhen wrote on 2024/3/12
 * @version 1.0
 * @description:
 */
public enum OrderEnum {


    CREATE(0),
    PAID(1),
    SUCCESS(2);

    private final int code;

    OrderEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }


}
