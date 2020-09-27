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
					aux=vetor[j];
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
			System.out.print(vetor[i] + ", ");
		}
		System.out.print("]");
	}

	public static void main(String[] args) throws IOException {
		Socket socket;
		ServerSocket socketServidor = new ServerSocket(2800);;

		int vetor[] = new int[25];
		
		while (true) {
			System.out.println("Aguardando mensagem...");
			socket = socketServidor.accept(); // Aceitando uma requesição quando chegar
			
			BufferedReader requisicao = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String msgCliente = requisicao.readLine();
			
			DataOutputStream resposta = new DataOutputStream(socket.getOutputStream());
			if (Integer.valueOf(msgCliente) == 1) {
				for (int i = 0; i < 25; i++) {
					System.out.println((i + 1) + " valores armazenados.");
					requisicao = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					msgCliente = requisicao.readLine();
					
					vetor[i] = Integer.valueOf(msgCliente);
					
					resposta.writeBytes("Valor recebido e armazenado: " + msgCliente);
					resposta.writeBytes("\n"); // Fim da linha
					resposta.flush(); // Manda para o cliente
				}
			} else if (Integer.valueOf(msgCliente) == 2) {
				for (int i = 0; i < 25; i++) {
					requisicao = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					msgCliente = requisicao.readLine();
					
					resposta.writeBytes("Valor recebido e armazenado: " + msgCliente);
					resposta.writeBytes("\n"); // Fim da linha
					resposta.flush(); // Manda para o cliente
				}
			} else if (Integer.valueOf(msgCliente) == 3) {
				for (int i = 0; i < 25; i++) {
					requisicao = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					msgCliente = requisicao.readLine();
					
					resposta.writeBytes("Valor recebido e armazenado: " + msgCliente);
					resposta.writeBytes("\n"); // Fim da linha
					resposta.flush(); // Manda para o cliente
				}
			}
			
			requisicao = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			msgCliente = requisicao.readLine();
			
			switch (Integer.valueOf(msgCliente)) {
				case 1:
					System.out.print("[");
					for (int i = 0; i < 25; i++) {
						System.out.print(vetor[i] + ", ");
					}
					System.out.print("]");
					
					ordenarBubbleVetor(vetor, 25);
					break;
				case 2:
					System.out.println("Escolheu n.log(n)");
					break;
			}
		}
	}
}
