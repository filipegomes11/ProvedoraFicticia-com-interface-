package dto;

public class ConexaoDTO {
	private int id;
	private int origem;
	private int destino;
	
	public ConexaoDTO() {
	}
	
	public int getId() {
		return id;
	}

	public int getOrigem() {
		return origem;
	}

	public int getDestino() {
		return destino;
	}

	@Override
	public String toString() {
		return "ConexaoDTO [id=" + id + ", origem=" + origem + ", destino=" + destino + "]";
	}
}
