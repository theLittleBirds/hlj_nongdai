


var self_edu=new Array(0,2,4);//本人学历
var self_eduItem=document.getElementsByName("self_edu");

var self_age=new Array(1,3,2,0);
var self_ageItem=document.getElementsByName("self_age");

var wife_wed=new Array(5,3,0);
var wife_wedItem=document.getElementsByName("wife_wed");

var wife_with=new Array(2,0);
var wife_withItem=document.getElementsByName("wife_with");

var wife_borrow=new Array(3,0);
var wife_borrowItem=document.getElementsByName("wife_borrow");


var wife_phone=new Array(3,0);
var wife_phoneItem=document.getElementsByName("wife_phone");


var father_borrow=new Array(5,0);
var father_borrowItem=document.getElementsByName("father_borrow");

var father_phone=new Array(2,0);
var father_phoneItem=document.getElementsByName("father_phone");


var mother_borrow=new Array(5,0)
var mother_borrowItem=document.getElementsByName("mother_borrow");

var mother_phone=new Array(2,0);
var mother_phoneItem=document.getElementsByName("mother_phone");


var son1_borrow=new Array(7,0);
var son1_borrowItem=document.getElementsByName("son1_borrow");


//var son1_work=new Array(0,1);
var son1_workItem=document.getElementsByName("son1_work");
var son1_eduItem=document.getElementsByName("son1_edu");
var son1_edu1=new Array(2,7);
var son1_edu2=new Array(0,3);


var  son1_wed=new Array(1,3);
var son1_wedItem=document.getElementsByName("son1_wed");

var son1_phone=new Array(4,0);
var son1_phoneItem=document.getElementsByName("son1_phone");





var son2_borrow=new Array(7,0);
var son2_borrowItem=document.getElementsByName("son1_borrow");


//var son1_work=new Array(0,1);
var son2_workItem=document.getElementsByName("son2_work");
var son2_eduItem=document.getElementsByName("son2_edu");
var son2_edu1=new Array(2,7);
var son2_edu2=new Array(0,3);


var  son2_wed=new Array(1,3);
var son2_wedItem=document.getElementsByName("son1_wed");

var son2_phone=new Array(4,0);
var son2_phoneItem=document.getElementsByName("son1_phone");


var home_lia=new Array(-1,-1,-1,-3,-5);
var home_liaItem=document.getElementsByName("home_liab");

var home_house=new Array(5,5,7,-5);
var home_houseItem=document.getElementsByName("home_house");


var home_car=new Array(2,3);
var home_carItem=document.getElementsByName("home_car");

var home_debt=new Array(3,6,9,12,15);
var home_debtItem=document.getElementsByName("home_debt");

var home_loan=new Array(0,-3,-5,-10);
var home_loanItem=document.getElementsByName("home_loan");





var home_cost=new Array(-5,-7);
var home_costItem=document.getElementsByName("home_cost");


var farm_year=new Array(0,3,5);
var farm_yearItem=document.getElementsByName("farm_year");

var farm_goods=new Array(3,6,9,15);
var farm_goodsItem=document.getElementsByName("farm_goods");

var farm_type=new Array(12,0);
var farm_typeItem=document.getElementsByName("farm_type");

var farm_area1=new Array(6,8,10);
var farm_area2=new Array(2,3,5);
var farm_areaItem=document.getElementsByName("farm_area");

var op_type=new Array(12,0);
var op_typeItem=document.getElementsByName("op_type");

var month_sale=new Array(5,7,10);
var month_saleItem=document.getElementsByName("month_sale");


var p_lab=new Array(0,4,10,15,20);
var p_labItem=document.getElementsByName("p_lab");


var farm_arr=new Array(30,25,20,15,10,0,-15,-30);
var farm_arrItem=document.getElementsByName("farm_arr");


//------------------------------------------------------------

var self_edu2=new Array(0,3,5);//本人学历
var self_eduItem2=document.getElementsByName("self_edu2");

var self_age2=new Array(5,10,5,0);
var self_ageItem2=document.getElementsByName("self_age2");

var self_wed=new Array(20,1,10,0);
var self_wedItem=document.getElementsByName("self_wed");


var home_gov=new Array(-3,-7,-10);
var home_govItem=document.getElementsByName("home_gov");

var home_sel=new Array(-5,-10,-15);
var home_selItem=document.getElementsByName("home_sel");

var home_isy=new Array(-5,-20,-30);
var home_isyItem=document.getElementsByName("home_isy");



var home_loanCout=new Array(65,60,55,50,45,15);
var home_loanCoutItem=document.getElementsByName("home_loanCout");

function self()
{
    var s1=0;
    var s2=0;
    for(var i=0;i<self_eduItem.length;i++)
    {
        if(self_eduItem[i].checked==true)

        {
            s1=self_edu[i];

            //alert(s1);
        }
    }


    for(var i=0;i<self_ageItem.length;i++)
    {
        if(self_ageItem[i].checked==true)

        {
            s2=self_age[i];

            //alert(s1);
        }
    }


    var count=s1+s2;

    return count;


}

//--------------------------------------------------------


function wife()
{

    var w1=0;
    var w2=0;
    var w3=0;
    var w4=0;

    for(var i=0;i<wife_wedItem.length;i++)
    {
        if(wife_wedItem[i].checked==true)

        {
            w1=wife_wed[i];

            //alert(s1);
        }
    }



    for(var i=0;i<wife_withItem.length;i++)
    {
        if(wife_withItem[i].checked==true)

        {
            w2=wife_with[i];

            //alert(s1);
        }
    }



    for(var i=0;i<wife_borrowItem.length;i++)
    {
        if(wife_borrowItem[i].checked==true)

        {
            w3=wife_borrow[i];

            //alert(s1);
        }
    }




    for(var i=0;i<wife_phoneItem.length;i++)
    {
        if(wife_phoneItem[i].checked==true)

        {
            w4=wife_phone[i];

            //alert(s1);
        }
    }

    var count=w1+w2+w3+w4;

    return count;

}

//---------------------------------------------------
function father()
{

    var f1=0;
    var f2=0;

    for(var i=0;i<father_borrowItem.length;i++)
    {
        if(father_borrowItem[i].checked==true)

        {
            f1=father_borrow[i];

            //alert(s1);
        }
    }



    for(var i=0;i<father_phoneItem.length;i++)
    {
        if(father_phoneItem[i].checked==true)

        {
            f2=father_phone[i];

            //alert(s1);
        }
    }

    var count =f1+f2;

    return count;





}
//---------------------------------------------

function mother()
{
    var m1=0;
    var m2=0;


    for(var i=0;i<mother_borrowItem.length;i++)
    {
        if(mother_borrowItem[i].checked==true)

        {
            m1=mother_borrow[i];

            //alert(s1);
        }
    }

    for(var i=0;i<mother_phoneItem.length;i++)
    {
        if(mother_phoneItem[i].checked==true)

        {
            m2=mother_phone[i];

            //alert(s1);
        }
    }

    var count=m1+m2;

    return count;

}


//----------------------------------------------
function son1()
{

    var s1=0;
    var s2=0;
    var s3=0;

    var s4=0;
    var s5=0;


    for(var i=0;i<son1_borrowItem.length;i++)
    {
        if(son1_borrowItem[i].checked==true)

        {
            s1=son1_borrow[i];

            //alert(s1);
        }
    }


    if(son1_workItem[0].checked==true)
    {
        for(var i=0;i<son1_eduItem.length;i++)
        {
            if(son1_eduItem[i].checked==true)

            {
                s2=son1_edu1[i];

                //alert(s1);
            }
        }

    }


    if(son1_workItem[1].checked==true)
    {
        for(var i=0;i<son1_eduItem.length;i++)
        {
            if(son1_eduItem[i].checked==true)

            {
                s3=son1_edu2[i];

                //alert(s1);
            }
        }


        for(var i=0;i<son1_wedItem.length;i++)
        {
            if(son1_wedItem[i].checked==true)

            {
                s4=son1_wed[i];

                //alert(s1);
            }
        }
    }


    for(var i=0;i<son1_phoneItem.length;i++)
    {
        if(son1_phoneItem[i].checked==true)

        {
            s5=son1_phone[i];

            //alert(s1);
        }
    }


    var count=s1+s2+s3+s4+s5;

    return count;

}

//-------------------------------------------------------------------


function son2()
{

    var s1=0;
    var s2=0;
    var s3=0;

    var s4=0;
    var s5=0;

    for(var i=0;i<son2_borrowItem.length;i++)
    {
        if(son2_borrowItem[i].checked==true)

        {
            s1=son2_borrow[i];

            //alert(s1);
        }
    }


    if(son2_workItem[0].checked==true)
    {
        for(var i=0;i<son2_eduItem.length;i++)
        {
            if(son2_eduItem[i].checked==true)

            {
                s2=son2_edu1[i];

                //alert(s1);
            }
        }

    }


    if(son2_workItem[1].checked==true)
    {
        for(var i=0;i<son2_eduItem.length;i++)
        {
            if(son2_eduItem[i].checked==true)

            {
                s3=son2_edu2[i];

                //alert(s1);
            }
        }


        for(var i=0;i<son2_wedItem.length;i++)
        {
            if(son2_wedItem[i].checked==true)

            {
                s4=son2_wed[i];

                //alert(s1);
            }
        }
    }
    for(var i=0;i<son2_phoneItem.length;i++)
    {
        if(son2_phoneItem[i].checked==true)

        {
            s5=son2_phone[i];

            //alert(s1);
        }
    }

    var count=s1+s2+s3+s4+s5;;

    return count;

}
//-----------------------------------------


function homeliab()//-------------------------------逾期次数
{
    var h1=0;


    h1=Number(document.getElementsByName("home_liab").item(0).value)*-5;



    var count =h1;
    return count;

}

//--------------------------------

function homehose()
{

    var h1=0;
    var h2=0;

    for(var i=0;i<home_houseItem.length;i++)
    {
        if(home_houseItem[i].checked==true)

        {
            h1=home_house[i];

            //alert(s1);
        }
    }

    for(var i=0;i<home_carItem.length;i++)
    {
        if(home_carItem[i].checked==true)

        {
            h2=home_car[i];

            //alert(s1);
        }
    }

    var count=h1+h2;
    return count;
}
//----------------------------------------------------------

function homedebt()
{

    var h1=0;


    for(var i=0;i<home_debtItem.length;i++)
    {
        if(home_debtItem[i].checked==true)

        {
            h1=home_debt[i];

            //alert(s1);
        }
    }

    var count =h1;
    return count;
}
//----------------------------------------------------


function homeloan()
{
    var h1=0;

    for(var i=0;i<home_loanItem.length;i++)
    {
        if(home_loanItem[i].checked==true)

        {

            h1=home_loan[i];

            //alert(s1);
        }
    }

    var count=h1;
    return count;
}
//-----------------------------

function homegov()
{
    var h1=0;
    var h2=0;
    var h3=0;
    var h4=0;
    for(var i=0;i<home_govItem.length;i++)
    {
        if(home_govItem[i].checked==true)

        {

            h1=home_gov[i];

            //alert(s1);
        }
    }

    for(var i=0;i<home_selItem.length;i++)
    {
        if(home_selItem[i].checked==true)

        {

            h2=home_sel[i];

            //alert(s1);
        }
    }

    for(var i=0;i<home_isyItem.length;i++)
    {
        if(home_isyItem[i].checked==true)

        {

            h3=home_isy[i];

            //alert(s1);
        }
    }


    for(var i=0;i<home_loanCoutItem.length;i++)
    {
        if(home_loanCoutItem[i].checked==true)

        {

            h4=home_loanCout[i];

            //alert(s1);
        }
    }

    var count=h1+h2+h3+h4;
    return count;
}
//----------------------------------------


function homecost()
{

    var h1=0;

    for(var i=0;i<home_costItem.length;i++)
    {
        if(home_costItem[i].checked==true)

        {

            h1=home_cost[i];

            //alert(s1);
        }
    }
    var count=h1;
    return count;

}
//-----------------------------------------

function farmyear()
{
    var f1=0;
    for(var i=0;i<farm_yearItem.length;i++)
    {
        if(farm_yearItem[i].checked==true)

        {

            f1=farm_year[i];

            //alert(s1);
        }
    }
    var count=f1;
    return count;

}
//-------------------------------------------

function farmgoods()
{
    var f1=0;
    for(var i=0;i<farm_goodsItem.length;i++)
    {
        if(farm_goodsItem[i].checked==true)

        {

            f1=farm_goods[i];

            //alert(s1);
        }
    }
    var count=f1;
    return count;

}
//-------------------------------------------

function farmtype()
{
    var f1=0;
    var f2=0;


    if(farm_typeItem[0].checked==true)
    {
        for(var i=0;i<farm_areaItem.length;i++)
        {
            if(farm_areaItem[i].checked==true)

            {
                f1=farm_area1[i];

                //alert(s1);
            }
        }

    }

    if(farm_typeItem[1].checked==true)
    {
        for(var i=0;i<farm_areaItem.length;i++)
        {
            if(farm_areaItem[i].checked==true)

            {
                f2=farm_area2[i];

                //alert(s1);
            }
        }

    }
    var count=f1+f2;
    return count;

}
//------------------------------------------------

function optype()
{
    var o1=0;

    for(var i=0;i<op_typeItem.length;i++)
    {
        if(op_typeItem[i].checked==true)

        {
            o1=op_type[i];

            //alert(s1);
        }
    }

    var count=o1;
    return count;
}
//------------------------------

function monthsale()
{
    var m1=0;
    for(var i=0;i<month_saleItem.length;i++)
    {
        if(month_saleItem[i].checked==true)

        {
            m1=month_sale[i];

            //alert(s1);
        }
    }

    var count=m1;
    return count;
}
//------------------------------------------

function plab()
{

    var p1=0;
    for(var i=0;i<p_labItem.length;i++)
    {
        if(p_labItem[i].checked==true)

        {
            p1=p_lab[i];

            //alert(s1);
        }
    }

    var count=p1;
    return count;

}
//------------------------------------------



function farmarr()
{

    var f1=0;
    for(var i=0;i<farm_arrItem.length;i++)
    {
        if(farm_arrItem[i].checked==true)

        {
            f1=farm_arr[i];

            //alert(s1);
        }
    }

    var count=f1;
    return count;

}




function self2()
{

    var s1=0;
    for(var i=0;i<self_eduItem2.length;i++)
    {
        if(self_eduItem2[i].checked==true)

        {
            s1=self_edu2[i];

            //alert(s1);
        }
    }


    var s2=0;
    for(var i=0;i<self_ageItem2.length;i++)
    {
        if(self_ageItem2[i].checked==true)

        {
            s2=self_age2[i];

            //alert(s1);
        }
    }


    var s3=0;
    for(var i=0;i<self_wedItem.length;i++)
    {
        if(self_wedItem[i].checked==true)

        {
            s3=self_wed[i];

            //alert(s1);
        }
    }


    var count=s1+s2+s3;
    return count;

}


function calc()
{
	var user=document.getElementsByName("r_name").item(0).value;
	
	//if(user=="")
	//{
	//	document.getElementsByName("r_name").item(0).focus();
	//	alert("请填写姓名");
	//return false
//	}
	
	var parta=self()+wife()+father()+mother()+son1()+son2()+homehose()+homedebt()+homeloan()+homecost();
	var partb=farmyear()+farmgoods()+farmtype()+optype()+monthsale()+plab()+farmarr();
    var partc=self2()+homegov()+homeliab();
	var resWordA="";
	var resWordB="";
	var resWordC="";
	var money=0;
	
	if(parta>=61){ resWordA="A"; money=money+80000 }
	else if(parta>=56&&parta<=60){resWordA="B"; money=money+70000 }
	else if(parta>=51&&parta<=55){resWordA="C";  money=money+60000}
	else if(parta>=46&&parta<=50){resWordA="D";  money=money+50000}
	else if(parta>=41&&parta<=45){resWordA="E";  money=money+40000}
	else if(parta>=36&&parta<=40){resWordA="F";  money=money+30000}
	else if(parta>=31&&parta<=35){resWordA="G";  money=money+20000}
	else if(parta>=26&&parta<=30){resWordA="H";  money=money+10000}
 	else if(parta<20){resWordA="I";money=money+0 }
	
	//alert(resWordA);
	
	 if(partb>76){ resWordB="A"; money=money+80000 }
	  else if(partb>=71&&partb<=75){resWordB="B"; money=money+70000 }
	   else if(partb>=66&&partb<=70){resWordB="C"; money=money+60000 }
	 else if(partb>=61&&partb<=65){resWordB="D"; money=money+50000 }
	  else if(partb>=56&&partb<=60){resWordB="E";  money=money+40000}
	   else if(partb>=51&&partb<=55){resWordB="F";  money=money+30000}
	 else if(partb>=41&&partb<=50){resWordB="G";  money=money+20000}
	  else if(partb>=26&&partb<=40){resWordB="H";  money=money+10000}
	 else if(partb<=25){resWordB="I";money=money+0 }
	
	//alert(partc);
	
	
	 if(partc>=56){ resWordC="A"; money=money+40000 }
	  else if(partc>=46&&partc<=55){resWordC="B"; money=money+30000 }
	   else if(partc>=36&&partc<=45){resWordC="C"; money=money+20000 }
 	  else if(partc>=31&&partc<=35){resWordC="D";  money=money+10000}
 	 else if(partc<=30){resWordC="E";money=money+0 }
	
	
	
	document.getElementsByName("parta").item(0).value=parta+"";
    document.getElementsByName("partb").item(0).value=partb+"";
	document.getElementsByName("partc").item(0).value=partc+"";
	document.getElementsByName("res").item(0).value=resWordA+resWordB+resWordC;
//	alert("---"+money);
	document.getElementsByName("resmoney").item(0).value=money;
	//alert(parta);
	//alert(resWordC)
  document.forms.item(0).submit();
	
}