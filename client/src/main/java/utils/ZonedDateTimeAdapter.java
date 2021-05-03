package utils;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.ZonedDateTime;

public class ZonedDateTimeAdapter extends XmlAdapter<String, ZonedDateTime> {
    public ZonedDateTime unmarshal(String v) throws Exception {
        ZonedDateTime tmp;
        tmp = ZonedDateTime.parse(v);
        return ZonedDateTime.parse(v);
    }

    @Override
    public String marshal(ZonedDateTime v) throws Exception {
        return v.toString();
    }

}
