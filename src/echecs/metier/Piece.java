package echecs.metier;

import java.util.ArrayList;

import echecs.Jeu;

public abstract class Piece
{
	public static final char BLANC = 'B';
	public static final char NOIR  = 'N';

	protected int                x;
	protected int                  y;
	protected char                 couleur;
	protected ArrayList<Mouvement> alMouvs;
	protected Jeu                  jeu;

	public Piece(int x, int y, char couleur, Jeu jeu)
	{
		this.x = x;
		this.y = y;

		if (couleur == Piece.BLANC || couleur == Piece.NOIR) this.couleur = couleur;
		else                                                 this.couleur = Piece.BLANC;

		this.alMouvs = new ArrayList<>();
		this.jeu     = jeu;
	}

	public abstract boolean peutDeplacer(int x, int y);

	public boolean deplacer(int x, int y)
	{
		if (!this.peutDeplacer(x, y)) return false;

		//if ((this.jeu.getRoi(this.couleur)).echec()) return false;

		this.alMouvs.add(new Mouvement(this.x, this.y, x, y));

		this.x = x;
		this.y = y;

		return true;
	}

	public boolean couvreEchec(int x, int y)
	{
		boolean bRet = true;
		this.jeu.setPlateau(this, x, y);

		Roi roi = this.jeu.getRoi(this.couleur);
		roi.calculEchec();

		if (roi.isEchec())
			bRet = false;

		this.jeu.setPlateau(null, x, y);
		return bRet;
	}
	
	public abstract char getSymbole();

	public int  getX      () { return this.x;       }

	public int  getY      () { return this.y;       }

	public char getCouleur() { return this.couleur; }

	public String toString()
	{
		return this.couleur == Piece.BLANC ? ("" + this.getSymbole()).toLowerCase() : "" + this.getSymbole();
	}

	public String toStringEvolved()
	{
		return this.couleur == Piece.BLANC ? ("" + this.getSymbole()).toLowerCase() + String.format("x:%d y:%d", this.x, this.y) : "" + this.getSymbole() + String.format("x:%d y:%d", this.x, this.y);
	}
}