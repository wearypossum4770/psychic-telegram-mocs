package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class StudentList {
  List<Student> students = new ArrayList<>();

  public StudentList() {}

  public StudentList addStudent(Student student) {
    this.students.add(student);
    return this;
  }

  public String pickValedictorian() {
    this.students.sort((a, b) -> Double.compare(b.averageScore(), a.averageScore()));

    Student first = this.students.get(0);

    Student second = this.students.get(1);

    return String.format("%s has a higher average than %s.", first.name, second.name);
  }
}

public class Student {

  String name;

  int id;

  double score1 = -1;

  double score2 = -1;

  double score3 = -1;

  List<Double> scores = new ArrayList<>();

  public Student(String name) {
    this.name = name;
  }

  public Student(String name, int id) {
    this.name = name;
    this.id = id;
  }

  public static void main(String[] args) {
    Student alice = new Student("Alice", 1001).setScore(85).setScore(78).setScore(92);

    Student bob = new Student("Bob", 1002);

    bob.bulkSetScore(new double[] {45, 55, 50});

    StudentList students = new StudentList();

    students.addStudent(alice).addStudent(bob);

    alice.printSummary();

    System.out.println("-------------------");

    bob.printSummary();

    System.out.println(students.pickValedictorian());
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setId(int id) {
    this.id = id;
  }

  public char grade() {
    double average = this.averageScore();

    if (average >= 90) return 'A';

    if (average >= 80) return 'B';

    if (average >= 70) return 'C';

    if (average >= 60) return 'D';

    return 'F';
  }

  public void bulkSetScore(double[] scores) {
    Arrays.stream(scores).forEach(this.scores::add);
  }

  public Student setScore(double score) {
    if (this.score1 == -1) {
      score1 = score;
      return this;
    } else if (this.score2 == -1) {
      score2 = score;
      return this;
    }
    score3 = score;
    return this;
  }

  public double averageScore() {
    if (score1 >= 0 || score2 >= 0 || score3 >= 0) {
      double sum = 0;
      int count = 0;
      if (score1 >= 0) {
        sum += score1;
        count++;
      }
      if (score2 >= 0) {
        sum += score2;
        count++;
      }
      if (score3 >= 0) {
        sum += score3;
        count++;
      }
      return sum / count;
    }
    if (scores.isEmpty()) return 0.0;

    return scores.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
  }

  public boolean isPassing() {
    return this.averageScore() > 60;
  }

  public String displayPassFail() {
    return this.isPassing() ? "Pass" : "Fail";
  }

  public void printSummary() {
    System.out.println(
        String.format(
            "Name: %s\nID: %d\nAverage Score: %.2f%n\nStatus: %s",
            this.name, this.id, this.averageScore(), this.displayPassFail()));
  }
}
