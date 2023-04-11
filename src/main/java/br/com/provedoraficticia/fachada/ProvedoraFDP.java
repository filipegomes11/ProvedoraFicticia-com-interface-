package br.com.provedoraficticia.fachada;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.provedoraficticia.basicas.Conexao;
import br.com.provedoraficticia.basicas.Grafo;
import br.com.provedoraficticia.basicas.Poste;
import br.com.provedoraficticia.negocio.MenorCaminho;

@Service
public class ProvedoraFDP {
	
	private Grafo grafo;
	private List<Poste> postes; 
	/*
	public Grafo preencheGrafo() {
		if(grafo == null) {
			Poste poste1 = new Poste(1, 0, 2, 5, 9);
			Poste poste2 = new Poste(2, 1, 2, 6, 10);
			Poste poste3 = new Poste(3, 0, 2, 3, 6);
			Poste poste4 = new Poste(4, 2, 2, 15, 4);
			Poste poste5 = new Poste(5, 2, 2, 8, 15);
			Poste poste6 = new Poste(6, 0, 2, 7, 4);
	
			Conexao aresta1 = new Conexao(1, poste1, poste2);
			Conexao aresta2 = new Conexao(2, poste2, poste3);
			Conexao aresta3 = new Conexao(3, poste3, poste4);
			Conexao aresta4 = new Conexao(4, poste4, poste5);
			Conexao aresta5 = new Conexao(5, poste2, poste4);
			Conexao aresta6 = new Conexao(6, poste3, poste5);
			Conexao aresta7 = new Conexao(7, poste1, poste6);
			
			grafo = new Grafo(new ArrayList<Conexao>(Arrays.asList(aresta1, aresta2, aresta3, aresta4, aresta5, aresta6, aresta7)));
		}
		return grafo;
	}*/
	
	public void inserirPostes(List<Poste> postes) {
		this.postes = postes;
	}
	
	public void inserirConexoes(List<Conexao> conexoes) {
		grafo = new Grafo(conexoes);
	}
	
	public List<Poste> listarPostes() {
		return postes;
	}
	
	public List<Conexao> listarConexoes() {
		if(grafo != null)
			return grafo.getConexoes();
		else
			return new ArrayList<>();
	}
	
	public List<Poste> menorCaminho(int idOrigem) {
		if (grafo != null) {
			Poste origem = encontraPostePorId(idOrigem);
			if(origem != null)
				return MenorCaminho.encontrarMenorCaminho(grafo, origem);
			else {
				System.out.println("IF I HAVE A HAMMER!!!!!!!");
				return new ArrayList<>();
			}
		} else {
			return new ArrayList<>();
		}
	}
	
	public Poste encontraPostePorId(int id) {
		return postes.stream().filter(v -> v.getId() == id).findAny().orElse(null);
	}
}
