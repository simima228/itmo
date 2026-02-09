package attacks;

import ru.ifmo.se.pokemon.*;

final public class BugBuzz extends SpecialMove {
    public BugBuzz(){
        super(Type.BUG, 90, 100);
    }

    private boolean isLowered = false;
    @Override
    public void applyOppEffects(Pokemon p) {
        if (Math.random() <= 0.1){
            p.setMod(Stat.SPECIAL_DEFENSE, -1);
            isLowered = true;
        }
    }

    @Override
    protected String describe(){
        return "использует Bug Buzz" + ((isLowered) ? " и снижает специальную защиту" : "");
    }
}