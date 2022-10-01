#include <stdio.h>
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

#define VALOR 0
#define N 2
#define NUMBER_OF_THREADS 2

void * incrementa( void *);
void * decrementa( void *);

typedef struct {

    pthread_mutex_t mutex;
    int valor;

} shared_mem_t;

typedef struct {

    shared_mem_t *shared_mem_p;
    int rank;

} private_data_t;

int main (void) {
    pthread_t thread_id[NUMBER_OF_THREADS];

    shared_mem_t shared_mem;

    pthread_mutex_init( &shared_mem.mutex, NULL);
    shared_mem.valor = VALOR;

    private_data_t thread_data[2];

    thread_data[0].rank = 0;
    thread_data[0].shared_mem_p = &shared_mem;
    pthread_create(&thread_id[0], NULL, incrementa, (void*) &thread_data[0]);

    thread_data[1].rank = 1;
    thread_data[1].shared_mem_p = &shared_mem;
    pthread_create(&thread_id[1], NULL, decrementa, (void*) &thread_data[1]);


    pthread_join(thread_id[0],NULL);
	pthread_join(thread_id[1],NULL);
	pthread_mutex_destroy(&shared_mem.mutex);


    printf("\n VALOR FINAL %d \n", shared_mem.valor);

    return 0;
}

void * incrementa(void * param) {
    int i;
    private_data_t *private_data_p = (private_data_t*)(param);
    shared_mem_t *shared_mem = (shared_mem_t*) private_data_p->shared_mem_p;
    pthread_mutex_t *mutex = &shared_mem->mutex;

    printf("\n My rank %d \n", private_data_p->rank);

    for(i = 0; i < N; i++) {
        pthread_mutex_lock(mutex);
        shared_mem->valor += 1;
        pthread_mutex_unlock(mutex);
    }
}


void * decrementa(void * param) {
     int i;
    private_data_t *private_data_p = (private_data_t*)(param);
    shared_mem_t *shared_mem = (shared_mem_t*) private_data_p->shared_mem_p;
    pthread_mutex_t *mutex = &shared_mem->mutex;

    printf("\n My rank %d \n", private_data_p->rank);

    for(i = 0; i < N; i++) {
        pthread_mutex_lock(mutex);
        shared_mem->valor -= 1;
        pthread_mutex_unlock(mutex);
    }
}
