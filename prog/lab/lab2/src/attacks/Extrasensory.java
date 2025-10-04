package attacks;

import ru.ifmo.se.pokemon.*;

public class Extrasensory extends SpecialMove {
    public Extrasensory(){
        super(Type.PSYCHIC, 80, 100);

    }
    private boolean isFlinched = false;
    @Override
    public void applyOppEffects(Pokemon def) {
        if (Math.random() <= 0.1){
            Effect.flinch(def);
            isFlinched = true;
        }
    }
    @Override
    protected String describe(){
        return "использует Extrasensory " + ((isFlinched) ? " и накладывает страх на" : "");
    }
}