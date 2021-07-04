package com.fly.study.gradle.test;

import com.mflyyou.LibA;
import com.mflyyou.LibB;
import org.apache.commons.lang3.StringUtils;

/**
 * @author 张攀钦
 * @date 2021-07-04-17:19
 */
public class Test {
    public static void main(String[] args) {
        new LibB().libB();
        new LibA().libA();
        System.out.println(StringUtils.repeat("22", 3));
    }
}
