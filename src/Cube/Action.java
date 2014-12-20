package Cube;

/*
 * Cette classe permet de stocker les actions dans une liste sans utiliser le couple d'entier qui n'existe qu'en C++
 */

public class Action {
	
	int face;
	int tour;
	
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
	
}
