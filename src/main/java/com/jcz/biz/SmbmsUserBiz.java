package com.jcz.biz;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jcz.entity.SmbmsUser;
import com.jcz.entity.SmbmsUserExample;

public interface SmbmsUserBiz {

	Map selectByExample(SmbmsUser user,String code,String queryname,String queryUserRole,String pageIndex);
	

	
    int countByExample(SmbmsUserExample example);

    int deleteByExample(SmbmsUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SmbmsUser record);

    int insertSelective(SmbmsUser record);


    SmbmsUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SmbmsUser record, @Param("example") SmbmsUserExample example);

    int updateByExample(@Param("record") SmbmsUser record, @Param("example") SmbmsUserExample example);

    int updateByPrimaryKeySelective(SmbmsUser record);

    int updateByPrimaryKey(SmbmsUser record);

}
