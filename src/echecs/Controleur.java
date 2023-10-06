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

	public static void main(String[] args)
	{
		new Controleur();
	}
}
