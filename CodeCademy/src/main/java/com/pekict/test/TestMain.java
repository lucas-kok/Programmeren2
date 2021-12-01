
package com.pekict.test;

import java.util.Scanner;

import javax.sound.sampled.SourceDataLine;

public class TestMain {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Gabagoo Maker 2021:");
        
        while (true) {
            System.out.print("Word:");
            String word = scanner.nextLine();
            System.out.println("Gaba" + word);

            if (word.isEmpty()) {
                break;
            }
        }

    }
}
