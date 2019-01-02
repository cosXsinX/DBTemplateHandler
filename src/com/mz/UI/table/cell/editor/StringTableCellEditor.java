package com.mz.UI.table.cell.editor;

import java.awt.Component;
import java.io.IOException;
import java.util.EventObject;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

public class StringTableCellEditor extends DefaultCellEditor  {


	
	
	public StringTableCellEditor() {
		super(new JTextField());
		// TODO Auto-generated constructor stub
	}

//	@Override
//	public Component getTableCellEditorComponent(JTable arg0, Object arg1,
//			boolean arg2, int arg3, int arg4) {
//		return new JEditorPane();
//	}

}
