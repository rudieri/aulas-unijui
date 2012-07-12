
#include "mpi.h"
#include <stdio.h>

#define TOPDOWN  0
#define DOWNTOP  1
#define DIAGONAL_ESQ_DIR  2
#define DIAGONAL_DIR_ESQ  3
#define TAMANHO 1000000


void encontrar(int tipo, char code[]);
void encontrarDiagonal(int tipo, char code[]);
void encontrarTD(int tipo, char code[]);

int main(int argc, char *argv[]) {
    MPI_Status status;
    int tipo, myid, numprocs, namelen;
    char code[4] = {"GHVA"};

    double startwtime = 0.0,
            endwtime = 0.0;
    char processor_name[MPI_MAX_PROCESSOR_NAME];

    MPI_Init(&argc, &argv);

    MPI_Comm_size(MPI_COMM_WORLD, &numprocs);
    printf("\nNumero total de processos: %d", numprocs);

    MPI_Comm_rank(MPI_COMM_WORLD, &myid);
    printf("\nMeu id: %d", myid);

    MPI_Get_processor_name(processor_name, &namelen);
    printf("\nNome da Maquina : %s\n", processor_name);

    if (myid == 0) /*mestre*/ {

        startwtime = MPI_Wtime();

        int aux = TOPDOWN;
        encontrar(aux,code);
        
/*
        MPI_Send(&aux, 1, MPI_INT, 1, 4, MPI_COMM_WORLD);
*/
        /*
                aux = DOWNTOP;
                MPI_Send(&aux, 1, MPI_INT, 2, 4, MPI_COMM_WORLD);
                aux = DIAGONAL_DIR_ESQ;
                MPI_Send(&aux, 1, MPI_INT, 3, 4, MPI_COMM_WORLD);
                aux = DIAGONAL_ESQ_DIR;
                MPI_Send(&aux, 1, MPI_INT, 4, 4, MPI_COMM_WORLD);
         */


        endwtime = MPI_Wtime();
        printf("\nTempo de execucao = %f\n", endwtime - startwtime);
        fflush(stdout);
    }
    if (myid == 1) /*id 1 */ {

        MPI_Recv(&tipo, 1, MPI_INT, 0, 4, MPI_COMM_WORLD, &status);
        encontrar(tipo, code);

    }
    if (myid == 2) /*id 1 */ {

        MPI_Recv(&tipo, 1, MPI_INT, 0, 4, MPI_COMM_WORLD, &status);
        encontrar(tipo, code);

    }
    if (myid == 3) /*id 1 */ {

        MPI_Recv(&tipo, 1, MPI_INT, 0, 4, MPI_COMM_WORLD, &status);
        encontrar(tipo, code);

    }
    if (myid == 4) /*id 1 */ {

        MPI_Recv(&tipo, 1, MPI_INT, 0, 4, MPI_COMM_WORLD, &status);
        encontrar(tipo, code);

    }

    MPI_Finalize();

    return 0;
}

void encontrar(int tipo, char code[]) {
    if (tipo == TOPDOWN || tipo == DOWNTOP) {
        encontrarTD(tipo, code);
    } else {
        encontrarDiagonal(tipo, code);
    }
}

void encontrarTD(int tipo, char code[]) {
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



