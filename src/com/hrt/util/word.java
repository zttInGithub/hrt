package com.hrt.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import word.model;
import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class word {
    private Configuration configuration = null;

    public word() {
        configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
    }

    public void createDoc(Map dataMap, String path, String filename) {
        // 	要填入模本的数据文件
        TemplateLoader templateLoader = null;

        Template t = null;
        try {
            // test.ftl	为要装载的模板
            templateLoader = new ClassTemplateLoader(model.class);
            //   path="model4.xml";
            configuration.setTemplateLoader(templateLoader);
            t = configuration.getTemplate(path);
            t.setEncoding("utf-8");
        } catch
        (IOException e) {
            e.printStackTrace();
        }
        // 	输出文档路径及名称
        //String filename="装机单.doc";
        HttpServletResponse resp = ServletActionContext.getResponse();
        Writer out = null;
        try {
            resp.setCharacterEncoding("utf-8");
            // resp.setContentType("application/vnd.ms-word");
            resp.setHeader("content-disposition", "attachment; filename=" + new String(filename.getBytes("GB2312"), "ISO-8859-1"));
            out = new BufferedWriter(new OutputStreamWriter(resp.getOutputStream(), "utf-8"));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            if (dataMap != null) {
                t.process(dataMap, out);
                if (out != null) {
                    out.close();
                }
            }
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //	测试类
    public static void main(String[] args) {
        //	word hd= new word();
        //	hd.createDoc();

    }

}
