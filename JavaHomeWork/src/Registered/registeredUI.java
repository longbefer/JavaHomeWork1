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
		char charPass[]=password.toCharArray();//��String����ת��Ϊchar���͵����� 2
		int intLength=password.length();//�ж����볤�� 3
		boolean isLetter=false,isNumber=false;
		if(intLength>=6 && intLength<=16) {
			for(int i=0;i<intLength;i++)//�ж������Ƿ������ֺ���ĸ
			{
				if((charPass[i]>='a' && charPass[i]<='z')||(charPass[i]>='A'&&charPass[i]<='Z'))
					isLetter=true;
				if(charPass[i]>='0'&&charPass[i]<='9')
					isNumber=true;
			}
			if(isLetter&&isNumber) {
				this.password=password;//������洢
				return true;
			}
			else return false;//δ�������ֻ���ĸ
		}
		else return false;//���ȴﲻ������
	}
	private boolean repassIdentify(String repass) {
		if(this.password.equals(repass)) {//�Ƚ��ַ����Ƿ���� 5
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
	//������
	public static void main(String args[]) {
		String password, repass;
		registeredUI reg=new registeredUI();
		Scanner sc=new Scanner(System.in);
		//��������
		System.out.print("�����������ַ��");
		reg.email=sc.nextLine();
		//��������
		System.out.println("���������룺");
		password=sc.nextLine();
		while(!reg.passwordIdentify(password)) {
			System.out.println("���볤�ȱ���Ϊ6-16λ������������ֺ���ĸ�����������룡");
			password=sc.nextLine();
		}
		//�ٴ���������
		System.out.println("���ٴ��������룺");
		repass=sc.nextLine();
		while(!reg.repassIdentify(repass)) {
			System.out.println("�������ϸ����벻һ�£�����������");
			repass=sc.nextLine();
		}
		//��ʾ��֤��
		System.out.print("��֤�룺 "+reg.randomValidate());
		sc.close();
	}
}

/**   
 * �ο���
 * 1��java��������ַ�����A-Z0-9) https://blog.csdn.net/coderALEX/article/details/78750880
 * 2��Java�����ȡ��String���ַ���ÿһλ   https://zhidao.baidu.com/question/73342615.html
 * 3��Java��ѧ�ߣ����飬�õ����鳤��  https://www.cnblogs.com/entry-android/p/5539362.html
 * 4��Java����Ķ����ʹ��   https://www.cnblogs.com/ok932343846/p/6743699.html
 * 5��JAVA����ж������ַ����Ƿ����   https://www.cnblogs.com/Dreamice/p/7809605.html
 * 
 */
