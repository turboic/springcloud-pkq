package com.turboic.cloud.util;

import com.turboic.cloud.config.CustomXWPFDocument;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.stream.FileImageInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DecimalFormat;


/**
 * @author liebe
 */
public class ExportWordUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExportWordUtil.class);

    /**
     * 导出word
     * @param base64Info1		报表图片数据
     * @return
     */
    public XWPFDocument export(byte[] base64Info1) {
        try {
            CustomXWPFDocument xdoc = new CustomXWPFDocument();
            // 创建页眉
            createCtp(xdoc);

            // 标题
            createTitle(xdoc);

            XWPFTable dTable = xdoc.createTable(4, 3);
            createBaseInfoTable(dTable, xdoc, "未来科技", "谢谢侬",	"1024", "生成报表201709120056251");
            // 标题一、未来科技数据统计分析
            createTitle(xdoc, "一、	未来科技数据统计分析");

            // 报表数据分析
            XWPFTable dataReportTable = xdoc.createTable(4, 2);
            createDataReportTable(dataReportTable, xdoc, base64Info1);

            return xdoc;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("生成文件失败");
        }
    }

    /**
     * 在cell 里面插入图片
     * @param xdoc
     * @param paragraph
     * @param imageByte
     */
    private void createPic(CustomXWPFDocument xdoc, XWPFParagraph paragraph, byte[] imageByte) {
        try {
            xdoc.addPictureData(imageByte, XWPFDocument.PICTURE_TYPE_JPEG);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        xdoc.createPicture(paragraph, xdoc.getAllPictures().size() - 1, 300, 200, "    ");
    }

    /***
     *  图片到byte数组
     */

    public byte[] image2byte(String path) {
        byte[] data = null;
        FileImageInputStream input = null;
        try {
            input = new FileImageInputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead = 0;
            while ((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();
            output.close();
            input.close();
        } catch (FileNotFoundException ex1) {
            ex1.printStackTrace();
        } catch (IOException ex1) {
            ex1.printStackTrace();
        }
        return data;
    }

    /**
     * 创建标题
     */
    private void createTitle(CustomXWPFDocument xdoc) {
        // 标题
        XWPFParagraph titleMes = xdoc.createParagraph();
        titleMes.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun r1 = titleMes.createRun();
        r1.setBold(true);
        r1.setFontFamily("华文仿宋");
        r1.setText("未来科技平台统计分析报告");
        r1.setFontSize(18);
        r1.setColor("333333");
        r1.setBold(true);
    }

    /**
     * 生成页眉
     */
    public void createCtp(CustomXWPFDocument document) {
        CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
        XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(document, sectPr);
        // 添加页眉
        CTP ctpHeader = CTP.Factory.newInstance();
        CTR ctrHeader = ctpHeader.addNewR();
        CTText ctHeader = ctrHeader.addNewT();
        String headerText = "我的报表我做主                                                                                         在我的地盘听我的";
        ctHeader.setStringValue(headerText);
        XWPFParagraph headerParagraph = new XWPFParagraph(ctpHeader, document);
        // 设置为左对齐
        headerParagraph.setAlignment(ParagraphAlignment.BOTH);
        XWPFParagraph[] parsHeader = new XWPFParagraph[1];
        parsHeader[0] = headerParagraph;
        try {
            policy.createHeader(XWPFHeaderFooterPolicy.DEFAULT, parsHeader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成基础信息Table
     *
     * @param table
     * @param xdoc
     */
    public void createBaseInfoTable(XWPFTable table, CustomXWPFDocument xdoc, String dateStr, String unitName,
                                    String machineNum, String reportNo) {
        String bgColor = "111111";
        CTTbl ttbl = table.getCTTbl();
        CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
        CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
        tblWidth.setW(new BigInteger("8600"));
        tblWidth.setType(STTblWidth.AUTO); // STTblWidth.AUTO 自动长度
        mergeCellsVertically(table, 0, 0, 3);
        setCellText(xdoc, getCellHight(table, 0, 0, 2400), "基 础 信 息", bgColor, 600, 14, "仿宋");
        setCellText(xdoc, getCellHight(table, 0, 1, 600), "报告周期", bgColor, 1800, 14, "仿宋");

        setCellText(xdoc, getCellHight(table, 0, 2, 600), dateStr, bgColor, 6200, 14, "仿宋");

        setCellText(xdoc, getCellHight(table, 1, 1, 600), "单位名称", bgColor, 1800, 14, "仿宋");
        if (unitName == null){
            unitName = "";
        }

        setCellText(xdoc, getCellHight(table, 1, 2, 600), unitName, bgColor, 6200, 14, "仿宋");

        setCellText(xdoc, getCellHight(table, 2, 1, 600), "主机数量", bgColor, 1800, 14, "仿宋");
        setCellText(xdoc, getCellHight(table, 2, 2, 600), machineNum, bgColor, 6200, 14, "仿宋");

        setCellText(xdoc, getCellHight(table, 3, 1, 600), "报告编号", bgColor, 1800, 14, "仿宋");
        setCellText(xdoc, getCellHight(table, 3, 2, 600), reportNo, bgColor, 6200, 14, "仿宋");
    }

    /**
     * 生成标题
     *
     * @param xdoc
     * @param titleText
     */
    public void createTitle(CustomXWPFDocument xdoc, String titleText) {
        XWPFParagraph headLine2 = xdoc.createParagraph();
        headLine2.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun runHeadLine2 = headLine2.createRun();
        runHeadLine2.setText(titleText);
        runHeadLine2.setFontSize(16);
        runHeadLine2.setFontFamily("华文仿宋");
        runHeadLine2.setBold(true);
        runHeadLine2.setColor("333333");
    }



    /**
     * 报表数据分析
     *
     * @param table
     * @param xdoc
     */
    public void createDataReportTable(XWPFTable table, CustomXWPFDocument xdoc, byte[] base64Info1) {
        String bgColor = "111111";
        CTTbl ttbl = table.getCTTbl();
        CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
        CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
        tblWidth.setW(new BigInteger("8600"));
        tblWidth.setType(STTblWidth.AUTO);
        // mergeCellsVertically(table, 0, 0, 3);

        String  str = "";
        Double sss = 0.6666666666666666;
        DecimalFormat df = new DecimalFormat("0.00");

        str = "（一）报告时间内误报率"+df.format((sss * 100))+"%";

        /*** 第一行的标题行 **/
        setCellText(xdoc, getCellHight(table, 0, 0, 1200), str, bgColor, 8600, 14, "仿宋");

        setCellText(xdoc, getCellHight(table, 1, 0, 1200), "报表数据", bgColor, 4300, 14, "仿宋");
        setCellText(xdoc, getCellHight(table, 1, 1, 1200), "报表数据", bgColor, 4300, 14, "仿宋");






        // 自动报警数量环比 报表
        if(base64Info1 == null || base64Info1.length < 100){
            setCellText(xdoc, getCellHight(table, 2, 0, 1200), "暂无数据", bgColor, 4300, 14, "仿宋");
        } else {
            setCellPic(xdoc, getCellHight(table, 2, 0, 1200), base64Info1);
            //setCellText(xdoc, getCellHight(table, 2, 0, 1200), "暂无数据", bgColor, 4300, 14, "仿宋");
        }
        // 自动报警数量同比 报表
        if(base64Info1 == null || base64Info1.length < 100){
            setCellText(xdoc, getCellHight(table, 2, 1, 1200), "暂无数据", bgColor, 4300, 14, "仿宋");
        } else {
            //setCellPic(xdoc, getCellHight(table, 2, 1, 1200), base64Info1);
            setCellText(xdoc, getCellHight(table, 2, 1, 1200), "暂无数据", bgColor, 4300, 14, "仿宋");
        }
        setCellText(xdoc, getCellHight(table, 3, 0, 1200), "数据汇总\n1:金山办公旗下主要产著作权总计分为164项和282项，" +
                "其中中国境内登记的专利共146计7项。\n2Java下载文件的几种方式", bgColor, 4300, 14, "仿宋", ParagraphAlignment.LEFT,true);
        setCellText(xdoc, getCellHight(table, 3, 1, 1200), "", bgColor, 4300, 14, "仿宋");
        //mergeCellsHorizontal(table, 1, 0, 1);
        mergeCellsHorizontal(table, 2, 1, 1);
        mergeCellsHorizontal(table, 3, 0, 1);

    }

    /**
     * 设置表格的高度
     * @param xTable
     * @param rowNomber
     * @param cellNumber
     * @param hight
     * @return
     */
    private XWPFTableCell getCellHight(XWPFTable xTable, int rowNomber, int cellNumber, int hight) {
        XWPFTableRow row = null;
        row = xTable.getRow(rowNomber);
        row.setHeight(hight);
        XWPFTableCell cell = null;
        cell = row.getCell(cellNumber);
        cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        return cell;
    }

    /**
     * 创建图片
     */
    private void setCellPic(CustomXWPFDocument xdoc, XWPFTableCell cell, byte[] imageByte) {
        createPic(xdoc, cell.addParagraph(), imageByte);
    }

    private void setCellText(CustomXWPFDocument xDocument, XWPFTableCell cell, String text, String bgcolor, int width,
                             int fontSize, String textType) {
        setCellText(xDocument, cell, text, bgcolor, width, fontSize, textType, ParagraphAlignment.CENTER);
    }

    /**
     *
     * @param xDocument
     * @param cell
     * @param text
     * @param bgcolor
     * @param width
     */
    private void setCellText(CustomXWPFDocument xDocument, XWPFTableCell cell, String text, String bgcolor, int width,
                             int fontSize, String textType, ParagraphAlignment align) {
        setCellText(xDocument, cell, text, bgcolor, width, fontSize, textType, align, false);
    }

    private void setCellText(CustomXWPFDocument xDocument, XWPFTableCell cell, String text, String bgcolor, int width,
                             int fontSize, String textType, ParagraphAlignment align, boolean isBold) {
        CTTc cttc = cell.getCTTc();
        CTTcPr cellPr = cttc.addNewTcPr();
        cellPr.addNewTcW().setW(BigInteger.valueOf(width));
        XWPFParagraph pIO = cell.addParagraph();
        if (null == align) {
            pIO.setAlignment(ParagraphAlignment.CENTER);
        } else {
            pIO.setAlignment(align);
        }
        cell.removeParagraph(0);

        if (text.contains("\n")) {
            String[] myStrings = text.split("\n");
            for (int i = 0; i < myStrings.length; i++) {
                String temp = myStrings[i];
                if (isBold) {
                    if (i == 0) {
                        setTextStyle(pIO, textType, bgcolor, fontSize, temp, true, true);
                    } else {
                        setTextStyle(pIO, textType, bgcolor, fontSize, "      " + temp, true, false);
                    }
                } else {
                    setTextStyle(pIO, textType, bgcolor, fontSize, temp, true, false);
                }
            }
        } else {
            setTextStyle(pIO, textType, bgcolor, fontSize, text, false, false);
        }
    }

    private void setTextStyle(XWPFParagraph pIO, String textType, String bgcolor, int fontSize, String text,
                              boolean isEntery, boolean isBold) {
        XWPFRun rIO = pIO.createRun();
        if (textType == null || textType.equals("")) {
            rIO.setFontFamily("微软雅黑");
        } else {
            rIO.setFontFamily(textType);
        }
        if (bgcolor == null || bgcolor.equals("")) {
            rIO.setColor("000000");
        } else {
            rIO.setColor(bgcolor);
        }
        rIO.setFontSize(fontSize);
        rIO.setText(text);
        if (isBold){
            rIO.setBold(true);
        }
        if (isEntery){
            rIO.addBreak();
        }
    }

    /**
     * 设置表格见的空行
     * @param xdoc
     * @param r1
     */
    public void setEmptyRow(CustomXWPFDocument xdoc, XWPFRun r1) {
        XWPFParagraph p1 = xdoc.createParagraph();
        p1.setAlignment(ParagraphAlignment.CENTER);
        p1.setVerticalAlignment(TextAlignment.CENTER);
        r1 = p1.createRun();
    }

    /***
     * word跨列合并单元格
     * @param table
     * @param row
     * @param fromCell
     * @param toCell
     */
    public void mergeCellsHorizontal(XWPFTable table, int row, int fromCell, int toCell) {
        for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {
            XWPFTableCell cell = table.getRow(row).getCell(cellIndex);
            if (cellIndex == fromCell) {
                // The first merged cell is set with RESTART merge value
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
            } else {
                // Cells which join (merge) the first one, are set with CONTINUE
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
            }
        }
    }

    /***
     * word跨行并单元格
     * @param table
     * @param col
     * @param fromRow
     * @param toRow
     */
    public void mergeCellsVertically(XWPFTable table, int col, int fromRow, int toRow) {
        for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {
            XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
            if (rowIndex == fromRow) {
                // The first merged cell is set with RESTART merge value
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
            } else {
                // Cells which join (merge) the first one, are set with CONTINUE
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
            }
        }
    }
}