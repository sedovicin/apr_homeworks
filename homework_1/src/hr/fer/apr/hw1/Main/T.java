package hr.fer.apr.hw1.Main;

import hr.fer.apr.hw1.Matrix;

public class T {

	public static void main(final String[] args) {
		// TODO Auto-generated method stub
		try {
			Matrix A = new Matrix(3, 3, 1, 5, 3, 2, 1, 0, 3, 0, 2);
			Matrix B = new Matrix(1, 3, 1, 2, 3);

			System.out.println(A);
			System.out.println(A.decomposeLUP());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
