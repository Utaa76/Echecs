package echecs;

public class Jeu
{
	public static final int TAILLE = 8;

	private Piece[][] plateau;

	public Jeu()
	{
		this.plateau = new Piece[Jeu.TAILLE][Jeu.TAILLE];
	}
}
