package me.dragon.base.utils;

/**
 * Created by dragon on 4/1/2017.
 */

import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 使用POI,进行Word相关的操作
 */
public class MSWordTool {

    /** 内部使用的文档对象 **/
    private XWPFDocument document;
    public XWPFTable getMarkTable(String bookMark){
        BookMark bk = bookMarks.getBookmark(bookMark);

        XWPFTable table = bk.getContainerTable();


        return table;
    }
    private BookMarks bookMarks = null;

    /**
     * 为文档设置模板
     * @param templatePath  模板文件名称
     */
    public void setTemplate(String templatePath) {
        try {
            this.document = new XWPFDocument(POIXMLDocument.openPackage(templatePath));
            bookMarks = new BookMarks(document);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 进行标签替换的例子,传入的Map中，key表示标签名称，value是替换的信息
     * @param indicator
     */
    public void  replaceBookMark(Map<String,String> indicator) {
        //循环进行替换
        Iterator<String> bookMarkIter = bookMarks.getNameIterator();
        while (bookMarkIter.hasNext()) {
            String bookMarkName = bookMarkIter.next();

            //得到标签名称
            BookMark bookMark = bookMarks.getBookmark(bookMarkName);

            //进行替换
            if (indicator.get(bookMarkName)!=null) {
                bookMark.insertTextAtBookMark(indicator.get(bookMarkName), BookMark.INSERT_BEFORE);
            }

        }

    }

    /**
     * 进行标签替换的例子,传入的Map中，key表示标签名称，value是替换的信息
     * @param indicator
     */
    public void  replaceTextBookMark(Map<String,String> indicator) {
        //循环进行替换
        Iterator<String> bookMarkIter = bookMarks.getNameIterator();
        while (bookMarkIter.hasNext()) {
            String bookMarkName = bookMarkIter.next();

            //得到标签名称
            BookMark bookMark = bookMarks.getBookmark(bookMarkName);

            //进行替换
            if (indicator.get(bookMarkName)!=null) {
                bookMark.insertTextAtBookMark(indicator.get(bookMarkName), BookMark.REPLACE);
            }

        }

    }
    /**
     * 替换标签内容（含标签样式）
     * @param map
     */
    public void replaceMark(Map<String,String> map){

        //循环进行替换
        Iterator<String> bookMarkIter = bookMarks.getNameIterator();
        while (bookMarkIter.hasNext()) {
            String bookMarkName = bookMarkIter.next();

            //得到标签名称
            BookMark bookMark = bookMarks.getBookmark(bookMarkName);

            //进行替换
            if (map.get(bookMarkName)!=null) {
                bookMark.replaceMark(map.get(bookMarkName));
            }

        }

    }

    public void getTableInfo(String bookMarkName){
        //首先得到标签
        BookMark bookMark = bookMarks.getBookmark(bookMarkName);
        //标签是否处于表格内
        if(bookMark.isInTable()){
            XWPFTableRow row = bookMark.getContainerTableRow();
            System.out.println(row.getHeight());
            List<XWPFTableCell> rowCell = row.getTableCells();
            XWPFTableCell cell = rowCell.get(0);
            CTTc cttc = cell.getCTTc();
            CTTcPr tcPr = cttc.isSetTcPr() ? cttc.getTcPr() : cttc.addNewTcPr();
            CTTblWidth tcw = tcPr.isSetTcW() ? tcPr.getTcW() : tcPr.addNewTcW();
            System.out.println(tcw.getW());
        }
    }



    public void replaceText(Map<String,String> bookmarkMap, String bookMarkName) {

        //首先得到标签
        BookMark bookMark = bookMarks.getBookmark(bookMarkName);
        //获得书签标记的表格
        XWPFTable table = bookMark.getContainerTable();
        //获得所有的表
        //Iterator<XWPFTable> it = document.getTablesIterator();

        if(table != null){
            //得到该表的所有行
            int rcount = table.getNumberOfRows();
            for(int i = 0 ;i < rcount; i++){
                XWPFTableRow row = table.getRow(i);

                //获到改行的所有单元格
                List<XWPFTableCell> cells = row.getTableCells();
                for(XWPFTableCell c : cells){
                    for(Map.Entry<String,String> e : bookmarkMap.entrySet()){
                        if(c.getText().equals(e.getKey())){

                            //删掉单元格内容
                            c.removeParagraph(0);

                            //给单元格赋值
                            c.setText(e.getValue());
                        }
                    }
                }
            }
        }
    }

    /**
     * @Description: 跨列合并
     * @param table
     * @param row 合并的行数
     * @param fromCell 开始单元格
     * @param toCell 结束单元格
     */
    public  void mergeCellsHorizontal(XWPFTable table, int row, int fromCell, int toCell) {
        for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {
            XWPFTableCell cell = table.getRow(row).getCell(cellIndex);
            if ( cellIndex == fromCell ) {
                // The first merged cell is set with RESTART merge value
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
            } else {
                // Cells which join (merge) the first one, are set with CONTINUE
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
            }
        }
    }

    /**
     * @Description: 跨行合并
     * @param table
     * @param col 合并的列
     * @param fromRow 开始合并行
     * @param toRow 结束合并行
     */
    public  void mergeCellsVertically(XWPFTable table, int col, int fromRow, int toRow) {
        for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {
            XWPFTableRow row = table.getRow(rowIndex);
            if(row != null){
                XWPFTableCell cell = row.getCell(col);
                if ( rowIndex == fromRow ) {
                    // The first merged cell is set with RESTART merge value
                    cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
                } else {
                    // Cells which join (merge) the first one, are set with CONTINUE
                    cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
                }
            }
        }
    }

    /**
     * 指定书签位置插入表格记录
     * @param dataList 数据集合
     * @param bookMark 插入书签 书签定义为插入行第一个单元格
     * @param colStart 其实列
     * @param colEnd 结束列
     * @return 插入书签的位置 为合并单元格准备
     */
    public int insertTaleRow(List<List<String>> dataList,String bookMark,int colStart,int colEnd){
        int insertNum = 0;

        BookMark bk = bookMarks.getBookmark(bookMark);
        XWPFTable table = bk.getContainerTable();

        List<XWPFTableRow> rows = table.getRows();
        for(int i=0;i<rows.size();i++){
            boolean isBreak = false;
            XWPFTableRow row = rows.get(i);
            XWPFTableCell cell = row.getCell(0);

            List<CTBookmark> marks = cell.getParagraphs().get(0).getCTP().getBookmarkStartList();
            for(CTBookmark mark : marks){
                String markName = mark.getName();
                //找到书签位置
                if(markName.equals(bookMark)){
                    insertNum = i;
                    for(int j=0;j<dataList.size();j++){
                        //循环插入模板数据
                        table.addRow(row, insertNum+j);
                        List<String> data = dataList.get(j);
                        for(int k=colStart;k<=colEnd;k++){
                            //清空模板数据内容
                            XWPFTableCell c = row.getCell(k);
                            XWPFParagraph para = c.getParagraphs().get(0);
                            para.getCTP().getDomNode().removeChild(para.getCTP().getDomNode().getLastChild());

                            //获取插入行 并插入记录
                            XWPFTableRow r = table.getRow(insertNum+j);
                            r.getCell(k).setText(data.get(k-colStart));
                        }

                    }
                    //删除模板行
                    table.removeRow(insertNum);
                    isBreak = true;
                    break;
                }
            }

            if(isBreak){
                break;
            }

        }

        return insertNum;

    }

    public void saveAs(String path) {
        File newFile = new File(path);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(newFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            this.document.write(fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
