package com.relieveslondono.gestion;

import org.springframework.boot.SpringApplication;

public class TestRelievesLondonoApplication {

    public static void main(String[] args) {
        SpringApplication.from(RelievesLondonoApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
