	package FoodPriceAnalysis;
	
	import java.io.*;
	import java.util.*;
	
	class NodeTrie {
		Map<Character, NodeTrie> child;
		Set<String> documents;
	
		NodeTrie() {
			child = new HashMap<>();
			documents = new HashSet<>();
		}
	}
	
	
	public class Inverted_Indexing {
		private NodeTrie root;
		
		//initializing the root
		public Inverted_Indexing() {
			root = new NodeTrie();
		}
	
		//getRoot method to return the root node
		public NodeTrie getRoot() {
			return root;
		}
	
		//insert method to insert words in the tree
		public void insert(NodeTrie node, String word, String document) {
			NodeTrie current = node;
			for (char c : word.toCharArray()) {
				current.child.putIfAbsent(c, new NodeTrie());
				current = current.child.get(c);
				current.documents.add(document);
			}
		}
	
		//search method to find specific words along with the documents in which they are present
		public Set<String> search(NodeTrie node, String word) {
			NodeTrie current = node;
			for (char c : word.toCharArray()) {
				if (!current.child.containsKey(c)) {
					return Collections.emptySet();
				}
				current = current.child.get(c);
			}
			return current.documents;
		}
	
		//a function to print the entire tree 
		public void Print_entiretree(NodeTrie node, String prefix) {
			if (node == null)
				return;
	
			if (!prefix.isEmpty()) {
				System.out.println(prefix + " -> " + node.documents);
			}
	
			for (Map.Entry<Character, NodeTrie> entry : node.child.entrySet()) {
				Print_entiretree(entry.getValue(), prefix + entry.getKey());
			}
		}
	
		//Main method which read the csv files from directory Assg3, we read specific colum containing product name, convert all text into lowercase 
		//and remove characters other than letters and digits. We insert the text in a trie and later based on user inputted word we search the word to find list of entries in which 
		//that specific word is present
		public static void main(String[] args) {
			Inverted_Indexing invertedIndex = new Inverted_Indexing();
	
			String dir_path = "C:\\Users\\sanja\\OneDrive\\Desktop\\ACC\\Assg3\\";
			int Txtcol_Index = 0;
			
	
			File directory_name = new File(dir_path);
			File[] fl = directory_name.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));
	
			if (fl != null) {
				for (File fl1 : fl) {
					try (BufferedReader buffer_read = new BufferedReader(new FileReader(fl1))) {
						String line;
						int liNum = 1;
						while ((line = buffer_read.readLine()) != null) {
							String[] cl = line.split(",");
	
							if (cl.length > Txtcol_Index) {
								String text = cl[Txtcol_Index];
								String[] words_seq = text.split("\\s+");
	
								//read text and convert it to lower case along with removing any extra special character except A to Z and digits.
								for (String wo : words_seq) {
									wo = wo.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
									if (!wo.isEmpty()) {
										//Inserting text in the trie
										invertedIndex.insert(invertedIndex.root, wo, fl1.getName() + ":" + liNum  +" " +text);
									}
								}
								liNum++;
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			
			Scanner new_sca = new Scanner(System.in);
			String wordsearch;
			char choice01;
			NodeTrie root = invertedIndex.getRoot();
			invertedIndex.Print_entiretree(root, "");
			do {
				//Take specific word to search from the user using Scanner 
				System.out.println(
						"\nEnter a word to find from different documents under directory input or (press * to exit).");
				wordsearch = new_sca.nextLine();
				choice01 = wordsearch.charAt(0);
	
				if (choice01 != '0') {
					//Use Search method to search the word from all CSV files and print the output which contains the File name and line number in which the specific word is present
					Set<String> results = invertedIndex.search(invertedIndex.root, wordsearch);
					if (!results.isEmpty()) {
						System.out.println(wordsearch + " -> " + results);
					} else {
						System.out.println("The word is not present in any document");
					}
				}
			} while (choice01 != '*');
		}
	}
