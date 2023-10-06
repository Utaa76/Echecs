package echecs.vue;

import javax.swing.JFrame;
import java.awt.Graphics;

import echecs.Controleur;

public class FrameJeu extends JFrame
{
	private Controleur ctrl;
	private PanelJeu   panelJ;

	public FrameJeu(Controleur ctrl)
	{
		this.ctrl = ctrl;

		this.setName("Echecs");
		this.setSize(1000,1000);
		this.setLocationRelativeTo(null);

		this.panelJ = new PanelJeu(this.ctrl);
		this.add(this.panelJ);

		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
