

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class InputStreamEx {
    public static void main(String[] args) {
    	try{
    		System.out.println();
            //���� ��ü ����
            File file = new File("movie_data.txt");
            //�Է� ��Ʈ�� ����
            
            System.out.println("      hi ,  hello     ");
            String[] ar2 = "      hi ,  hello     ".split(",");
            System.out.println(ar2[0].trim());
            System.out.println(ar2[1].trim());
            
            FileReader filereader = new FileReader(file);
            //�Է� ���� ����
            BufferedReader bufReader = new BufferedReader(filereader);
            String line = "";
            int count=0;
            
            
            
            String s2= "hi|hello";
            String[] ar = s2.split("\\|");
            for(String a: ar) {
            	System.out.println(a);
            }
            
            while((line = bufReader.readLine()) != null){
            	System.out.println(count);
            	count++;
            	String[] array = line.split("\\|");
            	String insert_data = "INSERT INTO movie VALUES ('"+ array[1] +"', '"+ array[2] +"', '"+array[3]+"', '"+array[4]+"', '"+array[5]+"', " +array[6] + ", " + array[7] + ", " + array[8] + ", '" + array[9]+ "');";
            	for(int i=0;i<array.length;i++) {
            		System.out.print(" "+array[i]);
            	}
            	System.out.println();
                //System.out.println(line);
            }
            //.readLine()�� ���� ���๮�ڸ� ���� �ʴ´�.            
            bufReader.close();
        }catch (FileNotFoundException e) {
            // TODO: handle exception
        }catch(IOException e){
            System.out.println(e);
        }
    }
}