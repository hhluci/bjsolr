package com.solr.search.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.solr.search.entity.RequestCode;
import com.solr.search.service.solrservice.Solr;
import com.solr.search.service.uploadService.UploadFile;

/**
 * @Author: search
 * @ProjectName: solr
 * @Package: com.solr.search.controller
 * @Description: 文件上传
 * @Date: Created in 20:45 2018/8/13
 */
@Controller
@RequestMapping(value = "/upload")
public class UploadController {

	 @Resource
	    private UploadFile uploadFile;
	    @Resource
	    private Solr solr;

    @Autowired
    public UploadController(UploadFile uploadFile) {
        this.uploadFile = uploadFile;
    }

    /**
     * 单文件上传页面
     * @return
     */
    @RequestMapping(value = "/one/view.do")
    public ModelAndView oneUploadView(){
        return new ModelAndView("upload");
    }

    /**
     * 多文件上传视图
     * @return
     */
    @RequestMapping(value = "/more/view.do")
    public ModelAndView moreUploadView(){
        return new ModelAndView("uploadmore");
    }

    /**
     * 单文件上传视图控制
     * @param file
     * @return
     */
    @RequestMapping(value = "/one.do")
    public ModelAndView uploadOne(MultipartFile file){
        if (file != null){
            if (uploadFile.uploadOne(file)){
            	try {
                    solr.commitSolr();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return new ModelAndView("success");
            }
        }
        return new ModelAndView("error");
    }

    /**
     * 多文件上传
     * @param file
     * @return
     */
    @RequestMapping(value = "/more.do")
    public ModelAndView uploadOne(MultipartHttpServletRequest file){
        if (file != null){
            if (uploadFile.uploadMore(file)){
            	try {
                    solr.commitSolr();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            	 return new ModelAndView("success");
            }
        }
        return new ModelAndView("error");
    }

}
