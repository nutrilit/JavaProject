package com.mygdx.game;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BufferedReader {
    List<Integer> optionList = new ArrayList<>();

    // Konstruktor przyjmujący ścieżkę do pliku
    public BufferedReader(String filePath) {
        try {
            FileReader fileReader = new FileReader(filePath);
            java.io.BufferedReader bufferedReader = new java.io.BufferedReader(fileReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                // Konwertuj odczytaną linię na liczbę całkowitą i dodaj do listy
                int number = Integer.parseInt(line);
                optionList.add(number);
            }

            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("Błąd podczas odczytu pliku.");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Błąd podczas konwersji linii na liczbę całkowitą.");
            e.printStackTrace();
        }

        // Wyświetlenie odczytanych liczb całkowitych
        for (int number : optionList) {
            System.out.println(number);
        }
    }
}
