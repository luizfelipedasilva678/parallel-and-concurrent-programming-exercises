import java.util.concurrent.Semaphore;

class ImprimeLetras extends Thread {
	private Semaphore semaphore;
	private int[] qtdAs, qtdBs;
	
	ImprimeLetras(int id, Semaphore semaphore, int[] qtdAs, int[] qtdBs) {
		this.semaphore = semaphore;
		this.qtdAs = qtdAs;
		this.qtdBs = qtdBs;
	}
	
	public void run() {
		try {	
			this.semaphore.acquire();				
				while(this.qtdAs[0] <= 5) {
					System.out.print("A");
					this.qtdAs[0]++;
				}
				
				while(this.qtdBs[0] <= 5) {
					System.out.print("B");
					this.qtdBs[0]++;
				}
		    this.semaphore.release();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

public class Exe8 {
	public static void main(String[] args) {	
		Semaphore semaphore = new Semaphore(1);
		ImprimeLetras[] imprimeLetra = new ImprimeLetras[5];
		final int[] qtdAs = new int[1];
		final int[] qtdBs = new int[1];
		
		qtdAs[0] = 0;
		qtdBs[0] = 0;
		
		imprimeLetra[0] = new ImprimeLetras(0, semaphore, qtdAs, qtdBs);
		imprimeLetra[1] = new ImprimeLetras(1, semaphore, qtdAs, qtdBs);
		imprimeLetra[2] = new ImprimeLetras(2, semaphore, qtdAs, qtdBs);
		imprimeLetra[3] = new ImprimeLetras(3, semaphore, qtdAs, qtdBs);
		imprimeLetra[4] = new ImprimeLetras(4, semaphore, qtdAs, qtdBs);
		
		for(int i = 0; i < 5; i++) {
			imprimeLetra[i].start();
		}
		
		for(int i = 0; i < 5; i++) {
			try {
				imprimeLetra[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
