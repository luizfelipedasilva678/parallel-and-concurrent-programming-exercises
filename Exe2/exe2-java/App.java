package br.cefet.exe2;

public class App {
	public static void main(String[] args) {
		int i;
		final int NUMBERS_OF_THREADS = 2;
		final int VECTOR_SIZE = 10;
		
		Count[] sumVectors;
		int[] v = new int[VECTOR_SIZE]; 
		int[] sum = new int[1];
		int[] count = new int[1];
		
		sumVectors = new Count[NUMBERS_OF_THREADS];
		
		for (i = 0; i < VECTOR_SIZE; i++) {
			v[i] = i;
		}
		
		for (i = 0; i < NUMBERS_OF_THREADS; i++) {
			sumVectors[i] = new Count(i, NUMBERS_OF_THREADS, VECTOR_SIZE, v, sum, count);
			sumVectors[i].start();	
		}
		
		for (i = 0; i < NUMBERS_OF_THREADS; i++) {
			try {
				sumVectors[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
       System.out.println("\n " + " NUMERO DE VALORES MAIORES QUE 0 = " + count[0] + ", SOMA DOS VALORES MAIORES QUE 0 = " + sum[0]);
        
	}
}