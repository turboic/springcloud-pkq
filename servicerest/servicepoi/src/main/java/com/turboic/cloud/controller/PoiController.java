package com.turboic.cloud.controller;
import com.turboic.cloud.util.Base64Utils;
import com.turboic.cloud.util.ExportWordUtil;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liebe
 */
@Controller
@RequestMapping("/poi")
public class PoiController {

    private static final Logger logger = LoggerFactory.getLogger(PoiController.class);


    private static final String WINDOW = "window";


    private static final String LINUX = "linux";


    /**
     * 报警分析报表下载
     */
    @RequestMapping("/word")
    public ResponseEntity<byte[]> exportReport(HttpServletResponse response, HttpServletRequest request, String base64String) {
        Map<String,String> map = new HashMap(10);
        try {
            logger.info("文件的路径为:{}",base64String);
            base64String = Base64Utils.convertImageToBase64(base64String);
            logger.error("将图片转换成base64字符串内容是:{}",base64String);
            byte[] base64Info1 = decodeBase64(base64String);
            XWPFDocument xwpfDocument = new ExportWordUtil().export(base64Info1);

            Calendar c = Calendar.getInstance();
            String fileName = "生成分析报告" + c.get(Calendar.YEAR) + to2String(String.valueOf((c.get(Calendar.MONTH) + 1)))
                    + c.get(Calendar.DAY_OF_MONTH) + c.get(Calendar.HOUR_OF_DAY) + c.get(Calendar.MILLISECOND) + ".docx";
            URL url = Base64Utils.class.getClassLoader().getResource("/");
            String classPath;
            if(url != null){
                classPath = Base64Utils.class.getClassLoader().getResource("/").getPath();
                logger.error("获取存放路径:{}",classPath);
                String os_name = System.getProperties().get("os.name").toString().toLowerCase();
                logger.error("操作系统名称:{}",os_name);

                if (os_name.indexOf(WINDOW) != -1) {
                    classPath = classPath.substring(1, classPath.indexOf("/WEB-INF/classes")) + "/upload/";
                } else if (os_name.indexOf(LINUX) != -1) {
                    classPath = FileUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
                    classPath = "/" + classPath.substring(1, classPath.indexOf("/WEB-INF/")) + "/upload/";
                }
            }
            else{
                classPath = System.getProperty("java.io.tmpdir");
            }
            logger.info("==============文件路径================" + classPath + fileName);
            FileOutputStream fos = new FileOutputStream(classPath + fileName);
            xwpfDocument.write(fos);
            fos.close();
            map.put("ret", "/upload/" + fileName);
            File file = new File(classPath + fileName);
            byte[] body = null;
            InputStream is = new FileInputStream(file);
            body = new byte[is.available()];
            is.read(body);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attchement;filename=" + file.getName());
            HttpStatus statusCode = HttpStatus.OK;
            return new ResponseEntity<byte[]>(body, headers, statusCode);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            map.put("ret", "失败");
            return null;
        }
    }


    /**
     * 解析base64，返回图片所在路径
     *
     * @param base64Info
     * @return
     */
    private byte[] decodeBase64(String base64Info) {
        if (StringUtils.isEmpty(base64Info)) {
            return null;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        if (!base64Info.contains("base64,")){
            return null;
        }
        String[] arr = base64Info.split("base64,");
        try {
            return decoder.decodeBuffer(arr[1]);
        } catch (IOException e) {
            logger.info(e.getMessage(), e);
            return null;
        }
    }

    private String to2String(String str) {
        if (str.length() > 2) {
            str = str.substring(0, 2);
        } else {
            str = "0" + str;
        }
        return str;
    }
}