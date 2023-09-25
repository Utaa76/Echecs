package echecs;

public class Dame extends Piece
{
	public Dame(int x, int y, char couleur, Jeu jeu)
	{
		super(x, y, couleur, jeu);
	}

	public boolean peutDeplacer(int x, int y)
	{
		if (x <  0 && x > Jeu.TAILLE && y <  0 && y > Jeu.TAILLE && this.x == x && this.y == y) return false;

		Piece p = jeu.getPiece(x, y);
		if (p != null && p.getCouleur() == this.couleur) return false;

		for (int i = -8 ; i < Jeu.TAILLE ; i++)
		{
			System.out.printf("this.x = %d, + i = %d == %d (this.y = %d)\n", this.x, i, (this.x+i), this.y);
			if (this.x + i == x && this.y == y && verifierCheminDroit(x, y) ||
			    this.x == x && this.y + i == y && verifierCheminDroit(x, y)) return true;

		}

		System.out.println("apres verifierCheminDroit");

		for (int i = 1 ; i < Jeu.TAILLE ; i++)
		{
			if (this.x + i == x && this.y + i == y && verifierCheminDiag(x, y) ||
			    this.x - i == x && this.y + i == y && verifierCheminDiag(x, y) ||
				this.x + i == x && this.y - i == y && verifierCheminDiag(x, y) ||
				this.x - i == x && this.y - i == y && verifierCheminDiag(x, y)   ) return true;
			
		}

		System.out.println("apres verifierCheminDiag");

		return false;
	}

	public char getSymbole() { return 'D'; }

	public boolean verifierCheminDroit(int x, int y)
	{
		int ratio = x - this.x + y - this.y;
		for (int i = ratio > 0 ? 1 : -1 ; i != ratio ; i = ratio > 0 ? i + 1 : i - 1)
		{
			if (this.x - x == 0 && this.jeu.getPiece(this.x, this.y + i) != null) return false;
			if (this.y - y == 0 && this.jeu.getPiece(this.x + i, this.y) != null) return false;
		}

		return true;
	}

	public boolean verifierCheminDiag(int x, int y)
	{
		int ratioX = x - this.x;
		int ratioY = y - this.y;

		for (int i = ratioX > 0 ? 1 : -1, j = ratioY > 0 ? 1 : -1 ; i != ratioX || j != ratioY; i = ratioX > 0 ? i + 1 : i - 1, j = ratioY > 0 ? j + 1 : j - 1)
		{
			System.out.println("i = " + i + " / " + ratioX);
			System.out.println("j = " + j + " / " + ratioY);
			if (this.jeu.getPiece(this.x + i, this.y + j) != null) return false;
		}

		return true;
	}
}
