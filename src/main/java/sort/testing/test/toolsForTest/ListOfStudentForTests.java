package src.main.java.sort.testing.test.toolsForTest;
import model.Student;

import java.util.List;

public final class ListOfStudentForTests {
   private final Student student1 = new Student.Builder()
           .setGroupNumber(1)
           .setAverageScore(12)
           .setRecordBookNumber("A-1")
           .build();

   private final Student student2 = new Student.Builder()
           .setGroupNumber(2)
           .setAverageScore(12)
           .setRecordBookNumber("A-2")
           .build();
   private final Student student3 = new Student.Builder()
           .setGroupNumber(1)
           .setAverageScore(13)
           .setRecordBookNumber("A-3")
           .build();
   private final Student student4 = new Student.Builder()
           .setGroupNumber(1)
           .setAverageScore(12)
           .setRecordBookNumber("A-3")
           .build();
   private  final List<Student> list = List.of(student1,student2,student3,student4);

   public List<Student> getStudentsList(){
       return list;
   }
 }
