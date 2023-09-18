package echecs;

public class Cavalier extends Piece
{
	public Cavalier(int x, int y, char couleur)
	{
		super(x, y, couleur);
	}

	public boolean deplacer(int x, int y)
	{
		if (x <  0 && x > Jeu.TAILLE && y <  0 && y > Jeu.TAILLE && this.x == x && this.y == y) return false;

		if (this.x + 1 == x && this.y + 2 == y &&
		    this.x + 1 == x && this.y - 2 == y &&
		    this.x - 1 == x && this.y + 2 == y &&
		    this.x - 1 == x && this.y - 2 == y &&
		    this.x + 2 == x && this.y + 1 == y &&
		    this.x + 2 == x && this.y - 1 == y &&
		    this.x - 2 == x && this.y + 1 == y &&
		    this.x - 2 == x && this.y - 1 == y    ) return true;

		return false;
	}

	public char getSymbole() { return 'P'; }
}
