import java.util.concurrent.TimeoutException;

import Config.*;
import Cube.*;
import Chemin.*;

public class Test {
	
	public static void main(String[] args){
		//init();
		//testAffichage(8);
		//testFindDFS(8);
		//debugDistanceCoinEdge(20);
		//testFindPQ(10, 't');
		//testInitDistance();
		testInitPattern();
		//testLimite(6);
		//testSQL(12);
		//testDistance();
		//debugCoinEdge();
		//debugCoins(30);
		//Pattern.print();
		//testFindIDA(20, 'p');
		//testHash();
		//testPattern(20);
		//testCompareFind(20, 'p');
		//statCompareFind(16,5);
		//statCompareDistance(14,10);
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
	
	public static void testAffichage(int etape)
	{
		System.out.println("\n=========== Test d'affichage ===========\n");
		Cube test = new Cube(Cube.src);
		melanger(test, etape, true);
		test.show2D();
	}
	
	/*
	 * Initialisation du programme
	 */
	
	static void init(){
		System.out.println("\n=========== Début d'initialisation ===========\n");
		Cube.setWidth(40);
		//Pattern.readPattern("Coin.txt", "EdgeOne.txt", "EdgeTwo.txt");  //lire les fichiers
		//Pattern.readBinaryPattern("Coin.dat", "EdgeOne.dat", "EdgeTwo.dat");
		//Pattern.printResume();
		//Distance.readDistance("DistanceSimple.txt","DistanceManhattan.txt");
		PatternArray.readBinaryPattern();
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
			//Cube dest = new Cube(test);  //Sauvegarder la disposition pour l'affichage
			//dest.show2D();
			long startTime = System.currentTimeMillis();
			Chemin ans = new Chemin(test, Cube.src);
			try{
				ans.runFindDFS(i, true);  //Trouver le chemin
				ans.print();
			}
			catch (TimeoutException e)
			{
				System.err.println(e.getMessage());
			}
			long endTime = System.currentTimeMillis();
			long duration = endTime - startTime;
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
	 * La recherche avec la queue de priorité
	 */
	
	static void testFindPQ(int etape, char mode){
		for (int i = 0 ; i <= etape ; i++)
		{
			System.out.format("\n//======== Test %d =======\n", i + 1);
			Cube test = new Cube(Cube.src);
			melanger(test, i, true);
			System.out.format("\nDistance minimale : %d\n",test.distance(mode));
			//Cube dest = new Cube(test);  //Sauvegarder la disposition pour l'affichage
			//dest.show2D();
			long startTime = System.currentTimeMillis();
			Chemin ans = new Chemin(test, Cube.src);
			try{
				ans.runFindPQ(mode);  //Trouver le chemin
				ans.print();
			}
			catch (TimeoutException e)
			{
				System.err.println(e.getMessage());
			}
			long endTime = System.currentTimeMillis();
			long duration = endTime - startTime;
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
		long startTime = System.currentTimeMillis();
		//Pattern.calculatePattern();
		//Pattern.printResume();
		PatternArray.calculatePattern((byte)7);
		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;
		System.out.printf("\nElapsed time: %d milliseconds\n", duration);
		
	}
	
	static void testLimite(int limite)
	{
		long startTime = System.currentTimeMillis();
		Pattern.patternLimite(new Cube(Cube.src), (byte)1, limite, -1);
		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;
		System.out.printf("\nElapsed time: %d milliseconds\n", duration);
	}
	
	static void testSQL(int limite)
	{
		long startTime = System.currentTimeMillis();
		Pattern.calculatePatternSQL(limite);
		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;
		System.out.printf("\nElapsed time: %d milliseconds\n", duration);
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
	
	static void testFindIDA(int etape, char mode){
		for (int i = 0 ; i <= etape ; i++)
		{
			System.out.format("\n//======== Test %d =======\n", i + 1);
			Cube test = new Cube(Cube.src);
			melanger(test, i, true);
			System.out.format("\nDistance minimale : %d\n",test.distance(mode));
			long startTime = System.currentTimeMillis();
			Chemin ans = new Chemin(test, Cube.src);
			try{
				ans.runFindIDA(mode, true);  //Trouver le chemin
				ans.print();
			}
			catch (TimeoutException e)
			{
				System.out.println(e.getMessage());
			}	
			long endTime = System.currentTimeMillis();
			long duration = endTime - startTime;
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
	
	static void testCompareFind(int etape, char mode)
	{
		System.out.format("Mode %c\n", mode);
		for (int i = 0 ; i <= etape ; i++)
		{
			System.out.format("\n//======== Test %d =======\n", i + 1);
			Cube test = new Cube(Cube.src);
			melanger(test, i, true);
			Cube compare = new Cube(test);
			System.out.format("\nDistance minimale : %d\n",test.distance('p'));
			long startTime = System.currentTimeMillis();
			Chemin ans = new Chemin(compare, Cube.src);
			try{
				ans.runFindIDA(mode, true);  //Trouver le chemin
				ans.print();
			}
			catch (TimeoutException e)
			{
				System.out.println(e.getMessage());
			}	
			long endTime = System.currentTimeMillis();
			long duration = endTime - startTime;
			System.out.printf("\nElapsed time: %d milliseconds pour IDA*\n\n", duration);
			
			System.out.println("-----------");
			compare = new Cube(test);
			System.out.format("\nDistance minimale : %d\n",compare.distance('i'));
			startTime = System.currentTimeMillis();
			ans = new Chemin(compare, Cube.src);
			try{
				ans.runFindPQ(mode);  //Trouver le chemin
				ans.print();
			}
			catch (TimeoutException e)
			{
				System.out.println(e.getMessage());
			}	
			endTime = System.currentTimeMillis();
			duration = endTime - startTime;
			System.out.printf("\nElapsed time: %d milliseconds pour BFS avec la queue de priorité\n", duration);
			
			System.out.println("-----------");
			compare = new Cube(test);
			System.out.format("\nDistance minimale : %d\n",compare.distance('i'));
			startTime = System.currentTimeMillis();
			ans = new Chemin(compare, Cube.src);
			try{
				ans.runFindDFS(10, true);  //Trouver le chemin
				ans.print();
			}
			catch (TimeoutException e)
			{
				System.out.println(e.getMessage());
			}	
			endTime = System.currentTimeMillis();
			duration = endTime - startTime;
			System.out.printf("\nElapsed time: %d milliseconds pour BFS\n", duration);
		}	
	}
	
	static void statCompareFind(int step, int seed)
	{
		int n = 5;
		long time[][] = new long[n][step];
		int count[][] = new int[n][step];
		int stat[][] = new int[n][step];
		for (int i = 0 ; i < n ; i++)
		{
			for (int j = 0 ; j < step ; j++)
			{
				stat[i][j] = 0;
				count[i][j] = 0;
				time[i][j] = 0;
			}
		}
		for (int i = 0 ; i < step ; i++)
		{
			for (int j = 0 ; j <= seed ; j++)
			{
				Cube test = new Cube(Cube.src);
				melanger(test, i, false);
				
				Cube compare = new Cube(test);
				long startTime = System.currentTimeMillis();
				Chemin ans = new Chemin(compare, Cube.src);
				try{
					ans.runFindIDA('p', false);  //Trouver le chemin
					long endTime = System.currentTimeMillis();
					time[4][ans.size()] += endTime - startTime;
					count[4][ans.size()]++;
				}
				catch (TimeoutException e)
				{
					//System.out.println(e.getMessage());
				}	
				
				compare = new Cube(test);
				startTime = System.currentTimeMillis();
				ans = new Chemin(compare, Cube.src);
				try{
					ans.runFindIDAPQ('p', false);  //Trouver le chemin
					long endTime = System.currentTimeMillis();
					time[3][ans.size()] += endTime - startTime;
					count[3][ans.size()]++;
				}
				catch (TimeoutException e)
				{
					//System.out.println(e.getMessage());
				}
				
				if (i > 7) continue;
				
				compare = new Cube(test);
				startTime = System.currentTimeMillis();
				ans = new Chemin(compare, Cube.src);
				try{
					ans.runFindDFS(i, false); //Trouver le chemin
					long endTime = System.currentTimeMillis();
					time[2][ans.size()] = endTime - startTime;
					count[2][ans.size()]++;
				}
				catch (TimeoutException e)
				{
					//System.out.println(e.getMessage());
				}
				
				if (i > 6) continue;
				
				compare = new Cube(test);
				startTime = System.currentTimeMillis();
				ans = new Chemin(compare, Cube.src);
				try{
					ans.runFindBFS(i);  //Trouver le chemin
					long endTime = System.currentTimeMillis();
					time[1][ans.size()] = endTime - startTime;
					count[1][ans.size()]++;
				}
				catch (TimeoutException e)
				{
					//System.out.println(e.getMessage());
				}	
				
				if (i > 5) continue;
				
				compare = new Cube(test);
				startTime = System.currentTimeMillis();
				ans = new Chemin(compare, Cube.src);
				try{
					ans.runFindPQ('p');  //Trouver le chemin
					long endTime = System.currentTimeMillis();
					time[0][ans.size()] = endTime - startTime;
					count[0][ans.size()]++;
				}
				catch (TimeoutException e)
				{
					//System.out.println(e.getMessage());
				}
				
			}
		}
		for (int i = 0 ; i < n ; i++)
		{
			for (int j = 0 ; j < step ; j++)
			{
				if (count[i][j] >= 1)
					stat[i][j] = (int)time[i][j] / count[i][j];
			}
		}
		for (int i = 0 ; i < n ; i++)
		{
			System.out.printf("Méthode #%d :\t", i + 1);
			for (int j = 0 ; j < step ; j++)
			{
				if (count[i][j] >= 1)
					System.out.printf("%d(%d)\t", stat[i][j], j);
				else
					System.out.printf("NA\t", stat[i][j]);
			}
			System.out.println();
		}
	}
	
	static void statCompareDistance(int step, int seed)
	{
		int n = 8;
		long time[][] = new long[n][step];
		int count[][] = new int[n][step];
		int stat[][] = new int[n][step];
		for (int i = 0 ; i < n ; i++)
		{
			for (int j = 0 ; j < step ; j++)
			{
				stat[i][j] = 0;
				count[i][j] = 0;
				time[i][j] = 0;
			}
		}
		char mode[] = {'s', 't', 'm', 'i', 'c', 'o', 't', 'p'};
		int limit[] = {5, 8, 10, 10, 10, 11, 8, 14};
		for (int i = 0 ; i < step ; i++)
		{
			for (int j = 0 ; j <= seed ; j++)
			{
				Cube test = new Cube(Cube.src);
				melanger(test, i, false);
				for (int k = 0 ; k < n ; k++)
				{
					if (k < 1) continue;
					
					if (i > limit[k]) continue;
					
					Cube compare = new Cube(test);
					long startTime = System.currentTimeMillis();
					Chemin ans = new Chemin(compare, Cube.src);
					try{
						ans.runFindIDA(mode[k], false);  //Trouver le chemin
						long endTime = System.currentTimeMillis();
						time[k][ans.size()] += endTime - startTime;
						count[k][ans.size()]++;
					}
					catch (TimeoutException e)
					{
						//System.out.println(e.getMessage());
					}	
				}
			}
		}
		for (int i = 0 ; i < n ; i++)
		{
			for (int j = 0 ; j < step ; j++)
			{
				if (count[i][j] >= 1)
					stat[i][j] = (int)time[i][j] / count[i][j];
			}
		}
		for (int i = 0 ; i < n ; i++)
		{
			System.out.printf("Distance #%c :\t", mode[i]);
			for (int j = 0 ; j < step ; j++)
			{
				if (count[i][j] >= 1)
					System.out.printf("%d(%d)\t", stat[i][j], j);
				else
					System.out.printf("NA\t", stat[i][j]);
			}
			System.out.println();
		}
	}
	
}