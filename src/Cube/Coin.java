package Cube;
import Chemin.*;

/*
 * Cette classe modélise les pièces au sommet, donc trois couleurs
 * L'explication est la même que la classe Edge
 */

public class Coin {
	
	int First;
	int[] fCoord;
	int Second;
	int[] sCoord;
	int Third;
	int[] tCoord;
	int index = -1;
	int occupied = -1;
	
	static int[][][] realPosition = {
		{{0, 2, 0}, {1, 0, 2}, {2, 0, 0}},
		{{0, 0, 0}, {1, 0, 0}, {4, 0, 2}},
		{{0, 2, 2}, {2, 0, 2}, {3, 0, 0}},
		{{0, 0, 2}, {3, 0, 2}, {4, 0, 0}},
		{{1, 2, 2}, {2, 2, 0}, {5, 0, 0}},
		{{1, 2, 0},	{4, 2, 2}, {5, 2, 0}},
		{{2, 2, 2}, {3, 2, 0}, {5, 0 ,2}},
		{{3, 2 ,2}, {4, 2, 0}, {5, 2, 2}},
		};
	
	static int[][] realCoord = {
		{2, 0, 2}, {0, 0, 2}, {2, 2, 2}, {0, 2, 2}, {2, 0, 0}, {0, 0, 0}, {2, 2, 0}, {0, 2, 0}
	};
	
	public Coin(int i, Cube c)
	{
		this.fCoord = realPosition[i][0];
		this.sCoord = realPosition[i][1];
		this.tCoord = realPosition[i][2];
		this.First = c.color[fCoord[0]][fCoord[1]][fCoord[2]];
		this.Second = c.color[sCoord[0]][sCoord[1]][sCoord[2]];
		this.Third = c.color[tCoord[0]][tCoord[1]][tCoord[2]];
		this.index = Index();
		this.occupied = i;
	}
	
	public Coin(int i, int f, int s, int t)
	{
		this.First = f;
		this.Second = s;
		this.Third = t;
		this.fCoord = realPosition[i][0];
		this.sCoord = realPosition[i][1];
		this.tCoord = realPosition[i][2];
		this.index = Index();
		this.occupied = i;
	}
	
	public Coin(int[] fc, int f, int[] sc, int s, int[] tc, int t)
	{
		this.First = f;
		this.fCoord = fc;
		this.Second = s;
		this.sCoord = sc;
		this.Third = t;
		this.tCoord = tc;
		this.index = this.Index();
	}
	
	public int Index()
	{
		int[] l = {First, Second, Third};
		for (int i = 0 ; i < 2 ; i++)
		{
			for (int j = i + 1 ; j < 3 ; j++)
			{
				if (l[i] > l[j])
				{
					int tmp = l[i];
					l[i] = l[j];
					l[j] = tmp;
				}
			}
		}
		switch (l[0])
		{
		case 0:
			switch (l[1])
			{
			case 1:
				switch (l[2])
				{
				case 2:
					return 0;
				case 4:
					return 1;
				}
				break;
			case 2:
				if (l[2] == 3) return 2;
				break;
			case 3:
				if (l[2] == 4) return 3;
				break;
			}
			break;
		case 1:
			switch (l[1])
			{
			case 2:
				if (l[2] == 5) return 4;
				break;
			case 4:
				if (l[2] == 5) return 5;
				break;
			}
			break;
		case 2:
			if (l[1] == 3 && l[2] == 5) return 6;
			break;
		case 3:
			if (l[1] == 4 && l[2] == 5) return 7;
			break;
		}
		return -1;
	}
	
	void makeTest(Cube t)
	{
		t.setColor(First, fCoord);
		t.setColor(Second, sCoord);
		t.setColor(Third, tCoord);
	}
	
	void makeBlack(Cube t)
	{
		if (index == -1) return;
		t.setColor(realPosition[index][0]);
		t.setColor(realPosition[index][1]);
		t.setColor(realPosition[index][2]);
	}
	
	public int recover(Cube t)
	{
		Cube black = new Cube(Cube.black);
		Cube test = new Cube(Cube.black);
		makeTest(test);
		makeBlack(black);
		Chemin ans = new Chemin(test, black);
		ans.runFindAStar('t');
		for (Action a : ans.chemin())
		{
			a.Run(t);
		}
		ans.print();
		return ans.size();
	}
	
	public int recoverSteps()
	{
		Cube black = new Cube(Cube.black);
		Cube test = new Cube(Cube.black);
		makeTest(test);
		makeBlack(black);
		Chemin ans = new Chemin(test, black);
		int r = ans.runFindSimple(3, 6);
		return r;
	}
	
	public int manhattanDistance()
	{
		int dist = 0;
		if (occupied < 0 || index < 0) return -1;
		for (int i = 0 ; i < 3 ; i++)
		{
			dist += Math.abs(realCoord[occupied][i] - realCoord[index][i]);
		}
		return dist;
	}
	
	public static int recoverSteps(Coin[] coins)
	{
		Cube black = new Cube(Cube.black);
		Cube test = new Cube(Cube.black);
		for (Coin c : coins)
		{
			c.makeTest(test);
			c.makeBlack(black);
		}
		Chemin ans = new Chemin(test, black);
		ans.runDFS('m');
		int r = ans.size();
		return r;
	}
	
	public static int recoverStepsImproved(Coin[] coins)
	{
		Cube black = new Cube(Cube.black);
		Cube test = new Cube(Cube.black);
		for (Coin c : coins)
		{
			c.makeTest(test);
			c.makeBlack(black);
		}
		int i = 0;
		for (i = 0 ; i < 8 ; i++)
		{
			if (coins[i].index == 7)
				break;
		}
		int cost = coins[i].recover(test);
		Chemin ans = new Chemin(test, black);
		ans.runDFSLimited('t', 3);
		int r = ans.size();
		return r + cost;
	}
	
	public boolean correctPosition()
	{
		if (First != fCoord[0]) return false;
		if (Second != sCoord[0]) return false;
		if (Third != tCoord[0]) return false;
		return true;
	}
	
	public void print()
	{
		System.out.format("Coin #%d: Color %d, Position %d %d %d ; Color %d, Position %d %d %d ; Color %d, Position %d %d %d\n", index, First, fCoord[0], fCoord[1], fCoord[2], Second, sCoord[0], sCoord[1], sCoord[2], Third, tCoord[0], tCoord[1], tCoord[2]);
	}

}
