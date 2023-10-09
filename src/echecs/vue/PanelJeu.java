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
		{
			for (int j = 0 ; j < Controleur.TAILLE ; j++)
			{
				if ((i+j)%2==0)
				{
					g.setColor(Color.WHITE);
					g.fillRect(100 + i*100, 100 + j*100, 100, 100);
				}
				else
				{
					g.setColor(Color.BLACK);
					g.fillRect(100 + i*100, 100 + j*100, 100, 100);
				}

				g.setFont(new Font("", Font.PLAIN, 40));

				Piece p = this.ctrl.getPiece(i, Math.abs(7-j));

				if (p != null)
				{
					g.setColor  (      p.getCouleur() == 'B' ? Color.BLUE : Color.RED);
					g.drawString(("" + p.toString().toUpperCase()), 137 + 100*i, 162 + 100*j);
				}

				g.setColor(Color.BLACK);

				g.setFont(new Font("", Font.PLAIN, 30));
				if (j == 0) g.drawString("" + (Math.abs(7-i)+1), 50, 162 + i*100);
			}

			g.drawString("" + (char)('A' + i), 137 + i*100, 950);
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
					this.ensHitbox[i][j] = new Rectangle(100 + i*100, 100 + Math.abs(7-j)*100, 100, 100);
				
		}

		public void mouseClicked(MouseEvent e)
		{
			int x = e.getX();
			int y = e.getY();

			for (int i = 0 ; i < Controleur.TAILLE ; i++)
				for (int j = 0 ; j < Controleur.TAILLE ; j++)
					if (this.ensHitbox[i][j].contains(x, y))
					{
						System.out.println("i:" + i + " j:" + j);
						Piece p = PanelJeu.this.ctrl.getPiece(i, j);;
						if      (PanelJeu.this.pieceSelect == null && p != null) PanelJeu.this.pieceSelect = p;
						else if (PanelJeu.this.pieceSelect != null)
						{
							PanelJeu.this.ctrl.deplacer(PanelJeu.this.pieceSelect, i, j);
							PanelJeu.this.pieceSelect = null;
						}
					}

			PanelJeu.this.repaint();
		}
	}
}
