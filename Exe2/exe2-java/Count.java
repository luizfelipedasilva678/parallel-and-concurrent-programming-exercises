package br.cefet.exe2;

public class Count extends Thread {
	private int rank;
	private int numberOfThreads;
	private int vectorSize;
	private int[] v;
	private int[] sum;
	private int[] count;
	
	public Count(int rank, int numberOfThreads, int vectorSize, int[] v, int[] sum, int [] count) {
		this.rank = rank;
		this.v = v;
		this.sum = sum;
		this.count = count;
		this.numberOfThreads = numberOfThreads;
		this.vectorSize = vectorSize;
	}
	
	public void run() {
		
		int myMin = (vectorSize/numberOfThreads) * this.rank;
		int myMax = (vectorSize/numberOfThreads) + myMin - 1;
		int i;
		
		System.out.println("Mymin " + myMin + "Mymax " + myMax);
		
		for(i = myMin; i <= myMax; i++) {
			if(this.v[i] > 0) {
				this.count[0]++;
				this.sum[0] += this.v[i]; 
			}
        }
	}
}
