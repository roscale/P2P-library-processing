package P2P;

import processing.core.PApplet;
import processing.net.Client;
import processing.net.Server;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by roscale on 2/28/17.
 */
public class Connection {

    private Server s;
    private Client c;

    private ConnectionType type = ConnectionType.UNDEFINED;

    // Create Server
    public Connection(PApplet p, int port)
    {
        s = new Server(p, port);
        type = ConnectionType.SERVER;
    }

    // Create Client
    public Connection(PApplet p, String ip, int port)
    {
        c = new Client(p, ip, port);
        type = ConnectionType.CLIENT;
    }

    private void sendBytes(byte[] byteArray)
    {
        if (type == ConnectionType.SERVER)
            s.write(byteArray);
        else if (type == ConnectionType.CLIENT)
            c.write(byteArray);
    }

    // Write the object to an output stream and transfer the byte array
    public void sendObject(Serializable obj)
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        AppendingObjectOutputStream oos = null;
        try {
            oos = new AppendingObjectOutputStream(bos);

            oos.writeObject(obj);
            oos.flush();
            oos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        sendBytes(bos.toByteArray());
    }

    private ByteArrayInputStream receiveBytes()
    {
        ByteArrayInputStream bis = null;

        if (type == ConnectionType.SERVER)
        {
            Client availableClient = s.available();
            if (availableClient != null)
                bis = new ByteArrayInputStream(availableClient.readBytes());
        }
        else if (type == ConnectionType.CLIENT)
            if (c.available() > 0)
                bis = new ByteArrayInputStream(c.readBytes());

        if (bis != null && bis.available() > 0)
            return bis;
        else
            return null;
    }

    public ArrayList<Object> receiveObjects()
    {
        ArrayList<Object> objs = new ArrayList<>();

        ByteArrayInputStream bis = receiveBytes();
        if (bis == null)
            return objs; // Empty array

        // Create the object input stream
        AppendingObjectInputStream ois = null;
        try {
            ois = new AppendingObjectInputStream(bis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Read all the objects and add them to the array
        while (true)
            try {
                objs.add(ois.readObject());
            }
            catch (ClassNotFoundException ignored) {}
            catch (IOException e) {
                break;
            }

        return objs;
    }
}

//
// Remove header writing/checking
//

class AppendingObjectOutputStream extends ObjectOutputStream {

    public AppendingObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    @Override
    protected void writeStreamHeader() throws IOException {
        reset();
    }
}

class AppendingObjectInputStream extends ObjectInputStream {
    public AppendingObjectInputStream(InputStream in) throws IOException {
        super(in);
    }

    @Override
    protected void readStreamHeader() {
        // NADA
    }
}
