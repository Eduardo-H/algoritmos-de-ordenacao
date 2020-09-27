package cliente;

import java.util.Scanner;
import java.util.Random;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {

	public static void main(String[] args) throws IOException {
		int escolha, tipo, numero;
		Scanner leitor = new Scanner(System.in);
		Random aleatorio = new Random();
		
		Socket socket;
		BufferedWriter msgCliente;
		BufferedReader leitorServidor;
		String msgServidor;
		
		while(true) {
			System.out.println("---------------------------------------");
			System.out.println("|   Escolha o tipo de armazenamento   |");
			System.out.println("---------------------------------------");
			System.out.println("|1 - Vetor                            |");
			System.out.println("|2 - Lista encadeada                  |");
			System.out.println("|3 - Estrutura pŕopia da linguagem    |");
			System.out.println("---------------------------------------");
			tipo = leitor.nextInt();
			socket = new Socket("localhost", 2800);
			
			msgCliente = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); 
			msgCliente.write(String.valueOf(tipo)); // Armazenda a mensagem a ser enviada
			msgCliente.write("\n"); //Fim da linha
			msgCliente.flush(); // Envia
			
			for (int i = 0; i < 25; i++) {
				numero = aleatorio.nextInt(1000000);
				
				try {
					System.out.println("Enviou " + numero);
					msgCliente = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); 
					msgCliente.write(String.valueOf(numero)); // Armazenda a mensagem a ser enviada
					msgCliente.write("\n"); // Fim da linha
					msgCliente.flush(); // Envia
					
					leitorServidor = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					msgServidor = leitorServidor.readLine(); // Lê a resposta
					System.out.println("Resposta do Servidor: " + msgServidor); // Mostra a mensagem
					// leitorServidor.close();
				} catch (UnknownHostException e) {
					System.out.println(e);
				} catch (IOException e2) {
					System.out.println(e2);
				}
			}
			
			System.out.println("-----------------------------------");
			System.out.println("|   Escolha o tipo de ordenação   |");
			System.out.println("-----------------------------------");
			System.out.println("|1 - n​2                           |");
			System.out.println("|2 - n.log(n)                     |");
			System.out.println("-----------------------------------");
			escolha = leitor.nextInt();
			
			switch (escolha) {
				case 1:
					msgCliente = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); 
					msgCliente.write(String.valueOf(escolha)); // Armazenda a mensagem a ser enviada
					msgCliente.write("\n"); //Fim da linha
					msgCliente.flush(); // Envia
					
					break;
					
				case 2:
					msgCliente = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); 
					msgCliente.write(String.valueOf(escolha)); // Armazenda a mensagem a ser enviada
					msgCliente.write("\n"); //Fim da linha
					msgCliente.flush(); // Envia
					
					break;
					
				default:
					System.out.println("Opção inválida");
					break;
			}
			
		}

	}

}