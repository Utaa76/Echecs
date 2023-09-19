package echecs;

import java.util.ArrayList;

public class Jeu
{
	public static final int TAILLE = 8;

	private Piece[][]        plateau;
	private ArrayList<Piece> alPiece;

	public Jeu()
	{
		this.plateau = new Piece[Jeu.TAILLE][Jeu.TAILLE];
		this.alPiece = new ArrayList<>();
		this.initialiserPieces();
	}

	public void initialiserPieces()
	{
		char couleur = Piece.BLANC;
		Piece p;

		for (int x = 0 ; x < Jeu.TAILLE ; x++)
			for (int y = 0 ; y < Jeu.TAILLE ; y++)
			{
				p = null;
				couleur = y < 2 ? Piece.BLANC : Piece.NOIR;

				if (y == 0 || y == Jeu.TAILLE-1)
				{
					if (x == 0 || x == Jeu.TAILLE-1)
						p = new Tour(x, y, couleur, this);

					if (x == 1 || x == Jeu.TAILLE-2)
						p = new Cavalier(x, y, couleur, this);

					if (x == 2 || x == Jeu.TAILLE-3)
						p = new Fou(x, y, couleur, this);

					if (x == 3)
						p = new Roi(x, y, couleur, this);
					
					if (x == 4)
						p = new Dame(x, y, couleur, this);

				}

				if (y == 1 || y == Jeu.TAILLE-2)
					p = new Pion(x, y, couleur, this);

				if (p != null)
				{
					this.plateau[x][y] = p;
					this.alPiece.add(p);
				}
			}
	}

	public String toString()
	{
		String sRet = "";

		for (int j = Jeu.TAILLE-1 ; j >= 0 ; j--)
		{
			for (int i = 0 ; i < Jeu.TAILLE ; i++)
			{
				if (this.plateau[i][j] != null) sRet += this.plateau[i][j].toString();
				else                            sRet += " ";
			}

			sRet += "\n";
		}

		return sRet;
	}

	public boolean deplacer(Piece p, int x, int y)
	{
		if (p.peutDeplacer(x, y))
		{
			this.plateau[p.getX()][p.getY()] = null;
			p.deplacer(x, y);
			this.plateau[x][y] = p;
			return true;
		}

		return false;
	}

	public Piece getPiece(int x, int y)
	{
		return this.plateau[x][y];
	}

	public static void main(String[] args)
	{
		Jeu j = new Jeu();
		System.out.println(j);

		// Déplacement d'un pion
		System.out.println(j.deplacer(j.getPiece(3, 1), 3, 3));
		System.out.println(j);

		// Déplacement du Roi
		System.out.println(j.deplacer(j.getPiece(3, 0), 3, 1));
		System.out.println(j);

		// Déplacement d'un pion
		System.out.println(j.deplacer(j.getPiece(0, 1), 0, 3));
		System.out.println(j);

		// Déplacement de la tour
		System.out.println(j.deplacer(j.getPiece(0, 0), 0, 2));
		System.out.println(j);

		// Déplacement de la tour
		System.out.println(j.deplacer(j.getPiece(0, 2), 7, 2));
		System.out.println(j);
	}
}
