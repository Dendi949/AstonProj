package input;

import model.Student;
import java.util.List;

public class DataFiller {
    public enum FillType { RANDOM, FILE, MANUAL }

    public static List<Student> fill(FillType type, int size, String filePath) {
        // заглушка, будет реализовано позже
        return List.of();
    }
}