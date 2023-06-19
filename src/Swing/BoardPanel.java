

package Swing;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.GeneralPath;
import java.util.*;

public class BoardPanel extends JPanel {

	private PawnEllipse[] pawns;
	private Set<Rectangle2D.Double> allTiles; 
	Graphics2D g2d;
	private Set<TileRepresentation> tileRepresentations; 

    private MouseListener listener; // Corrigido para MouseListener

    // public BoardPanel(MouseListener listener) { // Corrigido para receber MouseListener
    //     this.listener = listener;
    //     addMouseListener(listener);
    // }
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.g2d = (Graphics2D) g;
		this.allTiles = new HashSet<Rectangle2D.Double>();

        // Desenha o fundo do tabuleiro
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, 800, 800);

        // Define as dimensões do tabuleiro e as casas individuais
        int boardSize = Math.min(getWidth(), getHeight());
        double squareSize = (int)boardSize/2.5;
        double cellSize = squareSize/6;
        double circleSize = cellSize/2; 
       
//        MARK: Draw big squares circles and cells
        
        Rectangle2D.Double topLeft = new Rectangle2D.Double(0, 0, squareSize, squareSize);
        drawRectangle(topLeft,g2d,Color.RED);
        drawCircles(topLeft,g2d,circleSize);
        drawCellsHorizontal(g2d,cellSize,topLeft.getMinX(),topLeft.getMaxY(), true, true, Color.RED);        
        drawCellsVertical(g2d,cellSize,topLeft.getMaxX(),topLeft.getMinY(), true, false, Color.RED);
        
        drawEndCellsHorizontal(g2d,cellSize,topLeft.getMinX(),topLeft.getMaxY()+cellSize,true,Color.RED);

        
        Rectangle2D.Double topRight = new Rectangle2D.Double(getWidth()-squareSize, 0, squareSize, squareSize);
        drawRectangle(topRight,g2d,Color.GREEN);
        drawCircles(topRight,g2d,circleSize);
        drawCellsHorizontal(g2d,cellSize,topRight.getMinX(),topRight.getMaxY(), false, false, Color.GREEN);
        drawCellsVertical(g2d,cellSize,topRight.getMinX()-cellSize,topLeft.getMinY(), true, true, Color.GREEN);

        drawEndCellsVertical(g2d,cellSize,topRight.getMinX()-2*cellSize,topRight.getMinY(),true,Color.GREEN);

        
        Rectangle2D.Double bottomLeft = new Rectangle2D.Double(0, getHeight()-squareSize, squareSize, squareSize);
        drawRectangle(bottomLeft,g2d,Color.BLUE);
        drawCircles(bottomLeft,g2d,circleSize);
        drawCellsHorizontal(g2d,cellSize,bottomLeft.getMinX(),bottomLeft.getMinY()-cellSize, true, false, Color.BLUE);
        drawCellsVertical(g2d,cellSize,bottomLeft.getMaxX(),bottomLeft.getMinY(), false, true, Color.BLUE);
        
        drawEndCellsVertical(g2d,cellSize,bottomLeft.getMaxX()+cellSize,bottomLeft.getMinY(),false,Color.BLUE);


        
        Rectangle2D.Double bottomRight = new Rectangle2D.Double(getWidth()-squareSize, getHeight()-squareSize, squareSize, squareSize);
        drawRectangle(bottomRight,g2d,Color.YELLOW);
        drawCircles(bottomRight,g2d,circleSize);
        drawCellsHorizontal(g2d,cellSize,bottomRight.getMinX(),bottomRight.getMinY()-cellSize, false, true, Color.YELLOW);        
        drawCellsVertical(g2d,cellSize,bottomRight.getMinX()-cellSize,bottomRight.getMinY(), false, false, Color.YELLOW);
       
        drawEndCellsHorizontal(g2d,cellSize,bottomRight.getMinX(),bottomRight.getMinY()-2*cellSize,false,Color.YELLOW);



        //MARK: Draw triangles
        //red triangle
        double xPointsRED[] = {topLeft.getMaxX(),getWidth()/2,topLeft.getMaxX()};
        double yPointsRED[] = {topLeft.getMaxY(),getWidth()/2,bottomLeft.getMinY()};;
        
        //green triangle
        double xPointsGREEN[] = {topLeft.getMaxX(),getWidth()/2,topRight.getMinX()};
        double yPointsGREEN[] = {topLeft.getMaxY(),getWidth()/2,topLeft.getMaxY()};

        
        //yellow triangle
        double xPointsYELLOW[] = {topRight.getMinX(),getWidth()/2,topRight.getMinX()};
        double yPointsYELLOW[] = {topLeft.getMaxY(),getWidth()/2,bottomLeft.getMinY()};
   
        
        //bluew triangle
        double xPointsBLUE[] = {bottomLeft.getMaxX(),getWidth()/2,bottomRight.getMinX()};
        double yPointsBLUE[] = {bottomLeft.getMinY(),getWidth()/2,bottomLeft.getMinY()};

        drawTriangle(xPointsRED, yPointsRED, g2d, Color.RED);
        drawTriangle(xPointsGREEN, yPointsGREEN, g2d, Color.GREEN);
        drawTriangle(xPointsYELLOW, yPointsYELLOW, g2d, Color.YELLOW);
        drawTriangle(xPointsBLUE, yPointsBLUE, g2d, Color.BLUE);
        
        
        //MARK: draw pawns in barrier and exit tile
    //    drawBarrier(Color.GREEN,105,105,g2d);
    //    drawExitDoublePawn(Color.RED, Color.GREEN,100,100,g2d);
        // pawns = new PawnEllipse[4];
        // pawns[0] = new PawnEllipse(100,100,drawPawn(Color.GREEN,100,100,g2d),Color.GREEN);
        // pawns[1] = new PawnEllipse(200,100,drawPawn(Color.RED,100,100,g2d),Color.RED);
        // pawns[2] = new PawnEllipse(500,100,drawPawn(Color.BLUE,500,100,g2d),Color.BLUE);
        // pawns[3] = new PawnEllipse(300,100,drawPawn(Color.YELLOW,300,100,g2d),Color.YELLOW);
		// TileRepresentation[] tileRepresentations = controller.getOccupiedTiles();
		// pawns = new PawnEllipse[tileRepresentations.size()];
		// for(int i = 0; i < tileRepresentations; i++) {
		// 	pawns[i] = pawnsRepresentations[i];
		// }
        
		drawAllTiles();

    }
    
     public void updateView(Object data) {
		System.out.println("ENTROU AQUI");
    	// if(data instanceof TileRepresentation) {
    		TileRepresentation tileRep = (TileRepresentation) data;
    		if (tileRep.type == TileType.empty) {
				System.out.println("RETURN");
				return;
			}
    		else if(tileRep.type == TileType.single) {
				System.out.println("tileRep.type == TileType.single");
    			drawPawn(tileRep.pawns[0],tileRep.positionXY[0],tileRep.positionXY[1], g2d, 51.3);
    		}
    		else if(tileRep.type == TileType.twoDifferentColor) {
				System.out.println("tileRep.type == TileType.twoDifferentColor");
    			drawExitDoublePawn(tileRep.pawns[0],tileRep.pawns[1],tileRep.positionXY[0], tileRep.positionXY[1], g2d, 51.3);
    		}
    		else {
				System.out.println("tileRep.type == TileType.twoDifferentColor");
    			drawBarrier(tileRep.pawns[0],tileRep.positionXY[0],tileRep.positionXY[1],g2d, 51.3);
    		}
    		
    	// }
    }
	
	
	//getters
	public Ellipse2D.Double[] getPawns(){
		Ellipse2D.Double[] arr  = new Ellipse2D.Double[4];
		
		for (int i = 0;i < 4; i++) {
			arr[i] = pawns[i].getEllipse();
		}
			
		
		return arr;
	}
	
	public Set<Rectangle2D.Double> getTiles(){
		return allTiles;
	}

	//setters
	public void setTileRepresentations(Set<TileRepresentation> newTileRepresentations) {
		tileRepresentations = newTileRepresentations;
	}
	
//	public Color getColor(Ellipse2D pawn, Graphics2D g2d) {
//		g2d.getColor(pawn);
//	}
	
	//update
	
//	public void update(Observable observable, Object arg) {
//        if (observable instanceof Model) {
//        	
//        }
//	}
    
    //drawers
    private Ellipse2D.Double drawPawn(Color pawnColor, double posX, double posY, Graphics2D g2d, double tileSize) {
   	 	double circleSize = 25;
   	 	double panelWidth = posX;
   	 	double panelHeight = posY;
   	 	
        
        Ellipse2D.Double pawn = new Ellipse2D.Double(posX + tileSize/4, posY + tileSize/4, circleSize, circleSize);

        drawCircle(pawn,g2d, Color.BLACK, pawnColor);
        return pawn;
   }
 
    private void drawExitDoublePawn(Color exitTilePawnColor, Color otherPawnColor, double posX, double posY, Graphics2D g2d, double tileSize) {
   	 	double circleSize = 25;
   	 	double panelWidth = posX;
   	 	double panelHeight = posY;
        double largerCircleSize = circleSize*1.5;
        double smallerCircleSize = circleSize*1.2;
        
        double largerX = (panelWidth - largerCircleSize/2);
        double largerY = (panelHeight - largerCircleSize/2);
        double smallerX = (panelWidth - smallerCircleSize/2);
        double smallerY = (panelHeight - smallerCircleSize/2);
        
        Ellipse2D.Double backPawn = new Ellipse2D.Double(largerX + tileSize/2, largerY + tileSize/2,circleSize*1.5,circleSize*1.5);
        Ellipse2D.Double frontPawn = new Ellipse2D.Double(smallerX + tileSize/2, smallerY + tileSize/2,circleSize*1.2,circleSize*1.2);

        drawCircle(backPawn,g2d, Color.BLACK, exitTilePawnColor);
        drawCircle(frontPawn,g2d, otherPawnColor, otherPawnColor);
   }
    
    private void drawBarrier(Color pawnColor, double posX, double posY, Graphics2D g2d, double tileSize) {
    	 double circleSize = 25;
    	 double panelWidth = posX;
    	 double panelHeight = posY;
         double largerCircleSize = circleSize*1.5;
         double smallerCircleSize = circleSize*1.2;
         
         double largerX = (panelWidth - largerCircleSize/2.0);
         double largerY = (panelHeight - largerCircleSize/2.0);
         double smallerX = (panelWidth - smallerCircleSize/2.0);
         double smallerY = (panelHeight - smallerCircleSize/2.0);
         
         Ellipse2D.Double backPawn = new Ellipse2D.Double(largerX + tileSize/2, largerY + tileSize/2,circleSize*1.5,circleSize*1.5);
         Ellipse2D.Double frontPawn = new Ellipse2D.Double(smallerX + tileSize/2, smallerY + tileSize/2,circleSize*1.2,circleSize*1.2);

         drawCircle(backPawn,g2d, pawnColor, Color.WHITE);
         drawCircle(frontPawn,g2d, pawnColor, pawnColor);
    }
    
    private void drawEndCellsHorizontal(Graphics2D g2d, double cellSize, double posX, double posY, boolean begin, Color color) {
    	if (begin) {
    		Rectangle2D.Double first = new Rectangle2D.Double(posX, posY, cellSize, cellSize);
    		allTiles.add(first);
    		drawRectangle(first,g2d,Color.WHITE);
    		for (int i = 1; i<6;i++) {
        		Rectangle2D.Double cell = new Rectangle2D.Double(posX+i*cellSize, posY, cellSize, cellSize);
        		allTiles.add(cell);
        		drawRectangle(cell,g2d,color);
        	}
    	}
    	else {
    		for (int i = 0; i<5;i++) {
        		Rectangle2D.Double cell = new Rectangle2D.Double(posX+i*cellSize, posY, cellSize, cellSize);
        		allTiles.add(cell);
        		drawRectangle(cell,g2d,color);
        		
        	}
    		Rectangle2D.Double first = new Rectangle2D.Double(posX+5*cellSize, posY, cellSize, cellSize);
    		drawRectangle(first,g2d,Color.WHITE);
    		allTiles.add(first);

    	}
		
    	
    }
    
    private void drawCellsHorizontal(Graphics2D g2d, double cellSize, double posX, double posY, boolean begin, boolean isExit, Color color) {
    	//for below big red rect
    	for (int i = 0; i<6;i++) {
    		Rectangle2D.Double cell = new Rectangle2D.Double(posX+i*cellSize, posY, cellSize, cellSize);
    		allTiles.add(cell);

    		if (begin){
	    		if (i==1) {
	    			if(isExit) {
	    				drawRectangle(cell,g2d,color);
	    			}
	    			else {
	    				drawRectangle(cell,g2d,Color.BLACK);
	    			}
	    		}
	    		else {
	    			drawRectangle(cell,g2d,Color.WHITE);
	    		}
    		}
    		else {
    			if (i==4) {
	    			if(isExit) {
	    				drawRectangle(cell,g2d,color);
	    			}
	    			else {
	    				drawRectangle(cell,g2d,Color.BLACK);
	    			}
	    		}
    			else {
        			drawRectangle(cell,g2d,Color.WHITE);
        		}
    		}
    		
    	}
    	
    }
    
    private void drawEndCellsVertical(Graphics2D g2d, double cellSize, double posX, double posY, boolean begin, Color color) {
    	if (begin) {
    		Rectangle2D.Double first = new Rectangle2D.Double(posX, posY, cellSize, cellSize);
    		allTiles.add(first);
    		drawRectangle(first,g2d,Color.WHITE);
    		for (int i = 1; i<6;i++) {
        		Rectangle2D.Double cell = new Rectangle2D.Double(posX, posY+i*cellSize, cellSize, cellSize);
        		allTiles.add(cell);

        		drawRectangle(cell,g2d,color);
        	}
    	}
    	else {
    		for (int i = 0; i<5;i++) {
        		Rectangle2D.Double cell = new Rectangle2D.Double(posX, posY+i*cellSize, cellSize, cellSize);
        		allTiles.add(cell);

        		drawRectangle(cell,g2d,color);
        	}
    		Rectangle2D.Double first = new Rectangle2D.Double(posX, posY+5*cellSize, cellSize, cellSize);
    		allTiles.add(first);

    		drawRectangle(first,g2d,Color.WHITE);
    	}
		
    	
    }
 
    private void drawCellsVertical(Graphics2D g2d, double cellSize, double posX, double posY, boolean begin, boolean isExit, Color color) {
    	//for below big red rect
    	for (int i = 0; i<6;i++) {
    		Rectangle2D.Double cell = new Rectangle2D.Double(posX, posY+i*cellSize, cellSize, cellSize);
    		allTiles.add(cell);

    		if (begin){
	    		if (i==1) {
	    			if(isExit) {
	    				drawRectangle(cell,g2d,color);
	    			}
	    			else {
	    				drawRectangle(cell,g2d,Color.BLACK);
	    			}
	    		}
	    		else {
	    			drawRectangle(cell,g2d,Color.WHITE);
	    		}
    		}
    		else {
    			if (i==4) {
	    			if(isExit) {
	    				drawRectangle(cell,g2d,color);
	    			}
	    			else {
	    				drawRectangle(cell,g2d,Color.BLACK);
	    			}
	    		}
    			else {
        			drawRectangle(cell,g2d,Color.WHITE);
        		}
    		}
    		
    	}
    	
    }
        
    private void drawRectangle(Rectangle2D.Double rect, Graphics2D g2d, Color color) {
         g2d.setColor(Color.BLACK);
         g2d.setStroke(new BasicStroke(3));
         g2d.draw(rect);
         g2d.setColor(color);
         g2d.fill(rect);
         
    }
    
    private void drawTriangle(double xPoints[],double yPoints[], Graphics2D g2d,Color color) {
    	GeneralPath triangle = new GeneralPath();
        triangle.moveTo(xPoints[0], yPoints[0]);

        // Connect the remaining vertices
        for (int i = 1; i < 3; i++) {
        	triangle.lineTo(xPoints[i], yPoints[i]);
        }

        // Close the path to create a closed polygon
        triangle.closePath();

        // Set the colors
        g2d.setColor(Color.BLACK); // Outline color
        g2d.draw(triangle); // Draw the outline of the polygon
        g2d.setColor(color); // Fill color
        g2d.fill(triangle); // Fill the polygon with color
       
    }
    
    private void drawCircles(Rectangle2D.Double rect, Graphics2D g2d, double circleSize) {
    	 Ellipse2D.Double circle1 = new Ellipse2D.Double(rect.getMinX()+rect.getWidth()*0.25 - circleSize,rect.getMinY()+rect.getWidth()*0.25 - circleSize,circleSize*2,circleSize*2);
    	 drawCircle(circle1, g2d, Color.BLACK, Color.WHITE);
         
         Ellipse2D.Double circle2 = new Ellipse2D.Double(rect.getMinX()+rect.getWidth()*0.75 - circleSize,rect.getMinY()+rect.getWidth()*0.25 - circleSize,circleSize*2,circleSize*2);
    	 drawCircle(circle2, g2d,Color.BLACK, Color.WHITE);

         
         Ellipse2D.Double circle3 = new Ellipse2D.Double(rect.getMinX()+rect.getWidth()*0.25 - circleSize,rect.getMinY()+rect.getWidth()*0.75 - circleSize,circleSize*2,circleSize*2);
    	 drawCircle(circle3, g2d, Color.BLACK,Color.WHITE);

         
         Ellipse2D.Double circle4 = new Ellipse2D.Double(rect.getMinX()+rect.getWidth()*0.75 - circleSize,rect.getMinY()+rect.getWidth()*0.75 - circleSize,circleSize*2,circleSize*2);
    	 drawCircle(circle4, g2d, Color.BLACK,Color.WHITE);
    	
    }
    
    private void drawCircle(Ellipse2D.Double circ, Graphics2D g2d, Color outlineColor, Color fillColor) {
    	g2d.setColor(outlineColor);
        g2d.draw(circ);
        g2d.setColor(fillColor);
        g2d.fill(circ);
    }

	private void drawAllTiles() {
		double x, y;
		Color[] color;
		if(tileRepresentations == null) { 
			return; 
		}
		for(TileRepresentation representation : tileRepresentations) {
			if(representation == null) { continue; }
			x = representation.getX();
			y = representation.getY();
			color = representation.getColor();
			switch (representation.getType()) {
				case twoSameColor:
				drawBarrier(color[0], x, y, g2d, 51.3);
				break;
				case twoDifferentColor:
				drawExitDoublePawn(color[0], color[1], x, y, g2d, 51.3);
				break;
				case single:
				drawPawn(color[0], x, y, g2d, 51.3);
				break;
				default:
				continue;
			} 
		}
	}
    
    
    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(() -> {
    //         JFrame frame = new JFrame("Ludo Board");
    //         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //         frame.add(new BoardPanel());
    //         frame.setSize(770, 800);
    //         frame.setVisible(true);
    //     });
    // }
}



