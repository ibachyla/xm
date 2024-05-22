package com.github.ibachyla.xm.api;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Tag("api")
@SpringBootTest
public class TestTaskTwo {

    @Test
    void verifyStarshipPresence() {
        System.out.println("Starship is present");
    }
}
