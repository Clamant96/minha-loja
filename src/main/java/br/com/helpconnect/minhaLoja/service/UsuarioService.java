package br.com.helpconnect.minhaLoja.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.helpconnect.minhaLoja.modal.Usuario;
import br.com.helpconnect.minhaLoja.modal.UsuarioLogin;
import br.com.helpconnect.minhaLoja.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repository;
	
	public Optional<UsuarioLogin> login(Optional<UsuarioLogin> usuarioLogin) {
		
		Optional<Usuario> usuarioExistente = repository.findByUsername(usuarioLogin.get().getUsername());
		
		/* VERIFICA SE O USUARIO EXISTE NA BASE E SE A SENHA ESTA CORRETA */
		if (usuarioExistente.isPresent() && usuarioExistente.get().getSenha().equals(usuarioLogin.get().getSenha())) {
			
			/* INSERE O TOKEN GERADO DENTRO DE NOSSO ATRIBUTO TOKEN */
			usuarioLogin.get().setId(usuarioExistente.get().getId());
			usuarioLogin.get().setUsername(usuarioExistente.get().getUsername());
			usuarioLogin.get().setSenha(usuarioExistente.get().getSenha());
			usuarioLogin.get().setEmail(usuarioExistente.get().getEmail());
			usuarioLogin.get().setNome(usuarioExistente.get().getNome());
			usuarioLogin.get().setListaPedidos(usuarioExistente.get().getListaPedidos());
			usuarioLogin.get().setTotalCarrinho(usuarioExistente.get().getTotalCarrinho());
			usuarioLogin.get().setImg(usuarioExistente.get().getImg());
			
			return usuarioLogin;
		}
		
		return null;
	}
	
}
