package network;

import java.io.Serializable;

public record Response(boolean success, String message) implements Serializable {
}
