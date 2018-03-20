import java.io.*;
import java.util.Vector;
import java.util.regex.*;

public class wordcount {
      public static Vector<File> source;
      public static Vector<String> lists;
      public static int flag;
      private File outfile;
      public static String outpath;
      private static OutputStream output;
      
      public wordcount(Vector<File> source,String outpath,Vector<String> lists)throws IOException{
    	  this.source=source;
    	  this.outpath=outpath;
    	  this.outfile=new File(outpath);
    	  this.output=new FileOutputStream(outfile);
    	  this.lists=lists;
      }

      
      public void count(Vector<String> parameters)throws IOException{
    	 for(int i=0;i<source.size();i++){ 
    	     if(parameters.contains("-c")){
    		     flag=1;                   //对每种参数进行标记
    		     basiccount(source.get(i));
    	     }
    	     if(parameters.contains("-w")){
    	    	 flag=2;
    	    	 basiccount(source.get(i));
    	     }
    	     if(parameters.contains("-l")){
    	    	 flag=3;
    	    	 basiccount(source.get(i));
    	     }
    	     if(parameters.contains("-a")){
    	    	 complexcount(source.get(i));
    	     }
    	 }
      }
      
      public static void complexcount(File name)throws IOException{  
    	  int codeline=0;
    	  int nullline=0;
    	  int annotationline=0;
    	  try{
    		  InputStreamReader reader=new InputStreamReader(new FileInputStream(name)) ;
        	  BufferedReader br=new BufferedReader(reader);
        	  boolean comment = false; 
        	  Pattern annotationLinePattern = Pattern.compile("((//)|(/\\*+)|((^\\s)*\\*)|((^\\s)*\\*+/))+",   
                      Pattern.MULTILINE + Pattern.DOTALL);    // 注释匹配器(匹配单行、多行、文档注释)  
        	  Pattern nullLinePattern = Pattern.compile("^\\s*$");   // 空白行匹配器（匹配回车、tab键、空格）  
        	  
              Pattern codeLinePattern = Pattern.compile("(?!import|package).+;\\s*(((//)|(/\\*+)).*)*",  
                      Pattern.MULTILINE + Pattern.DOTALL);// 代码行匹配器（以分号结束为一行有效语句,但不包括import和package语句）
        	 
        	  while(br.read()!=-1){
        		  String s=br.readLine();
        		  if(annotationLinePattern.matcher(s).find()){
        			  annotationline++;
        		  }
        		  if(nullLinePattern.matcher(s).find()){
        			  nullline++;
        		  }
        		  if(codeLinePattern.matcher(s).find()){
        			  codeline++;
        		  }
        	  }
        	  reader.close();  
    	  }catch (Exception e){
              e.printStackTrace();
          }
    	  String result=name.getName()+",代码行/空行/注释行:"+codeline+"/"+nullline+"/"+annotationline+"\r\n";
    	  output.write(result.getBytes());
    	  System.out.println(result);
      }
      
      public static void basiccount(File name)throws IOException{ 
    	  int charnum=0;
    	  int wordnum=0;
    	  int linenum=0;
    	  try{
    	  InputStreamReader reader=new InputStreamReader(new FileInputStream(name)) ;
    	  BufferedReader br=new BufferedReader(reader);
    	  while(br.read()!=-1){
    		  String s=br.readLine();         //按行读取
    		  charnum=s.length()+charnum+1;
    		  String list[]=s.split(" |,");
    		  //wordnum+=list.length;
    		  for(int i=0;i<list.length;i++){
    			  if(lists.isEmpty()){
    				  if(!Pattern.matches("\\s*",list[i])){  //停用词表为空
    					  wordnum++;
    				  }
    			  }
    			  
    			  else {
    				  if(!lists.contains(list[i])&&!Pattern.matches("\\s*",list[i])){ //停用词表不为空
    				        wordnum++;
    				    }
    			  }
    			 
    		  }
    		  linenum++;
    	  }
    	  reader.close();
    	  }catch (Exception e){
              e.printStackTrace();
          }
    	  switch(flag){
    	      case 1:
    		      String result=name.getName()+",字符数："+charnum+"\r\n";
    		      output.write(result.getBytes());
    		      System.out.println(result);
    		      break;
    	      case 2:
    		      String result1=name.getName()+",单词数："+wordnum+"\r\n";
    		      output.write(result1.getBytes());
    		      System.out.println(result1);
    		      break;
    	      case 3:
    		      String result2=name.getName()+",行数："+linenum+"\r\n";
    		      output.write(result2.getBytes());
    		      System.out.println(result2);
    		      break;
    	  }
      }
}
