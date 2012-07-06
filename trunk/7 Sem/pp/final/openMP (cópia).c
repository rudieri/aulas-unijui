#include <stdio.h>
#include <stdlib.h> //contém a rand e srand 
#include <time.h> //necessária para usar o time(NULL)   
#include "tempo.h"
#include <omp.h>


#define TAM 10
int vetor[TAM];
int vetorAux[20][TAM];
int maxVetor[20];
//
void inicializa_vetor();
void mostra_vetor();
void organiza();


//

int main(int argc, char *argv[]) {
    int nrT = atoi(argv[1]);
    omp_set_num_threads(nrT);
    printf("Rodando a %d ThRead\n", nrT);



    tempo1();
    inicializa_vetor();
    int i, aux;
#pragma omp for private (i)  
    for (i = 1; i < TAM; i++) {
        aux = vetor[i];
         //printf("\n Valor %d", aux);
        int auxThRead = aux * nrT / TAM;
        //printf("\n Vetor Indice %d", auxThRead);        
        vetorAux[auxThRead][maxVetor[auxThRead]] = aux;
        maxVetor[auxThRead]++;
    }

    {
        organiza();

    }
    tempo2();
    tempoFinal("mili segundos", argv[0], MSGLOG, argv[1]);
    /*
        mostra_vetor();
     */

}

void organiza() {


    int i, j, aux;

    {
#pragma omp for private (i,j,aux)  
        for (i = 1; i < TAM; i++) {
            j = i;
//#pragma omp critical
            {
                while (vetor[j] < vetor[j - 1]) {
                    aux = vetor[j];
                    vetor[j] = vetor[j - 1];
                    vetor[j - 1] = aux;
                    j--;
                    if (j == 0)
                        break;

                }
            }

        }
    }

}

void mostra_vetor(int aux[]) {
    int z;
    printf("\n v e t o r \n");
    for (z = 0; z < TAM; z++) {
        printf("\n Posicao %d - valor %d", z, aux[z]);
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
