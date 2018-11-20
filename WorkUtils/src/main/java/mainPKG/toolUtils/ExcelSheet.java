package mainPKG.toolUtils;

import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;

public class ExcelSheet {
	private CellStyle cellStyle = null;
	public void setCellStyle(CellStyle cellStyle) {
		this.cellStyle = cellStyle;
	}
	
	private Sheet sheet;
	private int rowNum = -1;
	private int colNum = -1;
	private int colNumTmp = -1;
	private void updateColNumTmp() {
		colNumTmp = colNum;
	}
	public int getRowNum() {
		return rowNum;
	}
	public int getColNum() {
		return colNum;
	}
	public void reSetRow(int newRow) {
		rowNum = newRow;
		updateColNumTmp();
	}
	public void reSetCol(int newCol) {
		colNum = newCol;
		updateColNumTmp();
	}
	public void reSetRowCol(int newRow, int newCol) {
		rowNum = newRow;
		colNum = newCol;
		updateColNumTmp();
	}
	public Sheet getSheet() {
		return sheet;
	}
	public ExcelSheet(Sheet newSheet, int startRow, int startCol) {
		sheet = newSheet; 
		rowNum = startRow;
		colNum = startCol;
		updateColNumTmp();
	}
	
	public void writeOneLine(Object... items) {
		addOneLine(true, items);
		moveToNextLine();
	}
	public void moveToNextLine() {
		rowNum++;
		updateColNumTmp();
	}
	public void addOneLine(boolean updateColNum, Object... items) {
		for (Object item : items) {
			if (item instanceof String) {
				ExcelUtils.addCellData(rowNum, colNumTmp++, (String)item, sheet, cellStyle);
			} else if (item instanceof Integer) {
				ExcelUtils.addCellData(rowNum, colNumTmp++, (int)item, sheet, cellStyle);
			} else if (item instanceof Character) {
				ExcelUtils.addCellData(rowNum, colNumTmp++, ""+(char)item, sheet, cellStyle);
			} else if (item instanceof Boolean) {
				ExcelUtils.addCellData(rowNum, colNumTmp++, (boolean)item, sheet, cellStyle);
			} else if (item instanceof List) {
				colNumTmp = writeList(colNumTmp, item);
			} else if (item == null) {
				ExcelUtils.addCellData(rowNum, colNumTmp++, "", sheet, cellStyle);
			} else if (item.getClass().isArray()) {
				colNumTmp = writeArray(colNumTmp, (Object[]) item);
			} else {
				ExcelUtils.addCellData(rowNum, colNumTmp++, "???@"+item.toString(), sheet, cellStyle);
			}
		}
		if (updateColNum) {
			updateColNumTmp();
		}
	}
	@SuppressWarnings("rawtypes")
	private int writeList(int col, Object list) {
		for (Object item : (List)list) {
			if (item instanceof String) {
				ExcelUtils.addCellData(rowNum, col++, (String)item, sheet, cellStyle);
			} else if (item instanceof Integer) {
				ExcelUtils.addCellData(rowNum, col++, (int)item, sheet, cellStyle);
			} else if (item instanceof Boolean) {
				ExcelUtils.addCellData(rowNum, col++, (boolean)item, sheet, cellStyle);
			} else if (item == null) {
				ExcelUtils.addCellData(rowNum, col++, "", sheet, cellStyle);
			} else if (item instanceof List) {
				col = writeList(col, item);
			} else if (item.getClass().isArray()) {
				col = writeArray(col, (Object[]) item);
			} else {
				ExcelUtils.addCellData(rowNum, col++, "[BadType@]toString="+item.toString(), sheet, cellStyle);
			}
		}
		return col;
	}
	private int writeArray(int col, Object[] list) {
		for (Object item : list) {
			if (item instanceof String) {
				ExcelUtils.addCellData(rowNum, col++, (String)item, sheet, cellStyle);
			} else if (item instanceof Integer) {
				ExcelUtils.addCellData(rowNum, col++, (int)item, sheet, cellStyle);
			} else if (item instanceof Boolean) {
				ExcelUtils.addCellData(rowNum, col++, (boolean)item, sheet, cellStyle);
			} else if (item == null) {
				ExcelUtils.addCellData(rowNum, col++, "", sheet, cellStyle);
			} else if (item instanceof List) {
				col = writeList(col, item);
			} else if (item.getClass().isArray()) {
				col = writeArray(col, (Object[]) item);
			} else {
				ExcelUtils.addCellData(rowNum, col++, "[BadType@]toString="+item.toString(), sheet, cellStyle);
			}
		}
		return col;
	}
	
}
