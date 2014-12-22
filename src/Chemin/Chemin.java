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
	int etape = 2;  //Limiter le nombre d'étapes
	
	public Chemin()
	{
		chemin = new LinkedList<Action>();
	}
	
	public Chemin(Cube t, Cube s)
	{
		chemin = new LinkedList<Action>();
		this.original = t;
		this.source = s;
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
		return chemin.size();
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
				System.out.println(limite);
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
			return;
		}
		runFindSimple(etape);
	}
	
	void runFindSimple(int l)
	{
		findSimple(l, true, false, false);
	}
	
	/*
	 * Deux méthodes pour tester le calcul de la distance entre les deux dispositions d'une pièce, sommet ou arête
	 */
	
	public int runFindCoin()
	{
		if (original.same(source))
		{
			found = true;
			
			return 0;
		}
		else
		{
			findSimple(0, false, true, false);
			return chemin.size();
		}
	}
	
	public int runFindEdge()
	{
		if (original.same(source))
		{
			found = true;
			return 0;
		}
		else
		{
			findSimple(0, false, false, true);
			return chemin.size();
		}
	}
	
}
