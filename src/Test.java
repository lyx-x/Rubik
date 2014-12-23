import Config.*;
import Cube.*;
import Chemin.*;

public class Test {
	
	public static void main(String[] args){
		init("DistanceSimple.txt");
		//testSimple(8);
		//debugCoinEdge(20);
		//testAStar(10, 't');
		//testInit();
		//testDistance();
		//debugCoinEdge();
		debugCoins();
		//testDFS(20, 't');
	}
	
	/*
	 * Utiliser les valeurs aléatoires pour mélanger le cube, le nombre d'action est fixé pour le test
	 */
	
	public static void melanger(Cube c, int nombre, boolean text){
		for (int i = 0 ; i < nombre ; i++)
		{
			Action a = new Action((int)(Math.random() * 6), (int)(Math.random() * 3));
			a.Run(c);
			if (text) a.print();  //Mélanger sans afficher l'action
		}
	}
	
	/*
	 * Initialisation du test, on pourrait précalculer la distance entre toutes les dispositions d'une certaine pièce ici
	 */
	
	static void init(String path)
	{
		Cube.setWidth(40);
		Distance init = new Distance(path);
	}
	
	/*
	 * Générer une suite de test simple en augmentant la complexité du cube à résoudre
	 */
	
	static void testSimple(int etape){
		for (int i = 0 ; i <= etape ; i++)
		{
			System.out.format("\n//======== Test %d =======\n", i + 1);
			Cube test = new Cube(Cube.src);
			melanger(test, i, true);
			System.out.format("\nDistance minimale : %d\n",test.distance('s'));
			Cube dest = new Cube(test);  //Sauvegarder la disposition pour l'affichage
			dest.show2D();
			long startTime = System.currentTimeMillis();
			Chemin ans = new Chemin(test, Cube.src);
			ans.runFindSimple(i);  //Trouver le chemin
			long endTime = System.currentTimeMillis();
			long duration = endTime - startTime;
			ans.print();
			System.out.printf("\nElapsed time: %d milliseconds\n", duration);
		}	
	}
	
	/*
	 * Une suite de test pour calculer la distance entre une pièce à une position quelconque et sa posotion correcte
	 */
	
	static void debugCoinEdge(int etape){
		int max = 0;
		System.out.println("Test Edge :");
		for (int i = 0 ; i < etape ; i++)
		{
			Cube test = new Cube(Cube.src);
			melanger(test, 10, false);
			Edge e = new Edge((int)(Math.random() * 12), test);
			int tmp = e.recoverSteps();
			if (max < tmp) max = tmp;
			System.out.println(tmp);
		}
		System.out.println("Test Coin :");
		for (int i = 0 ; i < etape ; i++)
		{
			Cube test = new Cube(Cube.src);
			melanger(test, 10, false);
			Coin c = new Coin((int)(Math.random() * 8), test);
			int tmp = c.recoverSteps();
			if (max < tmp) max = tmp;
			System.out.println(tmp);
		}
		System.out.format("Max steps : %d\n", max);  //Estimer le nombre d'étape maximal
	}
	
	static void debugCoinEdge(){
		Coin c = new Coin(7, 2, 2, 5);
		int tmp = c.recoverSteps();
		System.out.println(tmp);
	}
	
	static void testAStar(int etape, char mode){
		for (int i = 0 ; i <= etape ; i++)
		{
			System.out.format("\n//======== Test %d =======\n", i + 1);
			Cube test = new Cube(Cube.src);
			melanger(test, i, true);
			System.out.format("\nDistance minimale : %d\n",test.distance(mode));
			Cube dest = new Cube(test);  //Sauvegarder la disposition pour l'affichage
			dest.show2D();
			long startTime = System.currentTimeMillis();
			Chemin ans = new Chemin(test, Cube.src);
			ans.runFindAStar(mode);  //Trouver le chemin
			long endTime = System.currentTimeMillis();
			long duration = endTime - startTime;
			ans.print();
			System.out.printf("\nElapsed time: %d milliseconds\n", duration);
		}	
	}
	
	static void testInit()
	{
		Distance p = new Distance();
		Distance.print();
	}
	
	static void testDistance()
	{
		Cube test = new Cube(Cube.src);
		melanger(test, 3, true);
		System.out.format("\nDistance minimale : %d\n",test.distance('s'));
		//test.show2D();
		//test.printDistance();
	}
	
	static void debugCoins()
	{
		Cube test = new Cube(Cube.src);
		melanger(test, 20, true);
		Coin[] coins = new Coin[8];
		for (int i = 0 ; i < 8 ; i++)
		{
			coins[i] = new Coin(i, test);
		}
		int tmp = Coin.recoverSteps(coins);
		System.out.println(tmp);
	}
	
	static void testDFS(int etape, char mode){
		for (int i = 0 ; i <= etape ; i++)
		{
			System.out.format("\n//======== Test %d =======\n", i + 1);
			Cube test = new Cube(Cube.src);
			melanger(test, i, true);
			System.out.format("\nDistance minimale : %d\n",test.distance(mode));
			long startTime = System.currentTimeMillis();
			Chemin ans = new Chemin(test, Cube.src);
			ans.runDFS(mode);  //Trouver le chemin
			long endTime = System.currentTimeMillis();
			long duration = endTime - startTime;
			ans.print();
			System.out.printf("\nElapsed time: %d milliseconds\n", duration);
		}	
	}

}
