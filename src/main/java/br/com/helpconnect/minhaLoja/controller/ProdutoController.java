package br.com.helpconnect.minhaLoja.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.helpconnect.minhaLoja.modal.Produto;
import br.com.helpconnect.minhaLoja.repository.ProdutoRepository;
import br.com.helpconnect.minhaLoja.service.ProdutoService;

@RestController
@RequestMapping("/produto")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository repository;
	
	@Autowired
	private ProdutoService service;
	
	@GetMapping
	public ResponseEntity<List<Produto>> findAllProdutos() {
		
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Produto> findByIdProduto(@PathVariable("id") long id) {
		
		return repository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/produto_pedido/comprar/idproduto/{idProduto}/idusuario/{idUsuario}")
	public ResponseEntity<Produto> comprarProduto(@PathVariable("idProduto") long idProduto, @PathVariable("idUsuario") long idUsuario) {
		
		return ResponseEntity.ok(service.comprarProduto(idProduto, idUsuario));
	}
	
	@GetMapping("/produto_pedido/devolver/idproduto/{idProduto}/idusuario/{idUsuario}")
	public ResponseEntity<Produto> devolverProduto(@PathVariable("idProduto") long idProduto, @PathVariable("idUsuario") long idUsuario) {
		
		return ResponseEntity.ok(service.devolverProduto(idProduto, idUsuario));
	}
	
	@PostMapping
	public ResponseEntity<Produto> postProduto(@RequestBody Produto produto) {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(produto));
	}
	
	@PutMapping
	public ResponseEntity<Produto> putProduto(@RequestBody Produto produto) {
		
		return ResponseEntity.ok(repository.save(produto));
	}
	
	@DeleteMapping("/{id}")
	public void deleteProduto(@PathVariable("id") long id) {
		repository.deleteById(id);
		
	}
}
