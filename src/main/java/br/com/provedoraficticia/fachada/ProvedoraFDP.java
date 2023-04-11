package br.com.provedoraficticia.fachada;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.provedoraficticia.basicas.Conexao;
import br.com.provedoraficticia.basicas.Grafo;
import br.com.provedoraficticia.basicas.Poste;
import br.com.provedoraficticia.negocio.MenorCaminho;
import repositorio.RepositorioGrafo;

@Service
public class ProvedoraFDP { 
	
	private RepositorioGrafo repo = new RepositorioGrafo();
	
	public void inserirPostes(List<Poste> postes) {
		repo.inserirPostes(postes);
	}
	
	public void inserirConexoes(List<Conexao> conexoes) {
		repo.inserirConexoes(conexoes);
	}
	
	public List<Poste> listarPostes() {
		return repo.listarPostes();
	}
	
	public List<Conexao> listarConexoes() {
		return repo.listarConexoes();
	}
	
	public Poste encontraPostePorId(int id) {
		return repo.encontraPostePorId(id);
	}
	
	public List<Poste> menorCaminho(int idOrigem) {
		Grafo grafo = repo.getGrafo();
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
	
	
}
