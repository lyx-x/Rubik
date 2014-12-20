package Chemin;
import Cube.*;
import java.util.*;

public class Chemin {
	
	LinkedList<Action> chemin;
	Cube original;
	Cube source;
	
	public Chemin()
	{
		chemin = new LinkedList<Action>();
	}
	
	public Chemin(Cube t, Cube s)
	{
		chemin = new LinkedList<Action>();
		this.original = t;
		this.source = s;
		if (original.same(source))
		{
			//return;
		}
		Find(1);
	}
	
	public void print()
	{
		System.out.println();
		for (Action a : chemin)
		{
			a.print();
		}
		System.out.format("Terminé en %d étapes !\n", chemin.size());
	}
	
	public void Find(int l)
	{
		LinkedList<LinkedList<Action>> queue = new LinkedList<LinkedList<Action>>();
		queue.addLast(new LinkedList<Action>());
		while(!queue.isEmpty())
		{
			LinkedList<Action> current = queue.peek();
			Cube test = new Cube(original);
			test.print();
			for (Action a : current)
			{
				a.Run(test);
			}
			if (current.size() > l - 1)
			{
				break;
			}
			for (int face = 0 ; face < 6 ; face++)
			{
				for (int tour = 0 ; tour < 3 ; tour++)
				{
					test.print();
					Action a = new Action(face, tour);
					a.Run(test);
					a.print();
					test.print();
					if (test.same(source))
					{
						a.print();
						chemin = current;
						chemin.add(a);
						return;
					}
					else
					{
						LinkedList<Action> tmp = new LinkedList<Action>();
						for (Action i : current)
						{
							tmp.add(i);
						}
						tmp.add(a);
						queue.addLast(tmp);
					}
					a.Rollback(test);
				}
			}
			queue.pop();
		}
	}
}
