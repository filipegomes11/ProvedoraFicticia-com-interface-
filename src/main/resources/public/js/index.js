let divCaminho = document.getElementById("caminho"),
	mapa = document.getElementById("mapa"),
	inputGrafo = form.elements.namedItem("grafo"),
	btnExibir = document.getElementById("btnExibir");
let postes, conexoes, qtdLinhas, qtdColunas;
let origem, destino, caminho;
let conexoesCaminho, cellsCaminho;


inputGrafo.onchange = async function(e) {
	e.preventDefault();
	limparGrafo();
	lerArquivo(inputGrafo.files[0]);
	inputGrafo.value = "";

}

function addGrafo() {
	inputGrafo.click();
}

function lerArquivo(file) {
	let reader = new FileReader();
	reader.onload = onReaderLoad;
	reader.readAsText(file);
}

function onReaderLoad(event) {
	let result = JSON.parse(event.target.result);
	qtdLinhas = result["qtdLinhas"];
	qtdColunas = result["qtdColunas"];
	createCells(qtdLinhas, qtdColunas);
	inserirPostes(result["postes"]);
	inserirConexoes(result["conexoes"]);
}

function limparGrafo() {
	origem = null;
	destino = null;
	caminho = null;
	postes = null;
	conexoes = null;
	conexoesCaminho = null;
	cellsCaminho = null;
	qtdLinhas = 0;
	qtdColunas = 0;
	mapa.innerHTML = "";
	divCaminho.innerHTML = "";
	document.getElementById("distancia").innerHTML = 0;
}

function createCells(lin, col) {
	for (let i = 1; i <= lin; i++) {
		let tr = document.createElement("TR");
		tr.id = "lin" + i;
		for (let j = 1; j <= col; j++) {
			let td = document.createElement("TD");
			td.id = "cell" + i + "x" + j;
			tr.appendChild(td);
		}
		mapa.appendChild(tr);
	}
}

function conectarCells() {
	for (let i = 0; i < conexoes.length; i++) {
		let ori = getCellCoordenada(conexoes[i].origem),
			dest = getCellCoordenada(conexoes[i].destino),
			p1 = { x: ori.offsetLeft, y: ori.offsetTop },
			p2 = { x: dest.offsetLeft, y: dest.offsetTop };

		let length = Math.sqrt(Math.pow((p1.x - p2.x), 2) + Math.pow((p1.y - p2.y), 2));

		var angleDeg = Math.atan2(p2.y - p1.y, p2.x - p1.x) * 180 / Math.PI;

		let line = criarLinha(conexoes[i].id);

		line.style.zIndex = "-1";
		mapa.style.zIndex = 0;
		line.style.width = length + 'px';
		line.style.left = (p1.x + (ori.clientWidth / 2) + 5) + 'px';
		line.style.top = (p1.y + (dest.clientWidth / 2) + 5) + 'px';
		line.style.transform = "rotate(" + angleDeg + "deg)";

		mapa.appendChild(line);
	}
}

function criarLinha(id) {
	let div = document.createElement("DIV");
	div.id = "conx" + id;
	div.className = "linha";
	return div;
}

function getCellCoordenada(poste) {
	return document.getElementById("cell" + poste.coordenadaX + "x" + poste.coordenadaY);;
}

function tornarInvisivel(dom) {
	dom.style.display = "none";
}

function tornarVisivel(dom) {
	dom.style.display = "initial";
}

function ocultarConexoes() {
	limparCaminho();
	if (conexoes) {
		for (let i = 0; i < conexoes.length; i++) {
			tornarInvisivel(document.getElementById("conx" + conexoes[i].id))
		}
	}
}

function preencherCells() {
	for (let i = 0; i < postes.length; i++) {
		let p = postes[i];
		let cell = getCellCoordenada(p);
		if (cell) {
			cell.innerHTML = p.id;
			cell.onclick = function() {
				if (origem != p) {
					if (origem)
						getCellCoordenada(origem).style.backgroundColor = "black";
					origem = p;
				}
				cell.style.backgroundColor = "green";
				getMenorCaminho(p.id);
			}
			cell.style.backgroundColor = "black";
		}
	}
}

function getPostes() {
	makeRequest("GET", "", function() {
		let responseJson = JSON.parse(this.responseText);
		if (this.readyState == 4 && this.status == 200) {
			postes = responseJson;
			preencherCells();
		} else {
			let rj = JSON.parse(this.responseText);
			errorHandler(rj);
		}
	});
}

function getConexoes() {
	makeRequest("GET", "/conexoes", function() {
		let responseJson = JSON.parse(this.responseText);
		if (this.readyState == 4 && this.status == 200) {
			conexoes = responseJson;
			conectarCells();
		} else {
			let rj = JSON.parse(this.responseText);
			errorHandler(rj);
		}
	});
}

function limparCaminho() {
	if (cellsCaminho) {
		for (let i = 1; i < cellsCaminho.length - 1; i++) {
			cellsCaminho[i].style.backgroundColor = "black";
		}
	}
	cellsCaminho = [];

	if (conexoesCaminho) {
		for (let i = 0; i < conexoesCaminho.length; i++) {
			tornarInvisivel(document.getElementById("conx" + conexoesCaminho[i].id));
		}
	}
	conexoesCaminho = [];
	divCaminho.innerHTML = "";
	if (caminho) {
		p = caminho[caminho.length - 1];
		if (destino != p) {
			if (destino)
				getCellCoordenada(destino).style.backgroundColor = "black";
			destino = p;
		}
	}
}

function inserirPostes(listaPostes) {
	makeRequest("POST", "", async function() {
		if (this.readyState == 4 && this.status == 200) {
			getPostes();
		} else {
			let rj = JSON.parse(this.responseText);
			errorHandler(rj);
		}
	}, listaPostes);
}

function inserirConexoes(listaConexoes) {
	makeRequest("POST", "/conexoes", async function() {
		if (this.readyState == 4 && this.status == 200) {
			getConexoes();
		} else {
			let rj = JSON.parse(this.responseText);
			errorHandler(rj);
		}
	}, listaConexoes);
}

function getMenorCaminho(id) {
	makeRequest("GET", "/menorcaminho/" + id, function() {
		let responseJson = JSON.parse(this.responseText);
		if (this.readyState == 4 && this.status == 200) {
			caminho = responseJson;
			preencheCaminho();
		} else {
			let rj = JSON.parse(this.responseText);
			errorHandler(rj);
		}
	});
}

function preencheCaminho() {
	limparCaminho();
	let distanciaCaminho = 0;
	for (let i = 0; i < caminho.length; i++) {
		divCaminho.innerHTML += caminho[i].id;
		if (i != caminho.length - 1)
			divCaminho.innerHTML += " -> ";
		
		cellsCaminho.push(getCellCoordenada(caminho[i]));
	}

	for (let i = 0; i < caminho.length - 1; i++) {
		for (let j = 0; j < conexoes.length; j++) {
			let conexao = conexaoCaminho(conexoes[j], caminho[i].id, caminho[i + 1].id);
			if (conexao) {
				conexoesCaminho.push(conexao);
				distanciaCaminho += conexao.distancia;
				tornarVisivel(document.getElementById("conx" + conexao.id));
			}
		}
	}
	document.getElementById("distancia").innerHTML = distanciaCaminho;
	getCellCoordenada(destino).style.backgroundColor = "blue";
	for (let i = 1; i < cellsCaminho.length - 1; i++) {
		cellsCaminho[i].style.backgroundColor = "yellow";
	}

}

function conexaoCaminho(conexao, posteId1, posteId2) {
	if (conexao.origem.id == posteId1 && conexao.destino.id == posteId2
		|| conexao.origem.id == posteId2 && conexao.destino.id == posteId1) {
		return conexao;
	}
	return false;
}

function makeRequest(method, url, onloadend, data) {
	let xhr = new XMLHttpRequest();
	xhr.open(method, "http://localhost:8080/postes" + url, true);
	xhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
	if (data) {
		xhr.send(JSON.stringify(data));
	} else {
		xhr.send();
	}
	xhr.onloadend = onloadend;
}