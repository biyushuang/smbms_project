package com.jcz.springMVC;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.annotation.JSONField;
import com.jcz.biz.SmbmsRoleBiz;
import com.jcz.biz.SmbmsUserBiz;
import com.jcz.entity.SmbmsRole;
import com.jcz.entity.SmbmsUser;
import com.jcz.entity.Param;

@Controller
public class userMVC {
	private Logger logger=Logger.getLogger(userMVC.class);
	
	@Autowired
	@Qualifier("uBiz")
	private SmbmsUserBiz uBiz;
	
	@Autowired
	@Qualifier("rBiz")
	private SmbmsRoleBiz rBiz;
	
	
	public void setrBiz(SmbmsRoleBiz rBiz) {
		this.rBiz = rBiz;
	}

	public void setuBiz(SmbmsUserBiz uBiz) {
		this.uBiz = uBiz;
	}

	/*登录判断*/
	@RequestMapping("/login.do")
	public String login(SmbmsUser user,HttpSession session){
		Map map2=uBiz.selectByExample(user,null,null,null,null);
		List<SmbmsUser> list=(List<SmbmsUser>)map2.get("listuser");
		if(list.size()>0){
			session.setAttribute("userSession", list.get(0));
			return "frame";
		}else{
			return "login";
		}		
	}
	
	/*转到登陆页面*/
	@RequestMapping("/login.htm")
	public String tologin(){
		return "login";
	}
	
	/*进入list前先查找数据*/
	@RequestMapping(value="/tolist.do")
	public String list(Model model,String queryname,String queryUserRole,String pageIndex){
		List<SmbmsRole> list=rBiz.selectByExample(null);/*获取角色列表*/
		Map map=uBiz.selectByExample(null,null,queryname,queryUserRole,pageIndex);/*按条件获取list*/
		model.addAttribute("roleList", list);
		List<SmbmsUser> listuser=(List<SmbmsUser>)map.get("listuser");
		model.addAttribute("userList", listuser);
		Param param=(Param)map.get("param");
		model.addAttribute("page", param);
		model.addAttribute("queryname",queryname);
		model.addAttribute("queryUserRole", queryUserRole);
		return "userlist";
	}
	
	/*跳到添加用户页面*/
	@RequestMapping("/useradd.do")
	public String adduser(){
		
		return "useradd";
	}
	
	/*Ajax查询方法*/
	/*第一种解决乱码方式
	 * @RequestMapping(value="/isuser",produces={"application/json;charset=UTF-8"})*/
	@ResponseBody
	@JSONField(format="yyyy-MM-dd")
	@RequestMapping("/isuser")
	public Object isuser(String method,String usercode,String uid,String oldpassword,HttpSession session){
		Map map=new HashMap<>();
		List listMap=new ArrayList<>();
		/*获取角色列表*/
		if("getrolelist".equals(method)){
			List<SmbmsRole> list=rBiz.selectByExample(null);
			for (SmbmsRole smbmsRole : list) {
				Map map2=new HashMap<>();
				map2.put("id", smbmsRole.getId());
				map2.put("rolename", smbmsRole.getRolename());
				listMap.add(map2);
			}
			map.put("rows", listMap);
			
		}
		/*判断code是否存在*/
		else if("ucexist".equals(method)){
			if(""==usercode){
				map.put("usercode", "kong");
			}else{				
				Map map2=uBiz.selectByExample(null, usercode,null,null,null);
				List<SmbmsUser> list=(List<SmbmsUser>)map2.get("listuser");
				if(list.size()>0){
					map.put("usercode", "exist");
				}else{
					map.put("usercode", "true");
				}
			}
		}
		/*删除账号*/
		else if("deluser".equals(method)){
			if(uid==null){
				map.put("delResult", "notexist");
			}else{			
				Integer flag=uBiz.deleteByPrimaryKey(Long.parseLong(uid));
				if(flag>0){
					map.put("delResult", "true");
				}else{
					map.put("delResult", "false");
				}
			}
		}
		/*判断密码是否正确*/
		else if("pwdmodify".equals(method)){
			SmbmsUser user=(SmbmsUser)session.getAttribute("userSession");
			if(user==null){
				map.put("result", "sessionerror");
			}
			if("".equals(oldpassword)){
				map.put("result", "error");
			}else{
				if(oldpassword.equals(user.getUserpassword())){
					map.put("result", "true");
				}else{
					map.put("result", "false");
				}
			}
			
		}
		return JSONArray.toJSONString(map);
	}


	/*添加用户,多文件上传*/
	@RequestMapping(value="/usersave.do",method=RequestMethod.POST)
	public String usersave(SmbmsUser user,HttpSession session,HttpServletRequest request,
			 @RequestParam(value ="attachs", required = false) MultipartFile[] pics){
		SmbmsUser user1=(SmbmsUser)session.getAttribute("userSession");
		if(user1.getUserrole()<3){
			String idPicPath = null;
			String workPicPath = null;
			String errorInfo = null;
			boolean flag = true;
			String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			logger.info("uploadFile path ============== > "+path);
			for(int i = 0;i < pics.length ;i++){
				MultipartFile pic = pics[i];
				if(!pic.isEmpty()){
					if(i == 0){
						errorInfo = "uploadFileError";
					}else if(i == 1){
						errorInfo = "uploadWpError";
					}
					String oldFileName = pic.getOriginalFilename();//原文件名
					logger.info("uploadFile oldFileName ============== > "+oldFileName);
					String prefix=FilenameUtils.getExtension(oldFileName);//原文件后缀
					logger.debug("uploadFile prefix============> " + prefix);
					int filesize = 500000;
					logger.debug("uploadFile size============> " + pic.getSize());
					if(pic.getSize() >  filesize){//上传大小不得超过 500k
						request.setAttribute(errorInfo, " * 上传大小不得超过 500k");
						flag = false;
					}else if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png")
							|| prefix.equalsIgnoreCase("jpeg") || prefix.equalsIgnoreCase("pneg")){
						//上传图片格式不正确
						//定义上传文件名：当前系统时间+随机数+“_Personal.jpg”
						String fileName = System.currentTimeMillis()+RandomUtils.nextInt(1000000)+".jpg";
						fileName=user.getPhone()+"_"+fileName;
						System.err.print(fileName);
						logger.debug("new fileName======== " + pic.getName());
						File targetFile = new File(path, fileName);
						if(!targetFile.exists()){
							targetFile.mkdirs();
						}
						//保存
						try {
							pic.transferTo(targetFile);
						} catch (Exception e) {
							e.printStackTrace();
							request.setAttribute(errorInfo, " * 上传失败！");
							flag = false;
						}
						if(i == 0){
							idPicPath = path+File.separator+fileName;
						}else if(i == 1){
							workPicPath = path+File.separator+fileName;
						}
						logger.debug("idPicPath: " + idPicPath);
						logger.debug("workPicPath: " + workPicPath);
						System.out.println("path"+path);

					}else{
						request.setAttribute(errorInfo, " * 上传图片格式不正确");
						flag = false;
					}
				}
			}
			if(flag){
				user.setCreatedby(((SmbmsUser)session.getAttribute("userSession")).getId());
				user.setCreationdate(new Date());
				user.setIdpicpath(idPicPath);
				user.setWorkpicpath(workPicPath);
				if(user1.getUserrole()<user.getUserrole() && uBiz.insertSelective(user)>0){
					return "redirect:/tolist.do";
				}
			}
		}
		return "useradd";
	}
	
	/*进入查看个人信息页面*/
	@RequestMapping(value="/view")
	public String view(String id,Model model){
		model.addAttribute("user", uBiz.selectByPrimaryKey(Long.parseLong(id)));
		return "userview";
	}
	
	/*进入修改个人信息页面*/
	@RequestMapping(value="/modify")
	public String modify(String id,Model model){
		model.addAttribute("user", uBiz.selectByPrimaryKey(Long.parseLong(id)));
		return "usermodify";
	}
	
	/*修改信息提交，成功则跳到list*/
	@RequestMapping("/modifysave.do")
	public String modifysave(SmbmsUser user){
		int flag=uBiz.updateByPrimaryKeySelective(user);
		if(flag>0){
			return "redirect:/tolist.do";
		}else{
			return "usermodify";
		}
	}
	
	/*进入密码修改页面*/
	@RequestMapping("/pwdmodify.do")
	public String pwdmodify(HttpSession session,Model model){
		Long id=((SmbmsUser)session.getAttribute("userSession")).getId();
		if(id==null){
			return "login";
		}
		model.addAttribute("user", uBiz.selectByPrimaryKey(id));
		return "pwdmodify";
	}
	
	/*提交密码修改，成功则跳到登陆页面*/
	@RequestMapping("/pwdmodifysave.do")
	public String pwdmodifysave(String newpassword,HttpSession session){
		SmbmsUser user=(SmbmsUser)session.getAttribute("userSession");
		user.setUserpassword(newpassword);
		int flag=uBiz.updateByPrimaryKeySelective(user);
		if(flag>0){
			return "redirect:logout.do";
		}else{			
			return "pwdmodify.do";
		}
	}
	
	/*注销*/
	@RequestMapping("/logout.do")
	public String loginout(HttpSession session){
		session.removeAttribute("userSession");
		return "syserror";
	}
}
