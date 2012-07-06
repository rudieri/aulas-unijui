#include<stdio.h>
#include <stdlib.h>
#include<string.h>
#include <time.h> //necessária para usar o time(NULL)   
#include "tempo.h"

int TOPDOWN = 0;
int DOWNTOP = 1;

void testar();

void encontrar(int tipo, char code[]);

int main(int argc, char *argv[]) {
    tempo1();
    testar();
    tempo2();
    tempoFinal("mili segundos", argv[0], MSGLOG, argv[1]);
}

void testar() {
    char code[4] = {"DHGK"};
    
    encontrar(TOPDOWN, code);
    encontrar(DOWNTOP, code);
    

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

