package br.com.provedoraficticia.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.provedoraficticia.basicas.Conexao;
import br.com.provedoraficticia.basicas.Poste;
import br.com.provedoraficticia.fachada.ProvedoraFDP;
import dto.ConexaoDTO;

@RestController
@RequestMapping(value = "/postes")
public class PosteResource {
	@Autowired
	private ProvedoraFDP pfdp;
	
	@GetMapping()
	public ResponseEntity<List<Poste>> listPostes() {
		return ResponseEntity.ok().body(pfdp.listarPostes());
	}
	
	@GetMapping("/conexoes")
	public ResponseEntity<List<Conexao>> listConexoes() {
		return ResponseEntity.ok().body(pfdp.listarConexoes());
	}
	
	@GetMapping("/menorcaminho/{id}")
	public ResponseEntity<List<Poste>> menorCamiho(@PathVariable Integer id) {
		return ResponseEntity.ok().body(pfdp.menorCaminho(id));
	}
	
	@PostMapping()
	public ResponseEntity<Void> inserirPostes(@RequestBody List<Poste> postes) {
		pfdp.inserirPostes(postes);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/conexoes")
	public ResponseEntity<Void> insertConexoes(@RequestBody List<ConexaoDTO> conexoes) {
		pfdp.inserirConexoes(conexoes.stream().map(x -> new Conexao(x.getId(), pfdp.encontraPostePorId(x.getOrigem()), pfdp.encontraPostePorId(x.getDestino()))).toList());
		return ResponseEntity.ok().build();
	}
}
