package test;

import P2P.Connection;
import processing.core.PApplet;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by roscale on 2/28/17.
 */
public class test extends PApplet {
    Connection network;

    @Override
    public void settings()
    {
        size(800, 800);
    }

    @Override
    public void setup()
    {

    }

    @Override
    public void draw()
    {
        if (network == null)
            return;

        for (Object obj : network.receiveObjects())
            parse(obj);
    }

    @Override
    public void keyPressed()
    {
        if (key == 's')
        {
            network = new Connection(this, 12345);
        }
        else if (key == 'c')
        {
            network = new Connection(this, "192.168.1.2", 12345);
        }
        else if (key == 'm')
        {
            Object toSend = new Bool(true);
            Object toSend2 = new Vector(123, 751.156f);

            network.sendObject((Serializable) toSend2);
        }
    }

    void parse(Object obj)
    {
        if (obj.getClass() == Vector.class)
        {
            Vector v = (Vector) obj;
            v.print();
            network.sendObject(new Vector(random(50), random(50)));
        }

        else if (obj.getClass() == Bool.class)
        {
            Bool b = (Bool) obj;
            System.out.println(b.value);
            network.sendObject(new Bool(true));
        }
    }
}
