<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="ThemeBucket">
  <link rel="shortcut icon" href="#" type="image/png">

  <title>产业链客户评分模型</title>

    <!--ios7-->
    <link rel="stylesheet" type="text/css" href="js/ios-switch/switchery.css" />

    <!--icheck-->
    <link href="${contextPath}/industry/js/iCheck/skins/minimal/minimal.css" rel="stylesheet">
    <link href="${contextPath}/industry/js/iCheck/skins/minimal/red.css" rel="stylesheet">
    <link href="${contextPath}/industry/js/iCheck/skins/minimal/green.css" rel="stylesheet">
    <link href="${contextPath}/industry/js/iCheck/skins/minimal/blue.css" rel="stylesheet">
    <link href="${contextPath}/industry/js/iCheck/skins/minimal/yellow.css" rel="stylesheet">
    <link href="${contextPath}/industry/js/iCheck/skins/minimal/purple.css" rel="stylesheet">

    <link href="${contextPath}/industry/js/iCheck/skins/square/square.css" rel="stylesheet">
    <link href="${contextPath}/industry/js/iCheck/skins/square/red.css" rel="stylesheet">
    <link href="${contextPath}/industry/js/iCheck/skins/square/green.css" rel="stylesheet">
    <link href="${contextPath}/industry/js/iCheck/skins/square/blue.css" rel="stylesheet">
    <link href="${contextPath}/industry/js/iCheck/skins/square/yellow.css" rel="stylesheet">
    <link href="${contextPath}/industry/js/iCheck/skins/square/purple.css" rel="stylesheet">

    <link href="${contextPath}/industry/js/iCheck/skins/flat/grey.css" rel="stylesheet">
    <link href="${contextPath}/industry/js/iCheck/skins/flat/red.css" rel="stylesheet">
    <link href="${contextPath}/industry/js/iCheck/skins/flat/green.css" rel="stylesheet">
    <link href="${contextPath}/industry/js/iCheck/skins/flat/blue.css" rel="stylesheet">
    <link href="${contextPath}/industry/js/iCheck/skins/flat/yellow.css" rel="stylesheet">
    <link href="${contextPath}/industry/js/iCheck/skins/flat/purple.css" rel="stylesheet">

    <!--multi-select-->
    <link rel="stylesheet" type="text/css" href="${contextPath}/industry/js/jquery-multi-select/css/multi-select.css" />

    <!--file upload-->
    <link rel="stylesheet" type="text/css" href="${contextPath}/industry/css/bootstrap-fileupload.min.css" />

    <!--tags input-->
    <link rel="stylesheet" type="text/css" href="js/jquery-tags-input/jquery.tagsinput.css" />



  <link href="${contextPath}/industry/css/style.css" rel="stylesheet">
  <link href="${contextPath}/industry/css/style-responsive.css" rel="stylesheet">

  <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!--[if lt IE 9]>
  <script src="js/html5shiv.js"></script>
  <script src="js/respond.min.js"></script>
  <![endif]-->
  <script language="javascript" src="${contextPath}/industry/js/code2.js"></script>
  
  
</head>

<body  style="background-color: #E3E3E3  ">

<section>
    <!-- left side start--><!--sidebar nav end-->

 
    </div>
    <!-- left side end-->

    <!-- main content start-->
    
    <form  action="${contextPath}/admin/industry/save" method="post" name="myform">
   

        <!-- header section start-->
       

        
        
        <!-- header section end-->

        <!-- page heading start-->
        <div class="col-md-12">
          <section class="panel">
            <header class="panel-heading"></header>
            <div class="panel-body">
              <div class= "" align="center">
                <div class="square-red single-row"></div>
                <p align="center">
                  <input name="into_piece_id" type="hidden" style="BORDER-TOP-STYLE: none; BORDER-RIGHT-STYLE: none; BORDER-LEFT-STYLE: none; BORDER-BOTTOM-STYLE: solidbackground:transparent;border:1px solid #ffffff" value="${into_piece_id}"  >         
                <span class="page-heading"   style="alignment-adjust:central" >
                  <h3>&nbsp;违约成本
                    <input name="parta" type="text" class="tx" style="BORDER-TOP-STYLE: none; BORDER-RIGHT-STYLE: none; BORDER-LEFT-STYLE: none; BORDER-BOTTOM-

STYLE: solidbackground:transparent;border:1px solid #eff0f4; background-color:#eff0f4"    value="${industry.parta}" size="3"  readonly>
                    还款能力
                    <input name="partb" type="text" class="tx" style="BORDER-TOP-STYLE: none; BORDER-RIGHT-STYLE: none; BORDER-LEFT-STYLE: none; BORDER-BOTTOM-

STYLE: solidbackground:transparent;border:1px solid #eff0f4; background-color:#eff0f4" value="${industry.partb}" size="3" readonly>
                    还款意愿
                    <input name="partc" type="text" class="tx" id="partc" style="BORDER-TOP-STYLE: none; BORDER-RIGHT-STYLE: none; BORDER-LEFT-STYLE: none; BORDER-BOTTOM-

STYLE: solidbackground:transparent;border:1px solid #eff0f4; background-color:#eff0f4" value="${industry.partc}" size="3" readonly>
                    结果：
                    <input name="res" type="text" class="tx" id="res" style="BORDER-TOP-STYLE: none; BORDER-RIGHT-STYLE: none; BORDER-LEFT-STYLE: none; BORDER-BOTTOM-

STYLE: solidbackground:transparent;border:1px solid #eff0f4; background-color:#eff0f4" value="${industry.res}" size="3" readonly>
                    授信金额：
                    <input name="resmoney" type="text" class="tx" id="money" style="BORDER-TOP-STYLE: none; BORDER-RIGHT-STYLE: none; BORDER-LEFT-STYLE: none; BORDER-BOTTOM-

STYLE: solidbackground:transparent;border:1px solid #eff0f4; background-color:#eff0f4"   size="5" readonly value="${industry.resmoney}">
                    <input name="credit" type="text" style="BORDER-TOP-STYLE: none; BORDER-RIGHT-STYLE: none; BORDER-LEFT-STYLE: none; BORDER-BOTTOM-STYLE: solidbackground:transparent;border:1px solid #ffffff" value=""  >
                </h3>
                </span></div>
            </div>
          </section>
        </div>
        <div class=" ">
          <h3></h3>
          <h3>&nbsp;&nbsp;违约成本</h3>
   
      </div>
        <!-- page heading end-->

        <!--body wrapper start-->
        
        
        
        
        
        <div class="wrapper">
            <div class="row">
            
            <div class="col-md-12" style="display:none">
                    <section class="panel">
                        <header class="panel-heading"></header>
                        <div class="panel-body">
                          <div class="slide-toggle">
                            <div class="square-red single-row"></div>
    
 
                               
                              <h4>
                            姓名：  <input name="r_name" type="text" style="BORDER-TOP-STYLE: none; BORDER-RIGHT-STYLE: none; BORDER-LEFT-STYLE: none; BORDER-BOTTOM-STYLE: solid; " maxlength="4">   <input name="r_id" type="text" style="BORDER-TOP-STYLE: none; BORDER-RIGHT-STYLE: none; BORDER-LEFT-STYLE: none; BORDER-BOTTOM-STYLE: solidbackground:transparent;border:1px solid #ffffff"value=" "  readonly /></h4>
                              </span> 
                             
                            <p>&nbsp;</p>

 <strong> </strong>
                               
                               

   
  
  <strong> </strong>
                               
                                
  
  
                               
                              
                          </div>
                        </div>
                    </section>
              </div>
            
            
            
              <div class="col-md-12">
                <section class="panel">
                  <header class="panel-heading"> 本人 <span class="tools pull-right"> </span> </header>
                  <div class="panel-body">
                    <div class="slide-toggle">
                      <div><strong>学历：</strong></div>
                      <div class="square-red single-row">
                        <div class="radio ">
                          <label>
                            <input  name="self_edu"  type="radio"     value="0"    ${industry.selfEdu== 0 ? 'checked':''}>  
                          高中以下 </label>
                        </div>
                      </div>
                      <div class="square-red single-row">
                        <div class="radio ">
                          <label>
                            <input  name="self_edu"  type="radio"     value="1"  ${industry.selfEdu== 1 ? 'checked':''}>
                          高中</label>
                        </div>
                      </div>
                      <div class="square-red single-row">
                        <div class="radio ">
                          <input  name="self_edu"  type="radio"    value="2"  ${industry.selfEdu== 2 ? 'checked':''}>
                          <label>大专及以上 </label>
                        </div>
                      </div>
                      
                      <p>&nbsp;</p>
                      <p>&nbsp;</p>
                      <div><strong>年龄：</strong></div>
                      <div class="square-red single-row">
                        <div class="radio ">
                          <input  type="radio"  name="self_age"value="0"   ${industry.selfAge== 0 ? 'checked':''}>
                          <label>20-30</label>
                        </div>
                      </div>
                      <div class="square-red single-row">
                        <div class="radio ">
                          <input  type="radio"  name="self_age" value="1"  ${industry.selfAge== 1 ? 'checked':''}>
                          30-45
                        </div>
                      </div>
                      
                      <div class="square-red single-row">
                        <div class="radio ">
                          <input  type="radio"  name="self_age" value="2"  ${industry.selfAge== 2 ? 'checked':''}>
                          45
                          -55
                          
                        </div>
                      </div>
                      
                      <div class="square-red single-row">
                        <div class="radio ">
                          <input  type="radio"  name="self_age" value="3"  ${industry.selfAge== 3 ? 'checked':''}>
                          <label>55-60 </label>
                        </div>
                      </div>
                      
                    </div>
                  </div>
                </section>
              </div>
              <div class="col-md-12">
                <section class="panel">
                  <header class="panel-heading"> 配偶 <span class="tools pull-right"> </span></header>
                  <div class="panel-body">
                    <div class="slide-toggle">
                      <div><strong>婚姻：</strong></div>
                      <div class="square-red single-row">
                        <div class="radio ">
                          <input  name="wife_wed"  type="radio"      value="0"  ${industry.wifeWed== 0 ? 'checked':''}>
                          已婚
                        </div>
                      </div>
                      <div class="square-red single-row">
                        <div class="radio ">
                          <input  name="wife_wed"  type="radio"     value="1" ${industry.wifeWed== 1 ? 'checked':''}>
                          <label>二婚 </label>
                        </div>
                      </div>
                      <div class="square-red single-row">
                        <div class="radio ">
                          <input  name="wife_wed"  type="radio"   alue="2" ${industry.wifeWed== 2 ? 'checked':''}>
                          <label>单身（未婚/离异） </label>
                        </div>
                      </div>
                       
                      <div><strong>是否同住：</strong></div>
                      <div class="square-red single-row">
                        <div class="radio ">
                          <input  type="radio"  name="wife_with" value="0"   ${industry.wifeWith== 0 ? 'checked':''}>
                          是
                        </div>
                      </div>
                      <div class="square-red single-row">
                        <div class="radio ">
                          <input  type="radio"  name="wife_with" value="1" ${industry.wifeWith== 1 ? 'checked':''}>
                          <label>否 </label>
                        </div>
                      </div>
                      <div class="square-red single-row"></div>
                    
                      <p>&nbsp;</p>
                      <p>&nbsp;</p>
                      <div><strong>能否作为共同借款人：</strong></div>
                      <div class="square-red single-row">
                        <div class="radio ">
                          <input  name="wife_borrow"  type="radio"      value="0"   ${industry.wifeBorrow== 0 ? 'checked':''}>
                          <label>能 </label>
                        </div>
                      </div>
                      <div class="square-red single-row">
                        <div class="radio ">
                          <input  name="wife_borrow"  type="radio"      value="1"  ${industry.wifeBorrow== 1 ? 'checked':''}>
                          <label>不能 </label>
                        </div>
                      </div>
                     <div><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</strong></div>
                      <div><strong>有无联系方式：</strong></div>
                      <div class="square-red single-row">
                        <div class="radio ">
                          <input  name="wife_phone"  type="radio"     value="0"  ${industry.wifePhone== 0 ? 'checked':''}>
                          <label>有 </label>
                        </div>
                      </div>
                      <div class="square-red single-row">
                        <div class="radio ">
                          <input  name="wife_phone"  type="radio"     value="1" ${industry.wifePhone== 1 ? 'checked':''}>
                          <label>没有 </label>
                        </div>
                      </div>
                      
                      
                    </div>
                  </div>
                </section>
              </div>
              <div class="col-md-12">
                  <section class="panel">
                        <header class="panel-heading">
                            父亲
                        <span class="tools pull-right">
                                 
                                
                          </span>
                        </header>
                        <div class="panel-body">
                            <div class="slide-toggle">
                              <div><strong>能否作为共同借款人：</strong></div>
                              <div class="square-red single-row">
                               <div class="radio ">
                                 <input  type="radio"  name="father_borrow" value="0" ${industry.fatherBorrow== 0 ? 'checked':''}>
                                 <label>能</label>
                          
                         
                               </div>
                              </div>
                              <div class="square-red single-row">
                                <div class="radio ">
                                  <input  type="radio"  name="father_borrow" value="1" ${industry.fatherBorrow== 1 ? 'checked':''}>
                                  <label>不能 </label>
                                </div>
                              </div>

  <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </div>
  
  <div><strong>是否有联系方式：</strong></div>
                              <div class="square-red single-row">
                               <div class="radio ">
                                 <input  type="radio"  name="father_phone"value="0"  ${industry.fatherPhone== 0 ? 'checked':''}>
                                 <label>有</label>
                          
                         
                               </div>
                              </div>
                              <div class="square-red single-row">
                                <div class="radio ">
                                  <input  type="radio"  name="father_phone" value="1" ${industry.fatherPhone== 0 ? 'checked':''}>
                                  <label>没有 </label>
                                </div>
                              </div>  
  
  
                               
                              
                          </div>
                        </div>
                </section>
              </div>
                
                
                
  <div class="col-md-12">
                    <section class="panel">
                        <header class="panel-heading"><span class="tools pull-right">
                                 
                                
                             </span>母亲</header>
                        <div class="panel-body">
                            <div class="slide-toggle">
                            

 <div><strong>能否作为共同借款人：</strong></div>
                              <div class="square-red single-row">
                               <div class="radio ">
                                 <input  type="radio"  name="mother_borrow" value="0"   ${industry.motherBorrow== 0 ? 'checked':''}>
                                 能</div>
                              </div>
                              <div class="square-red single-row">
                                <div class="radio ">
                                  <input  type="radio"  name="mother_borrow" value="1"  ${industry.motherBorrow== 1 ? 'checked':''}>
                                  不能</div>
                              </div>

  <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </div>
  
  <div><strong>是否有联系方式：</strong></div>
                              <div class="square-red single-row">
                               <div class="radio ">
                                 <input  type="radio"  name="mother_phone" value="0"  ${industry.motherPhone== 0 ? 'checked':''}>
                                 有</div>
                              </div>
                              <div class="square-red single-row">
                                <div class="radio ">
                                  <input  type="radio"  name="mother_phone" value="1" ${industry.motherPhone== 1 ? 'checked':''}>
                                  无</div>
                              </div>  
  
  
                               
                              
                          </div>
                        </div>
                    </section>
              </div>
  <div class="col-md-12">
    <section class="panel">
      <header class="panel-heading"><span class="tools pull-right"> </span>第一子女</header>
      <div class="panel-body">
        <div class="slide-toggle">
          <div><strong>能否作为共同借款人：</strong></div>
          <div class="square-red single-row">
            <div class="radio ">
              <label>
                <input  name="son1_borrow"  type="radio" value="0"  ${industry.son1Borrow== 0 ? 'checked':''}>
                是</label>
            </div>
          </div>
          <div class="square-red single-row">
            <div class="radio ">
              <input  name="son1_borrow"  type="radio" value="1"   ${industry.son1Borrow== 1 ? 'checked':''}>
              <label>否 </label>
            </div>
          </div>
          
          <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </div>
          <div><strong>在校生：</strong></div>
          <div class="square-red single-row">
            <div class="radio ">
              <label>
                <input  name="son1_work"  type="radio" value="0"  ${industry.son1Work== 0 ? 'checked':''} >
                是</label>
            </div>
          </div>
          <div class="square-red single-row"></div>
          <p>&nbsp;</p>
          <p>&nbsp;</p>
          <div><strong>在职人员：</strong></div>
          <div class="square-red single-row">
            <div class="radio ">
              <label>
                <input  name="son1_work"  type="radio" value="1"  ${industry.son1Work== 1 ? 'checked':''}>
                是</label>
            </div>
          </div>
          <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </div>
          <div><strong> 学历情况：</strong></div>
          <div class="square-red single-row">
            <div class="radio ">
              <input  name="son1_edu"  type="radio" value="0"   ${industry.son1Edu== 0 ? 'checked':''}>
              <label>高中           
                及以以下 </label>
            </div>
        </div>
          <div class="square-red single-row">
            <div class="radio ">
             <input  name="son1_edu"  type="radio" value="1"  ${industry.son1Edu== 1 ? 'checked':''}>
              <label>大专及以上 </label>
            </div>
          </div>
           
          <p>&nbsp;</p>
          <p>&nbsp;</p>
         
          <div><strong> 婚姻情况：</strong></div>
          <div class="square-red single-row">
            <div class="radio ">
              <input  name="son1_wed"  type="radio"     value="0"    ${industry.son1Wed== 0 ? 'checked':''}>
              <label>未婚 </label>
            </div>
          </div>
          <div class="square-red single-row">
            <div class="radio ">
              <input  name="son1_wed"  type="radio" value="1"  ${industry.son1Wed== 1 ? 'checked':''} >
              <label>已婚 </label>
            </div>
          </div>
    
          <div><strong>是否有联系方式：</strong></div>
          <div class="square-red single-row">
            <div class="radio ">
              <input  name="son1_phone"  type="radio" value="0"  ${industry.son1Phone== 0 ? 'checked':''}>
              有 </div>
          </div>
          <div class="square-red single-row">
            <div class="radio ">
              <input  name="son1_phone"  type="radio" value="1" ${industry.son1Phone== 1 ? 'checked':''} >
              <label>无 </label>
            </div>
          </div>
          <p>&nbsp;</p>
        </div>
      </div>
    </section>
  </div>
  <div class="col-md-12">
    <section class="panel">
      <header class="panel-heading"><span class="tools pull-right"> </span>第二子女</header>
      <div class="panel-body">
        <div class="slide-toggle">
          <div><strong>能否作为共同借款人：</strong></div>
          <div class="square-red single-row">
            <div class="radio ">
              <label>
                <input  name="son2_borrow"  type="radio" value="0" ${industry.son2Borrow== 0 ? 'checked':''} >
                是</label>
            </div>
          </div>
          <div class="square-red single-row">
            <div class="radio ">
              <input  name="son2_borrow"  type="radio" value="1" ${industry.son2Borrow== 1 ? 'checked':''} >
              <label>否 </label>
            </div>
          </div>
          <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </div>
          <div><strong>在校生：</strong></div>
          <div class="square-red single-row">
            <div class="radio ">
              <label>
                <input  name="son2_work"  type="radio" value="0"   ${industry.son2Work== 0 ? 'checked':''} >
                是</label>
            </div>
          </div>
          <p>&nbsp;</p>
          <p>&nbsp;</p>
          <div><strong>在职人员：</strong></div>
          <div class="square-red single-row">
            <div class="radio ">
              <label>
                <input  name="son2_work"  type="radio" value="1"  ${industry.son2Work== 1 ? 'checked':''}>
                是</label>
            </div>
          </div>
          <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </div>
          <div><strong> 学历情况：</strong></div>
          <div class="square-red single-row">
            <div class="radio ">
              <input  name="son2_edu"  type="radio" value="0"   ${industry.son2Edu== 0 ? 'checked':''}>
              <label>高中
                
                及以上 </label>
            </div>
          </div>
          <div class="square-red single-row">
            <div class="radio ">
            <input  name="son2_edu"  type="radio" value="1"  ${industry.son2Edu== 1 ? 'checked':''}>
              <label>大专及以上 </label>
            </div>
          </div>
          <p>&nbsp;</p>
          <p>&nbsp;</p>
          <div><strong> 婚姻情况：</strong></div>
          <div class="square-red single-row">
            <div class="radio ">
              <input  name="son2_wed"  type="radio"     value="0"  ${industry.son2Wed== 0 ? 'checked':''}>
              <label>未婚 </label>
            </div>
          </div>
          <div class="square-red single-row">
            <div class="radio ">
              <input  name="son2_wed"  type="radio" value="1"  ${industry.son2Wed== 1 ? 'checked':''} >
              <label>已婚 </label>
            </div>
          </div>
          <div><strong>是否有联系方式：</strong></div>
          <div class="square-red single-row">
            <div class="radio ">
              <input  name="son2_phone"  type="radio" value="0"  ${industry.son2Phone== 0 ? 'checked':''}>
              有 </div>
          </div>
          <div class="square-red single-row">
            <div class="radio ">
              <input  name="son2_phone"  type="radio" value="1"  ${industry.son2Phone== 1 ? 'checked':''} >
              <label>无 </label>
            </div>
          </div>
          <p>&nbsp;</p>
        </div>
      </div>
    </section>
  </div>
  <div class="col-md-12" style="display:none">
    <section class="panel">
      <header class="panel-heading"><span class="tools pull-right"> </span>家庭负担情况 yincang</header>
      <div class="panel-body">
        <div class="slide-toggle">
          <div class="square-red single-row">
            <div class="radio ">
              <input  name="home_liab"  type="radio" value="0" >
              <label></label>
              父亲无收入来源</div>
          </div>
          <div class="square-red single-row">
            <div class="radio ">
              <input  name="home_lia"  type="radio" value="1" >
              <label></label>
              母亲无收入来源</div>
          </div>
          <div class="square-red single-row">
            <div class="radio ">
              <input  name="home_lia"  type="radio" value="2" >
              <label>1个子女无收入来源</label>
            </div>
          </div>
          <div class="square-red single-row">
            <div class="radio ">
              <input  name="home_lia"  type="radio" value="3" >
              <label>2个子女无收入来源</label>
            </div>
          </div>
          <div></div>
          <div class="square-red single-row">
            <div class="radio ">
              <input  name="home_lia"  type="radio" value="4" >
              <label>3个子女无收入来源</label>
            </div>
          </div>
          <p>&nbsp;</p>
          <p>&nbsp;</p>
        </div>
      </div>
    </section>
  </div>
  <div class="col-md-12">
                    <section class="panel">
                        <header class="panel-heading"><span class="tools pull-right">
                                 
                                
                             </span>家庭资产情况</header>
                      <div class="panel-body">
                            <div class="slide-toggle">
   
                              <div><strong>住宅情况：</strong></div>
                              <div class="square-red single-row">
                               <div class="radio ">
                                 <input  name="home_house"  type="radio" value="0"   ${industry.homeHouse== 0 ? 'checked':''}>
                                 农村房屋</div>
                              </div>
                              <div class="square-red single-row">
                                <div class="radio ">
                                  <input  name="home_house"  type="radio" value="1"${industry.homeHouse== 1 ? 'checked':'' }>
                                  小产权房</div>
                              </div>
                              <div class="square-red single-row">
                                <div class="radio ">
                                  <input  name="home_house"  type="radio" value="2"${industry.homeHouse== 2 ? 'checked':'' }>
                                  商品房</div>
                              </div>
                              <div class="square-red single-row">
                                <div class="radio ">
                                  <input  name="home_house"  type="radio" value="3" ${industry.homeHouse== 3 ? 'checked':''}>
                                  在外租房无固定住所</div>
                              </div>
                             
 
    
 
                              <div><strong>车辆：</strong></div>
                              <div class="square-red single-row">
                               <div class="radio ">
                                 <input  name="home_car"  type="radio" value="0"  ${industry.homeCar== 0 ? 'checked':''}>
                                <label>轿车</label></div>
                              </div>
                              <div class="square-red single-row">
                                <div class="radio ">
                                  <input  name="home_car"  type="radio" value="1"  ${industry.homeCar== 1 ? 'checked':''}>
                                  <label>货车 </label>
                                </div>
                              </div>   
  
                               
                              
                          </div>
                      </div>
                    </section>
              </div>
 
 
 
 
 
 
 
 
 
 
 
 
  <div class="col-md-12">
                    <section class="panel">
                        <header style="display:none" class="panel-heading"><span class="tools pull-right">
                                 
                                
                             </span>负债（种养物价值）</header>
                      <div class="panel-body">
                        <div class="slide-toggle">
                          <div><strong><span class="panel-heading">种养物价值</span>：</strong></div>
                          <div class="square-red single-row">
                            <div class="radio ">
                                 <input  name="home_debt"  type="radio" value="0"  ${industry.homeDebt== 0 ? 'checked':''} >
                              10万以下 </div>
                              </div>
                              <div class="square-red single-row">
                                <div class="radio ">
                                  <input  name="home_debt"  type="radio" value="1" ${industry.homeDebt== 1 ? 'checked':''}>
                                  <label>10-20万</label>
                                </div>
                              </div>
                          <div class="square-red single-row">
                            <div class="radio ">
                                  <input  name="home_debt"  type="radio" value="2"  ${industry.homeDebt== 2 ? 'checked':''}>
                                  <label>20-30万</label>
                            </div>
                          </div>
                              
 
                          <div class="square-red single-row">
                                   <div class="radio ">
                                     <input  name="home_debt"  type="radio" value="3"  ${industry.homeDebt== 3 ? 'checked':''}>
                                     <label>30-40万</label>
                                   </div>
                          </div>
                          <div class="square-red single-row">
                            <div class="radio ">
                              <input  name="home_debt"  type="radio" value="4"  ${industry.homeDebt== 4 ? 'checked':''}>
                              <label>40万以上</label>
                            </div>
                          </div>
                          <p>&nbsp;</p>
            <p>&nbsp;</p>
                        </div>
                      </div>
                    </section>
              </div>
 
 
 
 
 
 
 <div class="col-md-12" style="display:none">
                    <section class="panel">
                        <header class="panel-heading"><span class="tools pull-right">
                                 
                                
                             </span>近期（2个月内）贷款到期金额 yinc</header>
                      <div class="panel-body">
                        <div class="slide-toggle">
                          <div class="square-red single-row">
                            <div class="radio ">
                                 <input  name="home_loan"  type="radio" value="0" >
                                 <label>0-5万</label>
                          
                         
                            </div>
                          </div>
                          <div class="square-red single-row">
                            <div class="radio ">
                              <input  name="home_loan"  type="radio" value="1" >
                              <label></label>
                              5-10万</div>
                          </div>
                          <div class="square-red single-row">
                                <div class="radio ">
                                  <input  name="home_loan"  type="radio" value="2" >
                                  <label>10-20万</label>
                                </div>
                          </div>
                              <div class="square-red single-row">
                                <div class="radio ">
                                  <input  name="home_loan"  type="radio" value="3" >
                                  <label>20万以上</label>
                                </div>
                              </div>
                         
 
                              <strong> </strong>
                              <div class="square-red single-row">
                                
                              </div>
                           
                                 
  
                               
                              
                        </div>
                      </div>
                    </section>
              </div>

 <!--          
  <div class="col-md-12">
                    <section class="panel">
                        <header class="panel-heading"><span class="tools pull-right">
                                 
                                
                             </span>工作性质</header>
                      <div class="panel-body">
                        <div class="slide-toggle">
   
                              <div><strong> </strong></div>
                              <div class="square-red single-row">
                               <div class="radio ">
                                 <input  type="radio"  name="w_type" >
                                 <label>长期外出不经常回家的</label>
                          
                         
                               </div>
                              </div>
                          <div class="square-red single-row">
                            <div class="radio ">
                              <input  type="radio"  name="w_type" >
                              <label></label>
                              外出务工但是经常回家的</div>
                          </div>
                           
                               
                              <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
 
   <p>&nbsp;</p>
            <p>&nbsp;</p>
 
                              <strong> </strong>
                              <div class="square-red single-row">
                                
                              </div>
                           
                                 
  
                               
                              
                        </div>
                      </div>
          </section>
              </div>             
               
                -->
 <div class="col-md-12" style="display:none">
   <section class="panel">
     <header class="panel-heading"><span class="tools pull-right"> </span>家庭月固定支出情况</header>
     <div class="panel-body">
       <div class="slide-toggle"> <strong> </strong>
         <div class="square-red single-row">
           <div class="radio ">
             <input  name="home_cost"  type="radio" value="0" >
             3000-5000</div>
         </div>
         <div class="square-red single-row">
           <div class="radio ">
             <input  name="home_cost"  type="radio" value="1" >
             5000-8000元</div>
         </div>
         <div class="square-red single-row">
           <div class="radio ">
             <input  name="home_cost"  type="radio" value="2" >
             8000元以上</div>
         </div>
         <div class="square-red single-row"></div>
         <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
         <strong> </strong>
         <p>&nbsp;</p>
         <p>&nbsp;</p>
         <strong> </strong> </div>
     </div>
   </section>
 </div>
 
 
    
    
    
    
    
    
    
    
    
    
    
   <div class=" ">
            <h3>
            </h3>
            <h3>&nbsp;&nbsp;&nbsp;还款能力</h3>
            
         
             
        </div>
        
        
           
    
    
<div class="col-md-12">
                    <section class="panel">
                        <header style="display:none" class="panel-heading"><strong>从业年限</strong></header>
                        <div class="panel-body">
                          <div class="slide-toggle">
                            <div><strong>从业年限：</strong></div>
                            <div class="square-red single-row">
                              <div class="radio ">
                                 <input  name="farm_year"  type="radio" value="0"  ${industry.farmYear== 0 ? 'checked':''}>
                              1年	</div>
                            </div>
                            <div class="square-red single-row">
                              <div class="radio ">
                                  <input  name="farm_year"  type="radio" value="1" ${industry.farmYear== 1 ? 'checked':''} >
                                  1-3年
                              </div>
                            </div>
                            <div class="square-red single-row">
                              <div class="radio ">
                                  <input  name="farm_year"  type="radio" value="2" ${industry.farmYear== 2 ? 'checked':''}>
                                  3年以上 </div>
                            </div>
                            <div class="square-red single-row"></div>
 
                            <div class="square-red single-row"></div>
                            <div class="square-red single-row"></div>
                            <div class="square-red single-row"></div>
                          </div>

                          
                        </div>
                    </section>
              </div>
<div class="col-md-12">
  <section class="panel">
    <header style="display:none" class="panel-heading"><strong>存栏价值</strong> 养殖规模</header>
    <div class="panel-body">
      <div class="slide-toggle">
         
        <div><strong>养殖规模：</strong></div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="farm_goods"  type="radio" value="0" ${industry.farmGoods== 0 ? 'checked':''} >
            0-10万</div>
        </div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="farm_goods"  type="radio" value="1"  ${industry.farmGoods== 1 ? 'checked':''}>
            10-20万</div>
        </div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="farm_goods"  type="radio" value="2"  ${industry.farmGoods== 2 ? 'checked':''}>
            20-30万</div>
        </div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="farm_aera"  type="radio" value="3"  ${industry.farmGoods== 3 ? 'checked':''}>
            30万以上</div>
        </div>
         
        <div class="square-red single-row"></div>
      </div>
    </div>
  </section>
</div>
<div class="col-md-12">
  <section class="panel">
 
    <div class="panel-body">
      <div class="slide-toggle">
       
        <div><strong>养殖场地：</strong></div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="farm_type"  type="radio" value="0"  ${industry.farmType== 0 ? 'checked':''}>
            自建</div>
        </div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="farm_type"  type="radio" value="1"  ${industry.farmType== 1 ? 'checked':''}>
            租用</div>
        </div>
  
        <div><strong>面积：</strong></div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="farm_area"  type="radio" value="0"  ${industry.farmArea== 0 ? 'checked':''}>
            500平方以内 </div>
        </div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="farm_area"  type="radio" value="1" ${industry.farmArea== 1 ? 'checked':''}>
            500-1000平方</div>
        </div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="farm_area"  type="radio" value="2" ${industry.farmArea== 2 ? 'checked':''}>
            1000平方以上</div>
        </div>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <div class="square-red single-row"></div>
      </div>
    </div>
  </section>
</div>
<div class="col-md-12">
  <section class="panel">
     
    <div class="panel-body">
      <div class="slide-toggle">
        <div><strong>经营场地：</strong></div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="op_type"  type="radio" value="0"   ${industry.opType== 1 ? 'checked':''}>
            自建</div>
      </div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="op_type"  type="radio" value="1" ${industry.opType== 2 ? 'checked':''} >
            租用</div>
        </div>
        <div class="square-red single-row"></div>
      </div>
    </div>
  </section>
</div>
<div class="col-md-12">
  <section class="panel">
    <header class="panel-heading" style="display:none"><strong>销售规模</strong></header>
    <div class="panel-body">
      <div class="slide-toggle">
        <div><strong><span class="panel-heading"><strong>销售规模</strong></span>：</strong></div>
        <div class="square-red single-row">
          <div class="radio ">
          <input  name="month_sale"  type="radio" value="0"   ${industry.monthSale== 0 ? 'checked':''}>
          1-20吨</div>
        </div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="month_sale"  type="radio" value="1"   ${industry.monthSale== 1 ? 'checked':''}>
            20-60吨</div>
        </div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="month_sale"  type="radio" value="2"  ${industry.monthSale== 2 ? 'checked':''}>
            60-100吨</div>
        </div>
        <div class="square-red single-row"></div>
        <div class="square-red single-row"></div>
        <div class="square-red single-row"></div>
        <div class="square-red single-row"></div>
      </div>
    </div>
  </section>
</div>
<div class="col-md-12">
  <section class="panel">
    <header class="panel-heading  " style="display:none"><strong><strong>债</strong>民间负</strong> 还款来源</header>
    <div class="panel-body">
      <div class="slide-toggle">
        <div><strong><span class="panel-heading">还款来源</span>：</strong></div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="p_lab"  type="radio" value="0"  ${industry.PLab== 0 ? 'checked':''} >
            仅有养殖还款来源
          </div>
        </div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="p_lab"  type="radio" value="1"  ${industry.PLab== 1? 'checked':''}>
           仅有销售还款来源 </div>
        </div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="p_lab"  type="radio" value="2"  ${industry.PLab== 2 ? 'checked':''}>
           有养殖+销售还款来源</div>
        </div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="p_lab"  type="radio" value="3"  ${industry.PLab== 3 ? 'checked':''}>
           有养殖、销售、其它产业还款来源</div>
        </div>
           <div class="square-red single-row">
          <div class="radio ">
            <input  name="p_lab"  type="radio" value="4"  ${industry.PLab== 4 ? 'checked':''}>
           有养殖、销售、其它产业及其他家庭成员还款来源</div>
        </div>
          
        <div class="square-red single-row"></div>
        <div class="square-red single-row"></div>
        <div class="square-red single-row"></div>
        <div class="square-red single-row"></div>
      </div>
    </div>
  </section>
</div>
<div class="col-md-12">
  <section class="panel">
    <header style="display:none" class="panel-heading"><strong>饲料欠款</strong> 负债系数 </header>
    <div class="panel-body">
      <div class="slide-toggle">
        <div><strong>负债比：</strong></div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="farm_arr"  type="radio" value="0" ${industry.farmArr== 0 ? 'checked':''}>
            20%以下
          </div>
        </div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="farm_arr"  type="radio" value="1" ${industry.farmArr== 1 ? 'checked':''}>
            21%-35%</div>
        </div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="farm_arr"  type="radio" value="2" ${industry.farmArr== 2 ? 'checked':''}>
            36%-45%</div>
        </div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="farm_arr"  type="radio" value="3" ${industry.farmArr== 3 ? 'checked':''}>
            46%-60%</div>
        </div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="farm_arr"  type="radio" value="4" ${industry.farmArr== 4 ? 'checked':''}>
            61%-70%</div>
        </div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="farm_arr"  type="radio" value="5" ${industry.farmArr== 5 ? 'checked':''}>
            71%-80%</div>
        </div>
        <div class="square-red single-row"></div>
        <div class="square-red single-row"></div>
        <div class="square-red single-row"></div>
        <div class="square-red single-row"></div>
      </div>
    </div>
  </section>
</div>
<div class=" ">
  <h3></h3>
  <h3>&nbsp;&nbsp;&nbsp;还款意愿</h3>
   
</div>
<div class="col-md-12">
  <section class="panel">
    <header class="panel-heading"> 本人 <span class="tools pull-right"> </span></header>
    <div class="panel-body">
      <div class="slide-toggle">
        <div><strong>学历：</strong></div>
        <div class="square-red single-row">
          <div class="radio ">
            <label>
              <input  name="self_edu2"  type="radio"     value="0" ${industry.selfEdu2== 0 ? 'checked':''}>
              高中及以下 </label>
          </div>
        </div>
        <div class="square-red single-row">
          <div class="radio ">
            <label>
              <input  name="self_edu2"  type="radio"     value="1" ${industry.selfEdu2== 1 ? 'checked':''}>
              高中及以下 </label>
          </div>
        </div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="self_edu2"  type="radio"     value="2"   ${industry.selfEdu2== 2 ? 'checked':''}>
            <label>大专及以上 </label>
          </div>
        </div>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <div><strong>年龄：</strong></div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  type="radio"  name="self_age2"value="0" ${industry.selfAge2== 0 ? 'checked':''}>
            <label>20-30</label>
          </div>
        </div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  type="radio"  name="self_age2" value="1" ${industry.selfAge2== 1 ? 'checked':''}>
            30-45 </div>
        </div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  type="radio"  name="self_age2" value="2"  ${industry.selfAge2== 2 ? 'checked':''}>
            45
            -55 </div>
        </div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  type="radio"  name="self_age2" value="3" ${industry.selfAge2== 3 ? 'checked':''}>
            <label>55-60 </label>
          </div>
        </div>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <div><strong>婚姻：</strong></div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="self_wed"  type="radio"     value="0"  ${industry.selfWed== 0 ? 'checked':''}>
            已婚有子女 </div>
        </div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="self_wed"  type="radio"     value="1"  ${industry.selfWed== 1 ? 'checked':''}>
            已婚无子女 </div>
        </div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="self_wed"  type="radio"     value="2"  ${industry.selfWed== 2 ? 'checked':''}>
            <label>二婚 </label>
          </div>
        </div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="self_wed"  type="radio"     value="3"  ${industry.selfWed== 3 ? 'checked':''}>
            <label>单身（未婚/离异） </label>
          </div>
        </div>
      </div>
    </div>
  </section>
</div>
<div class="col-md-12">
  <section class="panel">
    <header class="panel-heading"><span class="tools pull-right"> </span>夫妻征信查询次数</header>
    <div class="panel-body">
      <div class="slide-toggle">
        <div><strong>夫妻机构查询：</strong></div>
        <div><strong> </strong></div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="home_gov"  type="radio" value="0"  ${industry.homeGov== 0 ? 'checked':''} >
            <label>10次</label>
          </div>
        </div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="home_gov"  type="radio" value="1"  ${industry.homeGov== 1 ? 'checked':''}>
            <label>10-15次</label>
          </div>
        </div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="home_gov"  type="radio" value="2"  ${industry.homeGov== 2 ? 'checked':''}>
            <label>15-20次</label>
          </div>
        </div>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <div><strong>夫妻本人查询：</strong></div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="home_sel"  type="radio" value="0"  ${industry.homeSel== 0 ? 'checked':''}>
            <label>8次</label>
          </div>
        </div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="home_sel"  type="radio" value="1" ${industry.homeSel== 1 ? 'checked':''}>
            <label>8-12次</label>
          </div>
        </div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="home_sel"  type="radio" value="2" ${industry.homeSel== 2 ? 'checked':''} >
            <label>12-20次</label>
          </div>
        </div>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <div><strong>夫妻征信逾期：</strong></div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="home_isy"  type="radio" value="0"  ${industry.homeIsy== 0 ? 'checked':''}>
            <label>逾期一次</label>
          </div>
        </div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="home_isy"  type="radio" value="1"  ${industry.homeIsy== 1 ? 'checked':''}>
            当前逾期          </div>
        </div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="home_isy"  type="radio" value="2"  ${industry.homeIsy== 2 ? 'checked':''}>
            逾期超过90天及以上</div>
        </div>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p><strong> </strong>
        </p>
        <div><strong>5年内夫妻双方银行贷款总次数：</strong></div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="home_loanCout"  type="radio" value="0"   ${industry.homeLoancout== 0 ? 'checked':''}>
            <label>10次以上</label>
          </div>
        </div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="home_loanCout"  type="radio" value="1"   ${industry.homeLoancout== 1 ? 'checked':''} >
            <label>8-10次</label>
          </div>
        </div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="home_loanCout"  type="radio" value="2"   ${industry.homeLoancout== 2 ? 'checked':''} >
            <label>6-7次</label>
          </div>
        </div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="home_loanCout"  type="radio" value="3"  ${industry.homeLoancout== 3 ? 'checked':''}  >
            <label>4-5次</label>
          </div>
        </div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="home_loanCout"  type="radio" value="4"   ${industry.homeLoancout== 4 ? 'checked':''}>
            <label>1-3次</label>
          </div>
        </div>
        <div class="square-red single-row">
          <div class="radio ">
            <input  name="home_loanCout"  type="radio" value="5"  ${industry.homeLoancout== 5 ? 'checked':''} >
            <label>白户</label>
          </div>
        </div>
      </div>
    </div>
  </section>
</div>
<div  align="center"> 
  <button type="button"   id="comit" class="btn btn-primary"   onClick="calc()">Submit</button>
     
            </div> 
                
          </div>
          
          
          
           
          
          
          
          
          
          
            <div class="row"></div>
            <div class="row"></div>
            <div class="row"></div>

            <div class="row"></div>

            <div class="row"></div>

            <div class="row"></div>

            <div class="row"></div>

            <div class="row"></div>

        </div>
        <!--body wrapper end-->

        <!--footer section start-->
        <footer></footer>
        <!--footer section end-->


  
    
    </form>
    
    
    <!-- main content end-->
</section>

<!-- Placed js at the end of the document so the pages load faster -->
<script src="${contextPath}/industry/js/jquery-1.10.2.min.js"></script>
<script src="${contextPath}/industry/js/jquery-ui-1.9.2.custom.min.js"></script>
<script src="${contextPath}/industry/js/jquery-migrate-1.2.1.min.js"></script>
<script src="${contextPath}/industry/js/bootstrap.min.js"></script>
<script src="${contextPath}/industry/js/modernizr.min.js"></script>
<script src="${contextPath}/industry/js/jquery.nicescroll.js"></script>

<!--ios7-->
<script src="${contextPath}/industry//ios-switch/switchery.js" ></script>
<script src="${contextPath}/industry/js/ios-switch/ios-init.js" ></script>

<!--icheck -->
<script src="${contextPath}/industry/js/iCheck/jquery.icheck.js"></script>
<script src="${contextPath}/industry/js/icheck-init.js"></script>
<!--multi-select-->


<!--spinner-->


<!--file upload-->

<!--tags input-->
<script src="${contextPath}/industry/js/jquery-tags-input/jquery.tagsinput.js"></script>
<script src="${contextPath}/industry/js/tagsinput-init.js"></script>
<!--bootstrap input mask-->
<script type="text/javascript" src="${contextPath}/industry/js/bootstrap-inputmask/bootstrap-inputmask.min.js"></script>


<!--common scripts for all pages-->
<script src="${contextPath}/industry/js/scripts.js"></script>
 
</html>
