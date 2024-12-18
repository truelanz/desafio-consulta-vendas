package com.devsuperior.dsmeta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.devsuperior.dsmeta.services.SaleService;

@SpringBootApplication
public class DsmetaApplication implements CommandLineRunner {

	@Autowired
	SaleService saleService;

	public static void main(String[] args) {
		SpringApplication.run(DsmetaApplication.class, args);;

	}

	@Override
	public void run(String... args) throws Exception {

	}
}
