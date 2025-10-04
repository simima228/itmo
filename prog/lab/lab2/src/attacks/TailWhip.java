package attacks;

import ru.ifmo.se.pokemon.*;

public class TailWhip extends StatusMove {
    public TailWhip(){
        super(Type.NORMAL, 0, 0);
    }

    @Override
    public void applyOppEffects(Pokemon def) {
        def.setMod(Stat.DEFENSE, -1);
    }
    @Override
    protected String describe(){
        return "использует Tail Whip";
    }
}
