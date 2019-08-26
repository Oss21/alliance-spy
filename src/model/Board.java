package model;

import customExceptions.MultiplicationNotSupportedException;

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
	public int[][] multiplyMatrix(String selector) throws MultiplicationNotSupportedException {
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
                    	result = multiplicationLinearCombination(matrixList.get(0), matrixList.get(1));
                    }else if ((i+1) < matrixList.size()){
                    	result = multiplicationLinearCombination(result, matrixList.get(i+1));
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
	public int[][] classicMultiplyMatrix(int[][] matrixA, int[][] matrixB) throws MultiplicationNotSupportedException {

		int[][] result = new int[matrixA.length][matrixB[0].length];

		if (matrixA[0].length == matrixB.length){
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[i].length; j++) {
				for (int k = 0; k < matrixA.length; k++) {
					result[i][j] += matrixA[i][k]*matrixB[k][j];
				}
			}
		}
		}else{
			throw new MultiplicationNotSupportedException("Matrices incompatibles para multiplicar");
		}

		return result;
	}



	/**
	 * Multiplica dos matrices compatibles con el método de la multiplicacion como combinacion lineal.
	 * @param A - Es la matriz A.
	 * @param B - Es la matriz B.
	 * @return - Una matric C que es el resultado de la multiplicacion de las matrices A y B.
	 * @throws MultiplicationNotSupportedException - - Error que se lanza cuando las matrices no se pueden multiplicar.
	 */
	public int[][] multiplicationLinearCombination(int[][] A, int[][] B) throws MultiplicationNotSupportedException {
		// Es la matriz resultado de la multiplicación.
		int[][] matrixResult = new int[A.length][B[0].length];
		if (A[0].length == B.length) {
			// Es el vector columna de B.
			int[] vColumn = new int[B.length];
			for (int i = 0; i < B[0].length; i++) {
				// Toma los vectores columna de B
				for (int j = 0; j < B.length; j++) {
					vColumn[j] = B[j][i];
				}
				// Es el vector resultado de la multiplicación.
				int[][] vectorResult = matrixByVectorColumn(A, vColumn);
				// Llena la matriz C de resultado
				for (int k = 0; k < matrixResult.length; k++) {
					matrixResult[k][i] = vectorResult[k][0];
				}
			}
		} else {
			throw new MultiplicationNotSupportedException("Matrices incompatibles para multiplicar");
		}
		return matrixResult;
	}

	/**
	 * Multiplica una matriz con un vector.
	 * @param A - Es la matriz A
	 * @param V - Es el vector columna de la matriz B
	 * @return - El vector resultado de la multiplicación de A y V.
	 */
	private int[][] matrixByVectorColumn(int[][] A, int[] V) {
		// Tamaño de la matriz A
		int fA = A.length;
		int cA = A[0].length;
		// Tamaño del vector
		int fB = V.length;
		int cB = 1;
		// Crea el vector resultado
		int[][] matrixResult = new int[fA][cB];
		// Multiplica la matriz por el vector.
		for (int i = 0; i < matrixResult.length; i++) {
			for (int j = 0; j < matrixResult[i].length; j++) {
				for (int k = 0; k < cA; k++) {
					matrixResult[i][j] += A[i][k] * V[k];
				}
			}
		}
		return matrixResult;
	}

    /**
     * Permite multiplicar matrices por bloques
     * @param division permite indicar el valor de columnas de A y filas de B a dividir.
     * @param a matriz A
     * @param b matriz B
     * @return la matriz multiplicada
     * @throws MultiplicationNotSupportedException
     */
    public int[][] MultiplyByBox(int division, int [][] a, int [][] b) throws MultiplicationNotSupportedException {
        ArrayList<int[][]> divisionA = null;
        ArrayList<int[][]> divisionB = null;
        int i = 0;
        int j = 0;
        while (i < a[0].length) {
            divisionA.add(splitMatrix(i, i+division,j, j+division, a));
            divisionB.add(splitMatrix(i, i+division,j, j+division, b));
            i += division;
            j +=division;
        }

        int[][] result =new  int [a[0].length][b.length];
        int[][] matrix = null;
        boolean exit = false;
        for (int k = 0; k < divisionA.size(); k++) {
            matrix = classicMultiplyMatrix(divisionA.get(i), divisionB.get(i));
            for (int k2 = 0; k2 < result.length; k2++) {
                for (int l = 0; l < result[k2].length && exit ; l++) {
                    if (matrix.length == j) {
                        exit = true;
                    }else {
                        result[i][j] = matrix[i][j];
                    }
                }
            }
        }

        return result ;
    }

    /**
     * Permite dividir una matriz en partes más pequeñas indicando que fila o columna a iniciar.
     * @param initialR Fila inicial a tomar
     * @param finishR Fila final
     * @param initialC Columna iniciar a tomar
     * @param finishC Columna final
     * @param matrixA matriz a dividir
     * @return una submatriz de la matrixA.
     */
    private int[][]  splitMatrix(int initialR, int finishR, int initialC, int finishC, int [][] matrixA ) {
            int row = finishR-initialR;
            int column = finishC-initialC;

            int[][]  matrix = new int[++row][++column];
            int i = 0;
            int j = 0;
            int auxColumn = initialC;
            while(i < matrix.length) {
                while(j <  matrix[i].length) {
                    if(column ==  1) {
                        matrix[i][j] =matrixA[initialR++][initialC];
                    }else if(row == 1) {
                        matrix[i][j] =matrixA[initialR][initialC++];
                    }else {
                        matrix[i][j] =matrixA[initialR][initialC];
                        initialC++;
                    }
                    j++;
                }
                initialR++;
                initialC = auxColumn;
                i++;
            }


            return matrix;
        }




}



