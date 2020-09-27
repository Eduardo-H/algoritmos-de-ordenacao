package servidor;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

	public static void ordenarBubbleVetor(int vetor[],int n) {
		int aux;
		boolean troca;
		
		for(int i=0; i<n-1; i++) {
			troca = false;
			for(int j=0; j<n-1; j++) {
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
		
		System.out.print("[");
		for (int i=0; i < n; i++) {
			if (i != n-1)
				System.out.print(vetor[i] + ", ");
			else
				System.out.print(vetor[i]);
		}
		System.out.print("]");
	}
	
	public static int[] ordenarQuickVetor(int vetor[], int inicio, int fim) {
		if (inicio < fim) {
			int pIndice = QuickTroca(vetor, inicio, fim);
			ordenarQuickVetor(vetor, inicio, pIndice - 1);
			ordenarQuickVetor(vetor, pIndice + 1, fim);
		}
		
		return vetor;
	}

	public static int QuickTroca(int vetor[], int inicio, int fim) {
		int aux;
		int pivot = vetor[fim];
		int pIndice = inicio;
		
		for (int i = inicio; i < fim; i++) {
			if (vetor[i] <= pivot) {
				aux = vetor[i];
				vetor[i] = vetor[pIndice];
				vetor[pIndice] = aux;
				pIndice++;
			}
		}
		aux = vetor[pIndice];
		vetor[pIndice] = vetor[fim];
		vetor[fim] = aux;
		return pIndice;
	}

	public static void main(String[] args) throws IOException {
		Socket socket;
		ServerSocket socketServidor = new ServerSocket(2800);;

		int vetor[] = new int[10];
		
		while (true) {
			System.out.println("Aguardando mensagem...");
			socket = socketServidor.accept(); // Aceitando uma requesição quando chegar
			
			BufferedReader requisicao = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String msgCliente = requisicao.readLine();
			
			DataOutputStream resposta = new DataOutputStream(socket.getOutputStream());
			if (Integer.valueOf(msgCliente) == 1) {
				for (int i = 0; i < 10; i++) {
					// System.out.println((i + 1) + " valores armazenados.");
					requisicao = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					msgCliente = requisicao.readLine();
					
					vetor[i] = Integer.valueOf(msgCliente);
					
					resposta.writeBytes("Valor recebido e armazenado: " + msgCliente);
					resposta.writeBytes("\n"); // Fim da linha
					resposta.flush(); // Manda para o cliente
				}
				
				// Mostrando o vetor desordenado
				System.out.print("[");
				for (int i = 0; i < 10; i++) {
					if (i != 9)
						System.out.print(vetor[i] + ", ");
					else
						System.out.print(vetor[i]);
				}
				System.out.print("]");
				System.out.println();
				
				requisicao = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				msgCliente = requisicao.readLine();
				
				switch (Integer.valueOf(msgCliente)) {
					case 1:
						ordenarBubbleVetor(vetor, 10);
						break;
					case 2:
						vetor = ordenarQuickVetor(vetor, 0, 9);
						
						System.out.print("[");
						for (int i = 0; i < 10; i++) {
							if (i != 9)
								System.out.print(vetor[i] + ", ");
							else
								System.out.print(vetor[i]);
						}
						System.out.print("]");
						System.out.println();
						break;
				}
			} else if (Integer.valueOf(msgCliente) == 2) {
				for (int i = 0; i < 10; i++) {
					requisicao = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					msgCliente = requisicao.readLine();
					
					resposta.writeBytes("Valor recebido e armazenado: " + msgCliente);
					resposta.writeBytes("\n"); // Fim da linha
					resposta.flush(); // Manda para o cliente
				}
				
				requisicao = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				msgCliente = requisicao.readLine();
				
				switch (Integer.valueOf(msgCliente)) {
					case 1:
						System.out.print("[");
						for (int i = 0; i < 10; i++) {
							System.out.print(vetor[i] + ", ");
						}
						System.out.print("]");
						System.out.println();
						
						ordenarBubbleVetor(vetor, 10);
						break;
					case 2:
						System.out.println("Escolheu n.log(n)");
						break;
				}
			} else if (Integer.valueOf(msgCliente) == 3) {
				for (int i = 0; i < 10; i++) {
					requisicao = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					msgCliente = requisicao.readLine();
					
					resposta.writeBytes("Valor recebido e armazenado: " + msgCliente);
					resposta.writeBytes("\n"); // Fim da linha
					resposta.flush(); // Manda para o cliente
				}
				
				requisicao = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				msgCliente = requisicao.readLine();
				
				switch (Integer.valueOf(msgCliente)) {
					case 1:
						System.out.print("[");
						for (int i = 0; i < 10; i++) {
							System.out.print(vetor[i] + ", ");
						}
						System.out.print("]");
						System.out.println();
						
						ordenarBubbleVetor(vetor, 10);
						break;
					case 2:
						System.out.println("Escolheu n.log(n)");
						break;
				}
			}
						
			System.out.println();
		}
	}
}
