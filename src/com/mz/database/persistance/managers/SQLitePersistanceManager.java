package com.mz.database.persistance.managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mz.database.models.DatabaseDescriptionPOJO;
import com.mz.database.models.TableColumnDescriptionPOJO;
import com.mz.database.models.TableDescriptionPOJO;
import com.mz.sqlite.dal.dao.DAOMain;
import com.mz.sqlite.dal.dao.DatabaseDescriptionJavaSQLiteTableDAO;
import com.mz.sqlite.dal.dao.DatabaseDescriptionTableDescription;
import com.mz.sqlite.dal.dao.DatabaseDescriptionTableRecordPOJO;
import com.mz.sqlite.dal.dao.TableColumnDescriptionJavaSQLiteTableDAO;
import com.mz.sqlite.dal.dao.TableColumnDescriptionTableDescription;
import com.mz.sqlite.dal.dao.TableColumnDescriptionTableRecordPOJO;
import com.mz.sqlite.dal.dao.TableDescriptionJavaSQLiteTableDAO;
import com.mz.sqlite.dal.dao.TableDescriptionTableDescription;
import com.mz.sqlite.dal.dao.TableDescriptionTableRecordPOJO;

public class SQLitePersistanceManager implements IPersistanceManager {

	private String dbName = "DatabaseTemplateHandlerDB";
	private  Connection c = null;
	private Connection getConnection()
	{	
		if(c == null)
		{
			try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      try {
				c = DriverManager.getConnection("jdbc:sqlite:" +dbName);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      
		}
		return c;
	}
	
	DAOMain _daoMain;
	public SQLitePersistanceManager()
	{
		Connection connection = this.getConnection();
		_daoMain = new DAOMain(connection);
		
//		_daoMain.getTableColumnDescriptionDao().DropTable();
//		_daoMain.getTableDescriptionDao().DropTable();
//		_daoMain.getDatabaseDescriptionDao().DropTable();
		
		_daoMain.getDatabaseDescriptionDao().CreateAssociatedTableInDB();
		_daoMain.getTableDescriptionDao().CreateAssociatedTableInDB();
		_daoMain.getTableColumnDescriptionDao().CreateAssociatedTableInDB();
	}
	
	@Override
	public boolean doPersistDatabaseModel(DatabaseDescriptionPOJO persistedModel, String AssociatedKeyStr) {
		if(persistedModel == null) return false;
		if(AssociatedKeyStr == null) return false;
		DatabaseDescriptionJavaSQLiteTableDAO dao =  _daoMain.getDatabaseDescriptionDao();
		//TODO Add here specific sql query preparation management
		List<DatabaseDescriptionTableRecordPOJO> databaseDescriptionList = 
			dao.Select(DatabaseDescriptionTableDescription.TABLE_NAME + "." + 
					DatabaseDescriptionTableDescription.DatabaseName_COLUMN + "='"+ AssociatedKeyStr + "'" );
		DatabaseDescriptionTableRecordPOJO databaseModel = new DatabaseDescriptionTableRecordPOJO();
		Map(databaseModel, persistedModel);
		if(databaseDescriptionList.size() >0)
		{
			databaseModel.setIdInt(databaseDescriptionList.get(0).getIdInt());
			if (!dao.Update(databaseModel)) return false;
		}
		else
		{
			if(!dao.Insert(databaseModel)) return false;
		}
		if(!(SaveDatabaseTable(databaseModel.getIdInt(), persistedModel.getTableList()))) return false;
		return true;
	}

	private boolean SaveDatabaseTable(int idAssociatedDatabaseRecord,List<TableDescriptionPOJO> savedTables)
	{
		if(!(idAssociatedDatabaseRecord >0)) return false;
		if(savedTables == null) return true;
		TableDescriptionJavaSQLiteTableDAO dao =  _daoMain.getTableDescriptionDao();
		TableColumnDescriptionJavaSQLiteTableDAO daoColumn = _daoMain.getTableColumnDescriptionDao();
		
		List<TableDescriptionTableRecordPOJO> deletedList = dao.Select(TableDescriptionTableDescription.TABLE_NAME + "." 
		+ TableDescriptionTableDescription.IdDatabaseDescription_COLUMN + "=" +
				idAssociatedDatabaseRecord);
		for(TableDescriptionTableRecordPOJO currentDeletedTable: deletedList)
		{
			DeleteTableAssociatedColumn(currentDeletedTable.getIdInt());
		}
		dao.Delete(deletedList);
		List<TableDescriptionTableRecordPOJO> insertedTables = new ArrayList<TableDescriptionTableRecordPOJO>();
		for(TableDescriptionPOJO currentTable : savedTables)
		{
			TableDescriptionTableRecordPOJO tableDBAssociatedRecord = new TableDescriptionTableRecordPOJO();
			Map(tableDBAssociatedRecord,currentTable);
			tableDBAssociatedRecord.setIdDatabaseDescriptionInt(idAssociatedDatabaseRecord);
			insertedTables.add(tableDBAssociatedRecord);
			
		}
		dao.Insert(insertedTables);
		for(int  currentTableIndex = 0; currentTableIndex< insertedTables.size();currentTableIndex++ )
		{
			TableDescriptionTableRecordPOJO currentInsertedTable = insertedTables.get(currentTableIndex);
			TableDescriptionPOJO currentSavedTable = savedTables.get(currentTableIndex);
			List<TableColumnDescriptionTableRecordPOJO> insertedColumns = new ArrayList<TableColumnDescriptionTableRecordPOJO>();
			List<TableColumnDescriptionPOJO> savedColumns = currentSavedTable.get_ColumnsList();
			for(TableColumnDescriptionPOJO currentSavedColumn:savedColumns)
			{
				TableColumnDescriptionTableRecordPOJO currentInsertedColumn = new TableColumnDescriptionTableRecordPOJO();
				Map(currentInsertedColumn,currentSavedColumn);
				currentInsertedColumn.setIdTableDescriptionInt(currentInsertedTable.getIdInt());
				insertedColumns.add(currentInsertedColumn);
			}
			daoColumn.Insert(insertedColumns);
		}
		return true;
	}
	
	private boolean DeleteTableAssociatedColumn(int IdTable)
	{
		TableColumnDescriptionJavaSQLiteTableDAO dao = _daoMain.getTableColumnDescriptionDao();
		List<TableColumnDescriptionTableRecordPOJO> deletedList = 
				dao.Select(TableColumnDescriptionTableDescription.TABLE_NAME + "." + 
						TableColumnDescriptionTableDescription.IdTableDescription_COLUMN + "=" + IdTable  );
		return dao.Delete(deletedList);
	}
	
	
	@Override
	public DatabaseDescriptionPOJO getPersistedDatabaseModel(String ModelKeyStr) {
		if(ModelKeyStr == null) return null;
		DatabaseDescriptionJavaSQLiteTableDAO dao =  _daoMain.getDatabaseDescriptionDao();
		TableDescriptionJavaSQLiteTableDAO tableDao = _daoMain.getTableDescriptionDao();
		TableColumnDescriptionJavaSQLiteTableDAO columnDao = _daoMain.getTableColumnDescriptionDao();
		//TODO Add here specific sql query preparation management
		List<DatabaseDescriptionTableRecordPOJO> databaseDescriptionList = 
			dao.Select(DatabaseDescriptionTableDescription.TABLE_NAME + "." + 
					DatabaseDescriptionTableDescription.DatabaseName_COLUMN + "='"+ ModelKeyStr + "'" );
		if(!(databaseDescriptionList.size()>0)) return null;
		
		DatabaseDescriptionTableRecordPOJO databaseDescriptionDBItem = databaseDescriptionList.get(0);
		DatabaseDescriptionPOJO result = new DatabaseDescriptionPOJO();
		Map(result,databaseDescriptionDBItem);
		
		
		List<TableDescriptionTableRecordPOJO> tablesFromDB = tableDao.Select(TableDescriptionTableDescription.TABLE_NAME + "." +
			TableDescriptionTableDescription.IdDatabaseDescription_COLUMN + "=" +
				databaseDescriptionDBItem.getIdInt());
		ArrayList<TableDescriptionPOJO> resultTable = new ArrayList<TableDescriptionPOJO>();
		for(TableDescriptionTableRecordPOJO currentTableFromDB : tablesFromDB)
		{
			List<TableColumnDescriptionTableRecordPOJO> currentTableAssociatedColumnsFromDB = 
					columnDao.Select(TableColumnDescriptionTableDescription.TABLE_NAME + "." +
							TableColumnDescriptionTableDescription.IdTableDescription_COLUMN + "=" 
								+ currentTableFromDB.getIdInt());
			TableDescriptionPOJO currentResultSubTable = 
					new TableDescriptionPOJO(currentTableFromDB.getNameStrString());
			Map(currentResultSubTable,currentTableFromDB);
			for(TableColumnDescriptionTableRecordPOJO currentColumnFromDB : currentTableAssociatedColumnsFromDB)
			{
				TableColumnDescriptionPOJO currentResultSubColumn =
						new TableColumnDescriptionPOJO(
							currentColumnFromDB.getNameStrString(), 
								currentColumnFromDB.getTypeStrString(), 
									currentColumnFromDB.getPrimaryKeyBoolean());
				Map(currentResultSubColumn,currentColumnFromDB);
				currentResultSubTable.AddColumn(currentResultSubColumn);
			}
			resultTable.add(currentResultSubTable);
		}
		result.setTableList(resultTable);
		return result;
	}

	@Override
	public List<String> ListEveryAvailableKey() {
		DatabaseDescriptionJavaSQLiteTableDAO dao =  _daoMain.getDatabaseDescriptionDao();
		List<DatabaseDescriptionTableRecordPOJO> databaseDescriptionList = 
				dao.Select("");
		List<String> result = new ArrayList<String>();
		for(DatabaseDescriptionTableRecordPOJO current:databaseDescriptionList)
			result.add(current.getDatabaseNameString());
		return result;
	}

	@Override
	public boolean IsAlreadyPresentKeyInAvailableKey(String CheckedKeyStr) {
		if(CheckedKeyStr == null) return false;
		DatabaseDescriptionJavaSQLiteTableDAO dao =  _daoMain.getDatabaseDescriptionDao();
		List<DatabaseDescriptionTableRecordPOJO> databaseDescriptionList = 
				dao.Select(DatabaseDescriptionTableDescription.TABLE_NAME + "." + 
						DatabaseDescriptionTableDescription.DatabaseName_COLUMN + "='"+ CheckedKeyStr + "'" );
		return (databaseDescriptionList.size()>0);
	}

	@Override
	public boolean DeletePersistedDatabaseModel(String RemovedModelKeyStr) {

		if(RemovedModelKeyStr == null) return false;
		
		DatabaseDescriptionJavaSQLiteTableDAO dao =  _daoMain.getDatabaseDescriptionDao();
		TableDescriptionJavaSQLiteTableDAO daoTable =  _daoMain.getTableDescriptionDao();
		
		List<DatabaseDescriptionTableRecordPOJO> databaseDescriptionList = 
				dao.Select(DatabaseDescriptionTableDescription.TABLE_NAME + "." + 
						DatabaseDescriptionTableDescription.DatabaseName_COLUMN + "='"+ RemovedModelKeyStr + "'" );
		if(!(databaseDescriptionList.size()>0)) return false;
		
		int idAssociatedDatabaseRecord = databaseDescriptionList.get(0).getIdInt();
		
		List<TableDescriptionTableRecordPOJO> deletedList = daoTable.Select(TableDescriptionTableDescription.TABLE_NAME + "." 
		+ TableDescriptionTableDescription.IdDatabaseDescription_COLUMN + "=" +
				idAssociatedDatabaseRecord);
		for(TableDescriptionTableRecordPOJO currentDeletedTable: deletedList)
		{
			DeleteTableAssociatedColumn(currentDeletedTable.getIdInt());
		}
		daoTable.Delete(deletedList);
		return false;
	}

	
	private void Map(DatabaseDescriptionTableRecordPOJO destination,
			DatabaseDescriptionPOJO source)
	{
		if (destination == null)  return;
		if(source == null) return;
		destination.setDatabaseNameString(source.getDatabaseNameStr());
	}
	
	private void Map(DatabaseDescriptionPOJO destination,
			DatabaseDescriptionTableRecordPOJO source)
	{
		if (destination == null)  return;
		if(source == null) return;
		destination.setDatabaseNameStr(source.getDatabaseNameString());
	}
	
	
	private void Map(TableDescriptionTableRecordPOJO destination,
			TableDescriptionPOJO source)
	{
		if (destination == null)  return;
		if(source == null) return;
		destination.setNameStrString(source.get_NameStr());
	}
	
	private void Map(TableDescriptionPOJO destination,
			TableDescriptionTableRecordPOJO source)
	{
		if (destination == null)  return;
		if(source == null) return;
		destination.set_NameStr(source.getNameStrString());
	}
	
	private void Map(TableColumnDescriptionTableRecordPOJO destination,
			TableColumnDescriptionPOJO source)
	{
		if (destination == null)  return;
		if(source == null) return;
		destination.setAutoGeneratedValueBlnBoolean(source.is_AutoGeneratedValueBln());
		destination.setNameStrString(source.get_NameStr());
		destination.setNotNullBoolean(source.is_NotNull());
		destination.setPrimaryKeyBoolean(source.is_PrimaryKey());
		destination.setTypeStrString(source.get_TypeStr());
	}
	
	private void Map(TableColumnDescriptionPOJO destination,
			TableColumnDescriptionTableRecordPOJO source)
	{
		if (destination == null)  return;
		if(source == null) return;
		destination.set_AutoGeneratedValueBln(source.getAutoGeneratedValueBlnBoolean());
		destination.set_NameStr(source.getNameStrString());
		destination.set_NotNull(source.getNotNullBoolean());
		destination.set_PrimaryKey(source.getPrimaryKeyBoolean());
		destination.set_TypeStr(source.getTypeStrString());
	}
}
