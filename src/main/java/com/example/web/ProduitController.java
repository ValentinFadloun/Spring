package com.example.web;

import java.awt.print.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dao.ProduitRepository;
import com.example.entities.Produit;

@Controller
public class ProduitController {
	@Autowired
	private ProduitRepository produitRepository;
	
	@RequestMapping(value="/index")
	public String index(Model model, @RequestParam(name="page", defaultValue="0")int p, @RequestParam(name="size",defaultValue="5")int s, @RequestParam(name="mc",defaultValue="")String mc) {
//		List<Produit> produits = produitRepository.findAll();
//		Page<Produit> pageProduits = produitRepository.findAll(PageRequest.of(p, s));
		Page<Produit> pageProduits = produitRepository.chercher("%" + mc + "%", PageRequest.of(p, s));
		model.addAttribute("listProduits", pageProduits.getContent());
		int[] pages = new int[pageProduits.getTotalPages()];
		model.addAttribute("pages", pages);
		model.addAttribute("size", s);
		model.addAttribute("pageCourante", p);
		model.addAttribute("motCle", mc);
		return "Produits";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String delete(long id) {
		produitRepository.deleteById(id);;
		return "redirect:/index";
	}
}
