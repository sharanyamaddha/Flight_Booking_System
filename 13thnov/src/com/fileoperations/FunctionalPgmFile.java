package com.fileoperations;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FunctionalPgmFile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String file="text.txt";
		long count;
		try {
		Stream<String> lines=Files.lines(Paths.get(file));
		count=lines
			.flatMap(line->Stream.of(line.split("\\W+")))
			.filter(word->word.equalsIgnoreCase("india"))
			.count();
		System.out.println("The total count of india in the given text file is "+count);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

}
