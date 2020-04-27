package com.jcz.bizImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jcz.biz.SmbmsUserBiz;
import com.jcz.dao.SmbmsUserMapper;
import com.jcz.entity.Param;
import com.jcz.entity.SmbmsUser;
import com.jcz.entity.SmbmsUserExample;
@Service("uBiz")
public class SmbmsUserBizImpl implements SmbmsUserBiz {
	@Autowired
	private SmbmsUserMapper userMapper;
	
	public void setUserMapper(SmbmsUserMapper userMapper) {
		this.userMapper = userMapper;
	}

	/*登陆   判断code是否存在,判断密码是否正确*/
	@Override
	public Map selectByExample(SmbmsUser user,String code,String queryname,String queryUserRole,String pageIndex) {
		
		//设置查询条件
		SmbmsUserExample example=new SmbmsUserExample();
		SmbmsUserExample.Criteria criteria=example.createCriteria();
		/*登陆判断*/
		if(user!=null){
			criteria.andUsercodeEqualTo(user.getUsercode());
			criteria.andUserpasswordEqualTo(user.getUserpassword());
		}
		/*Ajax  code是否存在判断*/
		if(code!=null){
			criteria.andUsercodeEqualTo(code);
		}
		
		/*条件查找*/
		if(queryUserRole!=null){
			Long roleid=Long.parseLong(queryUserRole);			
			if(roleid!=0){
				criteria.andUserroleEqualTo(roleid);
				
			}	
			if(!"".equals(queryname)){
				criteria.andUsernameLike("%"+queryname+"%");
			}
		}
		List<SmbmsUser> list=userMapper.selectByExample(example);/*按条件获取所有的list*/
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
		List<SmbmsUser> listuser=new ArrayList<>();/*用来放需要的list*/
		for (int i = (param.getCurrentPageNo()-1)*param.getSize(); i < (param.getCurrentPageNo()*param.getSize())&&i<param.getTotalCount(); i++) {
			listuser.add(list.get(i));				
		}
		
		map.put("listuser", listuser);
		
		return map;
	}
	

	@Override
	public int countByExample(SmbmsUserExample example) {
		// TODO Auto-generated method stub
		
		return userMapper.countByExample(example);
	}

	@Override
	public int deleteByExample(SmbmsUserExample example) {
		// TODO Auto-generated method stub
		return userMapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return userMapper.deleteByPrimaryKey(id);
	}

	/*添加用户*/
	@Override
	public int insert(SmbmsUser record) {
		// TODO Auto-generated method stub
		return userMapper.insert(record);
	}

	@Override
	public int insertSelective(SmbmsUser record) {
		// TODO Auto-generated method stub
		return userMapper.insertSelective(record);
	}


	@Override
	public SmbmsUser selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByExampleSelective(SmbmsUser record, SmbmsUserExample example) {
		// TODO Auto-generated method stub
		return userMapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(SmbmsUser record, SmbmsUserExample example) {
		// TODO Auto-generated method stub
		return userMapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(SmbmsUser record) {
		// TODO Auto-generated method stub
		return userMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SmbmsUser record) {
		// TODO Auto-generated method stub
		return userMapper.updateByPrimaryKey(record);
	}

	

}
