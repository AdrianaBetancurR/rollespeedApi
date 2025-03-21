package com.rollerspeed;  
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication  
@ComponentScan(basePackages = "com.rollerspeed")

@EnableJpaRepositories(basePackages = "com.rollerspeed.repository")
public class RollerspeedApplication {

    public static void main(String[] args) {
        SpringApplication.run(RollerspeedApplication.class, args);  
        System.out.println("Aplicación cargada correctamente.");  
    }
}