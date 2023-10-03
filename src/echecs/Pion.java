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

		boolean bRet;
		if (this.couleur == Piece.BLANC)
		{
			if (p == null)
				bRet = this.x == x && this.y+1 == y || (this.x == x && this.y+2 == y && this.alMouvs.isEmpty());
			else
				bRet = p.getCouleur() != this.couleur && (this.x+1 == x || this.x-1 == x) && this.y+1 == y;
		}
		else
		{
			if (p == null)
				bRet = this.x == x && this.y-1 == y || (this.x == x && this.y-2 == y && this.alMouvs.isEmpty());
			else
				bRet = p.getCouleur() != this.couleur && (this.x+1 == x || this.x-1 == x) && this.y-1 == y;
		}

		return bRet;
	}

	public char getSymbole() { return 'P'; }
}
