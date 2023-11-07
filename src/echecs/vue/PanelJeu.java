package echecs.vue;

import java.awt.Color;
import java.awt.Cursor;
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
	private boolean    plateauRetourne;

	public PanelJeu(Controleur ctrl)
	{
		this.ctrl            = ctrl;
		this.pieceSelect     = null;
		this.plateauRetourne = false;

		GereSouris gs = new GereSouris();
		this.addMouseListener      (gs);
		this.addMouseMotionListener(gs);

		this.repaint();
	}

	@Override
	public void paintComponent(Graphics g)
	{
		// Dessiner un fond
		g.setColor(new Color(220, 255, 211));
		g.fillRect(0, 0, 2000, 2000);
		// Dessiner la grille
		for (int i = 0 ; i < Controleur.TAILLE ; i++)
		{
			for (int j = 0 ; j < Controleur.TAILLE ; j++)
			{
				if ((i+j)%2==0)
				{
					g.setColor(new Color(235, 235, 235));
					g.fillRect(100 + i*PanelJeu.TAILLE_CASE, 50 + j*PanelJeu.TAILLE_CASE, PanelJeu.TAILLE_CASE, PanelJeu.TAILLE_CASE);
					g.setColor(Color.WHITE);
					g.fillRect(100 + i*PanelJeu.TAILLE_CASE, 50 + j*PanelJeu.TAILLE_CASE, 80, 80);
				}
				else
				{
					g.setColor(new Color(110, 150, 100));
					g.fillRect(100 + i*PanelJeu.TAILLE_CASE, 50 + j*PanelJeu.TAILLE_CASE, PanelJeu.TAILLE_CASE, PanelJeu.TAILLE_CASE);
					// g.setColor(new Color(120, 170, 110));
					// g.fillRect(100 + i*PanelJeu.TAILLE_CASE, 100 + j*PanelJeu.TAILLE_CASE, 80, 80);
				}

				g.setColor(Color.BLACK);
				g.setFont(new Font("", Font.PLAIN, 30));
				if (j == 0) g.drawString("" + (Math.abs(7-i)+1), 50, 112 + i*PanelJeu.TAILLE_CASE);
			}

			g.drawString("" + (char)('A' + i), 137 + i*PanelJeu.TAILLE_CASE, 900);
		}

		Roi roi = this.ctrl.getRoi(Piece.BLANC);
		if (roi.isEchec())
		{
			g.setColor(Color.RED);
			g.fillOval(111+PanelJeu.TAILLE_CASE*roi.getX(), 68+PanelJeu.TAILLE_CASE*Math.abs(7-roi.getY()), 75, 75);
		}

		roi = this.ctrl.getRoi(Piece.NOIR);
		if (roi.isEchec())
		{
			g.setColor(Color.RED);
			g.fillOval(111+PanelJeu.TAILLE_CASE*roi.getX(), 68+PanelJeu.TAILLE_CASE*Math.abs(7-roi.getY()), 75, 75);
		}

		// Dessiner les pièces
		for (Piece p : this.ctrl.getAlPiece())
		{
			// System.out.println(p.toStringEvolved());
			ImageIcon imgPiece = new ImageIcon(new ImageIcon(this.ctrl.getImage(p)).getImage().getScaledInstance(PanelJeu.TAILLE_CASE, PanelJeu.TAILLE_CASE, Image.SCALE_DEFAULT));
			imgPiece.paintIcon(this, g, 100 + PanelJeu.TAILLE_CASE*p.getX(), 50 + PanelJeu.TAILLE_CASE*Math.abs(7-p.getY()));
		}

		for (int i = 0 ; i < Controleur.TAILLE ; i++)
		{
			for (int j = 0 ; j < Controleur.TAILLE ; j++)
			{
				// Afficher les possibilités de déplacement
				if (this.pieceSelect != null && this.pieceSelect.getCouleur() == this.ctrl.getCouleurAJouer() && this.pieceSelect.peutDeplacer(i, Math.abs(7-j), false))
				{
					g.setColor(Color.lightGray);
					g.fillOval(135+PanelJeu.TAILLE_CASE*i, 85+PanelJeu.TAILLE_CASE*j, 30, 30);
				}
			}
		}

		// for (int i = 0; i < Controleur.TAILLE ; i++)
		// {
		// 	for (int j = 0; j < Controleur.TAILLE ; j++)
		// 	{
		// 		Piece p = this.ctrl.getPiece(i, j);
		// 		if (p != null)
		// 		{
		// 			ImageIcon imgPiece = new ImageIcon(new ImageIcon(this.ctrl.getImage(p)).getImage().getScaledInstance(PanelJeu.TAILLE_CASE, PanelJeu.TAILLE_CASE, Image.SCALE_DEFAULT));
		// 			imgPiece.paintIcon(this, g, 100 + PanelJeu.TAILLE_CASE*p.getX(), 50 + PanelJeu.TAILLE_CASE*Math.abs(7-p.getY()));
		// 		}
		// 	}
		// }

		// Contour du plateau
		g.setColor(Color.BLACK);
		g.drawRect(100, 50, 800, 800);

		// Bouton "Retourner le plateau"
		g.setColor(Color.lightGray);
		g.fillRect(975, 425, 150, 50);
		g.setFont(new Font("", Font.PLAIN, 15));
		g.setColor(Color.BLACK);
		g.drawString("Retourner le plateau", 985, 455);

		// Pièces mangées
		int nbPiecesNoires = 0, nbPiecesBlanches = 0;
		for (Piece p : this.ctrl.getAlPieceMangees())
		{
			if (p != null)
			{
				System.out.println("plateauRetourne " + this.plateauRetourne);
				if (this.plateauRetourne)
				{
					if (p.getCouleur() == Piece.NOIR)
					{
						ImageIcon imgPiece = new ImageIcon(new ImageIcon(this.ctrl.getImage(p)).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
						imgPiece.paintIcon(this, g, 910 + 40 * (int)(nbPiecesNoires/5), 50 + 40 * (nbPiecesNoires%5));
						nbPiecesNoires++;
					}
					else
					{
						ImageIcon imgPiece = new ImageIcon(new ImageIcon(this.ctrl.getImage(p)).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
						imgPiece.paintIcon(this, g, 910 + 40 * (int)(nbPiecesBlanches/5), 825 - 40 * (nbPiecesBlanches%5));
						nbPiecesBlanches++;
					}
				}
				else
				{
					if (p.getCouleur() == Piece.NOIR)
					{
						ImageIcon imgPiece = new ImageIcon(new ImageIcon(this.ctrl.getImage(p)).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
						imgPiece.paintIcon(this, g, 910 + 40 * (int)(nbPiecesNoires/5), 825 - 40 * (nbPiecesNoires%5));
						nbPiecesNoires++;
					}
					else
					{
						ImageIcon imgPiece = new ImageIcon(new ImageIcon(this.ctrl.getImage(p)).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
						imgPiece.paintIcon(this, g, 910 + 40 * (int)(nbPiecesBlanches/5), 50 + 40 * (nbPiecesBlanches%5));
						nbPiecesBlanches++;
					}
				}
				
			}
		}
	}

	public class GereSouris extends MouseAdapter
	{
		private Rectangle[][] ensHitbox;
		private Rectangle     hbBtnRetourne;

		public GereSouris()
		{
			super();

			this.ensHitbox = new Rectangle[Controleur.TAILLE][Controleur.TAILLE];

			for (int i = 0 ; i < Controleur.TAILLE ; i++)
				for (int j = 0 ; j < Controleur.TAILLE ; j++)
					this.ensHitbox[i][j] = new Rectangle(100 + i*PanelJeu.TAILLE_CASE, 50 + Math.abs(7-j)*PanelJeu.TAILLE_CASE, PanelJeu.TAILLE_CASE, PanelJeu.TAILLE_CASE);

			this.hbBtnRetourne = new Rectangle(975, 425, 150, 50);
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
							System.out.println("deplacer : " + PanelJeu.this.ctrl.deplacer(PanelJeu.this.pieceSelect, i, j) + " couleur " + PanelJeu.this.ctrl.getCouleurAJouer());

							p = PanelJeu.this.ctrl.getPiece(i, j);

							// if (p == null || p.getCouleur() == PanelJeu.this.pieceSelect.getCouleur()) PanelJeu.this.pieceSelect = p;
							// else                                                                       PanelJeu.this.pieceSelect = null;

							PanelJeu.this.pieceSelect = p == null || p.getCouleur() == PanelJeu.this.ctrl.getCouleurAJouer() ? p : null;
						}
					}

			if (this.hbBtnRetourne.contains(x,y))
			{
				PanelJeu.this.plateauRetourne = !PanelJeu.this.plateauRetourne;
				PanelJeu.this.ctrl.retournerPlateau();
			}

			PanelJeu.this.repaint();
		}

		// public void mouseMoved(MouseEvent e)
		// {
		// 	int x = e.getX();
		// 	int y = e.getY();
		// 	System.out.printf("x:%d y:%d\n", x, y);

		// 	// TODO: changer la piece si elle est de la mm couleur que celle déjà sélect
		// 	for (int i = 0 ; i < Controleur.TAILLE ; i++)
		// 		for (int j = 0 ; j < Controleur.TAILLE ; j++)
		// 			if (this.ensHitbox[i][j].contains(x, y)) PanelJeu.this.setCursor(new Cursor(Cursor.HAND_CURSOR   ));
		// 			else                                     PanelJeu.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		// }
	}
}
