package attacks;

import pokemons.Flygon;
import pokemons.Mesprit;
import pokemons.Vibrava;
import ru.ifmo.se.pokemon.*;

public class MudSlap extends SpecialMove {
    public MudSlap(){
        super(Type.GROUND, 20, 100);

    }
    @Override
    public void applyOppEffects(Pokemon def) {
        if (!(def.getClass() == Mesprit.class) | (def.getClass() == Flygon.class) | (def.getClass() == Vibrava.class)) {
            def.setMod(Stat.ACCURACY, -1);
        }
    }
    @Override
    protected boolean checkAccuracy(Pokemon att, Pokemon def) {
        if ((def.getClass() == Mesprit.class) | (def.getClass() == Flygon.class) | (def.getClass() == Vibrava.class)) {
            return false;
        }
        return (accuracy * att.getStat(Stat.ACCURACY) / def.getStat(Stat.EVASION)) > Math.random();
    }

    @Override
    protected String describe(){
        return "использует Mud-Slap и снижает Accuracy на уровень";
    }
}