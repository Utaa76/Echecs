package echecs;

import java.util.ArrayList;

import echecs.metier.Jeu;
import echecs.metier.Piece;
import echecs.metier.Roi;
import echecs.vue.FrameJeu;

public class Controleur
{
	public static final int TAILLE = 8;

	private Jeu        metier;
	private FrameJeu   vue;

	public Controleur()
	{
		this.metier = new Jeu();
		this.vue    = new FrameJeu(this);
	}

	public char getCouleurPiece(int x, int y)
	{
		Piece p = this.metier.getPiece(x,y);

		if (p == null) return ' ';

		return p.getCouleur();
	}

	public char getSymbolePiece(int x, int y)
	{
		Piece p = this.metier.getPiece(x,y);

		if (p == null) return ' ';

		return p.getSymbole();
	}

	public char getCouleurAJouer()
	{
		return this.metier.getCouleurAJouer();
	}

	public Piece getPiece(int x, int y)
	{
		return this.metier.getPiece(x, y);
	}

	public ArrayList<Piece> getAlPiece()
	{
		return this.metier.getAlPiece();
	}

	public ArrayList<Piece> getAlPieceMangees()
	{
		return this.metier.getAlPieceMangees();
	}

	public boolean deplacer(Piece p, int x, int y)
	{
		if (p != null && x >= 0 && x < Controleur.TAILLE && y >= 0 && y < Controleur.TAILLE)
			return this.metier.deplacer(p, x, y);

		return false;
	}

	public boolean echecEtMat()
	{
		return this.metier.isEchecEtMat();
	}

	public boolean isRoiEchec(char couleur)
	{
		return this.metier.roiEchec(couleur);
	}

	public Roi getRoi(char couleur)
	{
		return this.metier.getRoi(couleur);
	}

	public void retournerPlateau()
	{
		this.metier.retournerPlateau();
	}

	public String getImage(int i, int j)
	{
		Piece p = this.metier.getPiece(i, j);
		
		return this.getImage(p);
	}

	public String getImage(Piece p)
	{
		if (p == null) return "";

		return "./images/" + p.getClass().getSimpleName().toLowerCase() + "_" + p.getCouleur() + ".png";
	}

	public static void main(String[] args)
	{
		new Controleur();
	}
}
