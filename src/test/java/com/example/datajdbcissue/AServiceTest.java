package com.example.datajdbcissue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.MySQLContainer;

import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = AServiceTest.DockerMySqlDataSourceInitializer.class)
class AServiceTest {

    public static final MySQLContainer<?> mysqlDBContainer = new MySQLContainer<>("mysql:5.7.34");

    static {
        mysqlDBContainer.start();
    }

    public static class DockerMySqlDataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {

            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.datasource.url=" + mysqlDBContainer.getJdbcUrl(),
                    "spring.datasource.username=" + mysqlDBContainer.getUsername(),
                    "spring.datasource.password=" + mysqlDBContainer.getPassword()
            );
        }
    }

    @Autowired
    AService aService;

    @Autowired
    BRepo bRepo;

    @Test
    void saveTest() {
        //created as entities with id=1,2
        bRepo.save(new B(null, null));
        bRepo.save(new B(null, null));


        List<B> bies = List.of(new B(), new B());

        //Failed with:  Duplicate entry '1' for key 'PRIMARY'
        aService.save(new A(null, bies));
    }
}
