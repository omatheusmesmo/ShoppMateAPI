package com.omatheusmesmo.shoppinglist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ListaDeComprasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ListaDeComprasApplication.class, args);
	}

}
