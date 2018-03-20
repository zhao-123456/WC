import java.io.*;
import java.util.Vector;

public class main {
    public static Vector<String> parameters;
    //public static String nowpath;
    public static Vector<File> source;
    public static String outpath;
    public static void main(String args[]){
    	getparameters(args);
    	//String path="C:\\Users\\lenovo\\Desktop\\Ô´´úÂë.txt";
    	File f=new File("");
    	//nowpath=f.getAbsolutePath();
    	try{
    	    wordcount wc=new wordcount(source,outpath);
    	    wc.count(parameters);
    	}catch (Exception e){
            System.out.println("ÓÐ´íÎó");
            return;
        }
    }
    
    public static void getparameters(String[] args){
    	outpath="result.txt";
    	parameters=new Vector<String>();
    	source=new Vector<File>();
    	for(int i=0;i<args.length;i++){
    		if(args[i].equals("-o")){
    			if(i<args.length-1){
    				outpath=args[i+1];
    			}
    			else{
    				 System.out.println("wrong");
    			}
    				
    		}
    		if(args[i].equals("-c")||args[i].equals("-w")||args[i].equals("-l")){
    			parameters.addElement(args[i]);
    		}
    		else{
    			source.addElement(new File(args[i]));
    		}
    	}
    }
}
