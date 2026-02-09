import ru.ifmo.se.pokemon.*;
import pokemons.*;

public class Main {
    public static void main(String[] args) {
        Battle b = new Battle();
        Mesprit p1 = new Mesprit("Mesprit",35);
        Psyduck p2 = new Psyduck("Psyduck", 1);
        Golduck p3 = new Golduck("Golduck",33);
        Trapinch p4 = new Trapinch("Trapinch", 1);
        Vibrava p6 = new Vibrava("Vibrava", 35);
        Flygon p5 = new Flygon("Flygon",45);

        b.addAlly(p1);
        b.addAlly(p2);
        b.addAlly(p3);
        b.addFoe(p4);
        b.addFoe(p5);
        b.addFoe(p6);
        b.go();
    }
}