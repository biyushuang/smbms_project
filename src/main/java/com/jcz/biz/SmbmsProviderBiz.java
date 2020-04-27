package com.jcz.biz;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jcz.entity.SmbmsProvider;
import com.jcz.entity.SmbmsProviderExample;

public interface SmbmsProviderBiz {

    int countByExample(SmbmsProviderExample example);

    int deleteByExample(SmbmsProviderExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SmbmsProvider record);

    int insertSelective(SmbmsProvider record);

    Map selectByExample(SmbmsProvider provider,String queryProCode,String queryProName,String pageIndex);

    SmbmsProvider selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SmbmsProvider record, @Param("example") SmbmsProviderExample example);

    int updateByExample(@Param("record") SmbmsProvider record, @Param("example") SmbmsProviderExample example);

    int updateByPrimaryKeySelective(SmbmsProvider record);

    int updateByPrimaryKey(SmbmsProvider record);

}
