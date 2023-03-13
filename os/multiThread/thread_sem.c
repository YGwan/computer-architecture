#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <assert.h>
#include <pthread.h>
#include<semaphore.h>

#define SEM_COUNT 3

int count[SEM_COUNT];
int working[SEM_COUNT];
int nthread = 1;
int worker_loop_cnt = 1;

pthread_mutex_t lock;
sem_t semaphore;

static void *work(void* num);

int main(int argc, char *argv[]) {
	pthread_t *th;
	void *value;
	long i;

	if(argc < 3 ) {
		fprintf(stderr, "%s parameter : nthread, worker_loop_cnt\n", argv[0]);
		exit(-1);
	}

	nthread = atoi(argv[1]);
	worker_loop_cnt = atoi(argv[2]);

	th = malloc(sizeof(pthread_t) * nthread);
	
	pthread_mutex_init(&lock , NULL);
	sem_init(&semaphore, 0, 1);

	for(i = 0; i< nthread; i++) {
		assert(pthread_create(&th[i], NULL, work, (void*) i) == 0);
	}

	for(i = 0; i < nthread; i++) {
		assert(pthread_join(th[i], &value) ==0);
	}

	sem_destroy(&semaphore);

	free(th);

	printf("Count array : \n");
	for(int i = 0; i < SEM_COUNT; i++) {
		printf("%d " , count[i]);
	}

	printf("\nConplete\n");
}

static void *work(void* num) {
	
	int number = (long)num;
	int count_index = -1;
	
	sem_wait(&semaphore);
	
	pthread_mutex_lock(&lock);

	for(int i = 0; i < SEM_COUNT; i++) {
		if(working[i] == 0) {
			working[i] = 1;
			count_index = i;
			break;
		}
	}

	pthread_mutex_unlock(&lock);

	if(count_index == -1) {
		fprintf(stderr, "Thread number %d: count_index < 0", number);
		exit(-1);
	}

	for(int i = 0; i < worker_loop_cnt; i++) count[count_index]++;

	pthread_mutex_lock(&lock);
	working[count_index] = 0;
	pthread_mutex_unlock(&lock);

	sem_post(&semaphore);

	return NULL;
}

