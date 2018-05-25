package cn.itcast.bos.web.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.base.Promotion;
import cn.itcast.bos.service.IPromotionService;

@ParentPackage("struts-default")
@Namespace("/")
@Actions
@Controller
@Scope("prototype")
public class PromotionAction extends BaseAction<Promotion> {

	@Resource
	private IPromotionService promotionService;

	private File imgFile;// 文件上传的属性名

	private String imgFileFileName;// 文件名

	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}

	public void setImgFileFileName(String imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
	}

	/**
	 * 文件上传方法
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "promotionAction_uploadImg")
	public String uploadImg() throws IOException {
		// 1.获取文件保存的绝对路径：D:\Program
		// Files\apache-tomcat-7.0.52\wtpwebapps\bos_management_web\attached
		String absPath = ServletActionContext.getServletContext().getRealPath("/") + "attached/";
		// 2.获取文件的相对路径：bos_management_web\attached
		String relPath = ServletActionContext.getRequest().getContextPath() + "/attached/";
		// 3.生成随机的文件名
		// 3.1获取随机字符串
		UUID uuid = UUID.randomUUID();
		// 3.2获取文件后缀：.jpeg等 abc.jpeg
		String sufixStr = imgFileFileName.substring(imgFileFileName.lastIndexOf("."));
		// 3.3生成随机文件名
		String randomFileName = uuid + sufixStr;
		// 4.将文件保存到绝对路径
		// 4.1创建绝对路径文件对象
		File destFile = new File(absPath + randomFileName);
		// 4.2将上传的文件拷贝到新文件中
		FileUtils.copyFile(imgFile, destFile);
		// 5.将文件的相对路径返回到界面
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("error", 0);
		map.put("url", relPath + randomFileName);
		String[] excludes = {};
		this.writeJava2JsonObject(map, excludes);
		return NONE;
	}

	@Action(value = "promotionAction_uploadManage")
	public String uploadManage() throws IOException {
		// 根目录路径，可以指定绝对路径，比如 /var/www/attached/
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter out = response.getWriter();
		String rootPath = ServletActionContext.getServletContext().getRealPath("/") + "attached/";
		// 根目录URL，可以指定绝对路径，比如 http://www.yoursite.com/attached/
		String rootUrl = request.getContextPath() + "/attached/";
		// 图片扩展名
		String[] fileTypes = new String[] { "gif", "jpg", "jpeg", "png", "bmp" };

		/*
		 * String dirName = request.getParameter("dir"); if (dirName != null) {
		 * if(!Arrays.<String>asList(new String[]{"image", "flash", "media",
		 * "file"}).contains(dirName)){ out.println("Invalid Directory name.");
		 * return NONE; } rootPath += dirName + "/"; rootUrl += dirName + "/";
		 * File saveDirFile = new File(rootPath); if (!saveDirFile.exists()) {
		 * saveDirFile.mkdirs(); } }
		 */
		// 根据path参数，设置各路径和URL
		String path = request.getParameter("path") != null ? request.getParameter("path") : "";
		String currentPath = rootPath + path;
		String currentUrl = rootUrl + path;
		String currentDirPath = path;
		String moveupDirPath = "";
		if (!"".equals(path)) {
			String str = currentDirPath.substring(0, currentDirPath.length() - 1);
			moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
		}

		// 排序形式，name or size or type
		String order = request.getParameter("order") != null ? request.getParameter("order").toLowerCase() : "name";

		// 不允许使用..移动到上一级目录
		if (path.indexOf("..") >= 0) {
			out.println("Access is not allowed.");
			return NONE;
		}
		// 最后一个字符不是/
		if (!"".equals(path) && !path.endsWith("/")) {
			out.println("Parameter is not valid.");
			return NONE;
		}
		// 目录不存在或不是目录
		File currentPathFile = new File(currentPath);
		if (!currentPathFile.isDirectory()) {
			out.println("Directory does not exist.");
			return NONE;
		}

		// 遍历目录取的文件信息
		List<Hashtable> fileList = new ArrayList<Hashtable>();
		if (currentPathFile.listFiles() != null) {
			for (File file : currentPathFile.listFiles()) {
				Hashtable<String, Object> hash = new Hashtable<String, Object>();
				String fileName = file.getName();
				if (file.isDirectory()) {
					hash.put("is_dir", true);
					hash.put("has_file", (file.listFiles() != null));
					hash.put("filesize", 0L);
					hash.put("is_photo", false);
					hash.put("filetype", "");
				} else if (file.isFile()) {
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					hash.put("is_dir", false);
					hash.put("has_file", false);
					hash.put("filesize", file.length());
					hash.put("is_photo", Arrays.<String> asList(fileTypes).contains(fileExt));
					hash.put("filetype", fileExt);
				}
				hash.put("filename", fileName);
				hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
				fileList.add(hash);
			}
		}

		if ("size".equals(order)) {
			Collections.sort(fileList, new SizeComparator());
		} else if ("type".equals(order)) {
			Collections.sort(fileList, new TypeComparator());
		} else {
			Collections.sort(fileList, new NameComparator());
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("moveup_dir_path", moveupDirPath);
		result.put("current_dir_path", currentDirPath);
		result.put("current_url", currentUrl);
		result.put("total_count", fileList.size());
		result.put("file_list", fileList);

		String[] excludes = {};
		this.writeJava2JsonObject(result, excludes);
		return NONE;
	}

	private File titleImgFile;// 文件

	private String titleImgFileFileName;// 文件名称

	public void setTitleImgFile(File titleImgFile) {
		this.titleImgFile = titleImgFile;
	}

	public void setTitleImgFileFileName(String titleImgFileFileName) {
		this.titleImgFileFileName = titleImgFileFileName;
	}

	@Action(value = "promotionAction_add", results = {
			@Result(name = "success", location = "/pages/take_delivery/promotion.jsp") })
	public String add() throws IOException {
		String absPath = ServletActionContext.getServletContext().getRealPath("/") + "upload/";
		String relPath = ServletActionContext.getRequest().getContextPath() + "/upload/";
		// 3.生成随机的文件名
		// 3.1获取随机字符串
		UUID uuid = UUID.randomUUID();
		// 3.2获取文件后缀：.jpeg等 abc.jpeg
		String sufixStr = titleImgFileFileName.substring(titleImgFileFileName.lastIndexOf("."));
		// 3.3生成随机文件名
		String randomFileName = uuid + sufixStr;
		// 4.将文件保存到绝对路径
		// 4.1创建绝对路径文件对象
		File destFile = new File(absPath + randomFileName);
		// 4.2将上传的文件拷贝到新文件中
		FileUtils.copyFile(titleImgFile, destFile);
		// 5.将相对路径放到model中，保存到数据：
		model.setTitleImg(relPath + randomFileName);
		// 6.保存活动数据
		promotionService.save(model);
		return "success";
	}
	
	/**
	 * 分页查询方法
	 * @return
	 * @throws IOException 
	 */
	@Action(value="promotionAction_pageQuery")
	public String pageQuery() throws IOException{
		//1.封装分页查询参数
		//参数1：当前页-1
		//参数2：每页条数
		Pageable pageable = new PageRequest(page - 1, rows);
		Specification<Promotion> spec = new Specification<Promotion>(){

			/**
			 * root：保存当前查询的实体类和表的映射关系
			 * query：可以将多个查询条件组装成一个查询数据
			 * cb：可以组装一个查询条件，比如id like ?
			 */
			@Override
			public Predicate toPredicate(Root<Promotion> root, 
					CriteriaQuery<?> query, CriteriaBuilder cb) {
                return null;
			}
		};
		//2.调用service执行分页查询：总条数和每页要显示的数据集合
		Page<Promotion> page = promotionService.pageQuery(spec, pageable);
		//3.将查询的结果转换成json对象
		/*{                                                      
			"total":100,	
			"rows":[ 
				{"id":"001","courierNum":"09876","name":"李大","telephone":"13912345678","checkPwd":"123456","pda":"123456","standard":{"name":"10-20公斤"},"type":"小件员","vehicleType":"卡车","vehicleNum":"98765","company":"杭州分部","deltag":"0"},
				{"id":"002","courierNum":"09876","name":"李大","telephone":"13912345678","checkPwd":"123456","pda":"123456","standard":{"name":"10-20公斤"},"type":"小件员","vehicleType":"卡车","vehicleNum":"98765","company":"杭州分部","deltag":"0"}
			]
		}*/
		String[] excludes = {};
		this.writePage2JsonObject(page, excludes);
		return NONE;
	}
}

class NameComparator implements Comparator {
	public int compare(Object a, Object b) {
		Hashtable hashA = (Hashtable) a;
		Hashtable hashB = (Hashtable) b;
		if (((Boolean) hashA.get("is_dir")) && !((Boolean) hashB.get("is_dir"))) {
			return -1;
		} else if (!((Boolean) hashA.get("is_dir")) && ((Boolean) hashB.get("is_dir"))) {
			return 1;
		} else {
			return ((String) hashA.get("filename")).compareTo((String) hashB.get("filename"));
		}
	}

}

class SizeComparator implements Comparator {
	public int compare(Object a, Object b) {
		Hashtable hashA = (Hashtable) a;
		Hashtable hashB = (Hashtable) b;
		if (((Boolean) hashA.get("is_dir")) && !((Boolean) hashB.get("is_dir"))) {
			return -1;
		} else if (!((Boolean) hashA.get("is_dir")) && ((Boolean) hashB.get("is_dir"))) {
			return 1;
		} else {
			if (((Long) hashA.get("filesize")) > ((Long) hashB.get("filesize"))) {
				return 1;
			} else if (((Long) hashA.get("filesize")) < ((Long) hashB.get("filesize"))) {
				return -1;
			} else {
				return 0;
			}
		}
	}
}

class TypeComparator implements Comparator {
	public int compare(Object a, Object b) {
		Hashtable hashA = (Hashtable) a;
		Hashtable hashB = (Hashtable) b;
		if (((Boolean) hashA.get("is_dir")) && !((Boolean) hashB.get("is_dir"))) {
			return -1;
		} else if (!((Boolean) hashA.get("is_dir")) && ((Boolean) hashB.get("is_dir"))) {
			return 1;
		} else {
			return ((String) hashA.get("filetype")).compareTo((String) hashB.get("filetype"));
		}
	}
}