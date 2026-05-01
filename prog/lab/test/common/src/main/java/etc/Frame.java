package etc;

import java.io.Serializable;

public record Frame(long id, int frameIndex, int totalFrames, byte[] data) implements Serializable {
}