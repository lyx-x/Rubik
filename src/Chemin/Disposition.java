package Chemin;
import java.util.*;
import Cube.Action;

public class Disposition {
	
	LinkedList<Action> actions = new LinkedList<Action>();
	int distance;
	
	Disposition(LinkedList<Action> a, int d)
	{
		this.actions = a;
		this.distance = d;
	}
	
	Disposition()
	{
		this.actions = new LinkedList<Action>();
		this.distance = 100;
	}

}
