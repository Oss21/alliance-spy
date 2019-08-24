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
	
	public void createCoefficienteMatrix(boolean repeatedNumbers, int row , int column ) {
		
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



