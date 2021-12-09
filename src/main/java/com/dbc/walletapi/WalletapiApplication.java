package com.dbc.walletapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class WalletapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalletapiApplication.class, args);
	}

}
