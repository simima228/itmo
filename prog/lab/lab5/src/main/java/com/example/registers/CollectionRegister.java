package com.example.registers;
import com.example.models.Movie;

import java.util.Stack;

public class CollectionRegister {
    private int id = 0;
    private Stack<Movie> stack = new Stack<>();

    public Stack<Movie> getStack(){
        return stack;
    }

    public void push(Movie movie) {
        stack.push(movie);
    }

    public int getNewId() {
        this.id = id + 1;
        return id;
    }

}
