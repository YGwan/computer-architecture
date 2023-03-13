#include <stdio.h>

#define MSIZE 13

int hash_function(int key) {
	return key % MSIZE;
}

int main(int argc, char *argv[]) {

	int key;
	int ret_index;

	printf("Please input your key: ");
	scanf("%d" , &key);

	ret_index = hash_function(key);

	printf("This is index of your key: %d\n", ret_index);

	return 0;
}
