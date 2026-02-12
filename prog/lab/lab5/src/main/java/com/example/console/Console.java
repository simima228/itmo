package com.example.console;

import com.example.etc.ConsoleInterface;

import java.util.Scanner;

public class Console implements ConsoleInterface {
    Scanner scanner;

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
        return scanner.nextLine();
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setDefaultScanner(){
        scanner = new Scanner(System.in);
    }
}
