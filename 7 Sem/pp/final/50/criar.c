#include<stdio.h>
#include <stdlib.h>
#include<string.h>

#define TAM 100000000


void cria_arq();
void mostra_arq();
void mostra_arq2();

void main(void)
{

	cria_arq();
	mostra_arq();

}


void cria_arq()
{

	FILE *file;		
	file = fopen("texto.txt","w");	    
	int n=0, cont;
	char c;
        int aux = 0;
	for (cont=TAM; cont > 0; cont --){

		do {
			n = rand() % 90;
		} while (n < 65);
		//printf("%d ",n);

		c = n + 0;
		//printf("%c ",c);

		putc(c,file);
/*
                aux++;
                 if(aux == 1000){
                 putc('\n',file);
                 aux=0;
                 }
*/
	}

	fclose(file);    
}





void mostra_arq()   // leitura sequencial
{

	FILE *file;
	file = fopen("texto.txt","r");	  

	char c;

	printf("\n\n");	
	
	while( (c=getc(file)) != EOF){

		printf("%c",c);	
	}

	printf("\n\n");	

	fclose(file);    
}





void mostra_arq2() // leitura em blocos
{

	FILE *file;
	file = fopen("texto.txt","r");	  

	char buffer[40];

	printf("\n\n");	

	while(fgets(buffer,40,file)!= NULL){
		puts(buffer);
	}

	printf("\n\n");	

	fclose(file);    
}
