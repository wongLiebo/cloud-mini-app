package com.mini.cloud.common.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.ServiceSignature;
import com.aliyun.oss.common.comm.RequestMessage;
import com.aliyun.oss.common.utils.DateUtil;
import com.aliyun.oss.internal.OSSHeaders;
import com.aliyun.oss.internal.SignUtils;
import com.aliyun.oss.model.PutObjectResult;
import com.mini.cloud.common.constant.BaseConstant;
import com.mini.cloud.common.dto.OssSignDto;
import com.mini.cloud.common.properties.OssProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * oss工具类
 */
@Component
public class OssUtils {



	private Logger logger = LoggerFactory.getLogger(OssUtils.class);

	@Autowired
	private OssProperties ossProperties;

	/**
	 *
	 * @param objectPath		存储路径
	 * @param objectName		文件名称
	 * @param theSuffix			文件后缀 eg: .jpg  .png  .video
	 * @param inputStream		上传字节流
	 * @return  String 			唯一MD5数字签名
	 * @throws OSSException
	 */
	public String pushFileToOSS( String objectPath,String objectName,String theSuffix,InputStream inputStream)
			throws OSSException {
		OSS ossClient = null;
		try{
			logger.info("===================oss上传路径及文件名:{}===================",objectName);
			// 创建OSSClient实例。
			ossClient = new OSSClientBuilder().build(ossProperties.getEndpoint(), ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret());
			logger.info("创建OSSClient实例创建成功");
			// 上传文件流。
			PutObjectResult result = ossClient.putObject(ossProperties.getBucketName(), objectPath+objectName, inputStream);
			logger.info("上传文件成功");
			// 关闭OSSClient。
			ossClient.shutdown();
			return result.getETag();
		}catch (OSSException oss){
			logger.error("文件上传失败----->[{0}]",oss.getCause());
		}finally {
			if(ossClient!=null){
				ossClient.shutdown();
			}
		}
		return null;
	}


	/**
	 *
	 * @param objectPath		存储路径
	 * @param objectName		文件名称
	 * @param theSuffix			文件后缀 eg: .jpg  .png  .video
	 * @param file				待上传文件
	 * @return  String 			唯一MD5数字签名
	 * @throws OSSException
	 */
	public String pushFileToOSS(String objectPath,String objectName,String theSuffix,File file)
			throws OSSException {
		OSS ossClient = null;
		try {
			logger.info("===================oss上传路径及文件名:{}===================",objectName);
			// 创建OSSClient实例。
			ossClient = new OSSClientBuilder().build(ossProperties.getEndpoint(), ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret());
			// 上传文件流
			if(objectName==null){
				objectName = UUID.randomUUID().toString().replace("-","");
			}
			PutObjectResult result = ossClient.putObject(ossProperties.getBucketName(), objectName, file);
			// 关闭OSSClient。
			ossClient.shutdown();
			result.getETag();
		}catch(OSSException oss){
			logger.error("文件上传失败----->[{0}]",oss.getCause());
		} finally {
			if(ossClient!=null){
				ossClient.shutdown();
			}
		}
		return null;
	}


	public OssSignDto ossSign(String contentLengeh,String contentType,String resourcePath){
		final String realHost = ossProperties.getBucketName()+ BaseConstant.BaseSymbol.POINT.getSymbol()+ossProperties.getEndpoint();
		final String realResource = BaseConstant.BaseSymbol.SLASH.getSymbol()+ossProperties.getBucketName()+ BaseConstant.BaseSymbol.SLASH.getSymbol()+resourcePath;
		final String realProtocol = BaseConstant.BaseProtocol.HTTP.getProtocol()+ realHost;
		logger.info("------入参-----[realHost----------------->[{}],realResource------------------------->[{}],realProtocol----------------->[{}]]",realHost,realResource,realProtocol);
		String date =  DateUtil.formatRfc822Date(new Date());
		Map<String, String> headers = new HashMap<>();
		headers.put(OSSHeaders.DATE,date);
		headers.put(OSSHeaders.CONTENT_LENGTH,contentLengeh);
		headers.put(OSSHeaders.CONTENT_TYPE,contentType);

		RequestMessage requestMessage = new RequestMessage(realHost, realResource);
		requestMessage.setHeaders(headers);
		String canonicalString = SignUtils.buildCanonicalString(BaseConstant.BaseRequestMethod.PUT.getMethod(), realResource, requestMessage, null);
		String sign = SignUtils.composeRequestAuthorization(ossProperties.getAccessKeyId(), ServiceSignature.create().computeSignature(ossProperties.getAccessKeySecret(), canonicalString));
		OssSignDto signDto = new OssSignDto();
		signDto.setDate(date);
		signDto.setOssSign(sign);
		signDto.setContentLength(contentLengeh);
		signDto.setContentType(contentType);
		signDto.setResource(resourcePath);
		signDto.setRealUrl(realProtocol+BaseConstant.BaseSymbol.SLASH.getSymbol()+resourcePath);
		logger.info("oss签名后的参数--------------------->[{}]",signDto);
		return signDto;
	}



	
}
