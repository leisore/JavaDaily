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
 * 获取所有：
 * <ol>
 * <li>工作地点在西安</li>
 * <li>职位为开发或测试</li>
 * <li>考核成绩大于75</li></li>
 * </ol>
 * 同事名单,以成绩降序排名
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
			// Java7之前
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
		return students.stream().filter(s -> s.getWorkPlace().equals("西安"))
				.filter(s -> (s.getJob().equals("开发") || s.getJob().equals("测试"))).filter(s -> s.getScore() > 75)
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

		String sql = "select * from Students where  (workPlace='西安' and job in ('开发', '测试') and score > 75) order by score desc";
		ResultSet<Student> results = parser.retrieve(studentIndexed, sql);
		return Lists.newArrayList(results);
	}

	public static List<Student> generateStudents() {
		String[][] students = { { "1", "魏东冬", "13772429525", "西安", "西安建筑科技大学", "开发", "90" },
				{ "2", "周敏", "18700656989", "西安", "陕西理工大学", "开发", "92" },
				{ "3", "宋泽浩", "15829059041", "西安", "西安建筑科技大学", "开发", "80" },
				{ "4", "李楠", "18391911942", "西安", "商洛学院", "开发", "70" },
				{ "5", "常逸飞", "13772449691", "西安", "西安建筑科技大学", "前端", "75" },
				{ "6", "沙翔宇", "17749191004", "西安", "延安大学", "开发", "85" },
				{ "7", "肖兴", "18829291180", "西安", "西安邮电大学", "开发", "79.8" },
				{ "8", "毛文强", "13474106574", "西安", "西安建筑科技大学", "开发", "88" },
				{ "9", "杨小翠", "18729237563", "苏州", "宝鸡文理学院", "测试", "67" },
				{ "10", "苏正青", "18711146399", "苏州", "长沙理工", "测试", "66" },
				{ "11", "王娇", "18120151650", "苏州", "长沙理工", "测试", "60" },
				{ "12", "郑友攀", "15675155420", "苏州", "长沙理工", "开发", "74" },
				{ "13", "刘海滨", "18192654380", "苏州", "西安理工大学", "开发", "87" },
				{ "14", "马俊民", "18351921889", "北京", "西安理工大学", "开发", "100" },
				{ "15", "王国伟", "18119905567", "北京", "西安理工大学", "开发", "89" },
				{ "16", "贺小乐", "13892778261", "西安（南京运维）", "宝鸡文理学院", "运维", "60" },
				{ "17", "张国强", "18591838081", "西安（上海运维）", "西安理工大学", "运维", "60" }, };
		return StudentFactory.create(students);
	}

}
