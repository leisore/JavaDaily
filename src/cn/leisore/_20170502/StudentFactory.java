package cn.leisore._20170502;

import java.util.ArrayList;
import java.util.List;

public class StudentFactory {

	public static List<Student> create(String[][] students) {

		List<Student> results = new ArrayList<>(students.length);

		for (String[] st : students) {

			int index = 0;

			int id = Integer.valueOf(st[index++]);
			String name = st[index++];
			String phoneNumber = st[index++];
			String workPlace = st[index++];
			String schoolName = st[index++];
			String job = st[index++];
			double score = Double.valueOf(st[index++]);

			Student student = new Student();
			student.setId(id);
			student.setName(name);
			student.setPhoneNumber(phoneNumber);
			student.setWorkPlace(workPlace);
			student.setSchoolName(schoolName);
			student.setJob(job);
			student.setScore(score);

			results.add(student);
		}

		return results;
	}
}
