//画出两阶段法的页面
function yemian(jj,ii,get_re,base_variable_arr,arr_first_varry){
  var tb_body="",tr_value="",tab_value="",input_value="",i=1,j=1;
  var tr1_value=new Array();
  var arr_input_value_2=new Array();
  var arr_get_re=new Array();
  
   if(get_re!==null&&get_re!=""){
	  arr_get_re=get_re.toString().substring(0,get_re.length-1).split("#");//中间所有的数据
	  arr_input_value_2=arr_get_re[0].toString().substring(0,arr_get_re[0].length-1).split(",");//第二行的数据
   }
   if(arr_first_varry==null){
	   arr_first_varry=new Array();
	   for(var kk=1;kk<=jj;kk++){
		   var X_j="X"+kk.toString();
		   arr_first_varry.push(X_j);
	   }
   }
   while(i<=ii){
	  while(i==1){//第一行
		  var fs_wz=0;
		  tr_value=first_line(i,jj,fs_wz,arr_first_varry);//fs_wz--->负数位置
		  i++;
	  }
	  while(i==2){
		  tr_value+=second_line(i,jj,arr_input_value_2);
		  i++;
	  }
	  if(i>=3&&i<=ii){
		  tr_value+=mid_line(ii,jj,arr_get_re,base_variable_arr);
		   break;
	  }
  }
   /*
   if(i<=ii){
	  for(var row_i=i;row_i<=ii;row_i++){
	  if(row_i==1){//第一行
		  var fs_wz=0;
		  tr_value=first_line(row_i,jj,fs_wz,arr_first_varry);//fs_wz--->负数位置
		  row_i++;
	  }
	  else if(row_i==2){
		  tr_value+=second_line(row_i,jj,arr_input_value_2);
		  row_i++;
	  }
	  else if(row_i>=3&&row_i<=ii){
		  tr_value+=mid_line(ii,jj,arr_get_re,base_variable_arr);
		 //  break;
	  }
	  }
  }
  */
  tab_value="<table id=\"tab1\" border=\"1\" bgcolor=\"#CCFFFF\" style=\"text-align:center\">"+tr_value+"</table>";
  tb_body=document.getElementById("div1");
  tb_body.innerHTML=tab_value;
  //hidden();
  document.getElementById("hidden1").value=jj;//把列数值 jj赋给隐含文本
  document.getElementById("hidden2").value=ii;//把行数值 ii赋给隐含文本
  bgbs();
 }

function first_line(i,jj,fs_wz,arr_first_varry){//第一行 function：Add_Row()，Add_Column()；Delete_Column()
	var td_value1="",td1_value="",Xj="",tr_value1="",Xj_all="";
	
	for(var j=1;j<=jj;j++){
		Xj_all+=arr_first_varry[j-1].toString()+"$";
		td_value1=td_value1+"<td>"+arr_first_varry[j-1]+"</td>";
	}
	var base_input1=document.getElementById("first_bl");//变量存入隐含文本------7.10
	base_input1.value=Xj_all;
	td1_value="<td>XB</td><td>b</td>"+td_value1;
	//td1_value=td1_value+td_value1;//前两单元格
	tr_value1="<tr>"+td1_value+"</tr>";
	return tr_value1;
}

function second_line(i,jj,arr_input_value_2){//第二行 function：Result()，Delete_Row()
	var td_value2="",td2_value="",txt_id="",tr_value2="" ,Xj="",input_str="";
	for(var j=0;j<=jj;j++){
		if(arr_input_value_2[j]=="undefined"||arr_input_value_2[j]==null){
		   arr_input_value_2[j]="";   }		
		txt_id="txt"+i.toString()+j.toString();
  	    input_str="<input type=\"text\" name="+txt_id+" id="+txt_id+" value="+arr_input_value_2[j]+"></input>" ;     
		td_value2=td_value2 +"<td>"+input_str+"</td>";
	}
	td2_value="<td>Z</td>"+td_value2;
	tr_value2="<tr height=\"40\">"+td2_value+"</tr>";
	////alert("tr_value2   =="+tr_value2);
	return tr_value2;
}

function mid_line(ii,jj,arr_get_re,base_variable_arr){//中间行
	var arr_input_value_mid=new Array();
	var td3_value="",td_value3="",txt_id="",txt_id1="",select_option1="",tr_value_mid="";
	if(base_variable_arr==null){
		base_variable_arr=new Array()
	}	
	for(var i=3;i<=parseInt(ii);i++){	
		arr_input_value_mid[i-3]=new Array();	
		//alert("arr_get_re 2222 =="+arr_get_re.toString());
		
		if(arr_get_re!=""&&arr_get_re!="undefined"&&i<=parseInt(ii)){
			//if(arr_get_re[i-2]!=""&&arr_get_re[i-2]!="undefined"){
				arr_input_value_mid[i-3]=arr_get_re[i-2].toString().substring(0,arr_get_re[i-2].length).toString().split(",");///////////////
			//}
		}else{
			arr_input_value_mid[i-3]=new Array();
				for(var n=0;n<jj;n++){
				arr_input_value_mid[i-3][n]="";
				}
		}		
		for(var j=0;j<=jj;j++){
			txt_id="txt"+i.toString()+j.toString();
			if(arr_input_value_mid[i-3][j]=="undefined"||arr_input_value_mid[i-3][j]==null){
				arr_input_value_mid[i-3][j]="";
			}
			var X_value1="<input type=\"text\" name="+txt_id+" id="+txt_id+" value="+arr_input_value_mid[i-3][j]+">" ;
			td_value3 +="<td>"+X_value1+"</td>";	
		}
		if(base_variable_arr[i-3]==null||base_variable_arr[i-3]==""||base_variable_arr[i-3]=="undefined"){
			base_variable_arr[i-3]="";
		}
   	    select_option1="<option value=\"=\">=</option><option value=\">\">></option><option value=\"<\"><</option>";
		td3_value +="<td>"+base_variable_arr[i-3]+"</td>"+td_value3+"</td>";
		tr_value_mid+="<tr height=\"40\">"+td3_value+"</tr>";
		td3_value="";
		td_value3="";
 	}
	return tr_value_mid;
}

function Go_on() {//转标准形式	
	var ii = 0, jj = 0;
	var input_value_str = "", get_re = "";
	var arr_input_value = new Array();
	//var aa=prompt("please input base variable quantity divideby '$':","");//输入基变量 用  "$" 做分隔符
	var aa = prompt("输入基变量用 $ 做分隔符(例如X2$X3)", "");//输入基变量 用  "$" 做分隔符
	var base_variable_arr = new Array();
	if (aa != "" && aa != null && aa != "undefined") {
		document.getElementById("basic_standard").value=aa;
		base_variable_arr = aa.toString().split("$");
	//	var base_input = document.getElementById("basic_standard");//基变量存入隐含文本------7.10
	//	base_input.value = aa;
	}
	jj = parseInt(document.getElementById("hidden1").value);
	ii = parseInt(document.getElementById("hidden2").value);
	get_re = get_input_value(ii, jj);
	var get_res = get_re.replace(/,#/g, "#");
	var base_input2 = document.getElementById("mid_value");//基变量存入隐含文本------7.10
	base_input2.value = get_res;
	yemian(jj, ii, get_re, base_variable_arr);
}

function get_input_value(ii,jj){//获取table值 input+select-----
	var txt_id="",input_value_str="",input_value_all="";	
	for(var i=2;i<=ii;i++){
		for(var j=0;j<=jj;j++){
			txt_id="txt"+i.toString()+j.toString();
				input_value_str+=document.getElementById(txt_id).value+",";
		}
		input_value_str+="#";
	}
	return input_value_str;
}

function Add_Column(){//增加一列
	var ii=0,jj=0,get_re="";
	jj=parseInt(document.getElementById("hidden1").value)+1;
   ii=parseInt(document.getElementById("hidden2").value);
    get_re=get_input_value(ii,jj-1).toString();
	yemian(jj,ii,get_re);
}

function Delete_Column(){//删除一列
	var ii=0,jj=0,get_re="";
	jj=parseInt(document.getElementById("hidden1").value)-1;
     ii=parseInt(document.getElementById("hidden2").value);
   get_re=get_input_value(ii,jj+1).toString().substring(0,get_input_value(ii,jj+1).length-1);
	yemian(jj,ii,get_re);
}

function Add_Row(){//增加一行
	var ii=0,jj=0,get_re="";
	jj=parseInt(document.getElementById("hidden1").value);
   ii=parseInt(document.getElementById("hidden2").value)+1;
    get_re=get_input_value(ii-1,jj).toString()+"#";
 	yemian(jj,ii,get_re);
}

function Delete_Row(){//删除一行
	var ii=0,jj=0,get_re="";
	jj=parseInt(document.getElementById("hidden1").value);
    ii=parseInt(document.getElementById("hidden2").value)-1;
    get_re=get_input_value(ii+1,jj).toString().substring(0,get_input_value(ii+1,jj).length-1);
	yemian(jj,ii,get_re);
}

function Result(){  //计算出最后结果，显示在页面上
	var str_base="",str_varry="",str_mid="";
	//var arr_base=new Array();
	var arr_varry=new Array();
	var arr_mid=new Array();
	var ew_arr_mid=new Array();
	var jj=0, ii=0;
	jj=parseInt(document.getElementById("hidden1").value);
	 ii=parseInt(document.getElementById("hidden2").value);
	str_base=document.getElementById("basic_standard").value;
;
	str_varry=document.getElementById("first_bl").value;
	str_mid=document.getElementById("mid_value").value;
	str_mid=str_mid.toString().substring(0,str_mid.length-1);
//	alert("str_base  =="+str_base +"  str_varry  =="+str_varry +"  str_mid  =="+str_mid);
//7_11_15_43  ls
	//arr_base=str_base.toString().split("$");
	arr_varry=str_varry.toString().substring(0,str_varry.length-1).split("$");
	arr_mid=str_mid.toString().split("#");
	for(var k=0;k<arr_mid.length;k++){//创建二维数组
		ew_arr_mid[k]=new Array();
		ew_arr_mid[k]=arr_mid[k].toString().split(",");
	}
	for(var k=0;k<arr_mid.length;k++){//二维数组转整
		for(var j=0;j<jj;j++){/////////
			if(ew_arr_mid[k][j]==""){
				//alert("是空格啊");
				ew_arr_mid[k][j]=0;
			}
			else {
				ew_arr_mid[k][j]=parseFloat(ew_arr_mid[k][j]);
			}
		}
	}	
	var arr_not_base_loc=find_not_base_loc(str_base,arr_varry);
	var fs_loc_min=first_line_juge(arr_not_base_loc,ew_arr_mid);
	if(fs_loc_min!=-1){
		alert("这是基变量   str_base============"+str_base);
		workout_result(ew_arr_mid,str_base,arr_varry,fs_loc_min,jj,ii);////---------------
	}else{alert("已经是最优解，无需继续计算！！！");}
}

function find_not_base_loc(str_base,arr_varry){//找出非基变量的位置
	//var arr_loc_jl=new Array();
	var arr_loc_jl="";
	for(var i=0;i<arr_varry.length;i++){
		var ss=arr_varry[i].toString();
		var loc_jl=str_base.toString().indexOf(ss,0);
		if(loc_jl==-1){
			arr_loc_jl=arr_loc_jl+(i+1)+",";
			//break;
		}
	}
	alert("非基变量的位置     ===="+arr_loc_jl);
	return arr_loc_jl;
}

function first_line_juge(arr_not_base_loc,ew_arr_mid){//找出非基变量负数最小的位置
	var fs_loc=-1,n_b_min=0;
	var arr_not_base=new Array();
	arr_not_base=arr_not_base_loc.split(",");
	for(var k=0;k<arr_not_base.length;k++){//找出非基变量负数位置
		var kk=parseInt(arr_not_base[k]);
		var n_b_judge=parseInt(ew_arr_mid[0][kk]);
		if(n_b_judge<n_b_min){
			n_b_min=n_b_judge;
			fs_loc=kk
		}
	}
	alert("主元所在的列数      ==="+fs_loc);
	return fs_loc;		
}

//ew_arr_mid  中间数组串；str_base 基变量串；arr_varry  首行的数组；fs_loc_min 主元所在列；
function workout_result(ew_arr_mid,str_base,arr_varry,fs_loc_min,jj,ii){  
	var bz_min_loc=-1;//记录最小位置
	var bz_jl=1000000;//记录基变量与最小负数列的比值 确定换入 换出变量   
	//7_11_16_18  ls
	var arr_base=new Array();
	//alert("   str_base  ======="+str_base);
	arr_base=str_base.split("$");
	
	for(var k=1;k<ew_arr_mid.length;k++){   //确定换入变量所在行bz_min_loc（列已知）
		if(parseFloat(ew_arr_mid[k][fs_loc_min])>0){
			var bz_pd=ew_arr_mid[k][0]/ew_arr_mid[k][fs_loc_min];//比值
			if(bz_pd<bz_jl&&bz_pd>=0){
				bz_jl=bz_pd;
				bz_min_loc=k;
			}
		}
	}
	if(bz_min_loc==-1){//比值全部为负（不是单纯形法，未处理）
		alert("此题用单纯型法无法求解");
	}
	else if(bz_min_loc!=-1){
		//arr_base[bz_min_loc-1]=arr_varry[fs_loc_min-1];//互换    换入 换出 变量（arr_varry[fs_loc_min-1] 为X1，X2）（bz_min_loc  行）
		//7_11_16_26  ls
		arr_base[bz_min_loc-1]=arr_varry[fs_loc_min-1];//互换    换入 换出 变量（arr_varry[fs_loc_min-1] 为X1，X2）（bz_min_loc  行）
		var jz_cs_z=ew_arr_mid[bz_min_loc][fs_loc_min];//（取出主元）基准 乘数
		for(var m=0;m<=jj;m++){//主元行除以主元值（主元化为1）
			ew_arr_mid[bz_min_loc][m]=ew_arr_mid[bz_min_loc][m]/jz_cs_z;
		}
		var a=1;
		
		
		//行下表出错
		for(var n=0;n<ew_arr_mid.length;n=n+a){
			var jz_cs_f=ew_arr_mid[n][fs_loc_min];//基准 乘数
			for(var j=0;j<=jj;j++){
				if(n==bz_min_loc){
					a=1;
				}else if(n!=bz_min_loc){
					if(jz_cs_f==0){
						a=1;
					}else if(jz_cs_f!=0){
						ew_arr_mid[n][j]=parseInt(ew_arr_mid[n][j])+parseInt(ew_arr_mid[bz_min_loc][j])*(-jz_cs_f);//循环改变值
						ew_arr_mid[n][j]=ForDight(ew_arr_mid[n][j],2);//保留两位小数
						a=1;
					}
				}
			}
		}
	}
	
	str_base="";
	for(var base_i=0;base_i<arr_base.length;base_i++){
		str_base+=arr_base[base_i]+"$";
	}
	str_base=str_base.substring(0,str_base.length-1);
	
	var arr_not_base_loc=find_not_base_loc(str_base,arr_varry);
	var fs_loc_min1=first_line_juge(arr_not_base_loc,ew_arr_mid);//找目标函数中，负数最小的位置
	if(fs_loc_min1!=-1){//若非基变量没有负数，则停止
		workout_result(ew_arr_mid,str_base,arr_varry,fs_loc_min1,jj,ii);
	}else{alert("2222222应经是最优解，无需继续计算！！！");}
	
	//得到最后结果，将最后结果显示在页面上
	var get_re="";
	for(var rr=0;rr<ew_arr_mid.length;rr++){
		for(var cc=0;cc<=jj;cc++){
			get_re+=ew_arr_mid[rr][cc].toString()+",";
			//7_11_16_38  ls
			//get_re+=ew_arr_mid[rr+1][cc].toString()+",";
		}
		get_re+="#";
	}	
	get_re=get_re.toString().substring(0,get_re.toString().length-1);
	yemian(jj,ii,get_re,arr_base,arr_varry);//////////////////////////////////	
}


function  ForDight(Dight,How){//保留小数两位  
	Dight=Math.round(Dight*Math.pow(10,How))/Math.pow(10,How);     
 	return  Dight;     
}     
function bgbs(){
	var cobj=document.getElementById("tab1").rows;
	for (i=0;i< cobj.length ;i++) {
		(i%2==0)?(cobj[i].style.background = "#F2F2F2"):(cobj[i].style.background = "#FFF");
	}
}