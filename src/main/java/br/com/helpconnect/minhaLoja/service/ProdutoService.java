package br.com.helpconnect.minhaLoja.service;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
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
			
			int contadorProduto = 0;
			
			// NAVEGA NO ARRAY DE PRODUTO DO USUARIO, E CASO ELE SEJA REPEDIDO, E INCREMENTADO O VALOR NA QTDPRODUTO E ADICIONADO AO ARRAY
			// CASO ELE NAO EXISTA AINDA NO ARRAY
			for (Produto produto : usuarioExistente.get().getListaPedidos()) {

				for (Produto produtoRepeticao : usuarioExistente.get().getListaPedidos()) {

					// VERIFICA SE EXISTE REPETICAO DO PRODUTO, PARA REALIZAR O INCREMENTO DO VALOR AO 'QTDPRODUTO'
					if(produto.getId() == produtoRepeticao.getId()) {
							
						contadorProduto = contadorProduto + 1;
						
						produto.setQtdPedidoProduto(contadorProduto); // INCREMENTA O VALOR NO CONTADOR

						// GERENCIA O ESTOQUE DEBITNADO UM PRODUTO DO ESTOQUE 
						produtoExistente.get().setQtdPedidoProduto(contadorProduto);

					}

				}

			}

			// RETIRA O VALOR EXISTENTE DO CARRINHO PARA PODER SER RECALCULADO 
			usuarioExistente.get().setTotalCarrinho(usuarioExistente.get().getTotalCarrinho() - (produtoExistente.get().getPreco() * contadorProduto));

			// COMPENSA ACRESCENTANDO O NOVO PRODUTO AO CARRINHO ==> O ID INFORMADO 
			contadorProduto = contadorProduto + 1;
		
			// AJUSTA O VALOR DO CARRINHO DE UM USUARIO ESPECIFICO 
			usuarioExistente.get().setTotalCarrinho(usuarioExistente.get().getTotalCarrinho() + (produtoExistente.get().getPreco() * contadorProduto));
			
			// SALVA AS ALTERACOES NO BANCO
			produtoRepository.save(produtoExistente.get());
			usuarioRepository.save(usuarioExistente.get());
			
			return produtoRepository.save(produtoExistente.get());
		}else if(produtoExistente.isPresent() && usuarioExistente.isPresent()) {

			// ADICIONA O PRODUTO AO CARRINHO DO USUARIO
			produtoExistente.get().getCarrinho().add(usuarioExistente.get());
			
			// GERENCIA O ESTOQUE DEBITNADO UM PRODUTO DO ESTOQUE 
			produtoExistente.get().setQtdPedidoProduto(1);

			// GERENCIA O ESTOQUE DEBITNADO UM PRODUTO DO ESTOQUE 
			produtoExistente.get().setQtdEstoque(produtoExistente.get().getQtdEstoque() - 1);
			
			// ATUALIZA O VALOR DO CARRINHO DO USUARIO 
			double total = usuarioExistente.get().getTotalCarrinho() + (produtoExistente.get().getPreco() * produtoExistente.get().getQtdPedidoProduto());
		
			NumberFormat formatter = new DecimalFormat("#0.00");
			usuarioExistente.get().setTotalCarrinho(Double.parseDouble(formatter.format(total).replace(",", ".")));
			
			produtoRepository.save(produtoExistente.get());
			usuarioRepository.save(usuarioExistente.get());
			usuarioRepository.save(usuarioExistente.get()).getTotalCarrinho();
			
			return produtoRepository.save(produtoExistente.get());
		}
		
		return null;
	}
	
	public Produto devolverProduto(long idProduto, long idUsuario) {
		
		Optional<Produto> produtoExistente = produtoRepository.findById(idProduto);
		Optional<Usuario> usuarioExistente = usuarioRepository.findById(idUsuario);
		
		// SE O 'PRODUTO' E 'PEDIDO' EXISTIREM, E SE O 'ESTOQUE' CONTEM PRODUTOS DISPONIVEIS ENTRA NA CONDICAO
		if(usuarioExistente.get().getListaPedidos().contains(produtoExistente.get())) {

			int contadorProduto = 0;
			
			// NAVEGA NO ARRAY DE PRODUTO DO USUARIO, E CASO ELE SEJA REPEDIDO, E INCREMENTADO O VALOR NA QTDPRODUTO E ADICIONADO AO ARRAY
			// CASO ELE NAO EXISTA AINDA NO ARRAY
			for (Produto produto : usuarioExistente.get().getListaPedidos()) {

				for (Produto produtoRepeticao : usuarioExistente.get().getListaPedidos()) {

					// VERIFICA SE EXISTE REPETICAO DO PRODUTO, PARA REALIZAR O INCREMENTO DO VALOR AO 'QTDPRODUTO'
					if(produto.getId() == produtoRepeticao.getId()) {
							
						contadorProduto = contadorProduto + 1;
						
						produto.setQtdPedidoProduto(contadorProduto); // INCREMENTA O VALOR NO CONTADOR

						// GERENCIA O ESTOQUE DEBITNADO UM PRODUTO DO ESTOQUE 
						produtoExistente.get().setQtdPedidoProduto(contadorProduto);

					}

				}

				contadorProduto = 0;

			}

			// REMOVE O CARRINHO DO PRODUTO
			produtoExistente.get().getCarrinho().remove(usuarioExistente.get());
			usuarioExistente.get().getListaPedidos().remove(produtoExistente.get()); // DELETA O PRODUTO DA LISTA DO CLIENTE DA INSTANCIA ATUAL

			// GERENCIA O ESTOQUE DEBITNADO UM PRODUTO DO ESTOQUE
			produtoExistente.get().setQtdEstoque(produtoExistente.get().getQtdEstoque() + 1);

			System.out.println("VALOR CARRINHO: "+ usuarioExistente.get().getTotalCarrinho());

			System.out.println("CONTADOR: "+ produtoExistente.get().getQtdPedidoProduto());
			System.out.println("VALOR PRODUTO = "+ produtoExistente.get().getPreco() +" x CONTADOR = "+ 1); 
			System.out.println("SUBTRACAO APLICADA PRECO PRODUTO X CONTADOR: "+ produtoExistente.get().getPreco() * 1);

			// AJUSTA O VALOR DO CARRINHO DO USUARIO
			double total = usuarioExistente.get().getTotalCarrinho() - (produtoExistente.get().getPreco() * 1); // TEM SOMENTE UMA UNIDADE, POIS ESTAMOS REALIZANDO A DELETA DE SOMENTE UAMA UNIDADE CABECAO
			NumberFormat formatter = new DecimalFormat("#0.00");
			usuarioExistente.get().setTotalCarrinho(Double.parseDouble(formatter.format(total).replace(",", ".")));

			produtoRepository.save(produtoExistente.get());
			usuarioRepository.save(usuarioExistente.get());
			usuarioRepository.save(usuarioExistente.get()).getTotalCarrinho();
			
			return produtoRepository.save(produtoExistente.get());
			
		}
		
		return null;
	}
	
	public List<Produto> retirraDuplicidadeCarrinho(Optional<Usuario> cliente) {

		List<Produto> produtos = new ArrayList<>();

		int contadorProduto = 0;
		
		// NAVEGA NO ARRAY DE PRODUTO DO USUARIO, E CASO ELE SEJA REPEDIDO, E INCREMENTADO O VALOR NA QTDPRODUTO E ADICIONADO AO ARRAY
		// CASO ELE NAO EXISTA AINDA NO ARRAY
		for (Produto produto : cliente.get().getListaPedidos()) {

			for (Produto produtoRepeticao : cliente.get().getListaPedidos()) {

				System.out.println(produtoRepeticao.getNome());

				// VERIFICA SE EXISTE REPETICAO DO PRODUTO, PARA REALIZAR O INCREMENTO DO VALOR AO 'QTDPRODUTO'
				if(produto.getId() == produtoRepeticao.getId()) {
						
					contadorProduto = contadorProduto + 1;
					
					produto.setQtdPedidoProduto(contadorProduto); // INCREMENTA O VALOR NO CONTADOR

				}

			}

			if(!produtos.contains(produto)) {
				
				// ADICIONA O PRODUTO AO CARRINHO DO USUARIO 
				produtos.add(produto);
				
			}
			
			contadorProduto = 0; // ZERA O CONTADOR PARA O PROXIMO PRODUTO
			
		}

		contadorProduto = 0; // ZERA O CONTADOR PARA O PROXIMO PRODUTO

		return produtos;
	}
	
}
