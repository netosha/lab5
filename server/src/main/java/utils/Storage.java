package utils;
import commands.*;
import collection.*;
import java.time.*;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.*;
import java.security.InvalidKeyException;
import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.Map;


public class Storage {
    private java.time.ZonedDateTime creationDate;
    private Map<String, StudyGroup> studyGroups = new LinkedHashMap<String, StudyGroup>();

    public Storage(){
        this.creationDate = ZonedDateTime.now();
    }


    public void put(String key, StudyGroup studyGroup) {
        StudyGroup sg = studyGroups.getOrDefault(key, null);
        studyGroups.put(key, studyGroup);
    }

    public void remove(String key) {
        studyGroups.remove(key);
    }
    public void remove(StudyGroup o) {
        studyGroups.remove(getKey(studyGroups, o));
    }

    private  <K, V> K getKey(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }


    public void clear() {
        studyGroups.clear();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        for(String key : studyGroups.keySet()){
            String sg = studyGroups.get(key).toString();
            sb.append(key).append(":").append(sg).append("\n");
        }
        return "Storage=[\n"+sb+"]";
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public Map<String, StudyGroup> getStudyGroups() {
        return studyGroups;
    }

}

