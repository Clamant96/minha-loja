package br.com.helpconnect.minhaLoja.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.helpconnect.minhaLoja.modal.Produto;
import br.com.helpconnect.minhaLoja.modal.Usuario;
import br.com.helpconnect.minhaLoja.repository.ProdutoRepository;
import br.com.helpconnect.minhaLoja.repository.UsuarioRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Produto comprarProduto(long idProduto, long idUsuario) {
		
		Optional<Produto> produtoExistente = produtoRepository.findById(idProduto);
		Optional<Usuario> usuarioExistente = usuarioRepository.findById(idUsuario);
		
		if(produtoExistente.isPresent() && usuarioExistente.isPresent() && produtoExistente.get().getQtdEstoque() >= 0) {
			
			// ADICIONA O PRODUTO AO CARRINHO DO USUARIO 
			produtoExistente.get().getCarrinho().add(usuarioExistente.get());
			
			// DEBITA O ESTOQUE SEMPRE QUE E INSERIDO UM PRODUTO A UM CARRINHO 
			produtoExistente.get().setQtdEstoque(produtoExistente.get().getQtdEstoque() - 1);
			
			// SALVA AS ALTERACOES NO BANCO
			produtoRepository.save(produtoExistente.get());
			usuarioRepository.save(usuarioExistente.get());
			
			return produtoRepository.save(produtoExistente.get());
		}
		
		return null;
	}
	
	public Produto devolverProduto(long idProduto, long idUsuario) {
		
		Optional<Produto> produtoExistente = produtoRepository.findById(idProduto);
		Optional<Usuario> usuarioExistente = usuarioRepository.findById(idUsuario);
		
		if(produtoExistente.isPresent() && usuarioExistente.isPresent() && produtoExistente.get().getQtdEstoque() >= 0) {
			
			// REMOVE O PRODUTO DO CARRINHO DO USUARIO 
			produtoExistente.get().getCarrinho().remove(usuarioExistente.get());
			
			// ADICIONA O ESTOQUE SEMPRE QUE E REMOVIDO UM PRODUTO DE UM CARRINHO 
			produtoExistente.get().setQtdEstoque(produtoExistente.get().getQtdEstoque() + 1);
			
			// SALVA AS ALTERACOES NO BANCO
			produtoRepository.save(produtoExistente.get());
			usuarioRepository.save(usuarioExistente.get());
			
			return produtoRepository.save(produtoExistente.get());
		}
		
		return null;
	}
}
