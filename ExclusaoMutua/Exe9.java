import java.util.concurrent.BrokenBarrierException;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

class Participante extends Thread {
	CyclicBarrier barrier;
	int id;
	int[] qtdChegaram;
	Semaphore semaphore;
	
	Participante(CyclicBarrier barrier, int id, int[] qtdChegaram, Semaphore semaphore) {
		this.barrier = barrier;
		this.id = id;
		this.qtdChegaram = qtdChegaram;
		this.semaphore = semaphore;
	}
	
	public void run() {		
		try {
			this.barrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
		
		try {
			this.semaphore.acquire();
				if(this.qtdChegaram[0] < 3) {
					this.qtdChegaram[0]++;
					System.out.println("Thread " + this.id + " chegou em " + this.qtdChegaram[0]);
				}
			this.semaphore.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

public class Exe9 {

	public static void main(String[] args) {
		System.out.println("Digite a quantidade de threads");
		String number = System.console().readLine();
		int qtdThreads = Integer.parseInt(number);
		CyclicBarrier barrier = new CyclicBarrier(qtdThreads);
		Semaphore semphore = new Semaphore(1);
		Participante[] participantes = new Participante[qtdThreads];
		int[] qtdChegaram = new int[1] ;
		qtdChegaram[0] = 0;
		
		for(int i = 0; i < qtdThreads; i++) {
			participantes[i] = new Participante(barrier, i, qtdChegaram, semphore);
		}
		
		for(int i = 0; i < qtdThreads; i++) {
			participantes[i].start();
		}
		
		for(int i = 0; i < qtdThreads; i++) {
			try {
				participantes[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
