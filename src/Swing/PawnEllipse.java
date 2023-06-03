package Swing;

import java.awt.Color;
import java.awt.geom.Ellipse2D;

public class PawnEllipse {
	private int position[];
	private Ellipse2D.Double ellipse;
	private Color color;
	//cada piao de cada jogador tem indice 1 a 4.
	
	
	public PawnEllipse(int x,int y,Ellipse2D.Double ellipse,Color color) {
		this.position = new int[2];
		this.position[0] = x;
		this.position[1] = y;
		this.ellipse = ellipse;
		this.color = color;

		
	}
	public Ellipse2D.Double getEllipse(){
		return ellipse;
	}
	
}
