package echecs;

public class Tour extends Piece
{
	boolean roque;

	public Tour(int x, int y, char couleur, Jeu jeu)
	{
		super(x, y, couleur, jeu);
		this.roque = false;
	}

	public boolean peutDeplacer(int x, int y)
	{
		if (x <  0 && x > Jeu.TAILLE && y <  0 && y > Jeu.TAILLE && this.x == x && this.y == y) return false;

		if (roque) return true;

		if (this.jeu.roiEchec(this.couleur))
		{
			this.jeu.setPlateau(this, x, y);

			Roi roi = this.jeu.getRoi(this.couleur);
			roi.calculEchec();

			if (roi.isEchec())
			{
				System.out.println("Le roi est tjrs échec malgré le déplacement en x:" + x + " et y:" + y);
				this.jeu.setPlateau(null, x, y);

				return false;
			}

			this.jeu.setPlateau(null, x, y);
		}


		Piece p = jeu.getPiece(x, y);
		if (p != null && p.getCouleur() == this.couleur) return false;

		for (int i = 1 ; i < Jeu.TAILLE ; i++)
		{
			if (this.x + i == x && this.y == y && verifierChemin(x, y) ||
			    this.x == x && this.y + i == y && verifierChemin(x, y)) return true;

		}

		// Piece roi;
		// if (this.alMouvs.isEmpty() && this.x == 7)
		// {
		// 	System.out.println(11);
		// 	roi = this.jeu.getPiece(this.x-1, this.y);
		// 	if (this.x-2 == x && this.y == y && roi != null && roi.alMouvs.size() == 1 && this.verifierChemin(x, y))
		// 		return true;
		// }
		
		// if (this.alMouvs.isEmpty() && this.x == 0)
		// {
		// 	System.out.println(12);
		// 	roi = this.jeu.getPiece(this.x+2, this.y);
		// 	if (this.x+3 == x && this.y == y && roi != null && roi.alMouvs.size() == 1 && this.verifierChemin(x, y))
		// 		return true;
		// }

		return false;
	}

	public char getSymbole() { return 'T'; }

	public boolean verifierChemin(int x, int y)
	{
		int ratio = x - this.x + y - this.y;
		for (int i = ratio > 0 ? 1 : -1 ; i != ratio ; i = ratio > 0 ? i + 1 : i - 1)
		{
			if (this.x - x == 0 && this.jeu.getPiece(this.x, this.y + i) != null) return false;
			if (this.y - y == 0 && this.jeu.getPiece(this.x + i, this.y) != null) return false;
		}

		return true;
	}
}
