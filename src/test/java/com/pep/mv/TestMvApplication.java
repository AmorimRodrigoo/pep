package com.pep.mv;

import org.springframework.boot.SpringApplication;

public class TestMvApplication {

	public static void main(String[] args) {
		SpringApplication.from(MvApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
