package br.com.helpconnect.minhaLoja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.helpconnect.minhaLoja.modal.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
