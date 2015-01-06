package Chemin;
import java.util.*;

/*
 * Pour construire une queue de priorité lors de la recherche A*
 */

public class ActionComparator implements Comparator<Action>{
	
	@Override
    public int compare(Action x, Action y)
    {
        if (x.change < y.change)
        {
            return -1;
        }
        if (x.change > y.change)
        {
            return 1;
        }
        return 0;
    }

}