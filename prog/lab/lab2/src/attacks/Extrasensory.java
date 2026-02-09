package attacks;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Type;


final public class Extrasensory extends SpecialMove {
    public Extrasensory(){
        super(Type.PSYCHIC, 80, 100);

    }
    private boolean isFlinched = false;
    @Override
    public void applyOppEffects(Pokemon def) {
        final double CHANCE_PERCENT = 10;
        if (Math.random() <= CHANCE_PERCENT/100){
            Effect.flinch(def);
            isFlinched = true;
        }
    }
    @Override
    protected String describe(){
        return "использует Extrasensory" + ((isFlinched) ? " и накладывает страх" : "");
    }
}