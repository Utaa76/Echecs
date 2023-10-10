package echecs.metier;

import java.util.ArrayList;

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

	public abstract boolean peutDeplacer(int x, int y, boolean bRoi);

	public boolean deplacer(int x, int y)
	{
		if (!this.peutDeplacer(x, y, false)) return false;

		this.mangerPiece(x, y);

		this.alMouvs.add(new Mouvement(this.x, this.y, x, y));

		this.x = x;
		this.y = y;

		return true;
	}

	public boolean mangerPiece(int x, int y)
	{
		Piece p = this.jeu.getPiece(x, y);
		if (p != null && this.couleur == p.getCouleur()) return false;

		this.jeu.getAlPiece().remove(p);
		return true;
	}

	public boolean couvreEchec(int x, int y)
	{
		Piece p = this.jeu.setPlateau(this, x, y);

		Roi roi = this.jeu.getRoi(this.couleur);
		roi.calculEchec();

		boolean bRet = !roi.isEchec();

		/*if (!bRet)*/ this.jeu.setPlateau(p, x, y);
		roi.setEchec(true);
		return bRet;
	}

	public boolean metEchec(int x, int y)
	{
		boolean bRet;

		Piece p = this.jeu.setPlateau(this, x, y);
		int xPrev = this.x;
		int yPrev = this.y;

		this.x = x;
		this.y = y;

		Roi roi = this.jeu.getRoi(this.couleur);
		roi.calculEchec();

		bRet = roi.isEchec();

		this.x = xPrev;
		this.y = yPrev;
		this.jeu.setPlateau(p, x, y);

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