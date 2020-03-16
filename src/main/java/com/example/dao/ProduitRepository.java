package com.example.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entities.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
	@Query("SELECT p FROM Produit p WHERE p.designation like :x")
	public Page<Produit> chercher(@Param("x") String mc, Pageable pageable);
}
