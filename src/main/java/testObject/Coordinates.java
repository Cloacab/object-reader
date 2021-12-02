package testObject;

import annotations.UserInput;
import model.CustomClass;
import rules.Rules;

import java.io.Serializable;

@CustomClass
public class Coordinates implements Serializable {
    @UserInput
    @Rules(value = {"XCoordinateRule"})
    private long x; //Максимальное значение поля: 411
    @UserInput
    private float y;

    public Coordinates(){
        this.x = 42;
        this.y = 42;
    }

    public Coordinates(long x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getY() {
        return y;
    }

    public long getX() {
        return x;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}