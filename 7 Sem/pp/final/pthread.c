#include <pthread.h>
#include <stdio.h>
#include <stdlib.h> //contém a rand e srand 
#include <time.h> //necessária para usar o time(NULL)   
#include "tempo.h"



#define TAM 100000
int vetor[TAM];
int novo[TAM];
int nrT;
int vetorAux[20][TAM];
int maxVetor[20];
int x;
//
void inicializa_vetor();
void mostra_vetor();
void *organiza(void *arg);
//

int main(int argc, char *argv[]) {
    nrT = atoi(argv[1]);
    printf("Rodando a %d ThRead\n", nrT);
    //sleep(1);
    pthread_t t[nrT];
    int aux = 0;


    tempo1();
    inicializa_vetor();
    int i = 0;
    for (i = 0; i < TAM; i++) {
        aux = vetor[i];
        //printf("\n Valor %d", aux);
        int auxThRead = aux * nrT / (TAM+1);
        //printf("\n Vetor Indice %d", auxThRead);
        vetorAux[auxThRead][maxVetor[auxThRead]] = aux;
        maxVetor[auxThRead]++;
    }

/*
    mostra_vetor(vetorAux[0]);
    mostra_vetor(vetorAux[1]);
*/
    for (x = 0; x < nrT; x++) {
        pthread_create(&t[x], NULL, organiza, (void *) &x);
    }

    for (x = 0; x < nrT; x++) {
        pthread_join(t[x], NULL);
    }
/*
    mostra_vetor(vetorAux[0]);
    mostra_vetor(vetorAux[1]);
*/
    
    aux = 0;
    for (i = 0; i < nrT; i++) {
        for (x = 0; x < maxVetor[i]; x++) {
            novo[aux] = vetorAux[i][x];
            aux++;
        }

    }


    tempo2();
    tempoFinal("mili segundos", argv[0], MSGLOG, argv[1]);
/*
    mostra_vetor(novo);
*/



}

void * organiza(void *arg) {

    int thread = x;

    printf("\n Crio a Thread %d ...", thread);
    int i, j, aux;
    for (i = 0; i < maxVetor[thread]; i++) {

        j = i;

        while (vetorAux[thread][j] < vetorAux[thread][j - 1]) {

            aux = vetorAux[thread][j];
            vetorAux[thread][j] = vetorAux[thread][j - 1];
            vetorAux[thread][j - 1] = aux;
            j--;

            if (j == 0)break;
        }
    }

    return (NULL);
}

void mostra_vetor(int v[]) {
    int z;
    printf("\n v e t o r \n");
    for (z = 0; z < TAM; z++) {
        printf("\n Posicao %d - valor %d", z, v[z]);
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
