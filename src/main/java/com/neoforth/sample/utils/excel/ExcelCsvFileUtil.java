package com.neoforth.sample.utils.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.opencsv.CSVReader;


public class ExcelCsvFileUtil {

	
	public static List<String[]> getExcelBigData(String filePath, ExcelReadOption excelReadOption) throws Exception {

		File destFile = new File(filePath);
		
	    excelReadOption.setFilePath(destFile.getAbsolutePath());
		
	    
	    //bulkInsertFile = File 객체 or FileInputStream (ex : new File(파일경로) 등으로 넣을 수 있음)
		//OPCPackage 파일을 읽거나 쓸수있는 상태의 컨테이너를 생성함
		OPCPackage opc = OPCPackage.open(filePath, PackageAccess.READ);
		//opc 컨테이너 XSSF형식으로 읽어옴. 이 Reader는 적은 메모리로 sax parsing 을 하기 쉽게 만들어줌.
				
		XSSFReader xssfReader = new XSSFReader(opc);
		//XSSFReader 에서 sheet 별 collection으로 분할해서 가져옴.
		XSSFReader.SheetIterator itr = (XSSFReader.SheetIterator)xssfReader.getSheetsData();
				 
		//통합문서 내의 모든 Sheet에서 공유되는 스타일 테이블이라는데 정확한 사용용도는 모름;; ㅎㅎ
		StylesTable styles = xssfReader.getStylesTable();
		//ReadOnlySharedStringsTable 이것도 정확한 역할은 모르겠음...ㅠ.ㅠ ...뭔가 data의 type을 처리할 때 참조하는 것 같긴한데....
		ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(opc);
			 
		//이건 내가 데이터를 파싱해서 담아올 List 객체... 
		//이것 마저도 메모리 부담이 된다면... Handler에 처리로직을 넣어서 한건씩 처리하면 됨.
		
		List<String[]> result = new ArrayList<String[]>();
		int i = 0;
		// Sheet 수 만큼 Loop를 돌린다.
		while(itr.hasNext()) {
			InputStream sheetStream = itr.next();
			InputSource sheetSource = new InputSource(sheetStream);		
		 if(i==0) {
			//ExcelSheetListHandler은 엑셀 data를 가져와서 SheetContentsHandler(Interface)를 재정의 해서 만든 Class
			ExcelSheetListHandler excelSheetListHandler = new ExcelSheetListHandler(result, excelReadOption.getColLength());	
			
			//new XSSFSheetXMLHandler(StylesTable styles, ReadOnlySharedStringsTable strings, SheetContentsHandler sheet2ListHandler, boolean formulasNotResults)
			//formulasNotResults 이것도 무슨 옵션인지 모르겠음 ㅋㅋㅋ 이건 정보공유를 하겠다는 건지 ㅋㅋㅋ
			//어쨌든 이 핸들러는  Sheet의 행(row) 및 Cell 이벤트를 생성합니다.
			excelSheetListHandler.startRow(excelReadOption.getStartRow());
			
			ContentHandler handler = new XSSFSheetXMLHandler(styles, strings, excelSheetListHandler, false);
			
			//sax parser를 생성하고...
			SAXParserFactory saxFactory = SAXParserFactory.newInstance();
			SAXParser saxParser = saxFactory.newSAXParser();			
			//sax parser 방식의 xmlReader를 생성
			XMLReader sheetParser = saxParser.getXMLReader();
			//xml reader에 row와 cell 이벤트를 생성하는 핸들러를 설정한 후.
			sheetParser.setContentHandler(handler);
			//위에서 Sheet 별로 생성한 inputSource를 parsing합니다.
			//이 과정에서 handler는 row와 cell 이벤트를 생성하고 생성된 이벤트는 sheet2ListHandler 가 받아서 처리합니다.
			//sheet2ListHandler의 내용은 아래를 참조하세요.
			
			//InputStream sheet2 =  xssfReader.getSheet("");
			
			sheetParser.parse(sheetSource);
			i++;
		 }
		sheetStream.close();
		}
		opc.close();
		
		return result;
	}
			

	public static List<String[]> getExcelDefaultData(String filePath, ExcelReadOption excelReadOption) {
		List<String[]> result = new ArrayList<>();
		String[] rows;
		try {
			
			Workbook workbook = WorkbookFactory.create(new File(filePath));
			
			Sheet sheet = workbook.getSheetAt(0);
			
			Iterator<Row> rowIterator = sheet.iterator();
			int currColNum = 0;
//			rows = new String[];
			while(rowIterator.hasNext()) {
				Row row = rowIterator.next();
				rows = new String[row.getLastCellNum()];
				Iterator<Cell> cellIterator = row.iterator();
				while (cellIterator.hasNext()) {
					Cell cell = (Cell) cellIterator.next();
					String value = CellRef.getValue(workbook, cell);
					rows[currColNum++] = value == null ? "":value;
				}
				result.add(rows);
				currColNum = 0;
			}
			
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static List<String[]> getCsvData(String filePath) {
		
		
		List<String[]> result = new ArrayList<>();
		

		
		
		
		
		File file = new File(filePath);
		try {
			FileReader filereader = new FileReader(file);
			CSVReader reader = new CSVReader(filereader);
			
			String[] s;
			while ((s = reader.readNext()) != null) {
				result.add(s);
			}
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static String getFileType (String filePath) {
		
		String result = "etc";
		
		if(filePath.toUpperCase().endsWith(".XLS")) {
			result = "xls";
		} else if(filePath.toUpperCase().endsWith(".XLSX")) {
			result = "xlsx";
		} else if(filePath.toUpperCase().endsWith(".CSV")) {
			result = "csv";
		} else {
			result = "etc";
		}
		
		return result;
	}

}
