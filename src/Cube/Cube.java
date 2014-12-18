package Cube;
import java.io.*;
import javax.swing.*;
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
	
	/*
	 * Listes des faces adjacentes à une face donnée dans le sens trigonométrique
	 * {4,1,1} pour 0 représente face 4, rang 1
	 * {0,1,0} pour 1 représente face 0, colonne 1
	 */
	
	int[][][] adjacent = {
			{{4}, {1}, {2}, {3}},
			{{4}, {5}, {2}, {0}},
			{{5}, {3}, {0}, {1}},
			{{0}, {2}, {5}, {4}},
			{{3}, {5}, {1}, {0}},
			{{2}, {1}, {0}, {3}}
	};
	
	/*
	 * Lire les couleurs par la console
	 */

	public Cube(){
		Scanner read = new Scanner(System.in);
		this.color = new int[6][3][3];
		int tmp = 0;
		for (int face = 0 ; face < 6 ; face++)
		{
			System.out.format("Face %d : ", face + 1);
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
			System.out.format("Face %d : \n", face + 1);
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
	 * Dessiner le cube 2D dans une nouvelle fenêtre
	 */
	
	public void show2D(){
		int width = 60;
		Plan dessin = new Plan(this, width);
		JFrame frame=new JFrame("Rubik's cube");
		frame.setSize(width * 14 + 20, width * 11 + 40);
		frame.setVisible(true);
		frame.add(dessin);
	}
	
	/*
	 * 
	 */
	
	public void set(int face, int no, int sens, int[] color){
		
	}
	
	/*
	 * Tourner une face de 90 degree dans le sens trigonométrique
	 */
	
	public void tourner(int face){
		int a[][] = new int[3][3];
		for (int rang = 0 ; rang < 3 ; rang++)
		{
			for (int colonne = 0 ; colonne < 3 ; colonne++)
			{
				a[rang][colonne] = color[face][colonne][rang];
			}
		}
		//TODO
		
	}

}
