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
	
	public static List<Elemento> ordenarBubbleLista(List<Elemento> lista, int n) {
		int aux;
		boolean troca;
		
		for(int i = 0; i < n-1; i++) {
			troca = false;
			for(int j = 0; j < n-1; j++) {
				if(lista.get(j).getValor() > lista.get(j+1).getValor()) {
					aux = lista.get(j).getValor();
					lista.get(j).setValor(lista.get(j+1).getValor());
					lista.get(j+1).setValor(aux);
					troca = true;
				}
			}
			if(troca == false)
				break;
		}
		
		return lista;
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
		System.out.print("]");
	}
	
	public static void imprimirLista(List<Elemento> lista) {
		System.out.print("[");
		for (int i = 0; i < lista.size(); i++) {
			if (i != lista.size() - 1)
				System.out.print(lista.get(i).getValor() + ", ");
			else
				System.out.print(lista.get(i).getValor());
		}
		System.out.print("]");
	}
	
	public static void main(String[] args) throws IOException {
		Socket socket;
		ServerSocket socketServidor = new ServerSocket(2800);;
		long inicio, fim;
		Runtime rt;
		
		while (true) {
			System.out.println("Aguardando mensagem...");
			socket = socketServidor.accept(); // Aceitando uma requesição quando chegar
			
			BufferedReader requisicao = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String msgCliente = requisicao.readLine();
			
			DataOutputStream resposta = new DataOutputStream(socket.getOutputStream());
			
			// Verificando qual tipo de estrura o cliente escolheu
			if (Integer.valueOf(msgCliente) == 1) {
				int vetor[] = new int[20];
				
				// Recebendo os valores do cliente
				for (int i = 0; i < vetor.length; i++) {
					requisicao = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					msgCliente = requisicao.readLine();
					
					vetor[i] = Integer.valueOf(msgCliente);
					
					resposta.writeBytes("Valor " + msgCliente + " recebido e armazenado");
					resposta.writeBytes("\n"); // Fim da linha
					resposta.flush(); // Manda para o cliente
				}
				
				// Imprimindo o vetor desordenado
				imprimirVetor(vetor);
				System.out.println();
				
				requisicao = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				msgCliente = requisicao.readLine();
				
				switch (Integer.valueOf(msgCliente)) {
					case 1:
						rt = Runtime.getRuntime();
						inicio = System.currentTimeMillis();
						vetor = ordenarBubbleVetor(vetor, vetor.length);
						fim = System.currentTimeMillis() - inicio;
						System.out.println("Memória usada: " + (Runtime.getRuntime().freeMemory() - rt.freeMemory()));
						
						// Imprimindo o vetor ordenado
						imprimirVetor(vetor);
						System.out.println();
						
						System.out.println("Tempo para ordenação em milisegundos: " + fim + "ms\n"
								+ "Tempo para ordenação em segundos: " + fim/1000 + "s");
						
						break;
					case 2:
						inicio = System.currentTimeMillis();
						vetor = ordenarQuickVetor(vetor, 0, vetor.length - 1);
						fim = System.currentTimeMillis() - inicio;
						
						// Imprimindo o vetor ordenado
						imprimirVetor(vetor);
						System.out.println();
						
						System.out.println("Tempo para ordenação em milisegundos: " + fim + "ms\n"
								+ "Tempo para ordenação em segundos: " + fim/1000 + "s");
						break;
				}
			} else if (Integer.valueOf(msgCliente) == 2) {
				Elemento elemento = new Elemento(-1, 0);
				List<Elemento> lista = new ArrayList<Elemento>();
				
				// Recebendo os valores do cliente
				for (int i = 0; i < 20; i++) {
					requisicao = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					msgCliente = requisicao.readLine();
					
					// Atribuindo os valores recebidos na lista
					if (elemento.getValor() == -1) {
						elemento.setValor(Integer.valueOf(msgCliente));
						elemento.setPosicao(i);
						lista.add(elemento);
					} else {
						Elemento novo_elemento = new Elemento(Integer.valueOf(msgCliente), i);
						elemento.setProx(novo_elemento);
						lista.add(novo_elemento);
						elemento = novo_elemento;
					}

					resposta.writeBytes("Valor " + msgCliente + " recebido e armazenado");
					resposta.writeBytes("\n"); // Fim da linha
					resposta.flush(); // Manda para o cliente
				}
				
				// Imprimindo a lista desordenada
				imprimirLista(lista);
				System.out.println();
				
				requisicao = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				msgCliente = requisicao.readLine();
				
				switch (Integer.valueOf(msgCliente)) {
					case 1:
						inicio = System.currentTimeMillis();
						lista = ordenarBubbleLista(lista, lista.size());
						fim = System.currentTimeMillis() - inicio;
						
						// Imprimindo a lista ordenada
						imprimirLista(lista);
						System.out.println();
						
						System.out.println("Tempo para ordenação em milisegundos: " + fim + "ms\n"
								+ "Tempo para ordenação em segundos: " + fim/1000 + "s");
						break;
					case 2:
						inicio = System.currentTimeMillis();
						lista = ordenarQuickLista(lista, 0, lista.size() - 1);
						fim = System.currentTimeMillis() - inicio;
						
						// Imprimindo a lista ordenada
						imprimirLista(lista);
						System.out.println();
						
						System.out.println("Tempo para ordenação em milisegundos: " + fim + "ms\n"
								+ "Tempo para ordenação em segundos: " + fim/1000 + "s");
						break;
				}
			} else if (Integer.valueOf(msgCliente) == 3) {
				int vetor[] = new int[20];
				
				for (int i = 0; i < vetor.length; i++) {
					requisicao = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					msgCliente = requisicao.readLine();

					vetor[i] = Integer.valueOf(msgCliente);

					resposta.writeBytes("Valor " + msgCliente + " recebido e armazenado");
					resposta.writeBytes("\n"); // Fim da linha
					resposta.flush(); // Manda para o cliente
				}
				
				// Imprimindo o vetor desordenado
				imprimirVetor(vetor);
				System.out.println();
				
				inicio = System.currentTimeMillis();
				Arrays.sort(vetor); // Estrutura de ordenação da linguagem
				fim = System.currentTimeMillis() - inicio;
				
				// Imprimindo o vetor ordenado
				imprimirVetor(vetor);
				System.out.println();
				
				System.out.println("\nTempo para ordenação em milisegundos: " + fim + "\n"
						+ "Tempo para ordenação em segundos: " + fim/1000);
			}
						
			System.out.println();
		}
	}
}
