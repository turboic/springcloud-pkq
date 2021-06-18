package com.turboic.cloud;

import com.github.tobato.fastdfs.domain.upload.FastImageFile;
import com.turboic.cloud.client.FastDfsClient;
import com.turboic.cloud.util.FastJsonUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {FastDfsClientApplication.class})
public class AppTest {
    private static final Logger log = LoggerFactory.getLogger(FastDfsClient.class);
    @Autowired
    private FastDfsClient client;

    @Test
    public void springUtilsTest() {
        while(true){
            File file = new File("C:\\Users\\liebe\\Pictures\\test");
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            File [] fs = file.listFiles();
            for( File f : fs){
                upload(f);
                f.delete();
            }
        }
    }

    public String upload(File file){
        String uploadPath = null;
        try {
            uploadPath = client.upload(file);
        } catch (IOException e) {
            e.printStackTrace();
            log.info("上传异常={}", e.getCause());
        }
        log.error("上传路径={}", uploadPath);
        return uploadPath;
    }


    @Test
    public void springUtilsTestDelete() {
        String groupName = "group1";
        String path = "M00/00/00/CgoKB2C8lFqAewLHAAW2x0M4D3k896.jpg";
        String fileInfo = FastJsonUtils.objectToJson(client.queryFileInfo(groupName, path));
        log.error("文件信息={}", fileInfo);
        client.deleteFile(groupName, path);
        log.error("删除文件完成groupName={},path={}", groupName, path);
    }


    @Test
    public void uploadImage() {
        String imagePath = "C:\\Users\\liebe\\Pictures\\1212.jpg";
        File file = new File(imagePath);
        ;
        FastImageFile imageFile = null;
        try {
            imageFile = new FastImageFile.Builder().withMetaData("image", "美女")
                    .toGroup("group1").withThumbImage(300, 300).
                            withFile(FileUtils.openInputStream(file), file.length(), FilenameUtils.getExtension(file.getName())).build();
        } catch (IOException e) {
            log.error("异常={}", e);
            log.error("异常2={}", e.getMessage());
            log.error("异常3={}", e.getCause());
        }
        String fileInfo = client.uploadImage(imageFile);
        log.error("上传uploadImage路径={}", fileInfo);
    }


    @Test
    public void getStorageNodeInfo() {
        String fileInfo = client.getStorageNodeInfo();
        log.error("存储信息={}", fileInfo);
        /***
         * 存储信息=存储节点:storageNode={"groupName":"group1","inetSocketAddress":
         * {"address":"10.10.10.7","port":23000},"ip":"10.10.10.7","port":23000,
         * "storeIndex":0}组状态节点:groupStates=[{"activeCount":1,"currentTrunkFileId":0,
         * "currentWriteServer":0,"freeMB":40414,"groupName":"group1","storageCount":1,
         * "storageHttpPort":80,"storagePort":23000,"storePathCount":1,
         * d"subdirCountPerPath":256,"totalMB":51175,"trunkFreeMB":0}]
         */
    }

    @Test
    public void download() {
        while(true){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String fileName = "2222.jpg";
            String fileUrl  = upload(new File(fileName));
            String download = "C:\\Users\\liebe\\Pictures\\"+System.currentTimeMillis()+".jpg";
            File file = client.download(fileUrl,download);
            log.error("文件信息={}", FastJsonUtils.objectToJson(file));
        }

    }

}
