package echecs;

public class Pion extends Piece
{
	public Pion(int x, int y, char couleur)
	{
		super(x, y, couleur);
	}

	public boolean peutDeplacer(int x, int y)
	{
		if (x <  0 && x > Jeu.TAILLE && y <  0 && y > Jeu.TAILLE && this.x == x && this.y == y) return false;

		for (Piece p : this.alPiece)
			if (p.getX() == x && p.getY() == y) return false;

		if (this.couleur == Piece.BLANC) return this.y+1 == y || this.y+2 == y;
		if (this.couleur == Piece.NOIR ) return this.y+1 == y || this.y+2 == y;

		return false;
	}

	public char getSymbole() { return 'P'; }
}
