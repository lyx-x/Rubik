package Config;

import Cube.*;

import java.io.*;
import java.util.*;

public class Pattern {
	
	public static HashMap<Long, Integer> coin = new HashMap<Long, Integer>();
	public static HashMap<Long, Integer> edgeOne = new HashMap<Long, Integer>();
	public static HashMap<Long, Integer> edgeTwo = new HashMap<Long, Integer>();
	
	public Pattern(){
		coin = new HashMap<Long, Integer>();
		edgeOne = new HashMap<Long, Integer>();
		edgeTwo = new HashMap<Long, Integer>();
		coin.put(3031323334353637L, 0);
		edgeOne.put(10203041214L, 0);
		edgeTwo.put(152325343545L, 0);
		int limite = 6;
		//patternDFSInt(limite);
		//patternDFSNaive(limite);
		patternDFS(new Cube(Cube.src), 1, limite, -1);
	}
	
	/*
	 * Le parcours est en profondeur pour une simple raison de stocker les positions à parcourir
	 * Malgré la simplification d'enregistrement de la liste d'actions, un parcours en largeur est toujours impossible à faire
	 * Cependant, les deux méthodes de simplification ont la même performance dans le parcours en profondeur
	 * Car elles ont toutes besoin d'être converties en liste d'actions
	 */
	
	//Environ 9 secondes pour une limite de 5 étapes
	
	void patternDFSInt(int limite)
	{
		LinkedList<Long> queue = new LinkedList<Long>();
		queue.addLast(1L);
		while(!queue.isEmpty())
		{
			long cur = queue.pop();
			LinkedList<Action> current = Action.fromInt(cur);
			Cube test = new Cube(Cube.src);
			int currentFace = -1;
			for (Action a : current)
			{
				a.Run(test);
				currentFace = a.Face();  //Enregistrer la face qu'on vient de tourner pour ne pas la tourner deux fois de suite
			}
			if (current.size() > limite - 1)  //Limiter le nombre d'étape
			{
				continue;
			}
			for (int face = 0 ; face < 6 ; face++)
			{
				if (currentFace == face) continue;
				for (int tour = 0 ; tour < 3 ; tour++)
				{
					Action a = new Action(face, tour);
					a.Run(test);
					{
						long key = test.hashCoin();
						if (!coin.containsKey(key)) 
							coin.put(key, current.size() + 1);
						key = test.hashEdgeOne();
						if (!edgeOne.containsKey(key))
							edgeOne.put(key, current.size() + 1);
						key = test.hashEdgeTwo();
						if (!edgeTwo.containsKey(key))
							edgeTwo.put(key, current.size() + 1);
					}
					queue.addFirst((cur * 10 + a.Face()) * 10 + a.Tour());
					a.Rollback(test);  //Afin de tester les autres chemins, il faut revenir en arrière
				}
			}
		}
	}
	
	//Environ 9 secondes pour une limite de 5 étapes
	
	void patternDFSNaive(int limite)
	{
		LinkedList<LinkedList<Action>> queue = new LinkedList<LinkedList<Action>>();
		queue.addLast(new LinkedList<Action>());
		while(!queue.isEmpty())
		{
			LinkedList<Action> current = queue.pop();  
			Cube test = new Cube(Cube.src);
			int currentFace = -1;
			for (Action a : current)
			{
				a.Run(test);
				currentFace = a.Face(); 
			}
			if (current.size() > limite - 1)  
			{
				continue;
			}
			for (int face = 0 ; face < 6 ; face++)
			{
				if (currentFace == face) continue;
				for (int tour = 0 ; tour < 3 ; tour++)
				{
					Action a = new Action(face, tour);
					a.Run(test);
					{
						long key = test.hashCoin();
						if (!coin.containsKey(key)) 
							coin.put(key, current.size() + 1);
						key = test.hashEdgeOne();
						if (!edgeOne.containsKey(key))
							edgeOne.put(key, current.size() + 1);
						key = test.hashEdgeTwo();
						if (!edgeTwo.containsKey(key))
							edgeTwo.put(key, current.size() + 1);
					}
					LinkedList<Action> tmp = new LinkedList<Action>();  
					for (Action i : current)
					{
						tmp.add(i);
					}
					tmp.add(a);
					queue.addFirst(tmp);
					a.Rollback(test);  
				}
			}
		}
	}
	
	/*
	 * Puisqu'on choisit le DFS, on aimerait réutiliser les cubes qu'on a, ce qui accélère le parcours
	 * Environ 3 secondes pour 5 étapes et 45 pour 6 étapes, on a bien un facteur de 15
	 */
	
	void patternDFS(Cube c, int level, int limite, int lastFace)
	{
		for (int face = 0 ; face < 6 ; face++)
		{
			if (lastFace == face) continue;  //toujours réduire 18 en 15
			for (int tour = 0 ; tour < 3 ; tour++)
			{
				Action a = new Action(face, tour);
				a.Run(c);
				{
					long key = c.hashCoin();
					if (!coin.containsKey(key) || (coin.containsKey(key) && coin.get(key) > level)) 
						coin.put(key, level);
					key = c.hashEdgeOne();
					if (!edgeOne.containsKey(key) || (edgeOne.containsKey(key) && edgeOne.get(key) > level))
						edgeOne.put(key, level);
					key = c.hashEdgeTwo();
					if (!edgeTwo.containsKey(key) || (edgeTwo.containsKey(key) && edgeTwo.get(key) > level))
						edgeTwo.put(key, level);
				}
				if (level < limite) patternDFS(c, level + 1, limite, face);
				a.Rollback(c);  //on retourne en arrière après le parcours
			}
		}
	}
	
	/*
	 * Bilan du pattern
	 */
	
	public static void print(){
		for (long l : coin.keySet())
		{
			System.out.format("%d : %d\n", l, coin.get(l));
		}
		System.out.format("%d dispositions pour les sommets.\n", coin.keySet().size());
		for (long l : edgeOne.keySet())
		{
			System.out.format("%d : %d\n", l, edgeOne.get(l));
		}
		System.out.format("%d dispositions pour les 6 premières arêtes.\n", edgeOne.keySet().size());
		for (long l : edgeTwo.keySet())
		{
			System.out.format("%d : %d\n", l, edgeTwo.get(l));
		}
		System.out.format("%d dispositions pour les 6 autres arêtes.\n", edgeTwo.keySet().size());
	}
	
	/*
	 * Ecrire les résultats dans un fichier
	 */
	
	public static void file(){
		try {
			File fileCoin = new File("Coin.txt");
			BufferedWriter outputCoin = new BufferedWriter(new FileWriter(fileCoin));
			for (long l : coin.keySet())
			{
				String str = String.valueOf(l);
				str += "\n" + coin.get(l) + "\n";
				outputCoin.write(str);
			}
			outputCoin.close();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		try {
			File fileEdgeOne = new File("EdgeOne.txt");
			BufferedWriter outputEdgeOne = new BufferedWriter(new FileWriter(fileEdgeOne));
			for (long l : edgeOne.keySet())
			{
				String str = String.valueOf(l);
				str += "\n" + edgeOne.get(l) + "\n";
				outputEdgeOne.write(str);
			}
			outputEdgeOne.close();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		try {
			File fileEdgeTwo = new File("EdgeTwo.txt");
			BufferedWriter outputEdgeTwo = new BufferedWriter(new FileWriter(fileEdgeTwo));
			for (long l : edgeTwo.keySet())
			{
				String str = String.valueOf(l);
				str += "\n" + edgeTwo.get(l) + "\n";
				outputEdgeTwo.write(str);
			}
			outputEdgeTwo.close();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Lire les résultats
	 */
	
	public Pattern(boolean fichier)
	{
		try{
			FileReader file = new FileReader("Coin.txt");
			BufferedReader read = new BufferedReader(file);
			try
			{
				while (read.ready()) 
				{
					String l = read.readLine();
					long key = Long.parseLong(l);
					String s = read.readLine();
					coin.put(key, Integer.parseInt(s));
				}
			}
			catch (Exception e)
			{
				System.out.println(e.getMessage());
			}
			read.close();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		try{
			FileReader file = new FileReader("EdgeOne.txt");
			BufferedReader read = new BufferedReader(file);
			try
			{
				while (read.ready()) 
				{
					String l = read.readLine();
					long key = Long.parseLong(l);
					String s = read.readLine();
					edgeOne.put(key, Integer.parseInt(s));
				}
			}
			catch (Exception e)
			{
				System.out.println(e.getMessage());
			}
			read.close();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		try{
			FileReader file = new FileReader("EdgeTwo.txt");
			BufferedReader read = new BufferedReader(file);
			try
			{
				while (read.ready()) 
				{
					String l = read.readLine();
					long key = Long.parseLong(l);
					String s = read.readLine();
					edgeTwo.put(key, Integer.parseInt(s));
				}
			}
			catch (Exception e)
			{
				System.out.println(e.getMessage());
			}
			read.close();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
