package com.mflyyou;

import java.time.LocalDateTime;

public class Demo {
    private static LocalDateTime now = LocalDateTime.now();

    public static void main(String[] args) {
        System.out.println(now);
    }

    public String demo() {
        return "a";
    }
}
