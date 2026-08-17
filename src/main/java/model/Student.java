package model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return groupNumber == student.groupNumber && Double.compare(averageScore, student.averageScore) == 0 && Objects.equals(recordBookNumber, student.recordBookNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupNumber, averageScore, recordBookNumber);
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
            return new Student(this);
        }
    }
}