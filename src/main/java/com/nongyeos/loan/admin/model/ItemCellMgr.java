package com.nongyeos.loan.admin.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nongyeos.loan.base.util.Constants;


public class ItemCellMgr {
	private Short type;
	private short step;
	private String customStep;		//特殊间距，1,2 表示第1、2期间距1，第2、3期间距2，以此类推
	private List itemList;
	private int[] row = null;
	private int[] col = null;
	private int[] revise = null;
	private String[] colAZ = null;

	
	public ItemCellMgr(Short type, Short step, List itemList, String customStep)
	{
		this.type = type;
		this.step = step.shortValue();
		this.customStep = customStep;
		this.itemList = itemList;
		init();
	}
	
	
	public ItemCellMgr(Short type, int initRow, String initCol, int stepRow, int stepCol, int itemNum, String customStep)
	{
		this.type = type;
		this.itemList = Collections.synchronizedList(new LinkedList());
		
		if(type==2)
		{
			this.step = (short)stepRow;
			
			/* 计算itemList */
		}
		else if(type==3)
		{
			this.step = (short)stepCol;
			
			for(int i=0;i<itemNum;i++)
			{
				String[] obj = new String[2];
				obj[0] = "";
				obj[1] = initCol + (initRow + i * stepRow);
				this.itemList.add(obj);
			}
		}
		
		this.customStep = customStep;
		
		init();
	}
	
	
	private void init()
	{
		String regEx = "[^0-9]";
		
		row = new int[itemList.size()];
		col = new int[itemList.size()];
		colAZ = new String[itemList.size()];
		Pattern p = Pattern.compile(regEx);
		
		for(int i=0;i<itemList.size();i++)
		{
			Object[] itemBean = (Object[])itemList.get(i);
			if(itemBean != null && !itemBean[1].equals(""))
			{
				Matcher m = p.matcher((String)itemBean[1]);
				row[i] = Integer.parseInt(m.replaceAll("").trim());
				colAZ[i] = ((String)itemBean[1]).replaceAll(m.replaceAll("").trim(), "").trim();
				col[i] = AZToInt(colAZ[i]);
			}
		}
		
		//特殊间距，1,2 表示第1、2期间距1，第2、3期间距2，以此类推
		
		if(customStep == null)
		{
			customStep = "";
		}
			
		String[] tmp = customStep.split(",");
		revise = new int[tmp.length];
		
		for(int i=0;i<tmp.length;i++)
		{
			try
			{
				revise[i] = Integer.parseInt(tmp[i]) - step;
			}
			catch(Exception e)
			{
				revise[i] = 0;
			}
		}
	}
	
	
	public String[] getCells(int idxEntry)
	{
		String[] cells = new String[itemList.size()];
		
		int reviseTotal = 0;
		for(int i=0;i<revise.length && i<=(idxEntry-1);i++)
		{
			reviseTotal = reviseTotal + revise[i];
		}
		
		for(int i=0;i<itemList.size();i++)
		{
			Object[] itemBean = (Object[])itemList.get(i);
			if(itemBean == null || itemBean[1].equals(""))
			{
				cells[i] = "";
			}
			else
			{
				if(type==2)
				{
					cells[i] = colAZ[i] + (row[i] + idxEntry * step + reviseTotal);
				}					
				else if(type==3)
				{					
					cells[i] = intToAZ(col[i] + idxEntry * step + reviseTotal) + row[i];
				}
			}
		}
		
		return(cells);
	}
	
	
	private int AZToInt(String src)
	{
		int result = 0;
		int base = 1;
		
		if(src == null)
		{
			src = "";
		}
		
		src = src.trim().toUpperCase();
		for(int i=(src.length()-1);i>=0;i--)
		{
			char c = src.charAt(i);
			result = result + ((byte)c - 65 + 1) * base;
			base = base * 26;
		}
		
		return(result);
	}
	
	
	private String intToAZ(int src)
	{
		String result = "";
		int rNum;
		char c = 'Z';
		
		while(src > 0)
		{
			rNum = src % 26;			
			if(rNum > 0)
			{
				c = (char)('A' + rNum - 1);
			}
			else if(rNum == 0)
			{
				c = 'Z';
				rNum = 26;
			}
			
			result = String.valueOf(c) + result;
			src = (src - rNum) / 26;
		}
		
		return(result);
	}
}
