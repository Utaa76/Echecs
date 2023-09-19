package echecs;

public class Tour extends Piece
{
	public Tour(int x, int y, char couleur, Jeu jeu)
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
			if (this.x + i == x && this.y == y && verifierChemin(x, y) ||
			    this.x == x && this.y + i == y && verifierChemin(x, y)) return true;
			
		}

		return false;
	}

	public char getSymbole() { return 'T'; }

	public boolean verifierChemin(int x, int y)
	{
		for (int i = 1 ; i != Math.abs((this.x - x) + (this.y - y)) ; i++)
		{
			System.out.println("test = " + (this.x - x) + (this.y - y));
			if (this.x - x == 0 && this.jeu.getPiece(this.x, this.y + i) != null) return false;
			System.out.println("test2");
			if (this.y - y == 0 && this.jeu.getPiece(this.x + 1, this.y) != null) return false;
			System.out.println("test3");
		}

		return true;
	}
}
