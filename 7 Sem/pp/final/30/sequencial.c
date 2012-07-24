#include <stdio.h>
#include <stdlib.h> //contém a rand e srand 
#include <time.h> //necessária para usar o time(NULL)   
#include "tempo.h"


#define TAM 100000
int vetor[TAM];
//
void inicializa_vetor();
void mostra_vetor();
void organiza();
//

int main(int argc, char *argv[]) {


    tempo1();
      
    inicializa_vetor();
    organiza();
    tempo2();
    tempoFinal("mili segundos", argv[0], MSGLOG, argv[1]);
/*
    mostra_vetor();
*/

}

void organiza() {

    int i, j, aux;
    for (i = 1; i < TAM; i++) {

        j = i;

        while (vetor[j] < vetor[j - 1]) {

            aux = vetor[j];
            vetor[j] = vetor[j - 1];
            vetor[j - 1] = aux;
            j--;

            if (j == 0)break;
        }
    }

}

void mostra_vetor() {
    int z;
    printf("\n v e t o r \n");
    for (z = 0; z < TAM; z++) {
        printf("\n Posicao %d - valor %d", z, vetor[z]);
    }
    printf("\n\n\n");
}

void inicializa_vetor() {
    int z;
    srand(time(NULL)); //pra sempre iniciar com outro valor
    /*
        printf("\n inicializando vetor ...\n");
     */
    for (z = 0; z < TAM; z++)
        vetor[z] = (rand() % TAM) + 1;
}
