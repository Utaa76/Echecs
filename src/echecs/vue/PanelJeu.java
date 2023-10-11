package echecs.vue;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import echecs.Controleur;
import echecs.metier.Piece;
import echecs.metier.Roi;

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

	@Override
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
					g.fillRect(100 + i*100, 100 + j*100, 80, 80);
				}
				else
				{
					g.setColor(Color.BLACK);
					g.fillRect(100 + i*100, 100 + j*100, 100, 100);
				}

				// Entourer la pièce sélectionnée
				if (this.pieceSelect != null)
				{
					// g.setColor(Color.green);
					// g.drawOval(125+100*this.pieceSelect.getX(), 125+100*Math.abs(7-this.pieceSelect.getY()), 50, 50);

					// Dessiner les possibilités de déplacement de la pièce sélectionnée
					g.setColor(Color.lightGray);
					if (this.pieceSelect.getCouleur() == this.ctrl.getCouleurAJouer() && this.pieceSelect.peutDeplacer(i, Math.abs(7-j), false))
						g.fillOval(135+100*i, 135+100*j, 30, 30);
				}

				g.setColor(Color.BLACK);
			}

			g.drawString("" + (char)('A' + i), 137 + i*100, 950);
		}

		//FIXME: ça met échec qd une piece fait un déplacement qui met échec mm si le déplacement n'est pas possible
		if (this.ctrl.isRoiEchec(Piece.BLANC))
		{
			g.setColor(Color.YELLOW);
			Roi roi = this.ctrl.getRoi(Piece.NOIR);
			g.fillOval(125+100*roi.getX(), 125+100*roi.getY(), 50, 50);
		}

		if (this.ctrl.isRoiEchec(Piece.NOIR))
		{
			g.setColor(Color.YELLOW);
			Roi roi = this.ctrl.getRoi(Piece.BLANC);
			g.fillOval(125+100*roi.getX(), 125+100*roi.getY(), 50, 50);
		}

		g.drawRect(100, 100, 800, 800);

		for (int i = 0 ; i < Controleur.TAILLE ; i++)
			for (int j = 0 ; j < Controleur.TAILLE ; j++)
			{
				g.setFont(new Font("", Font.PLAIN, 40));

				Piece p = this.ctrl.getPiece(i, Math.abs(7-j));

				if (p != null)
				{
					g.setColor  (      p.getCouleur() == 'B' ? Color.BLUE : Color.RED       );
					g.drawString(("" + p.toString().toUpperCase()), 137 + 100*i, 162 + 100*j);
				}

				g.setColor(Color.BLACK);

				g.setFont(new Font("", Font.PLAIN, 30));
				if (j == 0) g.drawString("" + (Math.abs(7-i)+1), 50, 162 + i*100);
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

			// TODO: changer la piece si elle est de la mm couleur que celle déjà sélect
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

			System.out.println("roinoirechec : " + PanelJeu.this.ctrl.isRoiEchec(Piece.NOIR));
			System.out.println("echecetmat " + PanelJeu.this.ctrl.echecEtMat());
			PanelJeu.this.repaint();
		}
	}
}
