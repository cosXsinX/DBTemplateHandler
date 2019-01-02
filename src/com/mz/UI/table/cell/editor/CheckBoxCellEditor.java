package com.mz.UI.table.cell.editor;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

public class CheckBoxCellEditor extends DefaultCellEditor {

	public CheckBoxCellEditor() {
		super(new JComboBox(new String []{"true","false"}));
	}

}
