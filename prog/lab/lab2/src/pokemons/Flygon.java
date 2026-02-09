package pokemons;

import attacks.FeintAttack;


final public class Flygon extends Vibrava{
    public Flygon(String name, int lvl) {
        super(name, lvl);
        setStats(80.0, 100.0, 80.0, 80.0, 80.0, 100.0);
        addMove(new FeintAttack());
    }
}