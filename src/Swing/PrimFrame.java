package Swing;

import java.util.*;

import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;

public class PrimFrame extends JFrame {

	private BoardPanel boardPanel;
	private JPanel menuPanel;
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JButton button4;
	public DicePanel dicePanel;

	

	public PrimFrame() {
		setTitle("LUDAO");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200, 900);
		setLayout(new BorderLayout());

		boardPanel = new BoardPanel();
		boardPanel.setPreferredSize(new Dimension(770, 800));

		// Configuração do painel do menu
		menuPanel = new JPanel();
		menuPanel.setBackground(Color.WHITE);
		menuPanel.setPreferredSize(new Dimension(230, 800));
		menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

		// Create a nested panel for each button
		JPanel buttonPanel1 = new JPanel();
		JPanel buttonPanel2 = new JPanel();
		JPanel buttonPanel3 = new JPanel();
		JPanel buttonPanel4 = new JPanel();
		dicePanel = new DicePanel();
		dicePanel.setPreferredSize(new Dimension(50, 50));

		// Set layout manager for each button panel
		buttonPanel1.setLayout(new BorderLayout());
		buttonPanel2.setLayout(new BorderLayout());
		buttonPanel3.setLayout(new BorderLayout());
		buttonPanel4.setLayout(new BorderLayout());
		dicePanel.setLayout(new BorderLayout());

		// Configuração dos botões
		button1 = new JButton("Novo Jogo");
		button2 = new JButton("Carregar Jogo");
		button3 = new JButton("Salvar");
		button4 = new JButton("Lançar dado");

		int spacingY = 30;
		int spacingX = 50;

		buttonPanel1.setBorder(BorderFactory.createEmptyBorder(spacingY, spacingX, spacingY, spacingX));
		buttonPanel2.setBorder(BorderFactory.createEmptyBorder(spacingY, spacingX, spacingY, spacingX));
		buttonPanel3.setBorder(BorderFactory.createEmptyBorder(spacingY, spacingX, spacingY, spacingX));
		buttonPanel4.setBorder(BorderFactory.createEmptyBorder(spacingY, spacingX, spacingY, spacingX));
		dicePanel.setBorder(BorderFactory.createEmptyBorder(spacingY, spacingX, spacingY, spacingX));

		// Center the buttons vertically
		button1.setAlignmentX(Component.CENTER_ALIGNMENT);
		button2.setAlignmentX(Component.CENTER_ALIGNMENT);
		button3.setAlignmentX(Component.CENTER_ALIGNMENT);
		button4.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Add the buttons to the nested button panels
		buttonPanel1.add(button1, BorderLayout.CENTER);
		buttonPanel2.add(button2, BorderLayout.CENTER);
		buttonPanel3.add(button3, BorderLayout.CENTER);
		buttonPanel4.add(button4, BorderLayout.CENTER);

		// Add the button panels to the menu panel
		menuPanel.add(buttonPanel1);
		menuPanel.add(buttonPanel2);
		menuPanel.add(buttonPanel3);
		menuPanel.add(buttonPanel4);
		menuPanel.add(dicePanel);

		add(menuPanel, BorderLayout.EAST);
		add(boardPanel, BorderLayout.WEST);

	}
	
	public void addDiceListener(ActionListener list) {
		button4.addActionListener(list);
	}
	
	public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    public JButton getSaveButton() {
		return button3;
    }
	
    public JButton getLoadButton() {
		return button2;
	}

	public JButton getNewGameButton() {
		return button1;
	}

//	public static void main(String[] args) {
//		PrimFrame gui = new PrimFrame();
//		gui.setVisible(true);
//		gui.setSize(1000, 800);
//
//	}
}
