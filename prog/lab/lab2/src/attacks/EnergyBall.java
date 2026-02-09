package attacks;

import ru.ifmo.se.pokemon.*;

final public class EnergyBall extends SpecialMove {
    public EnergyBall(){
        super(Type.GRASS, 90, 100);

    }
    private boolean isLowered = false;
    @Override
    public void applyOppEffects(Pokemon def) {
        if (Math.random() <= 0.1){
            Effect.flinch(def);
            def.setMod(Stat.SPECIAL_DEFENSE, -1);
            isLowered = true;
        }
    }
    @Override
    protected String describe(){
        return "использует Energy Ball" + ((isLowered) ? " и снижает специальную защиту" : "");
    }
}