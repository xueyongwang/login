package com.gs;

import java.util.ArrayList;
/**
 * 这个类经过调整优化过结构
 * 这个类服务于登记的由sql,变量,函数组成的表达式的解析,最终形成表达式树 存放在 FinalTree[][] 中
 * 计算的方法getRresultViaFinalTree()要在各种变量赋值后才能调用.
 * @author Administrator
 *
 */
public class ExpressionTestClass1 {
	
	public static void main(String[] args){
		ExpressionTestClass1 et = new ExpressionTestClass1();
		String str = "100*0.2*((1.0+4.0+3.0+4.0+3.0+1.0)/(1.0+4.0+3.0+4.0+3.0+1.0+4.0+7.0+7.0+3.0))*(0.4/(0.5+0.7+0.4))+1.0/(1.0+1.3+1.0+1.2+1.2)*0.7*100";
		//String str = "a+b+c+f+(a/(n*f+n))";
		et.work0utKh(str);
		System.out.println(et.getRresultViaFinalTree());// 必须为变量赋值后才能调用
		//String finalRe=getRresultViaFinalTree();//当赋值后可进行计算
		//outSYSO();//显示最终的结果树
		//System.out.println("解析算出的结果=="+result);
		System.out.println("------");
		for (int i = 0; i < et.dyArr.length; i++) {
			for (int j = 0; j < et.dyArr[0].length; j++) {
			System.out.print(et.dyArr[i][j]+"\t");	
			}
			System.out.println();
		}
	}
	
	/**
	 * 表达式解析
	 * 1，替换表达式里函数
	 * 2，替换表达式里括号
	 * 3，替换表达式中优先级高的运算符*和/
	 * 4，每次替换后将替换的数据插入数组形成数据表
	 */
	private  String[][] dyArr = null;		
	private  String[][] gsInfo = null;		//公式数据数组
	private  String[][] oneLevel = null;	//用于同一级别的运算
	private  String[][] FinalTree = null;	//最终形成的表达式树
	private  int dyIndex = 0;				//全局变量,用于拿字符替换掉已经处理的式子
	
	/**
	 * 根据表达式将括号化简,当为两级 优先级时候计算
	 * @return
	 */
	public String[][] work0utKh(String exp){
		initializateFinalTree();
		if(judgeExistKh(exp)){//存在括号
			char[] nowStrA=exp.toCharArray();//将字符串拆解
			for (int l = 0; l < nowStrA.length; l++) {
				ArrayList<Integer> positionList = new ArrayList<Integer>();//截取括号内容用
				nowStrA=exp.toCharArray();//将字符串拆解
				for (int i = 0; i < nowStrA.length; i++) {
					if(nowStrA[i]=='('){
						positionList.add(i);
					}else if(nowStrA[i]==')'){
						int start = positionList.get(positionList.size()-1);
						int end = i+1;
						String khContent=exp.substring(start , end);//截取到括号的内容(a+b)
						String khConInner=khContent.substring(1,khContent.length()-1);//把括号的去除(a+b)变成a+b
						System.out.println("---旧---"+khContent);
						//调用方法进行计算当前循环到的最里层数据
						String computed=computeViaArr(khConInner);
						exp = exp.replace(khContent,computed);//对表达式进行替换
						System.out.println("---新---"+exp);
						positionList.remove(positionList.size()-1);//将上面的元素删掉
						break;
					}
				}
			}
			exp=computeViaArr(exp);//将括号全部替换完成后,最终形式A+B+C
		}else{//不存在括号
			exp=computeViaArr(exp);
		}
		addToFinalTree(exp,null,"fPoint");////将父节点增加到节点树中最终的封口
		makBh();//定义一个方法用来向其中增加编号
		//getRresultViaFinalTree();//计算最后结果,只能当表达式有值时候才能计算
		outSYSO();//最终的结果显示
		
		return FinalTree;
	}
	
	/**
	 * 计算括号里面的内容的方法,可以计算到单个值,单个优先级,两级优先级的表达式,最终形成表达式树放到FinalTree中
	 * @return
	 */
	private  String computeViaArr(String str){
		String re="";
		//String result=replaceGs(str);
		if(isOneLeve(str)==true){//如果是同一个级别的表达式
			if(judgeExistfh(str)){//不存在任何运算符号,为单个值 a
				re=str;
			}else{//存在单个级别的运算表达式例如a+b
				re=computeOneLevel(str);//单个优先级的计算方法
			}
		}else{//如果包含两个级别的运算,例如 a+b*c
			re = computeTwoLevel(str);
		}
		return re;
	}
	/**
	 * 计算仅存在一个优先级的方法	a+b
	 * @return
	 */
	private String computeOneLevel(String exp){
		initializeOneLevelArr();//每次调用的时候初始化
		char[] chArr =  exp.toCharArray();
		int lastIndex = 0;
		for(int i=0;i<chArr.length;i++){
			char ch = chArr[i];
			if(isChar(ch)){
				String strBl = exp.substring(lastIndex,i);
				addToOneLevelArr(strBl,ch+"");//--------------
				lastIndex = i+1;
			}
		}
		//处理最后变量
		String lastBl = exp.substring(lastIndex,exp.length());///*/*/*/*/*/通过此可以发现前面写的有问题
		addToOneLevelArr(lastBl,null);
		String re=getReplace();
		for (int i = 1; i < oneLevel.length; i++) {
			System.out.print(oneLevel[i][0]+"\t");
			System.out.println(oneLevel[i][1]+"\t");
			addToFinalTree(oneLevel[i][0],oneLevel[i][1],re);
		}
		return re;
	}
	/**
	 * 计算仅存在两个优先级的方法	a+b*c
	 * @param exp
	 * @return
	 */
	public String computeTwoLevel(String exp){
		String re=null;
		re = replaceHighYxj(exp);
		initializationGsInfo(re);	//初始化gsInfo数组
		for(int i=0;i<gsInfo.length;i++){
			String gs = gsInfo[i][3];
			if(gs != ""){//表现,控制错误
				makGsInfoArr(gs);	//拆分表达式，向gsInfo数组中添加信息,形成最终的表达式树
			}
		}
		re=getReplace();//获取一个变量用于进行替换
		//定义生成树的过程,由gsInfo数组形成最终所要的FianlTree数组
		for(int i=0;i<gsInfo.length;i++){
			if(gsInfo[i][4].equals("2")){
				//获取父的方法,
				String fname=gsInfo[Integer.parseInt(gsInfo[i][1])-1][2];
				//增肌数组的方法
				addToFinalTree(gsInfo[i][2],gsInfo[i][5],fname);
			}
		}
		for(int i=0;i<gsInfo.length;i++){
			if(gsInfo[i][4].equals("2")){//当前级别为2
				//不处理
			}else if(!(gsInfo[i][0].equals("1"))){
				addToFinalTree(gsInfo[i][2],gsInfo[i][5],re);
			}
		}
		//System.out.println("multiLevelResult=="+gsInfo[0][2]);
		return re;
	}
	/**
	 * 替换优先级高的*和/
	 * @param gsStr
	 * @return
	 */
	private  String replaceHighYxj(String gsStr){
		ArrayList<Integer> positionList = getPosition(gsStr);  
		String newgsStr = gsStr;
		if(positionList.size()>0){
			int leftIndex = 0,rightIndex = 0;
			for(int i=0;i<positionList.size();i++){
				rightIndex = positionList.get(i); 
				if(rightIndex != 0){
					String subStr = gsStr.substring(leftIndex, rightIndex);
					//System.out.println("subStr=="+subStr);
					newgsStr = makReplace(newgsStr,subStr,"3");
					leftIndex = rightIndex+1;     //每次替换后将当前的符号位置赋给下次替换的左侧Index
				}
			}
		}
		return newgsStr;
	}
	
	/**
	 * 获取表达式中优先级为1的运算符+-号在表达式中的位置
	 * 1，判断当前表达式里所有运算符的优先级是否相同，若相同则无需替换
	 * 2，若不相同,则获取表达式中+-的索引位置
	 * @param gsStr
	 * @return
	 */
	private  ArrayList<Integer> getPosition(String gsStr){
		ArrayList<Integer> positionList = new ArrayList<Integer>();
		if(!isOneLeve(gsStr)){  //判断当前表达式里运算符是否为同一级别
			positionList.add(0);     //将表达式的起始索引位置加入
			char[] arrA = gsStr.toCharArray();
			for(int i=0; i < arrA.length; i++){
				char ch = arrA[i];
				if(getYxj(ch) == 1){     //获取表达式中运算符优先级为1的位置,即+ -位置
					positionList.add(i);
				}
			}
			positionList.add(arrA.length);    //加入表达式的结尾的索引位置
		}
		return positionList;
	}
	
	/**
	 * 判断表达式的运算符是不是同一个级别
	 * @param gsStr
	 */
	private  Boolean isOneLeve(String gsStr){
		boolean re = true;
		int lastIndex = 0;
		char[] arrA = gsStr.toCharArray();
		for(int i=0; i < arrA.length; i++){
			char ch = arrA[i];
			if(isChar(ch)){
				if(lastIndex == 0){
					lastIndex = i;
				}else{
					if(getYxj(arrA[lastIndex]) != getYxj(ch)){
						re = false;
					}
				}
			}
		}
		return re;
	}
	
	/**
	 * 判断是否为运算符//-----此方法待扩充,运算符不局限与算术运算符
	 * @param ch
	 * @return
	 */
	private  Boolean isChar(char ch){
		boolean re = false;
		if(ch == '+' || ch == '-' || ch == '*' || ch == '/' ) re = true;
		return re;
	}
	/**
	 * 比较优先级
	 * @param op
	 * @return
	 */
	private  int getYxj(char op){
		switch(op){
			case '+':
			case '-':
				return 1;
			case '*':
			case '/':
				return 2;
			case '(':
			case '@':
			default:
				return 0;
		}
	}
	
	/**
	 * 判断当前表达式中是否存在优先级为2的运算符,即是否存在  *和/
	 * @param gsStr
	 * @return
	 */
	private  boolean isHaveHigeYxj(String gsStr){
		boolean re = false;
		char[] arrA = gsStr.toCharArray();
		for(int i=0; i < arrA.length; i++){
			char ch = arrA[i];
			if(getYxj(ch) == 2){
				re = true;
			}
		}
		//System.out.println("re==="+re);
		return re;
	}
	
	/**
	 * 进行替换
	 * @param gsStr
	 * @param replaceStr	//截取[]后形成的数组中的单个内容
	 * @param replaceLx
	 * @return
	 */
	private  String makReplace(String gsStr,String replaceStr,String replaceLx){
		String re = gsStr;
		String newReStr = getReplace();            //获取新的替换量
		if(!replaceLx.equals("3")){ //处理函数和括号的截取
			re = re.replace(replaceStr, newReStr);         //替换
			int index = re.indexOf(newReStr);             //定位替换的位置 
			replaceStr = dealSubString(index,re);         //获取替换位的实际串
			re = re.replace(replaceStr, newReStr);            //将新的替换量替换实际串
			addToDyArr(newReStr, replaceStr);
		}else{
			if(isHaveHigeYxj(replaceStr)){
				re = re.replace(replaceStr, newReStr);            //将新的替换量替换实际串
				addToDyArr(newReStr, replaceStr);
			}
		}
		return re;
	}
	
	/**
	 * 将替换的变量存入对应数组中，数组结构：name,value的对应
	 * @param newReStr
	 * @param oldStr
	 */
	private  void addToDyArr(String newReStr,String oldStr){
		String[][] dyArr1 = new String[1][2];
		dyArr1[0][0] = newReStr;
		dyArr1[0][1] = oldStr;
		if(dyArr == null){
			dyArr = dyArr1;
		}else{
			dyArr = makNewArr(dyArr,dyArr1);
		}
	}
	
	/**
	 * 向最终形成的表达式树中添加数据
	 * @param blName	//变量名(运算内容)
	 * @param fh		//符号
	 * @param fname		//父的名
	 * @return
	 */
	private  void addToFinalTree(String blName,String fh,String fname){
		String[][] gsInfo1 = new String[1][6];
		gsInfo1[0][0] = "";      
		gsInfo1[0][1] = "";    		
		gsInfo1[0][2] = blName;     			   
		gsInfo1[0][3] = fh;     	
		gsInfo1[0][4] = fname;     	    					
		FinalTree = makNewArr(FinalTree,gsInfo1);
	}

	/**
	 * 将两个宽度相同的数组合成一个完整的长数组
	 * @param oldArr1
	 * @param oldArr2
	 * @return
	 */
	private  String[][] makNewArr(String[][] oldArr1,String[][] oldArr2){
		int length = oldArr1.length + oldArr2.length;
		int width = oldArr1[0].length; 
		String[][] newArr = new String[length][width];
		for(int i=0;i<length;i++){
			for(int j=0;j<width;j++){
				if(i<oldArr1.length){
					newArr[i][j] = oldArr1[i][j];
				}else{
					int arr1Length = oldArr1.length;
					newArr[i][j] = oldArr2[i-arr1Length][j];
				}
			}
		}
		return newArr;
	}
	
	/**
	 * 形成gsInfo数组：zid,fid,name,value,jb,fh
	 * @param gsStr
	 */
	private void makGsInfoArr(String gsStr){
		char[] chArr =  gsStr.toCharArray();
		int lastIndex = 0;
		for(int i=0;i<chArr.length;i++){
			char ch = chArr[i];
			if(isChar(ch)){
				String strBl = gsStr.substring(lastIndex,i);
				addToGsInfoArr(strBl,gsStr,ch+"");//--------------
				lastIndex = i+1;
			}
		}
		//处理最后变量
		String lastBl = gsStr.substring(lastIndex,gsStr.length());
		addToGsInfoArr(lastBl,gsStr,null);
	}
	
	/**
	 * 向gsInfo 数组中添加记录
	 * @param blName
	 * @param fValue
	 * @param fh
	 */
	private void addToGsInfoArr(String blName,String fValue,String fh){
		String fMes = searchFMes(fValue);
		String[][] gsInfo1 = new String[1][6];
		gsInfo1[0][0] = (gsInfo.length+1) +"";      //zid
		gsInfo1[0][1] = fMes.split(",")[0];    		//fid
		gsInfo1[0][2] = blName;     			    //name
		gsInfo1[0][3] = getDyValue(blName);     	//value
		gsInfo1[0][4] = fMes.split(",")[1];     	//jb
		gsInfo1[0][5] = fh;     					//fh
		gsInfo = makNewArr(gsInfo,gsInfo1);
	}
	
	/**
	 * 从gsInfo数组中查找父的信息
	 * @param fValue
	 * @return
	 */
	private  String searchFMes(String fValue){
		String re = "";
		for(int i=0;i<gsInfo.length;i++){
			if(gsInfo[i][3].equals(fValue)){
				int leve = Integer.parseInt(gsInfo[i][4]) + 1 ;
				re = gsInfo[i][0] + "," + leve;
				break ;
			}
		}
		return re;
	}
	
	/**
	 * 从对应数组中根据对应name获取dyValue
	 * @param dyName
	 * @return
	 */
	private  String getDyValue(String dyName){
		String re = "";
		for(int i=0;i<dyArr.length;i++){
			if(dyArr[i][0].equals(dyName)){
				re = dyArr[i][1] ;
				break ;
			}
		}
		return re;
	}
	
	/**
	 * 初始化gsInfo数组
	 * @param gsStr
	 */
	private  void initializationGsInfo(String gsStr){
		gsInfo = new String[1][6];
		gsInfo[0][0] = "1";     //zid
		gsInfo[0][1] = "-1";    //fid
		gsInfo[0][2] = "gsStr";     //name
		gsInfo[0][3] = gsStr;     //value
		gsInfo[0][4] = "0";     //jb
		gsInfo[0][5] = null;     //fh
	}
	
	/**
	 * 初始化gsInfo数组
	 * @param gsStr
	 */
	private  void initializateFinalTree(){
		FinalTree = new String[1][5];
		FinalTree[0][0] = "zbh";     //zid
		FinalTree[0][1] = "fbh";     //fid
		FinalTree[0][2] = "val";     //val
		FinalTree[0][3] = "op";      //op
		FinalTree[0][4] = "fname";   //fname
	}
	
	/**
	 * 初始化用于存放一个优先级的式子的数组
	 */
	private  void initializeOneLevelArr(){
		oneLevel = new String[1][2];
		oneLevel[0][0] = "0";     //zid
		oneLevel[0][1] = "0";    //fid
	}
	/**
	 * 获取替换函数和括号时实际要替换的量
	 * 1，从表达式开始到函数/括号内容位置找到最后一个运算符的位置做为截串的左边起始位置
	 * 2，从函数/括号内容位置到函数的结尾处找第一个运算符的位置作为截串的右边启示位置
	 * @param splitIndex
	 * @param gsStr
	 * @return
	 */
	private  String dealSubString(int splitIndex,String gsStr){
		char[] arrA = gsStr.toCharArray();
		int leftIndex = 0,rightIndex = 0;
		for(int i=0;i<splitIndex;i++){
			char ch = arrA[i];
			if(isChar(ch)){
				leftIndex = i;
			}
		}
		for(int i=splitIndex;i<arrA.length;i++){
			char ch = arrA[i];
			if(isChar(ch)){
				rightIndex = i;
				break;
			}
		}
		if(rightIndex == 0) rightIndex = arrA.length;
		if(leftIndex != 0) leftIndex = leftIndex+1;
		String re = gsStr.substring(leftIndex,rightIndex);
		return re;
	}
	
	/**
	 * 获取新替换的变量
	 * @param gsStr
	 * @return
	 */
	private  String getReplace(){
		String re = "";
		String[] arr = {"A","B","C","D","E","F","G","H","I","J","K",
				"L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		re=arr[dyIndex];
		dyIndex++;
		return re;
	}
	
	
	/**
	 * 判断是否存括号,存在返回true,不存在返回false
	 * @return
	 */
	private  boolean judgeExistKh(String exp){
		boolean re=false;
		char[] nowStrA=exp.toCharArray();
		for (int i = 0; i < nowStrA.length; i++) {
			if(nowStrA[i]=='('){
				re=true;
				break;
			}
		}
		return re;
	}
	/**
	 * 判断是否为单值,是返回true,不是返回false
	 * @return
	 */
	private  boolean judgeExistfh(String exp){
		boolean re=true;
		char[] nowStrA=exp.toCharArray();
		for (int i = 0; i < nowStrA.length; i++) {
			if(nowStrA[i]=='*'||nowStrA[i]=='/'||nowStrA[i]=='+'||nowStrA[i]=='-'){//如果存在操作符号,返回false;
				re=false;
				break;
			}
		}
		return re;
	}
	/**
	 * 增加一个级别计算数组的方法
	 * @param blName
	 * @param fh
	 * @return
	 */
	private  void addToOneLevelArr(String blName,String fh){
		String[][] gsInfo1 = new String[1][2];
		gsInfo1[0][0] = blName;     			    //name
		gsInfo1[0][1] = fh;     					//fh
		oneLevel = makNewArr(oneLevel,gsInfo1);
	}
	
	
	/**
	 * 线性计算的方法
	 * @param midResult	//单次计算的结果
	 * @param fh		//符号
	 * @param foreHead	//前面的元素
	 * @param backLast	//后面的元素
	 * @return
	 */
	private  double computeOne(double midResult,String fh,String foreHead,String backLast){
		double fore=Double.parseDouble(foreHead);//前面的变量
		double last=Double.parseDouble(backLast);//后面的变量
		if(fh.equals("+")){
			midResult=fore+last;
		}else if(fh.equals("-")){
			midResult=fore-last;
		}else if(fh.equals("*")){
			midResult=fore*last;
		}else if(fh.equals("/")){
			midResult=fore/last;
		}
		return midResult;
	}
	/**
	 * 遍历结果树显示的方法
	 */
	public  void outSYSO(){
		for (int i = 0; i < FinalTree.length; i++) {
			for (int j = 0; j < FinalTree[0].length; j++) {
				System.out.print(FinalTree[i][j]+"\t");
			}
			System.out.println();
		}
	}
	
	/**
	 * 生成编号的方法
	 */
	private void makBh(){
		for (int i = 1; i < FinalTree.length; i++) {
			int inBh=i;
			FinalTree[i][0]=outBh(inBh);
		}
		//定义一个方法通过fname,找它的fbh
		for (int i = 1; i < FinalTree.length; i++) {
			FinalTree[i][1]=outFbh(FinalTree[i][4]);
		}
	}
	
	/**
	 * 生成最终表达式树的编号
	 * @param inBh	//传入的循环变量
	 * @return
	 */
	private  String outBh(int inBh){
		String re="";
		if(inBh<10){
			re="00"+inBh;
		}else if(inBh<100){
			re="0"+inBh;
		}else{
			re=""+inBh;
		}
		return re;
	}
	/**
	 * 生成表达式树的父编号(fbh)的方法
	 * @param fname
	 * @return
	 */
	private  String outFbh(String fname){
		String re="";
		for (int i = 1; i < FinalTree.length; i++) {
			if(FinalTree[i][2].equals(fname)){
				re=FinalTree[i][0];
			}
		}
		return re;
	}
	/**
	 * 根据节点树计算结果(节点树生成之后并赋值后才可计算)
	 * @param fname
	 * @return
	 */
	public  String getRresultViaFinalTree(){
		double result=0;
		int p=0;//定义循环变量,用于区别单次线性计算中是否为第一个
		int i;//循环变量,取运算结果用到
		for (i = 1; i < FinalTree.length; i++) {
			if(FinalTree[i][3]!=null){//如果操作符不是null
				if(p==0){
					result=computeOne(result,FinalTree[i][3],FinalTree[i][2],FinalTree[i+1][2]);
				}else{
					result=computeOne(result,FinalTree[i][3],String.valueOf(result),FinalTree[i+1][2]);
				}
				p++;
			}else{//如果操作符为null
				p=0;//计算完一次线性关系,将p赋空
				if (!FinalTree[i][1].equals("")) {
					//System.out.println("找到父"+FinalTree[i][1]);
					int fIndex=Integer.parseInt(FinalTree[i][1]);//找到父
					FinalTree[fIndex][2]=String.valueOf(result);
				}
			}
		}
		return FinalTree[i-1][2];
	}
}