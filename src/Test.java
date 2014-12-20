import Cube.*;
import Chemin.*;

public class Test {
	
	public static void main(String[] args){
		Cube source = new Cube("Test.txt");
		source.setWidth(40);
		int etape = 5;  //Générer une suite de test en augmentant la complexité du cube à résoudre
		for (int i = 0 ; i <= etape ; i++)
		{
			System.out.format("\n//======== Test %d =======\n", i + 1);
			Cube test = new Cube("Test.txt");
			melanger(test, i);
			Cube dest = new Cube(test);
			dest.show2D();
			long startTime = System.currentTimeMillis();
			Chemin ans = new Chemin(test, source);
			ans.setEtape(i);  //Définir le nombre d'étape maximal pour faire la BFS
			ans.RunFind();  //Trouver le chemin
			long endTime = System.currentTimeMillis();
			long duration = endTime - startTime;
			ans.print();
			System.out.printf("\nElapsed time: %d milliseconds\n", duration);
		}
		
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
