package etc;

import commands.*;
import core.CollectionRegister;
import core.CommandRegister;
import core.HistoryRegister;


public class InitCommandRegister {
    private final CollectionRegister collectionRegister;
    private final HistoryRegister historyRegister;
    private final CommandRegister commandRegister;

    public InitCommandRegister(CollectionRegister collectionRegister, HistoryRegister historyRegister,
                               CommandRegister commandRegister) {
        this.collectionRegister = collectionRegister;
        this.historyRegister = historyRegister;
        this.commandRegister = commandRegister;
    }

    public void initialize() {
        commandRegister.register(new Help(commandRegister));
        commandRegister.register(new Info(collectionRegister));
        commandRegister.register(new Show(collectionRegister));
        commandRegister.register(new Add(collectionRegister));
        commandRegister.register(new UpdateId(collectionRegister));
        commandRegister.register(new RemoveById(collectionRegister));
        commandRegister.register(new Clear(collectionRegister));
        commandRegister.register(new ExecuteScript());
        commandRegister.register(new Exit());
        commandRegister.register(new InsertAt(collectionRegister));
        commandRegister.register(new Sort(collectionRegister));
        commandRegister.register(new History(historyRegister));
        commandRegister.register(new AverageOfTotalBoxOffice(collectionRegister));
        commandRegister.register(new CountGreaterThanOscarsCount(collectionRegister));
        commandRegister.register(new PrintDescending(collectionRegister));
        
    }
}
