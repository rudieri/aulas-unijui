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

#define TAMANHO 1000000



void testar();
void encontrarDiagonal(int tipo, char code[]);

void encontrar(int tipo, char code[]);

int main(int argc, char *argv[]) {
    tempo1();
    testar();
    tempo2();
    tempoFinal("mili segundos", argv[0], MSGLOG, argv[1]);
}

void testar() {
    char code[4] = {"PITI"};

     encontrar(TOPDOWN, code);
      encontrar(DOWNTOP, code);
     encontrarDiagonal(DIAGONAL_DIR_ESQ,code);
    encontrarDiagonal(DIAGONAL_ESQ_DIR, code);


}

void encontrar(int tipo, char code[]) {
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
                /*
                                printf("\nencontrada: %d", num);
                 */
            }
        } else if (tipo == DOWNTOP) {
            if ((code[0] == buf[3])
                    && (code[1] == buf[2])
                    && (code[2] == buf[1])
                    && (code[3] == buf[0])) {
                num++;
                /*
                                printf("\nencontrada: %d", num);
                 */
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

void encontrarDiagonal(int tipo, char code[]) {
    FILE *file;
    file = fopen("texto.txt", "r");
    int num = 0;
    char c;
/*
    char arquivo[TAMANHO];
    int aux = 0;
    while ((c = getc(file)) != EOF) {
        arquivo[aux] = c;
        aux++;
    }
*/

    //1000000 =1000 x 1000
    long aux = 0;
    int row = 0;
    int col = 0;
    char buf[4];
    for (aux = 0; aux < TAMANHO; aux++) {
        row = aux / 10000;
        col = aux - (row * 10000);
        if (tipo == DIAGONAL_ESQ_DIR) {
            if (10000 - row < 4 || 10000 - col < 4) {
                continue;
            }
            fseek(file, aux, SEEK_SET);
            buf[0] = getc(file);
            row = row + 1;
            col = col + 1;
            fseek(file, (row * 10000) + col, SEEK_SET);
            buf[1] = getc(file);
            row = row + 1;
            col = col + 1;
            fseek(file, (row * 10000) + col, SEEK_SET);
            buf[2] = getc(file);
            row = row + 1;
            col = col + 1;
            fseek(file, (row * 10000) + col, SEEK_SET);
            buf[3] = getc(file);

            if ((code[0] == buf[0])
                    && (code[1] == buf[1])
                    && (code[2] == buf[2])
                    && (code[3] == buf[3])) {
                num++;

            }
        }
        if (tipo == DIAGONAL_DIR_ESQ) {
            if (10000 - row < 4 || 10000 - col < 4) {
                continue;
            }
            fseek(file, aux, SEEK_SET);
            buf[0] = getc(file);
            row = row - 1;
            col = col - 1;
            fseek(file, (row * 10000) + col, SEEK_SET);
            buf[1] = getc(file);
            row = row - 1;
            col = col - 1;
            fseek(file, (row * 10000) + col, SEEK_SET);
            buf[2] = getc(file);
            row = row - 1;
            col = col - 1;
            fseek(file, (row * 10000) + col, SEEK_SET);
            buf[3] = getc(file);

            if ((code[0] == buf[0])
                    && (code[1] == buf[1])
                    && (code[2] == buf[2])
                    && (code[3] == buf[3])) {
                num++;

            }
        }
    }
    if (tipo == DIAGONAL_ESQ_DIR) {
        printf("\n\nsequencia encontrada %d vezes na procura diagonal ESQ_DIR.\n\n", num);
    }
    if (tipo == DIAGONAL_DIR_ESQ) {
        printf("\n\nsequencia encontrada %d vezes na procura diagonal DIR_ESQ.\n\n", num);
    }
fclose(file);

}

