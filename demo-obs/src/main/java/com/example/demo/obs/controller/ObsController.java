package com.example.demo.obs.controller;

import com.example.demo.obs.template.ObsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * ObsController
 * TODO
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @date 2021/2/5
 */
@RestController
public class ObsController {

    @Autowired
    private ObsTemplate obsTemplate;

    @RequestMapping("/init")
    public String init() throws IOException {
        String bucketName = "test-bucket-1612685396148";
        String objectKey = "20210226105848";
        //String objectKey = String.valueOf(Instant.now().toEpochMilli());

        /*
        System.out.println("获取桶列表1");
        System.out.println(obsTemplate.getBuckets());

        System.out.println("创建桶");
        System.out.println(obsTemplate.createBucket("bucket-20210226104837", AccessControlList.REST_CANNED_PUBLIC_READ_DELIVERED));

        System.out.println("获取桶列表2");
        System.out.println(obsTemplate.getBuckets());

        System.out.println("删除桶");
        System.out.println(obsTemplate.deleteBucket("bucket-20210226104837"));

        System.out.println("获取桶列表3");
        System.out.println(obsTemplate.getBuckets());

        System.out.println("获取桶中多个对象1");
        ObjectListing objects = obsTemplate.getObjects(bucketName);
        System.out.println(objects);

        System.out.println("获取桶中多个对象2");
        ObjectListing objects2 = obsTemplate.getObjects(bucketName, 2);
        System.out.println(objects2);

        System.out.println("获取桶中多个对象3");
        ObjectListing objects3 = obsTemplate.getObjects(objects2);
        System.out.println(objects3);
        */

        //System.out.println("上传文本");
        //PutObjectResult putObjectResult = obsTemplate.uploadContent(bucketName, Instant.now().toEpochMilli() + ".txt", "这是文本内容，哈哈哈哈");
        //System.out.println(putObjectResult);

        //System.out.println("上传文本");
        //PutObjectResult putObjectResult = obsTemplate.uploadContent(bucketName, "1614311329763.txt", "最新的内容，准备覆盖之前的内容v1", true);
        //System.out.println(putObjectResult);

        //System.out.println("获取文本");
        //String content = obsTemplate.getContent(bucketName, "1614311329763.txt");
        //System.out.println(content);

//        System.out.println("上传文件");
//        PutObjectResult putObjectResult = obsTemplate.uploadFile(bucketName, "1614311329763.java", "/Users/apple/Desktop/BucketObjectOperation.java");
//        System.out.println(putObjectResult);

//        System.out.println("上传对象-流");
//        InputStream inputStream = new FileInputStream("/Users/apple/Desktop/112233.md");
//        PutObjectResult putObjectResult = obsTemplate.uploadObject(bucketName, "20210228144444.md", inputStream);
//        inputStream.close();
//        System.out.println(putObjectResult);

//        System.out.println("上传对象-文件");
//        File file = new File("/Users/apple/Desktop/temp.graphqls");
//        PutObjectResult putObjectResult = obsTemplate.uploadObject(bucketName, "20210228145302.graphqls", file, true);
//        System.out.println(putObjectResult);

//        System.out.println("获取-文件");
//        boolean file = obsTemplate.getFile(bucketName, "20210228145302.graphqls", "/Users/apple/Desktop/999999.graphqls");
//        System.out.println(file);

//        System.out.println("获取桶中单个对象1");
//        ObsObject object1 = obsTemplate.getObject(bucketName, "20210228145302.graphqls");
//        System.out.println(object1);

//        System.out.println("获取桶中单个对象2");
//        String objectPreview = obsTemplate.getObjectPreview(bucketName, "20210228144444.md");
//        System.out.println(objectPreview);

//        System.out.println("删除桶对象");
//        boolean b = obsTemplate.deleteObject(bucketName, "");
//        System.out.println(b);

        // 删除桶
        boolean b = obsTemplate.deleteBucket(bucketName);
        System.out.println(b);

        return "init";
    }

    private String inputStreamToStr(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }
        String str = outputStream.toString(StandardCharsets.UTF_8.name());
        inputStream.close();
        outputStream.close();
        return str;
    }

    private void inputStreamToFile(InputStream inputStream, String destination) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(destination);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = inputStream.read(bytes)) != -1) {
            fileOutputStream.write(bytes, 0, length);
            fileOutputStream.flush();
        }
        inputStream.close();
        fileOutputStream.close();
    }

}
