package com.mflyyou.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class DemoUtils {
    public boolean test() {
        return true;
    }

    public List<String> test1() {
        return Lists.newArrayList("aaa");
    }

    public String test3() {
        return StringUtils.join("aa", "bb");
    }
}