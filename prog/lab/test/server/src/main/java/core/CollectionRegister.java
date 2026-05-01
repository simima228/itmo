package core;

import model.Movie;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.concurrent.locks.*;

public class CollectionRegister {
    private final LocalDate initialDate;
    private LocalDate changeDate;
    private final Stack<Movie> stack;

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    public CollectionRegister(ArrayList<Movie> movies) {
        this.initialDate = LocalDate.now();
        this.changeDate = null;
        this.stack = movies.stream()
                .collect(Collectors.toCollection(Stack::new));
    }

    public Stack<Movie> getStack() {
        readLock.lock();
        try {
            return new Stack<>() {{ addAll(stack); }};
        } finally {
            readLock.unlock();
        }
    }

    public void push(Movie movie) {
        writeLock.lock();
        try {
            stack.push(movie);
            setChangeDate(LocalDate.now());
        } finally {
            writeLock.unlock();
        }
    }

    public LocalDate getInitialDate() {
        readLock.lock();
        try {
            return initialDate;
        } finally {
            readLock.unlock();
        }
    }

    public LocalDate getChangeDate() {
        readLock.lock();
        try {
            return changeDate;
        } finally {
            readLock.unlock();
        }
    }

    public void setChangeDate(LocalDate changeDate) {
        this.changeDate = changeDate;
    }

    public String getInformation(Stack<Movie> stack) {
        readLock.lock();
        try {
            if (stack.isEmpty()) {
                return "В коллекции нет элементов!";
            }
            return stack.stream()
                    .map(Movie::toString)
                    .collect(Collectors.joining("\n"));
        } finally {
            readLock.unlock();
        }
    }

    public void add(int index, Movie movie) {
        writeLock.lock();
        try {
            stack.add(index, movie);
        } finally {
            writeLock.unlock();
        }
    }

    public void setStack(int index, Movie movie) {
        writeLock.lock();
        try {
            stack.set(index, movie);
            setChangeDate(LocalDate.now());
        } finally {
            writeLock.unlock();
        }
    }

    public void delete(int index) {
        writeLock.lock();
        try {
            stack.remove(index);
            setChangeDate(LocalDate.now());
        } finally {
            writeLock.unlock();
        }
    }

    public int getIndex(int id) {
        readLock.lock();
        System.out.println(stack.size());
        System.out.println(stack);
        try {
            for (int i = 0; i < stack.size(); i++) {
                if (stack.get(i).getId() == id) {
                    return i;
                }
            }
            return -1;
        } finally {
            readLock.unlock();
        }
    }

    public int getLength() {
        readLock.lock();
        try {
            return stack.size();
        } finally {
            readLock.unlock();
        }
    }

    public void clear(String owner) {
        writeLock.lock();
        try {
            stack.removeIf(movie -> movie.getOwner().equals(owner));
            setChangeDate(LocalDate.now());
        } finally {
            writeLock.unlock();
        }
    }

    public void sort() {
        writeLock.lock();
        try {
            stack.sort(Comparator.naturalOrder());
            setChangeDate(LocalDate.now());
        } finally {
            writeLock.unlock();
        }
    }

    public Stack<Movie> reverseSort() {
        readLock.lock();
        try {
            return stack.stream()
                    .sorted(Comparator.reverseOrder())
                    .collect(Collectors.toCollection(Stack::new));
        } finally {
            readLock.unlock();
        }
    }
}