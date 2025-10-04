package pokemons;

import attacks.Confide;
import attacks.MudSlap;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;


public class Trapinch extends Pokemon{
    public Trapinch(String name, int lvl) {
        super(name, lvl);
        setStats(45.0, 100.0, 45.0, 45.0, 45.0, 10.0);
        setType(Type.GROUND);
        setMove(new Confide(), new MudSlap());
    }
}