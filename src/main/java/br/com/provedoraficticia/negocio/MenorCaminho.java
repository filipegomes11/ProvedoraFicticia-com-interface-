package br.com.provedoraficticia.negocio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import br.com.provedoraficticia.basicas.Conexao;
import br.com.provedoraficticia.basicas.Grafo;
import br.com.provedoraficticia.basicas.Poste;

public class MenorCaminho {

	private  static void inicializar(Grafo grafo, Map<Poste, Double> distancias, Map<Poste, Poste> anteriores, PriorityQueue<PosteDistancia> heap, Poste origem) {
		for (Poste poste : grafo.getPostes()) {
			if (poste.equals(origem)) {
				distancias.put(poste, 0.0);
				heap.offer(new PosteDistancia(poste, 0));
			} else {
				distancias.put(poste, Double.POSITIVE_INFINITY);
			}
			anteriores.put(poste, null);
		}
	}
	
	public static List<Poste> encontrarMenorCaminho(Grafo grafo, Poste origem) {
		Map<Poste, Double> distancias = new HashMap<>(); // Mapa de distâncias para cada poste
		Map<Poste, Poste> anteriores = new HashMap<>(); // Mapa de postes anteriores no caminho mais curto
		PriorityQueue<PosteDistancia> heap = new PriorityQueue<>(); // Heap para encontrar o próximo poste com menor distancia
		Poste destino = origem;
		double minDistancia = Double.POSITIVE_INFINITY;
		// Inicialização
		inicializar(grafo, distancias, anteriores, heap, origem);

		// Relaxamento
		while (!heap.isEmpty()) {
			PosteDistancia atual = heap.poll();
			Poste posteAtual = atual.getPoste();
			if(posteAtual.getConexoesDisponiveis() > 0 && atual.getDistancia() < minDistancia) {
				destino = posteAtual;
				minDistancia = atual.getDistancia();
			}
			for (Conexao c : grafo.getConectados(posteAtual)) {
				
				Poste vizinho = c.getAdjacencia(posteAtual);
				double distancia = distancias.get(posteAtual) + c.getDistancia();
				if (distancia < distancias.get(vizinho) && !vizinho.equals(origem)) {
					distancias.put(vizinho, distancia);
					anteriores.put(vizinho, atual.getPoste());
					heap.offer(new PosteDistancia(vizinho, distancia));
				}
			}
			
		}
		return reconstroiCaminho(destino, anteriores);
	}
	
	private static List<Poste> reconstroiCaminho(Poste destino, Map<Poste, Poste> anteriores){
		List<Poste> caminho = new ArrayList<>();
		Poste poste = destino;
		while (poste != null) {
			caminho.add(poste);
			poste = anteriores.get(poste);
		}
		Collections.reverse(caminho);
		return caminho;
	}
	
}
