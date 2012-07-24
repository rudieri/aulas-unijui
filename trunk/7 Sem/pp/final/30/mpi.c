#include <pthread.h>
#include <stdio.h>
#include <stdlib.h> //contém a rand e srand 
#include <time.h> //necessária para usar o time(NULL)   
#include "tempo.h"
#include "mpi.h"



#define TAM 10
int vetor[TAM];
int novo[TAM];
int vetorAux[20][TAM];
int maxVetor[20];
//
void inicializa_vetor();
void mostra_vetor();
void organiza(int aux[], int max);
//

int main(int argc, char *argv[]) {
    MPI_Status status;
    int myid, numprocs;

    MPI_Init(&argc, &argv);

    MPI_Comm_size(MPI_COMM_WORLD, &numprocs);
    printf("\nNumero total de processos: %d", numprocs);

    MPI_Comm_rank(MPI_COMM_WORLD, &myid);
    printf("\nMeu id: %d", myid);

    //sleep(1);
    numprocs = numprocs-1;

    int aux = 0;
    int x;

    
    if (myid == 0) /*mestre*/ {
        tempo1();
        //reparte Vetor
        inicializa_vetor();
        mostra_vetor(vetor);
        int i = 0;
        for (i = 0; i < TAM; i++) {
            aux = vetor[i];
            //printf("\n Valor %d", aux);
            int auxThRead = aux * numprocs / (TAM + 1);
            //printf("\n Vetor Indice %d", auxThRead);
            vetorAux[auxThRead][maxVetor[auxThRead]] = aux;
            maxVetor[auxThRead]++;
        }

        //Manda para Escravos
        printf("\nManda Escravaos \n");
        for (x = 0; x < numprocs; x++) {
            MPI_Send(&vetorAux[x], 1, MPI_INT, (x + 1), 4, MPI_COMM_WORLD);
            MPI_Send(&maxVetor[x], 1, MPI_INT, (x + 1), 4, MPI_COMM_WORLD);
        }

        //Recebe Escravos
/*
        printf("\nRecebe Escravaos \n");
        x=0;
        for (x = 0; x < numprocs; x++) {
            int aux2[TAM];
            MPI_Recv(&aux2, 1, MPI_INT, 0, 4, MPI_COMM_WORLD, &status);
            mostra_vetor(aux2);
        }
*/
/*
        printf("\n Junta Escravaos \n"); 
       aux = 0;
        for (i = 0; i < numprocs; i++) {
            for (x = 0; x < maxVetor[i]; x++) {
                novo[aux] = vetorAux[i][x];
                aux++;
            }
        }
*/
        printf("\nMostra Escravaos \n");
        mostra_vetor(novo);
        tempo2();
        tempoFinal("mili segundos", argv[0], MSGLOG, argv[1]);

    } else {
        int aux[TAM];
        int max;
        MPI_Recv(&aux, 1, MPI_INT, 0, 4, MPI_COMM_WORLD, &status);
        MPI_Recv(&max, 1, MPI_INT, 0, 4, MPI_COMM_WORLD, &status);
        organiza(aux, max);
        mostra_vetor(aux);

/*
        MPI_Send(&aux, 1, MPI_INT, 0, 4, MPI_COMM_WORLD);
*/
    }




}

void organiza(int vetorAux[], int max) {

    int i, j, aux;
    for (i = 0; i < max; i++) {

        j = i;

        while (vetorAux[j] < vetorAux[j - 1]) {

            aux = vetorAux[j];
            vetorAux[j] = vetorAux[j - 1];
            vetorAux[j - 1] = aux;
            j--;

            if (j == 0)break;
        }
    }

    
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
