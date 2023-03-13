#include <stdio.h>
#include <stdlib.h>

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

void initQueue(Queue *queue) {

	queue-> front = queue->rear = NULL;
	queue -> count = 0;
}

int isEmpty(Queue *queue) {
	return queue -> count == 0;
}

void enqueue(Queue *queue, int data) {

	Node *newNode = (Node *)malloc(sizeof(Node));
	newNode -> data = data;
	newNode -> next = NULL;

	if(isEmpty(queue)) {
		queue -> front = newNode;
	} else {
		queue -> rear -> next = newNode;
	}
	
	queue -> rear = newNode;
	queue -> count++;
	
	printf("Enqueue data = [%d]\n", data);
}

int dequeue(Queue *queue) {
	
	int data;
	Node *loc;
	if(isEmpty(queue)) {
		fprintf(stderr, "Queue is Empty, You can not delete any data. \n");
		return 0;
	}

	loc = queue -> front;
	data = loc -> data;
	queue -> count--;

	free(loc);
	return data;
}

int main(int argc, char *argv[]) {
	
	int i;
	Queue queue;

	initQueue(&queue);

	for( i = 0; i <= MAX_SIZE; i++) {
		enqueue(&queue, i);
	} while(isEmpty(&queue)) {
		printf("Dnqueue data = [%d]\n", dequeue(&queue));
	}
	return 0;
}
