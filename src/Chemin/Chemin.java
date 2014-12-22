package Chemin;
import Cube.*;
import java.util.*;

/*
 * Cette classe permet de trouver un chemin entre deux dispositions
 */

public class Chemin {
	
	LinkedList<Action> chemin;
	Cube original;  //Disposition initiale
	Cube source;  //Disposition finale
	boolean found = false;  //Voir si une solution existe
	int etape = 10;  //Limiter le nombre d'étapes
	int size = -1;
	
	public Chemin()
	{
		chemin = new LinkedList<Action>();
		size = -1;
	}
	
	public Chemin(Cube t, Cube s)
	{
		chemin = new LinkedList<Action>();
		this.original = t;
		this.source = s;
		size = -1;
	}
	
	public void setEtape(int e)
	{
		etape = e;
	}
	
	/*
	 * Retourner la longueur du chemin qu'on a trouvé
	 */
	
	public int size()
	{
		return size;
	}
	
	/*
	 * Retourner l'état final de la recherche
	 */
	
	public boolean found()
	{
		return found;
	}
	
	public void print()
	{
		System.out.println();
		if (!found)
		{
			System.out.format("Pas de solutions en %d étapes\n", etape);
			return;
		}
		System.out.format("Solution en %d étapes trouvée : \n", chemin.size());
		int count = 0;
		for (Action a : chemin)
		{
			count++;
			System.out.format("Etape %d : ", count);
			a.print();
		}
	}
	
	/*
	 * Une méthode privée permettant de parcourir l'arbre en largeur afin d'atteidre le but
	 */
	
	void findSimple(int limite, boolean stepLimite, boolean coin, boolean edge)
	{
		LinkedList<LinkedList<Action>> queue = new LinkedList<LinkedList<Action>>();
		queue.addLast(new LinkedList<Action>());
		while(!queue.isEmpty())
		{
			LinkedList<Action> current = queue.peek();  //On recommence toujours de la disposition initiale
			Cube test = new Cube(original);
			int currentFace = -1;
			for (Action a : current)
			{
				a.Run(test);
				currentFace = a.Face();  //Enregistrer la face qu'on vient de tourner pour ne pas la tourner deux fois de suite
			}
			if (stepLimite && current.size() > limite - 1)  //Limiter le nombre d'étape
			{
				break;
			}
			for (int face = 0 ; face < 6 ; face++)
			{
				if (currentFace == face) continue;
				//if (coin && test.correctCoins(face)) continue;  //Ne pas tourner une face quand les quatre coins sont bons, utilisé pour accélérer le test
				//if (edge && test.correctEdges(face)) continue;
				for (int tour = 0 ; tour < 3 ; tour++)
				{
					Action a = new Action(face, tour);
					a.Run(test);
					if (test.same(source))
					{
						chemin = current;
						chemin.add(a);
						found = true;
						size = chemin.size();
						return;
					}
					else  //Ajouter les nouvelles dispositions intermédiares dans la queue
					{
						LinkedList<Action> tmp = new LinkedList<Action>();  //Toujours copier-coller pour créer une nouvelle suite
						for (Action i : current)
						{
							tmp.add(i);
						}
						tmp.add(a);
						queue.addLast(tmp);
					}
					a.Rollback(test);  //Afin de tester les autres chemins, il faut revenir en arrière
				}
			}
			queue.pop();
		}
	}
	
	public void runFindSimple()
	{
		if (original.same(source))
		{
			found = true;
			return;
		}
		findSimple(etape, true, false, false);
	}
	
	/*
	 * Deux méthodes pour tester le calcul de la distance entre les deux dispositions d'une pièce, sommet ou arête
	 */
	
	public int runFindCoin(boolean limite)
	{
		if (original.same(source))
		{
			found = true;
			return 0;
		}
		else
		{
			findSimple(2, limite, true, false);
			return size;
		}
	}
	
	public int runFindEdge(boolean limite)
	{
		if (original.same(source))
		{
			found = true;
			size = 0;
			return 0;
		}
		else
		{
			findSimple(3, limite, false, true);
			return size;
		}
	}
	
	void findAStar(boolean sum, String mode)
	{
		LinkedList<LinkedList<Action>> queue = new LinkedList<LinkedList<Action>>();
		queue.addLast(new LinkedList<Action>());
		while(!queue.isEmpty())
		{
			LinkedList<Action> current = queue.peek();   //On recommence toujours de la disposition initiale
			Cube test = new Cube(original);
			int currentFace = -1;
			//System.out.println(current.distance);
			for (Action a : current)
			{
				a.Run(test);
				currentFace = a.Face();  //Enregistrer la face qu'on vient de tourner pour ne pas la tourner deux fois de suite
			}
			int currentDistance = test.distance(sum, mode);
			for (int face = 0 ; face < 6 ; face++)
			{
				if (currentFace == face) continue;
				for (int tour = 0 ; tour < 3 ; tour++)
				{
					Action a = new Action(face, tour);
					a.Run(test);
					int dist = test.distance(sum, mode);
					//System.out.println(dist);
					if (test.same(source))
					{
						chemin = current;
						chemin.add(a);
						found = true;
						size = chemin.size();
						return;
					}
					else  //Ajouter les nouvelles dispositions intermédiares dans la queue
					{
						if (dist > currentDistance)
						{
							a.Rollback(test);
							continue;
						}
						LinkedList<Action> tmp = new LinkedList<Action>();  //Toujours copier-coller pour créer une nouvelle suite
						for (Action i : current)
						{
							tmp.add(i);
						}
						tmp.add(a);
						queue.addLast(tmp);
					}
					a.Rollback(test);  //Afin de tester les autres chemins, il faut revenir en arrière
				}
			}
			queue.remove();
		}
	}
	
	void findAStarPQ(boolean sum, String mode)
	{
		PriorityQueue<Disposition> queue = new PriorityQueue<Disposition>(10, new DispositionComparator());
		queue.add(new Disposition());
		while(!queue.isEmpty())
		{
			Disposition current = queue.peek();  //On recommence toujours de la disposition initiale
			Cube test = new Cube(original);
			int currentFace = -1;
			for (Action a : current.actions)
			{
				a.Run(test);
				currentFace = a.Face();  //Enregistrer la face qu'on vient de tourner pour ne pas la tourner deux fois de suite
			}
			for (int face = 0 ; face < 6 ; face++)
			{
				if (currentFace == face) continue;
				for (int tour = 0 ; tour < 3 ; tour++)
				{
					Action a = new Action(face, tour);
					a.Run(test);
					int dist = test.distance(sum, mode) + current.actions.size() + 1;
					//int dist = test.estimateDistance(true, sum, mode) + current.actions.size() + 1;
					//System.out.println(dist);
					if (test.same(source))
					{
						chemin = current.actions;
						chemin.add(a);
						found = true;
						size = chemin.size();
						return;
					}
					else  //Ajouter les nouvelles dispositions intermédiares dans la queue
					{
						if (dist > current.distance)
						{
							//a.Rollback(test);
							//continue;
						}
						LinkedList<Action> tmp = new LinkedList<Action>();  //Toujours copier-coller pour créer une nouvelle suite
						for (Action i : current.actions)
						{
							tmp.add(i);
						}
						tmp.add(a);
						Disposition d = new Disposition(tmp, dist);
						queue.add(d);
					}
					a.Rollback(test);  //Afin de tester les autres chemins, il faut revenir en arrière
				}
			}
			queue.remove();
		}
	}
	
	public void runFindAStar(boolean sum, String mode)
	{
		if (original.same(source))
		{
			found = true;
			size = 0;
			return;
		}
		findAStarPQ(sum, mode);
	}
}
