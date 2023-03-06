package br.com.helpconnect.minhaLoja.controller;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Optional;

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

import br.com.helpconnect.minhaLoja.modal.Usuario;
import br.com.helpconnect.minhaLoja.repository.UsuarioRepository;
import br.com.helpconnect.minhaLoja.service.ProdutoService;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@GetMapping
	public ResponseEntity<List<Usuario>> findAllUsuarios() {
		
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> findByIdUsuario(@PathVariable("id") long id) {
		
		Optional<Usuario> cliente = repository.findById(id);

		// AJUSTA VALOR CARRINHO
		double total = cliente.get().getTotalCarrinho();
		NumberFormat formatter = new DecimalFormat("#0.00");
		cliente.get().setTotalCarrinho(Double.parseDouble(formatter.format(total).replace(",", ".")));

		cliente.get().setListaPedidos(produtoService.retirraDuplicidadeCarrinho(cliente));

		try {
			return ResponseEntity.ok(cliente.get());
			
		}catch(Exception e) {
			return ResponseEntity.badRequest().build();
			
		}
	}
	
	@PostMapping
	public ResponseEntity<Usuario> postUsuario(@RequestBody Usuario usuario) {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(usuario));
	}
	
	@PutMapping
	public ResponseEntity<Usuario> putUsuario(@RequestBody Usuario usuario) {
		
		return ResponseEntity.ok(repository.save(usuario));
	}
	
	@DeleteMapping("/{id}")
	public void deleteUsuario(@PathVariable("id") long id) {
		repository.deleteById(id);
		
	}
	
}
