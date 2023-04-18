package tests.EnhancedForLoop;

import java.util.List;

import Models.Student;

public class EnhancedForLoop {
List<Student> students;
	
	public EnhancedForLoop(List<Student> students) {
		this.students = students;
	}
	
	public void foo() {
		int countCS = 0, countEE = 0, sumGPA = 0;
		StringBuilder extraWork = new StringBuilder();  
		for(Student student: students) {
			System.out.println(student.getFullName());
			System.out.println("     ===     ");
			student.printStudentInfo();
			System.out.println("------------------");
			if(student.getMajor() == "CS") {
				++countEE;
			}
			if(student.getMajor() == "EE") {
				++countEE;
			}
			sumGPA += student.getGpa();
			extraWork.append(student.getAge());
			extraWork.append(" , ");
		}
		System.out.println("This are " + countCS + " CS students!");
		System.out.println("This are " + countEE + " EE students!");
		System.out.println("Average students G.P.A is " + sumGPA / students.size());
	}
}
