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
	 * Modifier les couleurs d'une colonne (rang = false) ou d'un rang (rang = true), avec le numéro de la colonne ou de rang
	 * On retourne les anciennes valeurs pour le prochain étape (toujours de 0 à 2)
	 * Le booléan sens vérifie si on doit changer l'ordre d'affectation
	 */
	
	public int[] set(int face, int no, boolean rang, boolean sens, int[] source){
		int[] ancien = new int[3];
		for (int i = 0 ; i < 3 ; i++)
		{
			int From = sens ? 2 - i : i;
			if (rang = true)
			{
				ancien[0] = color[face][no][i];
				color[face][no][i] = source[From];
			}
			else
			{
				ancien[0] = color[face][no][i];
				color[face][i][no] = source[From];
			}
		}
		return ancien;
	}
	
	/*
	 * Modifier seulement les couleurs de la face quand on tourne
	 */
	
	public void tournerFace(int face){
		int tmp = color[face][0][0];
		color[face][0][0] = color[face][0][1];
		color[face][0][1] = color[face][0][2];
		color[face][0][2] = color[face][1][2];
		color[face][1][2] = color[face][2][2];
		color[face][2][2] = color[face][2][1];
		color[face][2][1] = color[face][2][0];
		color[face][2][0] = color[face][1][0];
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
	
	public void tourner(int face){
		tournerFace(face);
		switch (face){
		case 1:
			
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		case 5:
			break;
		case 6:
			break;
		}
	}

}
