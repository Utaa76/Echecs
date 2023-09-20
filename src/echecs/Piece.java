package echecs;

import java.util.ArrayList;

public abstract class Piece
{
	public static final char BLANC = 'B';
	public static final char NOIR  = 'N';

	protected int                x;
	protected int                  y;
	protected char                 couleur;
	protected ArrayList<Mouvement> alMouvs;
	protected Jeu                  jeu;

	public Piece(int x, int y, char couleur, Jeu jeu)
	{
		this.x = x;
		this.y = y;

		if (couleur == Piece.BLANC || couleur == Piece.NOIR) this.couleur = couleur;
		else                                                 this.couleur = Piece.BLANC;

		this.alMouvs = new ArrayList<>();
		this.jeu     = jeu;
	}

	public abstract boolean peutDeplacer(int x, int y);

	public boolean deplacer(int x, int y)
	{
		if (!this.peutDeplacer(x, y)) return false;

		this.x = x;
		this.y = y;

		return true;
	}
	
	public abstract char getSymbole();

	public int  getX      () { return this.x;       }

	public int  getY      () { return this.y;       }

	public char getCouleur() { return this.couleur; }

	public String toString()
	{
		return this.couleur == Piece.BLANC ? ("" + this.getSymbole()).toLowerCase() : "" + this.getSymbole();
	}
}