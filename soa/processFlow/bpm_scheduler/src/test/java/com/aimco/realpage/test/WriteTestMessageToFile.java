package com.aimco.realpage.test;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;

public class WriteTestMessageToFile {
    
    public WriteTestMessageToFile() {
    }
    
    public void writeMessage(String inputDirectory, String fileExtension, String fileContents) {        
        String prefix = Long.toString(Calendar.getInstance().getTimeInMillis());
        File x = null;
        try {
            x = File.createTempFile(prefix + "_",fileExtension,new File(inputDirectory));
            BufferedWriter out = new BufferedWriter(new FileWriter(x));
            out.write(fileContents.toCharArray());
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }
    
    public static void main(String args[]) throws Exception
    {               
        WriteTestMessageToFile stm = new WriteTestMessageToFile();
        stm.writeMessage(args[0], args[1], args[2]);
    }
    
}
