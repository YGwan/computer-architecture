#include <stdio.h>

#define MSIZE 13

int hash_function(int key) {
	return key % MSIZE;
}

int main(int argc, char *argv[]) {

	int key;
	int ret_index;
	int hash_array[100];

	for(int i =0; i<100; i++) {
		hash_array[i] = 0;
	}

	while(1) {

		printf("Please input your key: ");
		scanf("%d" , &key);

		ret_index = hash_function(key);

		if(hash_array[ret_index] != 0) {
			printf("This data is already in %d hash index\n", ret_index);
			break;
		}
		hash_array[ret_index] = key;
	
		printf("This is index of your key: %d\n", ret_index);
	}

	return 0;
}
