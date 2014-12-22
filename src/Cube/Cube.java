package Cube;
import java.io.*;

import javax.swing.*;

import Config.Path;

import java.util.*;

public class Cube {
	
	/*
	 * Disposition du cube : les chiffres représentent le numéro de la face ici ! (et pas la coleur pour l'instant)
	 * 
	 *        0 0 0
	 *        0 0 0
	 *        0 0 0
	 *  1 1 1 2 2 2 3 3 3 4 4 4
	 *  1 1 1 2 2 2 3 3 3 4 4 4
	 *  1 1 1 2 2 2 3 3 3 4 4 4
	 *        5 5 5
	 *        5 5 5
	 *        5 5 5
	 * 
	 *        up
	 *  left  front  right  back   
	 *        down       
	 * 
	 */

	int[][][] color = new int[6][3][3];
	static int width = 60;  //Modifier la taille une fois pour toute en utilisant une variable statique
	
	public static Cube src = new Cube("Test.txt");  //Ce cube est l'état final
	public static Cube black = new Cube("Black.txt");  //Ce cube est tout noir pour le test
	
	/*
	 * Reproduire le même cube
	 */
	
	public Cube(Cube c)
	{
		this.color = new int[6][3][3];
		for (int face = 0 ; face < 6 ; face++)
		{
			for (int rang = 0 ; rang < 3 ; rang++)
			{
				for (int colonne = 0 ; colonne < 3 ; colonne++)
				{
					this.color[face][rang][colonne] = c.color[face][rang][colonne];
				}
			}
		}
	}
	
	/*
	 * Lire les couleurs par la console
	 */

	public Cube(){
		Scanner read = new Scanner(System.in);
		this.color = new int[6][3][3];
		int tmp = 0;
		for (int face = 0 ; face < 6 ; face++)
		{
			System.out.format("Face %d : ", face);
			for (int rang = 0 ; rang < 3 ; rang++)
			{
				for (int colonne = 0 ; colonne < 3 ; colonne++)
				{
					tmp = read.nextInt();
					color[face][rang][colonne] = tmp;
				}
			}
		}
		read.close();
		System.out.println();
	}

	/*
	 * Lire le cube à partir d'un fichier
	 */
	
	public Cube(String path)
	{
		try{
			FileReader file = new FileReader(path);
			BufferedReader read = new BufferedReader(file);
			try
			{
				while (read.ready()) 
				{
					for (int face = 0 ; face < 6 ; face++)
					{
						for (int rang = 0 ; rang < 3 ; rang++)
						{
							String line = read.readLine();
							String[] col = line.split(" ");
							for (int colonne = 0 ; colonne < 3 ; colonne++)
							{
								color[face][rang][colonne] = Integer.parseInt(col[colonne]);
							}
						}
					}
				}
			}
			catch (Exception e)
			{
				System.out.println(e.getMessage());
			}
			read.close();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	/*
	 * Imprimer le cube dans le console face après face
	 */

	public void print(){
		if (this.color == null)
		{
			return;
		}
		for (int face = 0 ; face < 6 ; face++)
		{
			System.out.format("Face %d : \n", face);
			for (int rang = 0 ; rang < 3 ; rang++)
			{
				for (int colonne = 0 ; colonne < 3 ; colonne++)
				{
					System.out.print(color[face][rang][colonne]);
					System.out.print(' ');
				}
				System.out.println();
			}
			System.out.println();
		}
	}
	
	/*
	 * Modifier la taille d'affichage
	 */
	
	public static void setWidth(int w)
	{
		width = w;
	}
	
	/*
	 * Dessiner le cube 2D dans une nouvelle fenêtre
	 */
	
	public void show2D(){
		Plan dessin = new Plan(this, width);
		JFrame frame=new JFrame("Rubik's cube");
		frame.setSize(width * 14 + 20, width * 11 + 40);
		frame.setVisible(true);
		frame.add(dessin);
	}
	
	/*
	 * Dessiner le cube avec un titre
	 */
	
	public void show2D(String str){
		Plan dessin = new Plan(this, width);
		JFrame frame=new JFrame(str);
		frame.setSize(width * 14 + 20, width * 11 + 40);
		frame.setVisible(true);
		frame.add(dessin);
	}
	
	/*
	 * Modifier les couleurs d'une colonne (rang = false) ou d'un rang (rang = true), avec le numéro de la colonne ou de rang
	 * On retourne les anciennes valeurs pour le prochain étape (toujours de 0 à 2)
	 * Le booléan sens vérifie si on doit changer l'ordre d'affectation
	 */
	
	public int[] set(int face, int no, boolean rang, boolean changeSens, int[] source){
		int[] ancien = new int[3];
		for (int i = 0 ; i < 3 ; i++)
		{
			int From = changeSens ? 2 - i : i;
			if (rang)
			{
				ancien[i] = color[face][no][i];
				color[face][no][i] = source[From];
			}
			else
			{
				ancien[i] = color[face][i][no];
				color[face][i][no] = source[From];
			}
		}
		return ancien;
	}
	
	/*
	 * Modifier la couleur d'une seule case
	 */
	
	void setColor(int color, int[] coord)
	{
		this.color[coord[0]][coord[1]][coord[2]] = color;
	}
	
	/*
	 * Reset la bonne couleur qui se trouve dans le numéro de la face
	 */
	
	void setColor(int[] coord)
	{
		this.color[coord[0]][coord[1]][coord[2]] = coord[0];
	}
		
	/*
	 * Modifier seulement les couleurs de la face quand on tourne
	 */
	
	void tournerFace(int face){
		int tmp = color[face][0][0];
		color[face][0][0] = color[face][0][2];
		color[face][0][2] = color[face][2][2];
		color[face][2][2] = color[face][2][0];
		color[face][2][0] = tmp;
		tmp = color[face][0][1];
		color[face][0][1] = color[face][1][2];
		color[face][1][2] = color[face][2][1];
		color[face][2][1] = color[face][1][0];
		color[face][1][0] = tmp;
	}
	
	/*
	 * Tourner une face de 90 degree dans le sens trigonométrique
	 * Rappel :
	 *        0 0 0
	 *        0 0 0
	 *        0 0 0
	 *  1 1 1 2 2 2 3 3 3 4 4 4
	 *  1 1 1 2 2 2 3 3 3 4 4 4
	 *  1 1 1 2 2 2 3 3 3 4 4 4
	 *        5 5 5
	 *        5 5 5
	 *        5 5 5
	 */
	
	void tourner(int face){
		tournerFace(face);
		switch (face){
		case 1:
			int[] tmp1 = {color[0][0][0], color[0][1][0], color[0][2][0]};
			tmp1 = set(4, 2, false, true, tmp1);
			tmp1 = set(5, 0, false, true, tmp1);
			tmp1 = set(2, 0, false, false, tmp1);
			tmp1 = set(0, 0, false, false, tmp1);
			break;
		case 2:
			int[] tmp2 = {color[0][2][0], color[0][2][1], color[0][2][2]};
			tmp2 = set(1, 2, false, true, tmp2);
			tmp2 = set(5, 0, true, false, tmp2);
			tmp2 = set(3, 0, false, true, tmp2);
			tmp2 = set(0, 2, true, false, tmp2);
			break;
		case 3:
			int[] tmp3 = {color[2][0][2], color[2][1][2], color[2][2][2]};
			tmp3 = set(5, 2, false, false, tmp3);
			tmp3 = set(4, 0, false, true, tmp3);
			tmp3 = set(0, 2, false, true, tmp3);
			tmp3 = set(2, 2, false, false, tmp3);
			break;
		case 4:
			int[] tmp4 = {color[3][0][2], color[3][1][2], color[3][2][2]};
			tmp4 = set(5, 2, true, true, tmp4);
			tmp4 = set(1, 0, false, false, tmp4);
			tmp4 = set(0, 0, true, true, tmp4);
			tmp4 = set(3, 2, false, false, tmp4);
			break;
		case 5:
			int[] tmp5 = {color[2][2][0], color[2][2][1], color[2][2][2]};
			tmp5 = set(1, 2, true, false, tmp5);
			tmp5 = set(4, 2, true, false, tmp5);
			tmp5 = set(3, 2, true, false, tmp5);
			tmp5 = set(2, 2, true, false, tmp5);
			break;
		case 0:
			int[] tmp0 = {color[2][0][0], color[2][0][1], color[2][0][2]};
			tmp0 = set(3, 0, true, false, tmp0);
			tmp0 = set(4, 0, true, false, tmp0);
			tmp0 = set(1, 0, true, false, tmp0);
			tmp0 = set(2, 0, true, false, tmp0);
			break;
		}
	}
	
	/*
	 * Voici la méthode publique qu'on peut utiliser pour tourner le cube, attention tour = 0 veut dire une tour
	 */
	
	public void tourner(int face, int tour){
		for (int i = -1 ; i < tour ; i++)
		{
			tourner(face);
		}
	}
	
	/*
	 * Tester l'équivalence des deux cubes
	 */
	
	public boolean same(Cube c){
		for (int face = 0 ; face < 6 ; face++)
		{
			for (int rang = 0 ; rang < 3 ; rang++)
			{
				for (int colonne = 0 ; colonne < 3 ; colonne++)
				{
					if (color[face][rang][colonne] != c.color[face][rang][colonne])
					{
						return false;
					}
				}
			}
		}
		return true;
	}
	
	/*
	 * Vérifier si la face est bonne, on compare avec le centre qui ne bouge pas
	 */
	
	public boolean faceHomogene(int face){
		int base = color[face][1][1];
		for (int rang = 0 ; rang < 3 ; rang++)
		{
			for (int colonne = 0 ; colonne < 3 ; colonne++)
			{
				if (color[face][rang][colonne] != base)
				{
					return false;
				}
			}
		}
		return true;
	}
	
	/*
	 * Deux méthode qui ne servent qu'au test
	 */
	
	public boolean correctCoins(int face){
		int center = color[face][1][1];
		if (color[face][0][0] != center) return false;
		if (color[face][2][0] != center) return false;
		if (color[face][0][2] != center) return false;
		if (color[face][2][2] != center) return false;
		return true;
	}
	
	public boolean correctEdges(int face){
		int center = color[face][1][1];
		if (color[face][0][1] != center) return false;
		if (color[face][1][0] != center) return false;
		if (color[face][1][2] != center) return false;
		if (color[face][2][1] != center) return false;
		return true;
	}
	
	public int distanceSimple()
	{
		Cube test = new Cube(this);
		int ans = 0;
		int somme = 0;
		int tmp = 0;
		
		for (int edge = 0 ; edge < 12; edge++)
		{
			Edge e = new Edge(edge, test);
			if (!e.correctPosition())
			{
				//e.print();
				tmp = e.recoverSteps();
				if (ans < tmp) ans = tmp;
				somme += tmp;
			}
		}
		
		for (int coin = 0 ; coin < 8 ; coin++)
		{
			Coin c = new Coin(coin, test);
			if (!c.correctPosition())
			{
				//c.print();
				tmp = c.recoverSteps();
				if (ans < tmp) ans = tmp;
				somme += tmp;
			}
		}
		
		return somme;
	}
	
	
	public int distance()
	{
		int ans = 0;
		int somme = 0;
		int tmp = 0;
		
		for (int edge = 0 ; edge < 12; edge++)
		{
			int[][] eCoord = Edge.realPosition[edge];
			int f = color[eCoord[0][0]][eCoord[0][1]][eCoord[0][2]];
			int s = color[eCoord[1][0]][eCoord[1][1]][eCoord[1][2]];
			tmp = Path.distEdge[edge][f][s];
			if (ans < tmp) ans = tmp;
			somme += tmp;
		}
		
		for (int coin = 0 ; coin < 8 ; coin++)
		{
			int[][] cCoord = Coin.realPosition[coin];
			int f = color[cCoord[0][0]][cCoord[0][1]][cCoord[0][2]];
			int s = color[cCoord[1][0]][cCoord[1][1]][cCoord[1][2]];
			int t = color[cCoord[2][0]][cCoord[2][1]][cCoord[2][2]];
			tmp = Path.distCoin[coin][f][s][t];
			if (ans < tmp) ans = tmp;
			somme += tmp;
		}
		return ans;
	}
	
	public void printDistance()
	{
		for (int edge = 0 ; edge < 12; edge++)
		{
			int[][] eCoord = Edge.realPosition[edge];
			int f = color[eCoord[0][0]][eCoord[0][1]][eCoord[0][2]];
			int s = color[eCoord[1][0]][eCoord[1][1]][eCoord[1][2]];
			System.out.format("%d %d %d %d\n",edge, f, s, Path.distEdge[edge][f][s]);
		}
		System.out.println();
		for (int coin = 0 ; coin < 8 ; coin++)
		{
			int[][] cCoord = Coin.realPosition[coin];
			int f = color[cCoord[0][0]][cCoord[0][1]][cCoord[0][2]];
			int s = color[cCoord[1][0]][cCoord[1][1]][cCoord[1][2]];
			int t = color[cCoord[2][0]][cCoord[2][1]][cCoord[2][2]];
			System.out.format("%d %d %d %d %d\n",coin, f, s, t, Path.distCoin[coin][f][s][t]);
		}
	}

}

