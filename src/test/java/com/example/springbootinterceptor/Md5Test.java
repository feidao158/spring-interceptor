package com.example.springbootinterceptor;


import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;

public class Md5Test {

    public static void main(String[] args) {

        String replace = UUID.randomUUID().toString().replace("-", "");
        System.out.println(replace);
        String s = DigestUtils.md5Hex("abc-123" + replace);
        System.out.println(s);
    }
}
