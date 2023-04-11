package repositorio;

import java.util.ArrayList;
import java.util.List;

import br.com.provedoraficticia.basicas.Conexao;
import br.com.provedoraficticia.basicas.Grafo;
import br.com.provedoraficticia.basicas.Poste;

public class RepositorioGrafo {
	private Grafo grafo;
	private List<Poste> postes;
	
	public Grafo getGrafo() {
		return grafo;
	}

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
	
	public Poste encontraPostePorId(int id) {
		return postes.stream().filter(v -> v.getId() == id).findAny().orElse(null);
	}
}
