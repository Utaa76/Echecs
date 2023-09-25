package echecs;

import java.util.ArrayList;

public class Roi extends Piece
{
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

		return false;
	}

	public char getSymbole() { return 'R'; }

	public boolean echec()
	{
		ArrayList<Piece> alPiece = this.jeu.getAlPiece();

		for (Piece p : alPiece)
			if (p.getCouleur() != this.couleur)
				if (p.peutDeplacer(this.x, this.y)) return true;

		return false;
	}
}
