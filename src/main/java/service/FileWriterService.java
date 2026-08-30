package service;

import model.Student;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileWriterService {

    public void writeStudents(List<Student> students, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(filePath, true))) {

            for (Student student : students) {
                writer.write(student.toString());
                writer.newLine();
            }

            System.out.println("Результат записан в файл.");

        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }
}