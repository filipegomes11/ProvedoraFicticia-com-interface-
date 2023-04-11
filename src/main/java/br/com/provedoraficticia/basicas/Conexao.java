package br.com.provedoraficticia.basicas;

import java.lang.Math;

public class Conexao {
    private int id;
    private double distancia;
    private Poste origem;
    private Poste destino;

    public Conexao(int id, Poste origem, Poste destino) {
        this.id = id;
        this.origem = origem;
        this.destino = destino;
        this.distancia = calculaDistancia();
    }
    
    private double calculaDistancia() {
    	return Math.sqrt(Math.pow(origem.getCoordenadaX() - destino.getCoordenadaX(), 2)+Math.pow(origem.getCoordenadaY() - destino.getCoordenadaY(), 2));
    }

    public int getId() {
        return id;
    }

    public double getDistancia() {
        return distancia;
    }

    public Poste getOrigem() {
        return origem;
    }

    public Poste getDestino() {
        return destino;
    }
    
    public Poste getAdjacencia(Poste poste) {
    	return poste.equals(origem) ? destino: origem;
    } 
}
