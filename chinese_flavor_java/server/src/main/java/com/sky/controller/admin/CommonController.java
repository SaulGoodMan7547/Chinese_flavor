package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.entity.Category;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping(value = "/admin/common")
@Slf4j
@Api(tags = "公共controller")
public class CommonController {

    @Resource
    private AliOssUtil aliOssUtil;
    @PostMapping(value = "/upload")
    public Result<String> upload(MultipartFile file){

        //阿里OSS图片存储方案
        //try {
        //    //原始文件名
        //    String originalFilename = file.getOriginalFilename();
        //    //截取原始文件名的后缀
        //    String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        //    //构造新文件名称
        //    String objectName = UUID.randomUUID().toString() + extension;
        //
        //    //文件的请求路径
        //    String filePath = aliOssUtil.upload(file.getBytes(), objectName);
        //    return Result.success(filePath);
        //} catch (IOException e) {
        //    log.error("文件上传失败：{}", e);
        //}
        //
        //return Result.error(MessageConstant.UPLOAD_FAILED);


        //本地存储方案
        String filename = file.getOriginalFilename();
        filename = UUID.randomUUID().toString() + filename;
        //方案一path
        //File path = new File("D:\\code\\project\\sky\\img\\" + filename);

        //方案二path:获取当前运行环境根目录，创建image目录，将图片放入
        String path1 = this.getClass().getResource("/").getPath();
        File file1 = new File(path1 + "/image/");
        file1.mkdir();

        File path = new File(path1 + "/image/" + filename);

        try {

            file.transferTo(path);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String filepath = "http://127.0.0.1:8080/images/" + filename;

        return Result.success(filepath);
    }
}
