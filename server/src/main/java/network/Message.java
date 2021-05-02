package network;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Message")
@XmlAccessorType(XmlAccessType.FIELD)
public class Message {
    public String command;
    public Object data;

    public Message(String c, String d){
        command = c;
        data = d;
    }
}
