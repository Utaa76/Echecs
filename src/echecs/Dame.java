package echecs;

public class Dame extends Piece
{
	public Dame(int x, int y, char couleur, Jeu jeu)
	{
		super(x, y, couleur, jeu);
	}

	//TODO: à changer (Copy/Paste)
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

	public char getSymbole() { return 'D'; }

	//TODO: à changer (Copy/Paste)
	public boolean verifierChemin(int x, int y)
	{
		// for (int i = 0, j = 0 ; i == this.x - x && j == this.y - y ; i++, j++)
		// 	for (Piece p : this.alPiece)
		// 		if (p.getX() == this.x + i && p.getY() == this.y + j) return false;

		return true;
	}
}
