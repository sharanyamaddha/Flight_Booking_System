package com.fileoperations;
import java.io.FileReader;
import java.util.*;
import java.io.*;


public class FileSample {

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		String file="text.txt";
		int count=0;
		try {
			BufferedReader bufferReader=new BufferedReader(new FileReader(file));
			String line;
			while((line=bufferReader.readLine())!=null) {
				String[] words=line.split(" ");
				for(String word:words) {
					if(word.equalsIgnoreCase("india")) {
						count++;
					}
				}	
			}
			System.out.println("The total count of india in the given text file is "+count);
		}catch(Exception e) {
			e.printStackTrace();
		}
			

		

	}

}
