package testObject;

import annotations.UserInput;
import model.CustomClass;

import java.io.Serializable;

@CustomClass
public class Chapter implements Serializable {

    @UserInput
    private String name; //Поле не может быть null, Строка не может быть пустой
    @UserInput
    private String world; //Поле может быть null

    public Chapter() {
        this.name = "mock_name";
        this.world = "mock_world";
    }

    public Chapter(String name, String world) {
        this.name = name;
        this.world = world;
    }

    public String getName() {
        return name;
    }

    public String getWorld() {
        return world;
    }


    @Override
    public String toString() {
        return "Chapter{" +
                "\n\t\tname='" + name + '\'' +
                "\n\t\tworld='" + world + '\'' +
                "\n\t}";
    }
}
