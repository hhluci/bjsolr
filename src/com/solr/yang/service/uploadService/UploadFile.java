package com.solr.yang.service.uploadService;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * @Author: yang
 * @ProjectName: solr
 * @Package: com.solr.yang.service
 * @Description: 文件上传接口
 * @Date: Created in 20:53 2018/8/13
 */
public interface UploadFile {

    /**
     * 上传单个文件
     * @param file
     */
    boolean uploadOne(MultipartFile file);

    /**
     * 多文件上传
     * @param fileList
     */
    boolean uploadMore(MultipartHttpServletRequest fileList);

}
