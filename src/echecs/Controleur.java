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

	public static void main(String[] args)
	{
		new Controleur();
	}
}
