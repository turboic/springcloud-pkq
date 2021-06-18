package com.turboic.cloud.constant;

/**
 * @author liebe
 */
public class KafkaConstant {
    public static final String default_topic = "liebe";
    public enum KafKaPersistStatus {
        PRODUCT_SEND("发送成功", "product_send"), PRODUCT_ERROR("发送失败", "product_error"),
        CONSUME_RECEIVE("接收成功", "consume_receive"), CONSUME_ERROR("接收失败", "consume_error");
        private String name;
        private String status;
        KafKaPersistStatus(String name, String status) {
            this.name = name;
            this.status = status;
        }
        public static String getName(String status) {
            for (KafKaPersistStatus kafKaPersistStatus : KafKaPersistStatus.values()) {
                if (kafKaPersistStatus.getStatus() == status) {
                    return kafKaPersistStatus.name;
                }
            }
            return null;
        }

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

}
