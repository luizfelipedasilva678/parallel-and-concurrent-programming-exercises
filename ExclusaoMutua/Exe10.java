import java.util.concurrent.Semaphore;

class Incrementador extends Thread {
	Semaphore semaphore;
	int[] qtd;
	
	Incrementador(Semaphore semaphore, int[] qtd) {
		this.semaphore = semaphore;
		this.qtd = qtd;
	}
	
	public void run() {
		try {
			this.semaphore.acquire();
			for(int i = 0; i < 5; i++) {
				this.qtd[0]++;				
			}
			this.semaphore.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class Decrementador extends Thread {
	Semaphore semaphore;
	int[] qtd;
	
	Decrementador(Semaphore semaphore, int[] qtd) {
		this.semaphore = semaphore;
		this.qtd = qtd;
	}
	
	public void run() {
		try {
			this.semaphore.acquire();
			for(int i = 0; i < 5; i++) {
				this.qtd[0]--;				
			}
			this.semaphore.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
public class Exe10 {
	public static void main(String[] args) {
		int[] qtd = { 0 };
		Semaphore semaphore = new Semaphore(1);
		
		Incrementador incrementador = new Incrementador(semaphore, qtd);
		Decrementador decrementador = new Decrementador(semaphore, qtd);
		
		incrementador.start();
		decrementador.start();	
		
		try {
			incrementador.join();
			decrementador.join();	
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.print("Resultado " + qtd[0]);
	}
}
