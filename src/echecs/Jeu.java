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

	void setPlateau(Piece p, int x, int y)
	{
		if (x < 0 || x > Jeu.TAILLE || y < 0 || y > Jeu.TAILLE) return;

		this.plateau[x][y] = p;
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

	public boolean echecEtMat()
	{
		char couleur;
		if      (this.roiEchec(Piece.BLANC)) couleur = Piece.BLANC;
		else if (this.roiEchec(Piece.NOIR )) couleur = Piece.NOIR ;
		else return false;

		Roi roi = this.getRoi(couleur);
		for (int x = -1 ; x < 2 ; x++)
			for (int y = -1 ; y < 2 ; y++)
				if (!roi.seMetEchec(x, y)) return false;

		for (int x = 0 ; x < Jeu.TAILLE ; x++)
			for (int y = 0 ; y < Jeu.TAILLE ; y++)
				for (Piece p : this.alPiece)
					if (p.getCouleur() != couleur)
						if (p.peutDeplacer(x, y)) return false;

		return true;
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
		System.out.println(j.deplacer(j.getPiece(3, 1), 3, 3));
		System.out.println(j);
	}
}
