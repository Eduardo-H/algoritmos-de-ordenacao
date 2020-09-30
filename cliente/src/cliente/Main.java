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
		socket = new Socket("localhost", 2800); // Cria o socket para enviar
		msgCliente = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		msgCliente.write(chave + ":" + valor); // Armazenda a mensagem a ser enviada
		msgCliente.write("\n"); // Fim da linha
		msgCliente.flush(); // Envia mensagem
	}
	
	public static String receberMensagem() throws IOException {
		leitorServidor = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String resposta = leitorServidor.readLine();
		socket.close(); // Fecha o socket ao receber uma mensagem
		return resposta;
	}
	
	public static void atribuicaoVetor(int tamanho) {
		for (int i = 0; i < tamanho; i++) {
			int numero = aleatorio.nextInt(tamanho);

			try {
				mandarMensagem("atribuicaoVetor", String.valueOf(numero));
				receberACK();
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
				receberACK();
			} catch (UnknownHostException e) {
				System.out.println(e);
			} catch (IOException e2) {
				System.out.println(e2);
			}
		}
	}

	public static void receberACK() throws IOException {
		while (true) { // Enquanto não receber um ACK		
			String msgServidor = receberMensagem();
			if (msgServidor.equals("ACK")){ // Se a mensagem recebida for um ACK
				System.out.println("Resposta do Servidor: " + msgServidor); // Mostra a mensagem							
				return;
			}
		}
	}
	
	public static int escolheTipoArm() {
		Scanner leitor = new Scanner(System.in);		
		System.out.println("---------------------------------------");
		System.out.println("|   Escolha o tipo de armazenamento   |");
		System.out.println("---------------------------------------");
		System.out.println("|1 - Vetor                            |");
		System.out.println("|2 - Lista encadeada                  |");
		System.out.println("|3 - Estrutura própia da linguagem    |");
		System.out.println("---------------------------------------");
		System.out.print("Sua escolha: ");
		return leitor.nextInt();
	}
	
	public static int escolheComplex() {
		Scanner leitor = new Scanner(System.in);			
		System.out.println("-----------------------------------");
		System.out.println("|   Escolha o tipo de ordenação   |");
		System.out.println("-----------------------------------");
		System.out.println("|1 - n​2                           |");
		System.out.println("|2 - n.log(n)                     |");
		System.out.println("-----------------------------------");
		System.out.print("Sua escolha: ");
		return leitor.nextInt();
	}
	
	public static void main(String[] args) throws IOException {

		while(true) {
			// Primeiro passo: escolha do tipo de armazenamento
			tipo = escolheTipoArm();
			
			// Segundo passo: enviar os valores
			if (tipo == 1 || tipo == 3) {
				atribuicaoVetor(250000);
			} else if (tipo == 2)			
				atribuicaoLista(250000);
			else
				System.out.println("Opção inválida.");
			
			// Teceiro passo: escolher a complexidade (caso tenha sido escolhido a opção 1 ou 2)
			if (tipo == 1) {
				escolha = escolheComplex();
				mandarMensagem("complexidadeVetor", String.valueOf(escolha));
				receberACK();
			} else if (tipo == 2) {
				escolha = escolheComplex();
				mandarMensagem("complexidadeLista", String.valueOf(escolha));
				receberACK();
			} else if (tipo == 3) {
				mandarMensagem("ordenar", "1");
				receberACK();
			} else {			
				System.out.println("Opção inválida.");
			}
			
			// Quarto passo: recebendo e mostrando os relatório de tempo e memória
			mandarMensagem("relatorio", "1");
			recebeu = false;
			
			while(!recebeu) {
				String msgServidor = receberMensagem();
				String resposta[] = msgServidor.split(":");
				if (resposta[1].contains("ACK")){ // Se a mensagem recebida conter ACK
					System.out.println("\nRelatório:");
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
				if (resposta[1].contains("ACK")){ // Se a mensagem recebida conter ACK
					System.out.println("Memória usada: " + Integer.valueOf(resposta[0])); // Mostra a memória
					recebeu = true; // Sai do WHILE aguardando a resposta	
				}
			}
			
		}
	}
}