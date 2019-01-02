package com.mz.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XMLManager {
	
	public static boolean SaveDocumentIntoFile(Document doc,String AtFilePathStr,boolean DoOverwrite)
	{
		XMLOutputFactory factory = XMLOutputFactory.newFactory();
		if(DoOverwrite && FileManager.DoesFileExists(AtFilePathStr)) return false;
		File file = new File(AtFilePathStr);
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(file);
			StreamResult streamResult = new StreamResult(fileWriter);
			TransformerFactory transformFactory = TransformerFactory.newInstance();
			Transformer transformer = transformFactory.newTransformer();
			DOMSource domSource = new DOMSource(doc);
			transformer.transform(domSource,streamResult);
		} catch(TransformerException e2){
			e2.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return true;
	}
	
	public static Document GetDocumentFromFile(String SourceFilePathStr){
		Document result = null;
		if(!FileManager.DoesFileExists(SourceFilePathStr)) return result;
		File file = new File(SourceFilePathStr);
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			result = documentBuilder.parse(file);
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
