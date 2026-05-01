package network;

import java.io.Serializable;

public record Request(String commandName, Object arguments, String login, String password) implements Serializable {
}