#include<stdio.h>
#include<stdlib.h>
//Ish Davis
unsigned long long getLength(char **argument);
FILE *inputFile;

		int main(int argc, char *argv[]){
		
		//allocate memory for file
		unsigned long long length = getLength(argv);
		unsigned char *array;
		array = (unsigned char*)malloc(length*sizeof(unsigned char));
		inputFile = fopen(argv[1], "rb+");
		//seek back to beginning of file
		fseek(inputFile, 0, SEEK_SET);
		fread(array, sizeof(unsigned char), length, inputFile);
		//Go into loop
		int i = 0;
		while(i < length)
		{
			if((*(array + i) >= 32 && *(array + i) <=126) || (*(array + i) == 9))
			{
				int count = 1, q = i + 1;
				while((*(array + q) >= 32 && *(array + q) <=126) || (*(array + q) == 9))
				{
					count++;
					if(count == 4)
					{
						printf("%c%c%c%c",*(array + i),*(array + (q - 2)),*(array + (q - 1)),*(array + q));//Check this
					}
					else if(count > 4)
					{
						printf("%c",*(array + q));
					}
					q++;
				}
				if(count >= 4)
				{
					i += count;
					i++;
					printf("\n");
				}
				else{
					i += count;
					i++;
				}
			}
			else{
			i++;
			}
		}
		
		return 1;
		}
		
		
		unsigned long long getLength(char **argument)
		{
			inputFile = fopen(*(argument + 1), "rb");
			fseek(inputFile, 0, SEEK_END);
			unsigned long long len = (unsigned long long)ftell(inputFile);
			return len;
		}