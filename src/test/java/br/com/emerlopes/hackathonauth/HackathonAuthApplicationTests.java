package br.com.emerlopes.hackathonauth;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class HackathonAuthApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    void contextLoads() {
        // Act & Assert
        assertNotNull(applicationContext);
    }
}