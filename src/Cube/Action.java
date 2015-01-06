package Cube;
import java.util.*;

/*
 * Cette classe permet de stocker les actions dans une liste sans utiliser le couple d'entier qui n'existe qu'en C++
 */

public class Action {
	
	int face;
	int tour;
	
	public int change = 100;
	
	public Action(int f, int t)
	{
		this.face = f;
		this.tour = t;
	}
	
	public void Run(Cube c)
	{
		c.tourner(face, tour);
	}
	
	/*
	 * Revenir en arrière consiste à finir un tour de 360 degrés
	 */
	
	public void Rollback(Cube c)
	{
		c.tourner(face, 2 - tour);
	}
	
	/*
	 * Les membres étant privés, ne sont accessibles que par cette méthode
	 */
	
	public int Tour()
	{
		return tour;
	}
	
	public int Face()
	{
		return face;
	}
	
	public void print()
	{
		String[] script = {"Quart de tour, Face %d, Trigo\n", "Demie-tour, Face %d\n", "Quart de tour, Face %d, Horaire\n"};
		System.out.format(script[tour], face);
	}
	
	/*
	 * La partie suivante raccourcit l'écriture de la liste d'actions pour faciliter leur enregistrement
	 */
	
	public static String toChar(LinkedList<Action> actions){
		StringBuilder str = new StringBuilder();
		for (Action a : actions)
		{
			str.append(a.Face());
			str.append(a.Tour());
		}
		return str.toString();
	}
	
	public static LinkedList<Action> fromChar(String s){
		LinkedList<Action> actions = new LinkedList<Action>();
		int l = s.length();
		for (int i = 1 ; i < l ; i += 2)
		{
			actions.addLast(new Action(((int)s.charAt(i)) - 48, ((int)s.charAt(i + 1)) - 48));
		}
		return actions;
	}
	
	public static long toInt(LinkedList<Action> actions){
		long ans = 1;
		for (Action a : actions)
		{
			ans = ans * 10 + a.Face();
			ans = ans * 10 + a.Tour();
		}
		return ans;
	}
	
	public static LinkedList<Action> fromInt(long n){
		return fromChar(String.valueOf(n));
	}
}
