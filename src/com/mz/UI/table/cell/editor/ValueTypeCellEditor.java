package com.mz.UI.table.cell.editor;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;

public class ValueTypeCellEditor extends DefaultCellEditor {

	public ValueTypeCellEditor(String[]  possibleValues) {
		super(new JComboBox(possibleValues));
	}

}
