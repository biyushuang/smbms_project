package com.jcz.springMVC;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jcz.entity.SmbmsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.alibaba.fastjson.JSONArray;
import com.jcz.bizImpl.SmbmsProviderBizImpl;
import com.jcz.entity.SmbmsProvider;

import javax.servlet.http.HttpSession;

@Controller
public class providerMBVC {

	@Autowired
	@Qualifier("pBiz")
	private SmbmsProviderBizImpl pBiz;
	
	
	public void setpBiz(SmbmsProviderBizImpl pBiz) {
		this.pBiz = pBiz;
	}
	
	/*进入会员list*/
	@RequestMapping("/provider.do")
	public String topro(Model model,String queryProCode,String queryProName,String pageIndex){
		Map map=pBiz.selectByExample(null,queryProCode,queryProName,pageIndex);
		model.addAttribute("providerList", map.get("listuser"));
		model.addAttribute("page", map.get("param"));
		model.addAttribute("queryProCode",queryProCode);
		model.addAttribute("queryProName", queryProName);
		return "providerlist";
	}
	
	/*根据method跳转到不同字段*/
	@RequestMapping("/providerALL.do")
	public String bymethod(String method,String proid,Model model){
		
		/*转到查看页面*/
		if("view".equals(method)){
			SmbmsProvider provider=pBiz.selectByPrimaryKey(Long.parseLong(proid));
			model.addAttribute("provider", provider);
			return "providerview";
		}
		/*转到更新页面*/
		else if("modify".equals(method)){
			SmbmsProvider provider=pBiz.selectByPrimaryKey(Long.parseLong(proid));
			model.addAttribute("provider", provider);
			return "providermodify";
		}
		/*转到添加页面*/
		else if("add".equals(method)){
			return "provideradd";
		}
		return "";
	}
	
	/*转到删除*/
	@ResponseBody
	@RequestMapping("/delpro")
	public String DelegatingFilterProxy(String proid){
		Map map=new HashMap<>();	
		SmbmsProvider provider=pBiz.selectByPrimaryKey(Long.parseLong(proid));
		if(provider==null){
				map.put("delResult", "notexist");
		}
		int flag=pBiz.deleteByPrimaryKey(Long.parseLong(proid));
		if(flag>0){
			map.put("delResult", "true");
		}else{
			map.put("delResult", "false");
		}
			
		return JSONArray.toJSONString(map);
		
	}
	
	/*提交添加 或修改的请求*/
	@RequestMapping("providersave.do")
	public String tosave(String method, SmbmsProvider provider, HttpSession session){
		if("add".equals(method)){
			SmbmsUser user=(SmbmsUser)session.getAttribute("userSession");
			provider.setCreatedby(user.getId());
			provider.setCreationdate(new Date());
			int flag=pBiz.insertSelective(provider);
			if(flag>0){
				return "redirect:/provider.do";
			}else{
				return "provideradd";
			}
		}
		else{
			int flag=pBiz.updateByPrimaryKeySelective(provider);
			if(flag>0){
				return "redirect:/provider.do";
			}else{
				return "providermodify";
			}
		}
	}
}
