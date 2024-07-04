package com.tietoevry.serverskeletonjava;

import com.tietoevry.serverskeletonjava.client.EventClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class ServerSkeletonJavaApplicationTests {

    @Autowired
    EventClient eventClient;

/*    @Test
    void insertEvents() {
        assertDoesNotThrow(() -> {
        eventClient.fetchAndStoreEvents();
        });
    }*/

}
