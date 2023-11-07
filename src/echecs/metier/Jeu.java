package echecs.metier;

import java.util.ArrayList;

public class Jeu
{
	public static final int TAILLE = 8;

	private   Piece[][]         plateau;
	private   ArrayList<Piece>  alPiece;
	          ArrayList<Piece>  alPieceMangees;
	private   boolean           echecEtMat;
	private   char              couleurAJouer;
	protected boolean          plateauRetourne;

	public Jeu()
	{
		this.plateau         = new Piece[Jeu.TAILLE][Jeu.TAILLE];
		this.alPiece         = new ArrayList<>();
		this.alPieceMangees  = new ArrayList<>();
		this.echecEtMat      = false;
		this.couleurAJouer   = Piece.BLANC;
		this.plateauRetourne = false;
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

	Piece setPlateau(Piece p, int x, int y)
	{
		if (x < 0 || x > Jeu.TAILLE || y < 0 || y > Jeu.TAILLE && !(this.plateau[x][y] instanceof Roi)) return null;

		Piece pieceAvant = this.plateau[x][y];
		this.plateau[x][y] = p;
		return pieceAvant;
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
		int xPrev = 0, yPrev = 0;
		if (p != null)
		{
			xPrev = p.getX();
			yPrev = p.getY();
		}

		if (!this.echecEtMat && p.getCouleur() == this.couleurAJouer && p.deplacer(x, y))
		{
			this.plateau[xPrev][yPrev] = null;
			this.plateau[x][y] = p;

			
			if (p instanceof Tour && ((Tour)p).roque) return true;
			else                                      this.couleurAJouer = this.couleurAJouer == Piece.BLANC ? Piece.NOIR : Piece.BLANC;

			this.getRoi(Piece.BLANC).calculEchec();
			this.getRoi(Piece.NOIR ).calculEchec();

			this.echecEtMat = this.echecEtMat();
			System.out.println(this);
			System.out.println(alPiece);
			//this.majAlPiece();
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
			{
				int xRoi = roi.getX();
				int yRoi = roi.getY();
				if (xRoi+x >= 0 && xRoi+x < Jeu.TAILLE && yRoi+y >= 0 && yRoi+y < Jeu.TAILLE) { System.out.printf("x:%d y:%d\n", xRoi+x, yRoi+y);
					if (!roi.seMetEchec(xRoi+x, yRoi+y)) return false; }
			}

		System.out.println("le roi ne peut pas bouger");

		ArrayList<Piece> alPieceCheck = new ArrayList<>(this.alPiece);
		for (int x = 0 ; x < Jeu.TAILLE ; x++)
			for (int y = 0 ; y < Jeu.TAILLE ; y++)
				for (Piece p : alPieceCheck)
					if (p.getCouleur() == couleur && !(p instanceof Roi))
						if (p.couvreEchec(x, y)){ System.out.println(p + "x:" + x + " y:" + y); return false;}

		return true;
	}

	public Piece getPiece(int x, int y)
	{
		if (x < 0 || x >= Jeu.TAILLE || y < 0 || y >= Jeu.TAILLE) return null;
		return this.plateau[x][y];
	}

	public boolean isEchecEtMat()
	{
		return this.echecEtMat;
	}

	public ArrayList<Piece> getAlPiece()
	{
		return this.alPiece;
	}

	public ArrayList<Piece> getAlPieceMangees()
	{
		return this.alPieceMangees;
	}

	public Piece[][] getPlateau()
	{
		return this.plateau;
	}

	public Roi getRoi(char couleur)
	{
		for (Piece p : this.alPiece)
			if (p instanceof Roi && p.getCouleur() == couleur) return (Roi)p;

		return null;
	}

	public char getCouleurAJouer()
	{
		return this.couleurAJouer;
	}

	public void retournerPlateau()
	{
		this.plateauRetourne = !this.plateauRetourne;
		Piece[][] plateauTmp = new Piece[8][8];
		this.copierPlateau(plateauTmp);
		for (int i = 0 ; i < this.plateau.length ; i++)
			for (int j = 0 ; j < this.plateau[i].length ; j++)
			{
				System.out.printf("i:%d j:%d\n",i,j);
				this.plateau[i][j] = plateauTmp[7-i][7-j];

				Piece p = this.plateau[i][j];
				if (p != null)
				{
					p.x = i;
					p.y = j;
				}
			}

		System.out.println(this);
	}

	public void copierPlateau(Piece[][] plateauVide)
	{
		for (int i = 0 ; i < this.plateau.length ; i++)
			for (int j = 0 ; j < this.plateau[i].length ; j++)
				plateauVide[i][j] = this.plateau[i][j];
	}

	void majAlPiece()
	{
		this.alPiece.clear();

		for (int i = 0; i < this.plateau.length; i++)
		{
			for (int j = 0; j < this.plateau[i].length; j++)
			{
				Piece p = this.plateau[i][j];

				if (p != null) this.alPiece.add(p);
			}
		}
	}
}
