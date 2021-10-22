package com.peigo.wallet.ms.boilerplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.github.damianwajser","com.peigo.wallet.ms.boilerplate"})
public class MsBoilerplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsBoilerplateApplication.class, args);
	}
}
