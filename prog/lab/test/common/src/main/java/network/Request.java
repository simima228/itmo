package network;

import java.io.Serializable;

public record Request(String commandName, Object arguments) implements Serializable {
}