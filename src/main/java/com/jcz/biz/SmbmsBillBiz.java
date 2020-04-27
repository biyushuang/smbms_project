package com.jcz.biz;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jcz.entity.SmbmsBill;
import com.jcz.entity.SmbmsBillExample;

public interface SmbmsBillBiz {
	int countByExample(SmbmsBillExample example);

    int deleteByExample(SmbmsBillExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SmbmsBill record);

    int insertSelective(SmbmsBill record);

    Map selectByExample(SmbmsBill bill,String queryProductName,String queryProviderId,String queryIsPayment,String pageIndex);

    SmbmsBill selectByPrimaryKey(Long id);

    SmbmsBill selectBybillCode(String billcode);

    String selectAll();

    String selectMaxBillCode();

    int updateByExampleSelective(@Param("record") SmbmsBill record, @Param("example") SmbmsBillExample example);

    int updateByExample(@Param("record") SmbmsBill record, @Param("example") SmbmsBillExample example);

    int updateByPrimaryKeySelective(SmbmsBill record);

    int updateByPrimaryKey(SmbmsBill record);
}
