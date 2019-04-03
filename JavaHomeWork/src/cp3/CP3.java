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
	//调用类
	public static void main(String args[])
	{
		CP3 p0=new CP3();
		CP3 p1=new CP3(3,6,9); 
		CP3 p2=new CP3(8,9,10);
		CP3 p3=new CP3();
		p3.setPoint(2, 6, 8);
		System.out.println("p0的x坐标："+p0.x+"，p0的y坐标："+p0.y+"，p0的z坐标"+p0.z);
		System.out.println("p1的x坐标："+p1.x+"，p1的y坐标："+p1.y+"，p1的z坐标"+p1.z);
		System.out.println("p2的x坐标："+p2.x+"，p2的y坐标："+p2.y+"，p2的z坐标"+p2.z);
		System.out.println("p3的x坐标："+p3.x+"，p3的y坐标："+p3.y+"，p3的z坐标"+p3.z);
		System.out.println("p0-p1的距离："+Distance(p0,p1));
		System.out.println("p0-p2的距离："+Distance(p0,p2));
		System.out.println("p1-p2的距离："+Distance(p1,p2));
		System.out.println("(0,0,0)到(3,6,9)的距离："+Distance(0,0,0,3,6,9));
		System.out.println("(0,0,0)到(8,9,10)的距离："+Distance(0,0,0,8,9,10));
		System.out.println("(3,6,9)到(8,9,10)的距离："+Distance(3,6,9,8,9,10));
	}
}
