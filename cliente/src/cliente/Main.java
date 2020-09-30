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

public class Main{
	
	static int escolha, tipo, numero;
	static Random aleatorio = new Random();
	static BufferedWriter msgCliente;
	static BufferedReader leitorServidor;
	static boolean recebeu;
	static Socket socket;
	
	public static void mandarMensagem(String chave, String valor) throws UnknownHostException, IOException {
		socket = new Socket("localhost", 2800); //CRIA O SOCKET PARA ENVIAR	 (LB)
		msgCliente = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		msgCliente.write(chave + ":" + valor); // Armazenda a mensagem a ser enviada
		msgCliente.write("\n"); // Fim da linha
		msgCliente.flush(); // Envia mensagem
	}
	
	public static String receberMensagem() throws IOException {
		leitorServidor = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String resposta = leitorServidor.readLine();
		socket.close(); //FECHA O SOCKET AO RECEBER UMA RESPOSTA (LB)
		return resposta;
	}
	
	public static void atribuicaoVetor(int tamanho) {
		for (int i = 0; i < tamanho; i++) {
			int numero = aleatorio.nextInt(tamanho);

			try {
				mandarMensagem("atribuicaoVetor", String.valueOf(numero));
				System.out.println("Número enviado: " + numero);
				recebeu = false; //AO ENVIAR, COLOCA O RECEBEU COMO FALSO (LB)
				
				while (!recebeu) { //ENQUANTO NAO RECEBER UM RECONHECIMENTO	(LB)		
					String msgServidor = receberMensagem();
					if (msgServidor.equals("ACK")){ //SE A MENSAGEM RECEBIDA FOR ACK (LB)
						System.out.println("Resposta do Servidor: " + msgServidor); // Mostra a mensagem							
						recebeu = true; //SAI DO WHILE AGUARDANDO RESPOSTA	(LB)	
					}
				}
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
				recebeu = false; //AO ENVIAR, COLOCA O RECEBEU COMO FALSO (LB)
				
				while (!recebeu) { //ENQUANTO NAO RECEBER UM RECONHECIMENTO	(LB)		
					String msgServidor = receberMensagem();
					if (msgServidor.equals("ACK")){ //SE A MENSAGEM RECEBIDA FOR ACK (LB)
						System.out.println("Resposta do Servidor: " + msgServidor); // Mostra a mensagem							
						recebeu = true; //SAI DO WHILE AGUARDANDO RESPOSTA	(LB)	
					}
				}
			} catch (UnknownHostException e) {
				System.out.println(e);
			} catch (IOException e2) {
				System.out.println(e2);
			}
		}
	}

	
	public static int escolheTipoArm() { //(LB)
		Scanner leitor = new Scanner(System.in);		
		System.out.println("---------------------------------------");
		System.out.println("|   Escolha o tipo de armazenamento   |");
		System.out.println("---------------------------------------");
		System.out.println("|1 - Vetor                            |");
		System.out.println("|2 - Lista encadeada                  |");
		System.out.println("|3 - Estrutura própia da linguagem    |");
		System.out.println("---------------------------------------");
		return leitor.nextInt();
	}
	
	public static int escolheComplex() {//(LB)
		Scanner leitor = new Scanner(System.in);			
		System.out.println("-----------------------------------");
		System.out.println("|   Escolha o tipo de ordenação   |");
		System.out.println("-----------------------------------");
		System.out.println("|1 - n​2                           |");
		System.out.println("|2 - n.log(n)                     |");
		System.out.println("-----------------------------------");
		return leitor.nextInt();
	}
	
	public static void main(String[] args) throws IOException {

		while(true) {
			// Primeiro passo: escolha do tipo de armazenamento
			tipo = escolheTipoArm();
			
			// Segundo passo: enviar os valores
			if (tipo == 1 || tipo == 3) {
				atribuicaoVetor(20);
			} else if (tipo == 2)			
				atribuicaoLista(20);
			else
				System.out.println("Opção inválida.");
			
			// Teceiro passo: escolher a complexidade
			escolha = escolheComplex();
			
			if (tipo == 1) {
				mandarMensagem("complexidadeVetor", String.valueOf(escolha));
				recebeu = false; // AO ENVIAR, COLOCA O RECEBEU COMO FALSO (LB)
				
				while (!recebeu) { // ENQUANTO NAO RECEBER UM RECONHECIMENTO (LB)		
					String msgServidor = receberMensagem();
					if (msgServidor.equals("ACK")){ // SE A MENSAGEM RECEBIDA FOR ACK (LB)
						System.out.println("Resposta do Servidor: " + msgServidor); // Mostra a mensagem							
						recebeu = true; // SAI DO WHILE AGUARDANDO RESPOSTA	(LB)	
					}
				}
			} else if (tipo == 2) { 
				mandarMensagem("complexidadeLista", String.valueOf(escolha));
				recebeu = false; // AO ENVIAR, COLOCA O RECEBEU COMO FALSO (LB)
				
				while (!recebeu) { // ENQUANTO NAO RECEBER UM RECONHECIMENTO (LB)		
					String msgServidor = receberMensagem();
					if (msgServidor.equals("ACK")){ // SE A MENSAGEM RECEBIDA FOR ACK (LB)
						System.out.println("Resposta do Servidor: " + msgServidor); // Mostra a mensagem							
						recebeu = true; // SAI DO WHILE AGUARDANDO RESPOSTA	(LB)	
					}
				}
			} else {
				System.out.println("Opção inválida.");
			}
			
			// Mandando uma mensagem para o servidor pedindo do relatório de tempo
			mandarMensagem("relatorio", "1");
			recebeu = false;
			
			while(!recebeu) {
				String msgServidor = receberMensagem();
				String resposta[] = msgServidor.split(":");
				if (resposta[1].contains("ACK")){ // Se a mensagem recebida conter tempo
					System.out.println("Tempo de execução milisegundos: " + Integer.valueOf(resposta[0]) + "ms"); // Mostra o tempo
					System.out.println("Tempo de execução segundos: " + Integer.valueOf(resposta[0])/1000 + "s"); // Mostra o tempo
					recebeu = true; // Sai do WHILE aguardando a resposta	
				}
			}
			
			// Mandando uma mensagem para o servidor pedindo do relatório de memória
			mandarMensagem("relatorio", "2");
			recebeu = false;
			
			while(!recebeu) {
				String msgServidor = receberMensagem();
				String resposta[] = msgServidor.split(":");
				if (resposta[1].contains("ACK")){ // Se a mensagem recebida conter tempo
					System.out.println("Memória usada: " + Integer.valueOf(resposta[0])); // Mostra a memória
					recebeu = true; // Sai do WHILE aguardando a resposta	
				}
			}
			
		}
	}
}