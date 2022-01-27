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

    @Test
    void saveTest() {
        // works fine
        List<B> bies = List.of(new B());

        aService.save(new A(null, bies));
    }

    @Test
    void saveTwoBInATest() {
        List<B> bies = List.of(
                new B(),
                new B()
        );

        //  SQL [INSERT INTO `b` (`a_id`, `id`) VALUES (?, ?)];
        //  Duplicate entry '1' for key 'PRIMARY';
        //  nested exception is java.sql.SQLIntegrityConstraintViolationException:
        //  Duplicate entry '1' for key 'PRIMARY'
        aService.save(new A(null, bies));
    }

}
