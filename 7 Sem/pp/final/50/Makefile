

all: compile
	
compile:
	gcc -o c criar.c
	gcc -o t testar.c 
	gcc -o sequencial sequencial.c
	mpicc -o mpi mpi.c

clean:
	rm -rf *.out
