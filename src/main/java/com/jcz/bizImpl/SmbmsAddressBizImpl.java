package com.jcz.bizImpl;

import java.util.List;

import com.jcz.biz.SmbmsAddressBiz;
import com.jcz.dao.SmbmsAddressMapper;
import com.jcz.entity.SmbmsAddress;
import com.jcz.entity.SmbmsAddressExample;

public class SmbmsAddressBizImpl implements SmbmsAddressBiz {

	private SmbmsAddressMapper addM;
	
	public void setAddM(SmbmsAddressMapper addM) {
		this.addM = addM;
	}

	@Override
	public int countByExample(SmbmsAddressExample example) {
		// TODO Auto-generated method stub
		return addM.countByExample(example);
	}

	@Override
	public int deleteByExample(SmbmsAddressExample example) {
		// TODO Auto-generated method stub
		return addM.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return addM.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(SmbmsAddress record) {
		// TODO Auto-generated method stub
		return addM.insert(record);
	}

	@Override
	public int insertSelective(SmbmsAddress record) {
		// TODO Auto-generated method stub
		return addM.insertSelective(record);
	}

	@Override
	public List<SmbmsAddress> selectByExample(SmbmsAddressExample example) {
		// TODO Auto-generated method stub
		return addM.selectByExample(example);
	}

	@Override
	public SmbmsAddress selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return addM.selectByPrimaryKey(id);
	}

	@Override
	public int updateByExampleSelective(SmbmsAddress record, SmbmsAddressExample example) {
		// TODO Auto-generated method stub
		return addM.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(SmbmsAddress record, SmbmsAddressExample example) {
		// TODO Auto-generated method stub
		return addM.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(SmbmsAddress record) {
		// TODO Auto-generated method stub
		return addM.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SmbmsAddress record) {
		// TODO Auto-generated method stub
		return addM.updateByPrimaryKey(record);
	}

}
