package com.example.console;

import com.example.etc.ConsoleInterface;

import java.util.ArrayList;
import java.util.Scanner;

public class Console implements ConsoleInterface {
    ArrayList<Scanner> scanners = new ArrayList<>();

    @Override
    public void println(Object obj) {
        System.out.println(obj);
    }

    @Override
    public void print(Object obj) {
        System.out.print(obj);
    }

    @Override
    public String read() {
        return scanners.get(scanners.size() - 1).nextLine();
    }

    public void addScanner(Scanner scanner) {
        this.scanners.add(scanner);
    }

    public Scanner getScanner() {
        return scanners.get(scanners.size() - 1);
    }

    public Scanner removeScanner() {
        return scanners.remove(scanners.size() - 1);
    }

    public void initializeScanner() {
        scanners.add(new Scanner(System.in));
    }
}
