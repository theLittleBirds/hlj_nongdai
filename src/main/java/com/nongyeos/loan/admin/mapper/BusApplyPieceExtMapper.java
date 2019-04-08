package com.nongyeos.loan.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nongyeos.loan.admin.entity.BusApplyPieceExt;
import com.nongyeos.loan.admin.resultMap.DynamicDataMap;

public interface BusApplyPieceExtMapper {
    int deleteByPrimaryKey(String extId);

    int insert(BusApplyPieceExt record);

    int insertSelective(BusApplyPieceExt record);

    BusApplyPieceExt selectByPrimaryKey(String extId);

    int updateByPrimaryKeySelective(BusApplyPieceExt record);

    int updateByPrimaryKey(BusApplyPieceExt record);
    
    List<DynamicDataMap> selectBySectionId(DynamicDataMap record);
    
    int deleteByIpAndItem(DynamicDataMap record);
    
    BusApplyPieceExt queryByApplyPieceAndItem(@Param("intoPieceId")String intoPieceId, @Param("itemId")String itemId);

	BusApplyPieceExt getByItemIdAndIntoPieceId(String intoPieceId, String itemId);

	List<Object> selectByParentItemId(String mainId);

	List<BusApplyPieceExt> selectByIpId(String id);
}