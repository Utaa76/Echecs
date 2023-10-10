package echecs.metier;

import java.util.ArrayList;

public class Roi extends Piece
{
	private boolean isEchec = false;
	private boolean aRoque = false;

	public Roi(int x, int y, char couleur, Jeu jeu)
	{
		super(x, y, couleur, jeu);
	}

	public boolean peutDeplacer(int x, int y, boolean bRoi)
	{
		if (x <  0 && x > Jeu.TAILLE && y <  0 && y > Jeu.TAILLE && this.x == x && this.y == y) return false;

		if (this.aRoque)
		{
			this.aRoque = false;
			return true;
		}
		
		Piece p = jeu.getPiece(x, y);
		if (p != null && p.getCouleur() == this.couleur) return false;

		for (int i = -1 ; i < 2 ; i++)
			for (int j = -1 ; j < 2 ; j++)
				if (this.x+i == x && this.y+j == y) return true;


		if (this.alMouvs.isEmpty())
		{
			Piece tour = this.jeu.getPiece(x+1, this.y);
			if (this.x+2 == x && this.y == y && tour != null && tour.alMouvs.isEmpty() && this.verifierCheminRoque(x, y))
			{
				((Tour)tour).roque = true;
				this.aRoque = true;
				this.jeu.deplacer(tour, this.x+1, this.y);
				((Tour)tour).roque = false;
				return true;
			}

			tour = this.jeu.getPiece(x-2, this.y);
			if (this.x-2 == x && this.y == y && tour != null && tour.alMouvs.isEmpty() && this.verifierCheminRoque(x, y))
			{
				((Tour)tour).roque = true;
				this.aRoque = true;
				this.jeu.deplacer(tour, this.x-1, this.y);
				((Tour)tour).roque = false;
				return true;
			}
		}

		if (bRoi) return !this.seMetEchec(x, y);

		return false;
	}

	public char getSymbole() { return 'R'; }

	public void calculEchec()
	{
		ArrayList<Piece> alPiece = this.jeu.getAlPiece();

		for (Piece p : alPiece)
			if (p.getCouleur() != this.couleur && !(p instanceof Roi) && p.peutDeplacer(this.x, this.y, true))
			{
				this.isEchec = true;
				// System.out.println("piece qui met echec le roi " + this.couleur + " : " + p.toStringEvolved());
				return;
			}

		this.isEchec = false;
	}

	public boolean isEchec()
	{
		return this.isEchec;
	}

	public void setEchec(boolean echec)
	{
		this.isEchec = echec;
	}

	public boolean seMetEchec(int x, int y)
	{
		if (!this.peutDeplacer(x, y, false)) return true;
		boolean bRet = false;

		Piece p = this.jeu.setPlateau(this, x, y);
		int xPrev = this.x;
		int yPrev = this.y;

		this.x = x;
		this.y = y;

		this.calculEchec();

		if (this.isEchec)
		{
			//System.out.println("Le roi se met échec en se déplaçant en x:" + x + " et y:" + y);

			bRet = true;
		}

		this.x = xPrev;
		this.y = yPrev;
		this.jeu.setPlateau(p, x, y);

		System.out.println("le roi peut se libérer de l'échec en allant en x:" + x + " y:" + y);

		return bRet;
	}

	public boolean verifierCheminRoque(int x, int y)
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
