package commands;


import etc.DataClass;
import network.Response;

import java.io.Serializable;

abstract public class BaseCommand {
    private final String name;
    private final String description;
    private final String infoName;
    private final DataClass dataClass;

    public BaseCommand(String name, String infoName, String description, DataClass dataClass) {
        this.name = name;
        this.description = description;
        this.infoName = infoName;
        this.dataClass = dataClass;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getInfoName() {
        return infoName;
    }

    public DataClass getDataClass() {
        return dataClass;
    }

    public abstract Response execute(Object arguments);

}
