package model;

public class Student {
    private final int groupNumber;
    private final double averageScore;
    private final String recordBookNumber;

    private Student(Builder builder) {
        this.groupNumber = builder.groupNumber;
        this.averageScore = builder.averageScore;
        this.recordBookNumber = builder.recordBookNumber;
    }

    public int getGroupNumber() { return groupNumber; }
    public double getAverageScore() { return averageScore; }
    public String getRecordBookNumber() { return recordBookNumber; }

    @Override
    public String toString() {
        return "Student{group=" + groupNumber + ", avg=" + averageScore + ", book='" + recordBookNumber + "'}";
    }

    public static class Builder {
        private int groupNumber;
        private double averageScore;
        private String recordBookNumber;

        public Builder setGroupNumber(int groupNumber) {
            this.groupNumber = groupNumber;
            return this;
        }
        public Builder setAverageScore(double averageScore) {
            this.averageScore = averageScore;
            return this;
        }
        public Builder setRecordBookNumber(String recordBookNumber) {
            this.recordBookNumber = recordBookNumber;
            return this;
        }
        public Student build() {
            validate();
            return new Student(this);
        }

        private void validate() {
            if (groupNumber <= 0) {
                throw new IllegalArgumentException("Номер группы должен быть положительным: " + groupNumber);
            }
            if (averageScore < 0.0 || averageScore > 5.0) {
                throw new IllegalArgumentException("Средний балл должен быть в диапазоне [0.0, 5.0]: " + averageScore);
            }
            if (recordBookNumber == null || recordBookNumber.isBlank()) {
                throw new IllegalArgumentException("Номер зачётной книжки не может быть пустым");
            }
        }
    }
}
