package echecs;

public class Cavalier extends Piece
{
	public Cavalier(int x, int y, char couleur, Jeu jeu)
	{
		super(x, y, couleur, jeu);
	}

	public boolean peutDeplacer(int x, int y)
	{
		if (x <  0 && x > Jeu.TAILLE && y <  0 && y > Jeu.TAILLE && this.x == x && this.y == y) return false;

		if (this.jeu.roiEchec(this.couleur))
		{
			this.jeu.setPlateau(this, x, y);

			Roi roi = this.jeu.getRoi(this.couleur);
			roi.calculEchec();

			if (roi.isEchec())
			{
				this.jeu.setPlateau(null, x, y);

				return false;
			}

			this.jeu.setPlateau(null, x, y);
		}


		Piece p = jeu.getPiece(x, y);
		if (p != null && p.getCouleur() == this.couleur) return false;

		if (this.x + 1 == x && this.y + 2 == y ||
		    this.x + 1 == x && this.y - 2 == y ||
		    this.x - 1 == x && this.y + 2 == y ||
		    this.x - 1 == x && this.y - 2 == y ||
		    this.x + 2 == x && this.y + 1 == y ||
		    this.x + 2 == x && this.y - 1 == y ||
		    this.x - 2 == x && this.y + 1 == y ||
		    this.x - 2 == x && this.y - 1 == y    ) return true;

		return false;
	}

	public char getSymbole() { return 'C'; }
}
