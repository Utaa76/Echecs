package echecs;

import echecs.metier.Jeu;
import echecs.vue.FrameJeu;

public class Controleur
{
	private Jeu      metier;
	private FrameJeu vue;

	public Controleur()
	{
		this.metier = new Jeu();
		this.vue    = new FrameJeu();

	}
}
