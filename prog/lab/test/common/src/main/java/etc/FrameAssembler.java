package etc;

import java.net.SocketAddress;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class FrameAssembler {
    private static class State {
        byte[][] frames;
        int receivedCount;
        long lastUpdate;

        synchronized void addFrame(int index, byte[] data) {
            if (frames[index] == null) {
                frames[index] = data;
                receivedCount++;
            }
        }

        synchronized boolean isComplete() {
            return receivedCount == frames.length;
        }

        synchronized byte[] assemble() {
            int totalSize = 0;
            for (byte[] fram : frames) totalSize += fram.length;
            byte[] full = new byte[totalSize];
            int bound = 0;
            for (byte[] fram : frames) {
                System.arraycopy(fram, 0, full, bound, fram.length);
                bound += fram.length;
            }
            return full;
        }
    }

    private final Map<SocketAddress, Map<Long, State>> clients = new ConcurrentHashMap<>();
    private final long timeout;

    public FrameAssembler(long timeout) {
        this.timeout = timeout;
    }

    public byte[] addFrame(Frame frame, SocketAddress address) {
        cleanFrames();

        Map<Long, State> client = clients.computeIfAbsent(address,
                k -> new ConcurrentHashMap<>());

        State state = client.computeIfAbsent(frame.id(), k -> {
            State s = new State();
            s.frames = new byte[frame.totalFrames()][];
            s.receivedCount = 0;
            return s;
        });

        state.lastUpdate = System.currentTimeMillis();

        state.addFrame(frame.frameIndex(), frame.data());

        if (state.isComplete()) {
            byte[] full = state.assemble();
            client.remove(frame.id());
            if (client.isEmpty()) {
                clients.remove(address);
            }
            return full;
        }

        return null;
    }

    private void cleanFrames() {
        long now = System.currentTimeMillis();
        for (Map.Entry<SocketAddress, Map<Long, State>> entry : clients.entrySet()) {
            Map<Long, State> client = entry.getValue();
            client.entrySet().removeIf(e -> (now - e.getValue().lastUpdate > timeout));
            if (client.isEmpty()) {
                clients.remove(entry.getKey());
            }
        }
    }
}