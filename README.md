# DBTemplateHandler
A Database oriented DSL pre-compiler

This utility is devoted to create your own DAL generators devoted to tabular data representation database (or normalized) ( such as SQLite, SQLServer, MySql ...). 

The template handler implement a tabular and minimal tabular database domain specific language (functional way).

##Domain specific language words :

###Database specific words :

{:TDB:CURRENT:NAME::} : Is replaced by the current name of the database
{:TDB:TABLE:FOREACH[ ... ]::] : Is the iterator devoted to the table enumeration => this allows the replacement of the contextualized specified underneath in the "Table specific words" section

###Table specific words :

{:TDB:TABLE:CURRENT:NAME::} : Is replaced by the current table name
{:TDB:TABLE:COLUMN:FOREACH[ ... ]::} : Is the iterator devoted to the iteration of the current table columns
{:TDB:TABLE:COLUMN:AUTO:FOREACH[ ... ]::} : Is the iterator devoted to the iteration of the current table columns auto generated value table columns
{:TDB:TABLE:COLUMN:NOT:AUTO:FOREACH[ ... ]::} : Is the iterator devoted to the iteration of the current table columns which are not auto generated value table columns
{:TDB:TABLE:COLUMN:NOT:NULL:FOREACH[ ... ]::} : Is the iterator devoted to the iteration of the current table columns which are not nullable value table columns
{:TDB:TABLE:COLUMN:PRIMARY:FOREACH[ ... ]::} : Is the iterator devoted to the iteration of the current table columns which are primary key value columns
{:TDB:TABLE:COLUMN:NOT:PRIMARY:FOREACH[ ... ]::} : Is the iterator devoted to the iteration of the current table columns which are not primary key value columns

###Column specific words :

{:TDB:TABLE:COLUMN:AUTO:FOREACH:CURRENT:INDEX::} : Is replaced by the index of the current auto generated value column index iterate column in the table definition
{:TDB:TABLE:COLUMN:AUTO:FOREACH:CURRENT:IS:FIRST:COLUMN( ... )::} : Is replaced by the content between parentheses when the column correspond to the first auto generated value content
{:TDB:TABLE:COLUMN:AUTO:FOREACH:CURRENT:IS:NOT:FIRST:COLUMN( ... )::} : Is replaced by the content between parentheses when the column do not correspond to the first auto generated value content
{:TDB:TABLE:COLUMN:AUTO:FOREACH:CURRENT:IS:LAST:COLUMN( ... )::} : Is replaced by the content between parentheses when the column correspond to the last auto generated value content
{:TDB:TABLE:COLUMN:AUTO:FOREACH:CURRENT:IS:NOT:LAST:COLUMN( ... )::} : Is replaced by the content between parentheses when the column correspond to the last auto generated value content
{:TDB:TABLE:COLUMN:FOREACH:CURRENT:CONVERT:TYPE( ... )::} : Is replaced by targeted conversion type. Nowadays only one available JAVA => {:TDB:TABLE:COLUMN:FOREACH:CURRENT:CONVERT:TYPE(JAVA)::} only applicable pattern

To be continued ...

###Function specific words :

{:TDB:FUNCTION:FIRST:CHARACTER:TO:UPPER:CASE( ... )::} : Replace the first character of the content by it's upper case