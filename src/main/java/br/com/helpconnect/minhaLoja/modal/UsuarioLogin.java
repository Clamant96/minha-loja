package br.com.helpconnect.minhaLoja.modal;

import java.util.ArrayList;
import java.util.List;

public class UsuarioLogin {
	
	private long id;
	
	private String username;
	
	private String senha;

	private String email;
	
	private String nome;
	
	private List<Produto> listaPedidos = new ArrayList<>();
	
	private double totalCarrinho;
	
	private String img;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Produto> getListaPedidos() {
		return listaPedidos;
	}

	public void setListaPedidos(List<Produto> listaPedidos) {
		this.listaPedidos = listaPedidos;
	}

	public double getTotalCarrinho() {
		return totalCarrinho;
	}

	public void setTotalCarrinho(double totalCarrinho) {
		this.totalCarrinho = totalCarrinho;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
}
