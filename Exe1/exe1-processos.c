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

    int *vr, v1[VECTOR_SIZE], v2[VECTOR_SIZE];

    vr = (int*) mmap(NULL, sizeof( int)*VECTOR_SIZE, PROT_READ|PROT_WRITE, MAP_ANONYMOUS|MAP_SHARED, 0, 0);

    for (i = 0; i < VECTOR_SIZE; i++){
		v1[i] = i;
		v2[i] = (VECTOR_SIZE - 1) - i;
		vr[i] = 0;
	}

	if ( pid = fork() ) {

        int exit_status;

        waitpid(pid, &exit_status, 0);

        int my_min = (VECTOR_SIZE/PROCESS) * 1;
        int my_max = (VECTOR_SIZE/PROCESS) + my_min - 1;

        for(i = my_min; i <= my_max; i++) {
            vr[i] = v1[i] + v2[i];
        }

        for(i = 0; i < VECTOR_SIZE; i++) {
            printf("\n VALOR NO VR %d \n", vr[i]);
        }

        munmap(vr, sizeof(int) * VECTOR_SIZE);
	} else {
        int my_min = (VECTOR_SIZE/PROCESS) * 0;
        int my_max = (VECTOR_SIZE/PROCESS) + my_min - 1;

        for(i = my_min; i <= my_max; i++) {
            vr[i] = v1[i] + v2[i];
        }
	}

    return 0;
}
