import Cube.Cube;

public class Test {
	
	public static void main(String[] args){
		//Cube rubik = new Cube();
		Cube rubik = new Cube("Test.txt");
		rubik.tourner(5);
		rubik.show2D();
		//rubik.print();
	}

}
