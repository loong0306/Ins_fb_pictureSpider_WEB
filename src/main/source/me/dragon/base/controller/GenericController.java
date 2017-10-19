package me.dragon.base.controller;

import com.alibaba.fastjson.JSONObject;
import me.dragon.base.core.Page;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by dragon on 4/5/2017.
 */
public class GenericController {

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }

    protected String ajaxSuccess(Object msg)throws IOException {
        JSONObject jvo = new JSONObject();
        jvo.put("flag", "T");
        jvo.put("msg", msg);

        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(jvo.toString());

        return null;
    }

    protected String ajaxError(Object msg)throws IOException{
        JSONObject jvo = new JSONObject();
        jvo.put("flag", "F");
        jvo.put("msg", msg);

        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(jvo.toString());

        return null;
    }

    protected String ajaxParam(String flag, Object msg, Object param)throws IOException{
        JSONObject jvo = new JSONObject();
        jvo.put("flag", flag);
        jvo.put("msg", msg);
        jvo.put("param", param);

        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(jvo.toString());

        return null;
    }


    /**
     * 初始分页
     */
    public Page initPage = new Page();
    @ModelAttribute
    public Page getInitPage() {
        initPage.setPageNo(1);
        initPage.setPageSize(10);
        return initPage;
    }

}
