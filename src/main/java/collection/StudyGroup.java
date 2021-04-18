package collection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.ZonedDateTime;

@XmlAccessorType(XmlAccessType.FIELD)
public class StudyGroup implements Comparable {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long studentsCount; //Значение поля должно быть больше 0
    private FormOfEducation formOfEducation; //Поле не может быть null
    private Semester semesterEnum; //Поле не может быть null
    private Person groupAdmin; //Поле не может быть null

    public StudyGroup(String name, Coordinates coordinates, long studentsCount, FormOfEducation formOfEducation, Semester semesterEnum, Person groupAdmin){
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = ZonedDateTime.now();
        assert studentsCount > 0;
        this.studentsCount = studentsCount;
        this.formOfEducation = formOfEducation;
        this.semesterEnum = semesterEnum;
        this.groupAdmin = groupAdmin;
    }

    public StudyGroup(Integer id, String name, Coordinates coordinates, long studentsCount, FormOfEducation formOfEducation, Semester semesterEnum, Person groupAdmin){
        assert (id != null);
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = ZonedDateTime.now();
        assert studentsCount > 0;
        this.studentsCount = studentsCount;
        this.formOfEducation = formOfEducation;
        this.semesterEnum = semesterEnum;
        this.groupAdmin = groupAdmin;
    }

    public StudyGroup(){

    }

    @Override
    public String toString() {
        return "StudyGroup{" +
                "\n id=" + id +
                "\n name='" + name + '\'' +
                "\n coordinates=" + coordinates +
                "\n creationDate=" + creationDate +
                "\n studentsCount=" + studentsCount +
                "\n formOfEducation=" + formOfEducation +
                "\n semesterEnum=" + semesterEnum +
                "\n groupAdmin=" + groupAdmin +
                "\n}";
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public long getStudentsCount() {
        return studentsCount;
    }

    public void setStudentsCount(long studentsCount) {
        this.studentsCount = studentsCount;
    }

    public FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }

    public void setFormOfEducation(FormOfEducation formOfEducation) {
        this.formOfEducation = formOfEducation;
    }

    public Semester getSemesterEnum() {
        return semesterEnum;
    }

    public void setSemesterEnum(Semester semesterEnum) {
        this.semesterEnum = semesterEnum;
    }

    public Person getGroupAdmin() {
        return groupAdmin;
    }

    public void setGroupAdmin(Person groupAdmin) {
        this.groupAdmin = groupAdmin;
    }

    @Override
    public int compareTo(Object o) {
        if (o == null || !(o instanceof StudyGroup)) {
            return 1;
        }
        StudyGroup other = (StudyGroup) o;
        if (this.getStudentsCount() != other.getStudentsCount()) {
            return (int) (this.getStudentsCount() - other.getStudentsCount());
        }
        return 0;
    }
}