package collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Coordinates")
@XmlAccessorType(XmlAccessType.FIELD)
public class Coordinates {
    private int x;
    private long y; //Значение поля должно быть больше -496

    public Coordinates(Integer x, Long y) {
        this.x = x;
        if(y > -496){
            this.y = y;
        }else{
            throw new IllegalArgumentException("Y param should be greater than -496");
        }
    }

    public Coordinates() {
    }

    @Override
    public String toString() {
        return "x = " + x + ", y = " + y;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Long getY() {
        return y;
    }

    // Check this later
    public void setY(Long y) {
        if(y > -496) {
            this.y = y;
        }else{
            throw new IllegalArgumentException("Y param should be greater than -496");
        }
    }
}