package com.example.registers;
import com.example.models.Movie;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class CollectionRegister {
    private final LocalDate initialDate;
    private LocalDate changeDate;
    private final Stack<Movie> stack;
    private int id;

    public CollectionRegister(ArrayList<Movie> movies) {
        this.initialDate = LocalDate.now();
        this.changeDate = null;
        this.stack = new Stack<>();
        for (Movie movie : movies) {
            this.stack.push(movie);
        }
    }



    public Stack<Movie> getStack(){
        return stack;
    }

    public void setNewId(){
        if (this.stack.isEmpty()){
            this.id = 0;
        }
        else {
            int max = 0;
            for (Movie movie : this.stack) {
                if (movie.getId() > max){
                    max = movie.getId();
                }
            }
            this.id = max;
        }
    }

    public int getNewId(){
        id++;
        return id;
    }

    public void reduceId(){
        id--;
    }

    public void push(Movie movie) {
        stack.push(movie);
        setChangeDate(LocalDate.now());
    }

    public String toString() {
        return getStack().getClass().toString();
    }

    public LocalDate getInitialDate() {
        return initialDate;
    }

    public LocalDate getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(LocalDate changeDate) {
        this.changeDate = changeDate;
    }

    public String getInformation(Stack<Movie> stack) {
        if (stack.isEmpty()){
            return "В коллекции нет элементов!";
        }

        StringBuilder sb = new StringBuilder();
        for (Movie movie : stack) {
            sb.append(movie).append("\n");
        }

        return sb.substring(0, sb.length() - 1);
    }
    public void setStack(int index, Movie movie) {
        stack.add(index, movie);
    }
    public void delete(int index) {
        stack.remove(index);
    }

    public int getIndex(int id){
        int index = -1;

        for (Movie movie : stack) {
            if (movie.getId() == id) {
                index = stack.indexOf(movie);
                break;
            }
        }

        return index;
    }

    public int getLength(){
        return stack.size();
    }

    public void clear(){
        stack.clear();
    }

    public void sort(){
        Collections.sort(stack);
    }

    public Stack<Movie> reverseSort(){
        Stack<Movie> reverseStack = new Stack<>();
        reverseStack.addAll(stack);
        Collections.reverse(reverseStack);

        return reverseStack;
    }

}
