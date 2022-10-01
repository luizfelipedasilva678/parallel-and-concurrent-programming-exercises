#include <stdio.h>
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <semaphore.h>

#define VALOR 0
#define N 2
#define NUMBER_OF_THREADS 2

void * incrementa( void *);
void * decrementa( void *);

typedef struct {

    int valor;

} shared_mem_t;

typedef struct {

    sem_t *semaphore;
    shared_mem_t *shared_mem_p;
    int rank;

} private_data_t;

int main (void) {
    pthread_t thread_id[NUMBER_OF_THREADS];
    sem_t semaphore;

    shared_mem_t shared_mem;

    sem_init(&semaphore, 0, 1);

    shared_mem.valor = VALOR;

    private_data_t thread_data[2];

    thread_data[0].rank = 0;
    thread_data[0].semaphore = &semaphore;
    thread_data[0].shared_mem_p = &shared_mem;
    pthread_create(&thread_id[0], NULL, incrementa, (void*) &thread_data[0]);

    thread_data[1].rank = 1;
    thread_data[1].semaphore = &semaphore;
    thread_data[1].shared_mem_p = &shared_mem;
    pthread_create(&thread_id[1], NULL, decrementa, (void*) &thread_data[1]);


    pthread_join(thread_id[0],NULL);
	pthread_join(thread_id[1],NULL);
	sem_destroy(&semaphore);


    printf("\n VALOR FINAL %d \n", shared_mem.valor);

    return 0;
}

void * incrementa(void * param) {
    int i;
    private_data_t *private_data_p = (private_data_t*)(param);
    sem_t *semaphore = private_data_p->semaphore;
    shared_mem_t *shared_mem = (shared_mem_t*) private_data_p->shared_mem_p;

    printf("\n My rank %d \n", private_data_p->rank);

    for(i = 0; i < N; i++) {
        sem_wait(semaphore);
        shared_mem->valor += 1;
        sem_post(semaphore);
    }
}


void * decrementa(void * param) {
    int i;
    private_data_t *private_data_p = (private_data_t*)(param);
    sem_t *semaphore = private_data_p->semaphore;
    shared_mem_t *shared_mem = (shared_mem_t*) private_data_p->shared_mem_p;

    printf("\n My rank %d \n", private_data_p->rank);

    for(i = 0; i < N; i++) {
        sem_wait(semaphore);
        shared_mem->valor -= 1;
        sem_post(semaphore);
    }
}
