package com.turboic.cloud.client;
import com.github.tobato.fastdfs.domain.conn.FdfsWebServer;
import com.github.tobato.fastdfs.domain.fdfs.FileInfo;
import com.github.tobato.fastdfs.domain.fdfs.GroupState;
import com.github.tobato.fastdfs.domain.fdfs.StorageNode;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadCallback;
import com.github.tobato.fastdfs.domain.upload.FastImageFile;
import com.github.tobato.fastdfs.service.AppendFileStorageClient;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.github.tobato.fastdfs.service.TrackerClient;
import com.turboic.cloud.util.FastJsonUtils;
import org.apache.commons.io.FileSystemUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class FastDfsClient {
    private static final Logger log = LoggerFactory.getLogger(FastDfsClient.class);
    private final AppendFileStorageClient appendFileStorageClient;

    private final FastFileStorageClient fastFileStorageClient;

    private final TrackerClient trackerClient;

    private final FdfsWebServer fdfsWebServer;

    public FastDfsClient(AppendFileStorageClient appendFileStorageClient, FastFileStorageClient fastFileStorageClient, TrackerClient trackerClient, FdfsWebServer fdfsWebServer) {
        this.appendFileStorageClient = appendFileStorageClient;
        this.fastFileStorageClient = fastFileStorageClient;
        this.trackerClient = trackerClient;
        this.fdfsWebServer = fdfsWebServer;
    }


    public String getStorageNodeInfo(){
        StringBuffer stringBuffer = new StringBuffer();
        StorageNode storeStorage = trackerClient.getStoreStorage();
        stringBuffer.append("存储节点:storageNode=").append(FastJsonUtils.objectToJson(storeStorage));
        List<GroupState> groupStates = trackerClient.listGroups();
        stringBuffer.append("组状态节点:groupStates=").append(FastJsonUtils.objectToJson(groupStates));
        return stringBuffer.toString();
    }

    public String upload(MultipartFile file) throws IOException {
        StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()), null);
        return convertStorePath(storePath, file.getOriginalFilename(), null);
    }


    public String upload(File file) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        StorePath storePath = fastFileStorageClient.uploadFile(inputStream, file.length(), FilenameUtils.getExtension(file.getName()), null);
        return convertStorePath(storePath, file.getName(), inputStream);
    }


    private String convertStorePath(StorePath storePath, String fileName, InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String nginxAccessWebUrl = getNginxAccessWebUrl(storePath);
        log.info("upload调用,文件名={},返回上传路径={},Web访问地址={}", fileName, storePath.getFullPath(), nginxAccessWebUrl);
        return nginxAccessWebUrl;
    }


    private String getNginxAccessWebUrl(StorePath storePath) {
        String baseUrl = fdfsWebServer.getWebServerUrl();
        if (!baseUrl.startsWith("http")) {
            baseUrl = "http://" + baseUrl;
        }
        if (!baseUrl.endsWith("/")) {
            baseUrl = baseUrl + "/";
        }
        return baseUrl + storePath.getFullPath();
    }


    public File download(String fileUrl,String downloadPath) {
        Pattern p = Pattern.compile("((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)\\.(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)\\.(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)\\.(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d))");
        Matcher m = p.matcher(fileUrl);
        String ip = "";
        while (m.find()) {
            ip = m.group(1);
        }
        if(!StringUtils.isEmpty(ip)){
            fileUrl = fileUrl.replaceFirst(ip,"");
        }
        fileUrl = fileUrl.replaceFirst("http://","");
        if(fileUrl.contains(":")) {
            int index = fileUrl.indexOf("/");
            fileUrl = fileUrl.substring(index);
        }
        if(fileUrl.startsWith("/")){
            fileUrl = fileUrl.substring(1);
        }
        log.error("获取下载地址为={}",fileUrl);
        String groupName = fileUrl.substring(0, fileUrl.indexOf("/"));
        String path = fileUrl.substring(fileUrl.indexOf("/") + 1);
        File file = fastFileStorageClient.downloadFile(groupName, path, ins -> {
            File file1 = new File(downloadPath);
            if(!file1.exists()) {
                file1.createNewFile();
            }
            OutputStream outputStream = new FileOutputStream(file1);
            IOUtils.write(IOUtils.toByteArray(ins),outputStream);
            if(outputStream != null){
                outputStream.close();
            }
            return file1;
        });
        return file;
    }

    public void deleteFile(String groupName, String path) {
        fastFileStorageClient.deleteFile(groupName, path);
    }


    public FileInfo queryFileInfo(String groupName, String path) {
        return fastFileStorageClient.queryFileInfo(groupName, path);
    }

    public String uploadImage(FastImageFile fastImageFile) {
        return convertStorePath(fastFileStorageClient.uploadImage(fastImageFile), fastImageFile.getFileExtName(), null);
    }

    private String[] tipIp(String url) {
        String[] s= new String[2];
        Pattern p = Pattern.compile("((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})(\\.((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})){3}");
        Matcher m = p.matcher(url);
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer1 = new StringBuffer();
        while (m.find()) {
            stringBuffer.append(m.group(1));
            stringBuffer1.append(m.group(2));
        }
        return s;
    }
}