package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserManager {
    private static UserManager instance;
    private String userName;
    private String filePath = "resources/data.csv";

    private UserManager() { }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
 // Helper method to parse CSV line handling quoted fields
 	private String[] parseCsvLine(String line) {
 		List<String> tokens = new ArrayList<>();
 		Matcher m = Pattern.compile("\"([^\"]*)\"|(?<=,|^)([^,]*)(?=,|$)").matcher(line);
 		while (m.find()) {
 			tokens.add(m.group(1) != null ? m.group(1) : m.group(2));
 		}
 		return tokens.toArray(new String[0]);
 	}
    
    public Set<String> getUsers(){
    	Set<String> userSet = new HashSet<String>();
    	
    	try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = parseCsvLine(line);
				
				userSet.add(values[0]);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userSet;
    	
    }
    
	private void ensureFileEndsWithNewLine() {
		File file = new File(this.filePath);
		if (file.length() > 0) {
			try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
				long length = raf.length();
				if (length > 1) {
					raf.seek(length - 1);
					byte lastByte = raf.readByte();
					if (lastByte != '\n' && lastByte != '\r') {
						raf.writeBytes(System.lineSeparator());
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Error ensuring new line in file: " + e.getMessage());
			}
		}
	}
    
    public void createUser() {
    	String lineToAdd = String.format("\"%s\",\"\",\"\",\"\",\"\",\"\"", this.userName);
		System.out.println("Debug - Adding line to CSV: " + lineToAdd);
		ensureFileEndsWithNewLine();
		try (PrintWriter out = new PrintWriter(new FileWriter(filePath, true))) {
			out.println(lineToAdd);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error writing to file: " + e.getMessage());
		}
    }
}
