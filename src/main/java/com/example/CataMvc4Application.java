package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import com.example.dao.ProduitRepository;
import com.example.entities.Produit;

@SpringBootApplication
public class CataMvc4Application {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(CataMvc4Application.class, args);
		ProduitRepository produitRepository = ctx.getBean(ProduitRepository.class);		
		produitRepository.findAll().forEach((p) -> System.out.println(p.getDesignation()));
	}

}
