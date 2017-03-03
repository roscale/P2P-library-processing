package test;

import P2P.Connection;
import processing.core.PApplet;

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
            network = new Connection(this, "192.168.1.3", 12345);
        }
        else if (key == 'm')
        {
            network.sendObject(new Num(1));
        }
    }

    void parse(Object obj)
    {
//        if (obj.getClass() == Vector.class)
//        {
//            Vector v = (Vector) obj;
//            v.print();
//
//            delay(100);
//	        network.sendObject(new Vector(random(50), random(50)));
//	        network.sendObject(new Bool(true));
//        }
//
//        else if (obj.getClass() == Bool.class)
//        {
//            Bool b = (Bool) obj;
//            System.out.println(b.value);
//
//            delay(100);
//            network.sendObject(new Bool(true));
//	        network.sendObject(new Vector(random(50), random(50)));
//        }

//	    System.out.println(obj.getClass());
        if (obj.getClass() == Num.class)
        {
        	Num i = (Num) obj;
        	System.out.println(i.value);

        	// delay(10);
			network.sendObject(new Num(i.value+1));
        }
        else
        	System.out.println("Not a number !!");
    }
}
