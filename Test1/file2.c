import java.util.Vector;

public class wordcount {
      public static Vector<File> source;
      public static int flag;
      private File outfile;
      public static String outpath;
      private static OutputStream output;

/*ds
对方是否是
sfs*/     
      public wordcount(Vector<File> source,String outpath)throws IOException{
    	  this.source=source;
    	  this.outpath=outpath;
    	  this.outfile=new File(outpath);
    	  this.output=new FileOutputStream(outfile);
      }
