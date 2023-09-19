package echecs;

public class Roi extends Piece
{
	public Roi(int x, int y, char couleur)
	{
		super(x, y, couleur);
	}

	public boolean peutDeplacer(int x, int y)
	{
		if (x <  0 && x > Jeu.TAILLE && y <  0 && y > Jeu.TAILLE && this.x == x && this.y == y) return false;

		for (Piece p : this.alPiece)
			if (p.getX() == x && p.getY() == y) return false;

		for (int i = -1 ; i < 3 ; i++)
			for (int j = -1 ; j < 3 ; j++)
				if (this.x+i == x && this.y+j == y) return true;

		//TODO: condition du cas en échec

		return false;
	}

	public char getSymbole() { return 'R'; }
}
