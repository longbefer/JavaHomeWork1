package packageStudent;

 class Student{
	private String name;
	private long number;
	private char sex;
	private int age;
	Student(){
		name="xxx";
		number=0;
		sex='ÄÐ';
		age=0;
	}
	Student(long number,String name,char sex,int age){
		this.number=number;this.name=name;this.sex=sex;this.age=age;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age=age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name=name;
	}
	public long getNumber() {
		return number;
	}
	public void setNumber(long number) {
		this.number=number;
	}
	public char getSex() {
		return sex;
	}
	public void setSex(char sex) {
		this.sex=sex;
	}
}

public class Main {
public static void main(String args[]) {
	Student s1=new Student(1101,"Ö®»ª",'ÄÐ',28);
	Student s2=new Student();
	System.out.println(s1.getNumber()+" "+s1.getName()+" "+s1.getSex()+" "+s1.getAge());
	System.out.println(s2.getNumber()+" "+s2.getName()+" "+s2.getSex()+" "+s2.getAge());
}
}