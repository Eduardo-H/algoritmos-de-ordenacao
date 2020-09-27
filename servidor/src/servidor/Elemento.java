package servidor;

public class Elemento {
	private int valor;
	private int posicao;
	private Elemento prox;
	
	public Elemento() {
		
	}
	
	public Elemento(int valor, int posicao, Elemento prox) {
		this.valor = valor;
		this.posicao = posicao;
		this.prox = prox;
	}
	
	public Elemento(int valor, int posicao) {
		this.valor = valor;
		this.posicao = posicao;
		this.prox = null;
	}
	
	public int getValor() {
		return valor;
	}
	
	public void setValor(int valor) {
		this.valor = valor;
	}
	
	public int getPosicao() {
		return posicao;
	}
	
	public void setPosicao(int posicao) {
		this.posicao = posicao;
	}
	
	public Elemento getProx() {
		return prox;
	}
	
	public void setProx(Elemento prox) {
		this.prox = prox;
	}
}
