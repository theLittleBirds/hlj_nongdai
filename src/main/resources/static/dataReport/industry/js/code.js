// JavaScript Document


var  f_age=new Array(2,0);
var  f_ageItem=document.getElementsByName("f_age");

var  f_with=new Array(2,0);
var  f_withItem=document.getElementsByName("f_with");
var  f_borrow=new Array(5,0);
var  f_borrowItem=document.getElementsByName("f_borrow");
var  f_phone=new Array(3,0);
var  f_phoneItem=document.getElementsByName("f_phone");
var  m_age=new Array(2,0);
var  m_ageItem=document.getElementsByName("m_age");
var  m_with=new Array(2,0);
var  m_withItem=document.getElementsByName("m_with");
var  m_borrow=new Array(5,0);
var  m_borrowItem=document.getElementsByName("m_borrow");
var  m_phone=new Array(3,0);
var  m_phoneItem=document.getElementsByName("m_phone");

var  c_one_with=new Array(0,1);
var  c_one_withItem=document.getElementsByName("c_one_with");
var  c_one_edu_0=new Array(0,5,4,5);
var  c_one_edu_1=new Array(0,3,0,4);
var  c_one_eduItem=document.getElementsByName("c_one_edu"); 
var  c_one_w_0=new Array(4,5);
var  c_one_w_1=new Array(0,4);
//var  c_one_w=new Array(4,6);
//var  c_one_wItem=document.getElementsByName("c_one_w")
var  c_one_phone=new Array(5,0);
var  c_one_phoneItem=document.getElementsByName("c_one_phone");
var  c_one_borrow=new Array(6,0);
var  c_one_borrowItem=document.getElementsByName("c_one_borrow");
//c_one_borrowItem.item(0).innerHTML
var  c_two_with=new Array(0,1);
var  c_two_withItem=document.getElementsByName("c_two_with");
var  c_two_edu_0=new Array(0,5,4,5);
var  c_two_edu_1=new Array(0,3,0,4);
var  c_two_eduItem=document.getElementsByName("c_two_edu"); 
var  c_two_w_0=new Array(4,5);
var  c_two_w_1=new Array(0,4);
 
//var  c_two_wItem=document.getElementsByName("c_two_w"); 
var  c_two_phone=new Array(5,0);
var  c_two_phoneItem=document.getElementsByName("c_two_phone");
var  c_two_borrow=new Array(6,0);
var  c_two_borrowItem=document.getElementsByName("c_two_borrow");

var  h_det=new Array(5,2,0);
var  h_detItem=document.getElementsByName("h_det");
var  h_type=new Array(4,0);
var  h_typeItem=document.getElementsByName("h_type");
var  h_goods=new Array(5,0);
var  h_goodsItem=document.getElementsByName("h_goods");
var  h_ret=new Array(-5,0);
var  h_retItem=document.getElementsByName("h_ret");


var c_agr=new Array(3,0);
var c_agrItem=document.getElementsByName("c_agr");
var c_pri=new Array(2,0);
var c_priItem=document.getElementsByName("c_pri");
var l_eat=new Array(3,5);
var l_eatItem=document.getElementsByName("l_eat");
var l_ret=new Array(3,4,0);
var l_retItem=document.getElementsByName("l_ret");
var t_pri=new Array(3,4,5,7);
var t_priItem=document.getElementsByName("t_pri");
var l_det=new Array(7,5,3,0);
var l_detItem=document.getElementsByName("l_det");
//var w_type=new Array(0,4);
//var w_typeItem=document.getElementsByName("w_type");
var b_use=new Array(2,0);
var b_useItem=document.getElementsByName("b_use");

var b_age=new Array(7,15,10,0);
var b_ageItem=document.getElementsByName("b_age");
var b_wed=new Array(0,20,0,10);
var b_wedItem=document.getElementsByName("b_wed");
var b_edu=new Array(8,10,15);
var b_eduItem=document.getElementsByName("b_edu");
var b_loans=new Array(50,30);
var b_loansItem=document.getElementsByName("b_loans");
var b_after=new Array(0,-15,-30,-45,-60,-75,-90);
var b_afterItem=document.getElementsByName("b_after");

var b_work=new Array(0,8,12);
var b_workItem=document.getElementsByName("b_work");
var b_year=new Array(4,7,10);
var b_yearItem=document.getElementsByName("b_year");
var b_back=new Array(5,5,5);
var b_backItem=document.getElementsByName("b_back");
var b_liba=new Array(10,5,2.5);
var b_libaItem=document.getElementsByName("b_liba");
var b_gov_qur=new Array(15,8,5,0);
var b_gov_qurItem=document.getElementsByName("b_gov_qur");
var b_p_qur=new Array(15,8,5,0);
var b_p_qurItem=document.getElementsByName("b_p_qur");
var b_cost=new Array(10,7,6,0);
var b_costItem=document.getElementsByName("b_cost");
var b_list=new Array(0,5);
var b_listItem=document.getElementsByName("b_list");

var face=30;
var faceItem=document.getElementsByName("face");
var phone=70;
var phoneItem=document.getElementsByName("phone");

var part1=document.getElementsByName("parta");
var part2=document.getElementsByName("partb");
var part3=document.getElementsByName("partc");
var part4=document.getElementsByName("partd");
var res=document.getElementsByName("res");
var resWordP1="";
var resWordP2="";
var resWordP3="";
var resWordP4="";
var result="";
var credit=document.getElementsByName("credit");
var form=document.getElementsByName("myform");

function calc()
{      
 
    var partA=0;
    var partB=0;
	var partC=0;
    var partD=0;
 
	var f= father();
	var m= mother();
	var c1=c_one();
	var c2=c_two();
    var money=0;
    var moneyOff=1;
	
	
	var h=house();
	var c=car();
	var l=land();
	var t1=tree();
	var li=lib();
	var u=use();

	
	partA=h+c+l+t1+li+u+f+m+c1+c2;
	part1.item(0).value=partA;

	if(partA>50){ resWordP1="A"; money=money+12000 }
	else if(partA>=35&&partA<=50){resWordP1="B"; money=money+8000 }
	else if(partA>=20&&partA<=35){resWordP1="C";  money=money+4000}
	else if(partA<20){resWordP1="D";moneyOff=0 }
	
	var b=borrow();
	partB=b;
	part2.item(0).value=partB;
	
	if(partB>80){ resWordP2="A";  money=money+10000 }
	else if(partB>=60&&partB<=80){resWordP2="B";  money=money+6000 }
	else if(partB>=50&&partB<=60){resWordP2="C"; money=money+2000 }
	else if(partB<50){resWordP2="D";moneyOff=0.7 }
	
	
	
	var ba=backAbl();
	partC=ba;
	part3.item(0).value=partC;
	
	if(partC>80){ resWordP3="A";  money=money+8000}
	else if(partC>=70&&partC<=80){resWordP3="B";  money=money+4000}
	else if(partC>=60&&partC<=70){resWordP3="C";  money=money+1000}
	else if(partC<60){resWordP3="D"; moneyOff=0.5 }
	
	
	
	var ba=backAbl();
	partC=ba;
	part3.item(0).value=partC;
	
	 
	
	
	var fa=faceBoo();
	 
	partD=fa;
	part4.item(0).value=partD;
	
		//alert(f);
	//alert(m);
	//alert(c1);
  result=resWordP1+resWordP2+resWordP3+resWordP4;	 
  res.item(0).value=result;

money=money*moneyOff;
credit.item(0).value=money;
  //checkUser();
  // document.forms.item(0).submit();
 
  //if(Check())
 // {
	  
	  
	  
	   for(var i=0;i<document.forms.item(0).elements.length-1;i++)
         {
	     if(confirm("当前有未选定的项 ，确定提交？")&&checkUser())
		 {
			 
			 document.forms.item(0).submit();
			 break;
		 }else
		 {
			 
			 document.forms.item(0).elements[i].focus();
			  break;
		 }
			 
		     
	  
 // } 
      }
  
}

//--------------------------------------------------------------------------------------------------
function father()
{
	 
	 var f1=0;
	 var f2=0;
	 var f3=0;
	 var f4=0;
	for(var i=0;i<f_ageItem.length;i++)
	{
		if(f_ageItem[i].checked==true)
		
		{
		 
		 f1=f_age[i];
			//alert(f1);	    	
	    }
	}
	//------------------------------------------------------------
   for(var i=0;i<f_withItem.length;i++)
	{
		if(f_withItem[i].checked==true)
		
		{
	     f2=f_with[i];
			//alert(f1);	    	
	    }
	}
	
	//-----------------------------------------------
	
	  for(var i=0;i<f_borrowItem.length;i++)
	{
		if(f_borrowItem[i].checked==true)
		
		{
	    f3=f_borrow[i];
			//alert(f1);	    	
	    }
	}
//------------------------------------------------------

	  for(var i=0;i<f_phoneItem.length;i++)
	{
		if(f_phoneItem[i].checked==true)
		
		{
	    f4=f_phone[i];

			//alert(f1);	    	
	    }
	}
//---------------------------------------------------------
   for(var i=0;i<f_phoneItem.length;i++)
	{
		if(f_phoneItem[i].checked==true)
		
		{
	    f4=f_phone[i];

			//alert(f1);	    	
	    }
	}



	var fcout=f1+f2+f3+f4;
 
	
 
	 
	return fcout;
	
	
}



function mother()
{
	 　
	 var m1=0;
	 var m2=0;
	 var m3=0;
	 var m4=0;
	for(var i=0;i<m_ageItem.length;i++)
	{
		if(m_ageItem[i].checked==true)
		
		{
		 
		 m1=m_age[i];
			//alert(f1);	    	
	    }
	}
	//------------------------------------------------------------
   for(var i=0;i<m_withItem.length;i++)
	{
		if(m_withItem[i].checked==true)
		
		{
	     m2=m_with[i];
			//alert(f1);	    	
	    }
	}
	
	//-----------------------------------------------
	
	  for(var i=0;i<m_borrowItem.length;i++)
	{
		if(m_borrowItem[i].checked==true)
		
		{
	    m3=m_borrow[i];
			//alert(f1);	    	
	    }
	}
//------------------------------------------------------

	  for(var i=0;i<m_phoneItem.length;i++)
	{
		if(m_phoneItem[i].checked==true)
		
		{
	    m4=m_phone[i];

			//alert(f1);	    	
	    }
	}
//---------------------------------------------------------
   for(var i=0;i<m_phoneItem.length;i++)
	{
		if(m_phoneItem[i].checked==true)
		
		{
	    m4=m_phone[i];

			//alert(f1);	    	
	    }
	}



	var mcout=m1+m2+m3+m4;
 
	
 
	 
	return mcout;
	
	
}
//-----------------------------------------------------------------------------------------------------------------------------
 function isSelect(str1,str2,str3,str4)
 {    
  if(str4[0].checked==false || str4[1].checked==false)
  {
	  
	for(i=0;i<str1.length;i++)
	{
		if(str1[i].checked==true)
		{
			return false;
		}
		
	 }  
	  
  }
	
	 
 }
//-----------------------------------------
function c_one()
{
	var c1=0;
	var c2=0;
	var c3=0;
	var c4=0;
	 
	 
	
	 
	
	if(c_one_withItem.item(0).checked==true)
	{
			
		   
		 
		for(var i=0;i<c_one_eduItem.length;i++)
	    {
		if(c_one_eduItem[i].checked==true)
		
		{
	    c1=c_one_edu_0[i];
		//alert(c1);	    	
	    }

		
     	}
			//-----------------------------------------------------------------
	 
		//for(var i=0;i<c_one_wItem.length;i++)
	  //  {
		//  if(c_one_wItem[i].checked==true)
		
		//  {
	  	//  c2=c_one_w_0[i];
		//alert(f1);	    	
	  	//  }	
			
	 //  }
		//-----------------------------------------------------
		
		
	for(var i=0;i<c_one_phoneItem.length;i++)
	    {
		  if(c_one_phoneItem[i].checked==true)
		
		  {
	  	  c2=c_one_phone[i];
		//alert(f1);	    	
	  	  }	
			
	   }
	   //---------------------------------------------
	   for(var i=0;i<c_one_borrowItem.length;i++)
	    {
		  if(c_one_borrowItem[i].checked==true)
		
		  {
	  	  c3=c_one_borrow[i];
		//alert(f1);	    	
	  	  }	
			
	   }
	   
	  ////-------------------------------------------------- 
	   
	      
	   	
		
	}
	
	if(c_one_withItem.item(1).checked==true)
	{
		
		
	
	
	for(var i=0;i<c_one_eduItem.length;i++)
	    {
		if(c_one_eduItem[i].checked==true)
		
		{
	    c1=c_one_edu_1[i];
		//alert(f1);	    	
	    }

		
	}
	
	
	
			//-----------------------------------------------------------------
	 
	//	for(var i=0;i<c_one_wItem.length;i++)
//	    {
	//	  if(c_one_wItem[i].checked==true)
		
	//	  {
	 // 	  c2=c_one_w_1[i];
		//alert(f1);	    	
	 // 	  }	
			
	//   }
		//-----------------------------------------------------
		
		
	for(var i=0;i<c_one_phoneItem.length;i++)
	    {
		  if(c_one_phoneItem[i].checked==true)
		
		  {
	  	  c2=c_one_phone[i];
		//alert(f1);	    	
	  	  }	
			
	   }
	   //---------------------------------------------
	   for(var i=0;i<c_one_borrowItem.length;i++)
	    {
		  if(c_one_borrowItem[i].checked==true)
		
		  {
	  	  c3=c_two_borrow[i];
		//alert(f1);	    	
	  	  }	
			
	   }
	//------------------------------------------------------
	
	
	}
	var c_cout=c1+c2+c3;
	//alert(c_cout);
	return c_cout;
	
}
//-------------------------------------------------------------------------------------------------
//-                                                                                               - 
//-                                                                                               -
//-                                                                                               -
//-------------------------------------------------------------------------------------------------
function c_two()
{
	var c1=0;
	var c2=0;
	var c3=0;
	var c4=0;
	 
	 
	 
	 
	if(c_two_withItem.item(0).checked==true)
	{
			
		   
		 
		for(var i=0;i<c_two_eduItem.length;i++)
	    {
		if(c_two_eduItem[i].checked==true)
		
		{
	    c1=c_two_edu_0[i];
		//alert(c1);	    	
	    }

		
	}
			//-----------------------------------------------------------------
	 
		//for(var i=0;i<c_one_wItem.length;i++)
	  //  {
		//  if(c_one_wItem[i].checked==true)
		
		//  {
	  	//  c2=c_one_w_0[i];
		//alert(f1);	    	
	  	//  }	
			
	 //  }
		//-----------------------------------------------------
		
		
	for(var i=0;i<c_two_phoneItem.length;i++)
	    {
		  if(c_two_phoneItem[i].checked==true)
		
		  {
	  	  c2=c_two_phone[i];
		//alert(f1);	    	
	  	  }	
			
	   }
	   //---------------------------------------------
	   for(var i=0;i<c_two_borrowItem.length;i++)
	    {
		  if(c_two_borrowItem[i].checked==true)
		
		  {
	  	  c3=c_two_borrow[i];
		//alert(f1);	    	
	  	  }	
			
	   }
	   
	  ////-------------------------------------------------- 
	   
	      
	   	
		
	}
	
	if(c_two_withItem.item(1).checked==true)
	{
		
		
	
	
	for(var i=0;i<c_two_eduItem.length;i++)
	    {
		if(c_two_eduItem[i].checked==true)
		
		{
	    c1=c_two_edu_1[i];
		//alert(f1);	    	
	    }

		
	}
			//-----------------------------------------------------------------
	 
	//	for(var i=0;i<c_one_wItem.length;i++)
//	    {
	//	  if(c_one_wItem[i].checked==true)
		
	//	  {
	 // 	  c2=c_one_w_1[i];
		//alert(f1);	    	
	 // 	  }	
			
	//   }
		//-----------------------------------------------------
		
		
	for(var i=0;i<c_two_phoneItem.length;i++)
	    {
		  if(c_two_phoneItem[i].checked==true)
		
		  {
	  	  c2=c_two_phone[i];
		//alert(f1);	    	
	  	  }	
			
	   }
	   //---------------------------------------------
	   for(var i=0;i<c_one_borrowItem.length;i++)
	    {
		  if(c_two_borrowItem[i].checked==true)
		
		  {
	  	  c3=c_two_borrow[i];
		//alert(f1);	    	
	  	  }	
			
	   }
	//------------------------------------------------------
	
	
	}
	var c_cout=c1+c2+c3;
	//alert(c_cout);
	return c_cout;
	
}








//---------------------------------------------------------------


function house()
{
	var h1=0;
	var h2=0;
	var h3=0;
	var h4=0;
	
	for(var i=0;i<h_detItem.length;i++)
	    {
		  if(h_detItem[i].checked==true)
		
		  {
	  	  h1=h_det[i];
		//alert(f1);	    	
	  	  }	
			
	   }
	//------------------------------------------------
		for(var i=0;i<h_typeItem.length;i++)
	    {
		  if(h_typeItem[i].checked==true)
		
		  {
	  	  h2=h_type[i];
		//alert(f1);	    	
	  	  }	
			
	   }
	//---------------------------------------------------------------
	for(var i=0;i<h_typeItem.length;i++)
	    {
		  if(h_goodsItem[i].checked==true)
		
		  {
	  	  h3=h_goods[i];
		//alert(f1);	    	
	  	  }	
			
	   }
	//--------------------------------------------------------------
		for(var i=0;i<h_typeItem.length;i++)
	    {
		  if(h_retItem[i].checked==true)
		
		  {
	  	  h4=h_ret[i];
		//alert(f1);	    	
	  	  }	
			
	   }
	//------------------------------------------------------------   
	
	var h=h1+h2+h3+h4;
	//alert(h);
	return h;
	
	
}
//----------------------------------------------------------------------------------------------------------

function  car()
{
	
var c1=0;
var c2=0;


	for(var i=0;i<c_agrItem.length;i++)
	    {
		  if(c_agrItem[i].checked==true)
		
		  {
	  	  c1=c_agr[i];
		//alert(f1);	    	
	  	  }	
			
	   }
//----------------------------------------------------------------
	for(var i=0;i<c_priItem.length;i++)
	    {
		  if(c_priItem[i].checked==true)
		
		  {
	  	  c2=c_pri[i];
		//alert(f1);	    	
	  	  }	
			
	   }

//--------------------------------------------------------

var c =c1+c2;
//alert(c);	
return c;
	
}

function  land()
{
	var l1=0;
	var l2=0;


	for(var i=0;i<l_eatItem.length;i++)
	    {
		  if(l_eatItem[i].checked==true)
		
		  {
	  	  l1=l_eat[i];
		//alert(f1);	    	
	  	  }	
			
	   }
//--------------------------------------------------
for(var i=0;i<l_retItem.length;i++)
	    {
		  if(l_retItem[i].checked==true)
		
		  {
	  	  l2=l_ret[i];
		//alert(f1);	    	
	  	  }	
			
	   }
	  var l =l1+l2;
    // alert(l);	
     return l; 
	   
	   //--------------------------------
	
}
//----------------------------------------------------------------------------------------

function tree()
{
	var t1=0;
	
	
	for(var i=0;i<t_priItem.length;i++)
	    {
		  if(t_priItem[i].checked==true)
		
		  {
	  	  t1=t_pri[i];
		//alert(f1);	    	
	  	  }	
			
	   }
	
	 
   //  alert(t1);	
     return t1; 
	 
	
	//-----------------------------
	
	
}

function lib()
{

 	var l1=0;
	
	
	for(var i=0;i<l_detItem.length;i++)
	    {
		  if(l_detItem[i].checked==true)
		
		  {
	  	   l1=l_det[i];
		//alert(f1);	    	
	  	  }	
			
	   }

	  // alert(l1);	
     return l1; 
}

//-----------------------------

function libOnClick()
{


for(var i=0;i<l_detItem.length;i++)
	    {
		  if(l_detItem[i].checked==true)
		
		  {
	  	 b_libaItem.item(i).checked=true;
		//alert(f1);	    	
	  	  }	
			
	   }


	
	
}
//----------------------------------------------------------


function use()
{
   var u1=0;
	for(var i=0;i<b_useItem.length;i++)
	    {
		  if(b_useItem[i].checked==true)
		
		  {
	  	   u1=b_use[i];
		//alert(f1);	    	
	  	  }	
			
	   }
//alert(u1);
return u1;

}
//---------------------------------------------------------------------------------------------



function borrow()
{


 var b1=0;
 var b2=0;
 var b3=0;
 var b4=0;
 var b5=0;
 
	for(var i=0;i<b_ageItem.length;i++)
	    {
		  if(b_ageItem[i].checked==true)
		
		  {
	  	   b1=b_age[i];
		//alert(f1);	    	
	  	  }	
			
	   }
//---------------------------------------------------
	for(var i=0;i<b_wedItem.length;i++)
	    {
		  if(b_wedItem[i].checked==true)
		
		  {
	  	   b2=b_wed[i];
		//alert(f1);	    	
	  	  }	
			
	   }
	   
	//----------------------------------------------------   
	 for(var i=0;i<b_eduItem.length;i++)
	    {
		  if(b_eduItem[i].checked==true)
		
		  {
	  	   b3=b_edu[i];
		//alert(f1);	    	
	  	  }	
			
	   }  
	   
//-----------------------------------------------------------------------
      for(var i=0;i<b_loansItem.length;i++)
	    {
		  if(b_loansItem[i].checked==true)
		
		  {
	  	   b4=b_loans[i];
		//alert(f1);	    	
	  	  }	
			
	   } 

//------------------------------------------------
 for(var i=0;i<b_afterItem.length;i++)
	    {
		  if(b_afterItem[i].checked==true)
		
		  {
	  	   b5=b_after[i];
		//alert(f1);	    	
	  	  }	
			
	   } 
var b=b1+b2+b3+b4+b5;

return b;

}
//------------------------------------------------------------------------------------------------
function backAbl()
{
    var b1=0;
 	var b2=0;
 	var b3=0;
 	var b4=0;
	var b5=0;
    var b6=0;
	var b7=0;
	var b8=0;
	
  for(var i=0;i<b_workItem.length;i++)
	    {
		  if(b_workItem[i].checked==true)
		
		  {
	  	   b1=b1+b_work[i];
		//alert(b1);	    	
	  	  }	
			
	   } 
//----------------------------------------------
 for(var i=0;i<b_yearItem.length;i++)
	    {
		  if(b_yearItem[i].checked==true)
		
		  {
	  	   b2=b_year[i];
	//alert(b2);	    	
	  	  }	
			
	   } 
//---------------------------------------------


var b_backValue=""
 for(var i=0;i<b_backItem.length;i++)
	    {
		  if(b_backItem[i].checked==true)
		
		  {
	  	   b3=b3+b_back[i];
	//	   
		//alert(b3);	    	
	//b_backValue=b_backValue+b_backItem[i]+","
		
	  	  }	
			
	   } 
    //document.forms.item(0).b_back.value=b_backValue
//--------------------------------------------
 for(var i=0;i<b_libaItem.length;i++)
	    {
		  if(b_libaItem[i].checked==true)
		
		  {
	  	   b4=b_liba[i];
	//	alert(b4);    	
	  	  }	
			
	   } 
//-------------------------------------------------
 for(var i=0;i<b_gov_qurItem.length;i++)
	    {
		  if(b_gov_qurItem[i].checked==true)
		
		  {
	  	   b5=b_gov_qur[i];
	//	alert(b5);    	
	  	  }	
			
	   } 
	  //---------------------------------------------
	 for(var i=0;i<b_p_qurItem.length;i++)
	    {
			
		  if(b_p_qurItem[i].checked==true)
		
		  {
			 // alert(b_p_qur[i]);
	  	   b6=b_p_qur[i];
		//alert("dddd"+b6);	    	
	  	  }	
			
	   } 
	  // 	alert(b_p_qur[i].value); 
	//--------------------------------------------
	 for(var i=0;i<b_costItem.length;i++)
	    {
		  if(b_costItem[i].checked==true)
		
		  {
	  	   b7=b_cost[i];
		//alert(b7);  	
	  	  }	
			
	   }  
	//--------------------------------------------------
	 for(var i=0;i<b_listItem.length;i++)
	    {
		  if(b_listItem[i].checked==true)
		
		  {
	  	   b8=b_list[i];
	//	alert(b8);	
	  	  }	
			
	   }
     
   var b=b1+b2+b3+b4+b5+b6+b7+b8;
 //  alert(b);
   return b;
}
//--------------------------------------------------------------------------------------------------
function faceBoo()
{
	var fa=0;
	var phone=0;
	var count=0;
	
	if(faceItem.item(0).value!="")
	{
		fa=parseInt(faceItem.item(0).value);
	}
	 if(phoneItem.item(0).value!="")
	{
		phone=parseInt(phoneItem.item(0).value);
	}
	count=fa+phone;
	//alert(count);
	return  count;
}
//---------------------------------------=======------------------------------------
function faceChange()
{

   if(faceItem.item(0).value>30)
   {
	   faceItem.item(0).value=30;
	   
   }
   if(faceItem.item(0).value<0)
   {
	   faceItem.item(0).value=0;
	   
   }
   	if(phoneItem.item(0).value>70)
   {
	   phoneItem.item(0).value=70;
	   
   }
      if(phoneItem.item(0).value<0)
   {
	   phoneItem.item(0).value=0;
	   
   }
	
}


 function checkNum(obj){ 
        //为了去除最后一个. 
        obj.value = obj.value.replace(/\.$/g,""); 
 }
 
 	function clearNoNum(event,obj){ 
        //响应鼠标事件，允许左右方向键移动 
        event = window.event||event; 
        if(event.keyCode == 37 | event.keyCode == 39){ 
            return; 
        } 
        //先把非数字的都替换掉，除了数字和. 
        obj.value = obj.value.replace(/[^\d.]/g,""); 
        //必须保证第一个为数字而不是. 
        obj.value = obj.value.replace(/^\./g,""); 
        //保证只有出现一个.而没有多个. 
        obj.value = obj.value.replace(/\.{2,}/g,"."); 
        //保证.只出现一次，而不能出现两次以上 
        obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$","."); 
 } 
 
 
 
      function Check()
      { 
	  
	     
	  
	   
		 
	  
         for(var i=0;i<document.forms.item(0).elements.length-1;i++)
         {
			// calc();
          if(document.forms.item(0).elements[i].value=="")
          {
        //   alert("当前表单不能有空项");
		 //    if(confirm("当前有未选定的项 ，确定提交？"))
			 
		    // {   
			     //calc();
			//	 document.forms.item(0).submit();
				 return false;
			 // }else{
          // document.forms.item(0).elements[i].focus();
		   //  }
		  //document.forms.item(0).elements.item(0).checked.
           
          }
         }
		 // document.forms.item(0).submit();
         return true;
        
      }
	  
	 //=--------------------------------------------------------------- 
function firm()
 
   {

  
    if(confirm("确定提交？"))
 
    { 

       // document.forms.item(0).submit();

      } 

    else
    { 
 
 

    }

 

}

function dateUtil()
{
	
	
var now=new Date();
var year=now.getYear()+"";
var month=now.getMonth()+1;
var day=now.getDate()+"";
var hours=now.getHours()+"";
var minutes=now.getMinutes()+"";
var seconds=now.getSeconds()+"";
 
var dateStr=""+Math.floor(Math.random()*10)+Math.floor(Math.random()*10)+Math.floor(Math.random()*10)+Math.floor(Math.random()*10)+Math.floor(Math.random()*10)+Math.floor(Math.random()*10);
	return dateStr;
}

function checkUser()
{
	var r_id=document.getElementsByName("r_id");
	var r_name=document.getElementsByName("r_name");
	
	if(r_name.item(0).value=="")
	{
	
	alert("请填写姓名！");
	r_name.item(0).focus();
	return false;
	}else
	{
	 	
		
	if(r_id.item(0).value=="")
	{
		r_id.item(0).value=dateUtil();
 
		return true;
	}else
	{
		
			return true;
		}
		
	}

}

function checkCporRp(str)
{
var objarray=str.length;
var chestr=",";
for(i=0;i<objarray;i++)
{
  if(str[i].checked == true)
  {
   chestr+=str[i].value;
   
  }
  
 }
}