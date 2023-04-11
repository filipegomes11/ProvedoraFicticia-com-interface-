package br.com.provedoraficticia.fachada;

import java.util.ArrayList;
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
