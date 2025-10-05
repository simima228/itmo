package pokemons;

import attacks.AquaJet;
import ru.ifmo.se.pokemon.Type;


final public class Golduck extends Psyduck{
    public Golduck(String name, int lvl) {
        super(name, lvl);
        setStats(80.0, 82.0, 78.0, 95.0, 80.0, 85.0);
        setType(Type.WATER);
        addMove(new AquaJet());
    }

}
