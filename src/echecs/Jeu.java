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
		if (p.peutDeplacer(x, y))
		{
			this.plateau[p.getX()][p.getY()] = null;
			p.deplacer(x, y);
			this.plateau[x][y] = p;

			this.getRoi(Piece.BLANC).calculEchec();
			this.getRoi(Piece.NOIR ).calculEchec();

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

		// Déplacement de la tour
		System.out.println(j.deplacer(j.getPiece(7, 2), 7, 1));
		System.out.println(j);

		// Déplacement du cavalier
		System.out.println(j.deplacer(j.getPiece(6, 0), 7, 2));
		System.out.println(j);

		// Déplacement du cavalier
		System.out.println(j.deplacer(j.getPiece(6, 0), 5, 2));
		System.out.println(j);

		// Déplacement du roi
		System.out.println(j.deplacer(j.getPiece(3, 1), 3, 2));
		System.out.println(j);

		// Déplacement du cavalier
		System.out.println(j.deplacer(j.getPiece(1, 0), 3, 1));
		System.out.println(j);

		// Déplacement du pion 2
		System.out.println(j.deplacer(j.getPiece(1, 1), 1, 2));
		System.out.println(j);

		// Déplacement du pion 2
		System.out.println(j.deplacer(j.getPiece(1, 2), 1, 4));
		System.out.println(j);

		// Déplacement du fou
		System.out.println(j.deplacer(j.getPiece(2, 0), 0, 2));
		System.out.println(j);

		// Déplacement du fou
		System.out.println(j.deplacer(j.getPiece(0, 2), 2, 4));
		System.out.println(j);

		// Déplacement du fou
		System.out.println(j.deplacer(j.getPiece(2, 4), 4, 2));
		System.out.println(j);

		// Déplacement du fou
		System.out.println(j.deplacer(j.getPiece(2, 4), 1, 3));
		System.out.println(j);

		// Déplacement de la dame
		System.out.println(j.deplacer(j.getPiece(4, 0), 0, 0));
		System.out.println(j);

		// Déplacement de la dame
		System.out.println(j.deplacer(j.getPiece(0, 0), 2, 2));
		System.out.println(j);

		// Déplacement de la dame
		System.out.println(j.deplacer(j.getPiece(2, 2), 2, 6));
		System.out.println(j);

		// Déplacement du roi Noir
		System.out.println(j.deplacer(j.getPiece(3, 7), 2, 6));
		System.out.println(j);

		// Déplacement du cavalier Noir
		System.out.println(j.deplacer(j.getPiece(1, 7), 2, 5));
		System.out.println(j);

		// Déplacement du pion Noir
		System.out.println("06");
		System.out.println(j.deplacer(j.getPiece(0, 6), 0, 4));
		System.out.println(j);

		// Déplacement du pion Noir
		System.out.println(j.deplacer(j.getPiece(0, 4), 0, 3));
		System.out.println(j);

		// Déplacement du pion Noir
		System.out.println(j.deplacer(j.getPiece(0, 4), 1, 3));
		System.out.println(j);

		// Déplacement du cavalier Noir
		System.out.println(j.deplacer(j.getPiece(2, 5), 3, 3));
		System.out.println(j);

		// Déplacement du pion Noir
		System.out.println("Pion noir");
		System.out.println(j.deplacer(j.getPiece(3, 6), 3, 5));
		System.out.println(j);

		// Roi blanc en échec ?
		System.out.println(j.roiEchec(Piece.BLANC));

		// Déplacement de la dame
		System.out.println(j.deplacer(j.getPiece(4, 7), 1, 4));
		System.out.println(j);

		// Roi blanc en échec ?
		System.out.println(j.roiEchec(Piece.BLANC));

		// Déplacement du pion blanc
		System.out.println(j.deplacer(j.getPiece(2, 1), 2, 2));
		System.out.println(j);

		// Déplacement du roi
		System.out.println(j.deplacer(j.getPiece(3, 2), 4, 2));
		System.out.println(j);

		// Déplacement du pion blanc
		System.out.println(j.deplacer(j.getPiece(2, 1), 2, 3));
		System.out.println(j);
	}
}
