package etc;

import java.net.SocketAddress;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class FrameAssembler {
    private static class State {
        byte[][] frames;
        int receivedCount;
        long lastUpdate;
    }

    private final Map<SocketAddress, Map<Long, State>> clients = new ConcurrentHashMap<>();
    private final long timeout;

    public FrameAssembler(long timeout) {
        this.timeout = timeout;
    }

    public byte[] addFrame(Frame frame, SocketAddress address) {
        cleanFrames();
        Map<Long, State> client = clients.computeIfAbsent(address, k -> new ConcurrentHashMap<>());
        State state = client.get(frame.getId());
        if (state == null) {
            state = new State();
            state.frames = new byte[frame.getTotalFrames()][];
            state.receivedCount = 0;
            client.put(frame.getId(), state);
        }
        state.lastUpdate = System.currentTimeMillis();
        if (state.frames[frame.getFrameIndex()] == null) {
            state.frames[frame.getFrameIndex()] = frame.getData();
            state.receivedCount++;
            clients.put(address, client);
        }
        if (state.receivedCount == state.frames.length) {
            int totalSize = 0;
            for (byte[] fram : state.frames) totalSize += fram.length;
            byte[] full = new byte[totalSize];
            int bound = 0;
            for (byte[] fram : state.frames) {
                System.arraycopy(fram, 0, full, bound, fram.length);
                bound += fram.length;
            }
            client.remove(frame.getId());
            if (client.isEmpty())
            {
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
            client.entrySet().removeIf(e -> now - e.getValue().lastUpdate > timeout);
            if (client.isEmpty()){
                clients.remove(entry.getKey());
            }
        }
    }
}