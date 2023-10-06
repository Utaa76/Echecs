package echecs.vue;

import javax.swing.*;
import echecs.Controleur;
import echecs.metier.Piece;
import java.awt.event.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class PanelJeu extends JPanel
{
	private Controleur ctrl;
	private Piece      pieceSelect;

	public PanelJeu(Controleur ctrl)
	{
		this.ctrl        = ctrl;
		this.pieceSelect = null;

		this.addMouseListener(new GereSouris());

		this.repaint();
	}

	public void paintComponent(Graphics g)
	{
		// Dessiner la grille
		for (int i = 0 ; i < Controleur.TAILLE ; i++)
			for (int j = 0 ; j < Controleur.TAILLE ; j++)
			{
				if ((i+j)%2==0)
					g.drawRect(100 + i*100, 100 + j*100, 100, 100);
				else
					g.fillRect(100 + i*100, 100 + j*100, 100, 100);

				g.setFont(new Font("", Font.PLAIN, 40));

				g.setColor  (this.ctrl.getCouleurPiece(i, j) == 'B' ? Color.BLUE : Color.RED);
				g.drawString(("" + this.ctrl.getSymbolePiece(i, j)).toUpperCase(), 140 + 100*i, 160 + 100*j);

				g.setColor(Color.BLACK);
			}
	}

	public class GereSouris extends MouseAdapter
	{
		private Rectangle[][] ensHitbox;

		public GereSouris()
		{
			super();

			this.ensHitbox = new Rectangle[Controleur.TAILLE][Controleur.TAILLE];

			for (int i = 0 ; i < Controleur.TAILLE ; i++)
				for (int j = 0 ; j < Controleur.TAILLE ; j++)
					this.ensHitbox[i][j] = new Rectangle(100 + i*100, 100 + j*100, 100, 100);
				
		}

		public void mouseClicked(MouseEvent e)
		{
			int x = e.getX();
			int y = e.getY();

			for (int i = 0 ; i < Controleur.TAILLE ; i++)
				for (int j = 0 ; j < Controleur.TAILLE ; j++)
					if (this.ensHitbox[i][j].contains(x, y)) System.out.println("i:" + i + " j:" + j);
		}
	}
}
