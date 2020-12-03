package cn.leisore._20170502;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.management.JMException;
import javax.xml.bind.JAXBException;

import com.google.common.collect.Lists;
import com.googlecode.cqengine.ConcurrentIndexedCollection;
import com.googlecode.cqengine.IndexedCollection;
import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.query.parser.sql.SQLParser;
import com.googlecode.cqengine.resultset.ResultSet;

/**
 * ��ȡ���У�
 * <ol>
 * <li>�����ص�������</li>
 * <li>ְλΪ���������</li>
 * <li>���˳ɼ�����75</li></li>
 * </ol>
 * ͬ������,�Գɼ���������
 */
public class Test {

	public String getTypeOfDayWithSwitchStatement(String dayOfWeekArg) {
		String typeOfDay;
		switch (dayOfWeekArg) {
		case "Monday":
			typeOfDay = "Start of work week";
			break;
		case "Tuesday":
		case "Wednesday":
		case "Thursday":
			typeOfDay = "Midweek";
			break;
		case "Friday":
			typeOfDay = "End of work week";
			break;
		case "Saturday":
		case "Sunday":
			typeOfDay = "Weekend";
			break;
		default:
			throw new IllegalArgumentException("Invalid day of the week: " + dayOfWeekArg);
		}
		return typeOfDay;
	}

	static class Foo implements AutoCloseable {
		public void bar() throws Exception {
			throw new Exception("in bar");
		}

		@Override
		public void close() throws Exception {
			throw new Exception("in close");
		}

	}

	static void tryResourceException() throws Exception {
		try (Foo foo = new Foo();) {
			foo.bar();
		}
	}

	static void tryFinallyException() throws Exception {
		Foo foo = new Foo();
		try {
			foo.bar();
		} finally {
			foo.close();
		}
	}

	static void e1() throws IOException {
	}

	static void e2() throws SQLException {
	}

	static void e3() throws IOException, SQLException {
		try {
			e1();
			e2();
		} catch (Exception ex) {
			throw ex;
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		tryResourceException();
		tryFinallyException();

		{
			// Java 7
			List<String> list1 = new ArrayList<>();
			List<List<String>> list2 = new ArrayList<>();
			List<List<List<String>>> list3 = new ArrayList<>();
			Map<String, Collection<String>> map4 = new LinkedHashMap<>();
		}

		{
			// Java7֮ǰ
			List<String> list1 = new ArrayList<String>();
			List<List<String>> list2 = new ArrayList<List<String>>();
			List<List<List<String>>> list3 = new ArrayList<List<List<String>>>();
			Map<String, Collection<String>> map4 = new LinkedHashMap<String, Collection<String>>();
		}

		List<Student> students = generateStudents();

		List<Student> lamdaResults = queryWithLamda(students);
		List<Student> cqEngineResults = queryWithCQEngine(students);

		System.out.println("Lamda Results");
		for (Student s : lamdaResults) {
			System.out.println("\t " + s);
		}

		System.out.println("CQEngine Results");
		for (Student s : cqEngineResults) {
			System.out.println("\t " + s);
		}

		System.out.println("Is same = "
				+ (lamdaResults.containsAll(cqEngineResults) && cqEngineResults.containsAll(lamdaResults)));
	}

	public static List<Student> queryWithLamda(List<Student> students) {
		return students.stream().filter(s -> s.getWorkPlace().equals("����"))
				.filter(s -> (s.getJob().equals("����") || s.getJob().equals("����"))).filter(s -> s.getScore() > 75)
				.sorted((a, b) -> {
					return Double.compare(b.getScore(), a.getScore());
				}).collect(Collectors.toList());
	}

	public static List<Student> queryWithCQEngine(List<Student> students) {

		SQLParser<Student> parser = new SQLParser<Student>(Student.class);
		parser.registerAttribute(new SimpleAttribute<Student, String>("workPlace") {
			@Override
			public String getValue(Student object, QueryOptions queryOptions) {
				return object.getWorkPlace();
			}
		});
		parser.registerAttribute(new SimpleAttribute<Student, String>("job") {
			@Override
			public String getValue(Student object, QueryOptions queryOptions) {
				return object.getJob();
			}
		});
		parser.registerAttribute(new SimpleAttribute<Student, Double>("score") {
			@Override
			public Double getValue(Student object, QueryOptions queryOptions) {
				return object.getScore();
			}
		});

		IndexedCollection<Student> studentIndexed = new ConcurrentIndexedCollection<Student>();
		studentIndexed.addAll(students);

		String sql = "select * from Students where  (workPlace='����' and job in ('����', '����') and score > 75) order by score desc";
		ResultSet<Student> results = parser.retrieve(studentIndexed, sql);
		return Lists.newArrayList(results);
	}

	public static List<Student> generateStudents() {
		String[][] students = { { "1", "κ����", "13772429525", "����", "���������Ƽ���ѧ", "����", "90" },
				{ "2", "����", "18700656989", "����", "��������ѧ", "����", "92" },
				{ "3", "�����", "15829059041", "����", "���������Ƽ���ѧ", "����", "80" },
				{ "4", "���", "18391911942", "����", "����ѧԺ", "����", "70" },
				{ "5", "���ݷ�", "13772449691", "����", "���������Ƽ���ѧ", "ǰ��", "75" },
				{ "6", "ɳ����", "17749191004", "����", "�Ӱ���ѧ", "����", "85" },
				{ "7", "Ф��", "18829291180", "����", "�����ʵ��ѧ", "����", "79.8" },
				{ "8", "ë��ǿ", "13474106574", "����", "���������Ƽ���ѧ", "����", "88" },
				{ "9", "��С��", "18729237563", "����", "��������ѧԺ", "����", "67" },
				{ "10", "������", "18711146399", "����", "��ɳ��", "����", "66" },
				{ "11", "����", "18120151650", "����", "��ɳ��", "����", "60" },
				{ "12", "֣����", "15675155420", "����", "��ɳ��", "����", "74" },
				{ "13", "������", "18192654380", "����", "��������ѧ", "����", "87" },
				{ "14", "����", "18351921889", "����", "��������ѧ", "����", "100" },
				{ "15", "����ΰ", "18119905567", "����", "��������ѧ", "����", "89" },
				{ "16", "��С��", "13892778261", "�������Ͼ���ά��", "��������ѧԺ", "��ά", "60" },
				{ "17", "�Ź�ǿ", "18591838081", "�������Ϻ���ά��", "��������ѧ", "��ά", "60" }, };
		return StudentFactory.create(students);
	}

}
