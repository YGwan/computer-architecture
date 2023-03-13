#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include <time.h>
#include <assert.h>

#define MSIZE 13

int nthread = 1;
int add_cnt = 1;



typedef struct _Node
{
	int index;
	struct _Node* nextIndex;
}Node;

Node* hashArray[MSIZE];

int hash_function(int key) {
	return key % MSIZE;
}

static void *addHash(void *num) {

	int num_cnt = 1000;
	int th_num = (long)num;

	for(int i = 0; i< num_cnt; i++) {

		//printf("Your thread number : [%d]\n", th_num);
		srand(th_num);
		int key = rand() % 100;

		Node* node = (Node *)malloc(sizeof(Node));
		node -> index = key;
		node -> nextIndex = NULL;

		int ret_index = hash_function(key);
	
		if(hashArray[ret_index] == NULL) {
			hashArray[ret_index] = node;
		} else {
			node -> nextIndex = hashArray[ret_index];
			hashArray[ret_index] = node;
		}
	}
}

void showHash() {
	
	int cnt = 0;
	printf("\n[Show your Hash Data]\n");

	for(int i =0; i < MSIZE; i++) {

		if(hashArray[i] != NULL) {

			//printf("\n===Your hash index value = %d===\n",i);
			Node* node = hashArray[i];
			while(node -> nextIndex) {
				//printf("Hash value = %d\n", node -> index);
				node = node -> nextIndex;
				cnt++;
			};
			//printf("Hash Value = %d\n", node -> index);
			cnt++;
		}
	}
	printf("\n[Total number of data : %d\n", cnt);
}

int main(int argc, char *argv[]) {

	long i;
	pthread_t *th;

	if(argc < 2) {
		fprintf(stderr, "%s parameter: n therad\n", argv[9]);
		exit(-1);
	}

	nthread = atoi(argv[1]);

	th = malloc(sizeof(pthread_t) * nthread);

	for(i = 0; i < nthread; i++) {
		assert(pthread_create(&th[i], NULL, addHash, (void*) i) == 0);
	}

	for(i = 0; i < nthread; i++) {
		assert(pthread_join(th[i], NULL) == 0);
	}

	showHash();
	return 0;
}
