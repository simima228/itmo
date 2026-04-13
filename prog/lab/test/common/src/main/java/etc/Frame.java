package etc;

import java.io.Serializable;

public class Frame implements Serializable {
    private final long id;
    private final int frameIndex;
    private final int totalFrames;
    private final byte[] data;

    public Frame(long id, int frameIndex, int totalFrames, byte[] data) {
        this.id = id;
        this.frameIndex = frameIndex;
        this.totalFrames = totalFrames;
        this.data = data;
    }

    public long getId() { return id; }
    public int getFrameIndex() { return frameIndex; }
    public int getTotalFrames() { return totalFrames; }
    public byte[] getData() { return data; }
}