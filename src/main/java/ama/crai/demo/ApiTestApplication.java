package ama.crai.demo;

import ama.crai.demo.configuration.LoadDatabase;
import ama.crai.demo.configuration.LoadEmployee;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({LoadDatabase.class})
public class ApiTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiTestApplication.class, args);
    }

}
