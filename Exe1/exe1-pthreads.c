#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

#define VECTOR_SIZE 10
#define NUMBER_OF_THREADS 2

void * sum_vectors( void *);

typedef struct {

    int vr[VECTOR_SIZE];
    int v1[VECTOR_SIZE];
    int v2[VECTOR_SIZE];

} shared_mem_t;

typedef struct {

    shared_mem_t *shared_mem_p;

    int rank;

} private_data_t;

int main (void) {
    int i;
    pthread_t thread_id[NUMBER_OF_THREADS];

    shared_mem_t shared_mem;

    for (i = 0; i < VECTOR_SIZE; i++){
		shared_mem.v1[i] = i;
		shared_mem.v2[i] = (VECTOR_SIZE - 1) - i;
		shared_mem.vr[i] = 0;
	}

    private_data_t thread_data[2];

    thread_data[0].rank = 0;
    thread_data[0].shared_mem_p = &shared_mem;
    pthread_create(&thread_id[0], NULL, sum_vectors, (void*) &thread_data[0]);

    thread_data[1].rank = 1;
    thread_data[1].shared_mem_p = &shared_mem;
    pthread_create(&thread_id[1], NULL, sum_vectors, (void*) &thread_data[1]);


    pthread_join(thread_id[0],NULL);
	pthread_join(thread_id[1],NULL);

    for(i = 0; i < VECTOR_SIZE; i++) {
        printf("\n VALOR NO VR %d \n", shared_mem.vr[i]);
    }

    return 0;
}

void * sum_vectors(void * param) {
    int i;
    private_data_t *private_data_p = (private_data_t*)(param);

    printf("\n My rank %d \n", private_data_p->rank);

    int my_min = (VECTOR_SIZE/NUMBER_OF_THREADS) * private_data_p->rank;
    int my_max = (VECTOR_SIZE/NUMBER_OF_THREADS) + my_min - 1;

    for(i = my_min; i <= my_max; i++) {
        private_data_p->shared_mem_p->vr[i] = private_data_p->shared_mem_p->v1[i] + private_data_p->shared_mem_p->v2[i];
    }
}
