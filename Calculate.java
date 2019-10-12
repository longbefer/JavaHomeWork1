package com.longbofeng.demo2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
//import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Stack;
import java.util.Date;
//import javax.sound.sampled.DataLine;
import javax.swing.*;

public class Calculate extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//����
	JPanel InputPanel=new JPanel();
	BorderLayout border=new BorderLayout();//���򲼾�
	GridLayout grid=new GridLayout(5,4,0,0);//�������֣�4��5�м��Ϊ0
	//���
	JLabel show=new JLabel("0");
	JButton[] key=new JButton[20];//0-9��С�����e//��������ˣ�+-*/=%
	//����
	Font font=new Font("Ariel",Font.BOLD,24);
	//����
	Stack<Double> NumStack=new Stack<Double>();//����ջ
	Stack<Character> OperaStack=new Stack<Character>();//����ջ
	//�ļ�д��
	File GetDir=new File("");//��ȡĿ¼
	File filePath;//�����ļ�
	File file;//�����ļ�
	BufferedWriter fileOut;//�ļ�д����
	//��ʼ��
	public void init() {
		try {
			getContentPane().setLayout(border);//����������Ϊ���򲼾�
			show.setHorizontalAlignment(JLabel.RIGHT);//����ǩ�ı�����Ϊ�Ҷ���
			show.setPreferredSize(new Dimension(400, 80));//���ñ�ǩ���
			show.setFont(font);//��������
			InputPanel.setLayout(grid);//���̵Ĳ���Ϊ��������
			LoadButton();
			for(int i=0;i<20;i++)
				InputPanel.add(key[i]);
			this.setTitle("Calculation");
			getContentPane().add(show,BorderLayout.NORTH);//����ʾ����ӵ�����
			getContentPane().add(InputPanel,BorderLayout.CENTER);//��������ӵ��в�
			this.pack();
			this.setVisible(true);
			Click();
			filePath=new File(GetDir.getCanonicalPath()+"/Calculate");
			while(!filePath.exists()) filePath.mkdir();//�����ļ���
			file=new File(filePath,"log.txt");
			fileOut=new BufferedWriter(new FileWriter(file));
			SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String otime=time.format(new Date());
			fileOut.write(otime+" \t\n");
		}catch (Exception e) {
			System.out.println("��ʼ��ʧ�ܣ�����");
		}
	}
	
	
	void LoadButton() {
		for(int i=0;i<20;i++) {
			key[i]=new JButton("Button"+i);
			key[i].setPreferredSize(new Dimension(100,100));//����ƫ�ô�С
			key[i].setFont(font);//��������
			key[i].setBackground(Color.WHITE);//���ð�ť��ɫ
		}
		key[0].setText("C");key[1].setText("<-");
		key[2].setText("%");key[3].setText("/");
		key[4].setText("7");key[5].setText("8");
		key[6].setText("9");key[7].setText("x");
		key[8].setText("4");key[9].setText("5");
		key[10].setText("6");key[11].setText("-");
		key[12].setText("1");key[13].setText("2");
		key[14].setText("3");key[15].setText("+");
		key[16].setText("e");key[17].setText("0");
		key[18].setText(".");key[19].setText("=");
	}
	
	private void Click() {
		for(int i=0;i<20;i++)
			key[i].addActionListener(this);//Ϊ���а�ť�����Ӧ�¼�
	}
	
	//�����ж�
	private boolean hasNum=true;
	private boolean hasOpera=false;
	private boolean hasDot=false;//С���㲻��������
	private boolean hasEqual=false;//�Ƿ��Ѿ�������ں�
	private String SaveNum="0";//���ڱ�������
	private String Line="\0";//���ڱ������룬����֮��ȷ��������������
	public void actionPerformed(ActionEvent e) {//������ز�����ʱ�޷�ʵ��
		String str = e.getActionCommand();
		Line+=str;
		if(isNum(str)) {//����Ӧ�ÿ����޸ĵĸ��õ�
			if(hasNum||str==".") {
				if(str=="."&&hasDot)
					if(hasEqual)//��ΪǶ��
						str="0.";
					else str="";
				if(hasOpera&&str==".")
					str="0.";//�����������������������Ϊ0.
				if((str=="."||str=="0.")&&!hasDot)
					hasDot=true;
 			    String prive=show.getText();//��������Ϊ�ж��Ƿ�LabelΪ�ջ����Ѿ�����=
				if(prive=="0"||prive.isEmpty()||prive.equals("0")||hasEqual||hasOpera) {
					if(str==".") prive+=str;
					else prive=str;
				}else {
					if(prive=="e"||prive.contentEquals("e")||str=="e") {
						str="";
						hasDot=true;
					}
					prive+=str;
				}
				str=prive;
			}
			SaveNum=str;
			show.setText(SaveNum);
			hasNum=true;
			hasOpera=false;
			hasEqual=false;//�ԵȺŽ��д���
		}else {//����Ϊ���ֵĵ��룬����Ϊ�����
			try {
			if(hasNum) {//�������������ˣ�Ӧ��������ջ
				//String stackNum=SaveNum;
				String stackNum=show.getText();
				if(SaveNum=="e"||SaveNum.equals("e"))
					stackNum="2.71828183";
				if(stackNum.equals("")||stackNum.isEmpty())
					stackNum="0";
				//Double num=new Double(stackNum);
				Double num=Double.parseDouble(stackNum);
				NumStack.push(num);
				System.out.print(num);
				fileOut.write(stackNum);//����д��log�ļ�
			}else NumStack.push(0.0);//����������⼴ 3*4-=�����ģ����ǻ�3+4/=��Ϊ�����3+4*=��Ϊ3
			if(str=="<-") {//�˸�ţ����������������˸�
				if(hasNum) {
					if(SaveNum=="e"||SaveNum.equals("e"))
						SaveNum="0";
					if(hasEqual)
						SaveNum=show.getText();
					else NumStack.pop();
					SaveNum=SaveNum.substring(0,SaveNum.length()-1);
					//�ж��Ƿ񻹴��ڵ�
					char[] ishasDot=SaveNum.toCharArray();
					for(char t:ishasDot)//��С������д���
						if(t!='.') hasDot=false;
						else {
							hasDot=true;
							break;
						}
					//���������޸�E�����Ĵ���
					if(ishasDot.length!=0)
						if(ishasDot[ishasDot.length-1]=='E') 
							SaveNum=SaveNum.replace('E', '0');
					//�����޸�-��E-����������
					if(ishasDot.length!=0)
						if(ishasDot[ishasDot.length-1]=='-') 
							SaveNum=SaveNum.replace('-', '0');
					if(SaveNum.isEmpty()||SaveNum=="0"||SaveNum.equals("0"))
						SaveNum="0";
					show.setText(SaveNum);
					//Double correntDouble=new Double(SaveNum);
					//NumStack.push(correntDouble);
					hasNum=true;
					System.out.println("\nFix Error,Go Back is "+SaveNum);
					fileOut.write("\nFix Error,Go Back is "+SaveNum+"\t\n");
				}
			}
			if(str=="C") {//�������ջ�Ͳ���ջ
				while(!NumStack.isEmpty())
					NumStack.pop();
				while(!OperaStack.isEmpty())
					OperaStack.pop();
				SaveNum="0";
				hasDot=false;
				hasOpera=false;
				hasNum=true;
				hasEqual=false;
				show.setText("0");
				System.out.println(" clear all ");
				fileOut.write(" clear all \t\n");
			}   
			if(str=="%") {
				//���ھ���ԭ�򣬳���100�ᵼ�¿��ܵ����
				//���鵥���ƶ�С����toMoveDot(String num,int var)
				Double piverNum=NumStack.pop();
				//piverNum=piverNum/100.0;
				piverNum=toMoveUpDot(piverNum,2);//�ĳ�ֱ���ƶ�С����
				String Percent=piverNum.toString();//��doubleת��ΪString���ͣ�����Label����ʾ
				show.setText(Percent);
				NumStack.push(piverNum);
				hasNum=true;
				System.out.println("%");
				fileOut.write("%");
			}
			if(str=="/"||str=="x"||str=="-"||str=="+") {
				//1 ȥ����/0��Ӱ��
				//2 ʹ��ջ����
				//3 ��ѭ�������
				if(!hasOpera) {
				if(str=="x")
					str="*";//��X�ĳɿ���ʶ���*
				char[] stackChar=new char[1];
				str.getChars(0, str.length(), stackChar, 0);
				Calculated(NumStack,OperaStack,stackChar[0]);
				//OperaStack.push(stackChar[0]);
				hasNum=false;
				hasOpera=true;
				hasDot=false;
				}
			}
			if(str=="=") {
				String showTextW=show.getText();
				if(showTextW=="e"||showTextW.equals("e"))//eΪ����e
					showTextW="2.71828183";
				//Double result = new Double(showTextW);
				Double result=Double.parseDouble(showTextW);
				while(!OperaStack.isEmpty())//ѭ������ջ��ֱ���������������
					result=Calculated(NumStack,OperaStack,'#');
				while(!NumStack.isEmpty())
					NumStack.pop();
				show.setText(result.toString());
				hasNum=true;
				hasOpera=false;
				hasDot=true;
				hasEqual=true;
				SaveNum=result.toString();
				System.out.println("="+result);
				fileOut.write("="+result+"\t\n");
				fileOut.flush();
			}//��������
			}
			catch (Exception e1) {
				hasNum=false;
				hasOpera=false;
				hasDot=false;
				System.out.print(e1+"����\n");
				System.out.print("�������⣺"+Line);
				show.setText("δ֪������鿴Lineȷ��ԭ��");
			}	
		}
		
	}
	
	public boolean isNum(String str) {
		String[] temp= {"0","1","2","3","4","5","6","7","8","9","e","."};
		for(int i=0;i<temp.length;i++)
			if(temp[i]==str)
				return true;
		return false;
	}

	double cal(Stack<Double> S1, Stack<Character> Ca)
	{
		char opera = Ca.peek();
		double nextNum = S1.peek();
		//double prorNum = *(S1.top - 2);//Attention
		double prorNum=S1.get(S1.size()-2);
		switch (opera)
		{
		case '+':return prorNum + nextNum;
		case '-':return prorNum - nextNum;
		case '*':return prorNum * nextNum;
		case '/':return prorNum / nextNum;
		}
		return ERROR;
	}
	
	public double Calculated(Stack<Double> num, Stack<Character> in, char out) throws IOException {
		double newNum;
		char prio = popr(in, out);
		switch (prio)
		{
		case '>'://ջ�ڴ���ջ��
		{
			newNum = cal(num, in);
			in.pop();//��������
			num.pop(); num.pop();//�����������
			num.push(newNum); //���µ�����ѹ��ջ
			if(out!='#')
				in.push(out);//�ѷ���ѹ��ջ
			if(out!='#') {
				System.out.print(out);
				fileOut.write(out);
			}
			return newNum;
		}
		case '<'://ջ��С��ջ�⣬��ջ���������ջ
		{
			in.push(out);
			System.out.print(out);
			fileOut.write(out);
			return 0.0;
		}
		case '=':
		{
			in.pop();
			return 0.0;
		}
		default:
		case ERROR:return ERROR;
		}
	}
	
	char popr(Stack<Character> inS, char outOpera)//�ж����ȼ�
	{
		char inTop;
		if(inS.isEmpty())
			inTop='#';
		else inTop= inS.peek();
		switch (outOpera)
		{
		case '+':
		case '-':
			if (inTop == '+' || inTop == '-' ||
				inTop == '*' || inTop == '/') return '>';
			else if (inTop == '#' || inTop == '(') return '<';
			else return ERROR;
		case '*':
		case '/':
			if (inTop == '+' || inTop == '-' || inTop == '(' || inTop == '#')
				return '<';
			else if (inTop == '*' || inTop == '/') return '>';
			else return ERROR;
		case '(':
			if (inTop == '+' || inTop == '-' || inTop == '*' || 
				inTop == '/' || inTop == '(' || inTop == '#') return '<';
		case ')':
			if (inTop == '+' || inTop == '-' ||
				inTop == '*' || inTop == '/') return '>';
			else if (inTop == '(') return '=';
			else return ERROR;
		case '#':
			if (inTop == '+' || inTop == '-' || inTop == '*' ||
				inTop == '/' || inTop == '#') return '>';
			else if (inTop == '(') return ERROR;//�������ࣨ
			else return ERROR;
		default: return ERROR;
		}
	}
	
	public double toMoveUpDot(Double num,int place)
	{
		String strnum=num.toString();
		char[] charnum=strnum.toCharArray();
		char[] correctnum;
		//�ж��Ƿ���С����
		boolean Move_Dot=false;
		boolean addZero=false;
		boolean has_Minus_sign=false;//�и���
		int Dot_Postion=0;//��һλ����ΪС����,Ϊ�������е�λ��
		if(charnum[0]=='-') {
			char[] charnum1=new char[charnum.length-1];
			for(int i=0;i<charnum.length-1;i++)
				charnum1[i]=charnum[i+1];
			has_Minus_sign=true;
			charnum=charnum1;
		}
		for(int i=0;i<charnum.length;i++)
			if(charnum[i]=='.') {
				Move_Dot=true;
				Dot_Postion=i;
				break;
			}
		if(Move_Dot)//��С����
		{
			//�е���ȥ�㣬�ƶ�����
			for(int i=Dot_Postion;i<charnum.length-1;i++)
				charnum[i]=charnum[i+1];
			correctnum=new char[charnum.length-1];
			//correctnum=charnum;//ʡ�Ե����һλ
			for(int i=0;i<charnum.length-1;i++)
				correctnum[i]=charnum[i];
		}else {
			Dot_Postion=charnum.length-1;
			correctnum=charnum;
		}
		Double resultnum;
		String resStr;
		if(place<Dot_Postion)
			addZero=false;
		else addZero=true;
		if(addZero) {//����
			int how_zero=place-Dot_Postion;//��Ҫ��ӵ�0�м���
			char[] correctnum1=new char[how_zero+correctnum.length+2];
			correctnum1[0]='0';correctnum1[1]='.';//���0.
			for(int i=0;i<how_zero;i++)
				correctnum1[2+i]='0';
			for(int i=2+how_zero;i<correctnum1.length;i++)
				correctnum1[i]=correctnum[i-how_zero-2];
			resStr=new String(correctnum1);
			//resultnum=new Double(resStr);//�޸�����
			resultnum=Double.parseDouble(resStr);
		}else {//����Ҫ����
			char[] correctnum1=new char[correctnum.length+1];
			for(int i=0;i<correctnum.length;i++)
				correctnum1[i]=correctnum[i];
			correctnum1[correctnum.length]='0';
			int Dot_Position_End=Dot_Postion-place;
			for(int i=correctnum1.length-1;i>Dot_Position_End;i--) {
				correctnum1[i]=correctnum1[i-1];
			}
			correctnum1[Dot_Position_End]='.';
			resStr=new String(correctnum1);
			//resultnum=new Double(resStr);
			resultnum=Double.parseDouble(resStr);
		}
		if(has_Minus_sign) {//�и���
			char[] resChar=resStr.toCharArray();
			for(int i=resChar.length-1;i>0;i--) {
				resChar[i]=resChar[i-1];
			}
			resChar[0]='-';
			resStr=new String(resChar);
			//resultnum=new Double(resStr);
			resultnum=Double.parseDouble(resStr);
		}
		return resultnum;
	}
	
	public static void main(String args[]) {
		Calculate c1=new Calculate();
		c1.init();
	}
}
