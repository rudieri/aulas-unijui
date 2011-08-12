
import matriz.Matrizes;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author rudieri
 */
public class Main {
    
    public static void main(String[] args) {
        Matrizes matrizes = new Matrizes();
        double[][] matriz = {{1, 3}, {2, 0}, {-1, 4}};
        double[][] matriz2 = {{2, 1}, {3, 1}};
        double[][] multiplicar = matrizes.multiplicar(matriz, matriz2);
        System.out.println("------------MULTIPLICAÇÃO-------------");
        matrizes.printMatriz(multiplicar);
        System.out.println("------------INVERSA-------------");
        double[][] calcularInversa = matrizes.calcularInversa(matriz2);
        
        matrizes.printMatriz(calcularInversa);
    }
}
