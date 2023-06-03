package Swing;


public class Main {

	public static void main(String[] args) {
		PrimFrame f=new PrimFrame(); 
		Model model = new Model();
		Controller controller = new Controller(f,model);
    	System.out.println("Tapped");

		f.setTitle("Minha Primeira GUI"); 
		f.setVisible(true);
		f.setSize(1000, 800);
//		Tile t = new Tile();
		
	} 
}
