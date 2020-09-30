package servidor;

public class Elemento {
	private int valor;
	private Elemento prox;
	
	public Elemento() {
		
	}
	
	public Elemento(int valor, Elemento prox) {
		this.valor = valor;
		this.prox = prox;
	}
	
	public Elemento(int valor) {
		this.valor = valor;
		this.prox = null;
	}
	
	public int getValor() {
		return valor;
	}
	
	public void setValor(int valor) {
		this.valor = valor;
	}
	
	public Elemento getProx() {
		return prox;
	}
	
	public void setProx(Elemento prox) {
		this.prox = prox;
	}
}
