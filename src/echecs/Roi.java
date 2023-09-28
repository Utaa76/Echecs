package echecs;

import java.util.ArrayList;

public class Roi extends Piece
{
	private boolean isEchec = false;

	public Roi(int x, int y, char couleur, Jeu jeu)
	{
		super(x, y, couleur, jeu);
	}

	public boolean peutDeplacer(int x, int y)
	{
		if (x <  0 && x > Jeu.TAILLE && y <  0 && y > Jeu.TAILLE && this.x == x && this.y == y) return false;

		Piece p = jeu.getPiece(x, y);

		if (p != null && p.getCouleur() == this.couleur) return false;

		for (int i = -1 ; i < 3 ; i++)
			for (int j = -1 ; j < 3 ; j++)
				if (this.x+i == x && this.y+j == y) return true;

		//TODO: condition du cas en échec

		//TODO: le roque
		// if (this.x+2 == x && this.y == y && this.jeu.getPiece(x+1, this.y).alMouvs.isEmpty());
		// {
		// 	this.jeu.getPiece(x+1, this.y).deplacer(this.x+1, this.y);
		// 	return true;
		// }

		// if (this.x+2 == x && this.y == y && this.jeu.getPiece(x+1, this.y).alMouvs.isEmpty());
		// {
		// 	this.jeu.getPiece(x+1, this.y).deplacer(this.x+1, this.y);
		// 	return true;
		// }

		return false;
	}

	public char getSymbole() { return 'R'; }

	public void calculEchec()
	{
		ArrayList<Piece> alPiece = this.jeu.getAlPiece();

		for (Piece p : alPiece)
			if (p.getCouleur() != this.couleur && p.peutDeplacer(this.x, this.y))
			{
				this.isEchec = true;
				//System.out.println(p.toStringEvolved());
				return;
			}
		
		this.isEchec = false;
	}

	public boolean isEchec()
	{
		return this.isEchec;
	}
}
