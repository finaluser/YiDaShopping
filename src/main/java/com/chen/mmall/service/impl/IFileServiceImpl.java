package com.chen.mmall.service.impl;

import com.google.common.collect.Lists;
import com.chen.mmall.service.IFileService;
import com.chen.mmall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @program: mmall
 * @description:
 * @author: Mr.Wang
 * @create: 2020-02-15 18:06
 **/
@Service(value = "iFileService")
public class IFileServiceImpl implements IFileService {
    private Logger logger = LoggerFactory.getLogger(IFileServiceImpl.class);

    public String upload(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(",") + 1);
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
        logger.info("开始上传文件,上传文件的名字:{},上传的路径:{},新文件名:{}", fileName, path, uploadFileName);
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path, uploadFileName);
        try {
            file.transferTo(targetFile);
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            targetFile.delete();
        } catch (IOException e) {
            logger.error("上传文件错误" + e);
            return null;
        }
        return targetFile.getName();
    }
}
