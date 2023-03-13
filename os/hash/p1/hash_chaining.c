#include <stdio.h>
#include <stdlib.h>

#define MSIZE 5

typedef struct _Node
{
	int index;
	struct _Node* nextIndex;
}Node;

Node* hashArray[MSIZE];

int hash_function(int key) {
	return key % MSIZE;
}

void addHash(int key, Node* node) {
	
	int ret_index = hash_function(key);
	if(hashArray[ret_index] == NULL) {
		hashArray[ret_index] = node;
	} else {
		node -> nextIndex = hashArray[ret_index];
		hashArray[ret_index] = node;
	}
	printf("This is index of your key: %d\n", ret_index);
}

void showHash() {

	printf("\n[Show your Hash Data]\n");

	for(int i =0; i < MSIZE; i++) {

		if(hashArray[i] != NULL) {

			printf("\n===Your hash index value = %d===\n",i);
			Node* node = hashArray[i];
			while(node -> nextIndex) {
				printf("Hash value = %d\n", node -> index);
				node = node -> nextIndex;
			};
			printf("Hash Value = %d\n", node -> index);
		}
	}
}

int main(int argc, char *argv[]) {

	int key;
	int count_run = 0;


	while(1) {

		printf("Please input your key: ");
		scanf("%d" , &key);

		Node* node = (Node*)malloc(sizeof(Node));
		node -> index = key;
		node -> nextIndex = NULL;

		addHash(node -> index, node);
		count_run++;

		if(count_run == 5) break;
	}
	showHash();
	return 0;
}
