import java.io.*;
import java.util.Vector;
import java.util.regex.*;

public class wordcount {
      public static Vector<File> source;
      public static int flag;
      private File outfile;
      public static String outpath;
      private static OutputStream output;
      
      public wordcount(Vector<File> source,String outpath)throws IOException{
    	  this.source=source;
    	  this.outpath=outpath;
    	  this.outfile=new File(outpath);
    	  this.output=new FileOutputStream(outfile);
      }

      
      public void count(Vector<String> parameters)throws IOException{
    	  if(parameters.contains("-c")){
    		  flag=1;
    		  countall(source.firstElement());
    	  }
    	  if(parameters.contains("-w")){
    		  flag=2;
    		  countall(source.firstElement());
    	  }
    	  if(parameters.contains("-l")){
    		  flag=3;
    		  countall(source.firstElement());
    	  }
      }
      
      public static void countall(File name)throws IOException{
    	  int charnum=0;
    	  int wordnum=0;
    	  int linenum=0;
    	  try{
    	  InputStreamReader reader=new InputStreamReader(new FileInputStream(name)) ;
    	  BufferedReader br=new BufferedReader(reader);
    	  while(br.read()!=-1){
    		  String s=br.readLine();
    		  charnum+=s.length();
    		  wordnum+=s.split(" |,").length;
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
