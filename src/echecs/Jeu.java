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

					if (x == 4)
						p = new Roi(x, y, couleur, this);
					
					if (x == 3)
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
		String sRet = "+---+---+---+---+---+---+---+---+\n";

		for (int j = Jeu.TAILLE-1 ; j >= 0 ; j--)
		{
			for (int i = 0 ; i < Jeu.TAILLE ; i++)
			{
				if (i == 0)
				{
					if (this.plateau[i][j] != null) sRet += "| " + this.plateau[i][j].toString() + " |";
					else                            sRet += "|   |";
				}
				else 
				{
					if (this.plateau[i][j] != null) sRet += " " + this.plateau[i][j].toString() + " |";
					else                            sRet += "   |";
				}
			}

			sRet += "\n+---+---+---+---+---+---+---+---+\n";
		}

		return sRet;
	}

	public boolean deplacer(Piece p, int x, int y)
	{
		// System.out.println("deplacement " + p);
		if (p.peutDeplacer(x, y))
		{
			this.plateau[p.getX()][p.getY()] = null;
			System.out.println("deplacement " + p + " " + p.deplacer(x, y));
			this.plateau[x][y] = p;

			if (p instanceof Tour && ((Tour)p).roque) return true;

			this.getRoi(Piece.BLANC).calculEchec();
			this.getRoi(Piece.NOIR ).calculEchec();

			System.out.println("échec : " + this.getRoi(Piece.BLANC).isEchec());

			return true;
		}

		return false;
	}

	public boolean roiEchec(char couleur)
	{
		return this.getRoi(couleur).isEchec();
	}

	public Piece getPiece(int x, int y)
	{
		return this.plateau[x][y];
	}

	public ArrayList<Piece> getAlPiece()
	{
		return this.alPiece;
	}

	public Roi getRoi(char couleur)
	{
		for (Piece p : this.alPiece)
			if (p instanceof Roi && p.getCouleur() == couleur) return (Roi)p;

		return null;
	}

	//TODO: Echec et mat (si un roi est échec, regarder si pour chaque position il est tjrs échec (TODO: la couverture d'échec))

	public static void main(String[] args)
	{
		Jeu j = new Jeu();
		System.out.println(j);

		// Déplacement d'un pion
		System.out.println("1 " + j.deplacer(j.getPiece(3, 1), 3, 3));
		System.out.println(j);

		// Déplacement d'un fou
		System.out.println("2 " + j.deplacer(j.getPiece(2, 0), 4, 2));
		System.out.println(j);

		// Déplacement d'un cavalier
		System.out.println("3 " + j.deplacer(j.getPiece(1, 0), 2, 2));
		System.out.println(j);

		// Déplacement de la dame
		System.out.println("4 " + j.deplacer(j.getPiece(3, 0), 3, 2));
		System.out.println(j);

		// Roque Ouest
		System.out.println("5 " + j.deplacer(j.getPiece(4, 0), 2, 0));
		System.out.println(j);

		// Pion noir
		System.out.println("6 " + j.deplacer(j.getPiece(6, 6), 6, 5));
		System.out.println(j);

		// Fou noir
		System.out.println("7 " + j.deplacer(j.getPiece(5, 7), 7, 5));
		System.out.println(j);

		// Fou noir
		System.out.println("8 " + j.deplacer(j.getPiece(7, 5), 4, 2));
		System.out.println(j);

		// Pion blanc (test échec)
		System.out.println("9 " + j.deplacer(j.getPiece(2, 2), 1, 4));
		System.out.println(j);

		/* JEU 2 POUR TEST */
		Jeu j2 = new Jeu();

		System.out.println(j2.deplacer(j2.getPiece(4, 6), 4, 4));
		System.out.println(j2);

		System.out.println(j2.deplacer(j2.getPiece(5, 7), 1, 3));
		System.out.println(j2);

		// System.out.println(j2.deplacer(j2.getPiece(3, 1), 3, 3));
		// System.out.println(j2);

		System.out.println(j2.deplacer(j2.getPiece(6, 1), 6, 2));
		System.out.println(j2);

		System.out.println(j2.deplacer(j2.getPiece(5, 0), 6, 1));
		System.out.println(j2);

		System.out.println(j2.deplacer(j2.getPiece(6, 0), 7, 2));
		System.out.println(j2);

		System.out.println(j2.deplacer(j2.getPiece(4, 0), 6, 0));
		System.out.println(j2);

		System.out.println(j2.deplacer(j2.getPiece(5, 1), 5, 2));
		System.out.println(j2);
		
		System.out.println(j2.deplacer(j2.getPiece(3, 1), 3, 2));
		System.out.println(j2);

		System.out.println(j2.deplacer(j2.getPiece(3, 0), 3, 1));
		System.out.println(j2);

		System.out.println(j2.deplacer(j2.getPiece(1, 3), 2, 4));
		System.out.println(j2);

		System.out.println(j2.deplacer(j2.getPiece(3, 1), 2, 2));
		System.out.println(j2);
	}
}
