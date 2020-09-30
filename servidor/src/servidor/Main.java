package servidor;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

	static Socket socket;
	static ServerSocket socketServidor;
	static long inicio, tempo, memoria;
	static Runtime rt;
	
	public static int[] ordenarBubbleVetor(int vetor[],int n) {
		int aux;
		boolean troca;
		
		for(int i = 0; i < n-1; i++) {
			troca = false;
			for(int j=0; j < n-1; j++) {
				if(vetor[j] > vetor[(j+1)]) {
					aux = vetor[j];
					vetor[j] = vetor[(j+1)];
					vetor[(j+1)] = aux;
					troca = true;
				}
			}
			if(troca == false)
				break;
		}
		
		return vetor;
	}
	
	public static int[] ordenarQuickVetor(int vetor[], int inicio, int fim) {
		if (inicio < fim) {
			int pIndice = quickTrocaVetor(vetor, inicio, fim);
			ordenarQuickVetor(vetor, inicio, pIndice - 1);
			ordenarQuickVetor(vetor, pIndice + 1, fim);
		}
		
		return vetor;
	}

	public static int quickTrocaVetor(int vetor[], int inicio, int fim) {
		int aux;
		int pivot = vetor[fim];
		int pIndice = inicio;
		
		for (int i = inicio; i < fim; i++) {
			if (vetor[i] <= pivot) {
				aux = vetor[i];
				vetor[i] = vetor[pIndice];
				vetor[pIndice] = aux;
				pIndice ++;
			}
		}
		aux = vetor[pIndice];
		vetor[pIndice] = vetor[fim];
		vetor[fim] = aux;
		
		return pIndice;
	}
	
	public static Elemento ordenarBubbleLista(Elemento elemento) {
		Elemento primeiroElemento = elemento;
		Elemento elemento_aux = elemento;
		boolean troca;
		int aux;
		
		while (elemento.getProx() != null) {
			troca = false;
			while (elemento_aux.getProx() != null) {
				if(elemento_aux.getValor() > elemento_aux.getProx().getValor()) {
					aux = elemento_aux.getValor();
					elemento_aux.setValor(elemento_aux.getProx().getValor());
					elemento_aux.getProx().setValor(aux);
					troca = true;
				}
				elemento_aux = elemento_aux.getProx();
			}
			if (troca == false)
				break;
		}
		
		return primeiroElemento;
	}
	
	public static Elemento quickSortLista(Elemento elemento) {
		if (elemento == null)
			return null;

		Elemento elementoAux = elemento;
		Elemento proxElementoAux = elemento.getProx();
		Elemento pivo = elemento;
		Elemento temporario = null;
		Elemento particao = null;
		int temp;

		while (proxElementoAux != null) {
			if (proxElementoAux.getValor() < pivo.getValor()) {
				temporario = elementoAux;
				particao = elementoAux.getProx();

				elementoAux = elementoAux.getProx();

				temp = elementoAux.getValor();
				elementoAux.setValor(proxElementoAux.getValor());
				proxElementoAux.setValor(temp);
			}
			proxElementoAux = proxElementoAux.getProx();
		}

		if (elementoAux != elemento) {
			temp = elementoAux.getValor();
			elementoAux.setValor(pivo.getValor());
			pivo.setValor(temp);

			temporario.setProx(null);
			quickSortLista(elemento);
			temporario.setProx(particao);
		}

		quickSortLista(elementoAux.getProx());

		return elemento;
	}
	
	public static List<Elemento> ordenarQuickLista(List<Elemento> lista, int inicio, int fim) {
		if (inicio < fim) {
			int pIndice = quickTrocaLista(lista, inicio, fim);
			ordenarQuickLista(lista, inicio, pIndice - 1);
			ordenarQuickLista(lista, pIndice + 1, fim);
		}
		
		return lista;
	}

	public static int quickTrocaLista(List<Elemento> lista, int inicio, int fim) {
		int aux;
		int pivot = lista.get(fim).getValor();
		int pIndice = inicio;
		
		for (int i = inicio; i < fim; i++) {
			if (lista.get(i).getValor() <= pivot) {
				aux = lista.get(i).getValor();
				lista.get(i).setValor(lista.get(pIndice).getValor());
				lista.get(pIndice).setValor(aux);
				pIndice ++;
			}
		}
		
		aux = lista.get(pIndice).getValor();
		lista.get(pIndice).setValor(lista.get(fim).getValor());
		lista.get(fim).setValor(aux);
		
		return pIndice;
	}
	
	public static void imprimirVetor(int[] vetor) {
		System.out.print("[");
		for (int i = 0; i < vetor.length; i++) {
			if (i != vetor.length - 1)
				System.out.print(vetor[i] + ", ");
			else
				System.out.print(vetor[i]);
		}
		System.out.print("]\n");
	}
	
	public static void imprimirLista(Elemento elemento) {
		System.out.print("[");
		while (elemento.getProx() != null) {
			System.out.print(elemento.getValor() + ", ");
			elemento = elemento.getProx();
		}
		System.out.print(elemento.getValor());
		System.out.print("]\n");
	}
	
	public static int[] atribuirVetor(int[] vetor, String valor, int i) throws IOException {
		// Atribui o valor recebido na posição atual
		vetor[i] = Integer.valueOf(valor);
		
		// Responde o cliente com um ACK
		mandarACK();
		
		return vetor;
	}
	
	public static Elemento atribuirLista(Elemento elemento, String valor) throws IOException {		
		// Atribuindo os valores recebidos na lista
		if (elemento.getValor() == -1) {
			elemento.setValor(Integer.valueOf(valor));
		} else {
			elemento.setProx(new Elemento(Integer.valueOf(valor)));
			elemento = elemento.getProx();
		}
		// Responde o cliente com um ACK
		mandarACK();

		return elemento;
	}
	
	public static void mandarACK() throws IOException {
		DataOutputStream resposta = new DataOutputStream(socket.getOutputStream());
		
		resposta.writeBytes("ACK"); // Conteúdo da mensagem
		resposta.writeBytes("\n"); // Fim da linha
		resposta.flush(); // Manda para o cliente
	}
	
	public static void mandarMensagem(String mensagem) throws IOException {
		DataOutputStream resposta = new DataOutputStream(socket.getOutputStream());
		
		resposta.writeBytes(mensagem + ":ACK"); // Conteúdo da mensagem
		resposta.writeBytes("\n"); // Fim da linha
		resposta.flush(); // Manda para o cliente
	}
	
	public static void main(String[] args) throws IOException {
		socketServidor = new ServerSocket(2800);
		int vetor[] = new int[250000];
		int indice = 0;
		Elemento primeiroElemento = null;
		Elemento elemento = new Elemento(-1);
		
		System.out.println("Aguardando mensagem...");
		while (true) {
			socket = socketServidor.accept(); // Aceitando uma requesição quando chegar

			BufferedReader requisicao = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String msgCliente = requisicao.readLine();
			String escolha[] = new String[2];
			escolha = msgCliente.split(":");
			
			if (escolha[0].contentEquals("atribuicaoVetor")) {
				vetor = atribuirVetor(vetor, escolha[1], indice);
				indice++;
				
				// Caso o indice tenha chego no fim do vetor
				if (indice == vetor.length)
					indice = 0; // Reseta o indice para possível atribuição futura
			} else if (escolha[0].contentEquals("atribuicaoLista")) {
				if (indice == 0) {
					primeiroElemento = atribuirLista(elemento, escolha[1]);
					elemento = primeiroElemento;
					indice ++;
				} else {
					elemento = atribuirLista(elemento, escolha[1]);
				}
			} else if (escolha[0].contentEquals("complexidadeVetor")) {
				if (escolha[1].contentEquals("1")) {
					mandarACK();
					// imprimirVetor(vetor);
					rt = Runtime.getRuntime();
					inicio = System.currentTimeMillis();
					vetor = ordenarBubbleVetor(vetor, vetor.length);
					tempo = System.currentTimeMillis() - inicio;
					memoria = (Runtime.getRuntime().freeMemory() - rt.freeMemory());
					// imprimirVetor(vetor);
				} else if (escolha[1].contentEquals("2")) {
					mandarACK();
					// imprimirVetor(vetor);
					rt = Runtime.getRuntime();
					inicio = System.currentTimeMillis();
					vetor = ordenarQuickVetor(vetor, 0, vetor.length - 1);
					tempo = System.currentTimeMillis() - inicio;
					memoria = (Runtime.getRuntime().freeMemory() - rt.freeMemory());
					// imprimirVetor(vetor);
				}
			} else if (escolha[0].contentEquals("complexidadeLista")) {
				if (escolha[1].contentEquals("1")) {
					mandarACK();
					// imprimirLista(primeiroElemento);
					rt = Runtime.getRuntime();
					inicio = System.currentTimeMillis();
					primeiroElemento = ordenarBubbleLista(primeiroElemento);
					tempo = System.currentTimeMillis() - inicio;
					memoria = (Runtime.getRuntime().freeMemory() - rt.freeMemory());
					// imprimirLista(primeiroElemento);
				} else if (escolha[1].contentEquals("2")) {
					mandarACK();
					// imprimirLista(primeiroElemento);
					rt = Runtime.getRuntime();
					inicio = System.currentTimeMillis();
					primeiroElemento = quickSortLista(primeiroElemento);
					tempo = System.currentTimeMillis() - inicio;
					memoria = (Runtime.getRuntime().freeMemory() - rt.freeMemory());
					// imprimirLista(primeiroElemento);
				}
			} else if (escolha[0].contentEquals("ordenar")) {
				mandarACK();
				// imprimirVetor(vetor);
				rt = Runtime.getRuntime();
				inicio = System.currentTimeMillis();
				Arrays.sort(vetor); // Estrutura de ordenação da linguagem
				tempo = System.currentTimeMillis() - inicio;
				memoria = (Runtime.getRuntime().freeMemory() - rt.freeMemory());
				// imprimirVetor(vetor);
			} else if (escolha[0].contentEquals("relatorio")) {
				if (escolha[1].contentEquals("1"))
					mandarMensagem(String.valueOf(tempo));
				else if (escolha[1].contentEquals("2"))
					mandarMensagem(String.valueOf(memoria));
			}
		}
	}
}