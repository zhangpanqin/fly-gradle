package com.fly.study.gradle;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class GradleApplicationTests {

    private static final int SIZE = 1024 * 1024 * 1024;


    @Test
    void test22() {
        byte[] AA = new byte[SIZE];
        assertThat(AA.length, is(SIZE));
    }

}
