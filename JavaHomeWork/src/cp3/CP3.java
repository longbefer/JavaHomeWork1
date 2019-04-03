package cp3;

public class CP3 {
	private double x,y,z;
	public CP3(){
		this.x=0;this.y=0;this.z=0;
	};
	public CP3(double x,double y,double z){
		this.x=x;this.y=y;this.z=z;
	}
	public static double Distance(CP3 p0,CP3 p1) {
		return Math.sqrt(((p0.x-p1.x)*(p0.x-p1.x))+((p0.y-p1.y)*(p0.y-p1.y))+((p0.z-p1.z)*(p0.z-p1.z)));
	}
	public static double Distance(double x1,double y1,double z1,double x2,double y2,double z2) {
		return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2)+(z1-z2)*(z1-z2));
	}
	public void setPoint(double x,double y,double z) {
		this.x=x;this.y=y;this.z=z;
	}
	//������
	public static void main(String args[])
	{
		CP3 p0=new CP3();
		CP3 p1=new CP3(3,6,9); 
		CP3 p2=new CP3(8,9,10);
		CP3 p3=new CP3();
		p3.setPoint(2, 6, 8);
		System.out.println("p0��x���꣺"+p0.x+"��p0��y���꣺"+p0.y+"��p0��z����"+p0.z);
		System.out.println("p1��x���꣺"+p1.x+"��p1��y���꣺"+p1.y+"��p1��z����"+p1.z);
		System.out.println("p2��x���꣺"+p2.x+"��p2��y���꣺"+p2.y+"��p2��z����"+p2.z);
		System.out.println("p3��x���꣺"+p3.x+"��p3��y���꣺"+p3.y+"��p3��z����"+p3.z);
		System.out.println("p0-p1�ľ��룺"+Distance(p0,p1));
		System.out.println("p0-p2�ľ��룺"+Distance(p0,p2));
		System.out.println("p1-p2�ľ��룺"+Distance(p1,p2));
		System.out.println("(0,0,0)��(3,6,9)�ľ��룺"+Distance(0,0,0,3,6,9));
		System.out.println("(0,0,0)��(8,9,10)�ľ��룺"+Distance(0,0,0,8,9,10));
		System.out.println("(3,6,9)��(8,9,10)�ľ��룺"+Distance(3,6,9,8,9,10));
	}
}
