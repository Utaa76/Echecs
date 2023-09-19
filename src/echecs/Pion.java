package echecs;

public class Pion extends Piece
{
	public Pion(int x, int y, char couleur, Jeu jeu)
	{
		super(x, y, couleur, jeu);
	}

	public boolean peutDeplacer(int x, int y)
	{
		if (x <  0 && x > Jeu.TAILLE && y <  0 && y > Jeu.TAILLE && this.x == x && this.y == y) return false;

		Piece p = jeu.getPiece(x, y);
		if (p != null && p.getCouleur() == this.couleur) return false;

		if (this.couleur == Piece.BLANC) return this.x == x && this.y+1 == y || this.x == x && this.y+2 == y;
		if (this.couleur == Piece.NOIR ) return this.x == x && this.y+1 == y || this.y+2 == y;

		return false;
	}

	public char getSymbole() { return 'P'; }
}
