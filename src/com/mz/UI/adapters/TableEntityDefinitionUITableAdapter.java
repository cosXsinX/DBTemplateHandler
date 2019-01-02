package com.mz.UI.adapters;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import com.mz.database.models.DatabaseDescriptionPOJO;
import com.mz.database.models.TableColumnDescriptionPOJO;
import com.mz.database.models.TableDescriptionPOJO;

import java.util.List;

public class TableEntityDefinitionUITableAdapter extends AbstractTableModel {
	private Logger LOGGER = Logger.getLogger(TableEntityDefinitionUITableAdapter.class);
	
	private TableDescriptionPOJO table;
    private List<TableColumnDescriptionPOJO> TableColumnList ;

    private final String[] entetes = {"Column name", "Type","Is Primary Key", "Is Not Null"};

    //Parent frame entity
    private final DatabaseDefinitionUITableAdapter _parentFrameAdapter;
    
    public TableEntityDefinitionUITableAdapter(TableDescriptionPOJO tableEntity,
    		DatabaseDefinitionUITableAdapter parentFrameAdapter) {
        super();
        setTableEntity(tableEntity);
        _parentFrameAdapter = parentFrameAdapter;
    }
    
    public void setTableEntity(TableDescriptionPOJO tableEntity){
    	if(tableEntity == null)
    	{
    		TableColumnList = null;
    		table = null;
    		return;
    	}
    	table = tableEntity;
    	TableColumnList = tableEntity.get_ColumnsList();
        if(!(TableColumnList.size()>0))
        	TableColumnList.add(new TableColumnDescriptionPOJO("COLUMN_NAME", "",false));
    }
    
    public TableDescriptionPOJO getTableEntity(){
    	return table;
    }
    
    public int getRowCount() {
    	if(TableColumnList == null) return 0;
        return TableColumnList.size();
    }

    public int getColumnCount() {
        return entetes.length;
    }

    public String getColumnName(int columnIndex) {
        return entetes[columnIndex];
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
    	if(TableColumnList == null) return null;
    	if(rowIndex<0||columnIndex<0) return null;
    	if(rowIndex>TableColumnList.size()-1) return null;
        switch(columnIndex){
            case 0:
                return TableColumnList.get(rowIndex).get_NameStr();
            case 1:
                return TableColumnList.get(rowIndex).get_TypeStr();
            case 2:
            	return TableColumnList.get(rowIndex).is_PrimaryKey();
            case 3:
            	return TableColumnList.get(rowIndex).is_NotNull();
            default:
                return "";
        }
    }

    @Override
    public Class getColumnClass(int columnIndex){
        switch(columnIndex){
        	case 0:
        		return String.class;
            case 1:
                return String.class;
            case 2:
                return Boolean.class;
            case 3:
            	return Boolean.class;
            default:
                return Object.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true; //Toutes les cellules �ditables
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    	if(TableColumnList == null) return;
        if(aValue != null){
            TableColumnDescriptionPOJO ami = TableColumnList.get(rowIndex);

            switch(columnIndex){
                case 0:
                    ami.set_NameStr((String)aValue);
                    break;
                case 1:
                    ami.set_TypeStr((String)aValue);
                    break;
                case 2:
                	ami.set_PrimaryKey(Boolean.parseBoolean((String) aValue));
                	break;
                case 3:
                	ami.set_NotNull(Boolean.parseBoolean((String) aValue));
            }
            propagateModification();
        }
    }

    public void addColumn(TableColumnDescriptionPOJO column) {
    	if(TableColumnList == null) return;
    	 
    	for(TableColumnDescriptionPOJO currentColumn : TableColumnList){
    		if(currentColumn.get_NameStr().equals(column.get_NameStr()))
			{
    			column.set_NameStr(column.get_NameStr() + "_bis");
			}
    	}
    	TableColumnList.add(column);
        fireTableRowsInserted(TableColumnList.size() -1, TableColumnList.size() -1);
        propagateModification();
    }

    public void removeColumn(int rowIndex) {
    	if(TableColumnList == null) return;
    	TableColumnList.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
        propagateModification();
    }
    
    public void propagateModification(){
    	_parentFrameAdapter.fireTableDataChanged();
    }
    
}