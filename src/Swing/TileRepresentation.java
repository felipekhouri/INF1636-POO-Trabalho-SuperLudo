package Swing;

import java.awt.Color;

//Tile para criar na view
public class TileRepresentation {
	Color pawns[];
	TileType type;
	int positionXY[]; 
	
	public TileRepresentation (Color pawns[],TileType type,int positionXY[]) {
		this.pawns = pawns;
		this.type = type;
		this.positionXY = positionXY;
	}
}

