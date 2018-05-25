package cn.itcast.bos.web.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;

import cn.itcast.activemq.producer.queue.QueueSender;
import cn.itcast.bos.utils.MailUtils;
import cn.itcast.bos.utils.SmsUtils;
import cn.itcast.crm.domain.Customer;

@ParentPackage("struts-default")
@Namespace("/")
@Actions
@Controller
@Scope("prototype")
public class CustomerAction extends BaseAction<Customer> {

	@Resource
	private QueueSender queueSender;
	
	/**
	 * 给客户发短信方法
	 * @return
	 */
	@Action(value="customerAction_sendMsg")
	public String sendMsg(){
		//1.生成验证码
		String validcode = RandomStringUtils.randomNumeric(6);
		//2.将验证码放到session
		ServletActionContext.getRequest().getSession().setAttribute(model.getTelephone(), validcode);
		String msg = "尊敬的用户您好，本次获取的验证码为：" + validcode + ",服务电话：4006184000【传智播客】";
		//3.给activemq发送发短信的消息
//		SmsUtils.sendSmsByWebService(model.getTelephone(), msg);
		Map<String,String> map = new HashMap<String, String>();
		map.put("telephone", model.getTelephone());
		map.put("msg", msg);
		queueSender.send("test.map", map);
		return NONE;
	}

	private String validcode;
	
	public void setValidcode(String validcode) {
		this.validcode = validcode;
	}
	
	@Resource
	private RedisTemplate<String, String> redisTemplate;
	
	/**
	 * 用户注册方法
	 * @return
	 */
	@Action(value="customerAction_signup",
			results={
					@Result(name="success", location="/signup-success.html",type="redirect"),
					@Result(name="failure", location="/signup-fail.html",type="redirect")
			})
	public String signup(){
		//1.校验验证码是否正确
//		String validcodeS = (String) ServletActionContext
//				.getRequest().getSession().getAttribute(model.getTelephone());
//		if(StringUtils.isNotBlank(validcode)
//				&& validcode.equals(validcodeS)){
			//2.正确，调用crm接口保存客户信息，跳转成功界面
			WebClient.create("http://localhost:8080/")
					 .path("crm_management/services/crmservice/customer/regist")
					 .type(MediaType.APPLICATION_XML)
					 .accept(MediaType.APPLICATION_XML)
					 .post(model);
			//2.1生成激活码
			String activecode = RandomStringUtils.randomNumeric(64);
			//2.2将激活码保存到redis
			redisTemplate.opsForValue().set(model.getTelephone(), activecode, 24, TimeUnit.HOURS);
			String subject = "速运快递：激活邮件";
			String content = "尊敬的客户您好，请于24小时内,进行邮箱绑定，点击下面地址进行激活：<a href='"
							+MailUtils.activeUrl
							+"?telephone="+model.getTelephone()
							+"&activecode="+activecode
							+"'>速运快递激活链接</a>";
			//2.3给客户邮箱发送邮件
			MailUtils.sendMail(subject, content, model.getEmail());
			return "success";
//		} else {
//			//3.不正确，跳转失败界面
//			return "failure";
//		}
	}

	private String activecode;
	
	public void setActivecode(String activecode) {
		this.activecode = activecode;
	}
	
	/**
	 * 激活邮件方法
	 * @return
	 * @throws IOException 
	 */
	@Action(value="customerAction_activeMail")
	public String activeMail() throws IOException{
		//1.判断激活码是否有效和正确
		//1.1从redis获取激活码
		String activecodeR = redisTemplate.opsForValue().get(model.getTelephone());
		if(StringUtils.isNotBlank(activecodeR)
				&& activecodeR.equals(activecode)){
			//2.有效且正确，获取客户激活状态
			//2.1获取客户激活状态
			Customer customer = WebClient.create("http://localhost:8080/")
					 .path("crm_management/services/crmservice/customer/findCustomerByTelephone/"+model.getTelephone())
					 .type(MediaType.APPLICATION_JSON)
					 .accept(MediaType.APPLICATION_JSON)
					 .get(Customer.class);
			Integer type = customer.getType();
			//2.2判断客户是否激活
			if(null == type || type!=1){
				//2.3未激活，根据客户id激活客户状态
				try {
					WebClient.create("http://localhost:8080/")
					.path("crm_management/services/crmservice/customer/activeMail/"+customer.getId())
					.type(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.put(null);
					//2.6激活成功，提示客户激活成功！
					ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
					ServletActionContext.getResponse().getWriter().print("恭喜您，激活成功！");
				} catch (Exception e) {
					e.printStackTrace();
					//2.5激活失败，提示客户激活失败
					ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
					ServletActionContext.getResponse().getWriter().print("对不起，您激活失败，请您联系管理员！");
				}
			} else {
				//2.4已激活，提示客户已激活，不能重复激活
				ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
				ServletActionContext.getResponse().getWriter().print("对不起，您已经激活，不能重复激活！");
			}
		} else {
			//3.无效或错误，提示客户，激活码失效，不能激活！
			ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
			ServletActionContext.getResponse().getWriter().print("对不起，您的激活码已失效！");
		}
		return NONE;
	}
	
	@Action(value="customerAction_login",
			results={
					@Result(name="success", location="/index.html#/myhome",type="redirect"),
					@Result(name="failure", location="/login.html",type="redirect")
			})
	public String login(){
		//1.判断手机号密码是否为null
		String telephone = model.getTelephone();
		String password = model.getPassword();
		if(StringUtils.isNotBlank(telephone)
				&& StringUtils.isNotBlank(password)){
			//2.不为null，根据客户手机号和密码查询客户信息
			Customer customer = WebClient.create("http://localhost:8080/")
			.path("crm_management/services/crmservice/customer/findCustomerByTelephoneAndPassword/"+telephone+"/"+password)
			.type(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.get(Customer.class);
			if(null != customer){
				//2.1如果查询到客户，成功，将客户放到session中，跳转成功界面
				ServletActionContext.getRequest().getSession().setAttribute("loginCust", customer);
				return "success";
			} else {
				//2.2查询不到客户，跳转登录界面
				return "failure";
			}
		} else {
			//3.为null，账号或密码错误，跳转登陆界面
			return "failure";
		}
	}
}
