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
import java.time.format.DateTimeFormatter;
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

class SetDetails {
	private String setName;
	private LocalDateTime timestamp;

	public SetDetails(String setName, LocalDateTime timestamp) {
		this.setName = setName;
		this.timestamp = timestamp;
	}

	public String getSetName() {
		return setName;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}
}

public class FlashCardList {
	private String user;
	private String filePath = "resources/data.csv";
	private Map<String, FlashCard> flashCards = new HashMap<>();

	public FlashCardList(String user) {
		this.user = user;
	}

	public List<SetDetails> getUserSets() {
		Map<String, SetDetails> uniqueSets = new HashMap<>();
		try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.trim().isEmpty() || line.matches("^,+$")) {
					System.out.println("Skipping line: " + line);
					continue;
				}
				String[] values = parseCsvLine(line);
				System.out.println("Debug - User: " + this.user + " Line User: " + values[0]);
				if (values.length > 2 && values[0].equals(this.user) && !values[1].isEmpty()) {
					try {
						LocalDateTime timestamp = LocalDateTime.parse(values[2], DateTimeFormatter.ISO_LOCAL_DATE_TIME);
						if (!uniqueSets.containsKey(values[1])) { // Check if the set name already exists
							uniqueSets.put(values[1], new SetDetails(values[1], timestamp));
						}
					} catch (Exception e) {
						System.out.println("Error parsing date for line: " + line + " Error: " + e.getMessage());
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Total sets found: " + uniqueSets.size());
		return new ArrayList<>(uniqueSets.values()); // Convert the values to a list to return
	}

	// Method to load flashcards filtered by username and set name
	public void loadFlashCards(String setName) {
		try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = parseCsvLine(line);
				if (values[0].equals(this.user) && values[1].equals(setName)) {
					FlashCard card = new FlashCard(values[3], // question
							values[4], // answer
							values[0], // creator (userName)
							values[1] // setName
					);
					this.flashCards.put(card.getQuestion(), card);
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
	public List<FlashCard> getAllCards() {
		return new ArrayList<>(flashCards.values());
	}

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
					return String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"", parts[0], parts[1], parts[2], parts[3],
							parts[4]);
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

	public void renameFlashCard(FlashCard oldFlashCard, FlashCard newFlashCard) {
		List<String> fileContent;
		try {
			fileContent = Files.readAllLines(Paths.get(filePath));
			List<String> newContent = fileContent.stream().map(line -> {
				String[] parts = parseCsvLine(line);
				if (parts[0].equals(user) && parts[1].equals(oldFlashCard.getSetName())
						&& parts[3].equals(oldFlashCard.getQuestion())) {
					parts[3] = newFlashCard.getQuestion();
					parts[4] = newFlashCard.getAnswer();
					return String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"", parts[0], parts[1], parts[2], parts[3],
							parts[4]);
				}
				return line;
			}).collect(Collectors.toList());
			Files.write(Paths.get(filePath), newContent);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean deleteQuestion(String questionName, String setName, String userName) {
		boolean found = false;
		List<String> lines = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = parseCsvLine(line);
				if (!(values[1].equals(setName) && values[0].equals(this.user) && values[3].equals(questionName))) {
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
}
