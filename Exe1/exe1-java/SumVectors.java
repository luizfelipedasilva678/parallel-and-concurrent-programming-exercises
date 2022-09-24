package br.cefet.sumvectors;


public class SumVectors extends Thread {
	private int rank;
	private int numberOfThreads;
	private int vectorSize;
	private int[] vr;
	private int[] v1;
	private int[] v2;
	
	public SumVectors(int rank, int numberOfThreads, int vectorSize, int[] v1, int[] v2, int [] vr) {
		this.rank = rank;
		this.v1 = v1;
		this.v2 = v2;
		this.vr = vr;
		this.numberOfThreads = numberOfThreads;
		this.vectorSize = vectorSize;
	}
	
	public void run() {
		
		int myMin = (vectorSize/numberOfThreads) * this.rank;
		int myMax = (vectorSize/numberOfThreads) + myMin - 1;
		int i;
		
		System.out.println("Mymin " + myMin + "Mymax " + myMax);
		
		for(i = myMin; i <= myMax; i++) {
            this.vr[i] = this.v1[i] + this.v2[i];
        }
	}
}
