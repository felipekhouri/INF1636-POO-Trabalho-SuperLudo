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

	public TileRepresentation (Color pawns[],TileType type,int x, int y) {
		this.pawns = pawns;
		this.type = type;
		this.positionXY = new int[] {x, y};
	}

	public TileType getType() {
		return type;
	}

	public Color[] getColor() {
		return pawns;
	}

	public int[] getPosition() {
		return positionXY;
	}

	public int getX() {
		return positionXY[0];
	}

	public int getY() {
		return positionXY[1];
	}
}

