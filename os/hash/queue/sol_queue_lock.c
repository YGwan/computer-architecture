#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <assert.h>
#include <pthread.h>

#define MAX_SIZE 30


typedef struct Node
{
	int data;
	struct Node *next;
} Node;

typedef struct Queue
{
	Node *front;
	Node *rear;
	int count;
} Queue;

pthread_mutex_t en_lock;
pthread_mutex_t de_lock;

int nthread = 1;
Queue* queue;

void initQueue(Queue *_queue) {

	_queue-> front = queue->rear = NULL;
	_queue -> count = 0;
}

int isEmpty(Queue *queue) {
	return queue -> count == 0;
}

void* enqueue(void *data) {

	int _data = (long)data;

	for(int i = 0; i< MAX_SIZE; i++) {

		pthread_mutex_lock(&en_lock);

		Node *newNode = (Node *)malloc(sizeof(Node));
		newNode -> data = _data;
		_data = _data + 1;
		newNode -> next = NULL;

		if(isEmpty(queue)) {
			queue -> front = newNode;
		} else {
			queue -> rear -> next = newNode;
		}	
	
		queue -> rear = newNode;
		queue -> count++;
	
		//printf("Enqueue data = [%d]\n", data);
		
		pthread_mutex_unlock(&en_lock);
	}
}

void* dequeue(void *data) {
	
	int _data;

	for(int i = 0; i < MAX_SIZE; i++) {
		
		pthread_mutex_lock(&de_lock);

		Node *loc;
		if(isEmpty(queue)) {
			fprintf(stderr, "Queue is Empty, You can not delete any data. \n");
			return 0;
		}

		loc = queue -> front;
		_data = loc -> data;
		queue -> front = loc -> next;
		queue -> count--;
	
		free(loc);

		pthread_mutex_unlock(&de_lock);
	}
}

int main(int argc, char *argv[]) {

	pthread_t *th_en;	
	pthread_t *th_de;	
	long i;

	if(argc < 2) {
		fprintf(stderr, "%s parameter: ntherad\n", argv[0]);
	}

	nthread = atoi(argv[1]);
	
	th_en = malloc(sizeof(pthread_t) * nthread);
	th_de = malloc(sizeof(pthread_t) * nthread);
	queue = malloc(sizeof(Queue));

	initQueue(queue);
	
	for(i = 0; i < nthread; i++) {
		assert(pthread_create(&th_en[i], NULL, enqueue, (void *) i) == 0);
	}

	for(i = 0; i < nthread; i++) {
		assert(pthread_create(&th_de[i], NULL, dequeue, (void *) i) == 0);
	}

	for(i = 0; i < nthread; i++) {
		assert(pthread_join(th_en[i], NULL) == 0);
	}

	for(i = 0; i < nthread; i++) {
		assert(pthread_join(th_de[i], NULL) == 0);
	}

	return 0;
}
