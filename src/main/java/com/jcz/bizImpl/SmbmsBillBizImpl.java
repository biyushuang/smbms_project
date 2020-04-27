package com.jcz.bizImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcz.biz.SmbmsBillBiz;
import com.jcz.dao.SmbmsBillMapper;
import com.jcz.entity.Param;
import com.jcz.entity.SmbmsBill;
import com.jcz.entity.SmbmsBillExample;
import com.jcz.entity.SmbmsProvider;
import com.jcz.entity.SmbmsProviderExample;

@Service("bBiz")
public class SmbmsBillBizImpl implements SmbmsBillBiz {

	@Autowired
	private SmbmsBillMapper billM;
	
	public void setBillM(SmbmsBillMapper billM) {
		this.billM = billM;
	}

	@Override
	public int countByExample(SmbmsBillExample example) {
		// TODO Auto-generated method stub
		return billM.countByExample(example);
	}

	@Override
	public int deleteByExample(SmbmsBillExample example) {
		// TODO Auto-generated method stub
		return billM.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return billM.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(SmbmsBill record) {
		// TODO Auto-generated method stub
		return billM.insert(record);
	}

	@Override
	public int insertSelective(SmbmsBill record) {
		// TODO Auto-generated method stub
		return billM.insertSelective(record);
	}

	@Override
	public Map selectByExample(SmbmsBill bill,String queryProductName,String queryProviderId,String queryIsPayment,String pageIndex) {
		// TODO Auto-generated method stub
		SmbmsBillExample example=new SmbmsBillExample();
		SmbmsBillExample.Criteria criteria=example.createCriteria();
		
		if(bill!=null){
			
		}
		
		if(queryProductName!=null){
			criteria.andProductnameLike("%"+queryProductName+"%");
			Long queryPId=Long.parseLong(queryProviderId);
			Integer queryIP=Integer.parseInt(queryIsPayment);
			if(queryPId!=0){
				criteria.andProvideridEqualTo(queryPId);
			}
			if(queryIP!=0){
				criteria.andIspaymentEqualTo(queryIP);
			}
		}
		
		List<SmbmsBill> list=billM.selectByExample(example);
		
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
		List<SmbmsBill> listuser=new ArrayList<>();/*用来放需要的list*/
		for (int i = (param.getCurrentPageNo()-1)*param.getSize(); i < (param.getCurrentPageNo()*param.getSize())&&i<param.getTotalCount(); i++) {
			listuser.add(list.get(i));				
		}
		
		map.put("listuser", listuser);
		
		return map;
	}

	@Override
	public SmbmsBill selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return billM.selectByPrimaryKey(id);
	}

	@Override
	public SmbmsBill selectBybillCode(String billcode) {
		// TODO Auto-generated method stub
		return billM.selectBybillCode(billcode);
	}

	@Override
	public String selectAll() {
		return null;
	}

	@Override
	public String selectMaxBillCode() {
		// TODO Auto-generated method stub
		return billM.selectMaxBillCode();
	}

	@Override
	public int updateByExampleSelective(SmbmsBill record, SmbmsBillExample example) {
		// TODO Auto-generated method stub
		return billM.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(SmbmsBill record, SmbmsBillExample example) {
		// TODO Auto-generated method stub
		return billM.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(SmbmsBill record) {
		// TODO Auto-generated method stub
		return billM.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SmbmsBill record) {
		// TODO Auto-generated method stub
		return billM.updateByPrimaryKey(record);
	}

}
