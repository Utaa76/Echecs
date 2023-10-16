package echecs;

import echecs.metier.*;
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

	public String getImage(int i, int j)
	{
		Piece p = this.metier.getPiece(i, j);
		
		if (p == null) return "";

		return "/src/images/" + p.getClass().getSimpleName().toLowerCase() + "_" + p.getCouleur() + ".png";
	}

	public static void main(String[] args)
	{
		new Controleur();
	}
}
