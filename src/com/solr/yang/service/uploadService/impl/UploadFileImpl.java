package com.solr.yang.service.uploadService.impl;

import com.solr.yang.service.uploadService.UploadFile;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * @Author: yang
 * @ProjectName: solr
 * @Package: com.solr.yang.service.uploadService.impl
 * @Description: 文件上传服务接口实现类
 * @Date: Created in 21:34 2018/8/13
 */
@Service
public class UploadFileImpl implements UploadFile {
    /**
     * 上传单个文件
     *
     * @param file
     */
    @Override
    public boolean uploadOne(MultipartFile file) {
        System.out.println("file{}" + file.getOriginalFilename());
        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File("D:\\temp",
                    System.currentTimeMillis() + file.getOriginalFilename()));
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * 多文件上传
     *
     * @param fileList
     */
    @Override
    public boolean uploadMore(MultipartHttpServletRequest fileList) {

        Iterator<String> fileNames = fileList.getFileNames();
        while (fileNames.hasNext()){
            String fileName = fileNames.next();
            MultipartFile file = fileList.getFile(fileName);
            if (!file.isEmpty()){
                System.out.println("file{}" + file.getOriginalFilename());
                try {
                    FileUtils.copyInputStreamToFile(file.getInputStream(), new File("D:\\temp",
                            System.currentTimeMillis() + file.getOriginalFilename()));
                } catch (IOException e) {
                    return false;
                }
            }
        }
        return true;
    }
}
