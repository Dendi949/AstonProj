package model;

/**
 * Класс Student, представляющий студента с тремя полями:
 * - номер группы (int)
 * - средний балл (double)
 * - номер зачётной книжки (String)
 *
 * Реализован паттерн Builder для удобного создания объектов с валидацией.
 */
public class Student {
    private final int groupNumber;
    private final double averageScore;
    private final String recordBookNumber;

    // Приватный конструктор, доступный только через Builder
    private Student(Builder builder) {
        this.groupNumber = builder.groupNumber;
        this.averageScore = builder.averageScore;
        this.recordBookNumber = builder.recordBookNumber;
    }

    // Геттеры
    public int getGroupNumber() { return groupNumber; }
    public double getAverageScore() { return averageScore; }
    public String getRecordBookNumber() { return recordBookNumber; }

    @Override
    public String toString() {
        return "Student{group=" + groupNumber + ", avg=" + averageScore + ", book='" + recordBookNumber + "'}";
    }

    // ============ ВНУТРЕННИЙ КЛАСС BUILDER ============
    public static class Builder {
        private int groupNumber;
        private double averageScore;
        private String recordBookNumber;

        /**
         * Устанавливает номер группы с валидацией (должен быть > 0).
         */
        public Builder setGroupNumber(int groupNumber) {
            if (groupNumber <= 0) {
                throw new IllegalArgumentException("Номер группы должен быть > 0");
            }
            this.groupNumber = groupNumber;
            return this;
        }

        /**
         * Устанавливает средний балл с валидацией (должен быть в [0, 5]).
         */
        public Builder setAverageScore(double averageScore) {
            if (averageScore < 0 || averageScore > 5.0) {
                throw new IllegalArgumentException("Средний балл должен быть в [0, 5]");
            }
            this.averageScore = averageScore;
            return this;
        }

        /**
         * Устанавливает номер зачётной книжки с валидацией (не null и не пустой).
         */
        public Builder setRecordBookNumber(String recordBookNumber) {
            if (recordBookNumber == null || recordBookNumber.trim().isEmpty()) {
                throw new IllegalArgumentException("Номер зачётной книжки не может быть пустым");
            }
            this.recordBookNumber = recordBookNumber.trim();
            return this;
        }

        /**
         * Строит объект Student, проверяя, что все поля были заполнены.
         */
        public Student build() {
            if (groupNumber == 0 || recordBookNumber == null) {
                throw new IllegalStateException("Не все поля заполнены");
            }
            return new Student(this);
        }
    }
}