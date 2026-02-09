package com.example.registers;
import java.util.Stack;

public class CollectionRegister {
    private Stack<Object> stack = new Stack<>();

    public Stack<Object> getStack(){
        return stack;
    }

    public void push(Object obj){
        stack.push(obj);
    }
}
