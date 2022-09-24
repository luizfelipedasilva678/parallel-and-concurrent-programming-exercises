package br.cefet.sumvectors;

public class App {
	public static void main(String[] args) {
		int i;
		final int NUMBERS_OF_THREADS = 2;
		final int VECTOR_SIZE = 10;
		
		SumVectors[] sumVectors;
		int[] v1 = new int[VECTOR_SIZE]; 
		int[] v2 = new int[VECTOR_SIZE]; 
		int[] vr = new int[VECTOR_SIZE]; 
		
		sumVectors = new SumVectors[NUMBERS_OF_THREADS];
		
		for (i = 0; i < VECTOR_SIZE; i++) {
			v1[i] = i;
			v2[i] = (VECTOR_SIZE - 1) - i;
			vr[i] = 0;
		}
		
		for (i = 0; i < NUMBERS_OF_THREADS; i++) {
			sumVectors[i] = new SumVectors(i, NUMBERS_OF_THREADS, VECTOR_SIZE, v1, v2, vr);
			sumVectors[i].start();	
		}
		
		for (i = 0; i < NUMBERS_OF_THREADS; i++) {
			try {
				sumVectors[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		for(i = 0; i < VECTOR_SIZE; i++) {
	       System.out.println("\n VALOR " + i + " NO VR = " + vr[i] + " \n");
        }
	}
}
