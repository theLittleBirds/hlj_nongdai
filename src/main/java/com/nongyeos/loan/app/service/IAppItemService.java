package com.nongyeos.loan.app.service;

import java.util.List;

import com.nongyeos.loan.app.entity.AppItem;

public interface IAppItemService {

	List<AppItem> selectAllByEntityId(String entityId) throws Exception;

	AppItem selectByFiledName(String filedName);

	void addItem(AppItem item);

	void updateItem(AppItem item);

	void deleteItem(String itemId) throws Exception;

	AppItem selectItem(String itemId) throws Exception;

	List<AppItem> getAllItem() throws Exception;

	List<AppItem> queryAppItemByDegisnAndEmpty(AppItem appItem) throws Exception;
}
