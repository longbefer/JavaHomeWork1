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
	//布局
	JPanel InputPanel=new JPanel();
	BorderLayout border=new BorderLayout();//区域布局
	GridLayout grid=new GridLayout(5,4,0,0);//按键布局，4行5列间距为0
	//组件
	JLabel show=new JLabel("0");
	JButton[] key=new JButton[20];//0-9和小数点和e//清除，后退，+-*/=%
	//界面
	Font font=new Font("Ariel",Font.BOLD,24);
	//运算
	Stack<Double> NumStack=new Stack<Double>();//数字栈
	Stack<Character> OperaStack=new Stack<Character>();//操作栈
	//文件写入
	File GetDir=new File("");//获取目录
	File filePath;//创建文件
	File file;//创建文件
	BufferedWriter fileOut;//文件写入流
	//初始化
	public void init() {
		try {
			getContentPane().setLayout(border);//设置主布局为区域布局
			show.setHorizontalAlignment(JLabel.RIGHT);//将标签文本设置为右对齐
			show.setPreferredSize(new Dimension(400, 80));//设置标签宽高
			show.setFont(font);//设置字体
			InputPanel.setLayout(grid);//键盘的布局为按键布局
			LoadButton();
			for(int i=0;i<20;i++)
				InputPanel.add(key[i]);
			this.setTitle("Calculation");
			getContentPane().add(show,BorderLayout.NORTH);//将显示栏添加到北部
			getContentPane().add(InputPanel,BorderLayout.CENTER);//将按键添加到中部
			this.pack();
			this.setVisible(true);
			Click();
			filePath=new File(GetDir.getCanonicalPath()+"/Calculate");
			while(!filePath.exists()) filePath.mkdir();//建立文件夹
			file=new File(filePath,"log.txt");
			fileOut=new BufferedWriter(new FileWriter(file));
			SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String otime=time.format(new Date());
			fileOut.write(otime+" \t\n");
		}catch (Exception e) {
			System.out.println("初始化失败！！！");
		}
	}
	
	
	void LoadButton() {
		for(int i=0;i<20;i++) {
			key[i]=new JButton("Button"+i);
			key[i].setPreferredSize(new Dimension(100,100));//设置偏好大小
			key[i].setFont(font);//设置字体
			key[i].setBackground(Color.WHITE);//设置按钮颜色
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
			key[i].addActionListener(this);//为所有按钮添加响应事件
	}
	
	//动作判断
	private boolean hasNum=true;
	private boolean hasOpera=false;
	private boolean hasDot=false;//小数点不能有两个
	private boolean hasEqual=false;//是否已经输入等于号
	private String SaveNum="0";//用于保存数字
	private String Line="\0";//用于保存输入，方便之后确认输入错误的问题
	public void actionPerformed(ActionEvent e) {//负数相关操作暂时无法实现
		String str = e.getActionCommand();
		Line+=str;
		if(isNum(str)) {//下面应该可以修改的更好点
			if(hasNum||str==".") {
				if(str=="."&&hasDot)
					if(hasEqual)//改为嵌套
						str="0.";
					else str="";
				if(hasOpera&&str==".")
					str="0.";//在输入运算符后，在输入点更改为0.
				if((str=="."||str=="0.")&&!hasDot)
					hasDot=true;
 			    String prive=show.getText();//下面的语句为判断是否Label为空或者已经输入=
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
			hasEqual=false;//对等号进行处理
		}else {//上面为数字的导入，下面为计算符
			try {
			if(hasNum) {//即输入完数字了，应该入数字栈
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
				fileOut.write(stackNum);//将其写入log文件
			}else NumStack.push(0.0);//解决输入问题即 3*4-=这样的，但是会3+4/=会为无穷和3+4*=会为3
			if(str=="<-") {//退格号，将输入错误的数字退格
				if(hasNum) {
					if(SaveNum=="e"||SaveNum.equals("e"))
						SaveNum="0";
					if(hasEqual)
						SaveNum=show.getText();
					else NumStack.pop();
					SaveNum=SaveNum.substring(0,SaveNum.length()-1);
					//判断是否还存在点
					char[] ishasDot=SaveNum.toCharArray();
					for(char t:ishasDot)//对小数点进行处理
						if(t!='.') hasDot=false;
						else {
							hasDot=true;
							break;
						}
					//下面用于修复E带来的错误
					if(ishasDot.length!=0)
						if(ishasDot[ishasDot.length-1]=='E') 
							SaveNum=SaveNum.replace('E', '0');
					//下面修复-或E-带来的问题
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
			if(str=="C") {//清空数字栈和操作栈
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
				//由于精度原因，除以100会导致可能的溢出
				//建议单纯移动小数点toMoveDot(String num,int var)
				Double piverNum=NumStack.pop();
				//piverNum=piverNum/100.0;
				piverNum=toMoveUpDot(piverNum,2);//改成直接移动小数点
				String Percent=piverNum.toString();//将double转变为String类型，并在Label中显示
				show.setText(Percent);
				NumStack.push(piverNum);
				hasNum=true;
				System.out.println("%");
				fileOut.write("%");
			}
			if(str=="/"||str=="x"||str=="-"||str=="+") {
				//1 去除？/0的影响
				//2 使用栈运算
				//3 遵循运算规则
				if(!hasOpera) {
				if(str=="x")
					str="*";//将X改成可以识别的*
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
				if(showTextW=="e"||showTextW.equals("e"))//e为对数e
					showTextW="2.71828183";
				//Double result = new Double(showTextW);
				Double result=Double.parseDouble(showTextW);
				while(!OperaStack.isEmpty())//循环遍历栈，直至结束，计算完成
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
			}//结束等于
			}
			catch (Exception e1) {
				hasNum=false;
				hasOpera=false;
				hasDot=false;
				System.out.print(e1+"问题\n");
				System.out.print("输入问题："+Line);
				show.setText("未知错误，请查看Line确定原因");
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
		case '>'://栈内大于栈外
		{
			newNum = cal(num, in);
			in.pop();//清除运算符
			num.pop(); num.pop();//清除数字两个
			num.push(newNum); //将新的数字压入栈
			if(out!='#')
				in.push(out);//把符号压入栈
			if(out!='#') {
				System.out.print(out);
				fileOut.write(out);
			}
			return newNum;
		}
		case '<'://栈内小于栈外，将栈外操作符入栈
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
	
	char popr(Stack<Character> inS, char outOpera)//判断优先级
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
			else if (inTop == '(') return ERROR;//表明多余（
			else return ERROR;
		default: return ERROR;
		}
	}
	
	public double toMoveUpDot(Double num,int place)
	{
		String strnum=num.toString();
		char[] charnum=strnum.toCharArray();
		char[] correctnum;
		//判断是否有小数点
		boolean Move_Dot=false;
		boolean addZero=false;
		boolean has_Minus_sign=false;//有负号
		int Dot_Postion=0;//第一位不能为小数点,为在数组中的位置
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
		if(Move_Dot)//有小数点
		{
			//有点则去点，移动数字
			for(int i=Dot_Postion;i<charnum.length-1;i++)
				charnum[i]=charnum[i+1];
			correctnum=new char[charnum.length-1];
			//correctnum=charnum;//省略掉最后一位
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
		if(addZero) {//加零
			int how_zero=place-Dot_Postion;//需要添加的0有几个
			char[] correctnum1=new char[how_zero+correctnum.length+2];
			correctnum1[0]='0';correctnum1[1]='.';//添加0.
			for(int i=0;i<how_zero;i++)
				correctnum1[2+i]='0';
			for(int i=2+how_zero;i<correctnum1.length;i++)
				correctnum1[i]=correctnum[i-how_zero-2];
			resStr=new String(correctnum1);
			//resultnum=new Double(resStr);//修复如下
			resultnum=Double.parseDouble(resStr);
		}else {//不需要加零
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
		if(has_Minus_sign) {//有负号
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
