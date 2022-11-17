class Estatisticas2 {
	private int[] lista;
	private int soma = 0;
	
	Estatisticas2(int[] lista) {
		this.lista = lista;
	}
	
	public synchronized void media() {	
		if(this.soma == 0)
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
		System.out.println(" Media " + this.soma/this.lista.length);
	}
	
	public synchronized void soma() {
		int sum = 0;
		
		for(int i = 0; i < lista.length; i++) {
			sum += this.lista[i];
		}
		
		this.soma = sum;
		
		notify();
	}
	
}

class Media2 extends Thread {
	Estatisticas2 estatisticas;
	
	Media2(Estatisticas2 estatisticas) {
		System.out.println(" Thread da media");
		
		this.estatisticas = estatisticas;
	}
	
	public void run() {
		this.estatisticas.media();
	}
}

class Soma extends Thread {
	Estatisticas2 estatisticas;
	
	Soma(Estatisticas2 estatisticas) {
		System.out.println(" Thread da soma");
		
		this.estatisticas = estatisticas;
	}
	
	public void run() {
		this.estatisticas.soma();;
	}
}

public class Exe12 {
	public static void main(String[] args) {
		int[] lista = { 3, 3 };
		Estatisticas2 estatisticas = new Estatisticas2(lista);
		Media2 media = new Media2(estatisticas);
		Soma soma = new Soma(estatisticas);
		
		soma.start();
		media.start();

		try {
			media.join();
			soma.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

