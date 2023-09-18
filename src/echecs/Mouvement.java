package echecs;

public class Mouvement
{
	private int x1, y1;   //position de départ
	private int x2, y2;   //position d'arrivée

	public Mouvement(char x1, int y1, char x2, int y2)
	{
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	public String toString()
	{
		return String.format("[%c%d] -> [%c%d]", this.x1, this.y1, this.x2, this.y2);
	}
}
