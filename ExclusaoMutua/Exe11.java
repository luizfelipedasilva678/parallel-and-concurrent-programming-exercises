class Estatisticas {
	private int[] lista;
	
	Estatisticas(int[] lista) {
		this.lista = lista;
	}
	
	public synchronized void media() {
		int sum = 0;
		
		for(int i = 0; i < lista.length; i++) {
			sum += this.lista[i];
		}
		
		System.out.println(" Media " + sum/this.lista.length);
	}
	
	public synchronized void maior_numero() {
		int maior = this.lista[0];
		
		for(int i = 0; i < lista.length; i++) {
			if(this.lista[i] > maior) {
				maior = this.lista[i];
			}
		}
		
		System.out.println(" Maior " + maior);
	}
	
	public synchronized void menor_numero() {
		int menor = this.lista[0];
		
		for(int i = 0; i < lista.length; i++) {
			if(this.lista[i] < menor) {
				menor = this.lista[i];
			}
		}
		
		System.out.println(" Menor " + menor);
	}
	
}

class Media extends Thread {
	Estatisticas estatisticas;
	
	Media(Estatisticas estatisticas) {
		this.estatisticas = estatisticas;
	}
	
	public void run() {
		this.estatisticas.media();
	}
}

class MaiorNumero extends Thread {
	Estatisticas estatisticas;
	
	MaiorNumero(Estatisticas estatisticas) {
		this.estatisticas = estatisticas;
	}
	
	public void run() {
		this.estatisticas.maior_numero();
	}
}


class MenorNumero extends Thread {
	Estatisticas estatisticas;
	
	MenorNumero(Estatisticas estatisticas) {
		this.estatisticas = estatisticas;
	}
	
	public void run() {
		this.estatisticas.menor_numero();
	}
}


public class Exe11 {
	public static void main(String[] args) {
		int[] lista = {1,2,3,4,5, 70, 30, 12, 30, 100};
		Estatisticas estatisticas = new Estatisticas(lista);
		Media media = new Media(estatisticas);
		MaiorNumero maiorNumero = new MaiorNumero(estatisticas);
		MenorNumero menorNumero = new MenorNumero(estatisticas);
		
		media.start();
		maiorNumero.start();
		menorNumero.start();

		try {
			media.join();
			maiorNumero.join();
			menorNumero.join();	
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
