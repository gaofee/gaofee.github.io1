package com.gaofei.controller;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class MyFileUtils {

	//第一个参数是下载的文件地址，第二个参数是新文件名
	public static ResponseEntity<byte[]> download(File file,String fileNname) throws IOException {

		//设置httpHeaders,使浏览器响应下载
		HttpHeaders headers = new HttpHeaders();
		//告诉浏览器执行下载的操作，“attachment”告诉了浏览器进行下载,下载的文件 文件名为 downloadFileName
		String attName = new String(fileNname.getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题

		 headers.setContentDispositionFormData("attachment", attName);
		//header("Content-Disposition:attachment;filename=测试.xlsx");//8

		//设置响应方式为二进制，以二进制流传输
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
		//window.location="http://localhost:8787/hetong/xiazai?id="+1; 返回的下载的地址
	}

	public static ResponseEntity<byte[]> download(byte[] bytes,String fileNname) throws IOException {

		//设置httpHeaders,使浏览器响应下载
		HttpHeaders headers = new HttpHeaders();
		//告诉浏览器执行下载的操作，“attachment”告诉了浏览器进行下载,下载的文件 文件名为 downloadFileName
		String attName = new String(fileNname.getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题

		 headers.setContentDispositionFormData("attachment", attName);

		//设置响应方式为二进制，以二进制流传输
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.CREATED);
	}

}
