package com.joyfarm.farmstival;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@SpringBootApplication
public class FarmstivalApplication {

	public static void main(String[] args) {
		SpringApplication.run(FarmstivalApplication.class, args);
	}

}
