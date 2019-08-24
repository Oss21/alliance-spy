package model;

public class Board {
	/**
	 * Número primo.
	 */
	private boolean primeNumber;
	/**
	 * Número de filas de la matriz
	 */
	private int row;
	/**
	 * Número de columnas de la matriz.
	 */
	private int column;
	
	/**
	 * Constructor de la clase tablero
	 * @param row Número de filas.
	 * @param column Número de columnas.
	 */
	public Board(int row, int column) {
		this.row = row;
		this.column = column;
		this.primeNumber = false;
	}

	public void createRandomMatrix(boolean repeatedNumbers, int row , int column,int [][] matrix ) {
		
		//Initialize the Matrix with its rows and columns.
		matrix = new int [row][column] ;
		
			if(repeatedNumbers) {
				int number = 0;	
					for (int i = 0; i < matrix.length; i++) {
						for (int j = 0; j < matrix[i].length; j++) {
							//Random numbers from 1 to 20.
							number = (int) (Math.random()*20)+1;	
							matrix[i][j] = number;
						}
					}
			}else {
				int size = row*column;
				int [] matrixTemp = randomArray(size);
				int count = 0;
				for (int i = 0; i < matrix.length; i++) {
					for (int j = 0; j < matrix[i].length; j++) {
						//Random numbers from 1 to 20.
						matrix[i][j] =matrixTemp[count++];
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

}



