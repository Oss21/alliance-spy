package model;

import java.util.ArrayList;

public class Board {

	public static final String CLASSICAL_MULTIPLICATION = "classicalMultiplication";
	public static final String MULTIPLICATION_LINEAR_COMBINATION = "multiplicationLinearCombination";
	public static final String MULTIPLICATION_BY_BLOCKS = "multiplicationByBlocks";

    /**
     * Lista de matrices.
     */
	private ArrayList<int[][]> matrixList;


	/**
	 * Constructor de la clase tablero
	 */
	public Board() {
		matrixList = new ArrayList<int[][]>();
	}


    /**
     * Retorna la lista de matrices
     * @return matrixList - la lista de matrices.
     */
	public ArrayList<int[][]> getMatrixList(){
	    return matrixList;
    }

    /**
     * Crea una nueva matriz con el numero de filas y columnas indicadas.
     * @param row - numero de filas de la nueva matriz.
     * @param column - numero de columnas de la nueva matriz.
     */
    public void createMatrix(int row, int column){
        int[][] matrix = new int[row][column];
        matrixList.add(matrix);
    }

	/**
	 * Crea los coeficientes de la matriz pasada por parametro aleatoriamente.
	 * @param repeatedNumbers - condicion de que, si se pueden o no, repetir numeros dentro de la matriz a llenar.
	 * @param row - numero de filas que la matriz va a poseer.
	 * @param column - numero de columnas que la matriz va a poseer.
	 * @param matrix - matriz a llenar.
	 * @param size - valor del numero maximo dentro de la matriz a llenar.
	 */
	public void createRandomMatrix(boolean repeatedNumbers, int row , int column,int [][] matrix, int size ) {
		
			if(repeatedNumbers) {
				int number = 0;	
					for (int i = 0; i < matrix.length; i++) {
						for (int j = 0; j < matrix[i].length; j++) {
							//Random numbers from 1 to 20.
							number = (int) (Math.random()*size)+1;
							matrix[i][j] = number;
						}
					}
			}else {
				int s = row*column;
				int [] matrixTemp = randomArray(s);
				int count = 0;
				for (int i = 0; i < matrix.length; i++) {
					for (int j = 0; j < matrix[i].length; j++) {
						//Random numbers from 1 to 20.
						matrix[i][j] = matrixTemp[count++];
					}
				}
			}
	}
	
	/**
	 * Permite crear un arreglo de numeros aleatorios sin repetir
	 * @param size tamaño del arreglo.
	 */
	private int[] randomArray(int size) {
			int[] randomNumbers = new int[size];
				for (int i = 0; i < randomNumbers.length; i++) {
					int number = (int) (Math.random()*size+1);
					randomNumbers[i] = number;
				}
				for (int i = 0; i < randomNumbers.length; i++) {
					for (int j = 0; j < randomNumbers.length; j++) {
						if(randomNumbers[i] == randomNumbers[j] && i!=j) {
							int number = (int) (Math.random()*size+1);
							randomNumbers[i] = number;
							 i = 0;
						}
					}
				}
				for (int i = 0; i < randomNumbers.length; i++) {
					System.out.print(randomNumbers[i]+ " ");
				} 
			
		return randomNumbers;
	}

	/**
	 *Verifica si algun coeficiente de una matriz es un numero primo.
	 * @param number - coeficiente de la matriz a verificar.
	 * @return - verdadero si el coeficiente es un numero primo, falso en caso contrario.
	 */
    public boolean verifyIsPrimeNumber(int number) {
        int counter = 2;
        boolean prime = true;

        while ((prime) && (counter != number)) {
            if (number % counter == 0)
                prime = false;
            counter++;
        }
        return prime;
    }

    /**
     *Permite comenzar todo el proceso de multiplicacion de matrices, seleccionando el metodo adecuado para hacerlo.
     * @param selector - nombre del metodo a usar para la multiplicacion de matrices.
     * @return result - matriz resultante del proceso de multiplicacion.
     */
	public int[][] multiplyMatrix(String selector){
    	int[][] result = new int[0][0];

		for (int i = 0; i < matrixList.size(); i++) {

            switch (selector){
                case CLASSICAL_MULTIPLICATION:
                    if (i == 0){
                        result = classicMultiplyMatrix(matrixList.get(0), matrixList.get(1));
                    }else if ((i+1) < matrixList.size()){
                        result = classicMultiplyMatrix(result, matrixList.get(i+1));
                    }
                    break;

                case MULTIPLICATION_LINEAR_COMBINATION:
                    if (i == 0){

                    }else if ((i+1) < matrixList.size()){

                    }
                    break;

                case MULTIPLICATION_BY_BLOCKS:
                    if (i == 0){

                    }else if ((i+1) < matrixList.size()){

                    }
                    break;
            }
		}
		return result;
	}

    /**
     *Multiplica matrices utilizando el metodo de Strassen.
     * @param matrixA - primera matriz a multiplicar.
     * @param matrixB - segunda matriz a multiplicar.
     * @return result - la matriz resultante de multiplicar matrixA con matrixB.
     */
	public int[][] classicMultiplyMatrix(int[][] matrixA, int[][] matrixB){
    	int[][] result = new int[matrixA.length][matrixB[0].length];

		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[i].length; j++) {
				for (int k = 0; k < matrixA.length; k++) {
					result[i][j] += matrixA[i][k]*matrixB[k][j];
				}
			}
		}
		return result;
	}
}



