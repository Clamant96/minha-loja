package br.com.helpconnect.minhaLoja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.helpconnect.minhaLoja.modal.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}
