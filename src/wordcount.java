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
    		     flag=1;                   //��ÿ�ֲ������б��
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
                      Pattern.MULTILINE + Pattern.DOTALL);    // ע��ƥ����(ƥ�䵥�С����С��ĵ�ע��)  
        	  Pattern nullLinePattern = Pattern.compile("^\\s*$");   // �հ���ƥ������ƥ��س���tab�����ո�  
        	  
              Pattern codeLinePattern = Pattern.compile("(?!import|package).+;\\s*(((//)|(/\\*+)).*)*",  
                      Pattern.MULTILINE + Pattern.DOTALL);// ������ƥ�������ԷֺŽ���Ϊһ����Ч���,��������import��package��䣩
        	 
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
    	  String result=name.getName()+",������/����/ע����:"+codeline+"/"+nullline+"/"+annotationline+"\r\n";
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
    		  String s=br.readLine();         //���ж�ȡ
    		  charnum=s.length()+charnum+1;
    		  String list[]=s.split(" |,");
    		  //wordnum+=list.length;
    		  for(int i=0;i<list.length;i++){
    			  if(lists.isEmpty()){
    				  if(!Pattern.matches("\\s*",list[i])){  //ͣ�ôʱ�Ϊ��
    					  wordnum++;
    				  }
    			  }
    			  
    			  else {
    				  if(!lists.contains(list[i])&&!Pattern.matches("\\s*",list[i])){ //ͣ�ôʱ�Ϊ��
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
    		      String result=name.getName()+",�ַ�����"+charnum+"\r\n";
    		      output.write(result.getBytes());
    		      System.out.println(result);
    		      break;
    	      case 2:
    		      String result1=name.getName()+",��������"+wordnum+"\r\n";
    		      output.write(result1.getBytes());
    		      System.out.println(result1);
    		      break;
    	      case 3:
    		      String result2=name.getName()+",������"+linenum+"\r\n";
    		      output.write(result2.getBytes());
    		      System.out.println(result2);
    		      break;
    	  }
      }
}
