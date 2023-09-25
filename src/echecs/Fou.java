package echecs;

public class Fou extends Piece
{
	public Fou(int x, int y, char couleur, Jeu jeu)
	{
		super(x, y, couleur, jeu);
	}

	public boolean peutDeplacer(int x, int y)
	{
		if (x <  0 && x > Jeu.TAILLE && y <  0 && y > Jeu.TAILLE && this.x == x && this.y == y) return false;

		Piece p = jeu.getPiece(x, y);
		if (p != null && p.getCouleur() == this.couleur) return false;

		for (int i = 1 ; i < Jeu.TAILLE ; i++)
		{
			if (this.x + i == x && this.y + i == y && verifierChemin(x, y) ||
			    this.x - i == x && this.y + i == y && verifierChemin(x, y) ||
				this.x + i == x && this.y - i == y && verifierChemin(x, y) ||
				this.x - i == x && this.y - i == y && verifierChemin(x, y)   ) return true;
			
		}

		return false;
	}

	public char getSymbole() { return 'F'; }

	public boolean verifierChemin(int x, int y)
	{
		int ratioX = x - this.x;
		int ratioY = y - this.y;

		for (int i = ratioX > 0 ? 1 : -1, j = ratioY > 0 ? 1 : -1 ; i != ratioX || j != ratioY; i = ratioX > 0 ? i + 1 : i - 1, j = ratioY > 0 ? j + 1 : j - 1)
			if (this.jeu.getPiece(this.x + i, this.y + j) != null) return false;

		return true;
	}
}
