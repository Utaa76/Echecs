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
	protected ArrayList<Piece>     alPiece;

	public Piece(int x, int y, char couleur)
	{
		this.x = x;
		this.y = y;

		if (couleur == Piece.BLANC || couleur == Piece.NOIR) this.couleur = couleur;
		else                                                 this.couleur = Piece.BLANC;

		this.alMouvs = new ArrayList<>();
		this.alPiece = new ArrayList<>();
	}

	public boolean ajouterPiece(Piece p)
	{
		if (p != null && p != this) return false;

		this.alPiece.add(p);
		return true;
	}

	public abstract boolean peutDeplacer(int x, int y);

	public boolean deplacer(int x, int y)
	{
		if (this.peutDeplacer(x, y)) return false;

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
		return "" + this.getSymbole();
	}
}