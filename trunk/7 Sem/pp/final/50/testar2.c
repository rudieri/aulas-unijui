#include<stdio.h>
#include <stdlib.h>
#include<string.h>
#include <time.h> //necessária para usar o time(NULL)   
#include "tempo.h"

int TOPDOWN = 0;
int DOWNTOP = 1;
int DIAGONAL_ESQ_DIR = 2;
int DIAGONAL_DIR_ESQ = 3;
int DIAGONAL_ESQ_DIR_INV = 4;
int DIAGONAL_DIR_ESQ_iNV = 5;
int CIMA_BAIXO = 6;
int BAIXO_CIMA = 7;

#define TAM 1000000

void testar();
void encontrarDiagonal(int tipo,char code[]);

void encontrar(int tipo, char code[]);

int main(int argc, char *argv[]) {
    tempo1();
    testar();
    tempo2();
    tempoFinal("mili segundos", argv[0], MSGLOG, argv[1]);
}

void testar() {
    char code[4] = {"GHVA"};
    
    //encontrar(TOPDOWN, code);
    //encontrar(DOWNTOP, code);
    encontrarDiagonal(3,code);
    

}

void encontrar(int tipo,char code[]) {
    FILE *file;
    file = fopen("texto.txt", "r");

    int num = 0;

    char c, buf[4];

    c = getc(file); // leitura do primeiro char do arquivo
    buf[0] = c;

    c = getc(file); // leitura do segundo char do arquivo
    buf[1] = c;

    c = getc(file); // leitura do terceiro char do arquivo
    buf[2] = c;

    while ((c = getc(file)) != EOF) { // leitura dos n char do arquivo
        
        

        buf[3] = c;


        // se For TOPDOW
        if (tipo == TOPDOWN) {
            if ((code[0] == buf[0])
                    && (code[1] == buf[1])
                    && (code[2] == buf[2])
                    && (code[3] == buf[3])) {
                num++;
                printf("\nencontrada: %d", num);
            }
        } else if (tipo == DOWNTOP) {
            if ((code[0] == buf[3])
                    && (code[1] == buf[2])
                    && (code[2] == buf[1])
                    && (code[3] == buf[0])) {
                num++;
                printf("\nencontrada: %d", num);
            }
        }

        //shift - MOve o Buffer
        buf[0] = buf[1];
        buf[1] = buf[2];
        buf[2] = buf[3];
        //
    }
    if (tipo == TOPDOWN) {
        printf("\n\nsequencia encontrada %d vezes na procura top-down.\n\n", num);
    }
    if (tipo == DOWNTOP) {
        printf("\n\nsequencia encontrada %d vezes na procura down-top.\n\n", num);
    }

    fclose(file);
}

void encontrarDiagonal(int tipo,char code[]) {
    FILE *file;
    file = fopen("texto.txt", "r");
	
    int num = 0;
    char c;
    char arquivo[TAM];
    int aux = 0;
    while ((c = getc(file)) != EOF) {
       arquivo[aux] = c;
       aux++;
    }    
    fclose(file);
    //1000000 =1000 x 1000
    aux = 0;
    int row =0;
    int col = 0;
    char buf[4];
    for(aux =0; aux < TAM; aux++ ){
     row = aux /1000;
     col = aux-(row*1000);
     if (tipo == DIAGONAL_ESQ_DIR) {
	     if(1000-row<4 || 1000-col<4){
	       continue;
	     }     
	     buf[0] = arquivo[aux];
	     row=row+1;
	     col=col+1;
	     buf[1] = arquivo[(row*1000)+col];
	     row=row+1;
	     col=col+1;
	     buf[2] = arquivo[(row*1000)+col];
	     row=row+1;
	     col=col+1;
	     buf[3] = arquivo[(row*1000)+col];

	     if ((code[0] == buf[0])
		            && (code[1] == buf[1])
		            && (code[2] == buf[2])
		            && (code[3] == buf[3])) {
			num++;
		        printf("\nencontrada: %d", num);
		       }
       }
       if (tipo == DIAGONAL_ESQ_DIR_INV) {
	     if(1000-row<4 || 1000-col<4){
	       continue;
	     }     
	     buf[0] = arquivo[aux];
	     row=row+1;
	     col=col+1;
	     buf[1] = arquivo[(row*1000)+col];
	     row=row+1;
	     col=col+1;
	     buf[2] = arquivo[(row*1000)+col];
	     row=row+1;
	     col=col+1;
	     buf[3] = arquivo[(row*1000)+col];

	     if ((code[3] == buf[0])
		            && (code[2] == buf[1])
		            && (code[1] == buf[2])
		            && (code[0] == buf[3])) {
			num++;
		        printf("\nencontrada: %d", num);
		       }
     }
   }

        printf("\n\nsequencia encontrada %d vezes na procura diagonal.\n\n", num);
   

}

