package com.jcz.bizImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcz.biz.SmbmsRoleBiz;
import com.jcz.dao.SmbmsRoleMapper;
import com.jcz.entity.SmbmsRole;
import com.jcz.entity.SmbmsRoleExample;

@Service("rBiz")
public class SmbmsRoleBizImpl implements SmbmsRoleBiz {

	@Autowired
	private SmbmsRoleMapper rM;
	
	public void setrM(SmbmsRoleMapper rM) {
		this.rM = rM;
	}

	@Override
	public int countByExample(SmbmsRoleExample example) {
		// TODO Auto-generated method stub
		return rM.countByExample(example);
	}

	@Override
	public int deleteByExample(SmbmsRoleExample example) {
		// TODO Auto-generated method stub
		return rM.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return rM.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(SmbmsRole record) {
		// TODO Auto-generated method stub
		return rM.insert(record);
	}

	@Override
	public int insertSelective(SmbmsRole record) {
		// TODO Auto-generated method stub
		return rM.insertSelective(record);
	}

	@Override
	public List<SmbmsRole> selectByExample(SmbmsRole role) {
		SmbmsRoleExample example=new SmbmsRoleExample();
		SmbmsRoleExample.Criteria criteria=example.createCriteria();		
		List<SmbmsRole> list=rM.selectByExample(example);
		return list;
	}

	@Override
	public SmbmsRole selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return rM.selectByPrimaryKey(id);
	}

	@Override
	public int updateByExampleSelective(SmbmsRole record, SmbmsRoleExample example) {
		// TODO Auto-generated method stub
		return rM.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(SmbmsRole record, SmbmsRoleExample example) {
		// TODO Auto-generated method stub
		return rM.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(SmbmsRole record) {
		// TODO Auto-generated method stub
		return rM.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SmbmsRole record) {
		// TODO Auto-generated method stub
		return rM.updateByPrimaryKey(record);
	}

}
