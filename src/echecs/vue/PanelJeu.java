package echecs.vue;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import echecs.Controleur;
import echecs.metier.Piece;
import echecs.metier.Roi;

public class PanelJeu extends JPanel
{
	private static final int TAILLE_CASE = 100;

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
					g.setColor(new Color(235, 235, 235));
					g.fillRect(100 + i*PanelJeu.TAILLE_CASE, 100 + j*PanelJeu.TAILLE_CASE, PanelJeu.TAILLE_CASE, PanelJeu.TAILLE_CASE);
					g.setColor(Color.WHITE);
					g.fillRect(100 + i*PanelJeu.TAILLE_CASE, 100 + j*PanelJeu.TAILLE_CASE, 80, 80);
				}
				else
				{
					g.setColor(new Color(110, 150, 100));
					g.fillRect(100 + i*PanelJeu.TAILLE_CASE, 100 + j*PanelJeu.TAILLE_CASE, PanelJeu.TAILLE_CASE, PanelJeu.TAILLE_CASE);
					// g.setColor(new Color(120, 170, 110));
					// g.fillRect(100 + i*PanelJeu.TAILLE_CASE, 100 + j*PanelJeu.TAILLE_CASE, 80, 80);
				}

				// Entourer la pièce sélectionnée
				if (this.pieceSelect != null)
				{
					// g.setColor(Color.green);
					// g.drawOval(125+100*this.pieceSelect.getX(), 125+100*Math.abs(7-this.pieceSelect.getY()), 50, 50);

					// Dessiner les possibilités de déplacement de la pièce sélectionnée
					g.setColor(Color.lightGray);
					if (this.pieceSelect.getCouleur() == this.ctrl.getCouleurAJouer() && this.pieceSelect.peutDeplacer(i, Math.abs(7-j), false))
						g.fillOval(135+PanelJeu.TAILLE_CASE*i, 135+PanelJeu.TAILLE_CASE*j, 30, 30);
				}

				g.setColor(Color.BLACK);
				g.setFont(new Font("", Font.PLAIN, 30));
				if (j == 0) g.drawString("" + (Math.abs(7-i)+1), 50, 162 + i*PanelJeu.TAILLE_CASE);
			}

			g.drawString("" + (char)('A' + i), 137 + i*PanelJeu.TAILLE_CASE, 950);
		}

		//FIXME: ça met échec qd une piece fait un déplacement qui met échec mm si le déplacement n'est pas possible
		Roi roi = this.ctrl.getRoi(Piece.BLANC);
		if (roi.isEchec())
		{
			g.setColor(Color.RED);
			g.fillOval(111+PanelJeu.TAILLE_CASE*roi.getX(), 118+PanelJeu.TAILLE_CASE*Math.abs(7-roi.getY()), 75, 75);
		}

		roi = this.ctrl.getRoi(Piece.NOIR);
		if (roi.isEchec())
		{
			g.setColor(Color.RED);
			g.fillOval(111+PanelJeu.TAILLE_CASE*roi.getX(), 118+PanelJeu.TAILLE_CASE*Math.abs(7-roi.getY()), 75, 75);
		}

		// Dessiner les pièces
		for (int i = 0 ; i < Controleur.TAILLE ; i++)
			for (int j = 0 ; j < Controleur.TAILLE ; j++)
			{
				ImageIcon imgPiece = new ImageIcon(new ImageIcon(this.ctrl.getImage(i, j)).getImage().getScaledInstance(PanelJeu.TAILLE_CASE, PanelJeu.TAILLE_CASE, Image.SCALE_DEFAULT));
				// Piece p = this.ctrl.getPiece(i, Math.abs(7-j));

				// if (p != null)
				// {
				// 	g.setColor  (      p.getCouleur() == 'B' ? Color.BLUE : Color.RED       );
				// 	g.drawString(("" + p.toString().toUpperCase()), 137 + PanelJeu.TAILLE_CASE*i, 162 + PanelJeu.TAILLE_CASE*j);
				// }

				// g.setColor(Color.BLACK);
				imgPiece.paintIcon(this, g, 100 + PanelJeu.TAILLE_CASE*i, 100 + PanelJeu.TAILLE_CASE*Math.abs(7-j));
			}

		g.setColor(Color.BLACK);
		g.drawRect(100, 100, 800, 800);

		// ImageIcon image = new ImageIcon(this.ctrl.getImage(0, 0));
		// Image imgScaled = image.getImage().getScaledInstance(PanelJeu.TAILLE_CASE, PanelJeu.TAILLE_CASE, Image.SCALE_DEFAULT);
		// ImageIcon imgIcon = new ImageIcon(imgScaled);
		// imgIcon.paintIcon(this, g, PanelJeu.TAILLE_CASE, PanelJeu.TAILLE_CASE);
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
					this.ensHitbox[i][j] = new Rectangle(100 + i*PanelJeu.TAILLE_CASE, 100 + Math.abs(7-j)*PanelJeu.TAILLE_CASE, PanelJeu.TAILLE_CASE, PanelJeu.TAILLE_CASE);
				
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

						Piece p = PanelJeu.this.ctrl.getPiece(i, j);

						System.out.println("pieceSelect = " + PanelJeu.this.pieceSelect);
						System.out.println("p = " + p);
						if      (PanelJeu.this.pieceSelect == null && p != null && p.getCouleur() == PanelJeu.this.ctrl.getCouleurAJouer()) PanelJeu.this.pieceSelect = p;
						else if (PanelJeu.this.pieceSelect != null)
						{
							System.out.println("lalala");
							System.out.println("deplacer : " + PanelJeu.this.ctrl.deplacer(PanelJeu.this.pieceSelect, i, j));

							p = PanelJeu.this.ctrl.getPiece(i, j);

							// if (p == null || p.getCouleur() == PanelJeu.this.pieceSelect.getCouleur()) PanelJeu.this.pieceSelect = p;
							// else                                                                       PanelJeu.this.pieceSelect = null;

							PanelJeu.this.pieceSelect = p == null || p.getCouleur() == PanelJeu.this.ctrl.getCouleurAJouer() ? p : null;
						}
					}

			System.out.println("roinoirechec : " + PanelJeu.this.ctrl.isRoiEchec(Piece.NOIR));
			System.out.println("echecetmat " + PanelJeu.this.ctrl.echecEtMat());
			PanelJeu.this.repaint();
		}
	}
}
