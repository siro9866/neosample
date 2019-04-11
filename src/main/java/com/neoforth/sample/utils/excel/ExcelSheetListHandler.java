package com.neoforth.sample.utils.excel;

import java.util.List;

import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;

public class ExcelSheetListHandler implements SheetContentsHandler {
	
	//collection 객체
	private List<String[]> rows;
	//collection 에 추가될 객체 startRow에서 초기화함.
	private String[] row;
	//collection 내 객체를 String[] 로 잡았기 때문에 배열의 길이를 생성시 받도록 설계
	private int columnCnt;
	//cell 이벤트 처리 시 해당 cell의 데이터가 배열 어디에 저장되야 할지 가리키는 pointer
	private int currColNum = 0;
	
	private int currentCol = -1;
	
	//외부 collection 과 배열 size를 받기 위해 추가한 부분입니다.
	public ExcelSheetListHandler(List<String[]> rows, int columnsCnt) {		
		this.rows = rows;
		this.columnCnt = columnsCnt;
	}
	
	//Row의 시작 부분에서 발생하는 이벤트를 처리하는 method
	@Override
	public void startRow(int rowNum) {
		this.row = new String[columnCnt];
		currColNum = 0;		
	}
	
//	@Override
//	public void cell(String columnNum, String value) {
//		
//		int thisCol = (new CellReference(columnNum)).getCol();
//		int missedCols = thisCol - currentCol -1;
//		for (int i=0; i < missedCols ; i++) {
//			row[currColNum++] = "";
//		}
//		currentCol = thisCol;
//		//cell 이벤트 발생 시 해당 cell의 주소와 값을 받아옴. 
//		//입맛에 맞게 처리하면됨.
//		value = value.replaceAll(",","");
//		row[currColNum++] = value == null ? "":value;
//	}

	@Override
	public void headerFooter(String paramString1, boolean paramBoolean,
			String paramString2) {
		//sheet의 첫 row와 마지막 row를 처리하는 method
		//전 사용하지 않음 ㅋㅋ
	}

	@Override
	public void endRow(int rowNum) {
		//cell 이벤트에서 담아놓은 row String[]를 collection에 추가
		//데이터가 하나도 없는 row는 collection에 추가하지 않도록 조건 추가
		boolean addFlag = false;
		for(String data : row){
			if(!"".equals(data))
				addFlag = true;
		}
		if(addFlag)rows.add(row);		
		
	}

	@Override
	public void cell(String cellReference, String formattedValue, XSSFComment comment) {
		int thisCol = (new CellReference(cellReference)).getCol();
		int missedCols = thisCol - currentCol -1;
		for (int i=0; i < missedCols ; i++) {
			row[currColNum++] = "";
		}
		currentCol = thisCol;
		//cell 이벤트 발생 시 해당 cell의 주소와 값을 받아옴. 
		//입맛에 맞게 처리하면됨.
		formattedValue = formattedValue.replaceAll(",","");
		row[currColNum++] = formattedValue == null ? "":formattedValue;
		
	}
	
}