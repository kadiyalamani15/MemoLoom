//package application;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.RandomAccessFile;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//public class FlashCardList {
//	private String user;
//	private String filePath = "resources/data.csv"; // Ensure path is correct or configurable
//	private Map<String, FlashCard> flashCards = new HashMap<>();
//
//	public FlashCardList(String user) {
//		this.user = user;
//	}
//
//	// Method to get unique sets for the user
//	public Set<String> getUserSets() {
////    	System.out.println("Debug - Fired getUserSets");
//		Set<String> sets = new HashSet<>();
//		try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {
//			String line;
//			while ((line = br.readLine()) != null) {
//				// Skip empty lines or lines that effectively contain only commas
//				if (line.trim().isEmpty() || line.matches("^,+$")) {
//					System.out.println("Skipping empty or malformed line: " + line);
//					continue;
//				}
//
//				String[] values = line.split(",");
//				// Check if array has the minimum expected length and the user matches
//				if (values[0].equals(this.user) && !values[1].isEmpty()) {
//					sets.add(values[1]); // Add set name
//				}
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return sets;
//	}
//
//	// Method to load flashcards filtered by username and set name
//	public void loadFlashCards(String setName) {
//		String line;
//		try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {
//			while ((line = br.readLine()) != null) {
//				String[] values = line.split(",");
//				// Ensure the CSV has the correct number of fields and matches the user and set
//				// name
//				if (values[0].equals(this.user) && values[1].equals(setName)) {
//					// Parse fields based on the new order: userName, setName, timeStamp, question,
//					// answer, visited
//					FlashCard card = new FlashCard(
//							values[3], // question
//							values[4], // answer
//							values[0], // creator (userName)
//							values[1] // setName
////							values[2] // timestamp
//					);
//					System.out.println(values[4]);
//					this.flashCards.put(card.getQuestion(), card);
//				}
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	// Method to retrieve all loaded cards
//	public List<FlashCard> getAllCards() {
//		return new ArrayList<>(flashCards.values());
//	}
//
//	// Method to add a new set (only metadata)
//	public void addSet(String setName) {
//		String timestamp = LocalDateTime.now().toString();
//		// Create the CSV line to add. Ensure fields are in correct order and properly
//		// formatted.
//		String lineToAdd = String.format("%s,%s,%s,,,", this.user, setName, timestamp); // Assuming user, setName,
//																						// timestamp,
//		// then empty question, answer,
//		// visited
//
//		// Debugging output to verify the line format
//		System.out.println("Debug - Adding line to CSV: " + lineToAdd);
//
//		// Ensure the file ends with a new line
//		ensureFileEndsWithNewLine();
//
//		// Append the new line to the CSV file
//		try (PrintWriter out = new PrintWriter(new FileWriter(filePath, true))) {
//			out.println(lineToAdd);
//		} catch (IOException e) {
//			e.printStackTrace();
//			System.out.println("Error writing to file: " + e.getMessage());
//		}
//	}
//
//	private void ensureFileEndsWithNewLine() {
//		File file = new File(this.filePath);
//		if (file.length() > 0) {
//			try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
//				long length = raf.length();
//				if (length > 1) {
//					raf.seek(length - 1);
//					byte lastByte = raf.readByte();
//					if (lastByte != '\n' && lastByte != '\r') {
//						raf.writeBytes(System.lineSeparator());
//					}
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//				System.out.println("Error ensuring new line in file: " + e.getMessage());
//			}
//		}
//	}
//
//	// Method to rename a set
//	public void renameSet(String oldName, String newName) {
//		List<String> fileContent;
//		try {
//			fileContent = new ArrayList<>(Files.readAllLines(Paths.get(filePath)));
//			fileContent = fileContent.stream().map(line -> {
//				String[] parts = line.split(",");
//				if (parts[0].equals(user) && parts[1].equals(oldName)) {
//					parts[1] = newName;
//					return String.join(",", parts);
//				}
//				return line;
//			}).collect(Collectors.toList());
//			Files.write(Paths.get(filePath), fileContent);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public boolean deleteSet(String setName) {
//		try {
//
//			boolean found = false;
//			List<String> lines = new ArrayList<>();
//			try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {
//				String line;
//				while ((line = br.readLine()) != null) {
//					String[] values = line.split(",");
//					if (!(values[1].equals(setName) && values[0].equals(this.user))) {
//						lines.add(line); // Keep lines that don't match the deletion criteria
//					} else {
//						found = true;
//					}
//				}
//			}
//
//			if (found) {
//				try (PrintWriter pw = new PrintWriter(new FileWriter(this.filePath))) {
//					for (String line : lines) {
//						pw.println(line); // Write each line back to the file
//					}
//				}
//				return true;
//			}
//			return false;
//		} catch (IOException e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
//
//}

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.util.Callback;

public class FlashCardList {
    private String user;
    private String filePath = "resources/data.csv"; // Ensure path is correct or configurable
  
    // Method to get unique sets for the use


    // ... methods to add, remove flashcards etc.



        private List<FlashCard> flashCards = new ArrayList<>();

        // ... methods to add, remove flashcards etc.

        public List<FlashCard> searchByQuestion1(String searchText) {
            return flashCards.stream()
                .filter(flashCard -> flashCard.getQuestion().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
        }
    

    public Set<String> getUserSets() {
        Set<String> sets = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty() || line.matches("^,+$")) {
                    continue;
                }
                String[] values = parseCsvLine(line);
                if (values[0].equals(this.user) && !values[1].isEmpty()) {
                    sets.add(values[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sets;
    }

    // Method to load flashcards filtered by username and set name
    public void loadFlashCards(String setName) {
        try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = parseCsvLine(line);
                if (values[0].equals(this.user) && values[1].equals(setName)) {
                    FlashCard card = new FlashCard(
                            values[3], // question
                            values[4], // answer
                            values[0], // creator (userName)
                            values[1] // setName
                    );
                    ((Object) this.flashCards).put(card.getQuestion(), card);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    // Method to retrieve all loaded cards
  

    // Method to add a new set (only metadata)
    public void addSet(String setName) {
        String timestamp = LocalDateTime.now().toString();
        String lineToAdd = String.format("\"%s\",\"%s\",\"%s\",,,", this.user, setName, timestamp);
        System.out.println("Debug - Adding line to CSV: " + lineToAdd);
        ensureFileEndsWithNewLine();
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
            fileContent = Files.readAllLines(Paths.get(filePath));
            List<String> newContent = fileContent.stream().map(line -> {
                String[] parts = parseCsvLine(line);
                if (parts[0].equals(user) && parts[1].equals(oldName)) {
                    parts[1] = newName;
                    return String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"", parts[0], parts[1], parts[2], parts[3], parts[4]);
                }
                return line;
            }).collect(Collectors.toList());
            Files.write(Paths.get(filePath), newContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteSet(String setName) {
        boolean found = false;
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = parseCsvLine(line);
                if (!(values[1].equals(setName) && values[0].equals(this.user))) {
                    lines.add(line);
                } else {
                    found = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (found) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(this.filePath))) {
                for (String line : lines) {
                    pw.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }
    public List<FlashCard> search(String query) {
        List<FlashCard> foundFlashCards = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Assuming your CSV has a specific format and parseCsvLine is a method that splits the CSV line into a String array
                String[] values = parseCsvLine(line);
                // Assuming the question is in the second column (index 1) of your CSV
                if (values[1].toLowerCase().contains(query.toLowerCase())) {
                    // Assuming FlashCard has a constructor that takes an array of String values
                    FlashCard flashCard = new FlashCard(values);
                    foundFlashCards.add(flashCard);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return foundFlashCards;
    }

	public List<FlashCard> searchByQuestion(String searchText) {
		// TODO Auto-generated method stub
		return null;
	}


	


}