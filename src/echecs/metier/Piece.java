package echecs.metier;

import java.util.ArrayList;

public abstract class Piece
{
	public static final char BLANC = 'B';
	public static final char NOIR  = 'N';

	protected int                  x;
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

	public Piece mangerPiece(int x, int y)
	{
		Piece p = this.jeu.getPiece(x, y);
		if (p != null && this.couleur == p.getCouleur() && p instanceof Roi) return null;

		this.jeu.getAlPiece().remove(p);
		this.jeu.alPieceMangees.add(p);

		return p;
	}

	// FIXME: le fou disparait ou fait crash le programme
	public boolean couvreEchec(int x, int y)
	{
		if (this.jeu.getPiece(x, y) instanceof Roi) return false;
		Piece p = this.jeu.setPlateau(this, x, y);
		Roi roi = this.jeu.getRoi(this.couleur);
		if (p != null && !(p instanceof Roi)) this.jeu.getAlPiece().remove(p);
		roi.calculEchec();

		boolean bRet = !roi.isEchec();

		this.jeu.setPlateau(p, x, y);
		
		roi.setEchec(true);

		if (bRet)
			System.out.println("Piece = " + this.toString() + " x:" + x + " y:" + y);

		if (p != null && !(p instanceof Roi)) this.jeu.getAlPiece().add(p);
		return bRet;
	}

	//FIXME: le problème du dessus vient d'ici
	public boolean metEchec(int x, int y)
	{
		boolean bRet;

		Piece p = this.jeu.setPlateau(this, x, y);
		if (p != null && !(p instanceof Roi)) this.jeu.getAlPiece().remove(p);
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
		if (p != null && !(p instanceof Roi)) this.jeu.getAlPiece().add(p);

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