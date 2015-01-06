package Chemin;
import java.util.*;

public class DispositionComparator implements Comparator<Disposition>{
	
	@Override
    public int compare(Disposition x, Disposition y)
    {
        if (x.actions.size() < y.actions.size())
        {
            return -1;
        }
        if (x.actions.size() > y.actions.size())
        {
            return 1;
        }
        return 0;
    }

}