# comentario -> para executar digite make 

all: compile clean
	
compile:
	gcc -o sequencial sequencial.c
	gcc -o pthread pthread.c -lpthread
	gcc -o openMP openMP.c -fopenmp

	@echo "Compilaçao concluida"

clean:
	rm -rf *.out

