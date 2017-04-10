
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.List;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Akshatha
 */
public class LogAnalyzer {

    public static void print(String fileName, List<Pair> list) throws FileNotFoundException {
        PrintStream output = new PrintStream(new FileOutputStream(fileName)); 
        
        for(int i = list.size() - 1; i >=0; i--) {
            if(fileName == "resources.txt") {
               output.println(list.get(i).getFirst()); 
            } else {
               output.println(list.get(i).getFirst()+ "," + list.get(i).getSecond()); 
            }
                
        }
        output.close();
    }
    
    public static void main(String[] args) {
        try {
            File file = new File("/Users/Akshatha/Documents/Notes/Insight/log.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            TopN feature1 = new TopN();
            TopN feature2 = new TopN();
            TopN feature3 = new TopN();
            SlidingWindow slidingWindow = new SlidingWindow(feature3);
            String line = null;
            while((line = bufferedReader.readLine()) != null) {
                LogRecordParser logParser = new LogRecordParser(line);
                feature1.add(logParser.getHostAddress(), 1);
                feature2.add(logParser.getRequest(), logParser.getByteSize());
                slidingWindow.add(logParser.getDateAndTime());
            }
            fileReader.close();
            System.out.println("Writing feature1 output to hosts.txt");
            print("hosts.txt", feature1.getTop(10));
            System.out.println("Writing feature2 output to resources.txt");
            print("resources.txt", feature2.getTop(10));
            System.out.println("Writing feature3 output to hours.txt");
            print("hours.txt", feature3.getTop(10));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
