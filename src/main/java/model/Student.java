package model;

public class Student {
    private final int groupNumber;
    private final double averageScore;
    private final String recordNumber;

    private Student(Builder builder) {
        this.groupNumber = builder.groupNumber;
        this.averageScore = builder.averageScore;
        this.recordNumber = builder.recordNumber;
    }

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



    public static class Builder {
        private int groupNumber;
        private double averageScore;
        private String recordNumber;

        public Builder groupNumber(int groupNumber) {
            if (groupNumber <= 0) {
                throw new IllegalArgumentException("Group number must be > 0");
            }
            this.groupNumber = groupNumber;
            return this;
        }
        public Builder averageScore(double averageScore) {
            if (averageScore < 0 || averageScore > 5) {
                throw new IllegalArgumentException("Average score must be between 0 and 5");
            }
            this.averageScore = averageScore;
            return this;
        }
        public Builder recordNumber(String recordNumber) {
            if (recordNumber == null || recordNumber.trim().isEmpty()) {
                throw new IllegalArgumentException("Record number cannot be empty");
            }
            this.recordNumber = recordNumber;
            return this;
        }
        public Student build() {
            return new Student(this);
        }
    }
}