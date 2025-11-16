package com.flashfly_backend.flashfly;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
public class FlashflyBackendApplicationTests {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6");

    static {
        mongoDBContainer.start();
        System.setProperty("spring.data.mongodb.uri", mongoDBContainer.getReplicaSetUrl());
    }

    @Test
    void contextLoads() {
        // Test con MongoDB real
    }
}