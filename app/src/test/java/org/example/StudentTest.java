package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class StudentTest {
  double delta = 0.01;

  // public void test
  @ParameterizedTest
  @MethodSource("setupStudentBulkScoreAddData")
  public void setupStudentBulkScoreAddTest(
      String name, int id, double[] scores, double expectedAverage, String passFailString) {
    Student student = new Student(name);
    student.bulkSetScore(scores);
    double actualAverage = student.averageScore();
    assertEquals(expectedAverage, actualAverage, this.delta);
  }

  @ParameterizedTest
  @MethodSource("setupStudentData")
  public void setupStudentTest(
      String name, int id, double[] scores, double expectedAverage, String passFailString) {
    Student student = new Student(name);
    for (int i = 0; i < 3; i++) {
      student.setScore(scores[i]);
    }
    double actualAverage = student.averageScore();
    assertEquals(expectedAverage, actualAverage, this.delta);
  }

  static Stream<Arguments> setupStudentBulkScoreAddData() {
    return Stream.of(
        Arguments.of("", 101, new double[] {85.5, 78.0, 92.5, 67.0, 88.75}, 82.35, "Pass"));
  }

  static Stream<Arguments> setupStudentData() {
    return Stream.of(
        Arguments.of("Alice", 1001, new double[] {85, 78, 72}, 78.33, "Pass"),
        Arguments.of("Bob", 1002, new double[] {50, 55, 59}, 54.67, "Fail"),
        Arguments.of("Charlie", 1003, new double[] {90, 90, 90}, 90.0, "Pass"),
        Arguments.of("David", 1004, new double[] {60, 60, 60}, 60.0, "Pass"),
        Arguments.of("Eve", 1005, new double[] {59.9, 60, 60, 87}, 59.97, "Fail"),
        Arguments.of("Frank", 1006, new double[] {100, 100, 100}, 100.0, "Pass"),
        Arguments.of("Charles", 1012, new double[] {92, 88, 95}, 91.67, "Pass"),
        Arguments.of("Diana", 1010, new double[] {65, 70, 68}, 67.67, "Pass"),
        Arguments.of("Ava", 1009, new double[] {45, 55, 50}, 50.00, "Fail"),
        Arguments.of("Franklin", 1011, new double[] {98, 96, 99}, 97.67, "Pass"),
        Arguments.of("Grace", 1007, new double[] {59, 61, 58}, 59.33, "Fail"),
        Arguments.of("Henry", 1008, new double[] {88, 92, 85}, 88.33, "Pass"));
  }
}
