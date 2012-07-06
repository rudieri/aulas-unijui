#include<stdio.h>
#include <stdlib.h>
#include<string.h>

void testar();


void main(void)
{
	testar();
}


void testar()
{

	FILE *file;
	file = fopen("texto.txt","r");	  

	int 	num=0;

	char code[4]={"DHGK"},
		 c,
		 buf[4];

	c=getc(file);  // leitura do primeiro char do arquivo
	buf[0] = c;

	c=getc(file);  // leitura do segundo char do arquivo
	buf[1] = c;

	c=getc(file);  // leitura do terceiro char do arquivo
	buf[2] = c;

	while( (c=getc(file)) != EOF){  // leitura dos n char do arquivo

		buf[3] = c;
				

//				ok = strcmp(code, buf);    outra opção
				if ((code[0] == buf[0]) 
				 && (code[1] == buf[1]) 
				 && (code[2] == buf[2]) 
				 && (code[3] == buf[3])) {                          // nao faz a pesquisa invertida
					num ++;
					printf("\nencontrada: %d", num);
				}

		//shift
		buf[0] = buf[1];
		buf[1] = buf[2];
		buf[2] = buf[3];
	}

	printf("\n\nsequencia encontrada %d vezes na procura top-down.\n\n", num);

	fclose(file);    
}

