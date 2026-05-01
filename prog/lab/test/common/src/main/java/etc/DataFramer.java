package etc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;
import java.util.Map;

public class DataFramer {
    private final Map<Integer, byte[]> frames = new HashMap<>();

    private static byte[] framing(long id, byte[] data, int frameSize, int totalFrames, int i) throws IOException {
        int bound = i * frameSize;
        int len = Math.min(frameSize, data.length - bound);
        byte[] frame = new byte[len];
        System.arraycopy(data, bound, frame, 0, len);
        return DataConverter.serialize(new Frame(id, i, totalFrames, frame));
    }

    public void sendFramedSocket(DatagramSocket socket, InetAddress address, int port, long id, byte[] data, int frameSize) throws IOException {
        int totalFrames = (int) Math.ceil((double) data.length / frameSize);
        for (int i = 0; i < totalFrames; i++) {
            byte[] sendFrame = framing(id, data, frameSize, totalFrames, i);
            DatagramPacket packet = new DatagramPacket(sendFrame, sendFrame.length, address, port);
            socket.send(packet);
        }
    }

    public void sendFramedChannel(DatagramChannel channel, SocketAddress address, long id, byte[] data, int frameSize) throws IOException {
        int totalFrames = (int) Math.ceil((double) data.length / frameSize);
        for (int i = 0; i < totalFrames; i++) {
            byte[] sendFrame = framing(id, data, frameSize, totalFrames, i);
            channel.send(ByteBuffer.wrap(sendFrame), address);
        }
    }

    public byte[] receiveFramed(DatagramSocket socket, long expectedId, int timeout) throws IOException, ClassNotFoundException {
        int originalTimeout = socket.getSoTimeout();
        socket.setSoTimeout(timeout);
        try {
            int totalFrames = -1;
            while (true) {
                byte[] buffer = new byte[65535];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                try {
                    socket.receive(packet);
                } catch (SocketTimeoutException e) {
                    return null;
                }
                Frame frame = (Frame) DataConverter.deserialize(packet.getData());
                if (frame.id() != expectedId) { continue;}
                frames.put(frame.frameIndex(), frame.data());
                if (totalFrames == -1) {
                    totalFrames = frame.totalFrames();}
                if (frames.size() == totalFrames) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    for (int i = 0; i < totalFrames; i++) baos.write(frames.get(i));
                    frames.clear();
                    return baos.toByteArray();
                }
            }
        } finally {
            socket.setSoTimeout(originalTimeout);
        }
    }
}