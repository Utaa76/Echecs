package echecs.vue;

import javax.swing.JFrame;
import java.awt.Graphics;

public class FrameJeu extends JFrame
{
	public FrameJeu()
	{
		this.setName("Echecs");
		this.setLocationRelativeTo(null);

		this.repaint();

		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void paintComponent(Graphics g)
	{
		// Dessiner la grille
	}
}
