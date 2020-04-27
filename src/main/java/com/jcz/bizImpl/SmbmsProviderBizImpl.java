package com.jcz.bizImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcz.biz.SmbmsProviderBiz;
import com.jcz.dao.SmbmsProviderMapper;
import com.jcz.entity.Param;
import com.jcz.entity.SmbmsProvider;
import com.jcz.entity.SmbmsProviderExample;
import com.jcz.entity.SmbmsUser;
@Service("pBiz")
public class SmbmsProviderBizImpl implements SmbmsProviderBiz {
	
	@Autowired
	private SmbmsProviderMapper proM;
	
	public void setProM(SmbmsProviderMapper proM) {
		this.proM = proM;
	}

	@Override
	public int countByExample(SmbmsProviderExample example) {
		// TODO Auto-generated method stub
		return proM.countByExample(example);
	}

	@Override
	public int deleteByExample(SmbmsProviderExample example) {
		// TODO Auto-generated method stub
		return proM.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return proM.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(SmbmsProvider record) {
		// TODO Auto-generated method stub
		return proM.insert(record);
	}

	@Override
	public int insertSelective(SmbmsProvider record) {
		// TODO Auto-generated method stub
		return proM.insertSelective(record);
	}

	/*获得供应商列表list*/
	@Override
	public Map selectByExample(SmbmsProvider provider,String queryProCode,String queryProName,String pageIndex) {
		SmbmsProviderExample example=new SmbmsProviderExample();
		SmbmsProviderExample.Criteria criteria=example.createCriteria();
		
		if(provider!=null){
			
		}
		
		if(queryProName!=null){
			criteria.andPronameLike("%"+queryProName+"%");
			if(!"".equals(queryProCode)){
				criteria.andProcodeEqualTo(queryProCode);
			}
		}
		List<SmbmsProvider> list=proM.selectByExample(example);
		
		/*配置param的值*/
		Param param=new Param();
		param.setTotalCount(list.size());
		
		Integer index=0;
		/*若是第一次访问，赋值1*/
		if(pageIndex!=null){
			index=Integer.parseInt(pageIndex);
		}
		if(index<1){
			index=1;
		}
		if(index>param.getTotalPageCount()){
			index=param.getTotalPageCount();
		}
		param.setCurrentPageNo(index);
		
		Map map=new HashMap<>();
		map.put("param", param);
		if(list.size()==1||list.size()==0){
			map.put("listuser", list);
			return map;
		}
		List<SmbmsProvider> listuser=new ArrayList<>();/*用来放需要的list*/
		for (int i = (param.getCurrentPageNo()-1)*param.getSize(); i < (param.getCurrentPageNo()*param.getSize())&&i<param.getTotalCount(); i++) {
			listuser.add(list.get(i));				
		}
		
		map.put("listuser", listuser);
		
		return map;
	}

	@Override
	public SmbmsProvider selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return proM.selectByPrimaryKey(id);
	}

	@Override
	public int updateByExampleSelective(SmbmsProvider record, SmbmsProviderExample example) {
		// TODO Auto-generated method stub
		return proM.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(SmbmsProvider record, SmbmsProviderExample example) {
		// TODO Auto-generated method stub
		return proM.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(SmbmsProvider record) {
		// TODO Auto-generated method stub
		return proM.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SmbmsProvider record) {
		// TODO Auto-generated method stub
		return proM.updateByPrimaryKey(record);
	}

}
