package com.mflyyou.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DemoUtilsTest {
    @Test
    void someLibraryMethodReturnsTrue() {
        DemoUtils demoUtils = new DemoUtils();
        assertTrue(demoUtils.test(), "someLibraryMethod should return 'true'");
    }
}