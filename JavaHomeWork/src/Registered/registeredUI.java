package Registered;

import java.util.*;

public class registeredUI {
	registeredUI(){
		email="example@mail.com";
		password="password";
		repass="repass";
		identify="AAAA";
	}
	String email;
	private String password;
	private String repass;
	private String identify;
	private boolean passwordIdentify(String password) {
		char charPass[]=password.toCharArray();//将String类型转换为char类型的数组 2
		int intLength=password.length();//判断密码长度 3
		boolean isLetter=false,isNumber=false;
		if(intLength>=6 && intLength<=16) {
			for(int i=0;i<intLength;i++)//判断密码是否含有数字和字母
			{
				if((charPass[i]>='a' && charPass[i]<='z')||(charPass[i]>='A'&&charPass[i]<='Z'))
					isLetter=true;
				if(charPass[i]>='0'&&charPass[i]<='9')
					isNumber=true;
			}
			if(isLetter&&isNumber) {
				this.password=password;//将密码存储
				return true;
			}
			else return false;//未输入数字或字母
		}
		else return false;//长度达不到需求
	}
	private boolean repassIdentify(String repass) {
		if(this.password.equals(repass)) {//比较字符串是否相等 5
			this.repass=repass;
			return true;
		}
		return false;
	}
	String randomValidate() {//1
		char[] chr = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
		Random random = new Random();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			buffer.append(chr[random.nextInt(26)]);
		}
		identify=buffer.toString();
		return identify;
	}
	//主函数
	public static void main(String args[]) {
		String password, repass;
		registeredUI reg=new registeredUI();
		Scanner sc=new Scanner(System.in);
		//输入邮箱
		System.out.print("请输入邮箱地址：");
		reg.email=sc.nextLine();
		//输入密码
		System.out.println("请输入密码：");
		password=sc.nextLine();
		while(!reg.passwordIdentify(password)) {
			System.out.println("密码长度必须为6-16位，必须包含数字和字母，请重新输入！");
			password=sc.nextLine();
		}
		//再次输入密码
		System.out.println("请再次输入密码：");
		repass=sc.nextLine();
		while(!reg.repassIdentify(repass)) {
			System.out.println("密码与上个密码不一致，请重新输入");
			repass=sc.nextLine();
		}
		//显示验证码
		System.out.print("验证码： "+reg.randomValidate());
		sc.close();
	}
}

/**   
 * 参考：
 * 1，java生成随机字符串（A-Z0-9) https://blog.csdn.net/coderALEX/article/details/78750880
 * 2，Java中如何取得String型字符的每一位   https://zhidao.baidu.com/question/73342615.html
 * 3，Java初学者：数组，得到数组长度  https://www.cnblogs.com/entry-android/p/5539362.html
 * 4，Java数组的定义和使用   https://www.cnblogs.com/ok932343846/p/6743699.html
 * 5，JAVA如何判断两个字符串是否相等   https://www.cnblogs.com/Dreamice/p/7809605.html
 * 
 */
