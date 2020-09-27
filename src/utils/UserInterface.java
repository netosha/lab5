package utils;

import collection.*;
import exceptions.AbortCommandException;
import exceptions.InvalidInputException;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


/**
 * User CLI class
 */

public class UserInterface {
    private Reader reader;
    private Writer writer;
    private Scanner scanner;

    /**
     * User CLI Constructor
     *
     * @param reader    Reader instance
     * @param writer    Writer instance
     */
    public UserInterface(Reader reader, Writer writer){
        this.reader = reader;
        this.writer = writer;
        this.scanner = new Scanner(reader);
    }

    /**
     * User CLI Constructor with custom scanner
     *
     * @param reader    Reader instance
     * @param writer    Writer instance
     * @param scanner    Writer instance
     */
    public UserInterface(Reader reader, Writer writer, Scanner scanner){
        this.reader = reader;
        this.writer = writer;
        this.scanner = scanner;
    }

    /**
     * Writes message with "\n"
     *
     * @param message   Message to write
     */
    public void writeln(String message){
        try {
            writer.write(message+"\n");
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Writes message
     *
     * @param message   Message to write
     */
    public void write(String message){
        try {
            writer.write(message);
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Checks if scanner has next line to read
     *
     * @return bool
     */
    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    /**
     * Return scanner's next line to read
     *
     * @return User's string
     */
    public String read() {
        String tmp = scanner.nextLine();
        if(tmp.equals("#exit")){
            throw new AbortCommandException("Command aborted");
        }
        return  tmp;
    }

    public Coordinates readCoordinates() {
        try{
            int x;
            long y;
            writeln("Input X (integer)");
            x = Integer.parseInt(read());
            writeln("Input Y (long) (min value = -496)");
            y = Long.parseLong(read());
            return new Coordinates(x,y);
        }catch (Exception e){
            throw new InvalidInputException("Failed to parse params");
        }
    }

    public FormOfEducation readFormOfEducation() {
        int i = 1;
        int formIndex;
        for(FormOfEducation form : FormOfEducation.values()){
            writeln("| "+i+" "+form.getName());
            i++;
        }
        try{
            formIndex = Integer.parseInt(read());
            if(formIndex < 1 || formIndex > FormOfEducation.values().length){
                throw new InvalidInputException("Wrong number");
            }
            return FormOfEducation.values()[formIndex-1];
        }catch (Exception e){
            throw new InvalidInputException("Failed to parse params");
        }
    }


    public Semester readSemester(){
        int i = 1;
        int semesterIndex;
        for(Semester semester : Semester.values()){
            writeln("| "+i+" "+semester.getName());
            i++;
        }
        try{
            semesterIndex = Integer.parseInt(read());
            if(semesterIndex < 1 || semesterIndex > Semester.values().length){
                throw new InvalidInputException("Wrong number");
            }
            return Semester.values()[semesterIndex-1];
        }catch (Exception e){
            throw new InvalidInputException("Failed to parse params");
        }
    }

    public Color readColor(){
        int i = 1;
        int colorIndex;
        for(Color color : Color.values()){
            writeln("| "+i+" "+color.getName());
            i++;
        }
        try{
            colorIndex = Integer.parseInt(read());
            if(colorIndex < 1 || colorIndex > Color.values().length){
                throw new InvalidInputException("Wrong number");
            }
            return Color.values()[colorIndex-1];
        }catch (Exception e){
            throw new InvalidInputException("Failed to parse params");
        }
    }

    public Person readPerson(){
        String name;
        LocalDateTime birthday;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        double weight;
        Color color;

        try{
            writeln("Input Person name");
            name = read();
            writeln("Input Person birthday (like 2016-03-04 11:30)");
            birthday = LocalDateTime.parse(read(), formatter);
            writeln("Input Person weight (double)");
            weight = Double.parseDouble(read());
            writeln("Input Person hair color (integer)");
            color = readColor();
            return new Person(name, birthday, weight, color);
        }catch (Exception e){
            throw new InvalidInputException("Failed to parse params");
        }
    }

    public StudyGroup readStudyGroup() {
        String name;
        Coordinates coordinates;
        long studentsCount;
        FormOfEducation formOfEducation;
        Semester semester;
        Person person;
        try{
            writeln("Input StudyGroup name");
            name = read();
            writeln("Input StudyGroup coordinates");
            coordinates = readCoordinates();
            writeln("Input StudyGroup students count (>0) (long)");
            studentsCount = Long.parseLong(read());
            if(studentsCount <= 0){
                throw new InvalidInputException("Student's count should be more, than 0.");
            }
            writeln("Chose form of education (integer):");
            formOfEducation = readFormOfEducation();
            writeln("Chose semester (integer):");
            semester = readSemester();
            person = readPerson();
            return new StudyGroup(name, coordinates, studentsCount, formOfEducation, semester, person);
        }catch (AbortCommandException e){
            throw new AbortCommandException("Command abroted");
        }catch (Exception e){
            throw new InvalidInputException(e.getMessage());
        }
    }

}
