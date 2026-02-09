package attacks;

import ru.ifmo.se.pokemon.*;

final public class AquaJet extends PhysicalMove {
    public AquaJet(){
        super(Type.WATER,40, 100, 1, 1);
    }

    @Override
    protected String describe(){
        return "использует AquaJet";
    }
}