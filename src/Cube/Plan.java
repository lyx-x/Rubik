package Cube;
import java.awt.*;
import javax.swing.*;

/*
 * Cette classe permet de dessiner le cube 2D
 */

public class Plan extends JPanel {
	
	int[][][] color = new int[6][3][3];
	int width = 40;
	int arcWidth = 4;  //rectangle arondi
	Color border = Color.BLACK;  //la couleur du bord du rectangle
	Color[] colors = {Color.WHITE, Color.GREEN, Color.RED, Color.BLUE, Color.ORANGE, Color.YELLOW, Color.BLACK};  //La noir sert au test pour représenter une couleur quelconque
	int[] X = new int[6];  //les coordonnées du début de chaque face
	int[] Y = new int[6];
	int winWidth = 560;   //les dimensions de la fenêtre en fonction de la taille de chaque case
	int winHeight = 440;
	
	public Plan(Cube c)
	{
		this.color = c.color;
		this.width = 40;
		this.arcWidth = 4;
		this.winWidth = 560; 
		this.winHeight = 440;
		X[0] = width * 4;
		Y[0] = width;
		X[1] = width;
		Y[1] = width * 4;
		X[2] = width * 4;
		Y[2] = width * 4;
		X[3] = width * 7;
		Y[3] = width * 4;
		X[4] = width * 10;
		Y[4] = width * 4;
		X[5] = width * 4;
		Y[5] = width * 7;
	}
	
	public void setWidth (int l){
		this.width = l;
		this.arcWidth = l / 10;
		this.winHeight = l * 11;
		this.winWidth = l * 14;
		X[0] = l * 4;
		Y[0] = l;
		X[1] = l;
		Y[1] = l * 4;
		X[2] = l * 4;
		Y[2] = l * 4;
		X[3] = l * 7;
		Y[3] = l * 4;
		X[4] = l * 10;
		Y[4] = l * 4;
		X[5] = l * 4;
		Y[5] = l * 7;
	}
	
	public Plan(Cube c, int l)
	{
		this.color = c.color;
		setWidth(l);
	}
	
	public Plan(Cube c, int l, Color b, Color[] cs){
		this.color = c.color;
		setWidth(l);
		this.border = b;
		for (int i = 0 ; i <= 6 ; i++)
		{
			this.colors[i] = cs[i];
		}
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		for (int face = 0 ; face < 6 ; face++)
		{
			paintFace(g, face);
		}
	}
	
	public void paintFace(Graphics g, int face)
	{
		for (int rang = 0 ; rang < 3 ; rang++){
			for (int colonne = 0 ; colonne < 3 ; colonne++)
			{
				paintCase(g, color[face][rang][colonne], rang, colonne, X[face], Y[face]);
			}
		}
	}
	
	/*
	 * Dessiner chaque case puis sa bordure
	 */
	
	public void paintCase(Graphics g, int color, int rang, int colonne, int x, int y){
		g.setColor(colors[color]);
		g.fillRoundRect(x + colonne  * width, y + rang * width, width, width, arcWidth, arcWidth);
		g.setColor(border);
		g.drawRoundRect(x + colonne  * width, y + rang * width, width, width, arcWidth, arcWidth);
	}

}
