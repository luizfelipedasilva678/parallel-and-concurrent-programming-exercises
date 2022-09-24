#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

#define NUMBERS_OF_THREADS 2
#define VECTOR_SIZE 10

void * sum_and_count_numbers(void *);

typedef struct {

    int v[VECTOR_SIZE];
    int count;
    int sum;

} shared_mem_t;

typedef struct {

    shared_mem_t * shared_mem_p;
    int rank;

} private_data_t;

int main (void) {
    pthread_t threads[NUMBERS_OF_THREADS];

    shared_mem_t shared_mem;

    for(int i = 0; i < VECTOR_SIZE; i++) {
        shared_mem.v[i] = i;
    }

    shared_mem.count = 0;
    shared_mem.sum = 0;

    private_data_t thread_data[2];

    thread_data[0].rank = 0;
    thread_data[0].shared_mem_p = &shared_mem;
    pthread_create(&threads[0], NULL, sum_and_count_numbers, (void*) &thread_data[0]);

    thread_data[1].rank = 1;
    thread_data[1].shared_mem_p = &shared_mem;
    pthread_create(&threads[1], NULL, sum_and_count_numbers, (void*) &thread_data[1]);

    pthread_join(threads[0], NULL);
    pthread_join(threads[1], NULL);

    printf("NUMEROS MAIORES QUE 0 = %d, SOMA DOS NUMEROS MAIORES QUE 0 = %d", shared_mem.count, shared_mem.sum);

    return 0;
}

void * sum_and_count_numbers(void * param) {
    private_data_t * private_data = (private_data_t *) param;

    int my_min = (VECTOR_SIZE/NUMBERS_OF_THREADS) * private_data->rank;
    int my_max = (VECTOR_SIZE/NUMBERS_OF_THREADS) + my_min - 1;

    for(int i = my_min; i <= my_max; i++) {
        if( private_data->shared_mem_p->v[i] > 0 ) {
            private_data->shared_mem_p->count++;
            private_data->shared_mem_p->sum += private_data->shared_mem_p->v[i];
        }
    }

}
