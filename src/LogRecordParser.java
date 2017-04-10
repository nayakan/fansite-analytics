/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.regex.Pattern;
import java.util.regex.Matcher;
/**
 *
 * @author Akshatha
 */
public class LogRecordParser {
    
    private static final String LOG_ENTRY_PATTERN = 
            "^([\\d.|\\@*.|\\w-'_&:,/#.]+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(.+?)\" (\\d{3}) ([\\d | \\-]+)";
    
    private static final int NUM_FIELDS = 7;
    
    private final String hostAddress;
    private final String dateAndTime;
    private final String request;
    private final String replyCode;
    private final int byteSize;
    
    public LogRecordParser(String logEntryLine) {
        Pattern pattern = Pattern.compile(LOG_ENTRY_PATTERN);
        Matcher matcher = pattern.matcher(logEntryLine);
        if (!matcher.matches() || NUM_FIELDS != matcher.groupCount()) {
            System.err.println("Bad log entry:");
            System.err.println(logEntryLine);
        }
        hostAddress = matcher.group(1);
        dateAndTime = matcher.group(4);
        request = matcher.group(5).replaceAll("^(GET )", "").replaceAll("HTTP/1.0", "");
        replyCode = matcher.group(6);
        if(!matcher.group(7).equals("-")) {
            byteSize = Integer.parseInt(matcher.group(7));
        } else {
            byteSize = 0;
        }
            
         
    }
    
    
    
    public String getHostAddress() {
        return hostAddress;
    }
    
    public String getDateAndTime() {
        return dateAndTime;
    }
    
    public String getRequest() {
        return request;
    }
    
    public String getReplyCode() {
        return replyCode;
    }
    
    public int getByteSize() {
        return byteSize;
    }

}
