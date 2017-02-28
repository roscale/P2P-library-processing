package test;

import java.io.Serializable;

/**
 * Created by roscale on 2/28/17.
 */
public class Vector implements Serializable {

    float x;
    float y;

    Vector(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    void print()
    {
        System.out.println("(" + x + ", " + y + ")");
    }
}
