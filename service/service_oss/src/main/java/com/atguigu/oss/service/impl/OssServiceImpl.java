package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.atguigu.commonutils.ResultCode;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertiesUtil;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl  implements OssService {

    @Override
    public String uploadFileAvatar(MultipartFile file) {
        String endpoint = ConstantPropertiesUtil.END_POINT;
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;

        // 上传文件流。
        InputStream inputStream = null;
        String uploadUrl = null;
        //判断oss实例是否存在：如果不存在则创建，如果存在则获取
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            if (!ossClient.doesBucketExist(bucketName)) {
                //创建bucket
                ossClient.createBucket(bucketName);
                //设置oss实例的访问权限：公共读
                ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            }
            inputStream = file.getInputStream();
            //获取文件名称
            String filename = file.getOriginalFilename();
            filename = UUID.randomUUID().toString().replaceAll("-", "") + filename;
            String dateTime = new DateTime().toString("yyyy/MM/dd");
            filename = dateTime + "/" + filename;
            //参数一：Bucker名称  参数二：上传到oss的文件路径及名称aa/bb.jpg  参数三：文件输入流
            ossClient.putObject(bucketName, filename, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();

            //返回图片路径  https://edu0919.oss-cn-beijing.aliyuncs.com/363fc6.jpg
            uploadUrl = "https://" + bucketName + "." + endpoint + "/" + filename;
        } catch (IOException e) {
            throw new GuliException(ResultCode.ERROR, e.getMessage());
        }
        return uploadUrl;
    }

}
