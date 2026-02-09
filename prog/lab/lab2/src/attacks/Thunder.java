package attacks;

import ru.ifmo.se.pokemon.*;

public class Thunder extends SpecialMove {
    public Thunder(){
        super(Type.ELECTRIC, 110, 70);

    }
    private boolean isParalyzed = false;
    @Override
    public void applyOppEffects(Pokemon def) {
        if (Math.random() <= 0.3){
            Effect.flinch(def);
            isParalyzed = true;
        }
    }
    @Override
    protected String describe(){
        return "использует Thunder" + ((isParalyzed) ? " и парализует" : "");
    }
}