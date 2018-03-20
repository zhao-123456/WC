import java.io.*;
import java.util.Vector;

public class main {
    public static Vector<String> parameters;
    public static Vector<File> source;
    public static String nowpath;
    public static String outpath;
    public static String stopname;
    public static Vector<String> lists;
    public static void main(String args[]){
    	getparameters(args);
    	File f=new File("");
    	nowpath=f.getAbsolutePath(); //��ȡ��ǰ���ڵľ���·��
    	//System.out.println(nowpath);
    	try{
    	    wordcount wc=new wordcount(source,outpath,lists);
    	    wc.count(parameters);
    	}catch (Exception e){
            System.out.println("�д���");
            return;
        }
    }
    
    public static void getparameters(String[] args){
    	outpath="result.txt";
    	parameters=new Vector<String>();
    	source=new Vector<File>();   //��ʼ���������ָ�����
        lists=new Vector<String>();
    	for(int i=0;i<args.length;i++){
    		if(args[i].equals("-o")){   //�жϡ�-o�������Ƿ�����ļ���
    			if(i<args.length-1){
    				outpath=args[i+1]; 
    			}
    			else{
    				 System.out.println("wrong");
    			}
    				
    		}
    		else if(args[i].equals("-c")||args[i].equals("-w")||args[i].equals("-l")||args[i].equals("-a")){
    			parameters.addElement(args[i]);   //����������parameters������
    		}
    		else if(args[i].equals("-s")){
    			for(int j=0;j<args.length;j++){
    				if(args[j].contains("*")){
    					String type=args[j].substring(args[j].indexOf("."));//��ȡ�ļ�������
    					ListFile(type);
    				}
    			}
    		}
    		else if(args[i].equals("-e")){
    			stopname=args[i+1];
    			 try{
    				 Liststop(stopname);
    				 }
                 catch(Exception e){
                     e.printStackTrace();
                 }
    		}
    		else{
    			source.addElement(new File(args[i]));
    		}
    	}   	   	
    }
    
    public static void Liststop(String stopname)throws IOException{
    	File file=new File(stopname);
    	InputStreamReader reader=new InputStreamReader(new FileInputStream(file));
        BufferedReader br=new BufferedReader(reader);
        while(br.read()!=-1){
        	String s=br.readLine();
        	String liststop[]=s.split(" ");
        	for(int i=0;i<liststop.length;i++){
        		lists.addElement(liststop[i]);
        	}        	
        }
        reader.close();
    }
    
    public static void ListFile(String type){
    	File file=new File(nowpath);
    	File[] files=file.listFiles();  //����ǰ·���������ļ�װ���ļ��б���
    	if(files==null){
    		 System.out.println("null");
    	}
    	for(int i=0;i<files.length;i++){
    		if(files[i].isFile()&&files[i].getName().endsWith(type)){//�ж��Ƿ����ض����ͽ�β���ļ�
    			source.addElement(files[i]);
    		}
    		else if (files[i].isDirectory()) {
    			ListFile(type);//�ݹ�
    		}
    	}
    }
}
