package collection;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.*;
import java.time.LocalDateTime;

@XmlRootElement(name="Person")
@XmlAccessorType(XmlAccessType.FIELD)
public class Person {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private java.time.LocalDateTime birthday; //Поле может быть null
    private double weight; //Значение поля должно быть больше 0
    private Color hairColor; //Поле не может быть null

    public Person(String name, LocalDateTime birthday, double weight, Color hairColor) {
        this.name = name;
        this.birthday = birthday;
        assert(weight>0);
        this.weight = weight;
        this.hairColor = hairColor;
    }

    public Person() {

    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", birthday=" + birthday +
                ", weight=" + weight +
                ", hairColor=" + hairColor +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        assert (weight<0);
        this.weight = weight;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public void setHairColor(Color hairColor) {
        this.hairColor = hairColor;
    }
}
