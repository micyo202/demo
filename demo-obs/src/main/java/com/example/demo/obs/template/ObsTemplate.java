package com.example.demo.obs.template;

import com.obs.services.ObsClient;
import com.obs.services.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * ObsTemplate
 * OBS 模板方法类
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @date 2021/2/5
 * @link https://support.huaweicloud.com/sdk-java-devg-obs/obs_21_0107.html
 */
@ConditionalOnProperty(prefix = "obs", name = "end-point")
@Component
@Slf4j
public class ObsTemplate {

    @Autowired
    private ObsClient obsClient;

    /**
     * 获取桶列表
     */
    public List<ObsBucket> getBuckets() {
        ListBucketsRequest request = new ListBucketsRequest();
        request.setQueryLocation(true);
        return obsClient.listBuckets(request);
    }

    /**
     * 创建桶
     *
     * @param bucketName        桶名称
     * @param accessControlList 策略（https://support.huaweicloud.com/sdk-java-devg-obs/obs_21_0406.html）
     *                          <p>
     *                          私有读写
     *                          AccessControlList.REST_CANNED_PRIVATE
     *                          <p>
     *                          公共读
     *                          AccessControlList.REST_CANNED_PUBLIC_READ
     *                          <p>
     *                          公共读写
     *                          AccessControlList.REST_CANNED_PUBLIC_READ_WRITE
     *                          <p>
     *                          桶公共读，桶内对象公共读
     *                          AccessControlList.REST_CANNED_PUBLIC_READ_DELIVERED
     *                          <p>
     *                          桶公共读写，桶内对象公共读写
     *                          AccessControlList.REST_CANNED_PUBLIC_READ_WRITE_DELIVERED
     */
    public boolean createBucket(String bucketName, AccessControlList accessControlList) {
        if (StringUtils.isBlank(bucketName) || ObjectUtils.isEmpty(accessControlList)) {
            log.error("参数传递不正确");
            return false;
        }
        boolean exists = obsClient.headBucket(bucketName);
        if (exists) {
            log.error("桶名：'" + bucketName + "'已存在，不予创建");
            return false;
        }
        ObsBucket obsBucket = new ObsBucket();
        obsBucket.setBucketName(bucketName);
        // AccessControlList.REST_CANNED_PRIVATE 私有读写
        obsBucket.setAcl(accessControlList);
        ObsBucket bucket = obsClient.createBucket(obsBucket);
        return 200 == bucket.getStatusCode() ? true : false;
    }

    /**
     * 删除桶
     *
     * @param bucketName 桶名称
     */
    public boolean deleteBucket(String bucketName) {
        if (StringUtils.isBlank(bucketName)) {
            log.error("参数传递不正确");
            return false;
        }
        boolean exists = obsClient.headBucket(bucketName);
        if (!exists) {
            log.error("桶名称'" + bucketName + "'不存在");
            return false;
        }
        try {
            HeaderResponse response = obsClient.deleteBucket(bucketName);
            return 204 == response.getStatusCode() ? true : false;
        } catch (Exception e) {
            log.error("桶名称'" + bucketName + "'删除失败，请检查桶中数据是否已清空", e);
        }
        return false;
    }

    /**
     * 获取桶中对象（返回1000条）
     *
     * @param bucketName 桶名称
     */
    public ObjectListing getObjects(String bucketName) {
        if (StringUtils.isBlank(bucketName)) {
            log.error("参数传递不正确");
            return null;
        }
        return obsClient.listObjects(bucketName);
    }

    /**
     * 获取桶中对象
     *
     * @param bucketName 桶名称
     * @param maxKeys    返回条数
     */
    public ObjectListing getObjects(String bucketName, int maxKeys) {
        if (StringUtils.isBlank(bucketName) || maxKeys < 1) {
            log.error("参数传递不正确");
            return null;
        }
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
        listObjectsRequest.setMaxKeys(maxKeys);
        return obsClient.listObjects(listObjectsRequest);
    }

    /**
     * 获取桶中对象
     *
     * @param objectListing 上个桶中对象返回结果
     */
    public ObjectListing getObjects(ObjectListing objectListing) {
        if (ObjectUtils.isEmpty(objectListing)) {
            log.error("参数传递不正确");
            return null;
        }
        if (!objectListing.isTruncated()) {
            log.warn("已经是最后一个范围了，没有下一个范围的对象数据，返回当前对象");
            return objectListing;
        }
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(objectListing.getBucketName());
        listObjectsRequest.setMaxKeys(objectListing.getMaxKeys());
        listObjectsRequest.setMarker(objectListing.getNextMarker());
        return obsClient.listObjects(listObjectsRequest);
    }

    /**
     * 获取桶中对象
     *
     * @param bucketName 桶名称
     * @param objectKey  对象主键
     */
    public ObsObject getObject(String bucketName, String objectKey) {
        if (StringUtils.isAnyBlank(bucketName, objectKey)) {
            log.error("参数传递不正确");
            return null;
        }
        //boolean objectExist = obsClient.doesObjectExist(bucketName, objectKey);
        boolean objectExist = this.objectExist(bucketName, objectKey);
        if (!objectExist) {
            log.error("桶名'" + bucketName + "'，主键'" + objectKey + "'的对象不存在");
            return null;
        }
        return obsClient.getObject(bucketName, objectKey);
    }

    /**
     * 授权访问对象（生成带签名的 URL）默认有效时间5分钟
     *
     * @param bucketName 桶名称
     * @param objectKey  对象主键
     */
    public String getObjectPreview(String bucketName, String objectKey) {
        return this.getObjectPreview(bucketName, objectKey, 300);
    }

    /**
     * 授权访问对象（生成带签名的 URL）
     *
     * @param bucketName 桶名称
     * @param objectKey  对象主键
     * @param expire     URL 过期时间（单位：秒）
     */
    public String getObjectPreview(String bucketName, String objectKey, int expire) {
        if (StringUtils.isAnyBlank(bucketName, objectKey) || expire < 1) {
            log.error("参数传递不正确");
            return null;
        }
        TemporarySignatureRequest request = new TemporarySignatureRequest(HttpMethodEnum.GET, expire);
        request.setBucketName(bucketName);
        request.setObjectKey(objectKey);
        TemporarySignatureResponse response = obsClient.createTemporarySignature(request);
        return response.getSignedUrl();
    }

    /**
     * 获取文本对象
     *
     * @param bucketName 桶名称
     * @param objectKey  对象主键
     */
    public String getContent(String bucketName, String objectKey) {
        ObsObject obsObject = this.getObject(bucketName, objectKey);
        InputStream inputStream = obsObject.getObjectContent();
        return this.inputStreamToStr(inputStream);
    }

    /**
     * 获取文件对象
     *
     * @param bucketName  桶名称
     * @param objectKey   对象主键
     * @param destination 目标文件路径
     */
    public boolean getFile(String bucketName, String objectKey, String destination) {
        return this.getFile(bucketName, objectKey, destination, false);
    }

    /**
     * 获取文件对象
     *
     * @param bucketName  桶名称
     * @param objectKey   对象主键
     * @param destination 目标文件路径
     * @param override    是否覆盖
     */
    public boolean getFile(String bucketName, String objectKey, String destination, boolean override) {
        ObsObject obsObject = this.getObject(bucketName, objectKey);
        InputStream inputStream = obsObject.getObjectContent();
        return this.inputStreamToFile(inputStream, destination, override);
    }

    /**
     * 上传文本
     *
     * @param bucketName 桶名称
     * @param objectKey  对象主键
     * @param content    文本
     */
    public PutObjectResult uploadContent(String bucketName, String objectKey, String content) {
        return this.uploadContent(bucketName, objectKey, content, false);
    }

    /**
     * 上传文本
     *
     * @param bucketName 桶名称
     * @param objectKey  对象主键
     * @param content    文本
     * @param override   是否覆盖同名文件（默认：false 不覆盖）
     */
    public PutObjectResult uploadContent(String bucketName, String objectKey, String content, boolean override) {
        if (StringUtils.isBlank(content)) {
            log.error("上传文本内容不能为空");
            return null;
        }
        InputStream inputStream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
        PutObjectResult putObjectResult = this.uploadObject(bucketName, objectKey, inputStream, override);
        try {
            inputStream.close();
        } catch (IOException e) {
            log.error("流关闭失败", e);
        }
        return putObjectResult;
    }

    /**
     * 上传文件
     *
     * @param bucketName 桶名称
     * @param objectKey  对象主键
     * @param filePath   文件路径
     */
    public PutObjectResult uploadFile(String bucketName, String objectKey, String filePath) {
        return this.uploadFile(bucketName, objectKey, filePath, false);
    }

    /**
     * 上传文件
     *
     * @param bucketName 桶名称
     * @param objectKey  对象主键
     * @param filePath   文件路径
     * @param override   是否覆盖
     */
    public PutObjectResult uploadFile(String bucketName, String objectKey, String filePath, boolean override) {
        File file = new File(filePath);
        if (file.isDirectory()) {
            log.error("文件路径'" + filePath + "'是文件夹");
            return null;
        }
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            log.error("文件路径'" + filePath + "'不存在", e);
            return null;
        }
        PutObjectResult putObjectResult = this.uploadObject(bucketName, objectKey, inputStream, override);
        try {
            inputStream.close();
        } catch (IOException e) {
            log.error("流关闭失败", e);
        }
        return putObjectResult;
    }

    /**
     * 上传对象 - 流
     *
     * @param bucketName 桶名称
     * @param objectKey  对象主键
     * @param input      输入流
     */
    public PutObjectResult uploadObject(String bucketName, String objectKey, InputStream input) {
        return this.uploadObject(bucketName, objectKey, input, null, false);
    }

    /**
     * 上传对象 - 流
     *
     * @param bucketName 桶名称
     * @param objectKey  对象主键
     * @param input      输入流
     * @param override   是否覆盖
     */
    public PutObjectResult uploadObject(String bucketName, String objectKey, InputStream input, boolean override) {
        return this.uploadObject(bucketName, objectKey, input, null, override);
    }

    /**
     * 上传对象 - 流
     *
     * @param bucketName 桶名称
     * @param objectKey  对象主键
     * @param input      输入流
     * @param metadata   元数据
     */
    public PutObjectResult uploadObject(String bucketName, String objectKey, InputStream input, ObjectMetadata metadata) {
        return this.uploadObject(bucketName, objectKey, input, metadata, false);
    }

    /**
     * 上传对象 - 流
     *
     * @param bucketName  桶名称
     * @param objectKey   对象主键
     * @param inputStream 输入流
     * @param metadata    元数据
     * @param override    是否覆盖同名文件（默认：false 不覆盖）
     */
    public PutObjectResult uploadObject(String bucketName, String objectKey, InputStream inputStream, ObjectMetadata metadata, boolean override) {
        if (StringUtils.isAnyBlank(bucketName, objectKey) || ObjectUtils.isEmpty(inputStream)) {
            log.error("上传对象（流）参数传递不正确");
            return null;
        }
        //boolean objectExist = obsClient.doesObjectExist(bucketName, objectKey);
        boolean objectExist = this.objectExist(bucketName, objectKey);
        if (objectExist) {
            if (override) {
                log.warn("桶名：'" + bucketName + "'，主键：'" + objectKey + "'的对象已经存在，覆盖上传");
            } else {
                log.error("桶名：'" + bucketName + "'，主键：'" + objectKey + "'的对象已经存在，不予上传");
                return null;
            }
        }
        PutObjectResult result = obsClient.putObject(bucketName, objectKey, inputStream, metadata);
        return result;
    }

    /**
     * 上传对象 - 文件
     *
     * @param bucketName 桶名称
     * @param objectKey  对象主键
     * @param file       文件
     */
    public PutObjectResult uploadObject(String bucketName, String objectKey, File file) {
        return this.uploadObject(bucketName, objectKey, file, null, false);
    }

    /**
     * 上传对象 - 文件
     *
     * @param bucketName 桶名称
     * @param objectKey  对象主键
     * @param file       文件
     * @param override   是否覆盖同名文件（默认：false 不覆盖）
     */
    public PutObjectResult uploadObject(String bucketName, String objectKey, File file, boolean override) {
        return this.uploadObject(bucketName, objectKey, file, null, override);
    }

    /**
     * 上传对象 - 文件
     *
     * @param bucketName 桶名称
     * @param objectKey  对象主键
     * @param file       文件
     * @param metadata   元数据
     */
    public PutObjectResult uploadObject(String bucketName, String objectKey, File file, ObjectMetadata metadata) {
        return this.uploadObject(bucketName, objectKey, file, metadata, false);
    }

    /**
     * 上传对象 - 文件
     *
     * @param bucketName 桶名称
     * @param objectKey  对象主键
     * @param file       文件
     * @param metadata   元数据
     * @param override   是否覆盖同名文件（默认：false 不覆盖）
     */
    public PutObjectResult uploadObject(String bucketName, String objectKey, File file, ObjectMetadata metadata, boolean override) {
        if (StringUtils.isAnyBlank(bucketName, objectKey) || ObjectUtils.isEmpty(file)) {
            log.error("上传对象（文件）参数传递不正确");
            return null;
        }
        //boolean objectExist = obsClient.doesObjectExist(bucketName, objectKey);
        boolean objectExist = this.objectExist(bucketName, objectKey);
        if (objectExist) {
            if (override) {
                log.warn("桶名：'" + bucketName + "'，主键：'" + objectKey + "'的对象已经存在，覆盖上传");
            } else {
                log.error("桶名：'" + bucketName + "'，主键：'" + objectKey + "'的对象已经存在，不予上传");
                return null;
            }
        }
        return obsClient.putObject(bucketName, objectKey, file, metadata);
    }

    /**
     * 删除桶中对象
     *
     * @param bucketName 桶名称
     * @param objectKey  对象主键
     */
    public boolean deleteObject(String bucketName, String objectKey) {
        //boolean objectExist = obsClient.doesObjectExist(bucketName, objectKey);
        boolean objectExist = this.objectExist(bucketName, objectKey);
        if (!objectExist) {
            log.error("桶名：'" + bucketName + "'，主键：'" + objectKey + "'的对象不存在");
            return false;
        }
        obsClient.deleteObject(bucketName, objectKey);
        //DeleteObjectResult deleteObjectResult = obsClient.deleteObject(bucketName, objectKey);
        //return deleteObjectResult.isDeleteMarker();
        //boolean isDelete = obsClient.doesObjectExist(bucketName, objectKey);
        boolean isDelete = this.objectExist(bucketName, objectKey);
        return !isDelete;
    }

    /**
     * 流转为文本内容
     *
     * @param inputStream 输入流
     */
    private String inputStreamToStr(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        try {
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            return outputStream.toString(StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            log.error("获取文本内容流信息失败", e);
        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                log.error("流关闭失败", e);
            }
        }
        return null;
    }

    /**
     * 流转为目标文件
     *
     * @param inputStream 输入流
     * @param destination 目标文件路径
     */
    private boolean inputStreamToFile(InputStream inputStream, String destination, boolean override) {
        File file = new File(destination);
        if (file.isDirectory()) {
            log.error("目标文件路径'" + destination + "'是文件夹");
            return false;
        }
        if (file.exists()) {
            if (override) {
                log.warn("目标文件路径：'" + destination + "'已存在，覆盖下载");
            } else {
                log.error("目标文件路径：'" + destination + "'已存在，不予下载");
                return false;
            }
        }
        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            log.error("目标文件路径'" + destination + "'不存在", e);
            return false;
        }
        byte[] bytes = new byte[1024];
        int length;
        try {
            while ((length = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, length);
                outputStream.flush();
            }
            return true;
        } catch (IOException e) {
            log.error("获取文件内容流信息失败", e);
        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                log.error("流关闭失败", e);
            }
        }
        return false;
    }

    /**
     * 判断对象是否存在
     *
     * @param bucketName 桶名称
     * @param objectKey  对象主键
     */
    private boolean objectExist(String bucketName, String objectKey) {
        try {
            obsClient.getObject(bucketName, objectKey);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
