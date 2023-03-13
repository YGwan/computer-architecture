#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <assert.h>
#include <pthread.h>

int first_count = 0;
int second_count = 0;
int nthread = 1;

int max_work_cnt = 1;
int work_cnt = 0;
int *work_cnt_per_thread;

pthread_mutex_t first_lock;

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
	max_work_cnt = atoi(argv[2]);

	th = malloc(sizeof(pthread_t) * nthread);
	work_cnt_per_thread = malloc(sizeof(pthread_t) * nthread);

	pthread_mutex_init(&first_lock, NULL);

	for(i = 0; i< nthread; i++) {
		assert(pthread_create(&th[i], NULL, work, (void*) i) == 0);
	}

	for(i = 0; i < nthread; i++) {
		assert(pthread_join(th[i], &value) ==0);
	}

	for(i = 0; i < nthread; i++) {
		printf("Thread %ld work_cont : %d \n", i, work_cnt_per_thread[i]);
	}

	free(th);
	free(work_cnt_per_thread);
	printf("Conplete\n");
}

static void *work(void* num) {
	
	int number = (long)num;
	int answer = 0;

	while (work_cnt < max_work_cnt) {
		pthread_mutex_lock(&first_lock);

		answer = first_count + second_count;

		first_count++;
		second_count++;

		work_cnt++;
		work_cnt_per_thread[number]++;

		pthread_mutex_unlock(&first_lock);
			
	}

	return NULL;
}

