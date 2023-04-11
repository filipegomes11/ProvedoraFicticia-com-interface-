package br.com.provedoraficticia.basicas;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Grafo {
    private List<Conexao> conexoes;

    public Grafo(List<Conexao> conexoes) {
		this.conexoes = conexoes;
	}

    public void addConexao(Conexao conexao) {
        conexoes.add(conexao);
    }

    public List<Conexao> getConectados(Poste poste) {
    	return conexoes.stream().filter(c -> c.getOrigem().equals(poste) || c.getDestino().equals(poste)).toList();
    }
    
    public List<Conexao> getConexoes() {
        return conexoes;
    }
    
    public Set<Poste> getPostes() {
    	Set<Poste> postes = new HashSet<>();
    	for(Conexao c: conexoes) {
    		postes.add(c.getOrigem());
    		postes.add(c.getDestino());
    	}
    	return postes;
    }

	public void setConexoes(List<Conexao> conexoes) {
		this.conexoes = conexoes;
	}
}