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
	
	static int escolha, tipo, numero;
	static Random aleatorio = new Random();
	static BufferedWriter msgCliente;
	static BufferedReader leitorServidor;
	static String msgServidor;
	static boolean recebeu;
	static Socket socket;
	
	public static void mandarMensagem(String chave, String valor) throws UnknownHostException, IOException {
		msgCliente = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		msgCliente.write(chave + ":" + valor); // Armazenda a mensagem a ser enviada
		msgCliente.write("\n"); // Fim da linha
		msgCliente.flush(); // Envia mensagem
	}
	
	public static void atribuicaoVetor(int tamanho) {
		for (int i = 0; i < tamanho; i++) {
			int numero = aleatorio.nextInt(tamanho);

			try {
				mandarMensagem("atribuicaoVetor", String.valueOf(numero));
				System.out.println("Número enviado: " + numero);
				
				msgServidor = leitorServidor.readLine(); // Lê a resposta
				System.out.println("Resposta do Servidor: " + msgServidor); // Mostra a mensagem
			} catch (UnknownHostException e) {
				System.out.println(e);
			} catch (IOException e2) {
				System.out.println(e2);
			}
		}
	}
	
	public static void atribuicaoLista(int tamanho) {
		for (int i = 0; i < tamanho; i++) {
			int numero = aleatorio.nextInt(tamanho);

			try {
				mandarMensagem("atribuicaoLista", String.valueOf(numero));
				System.out.println("Número enviado: " + numero);
				
				msgServidor = leitorServidor.readLine(); // Lê a resposta
				System.out.println("Resposta do Servidor: " + msgServidor); // Mostra a mensagem
			} catch (UnknownHostException e) {
				System.out.println(e);
			} catch (IOException e2) {
				System.out.println(e2);
			}
		}
	}
		
	public static void main(String[] args) throws IOException {
		Scanner leitor = new Scanner(System.in);
		
		while(true) {
			System.out.println("---------------------------------------");
			System.out.println("|   Escolha o tipo de armazenamento   |");
			System.out.println("---------------------------------------");
			System.out.println("|1 - Vetor                            |");
			System.out.println("|2 - Lista encadeada                  |");
			System.out.println("|3 - Estrutura própia da linguagem    |");
			System.out.println("---------------------------------------");
			tipo = leitor.nextInt();
			socket = new Socket("localhost", 2800);
			
			mandarMensagem("atribuicaoVetor", "1");
			
			if (tipo == 1 || tipo == 3) {
				atribuicaoVetor(20);
			
				System.out.println("-----------------------------------");
				System.out.println("|   Escolha o tipo de ordenação   |");
				System.out.println("-----------------------------------");
				System.out.println("|1 - n​2                           |");
				System.out.println("|2 - n.log(n)                     |");
				System.out.println("-----------------------------------");
				escolha = leitor.nextInt();
				
				if (escolha == 1 || escolha == 2) {
					mandarMensagem("complexidade", String.valueOf(escolha));
				} else {
					System.out.println("Opção inválida.");
				}
			} else if (tipo == 2)
				atribuicaoLista(20);
			else
				System.out.println("Opção inválida.");
		}
	}
}
