package com.vanna.project_eaters;

import org.springframework.boot.SpringApplication;

public class TestProjectEatersApplication {

    public static void main(String[] args) {
        SpringApplication.from(ProjectEatersApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
