package com.example.carwash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication(scanBasePackages = "com.example.carwash")
public class CarwashApplication {

    public static void main(String[] args) {
        //BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //System.out.println(encoder.encode("admin"));
        SpringApplication.run(CarwashApplication.class, args);

    }

}
