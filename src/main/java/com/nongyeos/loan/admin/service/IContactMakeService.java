package com.nongyeos.loan.admin.service;

import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.entity.BusLoan;

public interface IContactMakeService {
	
	public void dyfdb(BusIntoPiece ip,BusLoan loan,String personId) throws Exception;
	
	public void wtcg(BusIntoPiece ip,BusLoan loan,String personId) throws Exception;
	
	public void wxld(BusIntoPiece ip,BusLoan loan,String personId) throws Exception;
	
	public void cnh(BusIntoPiece ip,BusLoan loan,String personId) throws Exception;

	public void fwht(BusIntoPiece ip, BusLoan loan, String personId) throws Exception;

	public void tdlz(BusIntoPiece ip, BusLoan loan, String personId) throws Exception;
	
}
