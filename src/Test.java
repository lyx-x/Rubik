import Config.*;
import Cube.*;
import Chemin.*;

public class Test {
	
	public static void main(String[] args){
		init();
		//testFindSimple(8);
		//debugDistanceCoinEdge(20);
		//testAStar(10, 't');
		//testInitDistance();
		//testInitPattern();
		//testDistance();
		//debugCoinEdge();
		//debugCoins(30);
		//Pattern.print();
		testFindDFS(20, 'p');
		//testHash();
		//testPattern(5);
		//testCompare(20);
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
	 * Initialisation du programme
	 */
	
	static void init(){
		System.out.println("\n=========== Début d'initialisation ===========\n");
		Cube.setWidth(40);
		//Pattern.readPattern("Coin.txt", "EdgeOne.txt", "EdgeTwo.txt");  //lire les fichiers
		Pattern.readBinaryPattern("Coin.dat", "EdgeOne.dat", "EdgeTwo.dat");
		//Pattern.printResume();
		Distance.readDistance("DistanceManhattan.txt");
		System.out.println("\n=========== Fin d'initialisation ===========\n");
	}
	
	/*
	 * Générer une suite de test simple en augmentant la complexité du cube à résoudre
	 */
	
	static void testFindSimple(int etape){
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
	
	static void debugDistanceCoinEdge(int etape){
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
	
	/*
	 * Tester de façon déterminée si on trouve le bon nombre d'étape pour remettre une pièce en position
	 */
	
	static void debugCoinEdge(){
		Coin c = new Coin(7, 2, 2, 5);
		int tmp = c.recoverSteps();
		System.out.println(tmp);
	}
	
	/*
	 * La recherche A*
	 */
	
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
	
	/*
	 * Tester la classe Distance
	 */
	
	static void testInitDistance()
	{
		Distance.calculateDistance();
		Distance.print();
	}
	
	/*
	 * Tester la classe Pattern
	 */
	
	static void testInitPattern()
	{
		Pattern.calculatePattern();
		Pattern.printResume();
	}
	
	/*
	 * Tester le calcul de la distance
	 */
	
	static void testDistance()
	{
		Cube test = new Cube(Cube.src);
		melanger(test, 3, true);
		System.out.format("\nDistance minimale : %d\n",test.distance('s'));
		//test.show2D();
		//test.printDistance();
	}
	
	/*
	 * Tester si on peut calculer le nombre d'étapes pour remettre tous les sommets
	 */
	
	static void debugCoins(int etape)
	{
		for (int j = 5 ; j <= etape ; j++)
		{
			System.out.format("\n//======== Test %d =======\n", j + 1);
			Cube test = new Cube(Cube.src);
			melanger(test, j, false);
			Coin[] coins = new Coin[8];
			for (int i = 0 ; i < 8 ; i++)
			{
				coins[i] = new Coin(i, test);
			}
			long startTime = System.currentTimeMillis();
			int tmp = Coin.recoverSteps(coins);  //on pourra changer la méthode de recherche
			long endTime = System.currentTimeMillis();
			long duration = endTime - startTime;
			System.out.println(tmp);
			System.out.printf("\nElapsed time: %d milliseconds\n", duration);
		}	
	}
	
	/*
	 * Test principal qu'on utilise pour résoudre le cube
	 */
	
	static void testFindDFS(int etape, char mode){
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
	
	/*
	 * Tester la fonction de hachage
	 */
	
	static void testHash(){
		Cube test = new Cube(Cube.src);
		melanger(test, 1, true);
		test.show2D();
		System.out.println(test.hashCoin());
		System.out.println(test.hashEdgeOne());
		System.out.println(test.hashEdgeTwo());
	}
	
	/*
	 * Tester le bon fonctionnement du pattern
	 * On constate qu'une limite de 5 étapes ne sont pas suffisante, mais une plus grande limite demande beaucoup de temps pour l'initiation
	 */

	static void testPattern(int steps){
		long startTime = System.currentTimeMillis();
		//Pattern.readPattern("Coin.txt", "EdgeOne.txt", "EdgeTwo.txt");
		Pattern.readBinaryPattern("Coin.dat", "EdgeOne.dat", "EdgeTwo.dat");
		Pattern.printResume();
		int i = 0;
		while(i<20)
		{
			System.out.format("\n//======== Test %d =======\n", i++);
			Cube test = new Cube(Cube.src);
			melanger(test, steps, true);
			System.out.println(test.hashCoin());
			System.out.println(test.hashEdgeOne());
			System.out.println(test.hashEdgeTwo());
			System.out.format("\nDistance minimale Coin : %d\n",test.distance('c'));
			System.out.format("\nDistance minimale EdgeOne : %d\n",test.distance('o'));
			System.out.format("\nDistance minimale EdgeTwo : %d\n",test.distance('e'));
		}
		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;
		System.out.printf("\nElapsed time: %d milliseconds\n", duration);
	}
	
	/*
	 * Comparer la méthode Pattern et la méthode avec une distance simple, très grande différence
	 */
	
	static void testCompare(int etape)
	{
		for (int i = 0 ; i <= etape ; i++)
		{
			System.out.format("\n//======== Test %d =======\n", i + 1);
			Cube test = new Cube(Cube.src);
			melanger(test, i, true);
			Cube compare = new Cube(test);
			System.out.format("\nDistance minimale : %d\n",test.distance('p'));
			long startTime = System.currentTimeMillis();
			Chemin ans = new Chemin(test, Cube.src);
			ans.runDFS('p');  //Trouver le chemin
			long endTime = System.currentTimeMillis();
			long duration = endTime - startTime;
			ans.print();
			System.out.printf("\nElapsed time: %d milliseconds pour Pattern\n\n", duration);
			System.out.format("\nDistance minimale : %d\n",test.distance('i'));
			startTime = System.currentTimeMillis();
			ans = new Chemin(compare, Cube.src);
			ans.runDFS('i');  //Trouver le chemin
			endTime = System.currentTimeMillis();
			duration = endTime - startTime;
			ans.print();
			System.out.printf("\nElapsed time: %d milliseconds pour Manhattan\n", duration);
		}	
	}
}
