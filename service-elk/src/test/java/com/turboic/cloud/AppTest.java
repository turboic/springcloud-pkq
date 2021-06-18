package com.turboic.cloud;

import com.turboic.cloud.index.Index;
import com.turboic.cloud.pojo.Clue;
import com.turboic.cloud.util.FastJsonUtils;
import com.turboic.cloud.vo.ClueVO;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ClassUtils;

import java.io.*;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

import static org.junit.Assert.assertSame;

/**
 * Unit test for simple App.
 */
public class AppTest 
{

    @Test
    public void springUtilsTest() {
        boolean reactiveStreamsPresent = ClassUtils.isPresent(
                "com.turboic.cloud.pojo.Clue", AppTest.class.getClassLoader());
        System.err.println(reactiveStreamsPresent);
        System.out.println(testFunction(2,i -> i * 2 + 1,j -> j * j));

        Function<String,String> function = Function.identity();
        String strValue = testIdentity(function);
        System.out.println(strValue);
    }

    public static int testFunction(int i, Function<Integer,Integer> function1, Function<Integer,Integer> function2) {

        return function1.compose(function2).apply(i);
    }

    public static String testIdentity(Function<String,String> function) {
        return function.apply("hello world");
    }

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        Clue c = new Clue();
        c.setId(UUID.randomUUID().toString());
        c.setActWay("极端");
        c.setAge(60);
        c.setUsername("admin");
        c.setPassword("admin");
        c.setCode("2021040111809G");
        c.setCreateTime(new Date());
        c.setUpdateTime(new Date());
        c.setCrowd("蛋壳公寓");
        c.setLevel("一级");
        c.setCreateUnit("网安八支队");
        c.setSourceUnit("公安部");
        c.setStatus("办结");
        c.setTitle("蛋壳公寓上市455天将被纽交所摘牌");
        c.setSubject("重点群体");
        c.setName("高静");
        c.setAddress("北京市东城区朝阳门内大街8号朝阳首府2楼212室");
        c.setContent("蛋壳公寓是紫梧桐(北京)资产管理有限公司旗下的高端白领公寓品牌，公司于2015年1月在北京成立，正式进入O2O租房市场，在北京、深圳开设分公司 [1]  。截至2019年1月17日，蛋壳公寓管理房间数量接近40万间 [2]  。\n" +
                "北京时间2020年1月17日晚间，长租公寓运营商蛋壳公寓成功登陆美国纽交所，股票代码“DNK”，成为2020年登陆纽交所的第一支中概股。 [3]  上市当天市值27.4亿美元，但到了2020年11月，市值已不到3亿美元。 [4] \n" +
                "2020年11月，据中新网，因拖欠房东房租与租客退款，蛋壳公寓陷讨债风波 [5]  。11月16日，北京市住建委方面向记者回复称，已经针对蛋壳公寓成立了专办小组 [6]  。12月4日，最高法副院长杨万明回应“蛋壳公寓爆雷”：有关地方和部门正在处理 [7]  。12月25日，蛋壳公寓App房源信息已经全部下架。 [8-9] \n" +
                "2021年3月15日，纽交所监管局暂停了蛋壳公寓ADS的交易；4月6日，纽约证券交易所宣布，其监管部门“纽交所监管局”工作人员已决定启动程序，将蛋壳公寓从纽交所摘牌，其美国存托股也将立即暂停交易。 [28-29]");
        //System.err.println(FastJsonUtils.objectToJson(c));

        Index index = new Index("id","name");
        c.setIndex(index);
        ClueVO clueVO = new ClueVO();
        BeanUtils.copyProperties(c,clueVO);
        //System.err.println(FastJsonUtils.objectToJson(c));

        index.setName("北京欢迎你");

        /***
         * 测试结果像是浅拷贝内
         */


        System.err.println(FastJsonUtils.objectToJson(clueVO));
        assertSame(c.getIndex().getName(),clueVO.getIndex().getName());
    }



    private void testFile() throws IOException {
        InputStream inputStream = new FileInputStream("C:\\Users\\liebe\\Pictures\\kafka接收MySQL-BINLOG消费信息.jpg");
        OutputStream outputStream = new FileOutputStream("C:\\Users\\liebe\\Pictures\\canal-example-instance实例日志.jpg");
        int length = 0;
        byte [] b = new byte[204800];
        while((length = inputStream.read(b,0,b.length))!=-1){
            outputStream.write(b,0,length);
        }
        outputStream.close();
        inputStream.close();
    }


    public static byte[] toByteArray(String hexString) {
        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {// 因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }
}
