package Config;
import Cube.*;

public class Path {
	
	public static int[][][][] distCoin;
	public static int[][][] distEdge;
	
	public Path()
	{
		distCoin = new int[8][6][6][6];
		distEdge = new int[12][6][6];
		for (int i = 0 ; i < 8 ; i++)
		{
			for (int j = 0 ; j < 6 ; j++)
			{
				for (int k = 0 ; k < 6 ; k++)
				{
					for (int l = 0 ; l < 6 ; l++)
					{
						Coin c = new Coin(i, j, k, l);
						int steps = c.recoverSteps();
						distCoin[i][j][k][l] = (steps >= 0) ? steps : 9;
					}
				}
			}
		}
		for (int i = 0 ; i < 12 ; i++)
		{
			for (int j = 0 ; j < 6 ; j++)
			{
				for (int k = 0 ; k < 6 ; k++)
				{
					Edge e = new Edge(i, j, k);
					int steps = e.recoverSteps();
					distEdge[i][j][k] = (steps >= 0) ? steps : 9;
				}
			}
		}
	}
	
	public static void print()
	{
		for (int i = 0 ; i < 8 ; i++)
		{
			for (int j = 0 ; j < 6 ; j++)
			{
				for (int k = 0 ; k < 6 ; k++)
				{
					for (int l = 0 ; l < 6 ; l++)
					{
						System.out.print(distCoin[i][j][k][l]);
						System.out.print(' ');
					}
					System.out.println();
				}
				System.out.println();
			}
			System.out.println();
		}
		System.out.println();
		for (int i = 0 ; i < 12 ; i++)
		{
			for (int j = 0 ; j < 6 ; j++)
			{
				for (int k = 0 ; k < 6 ; k++)
				{
					System.out.print(distEdge[i][j][k]);
					System.out.print(' ');
				}
				System.out.println();
			}
			System.out.println();
		}
		System.out.println();
	}

}
