package attacks;

import ru.ifmo.se.pokemon.*;

public class FeintAttack extends PhysicalMove {
    public FeintAttack(){
        super(Type.DARK, 60, 100);
    }
    @Override
    protected boolean checkAccuracy(Pokemon pokemon, Pokemon pokemon1) {
        return true;
    }
    @Override
    protected String describe(){
        return "использует Feint Attack";
    }
}
