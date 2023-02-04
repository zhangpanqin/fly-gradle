package com.mflyyou.application;

import com.mflyyou.util.DemoUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class Demo22 {
    public static void main(String[] args) {
        System.out.println(new DemoUtils().test());
        System.out.println(new DemoUtils().test1());
        System.out.println(new DemoUtils().test3());
        System.out.println(StringUtils.isEmpty("aa"));
//         Lists.newArrayList("不能使用 google Lists");
    }
}
