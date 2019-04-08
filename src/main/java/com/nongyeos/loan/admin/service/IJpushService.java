package com.nongyeos.loan.admin.service;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusJpush;

public interface IJpushService {

	void save(BusJpush jpush) throws Exception;

	List<BusJpush> selectAllPush() throws Exception;

	void deleteById(String id)throws Exception;

}
