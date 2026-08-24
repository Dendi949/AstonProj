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
    private final String recordNumber;

    // Приватный конструктор, доступный только через Builder
    private Student(Builder builder) {
        this.groupNumber = builder.groupNumber;
        this.averageScore = builder.averageScore;
        this.recordNumber = builder.recordNumber;
    }

    // Геттеры
    public int getGroupNumber() { return groupNumber; }
    public double getAverageScore() { return averageScore; }
    public String getRecordBookNumber() { return recordNumber; }

    @Override
    public String toString() {
        return "Student{group=" + groupNumber + ", avg=" + averageScore + ", book='" + recordNumber + "'}";
    }
    //equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return groupNumber == student.groupNumber &&
                Double.compare(student.averageScore, averageScore) == 0 &&
                (recordNumber != null ? recordNumber.equals(student.recordNumber) : student.recordNumber == null);
    }
// hashCode
    @Override
    public int hashCode() {
        int result;
        long temp;
        result = groupNumber;
        temp = Double.doubleToLongBits(averageScore);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (recordNumber != null ? recordNumber.hashCode() : 0);
        return result;
    }



    // ============ ВНУТРЕННИЙ КЛАСС BUILDER ============
    public static class Builder {
        private int groupNumber;
        private double averageScore;
        private String recordNumber;

<<<<<<< HEAD
        /**
         * Устанавливает номер группы с валидацией (должен быть > 0).
         */
        public Builder setGroupNumber(int groupNumber) {
            if (groupNumber <= 0) {
                throw new IllegalArgumentException("Номер группы должен быть > 0");
=======
        public Builder groupNumber(int groupNumber) {
            if (groupNumber <= 0) {
                throw new IllegalArgumentException("Group number must be > 0");
>>>>>>> main
            }
            this.groupNumber = groupNumber;
            return this;
        }
<<<<<<< HEAD

        /**
         * Устанавливает средний балл с валидацией (должен быть в [0, 5]).
         */
        public Builder setAverageScore(double averageScore) {
            if (averageScore < 0 || averageScore > 5.0) {
                throw new IllegalArgumentException("Средний балл должен быть в [0, 5]");
=======
        public Builder averageScore(double averageScore) {
            if (averageScore < 0 || averageScore > 5) {
                throw new IllegalArgumentException("Average score must be between 0 and 5");
>>>>>>> main
            }
            this.averageScore = averageScore;
            return this;
        }
<<<<<<< HEAD

        /**
         * Устанавливает номер зачётной книжки с валидацией (не null и не пустой).
         */
        public Builder setRecordBookNumber(String recordBookNumber) {
            if (recordBookNumber == null || recordBookNumber.trim().isEmpty()) {
                throw new IllegalArgumentException("Номер зачётной книжки не может быть пустым");
            }
            this.recordBookNumber = recordBookNumber.trim();
=======
        public Builder recordNumber(String recordNumber) {
            if (recordNumber == null || recordNumber.trim().isEmpty()) {
                throw new IllegalArgumentException("Record number cannot be empty");
            }
            this.recordNumber = recordNumber;
>>>>>>> main
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