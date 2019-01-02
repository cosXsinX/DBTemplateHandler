package com.mz.database.template.bundle.handlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class ZipFileManager {
	public boolean createZipFile(String zipFilePath)
	{
		try {
			Map<String, String> env = new HashMap<>(); 
	        env.put("create", "true");
	        // locate file system by using the syntax 
	        // defined in java.net.JarURLConnection
	        File zipFile = new File(zipFilePath);
	        URI uri = URI.create("jar:"+zipFile.toURI()+"");
	        
	       try (FileSystem zipfs = FileSystems.newFileSystem(uri, env)) {
	            Path pathInZipfile = zipfs.getPath("/manifest.txt");          
	            // copy a file into the zip file
	            String manifestContent = "this is the manifest of the project";
	            byte[] bytes = manifestContent.getBytes();
	            if(Files.exists(pathInZipfile))
	            {
	            	Files.delete(pathInZipfile);
	            }
	            Files.write(pathInZipfile, bytes);
	       }
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteZipFile(String zipFilePath)
	{
		File zipFile = new File(zipFilePath);
		zipFile.delete();
		return true;
	}
	
	public boolean IsFolderContainedInZipFile(String zipFilePath,String searchedFolder)
	{
		boolean result = false;
		Map<String,String> env = new HashMap<>();
		env.put("create","true");
        File zipFile = new File(zipFilePath);
        URI uri = URI.create("jar:"+zipFile.toURI()+"");
		try(FileSystem zipfs = FileSystems.newFileSystem(uri, env))
		{
			Path path = zipfs.getPath(searchedFolder);
			if(!Files.exists(path)) return false;
			return Files.isDirectory(path);
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public List<String> ListZipContainedFile(String zipFilePath)
	{
		List<String> result = new ArrayList<String>();
		Map<String, String> env = new HashMap<>(); 
        env.put("create", "true");
        
        File zipFile = new File(zipFilePath);
        
        // locate file system by using the syntax 
        // defined in java.net.JarURLConnection
        
        URI uri = URI.create("jar:"+ zipFile.toURI());
       try (FileSystem zipfs = FileSystems.newFileSystem(uri, env)) {
    	   result =  ListZipContainedFile(zipfs,zipFilePath,"/");
       } catch (IOException e) {
   		// TODO Auto-generated catch block
   			e.printStackTrace();
       }
       return result;
	}
	
	private List<String> ListZipContainedFile(FileSystem zipfs,String zipFilePath,String listedFolderPath) throws IOException
	{
		List<String> result = new ArrayList<String>(); 
        listedFolderPath = listedFolderPath.replace(File.separatorChar, '/');
        Path pathInZipfile = zipfs.getPath(listedFolderPath);
        if(Files.exists(pathInZipfile) && Files.isDirectory(pathInZipfile))
        {
        	Stream<Path> streamPath = Files.walk(pathInZipfile);
    		streamPath.forEach(s -> 
    		{
    			String filePath = s.toString();
    			filePath = filePath.replace('/', File.separatorChar);
    			if(!filePath.equals(File.separator)) result.add(filePath);	
			});
        	streamPath.close();
        	String pathInZipFileString = pathInZipfile.toString();
        	pathInZipFileString = pathInZipFileString.replace('/', File.separatorChar);
        	result.add(pathInZipFileString);
        }
       return result;
	
	}
	
	public boolean createFolderInZipFile(String zipFilePath,String createdfolderInternalPath)
	{
		try {
			Map<String, String> env = new HashMap<>(); 
	        env.put("create", "true");
	        // locate file system by using the syntax 
	        // defined in java.net.JarURLConnection
	        File zipFile = new File(zipFilePath);
	        URI uri = URI.create("jar:"+zipFile.toURI()+"");
	        
	       try (FileSystem zipfs = FileSystems.newFileSystem(uri, env)) {
	            Path pathInZipfile = zipfs.getPath(createdfolderInternalPath);  
	            if(!Files.exists(pathInZipfile))
	            {
	            	Files.createDirectory(pathInZipfile);
	            }
	       }
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	public List<String> DeleteZipContainedFile(FileSystem zipfs,String zipFilePath,String deletedFolderPath) throws IOException
	{
		List<String> result = new ArrayList<String>();
        Path pathInZipfile = zipfs.getPath(deletedFolderPath);  
        if(Files.exists(pathInZipfile) && Files.isDirectory(pathInZipfile))
        {
        	Stream<Path> streamPath = Files.walk(pathInZipfile);
    		streamPath.forEach(s -> 
    		{
    			if(!Files.isDirectory(s))
				{
    				try {
						Files.delete(s);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else
					try {
						if(!pathInZipfile.equals(s)) DeleteZipContainedFile(zipfs,zipFilePath,s.toString());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			});
        	streamPath.close();
        	Files.delete(pathInZipfile);
        }
       return result;
	
	}
	
	public boolean saveTextFileInZipFile(String zipFilePath, String savedZipTextFilePath, String textContent)
	{
		try {
			Map<String, String> env = new HashMap<>(); 
	        env.put("create", "true");
	        // locate file system by using the syntax 
	        // defined in java.net.JarURLConnection
	        File zipFile = new File(zipFilePath);
	        URI uri = URI.create("jar:"+zipFile.toURI()+"");
	        
	       try (FileSystem zipfs = FileSystems.newFileSystem(uri, env)) {
	            Path pathInZipfile = zipfs.getPath(savedZipTextFilePath);  
	            String manifestContent = textContent;
	            byte[] bytes = manifestContent.getBytes();
	            if(Files.exists(pathInZipfile))
	            {
	            	Files.delete(pathInZipfile);
	            }
	            Files.write(pathInZipfile, bytes);
	       }
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public String getTextFileContentInZipFile(String zipFilePath,String textFileInZipFilePath)
	{
		try {
			Map<String, String> env = new HashMap<>(); 
	        env.put("create", "true");
	        // locate file system by using the syntax 
	        // defined in java.net.JarURLConnection
	        File zipFile = new File(zipFilePath);
	        URI uri = URI.create("jar:"+zipFile.toURI()+"");
	        
	       try (FileSystem zipfs = FileSystems.newFileSystem(uri, env)) {
	            Path pathInZipfile = zipfs.getPath(textFileInZipFilePath);  
	            if(Files.exists(pathInZipfile))
	            {
	            	byte[] fileContentByte = Files.readAllBytes(pathInZipfile);
	            	return new String(fileContentByte);
	            }
	       }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public boolean deleteTextFileInZipFile(String zipFilePath, String deletedZipFIleTextFilePath)
	{
		try {
			Map<String, String> env = new HashMap<>(); 
	        env.put("create", "true");
	        // locate file system by using the syntax 
	        // defined in java.net.JarURLConnection
	        File zipFile = new File(zipFilePath);
	        URI uri = URI.create("jar:"+zipFile.toURI()+"");
	        
	       try (FileSystem zipfs = FileSystems.newFileSystem(uri, env)) {
	            Path pathInZipfile = zipfs.getPath(deletedZipFIleTextFilePath);  
	            if(Files.exists(pathInZipfile))
	            {
	            	Files.delete(pathInZipfile);
	            }
	       }
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteFolderInZipFile(String zipFilePath, String deletedZipFolderTextFilePath)
	{
		try {
			Map<String, String> env = new HashMap<>(); 
	        env.put("create", "true");
	        // locate file system by using the syntax 
	        // defined in java.net.JarURLConnection
	        File zipFile = new File(zipFilePath);
	        URI uri = URI.create("jar:"+zipFile.toURI()+"");
	        
	       try (FileSystem zipfs = FileSystems.newFileSystem(uri, env)) {
	            Path pathInZipfile = zipfs.getPath(deletedZipFolderTextFilePath);  
	            if(Files.exists(pathInZipfile))
	            {
	            	Files.delete(pathInZipfile);
	            }
	       }
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean moveFileInZipFile(String zipFilePath, String formerZipFileTextFilePath, String newZipFileTextFilePath )
	{
		return false;
	}
}
