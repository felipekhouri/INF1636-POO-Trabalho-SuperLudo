package Swing;

import java.io.*;

import java.io.IOException;

public class FileRW {

    public static void main(String[] args) {
        try {
            File file = new File("arquivo.txt");
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write("Jogador 1: Azul");
            bufferedWriter.newLine();
            bufferedWriter.write("Posicao piao 1: 30");
            bufferedWriter.newLine();
            bufferedWriter.write("Posicao piao 2: 40");
            bufferedWriter.newLine();
            bufferedWriter.write("Posicao piao 3: 20");
            bufferedWriter.newLine();
            bufferedWriter.write("Posicao piao 4: 10");
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.write("Jogador 2: Vermelho");
            bufferedWriter.newLine();
            bufferedWriter.write("Posicao piao 1: 30");
            bufferedWriter.newLine();
            bufferedWriter.write("Posicao piao 2: 40");
            bufferedWriter.newLine();
            bufferedWriter.write("Posicao piao 3: 20");
            bufferedWriter.newLine();
            bufferedWriter.write("Posicao piao 4: 0");
            bufferedWriter.newLine();
            bufferedWriter.newLine();




            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        try {
            File file = new File("arquivo.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}