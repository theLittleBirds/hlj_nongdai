package com.nongyeos.loan.base.util;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nongyeos.loan.admin.entity.BusJpush;
import com.nongyeos.loan.admin.entity.BusMemberLogin;
import com.nongyeos.loan.admin.service.IJpushService;
import com.nongyeos.loan.admin.service.IMemberLoginService;

@Service("jpushThread")
public class JpushThread extends Thread {
	
	private Integer clock = 0;
	private JpushThread(){
		
	}
	
	@Autowired
	private IJpushService jpushService;
	@Autowired
	private IMemberLoginService memberLoginService;
	
	@Autowired
	private JpushUtils jpushUtils;
	
	private static JpushThread jpushThread ;
	
	@PostConstruct  
    public void init() {       
		jpushThread = this; 
		jpushThread.jpushService = this.jpushService; 
		jpushThread.memberLoginService = this.memberLoginService; 
		jpushThread.jpushUtils = this.jpushUtils; 
		
		System.out.println("jpush线程启动........");
		jpushThread.start();
	} 
	
	public static JpushThread getJpushThread(){
		return jpushThread;
	}
	public void  setFlag(){
		synchronized(clock){
			clock.notify();
		}
	}
	public void run(){
		while(true){
			while(true){
				try {
					List<BusJpush> list = jpushThread.jpushService.selectAllPush();
					for (int i = 0; i < list.size(); i++) {
						BusJpush jpush = list.get(i);
						BusMemberLogin ml = jpushThread.memberLoginService.selectById(jpush.getUserLoginId()); 
						if(StringUtils.isNotEmpty(ml.getJpushId()))
								jpushThread.jpushUtils.SendPush(jpush.getTitle(), jpush.getContent(), ml.getJpushId(),ml.getChannel());
								jpushThread.jpushService.deleteById(jpush.getId());
					}
					if(list.size()==0)
						break;
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			try {
				synchronized(clock){
				clock.wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
