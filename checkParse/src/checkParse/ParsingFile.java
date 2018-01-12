package checkParse;
import java.util.*;
import java.io.*;
import java.lang.String;


public class ParsingFile
{
	private static String readFile(String pathname, String searchName) throws IOException
	{
	    File file = new File(pathname);
	    StringBuilder fileContents = new StringBuilder((int)file.length());
	    List<String> failureList = new ArrayList<String>();
	    List<String> correctList = new ArrayList<String>();
	    Scanner scanner = new Scanner(file);
	    int lineNumber = 1;
	    String completeTag = null;
	    String resultFindStartTag = null;
	    String resultFindEndTag = null;
	    try 
	    {
	    	while(scanner.hasNextLine())
	    	{
	    		String contentLine = scanner.nextLine();
	    		if (contentLine.contains(searchName))
	    		{
	    			for (int indexSearch = 0; (indexSearch = contentLine.indexOf(searchName, indexSearch)) >= 0; indexSearch++) 
	    			{
	    				if(findCov(contentLine, indexSearch, "cov"))
	    				{
	    					contentLine = contentLine.replaceAll("\\s+", " ");
	    					correctList.add("Result: true	Filename: " + pathname + "	LineNumber : " + lineNumber + " \t Content: " + "\t" + contentLine);;
	    				}
	    				else
	    				{
	    					resultFindStartTag = findStart(pathname, lineNumber, indexSearch);
	    					if (findStartTag(resultFindStartTag))
	    					{
	    						resultFindEndTag = findEnd(pathname, lineNumber, indexSearch);
	    						completeTag = resultFindStartTag + resultFindEndTag;
	    					}
	    					else
	    					{
	    						completeTag = resultFindStartTag;
	    					}
	    					completeTag = completeTag.replaceAll("\\s+", " ");
	    					if(finalResult(completeTag, lineNumber, pathname))
	    					{
	    						correctList.add("Result: true	Filename: " + pathname + "	LineNumber : " + lineNumber + " \t Content: " + "\t" + completeTag);
	    					}
	    					else
	    					{
	    						failureList.add("Result: false	Filename: " + pathname + "	LineNumber : " + lineNumber + " \t Content: " + "\t" + completeTag);
	    					}
	    				}
	    				 
	    			}
	    		}
	    		lineNumber++;
	        }
	    	for(int i=0;i<correctList.size();i++)
	    	{
	    	    System.out.println(correctList.get(i));
	    	} 
	    	for(int j=0;j<failureList.size();j++)
	    	{
	    	    System.out.println(failureList.get(j));
	    	} 
	        return fileContents.toString();
	    } 
	    finally
	    {
	        scanner.close();
	    }
	    
	}
	
	private static boolean finalResult(String contents, Integer line, String file) throws IOException 
	{
		String content = contents;
		if(content.contains("c:") || content.contains("cms:") || content.contains("ecicov:"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private static boolean findCov(String content, Integer indexNumber, String searchString) throws IOException 
	{
		String contentFound;
		contentFound = content.substring(indexNumber + 2, indexNumber + 5);
	    if (contentFound.equals(searchString))
		{
	    	return true;
		}
	    else
	    {
	    	return false;
	    }
	}
	
	private static boolean findStartTag(String content)
	{
		if (content.contains("<"))
		{
			return true;
		}
	    else
	    {
	    	return false;
	    }
	}
	
	private static String findStart(String pathname, Integer lineNumber, Integer indexNumber) throws IOException 
	{
		File file = new File(pathname);
	    StringBuilder fileContents = new StringBuilder((int)file.length());
	    Integer lineNumberEnd = 1;
	    Integer startLineNumber = lineNumber;
	    String startLine = "<";
	    String contentFound;
	    String contentCheck = null;
	    String contentCheckFirst = null;
	    String contentCheckEnd = null;
	    Integer startPoint;
	    Integer endPoint;
	    try 
	    {
	    	for(int i=0;i<3;i++)
	    	{
	    		Scanner scannerStart = new Scanner(file);
	    		lineNumberEnd = 1;
		    	while(scannerStart.hasNextLine())
		    	{
	    			String contentLine = scannerStart.nextLine();
	    			if (lineNumberEnd.equals(startLineNumber))
	    			{
	    				if (startLineNumber.equals(lineNumber))
		    			{
		    				contentCheckFirst = contentLine.substring(0, indexNumber);
		    				contentCheckEnd = contentLine;
		    				contentCheck = contentLine.substring(0, indexNumber);
		    			}
	    				else
	    				{
	    					contentCheck = contentLine;
	    				}
	    				if (contentCheck.contains(startLine))
	    				{
	    					startPoint = contentCheck.lastIndexOf(startLine);
	    					endPoint = contentCheck.lastIndexOf('>');
	    					if (endPoint > startPoint && endPoint != -1)
	    					{	
	    						contentFound = contentCheckEnd;
	    						scannerStart.close();
	    						return contentFound;
	    					}
	    					if (startLineNumber != lineNumber)
	    	    			{
	    						contentFound = contentCheck.substring(startPoint) + " " + contentCheckFirst;
	    	    			}
	    					else
	    					{
	    						contentFound = contentCheck.substring(startPoint);
	    					}	
	    					scannerStart.close();
	    					return contentFound;
	    				}
			    	}
		    		lineNumberEnd++;
		    	}
		    	scannerStart.close();
		    	
		    	startLineNumber = startLineNumber-1;
	    	}
	    	return fileContents.toString();
	    } 
	    finally
	    {
	    	//scannerStart.close();
	    }
	}
	
	private static String findEnd(String pathname, Integer lineNumber, Integer indexNumber) throws IOException 
	{
		File file = new File(pathname);
	    StringBuilder fileContents = new StringBuilder((int)file.length());
	    Integer lineNumberEnd = 1;
	    Integer startLineNumber = lineNumber;
	    String endLine = ">";
	    String contentCheck = null;
	    String contentCheckFirst = null;
	    String contentCheckEnd = null;
	    String contentFound = null;
	    Integer startPoint;
	    Integer endPoint;
	    try 
	    {
	    	for(int i=0;i<3;i++)
	    	{
	    		Scanner scanner = new Scanner(file);
	    		lineNumberEnd = 1;
		    	while(scanner.hasNextLine()) 
		    	{
	    			String contentLine = scanner.nextLine();
	    			if (lineNumberEnd.equals(startLineNumber))
	    			{
	    				if (startLineNumber.equals(lineNumber))
	    				{
	    					contentCheckFirst = contentLine.substring(indexNumber, contentLine.length());
	    					contentCheckEnd = contentLine;
	    					contentCheck = contentLine.substring(indexNumber, contentLine.length());
	    				}
	    				else
	    				{
	    					contentCheck = contentLine;
	    				}
	    				if (contentCheck.contains(endLine))
	    				{
	    					startPoint = contentCheck.indexOf('<');
	    					endPoint = contentCheck.indexOf('>');
	    					if (startLineNumber != lineNumber)
	    					{

	    						if (endPoint > startPoint && startPoint != -1)
		    					{	
	    							contentFound = contentCheckEnd;
		    						scanner.close();
		    						return contentFound;
		    					}
	    						else
	    						{
	    							contentFound = contentCheckFirst + " " + contentCheck.substring(0, endPoint + 1);
	    						}
	    					}
	    					else
	    					{
	    						contentFound = contentCheck.substring(0, endPoint + 1);
	    					}
	    				scanner.close();
	    				return contentFound;
		    			}
	    				else
	    				{
	    					break;
	    				}
	    			}
					lineNumberEnd++;
		        }
		    	scanner.close();
		    	startLineNumber = startLineNumber+1;
	    	}
		   return fileContents.toString();
	    } 
	    finally
	    {
	    	//scannerStart.close();
	    }
	}
	
	public static void main(String[] args) throws IOException 
	{
		printListOfFilesFromDirectory("C:/SearchString/sourceFile");
	}
	
	public static void printListOfFilesFromDirectory(final String path) throws IOException 
	{
        final File folder = new File(path);
        final String firstSearch = "${";
        final File[] listOfFiles = folder.listFiles(new FilenameFilter() 
        {
            @Override
            public boolean accept(final File dir, final String name)
            {
                if (new File(dir, name).isDirectory()) 
                {
                    return true;
	            }
	            if (name != null) 
	            {
                   return name.endsWith(".jsp") || name.endsWith(".tag");
	            }
                return false;
            }
        });
        if (listOfFiles != null)
        {
            for (int i = 0; i < listOfFiles.length; i++)
            {
            	if (listOfFiles[i].isFile()) 
            	{
                    System.out.println("File " + listOfFiles[i].getAbsolutePath());
                    System.out.println(readFile(listOfFiles[i].getAbsolutePath(),firstSearch));
            		System.out.println("------------------------------------------------------");
            		System.out.println("------------------------------------------------------");
            	} 
            	else if (listOfFiles[i].isDirectory()) 
            	{
                    printListOfFilesFromDirectory(listOfFiles[i].getAbsolutePath());
                }
            }
        }
        else 
        {
            System.out.println("Directory doesn't exist");
        }
    }
}
