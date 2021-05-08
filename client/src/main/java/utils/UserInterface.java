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
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
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
     * @param reader Reader instance
     * @param writer Writer instance
     */
    public UserInterface(Reader reader, Writer writer) {
        this.reader = reader;
        this.writer = writer;
        this.scanner = new Scanner(reader);
    }

    /**
     * User CLI Constructor with custom scanner
     *
     * @param reader  Reader instance
     * @param writer  Writer instance
     * @param scanner Writer instance
     */
    public UserInterface(Reader reader, Writer writer, Scanner scanner) {
        this.reader = reader;
        this.writer = writer;
        this.scanner = scanner;
    }

    /**
     * Writes message with "\n"
     *
     * @param message Message to write
     */
    public void writeln(String message) {
        try {
            writer.write(message + "\n");
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Writes message
     *
     * @param message Message to write
     */
    public void write(String message) {
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
        try{
            String tmp = scanner.nextLine();
            if (tmp.equals("#exit")) {
                throw new AbortCommandException("Command aborted");
            }
            return tmp;
        }catch (NoSuchElementException e){
            System.exit(1);
        }
        catch (Exception e){
            throw new InvalidInputException("");
        }
        return "";
    }
    public Long readLongWithMessage(String message) {
        Long tmp = null;
        do {
            try {
                writeln(message);
                tmp = Long.parseLong(read());
                return tmp;
            } catch (NumberFormatException e) {
            } catch (Exception e) {
                throw e;
            }
        } while (tmp == null);

        return tmp;
    }

    public double readDobuleWithMessage(String message) {
        Double tmp = null;
        do {
            try {
                writeln(message);
                tmp = Double.parseDouble(read());
                return tmp;
            } catch (NumberFormatException e) {
            } catch (Exception e) {
                throw e;
            }
        } while (tmp == null);
        return tmp;
    }

    public Integer readIntWithMessage(String message) {
        Integer tmp = null;
        do {
            try {
                writeln(message);
                tmp = Integer.parseInt(read());
                return tmp;
            } catch (NumberFormatException e) {
            }catch (Exception e){
                throw e;
            }
        } while (tmp == null);

        return tmp;
    }

    public String readStringWithMessage(String message) {
        String tmp = null;
        do {
            try{
                writeln(message);
                tmp = read();
            }catch (Exception e){
                throw e;
            }

        } while (tmp == null || tmp.length() == 0);
        return tmp;
    }

    public LocalDateTime readLocalDateTime(String message) {
        LocalDateTime tmp = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        do {
            try {
                writeln(message);
                tmp = LocalDateTime.parse(read(), formatter);
                return tmp;
            } catch (DateTimeParseException e) {

            } catch (Exception e) {
                throw e;
            }
        } while (tmp == null);
        return tmp;
    }

    public Coordinates readCoordinates() {
        try {
            int x;
            Long y = null;
            x = readIntWithMessage("Input X (int)");
            do {
                y = readLongWithMessage("Input Y (long) (min value = -495)");
            } while (y <= -496 || y == null);
            return new Coordinates(x, y);
        } catch (Exception e) {
            throw new InvalidInputException("Failed to parse params");
        }
    }

    public FormOfEducation readFormOfEducation(String message) {
        int formIndex = 0;
        do {
            try {
                int i = 1;
                writeln(message);
                for (FormOfEducation form : FormOfEducation.values()) {
                    writeln("| " + i + " " + form.getName());
                    i++;
                }
                formIndex = readIntWithMessage("Choose (from 1 to " + (i - 1) + ")");
            } catch (Exception e) {
                throw e;
            }
        } while (formIndex <= 0 || formIndex > (FormOfEducation.values().length));
        return FormOfEducation.values()[formIndex - 1];

    }


    public Semester readSemester(String message) {
        int semesterIndex = 0;
        do {
            try {
                int i = 1;
                writeln(message);
                for (Semester form : Semester.values()) {
                    writeln("| " + i + " " + form.getName());
                    i++;
                }
                semesterIndex = readIntWithMessage("Choose (from 1 to " + (i - 1) + ")");
            } catch (Exception e) {
                throw e;
            }
        } while (semesterIndex <= 0 || semesterIndex > (Semester.values().length));
        return Semester.values()[semesterIndex - 1];

    }

    public Color readColor(String message) {
        int colorIndex = 0;
        do {
            try {
                int i = 1;
                writeln(message);
                for (Color form : Color.values()) {
                    writeln("| " + i + " " + form.getName());
                    i++;
                }
                colorIndex = readIntWithMessage("Choose (from 1 to " + (i - 1) + ")");
            } catch (AbortCommandException e) {
                throw e;
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } while (colorIndex <= 0 || colorIndex > (Color.values().length));
        return Color.values()[colorIndex - 1];
    }


    public Person readPerson() {
        String name;
        LocalDateTime birthday;
        double weight;
        Color color;

        try {
            name = readStringWithMessage("Input Person name");
            birthday = readLocalDateTime("Input Person birthday (in format like 2016-03-04 11:30)");
            weight = readDobuleWithMessage("Input Person weight (dobule)");
            color = readColor("Input Person hair color");
            return new Person(name, birthday, weight, color);
        } catch (Exception e) {
            throw e;
        }
    }


    public StudyGroup readStudyGroup() {
        try {
            String name;
            Coordinates coordinates;
            long studentsCount = 0;
            FormOfEducation formOfEducation;
            Semester semester;
            Person person;
            try {
                name = readStringWithMessage("Input StudyGroup name");
                coordinates = readCoordinates();
                do {
                    studentsCount = readLongWithMessage("Input students count (long, >0)");
                } while (studentsCount <= 0);

                writeln("Chose form of education (integer):");
                formOfEducation = readFormOfEducation("Select form of education");
                semester = readSemester("Chose semester (integer):");
                person = readPerson();
                return new StudyGroup(name, coordinates, studentsCount, formOfEducation, semester, person);
            } catch (AbortCommandException e) {
                throw new AbortCommandException("Command aborted");
            } catch (Exception e) {
                throw new InvalidInputException(e.getMessage());
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
