package cn.itcast.bos.test.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;

public class POITest {

	@Test
	public void testPOI(){
		//使用poi读取excel文件
		try {
			//1.通过workbook获取excel文件
			HSSFWorkbook wb = new HSSFWorkbook(
					new FileInputStream(new File("E:\\current_course\\速运快递\\资料和视频\\day04\\资料\\03_区域测试数据\\区域导入测试数据.xls")));
			//2.通过workbook对象获取sheet页
			HSSFSheet sheet = wb.getSheetAt(0);
			//3.通过sheet页获取每一行row
			for(Row row : sheet){
				//4.通过row获取每个单元格
				String value1 = row.getCell(0).getStringCellValue();
				String value2 = row.getCell(1).getStringCellValue();
				String value3 = row.getCell(2).getStringCellValue();
				String value4 = row.getCell(3).getStringCellValue();
				String value5 = row.getCell(4).getStringCellValue();
				//5.打印结果
				System.out.println(value1+"--"+value2+"--"+value3+"--"+value4+"--"+value5);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
