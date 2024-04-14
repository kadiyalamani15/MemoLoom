package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class FlashCardList {
	private String user;
	private String filePath = "resources/data.csv"; // Ensure path is correct or configurable
	private Map<String, FlashCard> flashCards = new HashMap<>();

	public FlashCardList(String user) {
		this.user = user;
	}

	// Method to get unique sets for the user
	public Set<String> getUserSets() {
//    	System.out.println("Debug - Fired getUserSets");
		Set<String> sets = new HashSet<>();
		try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				// Skip empty lines or lines that effectively contain only commas
				if (line.trim().isEmpty() || line.matches("^,+$")) {
					System.out.println("Skipping empty or malformed line: " + line);
					continue;
				}

				String[] values = line.split(",");
				// Check if array has the minimum expected length and the user matches
				if (values[0].equals(this.user) && !values[1].isEmpty()) {
					sets.add(values[1]); // Add set name
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sets;
	}

	// Method to load flashcards filtered by username and set name
	public void loadFlashCards(String setName) {
		String line;
		try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				// Ensure the CSV has the correct number of fields and matches the user and set
				// name
				if (values[0].equals(this.user) && values[1].equals(setName)) {
					// Parse fields based on the new order: userName, setName, timeStamp, question,
					// answer, visited
					FlashCard card = new FlashCard(values[3], // question
							values[4], // answer
							"", // example (not provided in new format)
							values[0], // creator (userName)
							values[1], // setName
							values[2] // timestamp
					);
					this.flashCards.put(card.getQuestion(), card);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Method to retrieve all loaded cards
	public List<FlashCard> getAllCards() {
		return new ArrayList<>(flashCards.values());
	}

	// Method to add a new set (only metadata)
	public void addSet(String setName) {
		String timestamp = LocalDateTime.now().toString();
		// Create the CSV line to add. Ensure fields are in correct order and properly
		// formatted.
		String lineToAdd = String.format("%s,%s,%s,,,", this.user, setName, timestamp); // Assuming user, setName,
																						// timestamp,
		// then empty question, answer,
		// visited

		// Debugging output to verify the line format
		System.out.println("Debug - Adding line to CSV: " + lineToAdd);
		
		// Ensure the file ends with a new line
        ensureFileEndsWithNewLine();

		// Append the new line to the CSV file
		try (PrintWriter out = new PrintWriter(new FileWriter(filePath, true))) {
			out.println(lineToAdd);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error writing to file: " + e.getMessage());
		}
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

	// Method to rename a set
	public void renameSet(String oldName, String newName) {
		List<String> fileContent;
		try {
			fileContent = new ArrayList<>(Files.readAllLines(Paths.get(filePath)));
			fileContent = fileContent.stream().map(line -> {
				String[] parts = line.split(",");
				if (parts[0].equals(user) && parts[1].equals(oldName)) {
					parts[1] = newName;
					return String.join(",", parts);
				}
				return line;
			}).collect(Collectors.toList());
			Files.write(Paths.get(filePath), fileContent);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean deleteSet(String setName) {
		try {

			boolean found = false;
			List<String> lines = new ArrayList<>();
			try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {
				String line;
				while ((line = br.readLine()) != null) {
					String[] values = line.split(",");
					if (!(values[1].equals(setName) && values[0].equals(this.user))) {
						lines.add(line); // Keep lines that don't match the deletion criteria
					} else {
						found = true;
					}
				}
			}

			if (found) {
				try (PrintWriter pw = new PrintWriter(new FileWriter(this.filePath))) {
					for (String line : lines) {
						pw.println(line); // Write each line back to the file
					}
				}
				return true;
			}
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

}
