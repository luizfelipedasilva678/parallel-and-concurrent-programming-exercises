
class MySemaphore {
	private int semaphoreValue;
	
	MySemaphore(int semaphoreValue) {
		this.semaphoreValue = semaphoreValue;
	}
	
	public synchronized void down() {
		while (this.semaphoreValue == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		} 		
		
		this.semaphoreValue--;			
	}
	
	public synchronized void up() {
		this.semaphoreValue++;		
		notify();	
	}
	
	
	public int getValue() {
		return this.semaphoreValue;
	}
}

class Test extends Thread {
	private int id;	
	private MySemaphore semaphore;
	
	Test(int id, MySemaphore semaphore) {
		this.id = id;
		this.semaphore = semaphore;
	}
	
	public void run() {	
		this.semaphore.down();
		System.out.println("Oi da thread " + this.id);
		System.out.println("Valor do semafaro " + this.semaphore.getValue());
		this.semaphore.up();
	}
}

public class Exe6 {
	public static void main(String[] args) {
		Test[] test = new Test[5];
		MySemaphore semaphore = new MySemaphore(1);
		
		for(int i = 0; i < 5; i++) {
			test[i] = new Test(i, semaphore);
		}
		
		for(int i = 0; i < 5; i++) {
			test[i].start();
		}
		
		for(int i = 0; i < 5; i++) {
			try {
				test[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			};
		}
	}
}
