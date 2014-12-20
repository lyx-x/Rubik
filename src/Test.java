import Cube.*;
import Chemin.*;

public class Test {
	
	public static void main(String[] args){
		Cube source = new Cube("Test.txt");
		source.show2D();
		Cube test = new Cube("Test.txt");
		melanger(test, 0);
		Cube dest = new Cube(test);
		dest.show2D();
		Chemin ans = new Chemin(test, source);
		ans.print();
	}
	
	/*
	 * Utiliser les valeurs aléatoires pour mélanger le cube, le nombre d'action est fixé pour le test
	 */
	
	public static void melanger(Cube c, int nombre){
		for (int i = 0 ; i < nombre ; i++)
		{
			Action a = new Action((int)(Math.random() * 6), (int)(Math.random() * 3));
			a.Run(c);
			a.print();
		}
	}

}
