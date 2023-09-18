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

	public abstract boolean deplacer(int x, int y);
	
	public abstract char getSymbole();

	public int getX() { return x; }

	public int getY() { return y; }

	public String toString()
	{
		return "" + this.getSymbole();
	}
}