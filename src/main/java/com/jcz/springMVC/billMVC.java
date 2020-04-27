package com.jcz.springMVC;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;

import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.annotation.JSONField;
import com.jcz.entity.*;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.jcz.bizImpl.SmbmsBillBizImpl;
import com.jcz.bizImpl.SmbmsProviderBizImpl;

@Controller
public class billMVC {

	@Autowired
	@Qualifier("bBiz")
	private SmbmsBillBizImpl bBiz;
	
	@Autowired
	@Qualifier("pBiz")
	private SmbmsProviderBizImpl pBiz;

	
	public void setpBiz(SmbmsProviderBizImpl pBiz) {
		this.pBiz = pBiz;
	}

	public void setbBiz(SmbmsBillBizImpl bBiz) {
		this.bBiz = bBiz;
	}
	
	/*进入billlist页面   modify页面  view页面*/
	@RequestMapping("/bill.do")
	public String tobilllist(Model model,String method,String billid,String queryProductName,String queryProviderId,String queryIsPayment,String pageIndex){
		if("view".equals(method)){
			SmbmsBill bill=bBiz.selectByPrimaryKey(Long.parseLong(billid));
			
			bill.setProvidername(pBiz.selectByPrimaryKey(bill.getProviderid()).getProname());/*根据会员id，获得会员名字*/
			model.addAttribute("bill", bill);
			return "billview";
		}else if("modify".equals(method)){
			model.addAttribute("bill", bBiz.selectByPrimaryKey(Long.parseLong(billid)));
			return "billmodify";
		}else{			
			Map map=bBiz.selectByExample(null,queryProductName,queryProviderId,queryIsPayment,pageIndex);/*按照分页获取list 、和分页信息param*/
			Map map2=pBiz.selectByExample(null, null, null, null);/*获取会员列表*/
			List<SmbmsBill> list2=new ArrayList<>();
			for (SmbmsBill bill : (List<SmbmsBill>)map.get("listuser")) {
				bill.setProvidername(pBiz.selectByPrimaryKey(bill.getProviderid()).getProname());/*根据会员id，获得会员名字*/
				list2.add(bill);
			}
			
			model.addAttribute("billList", list2);
			model.addAttribute("providerList", map2.get("listuser"));
			model.addAttribute("page", map.get("param"));
			model.addAttribute("queryProductName",queryProductName);
			model.addAttribute("queryProviderId", queryProviderId);
			model.addAttribute("queryIsPayment", queryIsPayment);
			return "billlist";
		}
	}
	
	/*删除bill页面   获取会员列表*/
	@ResponseBody
	@RequestMapping("/delbill.do")
	public Object delbill(String billid,String method){
		Map map=new HashMap<>();
		if("delbill".equals(method)){
			if(bBiz.selectByPrimaryKey(Long.parseLong(billid))==null){
				map.put("delResult", "notexist");
			}
			int flag=bBiz.deleteByPrimaryKey(Long.parseLong(billid));
			if(flag>0){
				map.put("delResult", "true");
			}else{
				map.put("delResult", "false");
			}
		}else if("getproviderlist".equals(method)){
			Map map2=pBiz.selectByExample(null,null,null,null);
			map.put("list", map2.get("listuser"));
		}
		return JSONArray.toJSONString(map);
	}
	
	/*跳到添加bill页面*/
	@RequestMapping("/billadd.do")
	public String billadd(){
		
		return "billadd";
	}

	/*转换billCode流水号的方法
	* 最少四位，不足以0补齐
	* */
	public static String changetostring(int num){
		String numString = "";
		if(num<10){
			numString = "000" + String.valueOf(num);
		}else if(num<100){
			numString = "00" + String.valueOf(num);
		}else if(num<1000){
			numString = "0" + String.valueOf(num);
		}else{
			numString = String.valueOf(num);
		}
		return numString;
	}

	/*bill 各种save*/
	@RequestMapping("/billsave.do")
	public String billsave(String method,SmbmsBill bill,HttpSession session){
		if("add".equals(method)){
			//真实时间, 例20191217
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			String now_dateString = formatter.format(currentTime);
			//数据库中最大billcode, 例：201912010006
			String max_bcode = bBiz.selectMaxBillCode();
			String bef_dateString = max_bcode.substring(0,8);
			String numString = max_bcode.substring(8,max_bcode.length());
			String res_billCode = "";
			if(now_dateString.equals(bef_dateString)){
				int new_num = Integer.parseInt(numString)+1;
				res_billCode = now_dateString + changetostring(new_num);
			}else{
				res_billCode = now_dateString + "0001";
			}
			SmbmsUser user=(SmbmsUser)session.getAttribute("userSession");
			bill.setBillcode(res_billCode);
			bill.setCreatedby(user.getId());
			bill.setCreationdate(new Date());
			long m1 = 1;
			bill.setModifyby(m1);
			int flag=bBiz.insertSelective(bill);
			if(flag>0){
				return "redirect:/bill.do";
			}else{
				return "redirect:/billadd.do";
			}
		}else{
			SmbmsBillExample example=new SmbmsBillExample();
			SmbmsBillExample.Criteria criteria=example.createCriteria();
			SmbmsBill bill1 = bBiz.selectBybillCode(bill.getBillcode());
			long m2 = 2;
			SmbmsProvider pro = pBiz.selectByPrimaryKey(bill1.getProviderid());
			System.out.println(pro);
			if(!bill1.getTotalprice().equals(bill.getTotalprice()) || !bill1.getIspayment().equals(bill.getIspayment())){
				if(bill1.getModifyby() != m2){
					BigDecimal add_price = bill1.getTotalprice();
					BigDecimal before_price =new BigDecimal(pro.getProfax());
					BigDecimal now_price = add_price.add(before_price);
					// 0-2000，普通会员，打0.98折
					BigDecimal price1 = new BigDecimal(2000);
					// 2000-5000，vip会员，打0.95折
					// 5000，vvip会员，打0.9折
					BigDecimal price3 = new BigDecimal(5000);
					if(now_price.compareTo(price1)<1){
						pro.setProdesc("普通会员");
					}else if(now_price.compareTo(price3)>-1){
						pro.setProdesc("vvip会员");
					}else{
						pro.setProdesc("vip会员");
					}
					pro.setProfax(now_price.toString());
					int flag_p = pBiz.updateByPrimaryKeySelective(pro);
					if(flag_p>0){
						bill.setModifyby(m2);
					}
				}
				BigDecimal t_price = bill1.getTotalprice();
				String p_desc = pro.getProdesc();
				BigDecimal rebate1 = new BigDecimal(0.98);
				BigDecimal rebate2 = new BigDecimal(0.95);
				BigDecimal rebate3 = new BigDecimal(0.90);
				if(p_desc.equals("普通会员")){
					bill.setTotalprice(t_price.multiply(rebate1));
				}else if(p_desc.equals("vip会员")){
					bill.setTotalprice(t_price.multiply(rebate2));
				}else if(p_desc.equals("vvip会员")){
					bill.setTotalprice(t_price.multiply(rebate3));
				}
			}
			int flag=bBiz.updateByExampleSelective(bill, example);
			System.out.print(flag);
			if(flag>0){
				return "redirect:/bill.do";
			}else{
				return "redirect:/bill.do?method=modify&billid="+bill.getId();
			}
		}
	}
}
