package br.com.helpconnect.minhaLoja.modal;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "tb_produto")
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	private String nome;
	
	@ManyToOne
	@JsonIgnoreProperties("produtos")
	private Categoria categoria;
	
	@NotNull
	private double preco;
	
	@NotNull
	private int qtdEstoque;
	
	private String img;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(
	  name = "produto_pedido",
	  joinColumns = @JoinColumn(name = "produto_id"),
	  inverseJoinColumns = @JoinColumn(name = "carrinho_id")
	)
	@JsonIgnoreProperties({"listaPedidos"})
	private List<Usuario> carrinho = new ArrayList<>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public int getQtdEstoque() {
		return qtdEstoque;
	}

	public void setQtdEstoque(int qtdEstoque) {
		this.qtdEstoque = qtdEstoque;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public List<Usuario> getCarrinho() {
		return carrinho;
	}

	public void setCarrinho(List<Usuario> carrinho) {
		this.carrinho = carrinho;
	}
	
}
