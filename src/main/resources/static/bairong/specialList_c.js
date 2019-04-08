                                                      
var sl_id_abnormal                 = specialList_c.sl_id_abnormal;                  //通过身份证号查询高危行为                    
var sl_id_phone_overdue            = specialList_c.sl_id_phone_overdue;             //通过身份证号查询电信欠费                    
var sl_id_court_bad                = specialList_c.sl_id_court_bad;                 //通过身份证号查询法院失信人                  
var sl_id_court_executed           = specialList_c.sl_id_court_executed;            //通过身份证号查询法院被执行人                
var sl_id_bank_bad                 = specialList_c.sl_id_bank_bad;                  //通过身份证号查询银行(含信用卡)中风险        
var sl_id_bank_overdue             = specialList_c.sl_id_bank_overdue;              //通过身份证号查询银行(含信用卡)一般风险      
var sl_id_bank_fraud               = specialList_c.sl_id_bank_fraud;                //通过身份证号查询银行(含信用卡)资信不佳      
var sl_id_bank_lost                = specialList_c.sl_id_bank_lost;                 //通过身份证号查询银行(含信用卡)高风险        
var sl_id_bank_refuse              = specialList_c.sl_id_bank_refuse;               //通过身份证号查询银行(含信用卡)拒绝          
var sl_id_p2p_bad                  = specialList_c.sl_id_p2p_bad;                   //通过身份证号查询非银(含全部非银类型)中风险  
var sl_id_p2p_overdue              = specialList_c.sl_id_p2p_overdue;               //通过身份证号查询非银(含全部非银类型)一般风险
var sl_id_p2p_fraud                = specialList_c.sl_id_p2p_fraud;                 //通过身份证号查询非银(含全部非银类型)资信不佳
var sl_id_p2p_lost                 = specialList_c.sl_id_p2p_lost;                  //通过身份证号查询非银(含全部非银类型)高风险  
var sl_id_p2p_refuse               = specialList_c.sl_id_p2p_refuse;                //通过身份证号查询非银(含全部非银类型)拒绝    
var sl_id_nbank_p2p_bad            = specialList_c.sl_id_nbank_p2p_bad;             //通过身份证号查询非银-P2P中风险              
var sl_id_nbank_p2p_overdue        = specialList_c.sl_id_nbank_p2p_overdue;         //通过身份证号查询非银-P2P一般风险            
var sl_id_nbank_p2p_fraud          = specialList_c.sl_id_nbank_p2p_fraud;           //通过身份证号查询非银-P2P资信不佳            
var sl_id_nbank_p2p_lost           = specialList_c.sl_id_nbank_p2p_lost;            //通过身份证号查询非银-P2P高风险              
var sl_id_nbank_p2p_refuse         = specialList_c.sl_id_nbank_p2p_refuse;          //通过身份证号查询非银-P2P拒绝                
var sl_id_nbank_mc_bad             = specialList_c.sl_id_nbank_mc_bad;              //通过身份证号查询非银-小贷中风险             
var sl_id_nbank_mc_overdue         = specialList_c.sl_id_nbank_mc_overdue;          //通过身份证号查询非银-小贷一般风险           
var sl_id_nbank_mc_fraud           = specialList_c.sl_id_nbank_mc_fraud;            //通过身份证号查询非银-小贷资信不佳           
var sl_id_nbank_mc_lost            = specialList_c.sl_id_nbank_mc_lost;             //通过身份证号查询非银-小贷高风险             
var sl_id_nbank_mc_refuse          = specialList_c.sl_id_nbank_mc_refuse;           //通过身份证号查询非银-小贷拒绝               
var sl_id_nbank_ca_bad             = specialList_c.sl_id_nbank_ca_bad;              //通过身份证号查询非银-现金类分期中风险       
var sl_id_nbank_ca_overdue         = specialList_c.sl_id_nbank_ca_overdue;          //通过身份证号查询非银-现金类分期一般风险     
var sl_id_nbank_ca_fraud           = specialList_c.sl_id_nbank_ca_fraud;            //通过身份证号查询非银-现金类分期资信不佳     
var sl_id_nbank_ca_lost            = specialList_c.sl_id_nbank_ca_lost;             //通过身份证号查询非银-现金类分期高风险       
var sl_id_nbank_ca_refuse          = specialList_c.sl_id_nbank_ca_refuse;           //通过身份证号查询非银-现金类分期拒绝         
var sl_id_nbank_com_bad            = specialList_c.sl_id_nbank_com_bad;             //通过身份证号查询非银-代偿类分期中风险       
var sl_id_nbank_com_overdue        = specialList_c.sl_id_nbank_com_overdue;         //通过身份证号查询非银-代偿类分期一般风险     
var sl_id_nbank_com_fraud          = specialList_c.sl_id_nbank_com_fraud;           //通过身份证号查询非银-代偿类分期资信不佳     
var sl_id_nbank_com_lost           = specialList_c.sl_id_nbank_com_lost;            //通过身份证号查询非银-代偿类分期高风险       
var sl_id_nbank_com_refuse         = specialList_c.sl_id_nbank_com_refuse;          //通过身份证号查询非银-代偿类分期拒绝         
var sl_id_nbank_cf_bad             = specialList_c.sl_id_nbank_cf_bad;              //通过身份证号查询非银-消费类分期中风险       
var sl_id_nbank_cf_overdue         = specialList_c.sl_id_nbank_cf_overdue;          //通过身份证号查询非银-消费类分期一般风险     
var sl_id_nbank_cf_fraud           = specialList_c.sl_id_nbank_cf_fraud;            //通过身份证号查询非银-消费类分期资信不佳     
var sl_id_nbank_cf_lost            = specialList_c.sl_id_nbank_cf_lost;             //通过身份证号查询非银-消费类分期高风险       
var sl_id_nbank_cf_refuse          = specialList_c.sl_id_nbank_cf_refuse;           //通过身份证号查询非银-消费类分期拒绝         
var sl_id_nbank_other_bad          = specialList_c.sl_id_nbank_other_bad;           //通过身份证号查询非银-其他中风险             
var sl_id_nbank_other_overdue      = specialList_c.sl_id_nbank_other_overdue;       //通过身份证号查询非银-其他一般风险           
var sl_id_nbank_other_fraud        = specialList_c.sl_id_nbank_other_fraud;         //通过身份证号查询非银-其他资信不佳           
var sl_id_nbank_other_lost         = specialList_c.sl_id_nbank_other_lost;          //通过身份证号查询非银-其他高风险             
var sl_id_nbank_other_refuse       = specialList_c.sl_id_nbank_other_refuse;        //通过身份证号查询非银-其他拒绝               
var sl_cell_abnormal               = specialList_c.sl_cell_abnormal;                //通过手机号查询高危行为                      
var sl_cell_phone_overdue          = specialList_c.sl_cell_phone_overdue;           //通过手机号查询电信欠费                      
var sl_cell_bank_bad               = specialList_c.sl_cell_bank_bad;                //通过手机号查询银行(含信用卡)中风险          
var sl_cell_bank_overdue           = specialList_c.sl_cell_bank_overdue;            //通过手机号查询银行(含信用卡)一般风险        
var sl_cell_bank_fraud             = specialList_c.sl_cell_bank_fraud;              //通过手机号查询银行(含信用卡)资信不佳        
var sl_cell_bank_lost              = specialList_c.sl_cell_bank_lost;               //通过手机号查询银行(含信用卡)高风险          
var sl_cell_bank_refuse            = specialList_c.sl_cell_bank_refuse;             //通过手机号查询银行(含信用卡)拒绝            
var sl_cell_p2p_bad                = specialList_c.sl_cell_p2p_bad;                 //通过手机号查询非银(含全部非银类型)中风险    
var sl_cell_p2p_overdue            = specialList_c.sl_cell_p2p_overdue;             //通过手机号查询非银(含全部非银类型)一般风险  
var sl_cell_p2p_fraud              = specialList_c.sl_cell_p2p_fraud;               //通过手机号查询非银(含全部非银类型)资信不佳  
var sl_cell_p2p_lost               = specialList_c.sl_cell_p2p_lost;                //通过手机号查询非银(含全部非银类型)高风险    
var sl_cell_p2p_refuse             = specialList_c.sl_cell_p2p_refuse;              //通过手机号查询非银(含全部非银类型)拒绝      
var sl_cell_nbank_p2p_bad          = specialList_c.sl_cell_nbank_p2p_bad;           //通过手机号查询非银-P2P中风险                
var sl_cell_nbank_p2p_overdue      = specialList_c.sl_cell_nbank_p2p_overdue;       //通过手机号查询非银-P2P一般风险              
var sl_cell_nbank_p2p_fraud        = specialList_c.sl_cell_nbank_p2p_fraud;         //通过手机号查询非银-P2P资信不佳              
var sl_cell_nbank_p2p_lost         = specialList_c.sl_cell_nbank_p2p_lost;          //通过手机号查询非银-P2P高风险                
var sl_cell_nbank_p2p_refuse       = specialList_c.sl_cell_nbank_p2p_refuse;        //通过手机号查询非银-P2P拒绝                  
var sl_cell_nbank_mc_bad           = specialList_c.sl_cell_nbank_mc_bad;            //通过手机号查询非银-小贷中风险               
var sl_cell_nbank_mc_overdue       = specialList_c.sl_cell_nbank_mc_overdue;        //通过手机号查询非银-小贷一般风险             
var sl_cell_nbank_mc_fraud         = specialList_c.sl_cell_nbank_mc_fraud;          //通过手机号查询非银-小贷资信不佳             
var sl_cell_nbank_mc_lost          = specialList_c.sl_cell_nbank_mc_lost;           //通过手机号查询非银-小贷高风险               
var sl_cell_nbank_mc_refuse        = specialList_c.sl_cell_nbank_mc_refuse;         //通过手机号查询非银-小贷拒绝                 
var sl_cell_nbank_ca_bad           = specialList_c.sl_cell_nbank_ca_bad;            //通过手机号查询非银-现金类分期中风险         
var sl_cell_nbank_ca_overdue       = specialList_c.sl_cell_nbank_ca_overdue;        //通过手机号查询非银-现金类分期一般风险       
var sl_cell_nbank_ca_fraud         = specialList_c.sl_cell_nbank_ca_fraud;          //通过手机号查询非银-现金类分期资信不佳       
var sl_cell_nbank_ca_lost          = specialList_c.sl_cell_nbank_ca_lost;           //通过手机号查询非银-现金类分期高风险         
var sl_cell_nbank_ca_refuse        = specialList_c.sl_cell_nbank_ca_refuse;         //通过手机号查询非银-现金类分期拒绝           
var sl_cell_nbank_com_bad          = specialList_c.sl_cell_nbank_com_bad;           //通过手机号查询非银-代偿类分期中风险         
var sl_cell_nbank_com_overdue      = specialList_c.sl_cell_nbank_com_overdue;       //通过手机号查询非银-代偿类分期一般风险       
var sl_cell_nbank_com_fraud        = specialList_c.sl_cell_nbank_com_fraud;         //通过手机号查询非银-代偿类分期资信不佳       
var sl_cell_nbank_com_lost         = specialList_c.sl_cell_nbank_com_lost;          //通过手机号查询非银-代偿类分期高风险         
var sl_cell_nbank_com_refuse       = specialList_c.sl_cell_nbank_com_refuse;        //通过手机号查询非银-代偿类分期拒绝           
var sl_cell_nbank_cf_bad           = specialList_c.sl_cell_nbank_cf_bad;            //通过手机号查询非银-消费类分期中风险         
var sl_cell_nbank_cf_overdue       = specialList_c.sl_cell_nbank_cf_overdue;        //通过手机号查询非银-消费类分期一般风险       
var sl_cell_nbank_cf_fraud         = specialList_c.sl_cell_nbank_cf_fraud;          //通过手机号查询非银-消费类分期资信不佳       
var sl_cell_nbank_cf_lost          = specialList_c.sl_cell_nbank_cf_lost;           //通过手机号查询非银-消费类分期高风险         
var sl_cell_nbank_cf_refuse        = specialList_c.sl_cell_nbank_cf_refuse;         //通过手机号查询非银-消费类分期拒绝           
var sl_cell_nbank_other_bad        = specialList_c.sl_cell_nbank_other_bad;         //通过手机号查询非银-其他中风险               
var sl_cell_nbank_other_overdue    = specialList_c.sl_cell_nbank_other_overdue;     //通过手机号查询非银-其他一般风险             
var sl_cell_nbank_other_fraud      = specialList_c.sl_cell_nbank_other_fraud;       //通过手机号查询非银-其他资信不佳             
var sl_cell_nbank_other_lost       = specialList_c.sl_cell_nbank_other_lost;        //通过手机号查询非银-其他高风险               
var sl_cell_nbank_other_refuse     = specialList_c.sl_cell_nbank_other_refuse;      //通过手机号查询非银-其他拒绝                 
var sl_lm_cell_abnormal            = specialList_c.sl_lm_cell_abnormal;             //通过联系人手机查询高危行为                  
var sl_lm_cell_phone_overdue       = specialList_c.sl_lm_cell_phone_overdue;        //通过联系人手机查询电信欠费                  
var sl_lm_cell_bank_bad            = specialList_c.sl_lm_cell_bank_bad;             //通过联系人手机查询银行(含信用卡)中风险      
var sl_lm_cell_bank_overdue        = specialList_c.sl_lm_cell_bank_overdue;         //通过联系人手机查询银行(含信用卡)一般风险    
var sl_lm_cell_bank_fraud          = specialList_c.sl_lm_cell_bank_fraud;           //通过联系人手机查询银行(含信用卡)资信不佳    
var sl_lm_cell_bank_lost           = specialList_c.sl_lm_cell_bank_lost;            //通过联系人手机查询银行(含信用卡)高风险      
var sl_lm_cell_bank_refuse         = specialList_c.sl_lm_cell_bank_refuse;          //通过联系人手机查询银行(含信用卡)拒绝        
var sl_lm_cell_nbank_p2p_bad       = specialList_c.sl_lm_cell_nbank_p2p_bad;        //通过联系人手机查询非银-P2P中风险            
var sl_lm_cell_nbank_p2p_overdue   = specialList_c.sl_lm_cell_nbank_p2p_overdue;    //通过联系人手机查询非银-P2P一般风险          
var sl_lm_cell_nbank_p2p_fraud     = specialList_c.sl_lm_cell_nbank_p2p_fraud;      //通过联系人手机查询非银-P2P资信不佳          
var sl_lm_cell_nbank_p2p_lost      = specialList_c.sl_lm_cell_nbank_p2p_lost;       //通过联系人手机查询非银-P2P高风险            
var sl_lm_cell_nbank_p2p_refuse    = specialList_c.sl_lm_cell_nbank_p2p_refuse;     //通过联系人手机查询非银-P2P拒绝              
var sl_lm_cell_nbank_mc_bad        = specialList_c.sl_lm_cell_nbank_mc_bad;         //通过联系人手机查询非银-小贷中风险           
var sl_lm_cell_nbank_mc_overdue    = specialList_c.sl_lm_cell_nbank_mc_overdue;     //通过联系人手机查询非银-小贷一般风险         
var sl_lm_cell_nbank_mc_fraud      = specialList_c.sl_lm_cell_nbank_mc_fraud;       //通过联系人手机查询非银-小贷资信不佳         
var sl_lm_cell_nbank_mc_lost       = specialList_c.sl_lm_cell_nbank_mc_lost;        //通过联系人手机查询非银-小贷高风险           
var sl_lm_cell_nbank_mc_refuse     = specialList_c.sl_lm_cell_nbank_mc_refuse;      //通过联系人手机查询非银-小贷拒绝             
var sl_lm_cell_nbank_ca_bad        = specialList_c.sl_lm_cell_nbank_ca_bad;         //通过联系人手机查询非银-现金类分期中风险     
var sl_lm_cell_nbank_ca_overdue    = specialList_c.sl_lm_cell_nbank_ca_overdue;     //通过联系人手机查询非银-现金类分期一般风险   
var sl_lm_cell_nbank_ca_fraud      = specialList_c.sl_lm_cell_nbank_ca_fraud;       //通过联系人手机查询非银-现金类分期资信不佳   
var sl_lm_cell_nbank_ca_lost       = specialList_c.sl_lm_cell_nbank_ca_lost;        //通过联系人手机查询非银-现金类分期高风险     
var sl_lm_cell_nbank_ca_refuse     = specialList_c.sl_lm_cell_nbank_ca_refuse;      //通过联系人手机查询非银-现金类分期拒绝       
var sl_lm_cell_nbank_com_bad       = specialList_c.sl_lm_cell_nbank_com_bad;        //通过联系人手机查询非银-代偿类分期中风险     
var sl_lm_cell_nbank_com_overdue   = specialList_c.sl_lm_cell_nbank_com_overdue;    //通过联系人手机查询非银-代偿类分期一般风险   
var sl_lm_cell_nbank_com_fraud     = specialList_c.sl_lm_cell_nbank_com_fraud;      //通过联系人手机查询非银-代偿类分期资信不佳   
var sl_lm_cell_nbank_com_lost      = specialList_c.sl_lm_cell_nbank_com_lost;       //通过联系人手机查询非银-代偿类分期高风险     
var sl_lm_cell_nbank_com_refuse    = specialList_c.sl_lm_cell_nbank_com_refuse;     //通过联系人手机查询非银-代偿类分期拒绝       
var sl_lm_cell_nbank_cf_bad        = specialList_c.sl_lm_cell_nbank_cf_bad;         //通过联系人手机查询非银-消费类分期中风险     
var sl_lm_cell_nbank_cf_overdue    = specialList_c.sl_lm_cell_nbank_cf_overdue;     //通过联系人手机查询非银-消费类分期一般风险   
var sl_lm_cell_nbank_cf_fraud      = specialList_c.sl_lm_cell_nbank_cf_fraud;       //通过联系人手机查询非银-消费类分期资信不佳   
var sl_lm_cell_nbank_cf_lost       = specialList_c.sl_lm_cell_nbank_cf_lost;        //通过联系人手机查询非银-消费类分期高风险     
var sl_lm_cell_nbank_cf_refuse     = specialList_c.sl_lm_cell_nbank_cf_refuse;      //通过联系人手机查询非银-消费类分期拒绝       
var sl_lm_cell_nbank_other_bad     = specialList_c.sl_lm_cell_nbank_other_bad;      //通过联系人手机查询非银-其他中风险           
var sl_lm_cell_nbank_other_overdue = specialList_c.sl_lm_cell_nbank_other_overdue;  //通过联系人手机查询非银-其他一般风险         
var sl_lm_cell_nbank_other_fraud   = specialList_c.sl_lm_cell_nbank_other_fraud;    //通过联系人手机查询非银-其他资信不佳         
var sl_lm_cell_nbank_other_lost    = specialList_c.sl_lm_cell_nbank_other_lost;     //通过联系人手机查询非银-其他高风险           
var sl_lm_cell_nbank_other_refuse  = specialList_c.sl_lm_cell_nbank_other_refuse;   //通过联系人手机查询非银-其他拒绝             


if(sl_id_abnormal
	||sl_id_phone_overdue
	||sl_id_court_bad
	||sl_id_court_executed
	||sl_id_bank_bad
	||sl_id_bank_overdue
	||sl_id_bank_fraud
	||sl_id_bank_lost
	||sl_id_bank_refuse
	||sl_id_p2p_bad
	||sl_id_p2p_overdue
	||sl_id_p2p_fraud
	||sl_id_p2p_lost
	||sl_id_p2p_refuse
	||sl_id_nbank_p2p_bad
	||sl_id_nbank_p2p_overdue
	||sl_id_nbank_p2p_fraud
	||sl_id_nbank_p2p_lost
	||sl_id_nbank_p2p_refuse
	||sl_id_nbank_mc_bad
	||sl_id_nbank_mc_overdue
	||sl_id_nbank_mc_fraud
	||sl_id_nbank_mc_lost
	||sl_id_nbank_mc_refuse
	||sl_id_nbank_ca_bad
	||sl_id_nbank_ca_overdue
	||sl_id_nbank_ca_fraud
	||sl_id_nbank_ca_lost
	||sl_id_nbank_ca_refuse
	||sl_id_nbank_com_bad
	||sl_id_nbank_com_overdue
	||sl_id_nbank_com_fraud
	||sl_id_nbank_com_lost
	||sl_id_nbank_com_refuse
	||sl_id_nbank_cf_bad
	||sl_id_nbank_cf_overdue
	||sl_id_nbank_cf_fraud
	||sl_id_nbank_cf_lost
	||sl_id_nbank_cf_refuse
	||sl_id_nbank_other_bad
	||sl_id_nbank_other_overdue
	||sl_id_nbank_other_fraud
	||sl_id_nbank_other_lost
	||sl_id_nbank_other_refuse
	||sl_cell_abnormal
	||sl_cell_phone_overdue
	||sl_cell_bank_bad
	||sl_cell_bank_overdue
	||sl_cell_bank_fraud
	||sl_cell_bank_lost
	||sl_cell_bank_refuse
	||sl_cell_p2p_bad
	||sl_cell_p2p_overdue
	||sl_cell_p2p_fraud
	||sl_cell_p2p_lost
	||sl_cell_p2p_refuse
	||sl_cell_nbank_p2p_bad
	||sl_cell_nbank_p2p_overdue
	||sl_cell_nbank_p2p_fraud
	||sl_cell_nbank_p2p_lost
	||sl_cell_nbank_p2p_refuse
	||sl_cell_nbank_mc_bad
	||sl_cell_nbank_mc_overdue
	||sl_cell_nbank_mc_fraud
	||sl_cell_nbank_mc_lost
	||sl_cell_nbank_mc_refuse
	||sl_cell_nbank_ca_bad
	||sl_cell_nbank_ca_overdue
	||sl_cell_nbank_ca_fraud
	||sl_cell_nbank_ca_lost
	||sl_cell_nbank_ca_refuse
	||sl_cell_nbank_com_bad
	||sl_cell_nbank_com_overdue
	||sl_cell_nbank_com_fraud
	||sl_cell_nbank_com_lost
	||sl_cell_nbank_com_refuse
	||sl_cell_nbank_cf_bad
	||sl_cell_nbank_cf_overdue
	||sl_cell_nbank_cf_fraud
	||sl_cell_nbank_cf_lost
	||sl_cell_nbank_cf_refuse
	||sl_cell_nbank_other_bad
	||sl_cell_nbank_other_overdue
	||sl_cell_nbank_other_fraud
	||sl_cell_nbank_other_lost
	||sl_cell_nbank_other_refuse
	||sl_lm_cell_abnormal
	||sl_lm_cell_phone_overdue
	||sl_lm_cell_bank_bad
	||sl_lm_cell_bank_overdue
	||sl_lm_cell_bank_fraud
	||sl_lm_cell_bank_lost
	||sl_lm_cell_bank_refuse
	||sl_lm_cell_nbank_p2p_bad
	||sl_lm_cell_nbank_p2p_overdue
	||sl_lm_cell_nbank_p2p_fraud
	||sl_lm_cell_nbank_p2p_lost
	||sl_lm_cell_nbank_p2p_refuse
	||sl_lm_cell_nbank_mc_bad
	||sl_lm_cell_nbank_mc_overdue
	||sl_lm_cell_nbank_mc_fraud
	||sl_lm_cell_nbank_mc_lost
	||sl_lm_cell_nbank_mc_refuse
	||sl_lm_cell_nbank_ca_bad
	||sl_lm_cell_nbank_ca_overdue
	||sl_lm_cell_nbank_ca_fraud
	||sl_lm_cell_nbank_ca_lost
	||sl_lm_cell_nbank_ca_refuse
	||sl_lm_cell_nbank_com_bad
	||sl_lm_cell_nbank_com_overdue
	||sl_lm_cell_nbank_com_fraud
	||sl_lm_cell_nbank_com_lost
	||sl_lm_cell_nbank_com_refuse
	||sl_lm_cell_nbank_cf_bad
	||sl_lm_cell_nbank_cf_overdue
	||sl_lm_cell_nbank_cf_fraud
	||sl_lm_cell_nbank_cf_lost
	||sl_lm_cell_nbank_cf_refuse
	||sl_lm_cell_nbank_other_bad
	||sl_lm_cell_nbank_other_overdue
	||sl_lm_cell_nbank_other_fraud
	||sl_lm_cell_nbank_other_lost
	||sl_lm_cell_nbank_other_refuse
	){
	if(sl_id_abnormal                ){
		sl_id_abnormal = sl_id_abnormal                =='0'?'本人直接命中' :sl_id_abnormal                 =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询高危行为：<span style=\"color: #ff3333;\">"+sl_id_abnormal+"</span></label>");
	};

	if(sl_id_phone_overdue           ){
		sl_id_phone_overdue = sl_id_phone_overdue           =='0'?'本人直接命中' :sl_id_phone_overdue            =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询电信欠费：<span style=\"color: #ff3333;\">"+sl_id_phone_overdue+"</span></label>");
	};

	if(sl_id_court_bad               ){
		sl_id_court_bad = sl_id_court_bad               =='0'?'本人直接命中' :sl_id_court_bad                =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询法院失信人：<span style=\"color: #ff3333;\">"+sl_id_court_bad+"</span></label>");
	}; 

	if(sl_id_court_executed          ){
		sl_id_court_executed = sl_id_court_executed          =='0'?'本人直接命中' :sl_id_court_executed           =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询法院被执行人：<span style=\"color: #ff3333;\">"+sl_id_court_executed+"</span></label>");
	}; 

	if(sl_id_bank_bad                ){
		sl_id_bank_bad = sl_id_bank_bad                =='0'?'本人直接命中' :sl_id_bank_bad                 =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询银行(含信用卡)中风险：<span style=\"color: #ff3333;\">"+sl_id_bank_bad+"</span></label>");
	};  

	if(sl_id_bank_overdue            ){
		sl_id_bank_overdue = sl_id_bank_overdue            =='0'?'本人直接命中' :sl_id_bank_overdue             =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询银行(含信用卡)一般风险：<span style=\"color: #ff3333;\">"+sl_id_bank_overdue+"</span></label>");
	}; 

	if(sl_id_bank_fraud              ){
		sl_id_bank_fraud = sl_id_bank_fraud              =='0'?'本人直接命中' :sl_id_bank_fraud               =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询银行(含信用卡)资信不佳：<span style=\"color: #ff3333;\">"+sl_id_bank_fraud+"</span></label>");
	}; 

	if(sl_id_bank_lost               ){
		sl_id_bank_lost = sl_id_bank_lost               =='0'?'本人直接命中' :sl_id_bank_lost                =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询银行(含信用卡)高风险：<span style=\"color: #ff3333;\">"+sl_id_bank_lost+"</span></label>");
	};

	if(sl_id_bank_refuse             ){
		sl_id_bank_refuse = sl_id_bank_refuse             =='0'?'本人直接命中' :sl_id_bank_refuse              =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询银行(含信用卡)拒绝：<span style=\"color: #ff3333;\">"+sl_id_bank_refuse+"</span></label>");
	}; 

	if(sl_id_p2p_bad                 ){
		sl_id_p2p_bad = sl_id_p2p_bad                 =='0'?'本人直接命中' :sl_id_p2p_bad                  =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询非银(含全部非银类型)中风险：<span style=\"color: #ff3333;\">"+sl_id_p2p_bad+"</span></label>");
	}; 

	if(sl_id_p2p_overdue             ){
		sl_id_p2p_overdue = sl_id_p2p_overdue             =='0'?'本人直接命中' :sl_id_p2p_overdue              =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询非银(含全部非银类型)一般风险：<span style=\"color: #ff3333;\">"+sl_id_p2p_overdue+"</span></label>");
	}; 

	if(sl_id_p2p_fraud               ){
		sl_id_p2p_fraud = sl_id_p2p_fraud               =='0'?'本人直接命中' :sl_id_p2p_fraud                =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询非银(含全部非银类型)资信不佳：<span style=\"color: #ff3333;\">"+sl_id_p2p_fraud+"</span></label>");
	}; 

	if(sl_id_p2p_lost                ){
		sl_id_p2p_lost = sl_id_p2p_lost                =='0'?'本人直接命中' :sl_id_p2p_lost                 =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询非银(含全部非银类型)高风险：<span style=\"color: #ff3333;\">"+sl_id_p2p_lost+"</span></label>");
	}; 

	if(sl_id_p2p_refuse              ){
		sl_id_p2p_refuse = sl_id_p2p_refuse              =='0'?'本人直接命中' :sl_id_p2p_refuse               =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询非银(含全部非银类型)拒绝：<span style=\"color: #ff3333;\">"+sl_id_p2p_refuse+"</span></label>");
	};  

	if(sl_id_nbank_p2p_bad           ){
		sl_id_nbank_p2p_bad = sl_id_nbank_p2p_bad           =='0'?'本人直接命中' :sl_id_nbank_p2p_bad            =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询非银-P2P中风险：<span style=\"color: #ff3333;\">"+sl_id_nbank_p2p_bad+"</span></label>");
	}; 

	if(sl_id_nbank_p2p_overdue       ){
		sl_id_nbank_p2p_overdue = sl_id_nbank_p2p_overdue       =='0'?'本人直接命中' :sl_id_nbank_p2p_overdue        =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询非银-P2P一般风险：<span style=\"color: #ff3333;\">"+sl_id_nbank_p2p_overdue+"</span></label>");
	}; 

	if(sl_id_nbank_p2p_fraud         ){
		sl_id_nbank_p2p_fraud = sl_id_nbank_p2p_fraud         =='0'?'本人直接命中' :sl_id_nbank_p2p_fraud          =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询非银-P2P资信不佳：<span style=\"color: #ff3333;\">"+sl_id_nbank_p2p_fraud+"</span></label>");
	};  

	if(sl_id_nbank_p2p_lost          ){
		sl_id_nbank_p2p_lost = sl_id_nbank_p2p_lost          =='0'?'本人直接命中' :sl_id_nbank_p2p_lost           =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询非银-P2P高风险：<span style=\"color: #ff3333;\">"+sl_id_nbank_p2p_lost+"</span></label>");
	}; 

	if(sl_id_nbank_p2p_refuse        ){
		sl_id_nbank_p2p_refuse = sl_id_nbank_p2p_refuse        =='0'?'本人直接命中' :sl_id_nbank_p2p_refuse         =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询非银-P2P拒绝：<span style=\"color: #ff3333;\">"+sl_id_nbank_p2p_refuse+"</span></label>");
	}; 

	if(sl_id_nbank_mc_bad            ){
		sl_id_nbank_mc_bad = sl_id_nbank_mc_bad            =='0'?'本人直接命中' :sl_id_nbank_mc_bad             =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询非银-小贷中风险：<span style=\"color: #ff3333;\">"+sl_id_nbank_mc_bad+"</span></label>");
	}; 

	if(sl_id_nbank_mc_overdue        ){
		sl_id_nbank_mc_overdue = sl_id_nbank_mc_overdue        =='0'?'本人直接命中' :sl_id_nbank_mc_overdue         =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询非银-小贷一般风险：<span style=\"color: #ff3333;\">"+sl_id_nbank_mc_overdue+"</span></label>");
	}; 

	if(sl_id_nbank_mc_fraud          ){
		sl_id_nbank_mc_fraud = sl_id_nbank_mc_fraud          =='0'?'本人直接命中' :sl_id_nbank_mc_fraud           =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询非银-小贷资信不佳：<span style=\"color: #ff3333;\">"+sl_id_nbank_mc_fraud+"</span></label>");
	}; 

	if(sl_id_nbank_mc_lost           ){
		sl_id_nbank_mc_lost = sl_id_nbank_mc_lost           =='0'?'本人直接命中' :sl_id_nbank_mc_lost            =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询非银-小贷高风险：<span style=\"color: #ff3333;\">"+sl_id_nbank_mc_lost+"</span></label>");
	}; 

	if(sl_id_nbank_mc_refuse         ){
		sl_id_nbank_mc_refuse = sl_id_nbank_mc_refuse         =='0'?'本人直接命中' :sl_id_nbank_mc_refuse          =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询非银-小贷拒绝：<span style=\"color: #ff3333;\">"+sl_id_nbank_mc_refuse+"</span></label>");
	};  

	if(sl_id_nbank_ca_bad            ){
		sl_id_nbank_ca_bad = sl_id_nbank_ca_bad            =='0'?'本人直接命中' :sl_id_nbank_ca_bad             =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询非银-现金类分期中风险：<span style=\"color: #ff3333;\">"+sl_id_nbank_ca_bad+"</span></label>");
	};  

	if(sl_id_nbank_ca_overdue        ){
		sl_id_nbank_ca_overdue = sl_id_nbank_ca_overdue        =='0'?'本人直接命中' :sl_id_nbank_ca_overdue         =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询非银-现金类分期一般风险：<span style=\"color: #ff3333;\">"+sl_id_nbank_ca_overdue+"</span></label>");
	};  

	if(sl_id_nbank_ca_fraud          ){
		sl_id_nbank_ca_fraud = sl_id_nbank_ca_fraud          =='0'?'本人直接命中' :sl_id_nbank_ca_fraud           =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询非银-现金类分期资信不佳：<span style=\"color: #ff3333;\">"+sl_id_nbank_ca_fraud+"</span></label>");
	};  

	if(sl_id_nbank_ca_lost           ){
		sl_id_nbank_ca_lost = sl_id_nbank_ca_lost           =='0'?'本人直接命中' :sl_id_nbank_ca_lost            =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询非银-现金类分期高风险：<span style=\"color: #ff3333;\">"+sl_id_nbank_ca_lost+"</span></label>");
	};  

	if(sl_id_nbank_ca_refuse         ){
		sl_id_nbank_ca_refuse = sl_id_nbank_ca_refuse         =='0'?'本人直接命中' :sl_id_nbank_ca_refuse          =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询非银-现金类分期拒绝：<span style=\"color: #ff3333;\">"+sl_id_nbank_ca_refuse+"</span></label>");
	};   

	if(sl_id_nbank_com_bad           ){
		sl_id_nbank_com_bad = sl_id_nbank_com_bad           =='0'?'本人直接命中' :sl_id_nbank_com_bad            =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询非银-代偿类分期中风险：<span style=\"color: #ff3333;\">"+sl_id_nbank_com_bad+"</span></label>");
	};  

	if(sl_id_nbank_com_overdue       ){
		sl_id_nbank_com_overdue = sl_id_nbank_com_overdue       =='0'?'本人直接命中' :sl_id_nbank_com_overdue        =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询非银-代偿类分期一般风险：<span style=\"color: #ff3333;\">"+sl_id_nbank_com_overdue+"</span></label>");
	};   

	if(sl_id_nbank_com_fraud         ){
		sl_id_nbank_com_fraud = sl_id_nbank_com_fraud         =='0'?'本人直接命中' :sl_id_nbank_com_fraud          =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询非银-代偿类分期资信不佳：<span style=\"color: #ff3333;\">"+sl_id_nbank_com_fraud+"</span></label>");
	};   

	if(sl_id_nbank_com_lost          ){
		sl_id_nbank_com_lost = sl_id_nbank_com_lost          =='0'?'本人直接命中' :sl_id_nbank_com_lost           =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询非银-代偿类分期高风险：<span style=\"color: #ff3333;\">"+sl_id_nbank_com_lost+"</span></label>");
	};   

	if(sl_id_nbank_com_refuse        ){
		sl_id_nbank_com_refuse = sl_id_nbank_com_refuse        =='0'?'本人直接命中' :sl_id_nbank_com_refuse         =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询非银-代偿类分期拒绝：<span style=\"color: #ff3333;\">"+sl_id_nbank_com_refuse+"</span></label>");
	};   

	if(sl_id_nbank_cf_bad            ){
		sl_id_nbank_cf_bad = sl_id_nbank_cf_bad            =='0'?'本人直接命中' :sl_id_nbank_cf_bad             =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询非银-消费类分期中风险：<span style=\"color: #ff3333;\">"+sl_id_nbank_cf_bad+"</span></label>");
	};   

	if(sl_id_nbank_cf_overdue        ){
		sl_id_nbank_cf_overdue = sl_id_nbank_cf_overdue        =='0'?'本人直接命中' :sl_id_nbank_cf_overdue         =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询非银-消费类分期一般风险：<span style=\"color: #ff3333;\">"+sl_id_nbank_cf_overdue+"</span></label>");
	};  

	if(sl_id_nbank_cf_fraud          ){
		sl_id_nbank_cf_fraud = sl_id_nbank_cf_fraud          =='0'?'本人直接命中' :sl_id_nbank_cf_fraud           =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询非银-消费类分期资信不佳：<span style=\"color: #ff3333;\">"+sl_id_nbank_cf_fraud+"</span></label>");
	};   

	if(sl_id_nbank_cf_lost           ){
		sl_id_nbank_cf_lost = sl_id_nbank_cf_lost           =='0'?'本人直接命中' :sl_id_nbank_cf_lost            =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询非银-消费类分期高风险：<span style=\"color: #ff3333;\">"+sl_id_nbank_cf_lost+"</span></label>");
	};   

	if(sl_id_nbank_cf_refuse         ){
		sl_id_nbank_cf_refuse = sl_id_nbank_cf_refuse         =='0'?'本人直接命中' :sl_id_nbank_cf_refuse          =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询非银-消费类分期拒绝：<span style=\"color: #ff3333;\">"+sl_id_nbank_cf_refuse+"</span></label>");
	}; 

	if(sl_id_nbank_other_bad         ){
		sl_id_nbank_other_bad = sl_id_nbank_other_bad         =='0'?'本人直接命中' :sl_id_nbank_other_bad          =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" > 通过身份证号查询非银-其他中风险：<span style=\"color: #ff3333;\">"+sl_id_nbank_other_bad+"</span></label>");
	};  

	if(sl_id_nbank_other_overdue     ){
		sl_id_nbank_other_overdue = sl_id_nbank_other_overdue     =='0'?'本人直接命中' :sl_id_nbank_other_overdue      =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询非银-其他一般风险：<span style=\"color: #ff3333;\">"+sl_id_nbank_other_overdue+"</span></label>");
	};    

	if(sl_id_nbank_other_fraud       ){
		sl_id_nbank_other_fraud = sl_id_nbank_other_fraud       =='0'?'本人直接命中' :sl_id_nbank_other_fraud        =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询非银-其他资信不佳：<span style=\"color: #ff3333;\">"+sl_id_nbank_other_fraud+"</span></label>");
	};     

	if(sl_id_nbank_other_lost        ){
		sl_id_nbank_other_lost = sl_id_nbank_other_lost        =='0'?'本人直接命中' :sl_id_nbank_other_lost         =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询非银-其他高风险：<span style=\"color: #ff3333;\">"+sl_id_nbank_other_lost+"</span></label>");
	};     

	if(sl_id_nbank_other_refuse      ){
		sl_id_nbank_other_refuse = sl_id_nbank_other_refuse      =='0'?'本人直接命中' :sl_id_nbank_other_refuse       =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过身份证号查询非银-其他拒绝：<span style=\"color: #ff3333;\">"+sl_id_nbank_other_refuse+"</span></label>");
	};     

	if(sl_cell_abnormal              ){
		sl_cell_abnormal = sl_cell_abnormal              =='0'?'本人直接命中' :sl_cell_abnormal               =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询高危行为：<span style=\"color: #ff3333;\">"+sl_cell_abnormal+"</span></label>");
	};     

	if(sl_cell_phone_overdue         ){
		sl_cell_phone_overdue = sl_cell_phone_overdue         =='0'?'本人直接命中' :sl_cell_phone_overdue          =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询电信欠费：<span style=\"color: #ff3333;\">"+sl_cell_phone_overdue+"</span></label>");
	};     

	if(sl_cell_bank_bad              ){
		sl_cell_bank_bad = sl_cell_bank_bad              =='0'?'本人直接命中' :sl_cell_bank_bad               =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询银行(含信用卡)中风险：<span style=\"color: #ff3333;\">"+sl_cell_bank_bad+"</span></label>");
	};     

	if(sl_cell_bank_overdue          ){
		sl_cell_bank_overdue = sl_cell_bank_overdue          =='0'?'本人直接命中' :sl_cell_bank_overdue           =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询银行(含信用卡)一般风险：<span style=\"color: #ff3333;\">"+sl_cell_bank_overdue+"</span></label>");
	};     

	if(sl_cell_bank_fraud            ){
		sl_cell_bank_fraud = sl_cell_bank_fraud            =='0'?'本人直接命中' :sl_cell_bank_fraud             =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询银行(含信用卡)资信不佳：<span style=\"color: #ff3333;\">"+sl_cell_bank_fraud+"</span></label>");
	};     

	if(sl_cell_bank_lost             ){
		sl_cell_bank_lost = sl_cell_bank_lost             =='0'?'本人直接命中' :sl_cell_bank_lost              =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询银行(含信用卡)高风险：<span style=\"color: #ff3333;\">"+sl_cell_bank_lost+"</span></label>");
	};     

	if(sl_cell_bank_refuse           ){
		sl_cell_bank_refuse = sl_cell_bank_refuse           =='0'?'本人直接命中' :sl_cell_bank_refuse            =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询银行(含信用卡)拒绝：<span style=\"color: #ff3333;\">"+sl_cell_bank_refuse+"</span></label>");
	};    

	if(sl_cell_p2p_bad               ){
		sl_cell_p2p_bad = sl_cell_p2p_bad               =='0'?'本人直接命中' :sl_cell_p2p_bad                =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询非银(含全部非银类型)中风险：<span style=\"color: #ff3333;\">"+sl_cell_p2p_bad+"</span></label>");
	};     

	if(sl_cell_p2p_overdue           ){
		sl_cell_p2p_overdue = sl_cell_p2p_overdue           =='0'?'本人直接命中' :sl_cell_p2p_overdue            =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询非银(含全部非银类型)一般风险：<span style=\"color: #ff3333;\">"+sl_cell_p2p_overdue+"</span></label>");
	};    

	if(sl_cell_p2p_fraud             ){
		sl_cell_p2p_fraud = sl_cell_p2p_fraud             =='0'?'本人直接命中' :sl_cell_p2p_fraud              =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询非银(含全部非银类型)资信不佳：<span style=\"color: #ff3333;\">"+sl_cell_p2p_fraud+"</span></label>");
	};    

	if(sl_cell_p2p_lost              ){
		sl_cell_p2p_lost = sl_cell_p2p_lost              =='0'?'本人直接命中' :sl_cell_p2p_lost               =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询非银(含全部非银类型)高风险：<span style=\"color: #ff3333;\">"+sl_cell_p2p_lost+"</span></label>");
	};    

	if(sl_cell_p2p_refuse            ){
		sl_cell_p2p_refuse = sl_cell_p2p_refuse            =='0'?'本人直接命中' :sl_cell_p2p_refuse             =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询非银(含全部非银类型)拒绝：<span style=\"color: #ff3333;\">"+sl_cell_p2p_refuse+"</span></label>");
	};     

	if(sl_cell_nbank_p2p_bad         ){
		sl_cell_nbank_p2p_bad = sl_cell_nbank_p2p_bad         =='0'?'本人直接命中' :sl_cell_nbank_p2p_bad          =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询非银-P2P中风险：<span style=\"color: #ff3333;\">"+sl_cell_nbank_p2p_bad+"</span></label>");
	};     

	if(sl_cell_nbank_p2p_overdue     ){
		sl_cell_nbank_p2p_overdue = sl_cell_nbank_p2p_overdue     =='0'?'本人直接命中' :sl_cell_nbank_p2p_overdue      =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询非银-P2P一般风险：<span style=\"color: #ff3333;\">"+sl_cell_nbank_p2p_overdue+"</span></label>");
	};    

	if(sl_cell_nbank_p2p_fraud       ){
		sl_cell_nbank_p2p_fraud = sl_cell_nbank_p2p_fraud       =='0'?'本人直接命中' :sl_cell_nbank_p2p_fraud        =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询非银-P2P资信不佳：<span style=\"color: #ff3333;\">"+sl_cell_nbank_p2p_fraud+"</span></label>");
	};     

	if(sl_cell_nbank_p2p_lost        ){
		sl_cell_nbank_p2p_lost = sl_cell_nbank_p2p_lost        =='0'?'本人直接命中' :sl_cell_nbank_p2p_lost         =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询非银-P2P高风险：<span style=\"color: #ff3333;\">"+sl_cell_nbank_p2p_lost+"</span></label>");
	};     

	if(sl_cell_nbank_p2p_refuse      ){
		sl_cell_nbank_p2p_refuse = sl_cell_nbank_p2p_refuse      =='0'?'本人直接命中' :sl_cell_nbank_p2p_refuse       =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询非银-P2P拒绝：<span style=\"color: #ff3333;\">"+sl_cell_nbank_p2p_refuse+"</span></label>");
	};     

	if(sl_cell_nbank_mc_bad          ){
		sl_cell_nbank_mc_bad = sl_cell_nbank_mc_bad          =='0'?'本人直接命中' :sl_cell_nbank_mc_bad           =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询非银-小贷中风险：<span style=\"color: #ff3333;\">"+sl_cell_nbank_mc_bad+"</span></label>");
	};     

	if(sl_cell_nbank_mc_overdue      ){
		sl_cell_nbank_mc_overdue = sl_cell_nbank_mc_overdue      =='0'?'本人直接命中' :sl_cell_nbank_mc_overdue       =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询非银-小贷一般风险：<span style=\"color: #ff3333;\">"+sl_cell_nbank_mc_overdue+"</span></label>");
	};     

	if(sl_cell_nbank_mc_fraud        ){
		sl_cell_nbank_mc_fraud = sl_cell_nbank_mc_fraud        =='0'?'本人直接命中' :sl_cell_nbank_mc_fraud         =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询非银-小贷资信不佳：<span style=\"color: #ff3333;\">"+sl_cell_nbank_mc_fraud+"</span></label>");
	};     

	if(sl_cell_nbank_mc_lost         ){
		sl_cell_nbank_mc_lost = sl_cell_nbank_mc_lost         =='0'?'本人直接命中' :sl_cell_nbank_mc_lost          =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询非银-小贷高风险：<span style=\"color: #ff3333;\">"+sl_cell_nbank_mc_lost+"</span></label>");
	};     

	if(sl_cell_nbank_mc_refuse       ){
		sl_cell_nbank_mc_refuse = sl_cell_nbank_mc_refuse       =='0'?'本人直接命中' :sl_cell_nbank_mc_refuse        =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询非银-小贷拒绝：<span style=\"color: #ff3333;\">"+sl_cell_nbank_mc_refuse+"</span></label>");
	};     

	if(sl_cell_nbank_ca_bad          ){
		sl_cell_nbank_ca_bad = sl_cell_nbank_ca_bad          =='0'?'本人直接命中' :sl_cell_nbank_ca_bad           =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询非银-现金类分期中风险：<span style=\"color: #ff3333;\">"+sl_cell_nbank_ca_bad+"</span></label>");
	};     

	if(sl_cell_nbank_ca_overdue      ){
		sl_cell_nbank_ca_overdue = sl_cell_nbank_ca_overdue      =='0'?'本人直接命中' :sl_cell_nbank_ca_overdue       =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询非银-现金类分期一般风险：<span style=\"color: #ff3333;\">"+sl_cell_nbank_ca_overdue+"</span></label>");
	};     

	if(sl_cell_nbank_ca_fraud        ){
		sl_cell_nbank_ca_fraud = sl_cell_nbank_ca_fraud        =='0'?'本人直接命中' :sl_cell_nbank_ca_fraud         =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询非银-现金类分期资信不佳：<span style=\"color: #ff3333;\">"+sl_cell_nbank_ca_fraud+"</span></label>");
	};     

	if(sl_cell_nbank_ca_lost         ){
		sl_cell_nbank_ca_lost = sl_cell_nbank_ca_lost         =='0'?'本人直接命中' :sl_cell_nbank_ca_lost          =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询非银-现金类分期高风险：<span style=\"color: #ff3333;\">"+sl_cell_nbank_ca_lost+"</span></label>");
	};     

	if(sl_cell_nbank_ca_refuse       ){
		sl_cell_nbank_ca_refuse = sl_cell_nbank_ca_refuse       =='0'?'本人直接命中' :sl_cell_nbank_ca_refuse        =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询非银-现金类分期拒绝：<span style=\"color: #ff3333;\">"+sl_cell_nbank_ca_refuse+"</span></label>");
	};     

	if(sl_cell_nbank_com_bad         ){
		sl_cell_nbank_com_bad = sl_cell_nbank_com_bad         =='0'?'本人直接命中' :sl_cell_nbank_com_bad          =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询非银-代偿类分期中风险：<span style=\"color: #ff3333;\">"+sl_cell_nbank_com_bad+"</span></label>");
	};     

	if(sl_cell_nbank_com_overdue     ){
		sl_cell_nbank_com_overdue = sl_cell_nbank_com_overdue     =='0'?'本人直接命中' :sl_cell_nbank_com_overdue      =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询非银-代偿类分期一般风险：<span style=\"color: #ff3333;\">"+sl_cell_nbank_com_overdue+"</span></label>");
	};    

	if(sl_cell_nbank_com_fraud       ){
		sl_cell_nbank_com_fraud = sl_cell_nbank_com_fraud       =='0'?'本人直接命中' :sl_cell_nbank_com_fraud        =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询非银-代偿类分期资信不佳：<span style=\"color: #ff3333;\">"+sl_cell_nbank_com_fraud+"</span></label>");
	};     

	if(sl_cell_nbank_com_lost        ){
		sl_cell_nbank_com_lost = sl_cell_nbank_com_lost        =='0'?'本人直接命中' :sl_cell_nbank_com_lost         =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询非银-代偿类分期高风险：<span style=\"color: #ff3333;\">"+sl_cell_nbank_com_lost+"</span></label>");
	};     

	if(sl_cell_nbank_com_refuse      ){
		sl_cell_nbank_com_refuse = sl_cell_nbank_com_refuse      =='0'?'本人直接命中' :sl_cell_nbank_com_refuse       =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询非银-代偿类分期拒绝：<span style=\"color: #ff3333;\">"+sl_cell_nbank_com_refuse+"</span></label>");
	};     

	if(sl_cell_nbank_cf_bad          ){
		sl_cell_nbank_cf_bad = sl_cell_nbank_cf_bad          =='0'?'本人直接命中' :sl_cell_nbank_cf_bad           =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询非银-消费类分期中风险：<span style=\"color: #ff3333;\">"+sl_cell_nbank_cf_bad+"</span></label>");
	};     

	if(sl_cell_nbank_cf_overdue      ){
		sl_cell_nbank_cf_overdue = sl_cell_nbank_cf_overdue      =='0'?'本人直接命中' :sl_cell_nbank_cf_overdue       =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询非银-消费类分期一般风险：<span style=\"color: #ff3333;\">"+sl_cell_nbank_cf_overdue+"</span></label>");
	};     

	if(sl_cell_nbank_cf_fraud        ){
		sl_cell_nbank_cf_fraud = sl_cell_nbank_cf_fraud        =='0'?'本人直接命中' :sl_cell_nbank_cf_fraud         =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询非银-消费类分期资信不佳：<span style=\"color: #ff3333;\">"+sl_cell_nbank_cf_fraud+"</span></label>");
	};     

	if(sl_cell_nbank_cf_lost         ){
		sl_cell_nbank_cf_lost = sl_cell_nbank_cf_lost         =='0'?'本人直接命中' :sl_cell_nbank_cf_lost          =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询非银-消费类分期高风险：<span style=\"color: #ff3333;\">"+sl_cell_nbank_cf_lost+"</span></label>");
	};     

	if(sl_cell_nbank_cf_refuse       ){
		sl_cell_nbank_cf_refuse = sl_cell_nbank_cf_refuse       =='0'?'本人直接命中' :sl_cell_nbank_cf_refuse        =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询非银-消费类分期拒绝：<span style=\"color: #ff3333;\">"+sl_cell_nbank_cf_refuse+"</span></label>");
	};     

	if(sl_cell_nbank_other_bad       ){
		sl_cell_nbank_other_bad = sl_cell_nbank_other_bad       =='0'?'本人直接命中' :sl_cell_nbank_other_bad        =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询非银-其他中风险：<span style=\"color: #ff3333;\">"+sl_cell_nbank_other_bad+"</span></label>");
	};     

	if(sl_cell_nbank_other_overdue   ){
		sl_cell_nbank_other_overdue = sl_cell_nbank_other_overdue   =='0'?'本人直接命中' :sl_cell_nbank_other_overdue    =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询非银-其他一般风险：<span style=\"color: #ff3333;\">"+sl_cell_nbank_other_overdue+"</span></label>");
	};   

	if(sl_cell_nbank_other_fraud     ){
		sl_cell_nbank_other_fraud = sl_cell_nbank_other_fraud     =='0'?'本人直接命中' :sl_cell_nbank_other_fraud      =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询非银-其他资信不佳：<span style=\"color: #ff3333;\">"+sl_cell_nbank_other_fraud+"</span></label>");
	};    

	if(sl_cell_nbank_other_lost      ){
		sl_cell_nbank_other_lost = sl_cell_nbank_other_lost      =='0'?'本人直接命中' :sl_cell_nbank_other_lost       =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询非银-其他高风险：<span style=\"color: #ff3333;\">"+sl_cell_nbank_other_lost+"</span></label>");
	};     

	if(sl_cell_nbank_other_refuse    ){
		sl_cell_nbank_other_refuse = sl_cell_nbank_other_refuse    =='0'?'本人直接命中' :sl_cell_nbank_other_refuse     =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过手机号查询非银-其他拒绝：<span style=\"color: #ff3333;\">"+sl_cell_nbank_other_refuse+"</span></label>");
	};   

	if(sl_lm_cell_abnormal           ){
		sl_lm_cell_abnormal = sl_lm_cell_abnormal           =='0'?'本人直接命中' :sl_lm_cell_abnormal            =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过联系人手机查询高危行为：<span style=\"color: #ff3333;\">"+sl_lm_cell_abnormal+"</span></label>");
	};     

	if(sl_lm_cell_phone_overdue      ){
		sl_lm_cell_phone_overdue = sl_lm_cell_phone_overdue      =='0'?'本人直接命中' :sl_lm_cell_phone_overdue       =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过联系人手机查询电信欠费：<span style=\"color: #ff3333;\">"+sl_lm_cell_phone_overdue+"</span></label>");
	};     

	if(sl_lm_cell_bank_bad           ){
		sl_lm_cell_bank_bad = sl_lm_cell_bank_bad           =='0'?'本人直接命中' :sl_lm_cell_bank_bad            =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过联系人手机查询银行(含信用卡)中风险：<span style=\"color: #ff3333;\">"+sl_lm_cell_bank_bad+"</span></label>");
	};     

	if(sl_lm_cell_bank_overdue       ){
		sl_lm_cell_bank_overdue = sl_lm_cell_bank_overdue       =='0'?'本人直接命中' :sl_lm_cell_bank_overdue        =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过联系人手机查询银行(含信用卡)一般风险：<span style=\"color: #ff3333;\">"+sl_lm_cell_bank_overdue+"</span></label>");
	};    

	if(sl_lm_cell_bank_fraud         ){
		sl_lm_cell_bank_fraud = sl_lm_cell_bank_fraud         =='0'?'本人直接命中' :sl_lm_cell_bank_fraud          =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过联系人手机查询银行(含信用卡)资信不佳：<span style=\"color: #ff3333;\">"+sl_lm_cell_bank_fraud+"</span></label>");
	};     

	if(sl_lm_cell_bank_lost          ){
		sl_lm_cell_bank_lost = sl_lm_cell_bank_lost          =='0'?'本人直接命中' :sl_lm_cell_bank_lost           =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过联系人手机查询银行(含信用卡)高风险：<span style=\"color: #ff3333;\">"+sl_lm_cell_bank_lost+"</span></label>");
	};     

	if(sl_lm_cell_bank_refuse        ){
		sl_lm_cell_bank_refuse = sl_lm_cell_bank_refuse        =='0'?'本人直接命中' :sl_lm_cell_bank_refuse         =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过联系人手机查询银行(含信用卡)拒绝：<span style=\"color: #ff3333;\">"+sl_lm_cell_bank_refuse+"</span></label>");
	};     

	if(sl_lm_cell_nbank_p2p_bad      ){
		sl_lm_cell_nbank_p2p_bad = sl_lm_cell_nbank_p2p_bad      =='0'?'本人直接命中' :sl_lm_cell_nbank_p2p_bad       =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过联系人手机查询非银-P2P中风险：<span style=\"color: #ff3333;\">"+sl_lm_cell_nbank_p2p_bad+"</span></label>");
	};     

	if(sl_lm_cell_nbank_p2p_overdue  ){
		sl_lm_cell_nbank_p2p_overdue = sl_lm_cell_nbank_p2p_overdue  =='0'?'本人直接命中' :sl_lm_cell_nbank_p2p_overdue   =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过联系人手机查询非银-P2P一般风险：<span style=\"color: #ff3333;\">"+sl_lm_cell_nbank_p2p_overdue+"</span></label>");
	};   

	if(sl_lm_cell_nbank_p2p_fraud    ){
		sl_lm_cell_nbank_p2p_fraud = sl_lm_cell_nbank_p2p_fraud    =='0'?'本人直接命中' :sl_lm_cell_nbank_p2p_fraud     =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过联系人手机查询非银-P2P资信不佳：<span style=\"color: #ff3333;\">"+sl_lm_cell_nbank_p2p_fraud+"</span></label>");
	};   

	if(sl_lm_cell_nbank_p2p_lost     ){
		sl_lm_cell_nbank_p2p_lost = sl_lm_cell_nbank_p2p_lost     =='0'?'本人直接命中' :sl_lm_cell_nbank_p2p_lost      =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过联系人手机查询非银-P2P高风险：<span style=\"color: #ff3333;\">"+sl_lm_cell_nbank_p2p_lost+"</span></label>");
	};    

	if(sl_lm_cell_nbank_p2p_refuse   ){
		sl_lm_cell_nbank_p2p_refuse = sl_lm_cell_nbank_p2p_refuse   =='0'?'本人直接命中' :sl_lm_cell_nbank_p2p_refuse    =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过联系人手机查询非银-P2P拒绝：<span style=\"color: #ff3333;\">"+sl_lm_cell_nbank_p2p_refuse+"</span></label>");
	};   

	if(sl_lm_cell_nbank_mc_bad       ){
		sl_lm_cell_nbank_mc_bad = sl_lm_cell_nbank_mc_bad       =='0'?'本人直接命中' :sl_lm_cell_nbank_mc_bad        =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过联系人手机查询非银-小贷中风险：<span style=\"color: #ff3333;\">"+sl_lm_cell_nbank_mc_bad+"</span></label>");
	};     

	if(sl_lm_cell_nbank_mc_overdue   ){
		sl_lm_cell_nbank_mc_overdue = sl_lm_cell_nbank_mc_overdue   =='0'?'本人直接命中' :sl_lm_cell_nbank_mc_overdue    =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过联系人手机查询非银-小贷一般风险：<span style=\"color: #ff3333;\">"+sl_lm_cell_nbank_mc_overdue+"</span></label>");
	};   

	if(sl_lm_cell_nbank_mc_fraud     ){
		sl_lm_cell_nbank_mc_fraud = sl_lm_cell_nbank_mc_fraud     =='0'?'本人直接命中' :sl_lm_cell_nbank_mc_fraud      =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过联系人手机查询非银-小贷资信不佳：<span style=\"color: #ff3333;\">"+sl_lm_cell_nbank_mc_fraud+"</span></label>");
	};    

	if(sl_lm_cell_nbank_mc_lost      ){
		sl_lm_cell_nbank_mc_lost = sl_lm_cell_nbank_mc_lost      =='0'?'本人直接命中' :sl_lm_cell_nbank_mc_lost       =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过联系人手机查询非银-小贷高风险：<span style=\"color: #ff3333;\">"+sl_lm_cell_nbank_mc_lost+"</span></label>");
	};     

	if(sl_lm_cell_nbank_mc_refuse    ){
		sl_lm_cell_nbank_mc_refuse = sl_lm_cell_nbank_mc_refuse    =='0'?'本人直接命中' :sl_lm_cell_nbank_mc_refuse     =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过联系人手机查询非银-小贷拒绝：<span style=\"color: #ff3333;\">"+sl_lm_cell_nbank_mc_refuse+"</span></label>");
	};   

	if(sl_lm_cell_nbank_ca_bad       ){
		sl_lm_cell_nbank_ca_bad = sl_lm_cell_nbank_ca_bad       =='0'?'本人直接命中' :sl_lm_cell_nbank_ca_bad        =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过联系人手机查询非银-现金类分期中风险：<span style=\"color: #ff3333;\">"+sl_lm_cell_nbank_ca_bad+"</span></label>");
	};     

	if(sl_lm_cell_nbank_ca_overdue   ){
		sl_lm_cell_nbank_ca_overdue = sl_lm_cell_nbank_ca_overdue   =='0'?'本人直接命中' :sl_lm_cell_nbank_ca_overdue    =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过联系人手机查询非银-现金类分期一般风险：<span style=\"color: #ff3333;\">"+sl_lm_cell_nbank_ca_overdue+"</span></label>");
	};   

	if(sl_lm_cell_nbank_ca_fraud     ){
		sl_lm_cell_nbank_ca_fraud = sl_lm_cell_nbank_ca_fraud     =='0'?'本人直接命中' :sl_lm_cell_nbank_ca_fraud      =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过联系人手机查询非银-现金类分期资信不佳：<span style=\"color: #ff3333;\">"+sl_lm_cell_nbank_ca_fraud+"</span></label>");
	};    

	if(sl_lm_cell_nbank_ca_lost      ){
		sl_lm_cell_nbank_ca_lost = sl_lm_cell_nbank_ca_lost      =='0'?'本人直接命中' :sl_lm_cell_nbank_ca_lost       =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过联系人手机查询非银-现金类分期高风险：<span style=\"color: #ff3333;\">"+sl_lm_cell_nbank_ca_lost+"</span></label>");
	};     

	if(sl_lm_cell_nbank_ca_refuse    ){
		sl_lm_cell_nbank_ca_refuse = sl_lm_cell_nbank_ca_refuse    =='0'?'本人直接命中' :sl_lm_cell_nbank_ca_refuse     =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过联系人手机查询非银-现金类分期拒绝：<span style=\"color: #ff3333;\">"+sl_lm_cell_nbank_ca_refuse+"</span></label>");
	};   

	if(sl_lm_cell_nbank_com_bad      ){
		sl_lm_cell_nbank_com_bad = sl_lm_cell_nbank_com_bad      =='0'?'本人直接命中' :sl_lm_cell_nbank_com_bad       =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过联系人手机查询非银-代偿类分期中风险：<span style=\"color: #ff3333;\">"+sl_lm_cell_nbank_com_bad+"</span></label>");
	};     

	if(sl_lm_cell_nbank_com_overdue  ){
		sl_lm_cell_nbank_com_overdue = sl_lm_cell_nbank_com_overdue  =='0'?'本人直接命中' :sl_lm_cell_nbank_com_overdue   =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过联系人手机查询非银-代偿类分期一般风险：<span style=\"color: #ff3333;\">"+sl_lm_cell_nbank_com_overdue+"</span></label>");
	};   

	if(sl_lm_cell_nbank_com_fraud    ){
		sl_lm_cell_nbank_com_fraud = sl_lm_cell_nbank_com_fraud    =='0'?'本人直接命中' :sl_lm_cell_nbank_com_fraud     =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过联系人手机查询非银-代偿类分期资信不佳：<span style=\"color: #ff3333;\">"+sl_lm_cell_nbank_com_fraud+"</span></label>");
	};   

	if(sl_lm_cell_nbank_com_lost     ){
		sl_lm_cell_nbank_com_lost = sl_lm_cell_nbank_com_lost     =='0'?'本人直接命中' :sl_lm_cell_nbank_com_lost      =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过联系人手机查询非银-代偿类分期高风险：<span style=\"color: #ff3333;\">"+sl_lm_cell_nbank_com_lost+"</span></label>");
	};    

	if(sl_lm_cell_nbank_com_refuse   ){
		sl_lm_cell_nbank_com_refuse = sl_lm_cell_nbank_com_refuse   =='0'?'本人直接命中' :sl_lm_cell_nbank_com_refuse    =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过联系人手机查询非银-代偿类分期拒绝：<span style=\"color: #ff3333;\">"+sl_lm_cell_nbank_com_refuse+"</span></label>");
	};   

	if(sl_lm_cell_nbank_cf_bad       ){
		sl_lm_cell_nbank_cf_bad = sl_lm_cell_nbank_cf_bad       =='0'?'本人直接命中' :sl_lm_cell_nbank_cf_bad        =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过联系人手机查询非银-消费类分期中风险：<span style=\"color: #ff3333;\">"+sl_lm_cell_nbank_cf_bad+"</span></label>");
	};     

	if(sl_lm_cell_nbank_cf_overdue   ){
		sl_lm_cell_nbank_cf_overdue = sl_lm_cell_nbank_cf_overdue   =='0'?'本人直接命中' :sl_lm_cell_nbank_cf_overdue    =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过联系人手机查询非银-消费类分期一般风险：<span style=\"color: #ff3333;\">"+sl_lm_cell_nbank_cf_overdue+"</span></label>");
	};   

	if(sl_lm_cell_nbank_cf_fraud     ){
		sl_lm_cell_nbank_cf_fraud = sl_lm_cell_nbank_cf_fraud     =='0'?'本人直接命中' :sl_lm_cell_nbank_cf_fraud      =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过联系人手机查询非银-消费类分期资信不佳：<span style=\"color: #ff3333;\">"+sl_lm_cell_nbank_cf_fraud+"</span></label>");
	};    

	if(sl_lm_cell_nbank_cf_lost      ){
		sl_lm_cell_nbank_cf_lost = sl_lm_cell_nbank_cf_lost      =='0'?'本人直接命中' :sl_lm_cell_nbank_cf_lost       =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过联系人手机查询非银-消费类分期高风险：<span style=\"color: #ff3333;\">"+sl_lm_cell_nbank_cf_lost+"</span></label>");
	};     

	if(sl_lm_cell_nbank_cf_refuse    ){
		sl_lm_cell_nbank_cf_refuse = sl_lm_cell_nbank_cf_refuse    =='0'?'本人直接命中' :sl_lm_cell_nbank_cf_refuse     =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过联系人手机查询非银-消费类分期拒绝：<span style=\"color: #ff3333;\">"+sl_lm_cell_nbank_cf_refuse+"</span></label>");
	};   

	if(sl_lm_cell_nbank_other_bad    ){
		sl_lm_cell_nbank_other_bad = sl_lm_cell_nbank_other_bad    =='0'?'本人直接命中' :sl_lm_cell_nbank_other_bad     =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过联系人手机查询非银-其他中风险：<span style=\"color: #ff3333;\">"+sl_lm_cell_nbank_other_bad+"</span></label>");
	};   

	if(sl_lm_cell_nbank_other_overdue){
		sl_lm_cell_nbank_other_overdue = sl_lm_cell_nbank_other_overdue=='0'?'本人直接命中' :sl_lm_cell_nbank_other_overdue =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过联系人手机查询非银-其他一般风险：<span style=\"color: #ff3333;\">"+sl_lm_cell_nbank_other_overdue+"</span></label>");
	};

	if(sl_lm_cell_nbank_other_fraud  ){
		sl_lm_cell_nbank_other_fraud = sl_lm_cell_nbank_other_fraud  =='0'?'本人直接命中' :sl_lm_cell_nbank_other_fraud   =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过联系人手机查询非银-其他资信不佳：<span style=\"color: #ff3333;\">"+sl_lm_cell_nbank_other_fraud+"</span></label>");
	};   

	if(sl_lm_cell_nbank_other_lost   ){
		sl_lm_cell_nbank_other_lost = sl_lm_cell_nbank_other_lost   =='0'?'本人直接命中' :sl_lm_cell_nbank_other_lost    =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过联系人手机查询非银-其他高风险：<span style=\"color: #ff3333;\">"+sl_lm_cell_nbank_other_lost+"</span></label>");
	};   

	if(sl_lm_cell_nbank_other_refuse ){
		sl_lm_cell_nbank_other_refuse = sl_lm_cell_nbank_other_refuse =='0'?'本人直接命中' :sl_lm_cell_nbank_other_refuse  =='1'   ?'一度关系命中':'二度关系命中';
		$("#specialList_c").append("<label class=\"col-sm-4 control-label \" >通过联系人手机查询非银-其他拒绝：<span style=\"color: #ff3333;\">"+sl_lm_cell_nbank_other_refuse+"</span></label>");
	};
}else{
	$("#specialList_c").append("<label class=\"col-sm-4 control-label\" >无风险</label>");
}


                                                   