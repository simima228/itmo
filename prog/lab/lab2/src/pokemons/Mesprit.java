package pokemons;


import attacks.EnergyBall;
import attacks.Extrasensory;
import attacks.Swift;
import attacks.Thunder;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;


final public class Mesprit extends Pokemon{
    public Mesprit(String name, int lvl) {
        super(name, lvl);
        setStats(80.0, 105.0, 105.0, 105.0, 105.0, 80.0);
        setType(Type.PSYCHIC);
        setMove(new Swift(), new Extrasensory(), new Thunder(), new EnergyBall());
    }
}
