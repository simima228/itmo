package core;

import model.Movie;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Stack;
import java.util.stream.Collectors;

public class CollectionRegister {
    private final LocalDate initialDate;
    private LocalDate changeDate;
    private final Stack<Movie> stack;
    private int id;

    public CollectionRegister(ArrayList<Movie> movies) {
        this.initialDate = LocalDate.now();
        this.changeDate = null;
        this.stack = movies.stream()
                .collect(Collectors.toCollection(Stack::new));
        setNewId();
    }

    public Stack<Movie> getStack() {
        return stack;
    }

    public void setNewStack(Stack<Movie> stack) {
        this.stack.clear();
        this.stack.addAll(stack);
        setChangeDate(LocalDate.now());
    }

    public void setNewId() {
        this.id = stack.stream()
                .mapToInt(Movie::getId)
                .max()
                .orElse(0);
    }

    public int getNewId() {
        return ++id;
    }

    public void reduceId() {
        id--;
    }

    public void push(Movie movie) {
        stack.push(movie);
        setChangeDate(LocalDate.now());
    }

    @Override
    public String toString() {
        return stack.getClass().toString();
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
        if (stack.isEmpty()) {
            return "В коллекции нет элементов!";
        }

        return stack.stream()
                .map(Movie::toString)
                .collect(Collectors.joining("\n"));
    }

    public void setStack(int index, Movie movie) {
        stack.add(index, movie);
        setChangeDate(LocalDate.now());
    }

    public void delete(int index) {
        stack.remove(index);
        setChangeDate(LocalDate.now());
    }

    public int getIndex(int id) {
        return stack.stream()
                .filter(movie -> movie.getId() == id)
                .map(stack::indexOf)
                .findFirst()
                .orElse(-1);
    }

    public int getLength() {
        return stack.size();
    }

    public void clear() {
        stack.clear();
        setChangeDate(LocalDate.now());
    }

    public void sort() {
        stack.sort(Comparator.naturalOrder());
        setChangeDate(LocalDate.now());
    }

    public Stack<Movie> reverseSort() {
        return stack.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toCollection(Stack::new));
    }
}