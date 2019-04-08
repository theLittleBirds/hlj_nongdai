package com.nongyeos.loan.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nongyeos.loan.admin.entity.SysSeno;
import com.nongyeos.loan.admin.mapper.SysSenoMapper;
import com.nongyeos.loan.admin.service.ISysSenoService;

@Service("sysSenoService")
public class SysSenoServiceImpl implements ISysSenoService{
	
	@Autowired
	private SysSenoMapper sysSenoMapper;

	@Override
	public synchronized String getNextString(String name, int length, String prefix, long startNum)
	{
		long curNum;
		
		SysSeno seqNo = sysSenoMapper.selectByPrimaryKey(name);
		if(seqNo == null)
		{
			curNum = startNum;
			seqNo = new SysSeno();
			seqNo.setName(name);
			seqNo.setValue(new Long(curNum+1));
			sysSenoMapper.insert(seqNo);
		}
		else
		{
			curNum = seqNo.getValue().longValue();
			seqNo.setValue(new Long(curNum+1));
			sysSenoMapper.updateByPrimaryKeySelective(seqNo);
		}
		
		String strSeqNo = prefix;
		String strCurNum = Long.toString(curNum);
		int numZero = length - prefix.length() - strCurNum.length();
		
		for(int i=0;i<numZero;i++)
		{
			strSeqNo = strSeqNo + "0";
		}
		
		strSeqNo = strSeqNo + strCurNum;
		
		return(strSeqNo);
	}
}
