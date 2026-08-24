package model;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {

    @Test
    @DisplayName("Создание валидного студента через Builder")
    public void testValidStudentCreation() {
        Student student = new Student.Builder()
                .groupNumber(101)
                .averageScore(4.5)
                .recordNumber("12345")
                .build();

        assertNotNull(student);
        assertEquals(101, student.getGroupNumber());
        assertEquals(4.5, student.getAverageScore());
        assertEquals("12345", student.getRecordBookNumber());
    }

    @Test
    @DisplayName("Builder выбрасывает исключение при groupNumber <= 0")
    public void testInvalidGroupNumber() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Student.Builder()
                        .groupNumber(0)
                        .averageScore(4.0)
                        .recordNumber("12345")
                        .build()
        );
        assertEquals("Group number must be > 0", exception.getMessage());
    }

    @Test
    @DisplayName("Builder выбрасывает исключение при averageScore < 0")
    public void testAverageScoreLessThanZero() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Student.Builder()
                        .groupNumber(101)
                        .averageScore(-1.0)
                        .recordNumber("12345")
                        .build()
        );
        assertEquals("Average score must be between 0 and 5", exception.getMessage());
    }

    @Test
    @DisplayName("Builder выбрасывает исключение при averageScore > 5")
    public void testAverageScoreGreaterThanFive() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Student.Builder()
                        .groupNumber(101)
                        .averageScore(5.5)
                        .recordNumber("12345")
                        .build()
        );
        assertEquals("Average score must be between 0 and 5", exception.getMessage());
    }

    @Test
    @DisplayName("Builder выбрасывает исключение при null recordNumber")
    public void testNullRecordNumber() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Student.Builder()
                        .groupNumber(101)
                        .averageScore(4.0)
                        .recordNumber(null)
                        .build()
        );
        assertEquals("Record number cannot be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Builder выбрасывает исключение при пустой recordNumber")
    public void testEmptyRecordNumber() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Student.Builder()
                        .groupNumber(101)
                        .averageScore(4.0)
                        .recordNumber("")
                        .build()
        );
        assertEquals("Record number cannot be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Builder выбрасывает исключение при recordNumber с пробелами")
    public void testWhitespaceRecordNumber() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Student.Builder()
                        .groupNumber(101)
                        .averageScore(4.0)
                        .recordNumber("   ")
                        .build()
        );
        assertEquals("Record number cannot be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Проверка метода toString")
    public void testToString() {
        Student student = new Student.Builder()
                .groupNumber(202)
                .averageScore(3.75)
                .recordNumber("67890")
                .build();

        String expected = "Student{groupNumber=202, averageScore=3.75, recordNumber='67890'}";
        assertEquals(expected, student.toString());
    }

    @Test
    @DisplayName("Проверка метода equals для одинаковых объектов")
    public void testEqualsSameObjects() {
        Student student1 = new Student.Builder()
                .groupNumber(101)
                .averageScore(4.5)
                .recordNumber("12345")
                .build();

        Student student2 = new Student.Builder()
                .groupNumber(101)
                .averageScore(4.5)
                .recordNumber("12345")
                .build();

        assertEquals(student1, student2);
        assertEquals(student1.hashCode(), student2.hashCode());
    }

    @Test
    @DisplayName("Проверка метода equals для разных объектов")
    public void testEqualsDifferentObjects() {
        Student student1 = new Student.Builder()
                .groupNumber(101)
                .averageScore(4.5)
                .recordNumber("12345")
                .build();

        Student student2 = new Student.Builder()
                .groupNumber(102)
                .averageScore(4.5)
                .recordNumber("12345")
                .build();

        assertNotEquals(student1, student2);
    }

    @Test
    @DisplayName("Проверка hashCode для разных объектов")
    public void testHashCodeDifferentObjects() {
        Student student1 = new Student.Builder()
                .groupNumber(101)
                .averageScore(4.5)
                .recordNumber("12345")
                .build();

        Student student2 = new Student.Builder()
                .groupNumber(202)
                .averageScore(3.0)
                .recordNumber("67890")
                .build();

        assertNotEquals(student1.hashCode(), student2.hashCode());
    }

    @Test
    @DisplayName("Проверка граничных значений averageScore (0 и 5)")
    public void testBoundaryAverageScore() {
        Student student1 = new Student.Builder()
                .groupNumber(101)
                .averageScore(0.0)
                .recordNumber("12345")
                .build();

        Student student2 = new Student.Builder()
                .groupNumber(102)
                .averageScore(5.0)
                .recordNumber("67890")
                .build();

        assertEquals(0.0, student1.getAverageScore());
        assertEquals(5.0, student2.getAverageScore());
    }

    @Test
    @DisplayName("Проверка работы с отрицательным groupNumber")
    public void testNegativeGroupNumber() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Student.Builder()
                        .groupNumber(-5)
                        .averageScore(4.0)
                        .recordNumber("12345")
                        .build()
        );
        assertEquals("Group number must be > 0", exception.getMessage());
    }
}