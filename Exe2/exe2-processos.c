#include <sys/wait.h>
#include <sys/mman.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#define VECTOR_SIZE 10
#define PROCESS 2

int main(void) {
    int i;
    pid_t pid;

    int vr[VECTOR_SIZE];

    int * count = mmap(NULL, sizeof(int), PROT_READ|PROT_WRITE, MAP_ANONYMOUS|MAP_SHARED, 0, 0);
    int * sum = mmap(NULL, sizeof(int), PROT_READ|PROT_WRITE, MAP_ANONYMOUS|MAP_SHARED, 0, 0);
    *sum = 0;
    *count = 0;

    for (i = 0; i < VECTOR_SIZE; i++){
		vr[i] = i;
	}

	if ( pid = fork() ) {

        int exit_status;

        waitpid(pid, &exit_status, 0);

        int my_min = (VECTOR_SIZE/PROCESS) * 1;
        int my_max = (VECTOR_SIZE/PROCESS) + my_min - 1;

        for(i = my_min; i <= my_max; i++) {
            if (vr[i] > 0) {
                *sum += vr[i];
                *count += 1;
            }
        }

        printf("\n NUMEROS MAIORES QUE 0 = %d, SOMA DOS NUMEROS MAIORES QUE 0 = %d \n", *count, *sum);

        munmap(sum, sizeof(int));
        munmap(count, sizeof(int));

	} else {
        int my_min = (VECTOR_SIZE/PROCESS) * 0;
        int my_max = (VECTOR_SIZE/PROCESS) + my_min - 1;


        for(i = my_min; i <= my_max; i++) {
            if (vr[i] > 0) {
                *sum = *sum + vr[i];
                *count += 1;
            }
        }
	}

    return 0;
}
