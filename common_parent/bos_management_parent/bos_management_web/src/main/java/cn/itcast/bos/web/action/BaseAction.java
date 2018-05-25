package cn.itcast.bos.web.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

    protected int page;//当前页

    protected int rows;//每页条数

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setPage(int page) {
        this.page = page;
    }

    protected T model;//new User();

    @Override
    public T getModel() {
        return model;
    }

    public BaseAction() {
        //UserAction extends BaseAction<User>
        //UserAction userAction = new UserAction();//@Controller或<bean id="classname" class="类全路径"/>
        //this == userAction
        //this.getClass() == UserAction.class
        //this.getClass().getGenericSuperclass() == BaseAction<User>.class
        ParameterizedType parameterizedType = (ParameterizedType)
                this.getClass().getGenericSuperclass();
        //parameterizedType.getActualTypeArguments() == [User.class]
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        //actualTypeArguments[0] == User.class
        Class<T> entityClass = (Class<T>) actualTypeArguments[0];
        //entityClass.newInstance() == new User()
        try {
            model = entityClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将分页查询数据转换成json对象的方法
     *
     * @param page
     * @param excludes
     * @throws IOException
     */
    public void writePage2JsonObject(Page<T> page, String[] excludes) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", page.getTotalElements());//总条数
        map.put("rows", page.getContent());//每页要显示的数据集合
        //json-lib：将java格式的数据转换成json数据
        //JSONObject：将java格式的数据转换成json对象
        //JSONArray：将java格式的数据转换成json数组
        //JsonConfig：将转换结果中不需要的属性排除
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(excludes);
        JSONObject jsonObject = JSONObject.fromObject(map, jsonConfig);
        String json = jsonObject.toString();
        //4.将json对象通过response返回到界面
        ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");//设置返回的数据格式
        ServletActionContext.getResponse().getWriter().print(json);//返回json数据
    }

    /**
     * 将JAVA数据转换成json数组的方法
     *
     * @param page
     * @param excludes
     * @throws IOException
     */
    public void writeJava2JsonArray(List<?> list, String[] excludes) throws IOException {
        //json-lib：将java格式的数据转换成json数据
        //JSONObject：将java格式的数据转换成json对象
        //JSONArray：将java格式的数据转换成json数组
        //JsonConfig：将转换结果中不需要的属性排除
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(excludes);
        JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
        String json = jsonArray.toString();
        //4.将json对象通过response返回到界面
        ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");//设置返回的数据格式
        ServletActionContext.getResponse().getWriter().print(json);//返回json数据
    }

    /**
     * 将JAVA数据转换成json对象的方法
     *
     * @param page
     * @param excludes
     * @throws IOException
     */
    public void writeJava2JsonObject(Object obj, String[] excludes) throws IOException {
        //json-lib：将java格式的数据转换成json数据
        //JSONObject：将java格式的数据转换成json对象
        //JSONArray：将java格式的数据转换成json数组
        //JsonConfig：将转换结果中不需要的属性排除
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(excludes);
        JSONObject jsonObject = JSONObject.fromObject(obj, jsonConfig);
        String json = jsonObject.toString();
        //4.将json对象通过response返回到界面
        ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");//设置返回的数据格式
        ServletActionContext.getResponse().getWriter().print(json);//返回json数据
    }
}
