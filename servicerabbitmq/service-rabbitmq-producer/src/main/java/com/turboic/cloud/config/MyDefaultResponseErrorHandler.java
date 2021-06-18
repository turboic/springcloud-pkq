package com.turboic.cloud.config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.UnknownHttpStatusCodeException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author liebe
 */
public class MyDefaultResponseErrorHandler extends DefaultResponseErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(MyDefaultResponseErrorHandler.class);

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode = null;
        try {
            statusCode = HttpStatus.resolve(response.getRawStatusCode());
        } catch (IOException e) {
            logger.error("获取状态码异常");
        }
        if (statusCode == null) {
            byte[] body = super.getResponseBody(response);
            String msg = new String(body,"utf-8");
            logger.error("msg：{}",msg);
            String message = getErrorMessage(response.getRawStatusCode(), response.getStatusText(), body, this.getCharset(response));
            logger.error("message：{}",message);
            throw new UnknownHttpStatusCodeException(message, response.getRawStatusCode(), response.getStatusText(), response.getHeaders(), body, this.getCharset(response));
        } else {
            logger.error("this.handleError(response, statusCode)");
            this.handleError(response, statusCode);
        }
    }

    private String getErrorMessage(int rawStatusCode, String statusText, @Nullable byte[] responseBody, @Nullable Charset charset) {
        String preface = rawStatusCode + " " + statusText + ": ";
        if (ObjectUtils.isEmpty(responseBody)) {
            return preface + "[no body]";
        } else {
            charset = charset == null ? StandardCharsets.UTF_8 : charset;
            int maxChars = 200;
            if (responseBody.length < maxChars * 2) {
                return preface + "[" + new String(responseBody, charset) + "]";
            } else {
                try {
                    Reader reader = new InputStreamReader(new ByteArrayInputStream(responseBody), charset);
                    CharBuffer buffer = CharBuffer.allocate(maxChars);
                    reader.read(buffer);
                    reader.close();
                    buffer.flip();
                    return preface + "[" + buffer.toString() + "... (" + responseBody.length + " bytes)]";
                } catch (IOException var9) {
                    throw new IllegalStateException(var9);
                }
            }
        }
    }
}
