package cn.leisore._20170502;

public class Student {

	private int id;
	private String name;
	private String phoneNumber;
	private String workPlace;
	private String schoolName;
	private String job;
	private double score;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getWorkPlace() {
		return workPlace;
	}

	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
	
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", phoneNumber=" + phoneNumber + ", workPlace=" + workPlace
				+ ", schoolName=" + schoolName + ", job=" + job + ", score=" + score + "]";
	}

}
