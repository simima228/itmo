package attacks;

import ru.ifmo.se.pokemon.*;

final public class Swift extends PhysicalMove {
    public Swift(){
        super(Type.NORMAL, 60, 100);
    }
    @Override
    protected boolean checkAccuracy(Pokemon pokemon, Pokemon pokemon1) {
        return true;
    }
    @Override
    protected String describe(){
        return "использует Swift";
    }
}
