package cn.itcast.bos.test.freemarker;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerTest {

	@Test
	public void testOutput() throws IOException, TemplateException{
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_22);
		configuration.setDirectoryForTemplateLoading(new File("src/main/webapp/WEB-INF/template"));
		// 获取模板对象
		Template template = configuration.getTemplate("hello.ftl");
		
		//使用Map集合创建动态数据对象
		Map<String, Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("title", "黑马程序员");
		parameterMap.put("msg", "你好，这是第一个Freemarker的案例");
		
		//合并输出，在控制台显示
		template.process(parameterMap, new PrintWriter(System.out));
	}
}
