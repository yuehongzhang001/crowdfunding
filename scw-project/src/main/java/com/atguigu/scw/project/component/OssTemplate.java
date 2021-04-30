package com.atguigu.scw.project.component;

import java.io.InputStream;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class OssTemplate {
	
	//TODO: 完成注册，修改accessKey
	String endpoint;
	String accessKeyId;
	String accessKeySecret;
	String bucket;

	
	public String upload(String filename,InputStream inputStream) {
		log.debug("endpoint={}",endpoint);
		log.debug("accessKeyId={}",accessKeyId);
		log.debug("accessKeySecret={}",accessKeySecret);
		log.debug("bucket={}",bucket);
		try {
			OSS ossClient = new OSSClientBuilder().build("https://"+endpoint, accessKeyId, accessKeySecret);
			ossClient.putObject(bucket, "pic/"+filename, inputStream);
			ossClient.shutdown();
			return "https://"+bucket+"."+endpoint+"/pic/"+filename;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.debug(e.getMessage());
			return null;
		}
	}
}
