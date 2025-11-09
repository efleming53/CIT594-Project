package edu.upenn.cit5940.cit5940_project.datamanagement;

import java.util.*;

public class Trie {

    // inner class
    private class Node {
        private HashMap<Character, Node> children = new HashMap<>();

        // TODO (provide starting value)
        private boolean endOfWord = false;

    }

    // root node (has no value)
    private Node root = new Node();

    // TODO
    public void insertWord(String word) {
    	
    	Node current = root; // set current node starting at root
    	
    	int length = word.length(); // length of word
    	
    	// loop over chars in word
    	for (int i = 0; i < length; i++) {
    		Character currentChar = word.charAt(i); // get current char
    		
    		// if current char is a child of current node
    		if (current.children.containsKey(currentChar)) {
    			current = current.children.get(currentChar); // set current to that child node
    		
    		// if char not child of current node
    		} else {
    			Node newNode = new Node(); // create new node
    			current.children.put(currentChar, newNode); // put new char's node in children of current node
    			current = newNode; // make crated node current node
    		}
    	}
    	current.endOfWord = true; // after loop, set EOW to true for current node
    }

    // this implementation is given to students in the starter code
    public void insertList(String[] wordList) {
        for (String string : wordList) {
            insertWord(string);
        }
    }

    // TODO
    public boolean findWord(String word) {
    	
    	Node current = root; // set current node starting at root
    	
    	int length = word.length(); // get length of word
    	
    	// loop over chars in word
    	for (int i = 0; i < length; i++) {
    		
    		Character currentChar = word.charAt(i); // set current char
    		
    		// if current node has currentChar as a child
    		if (current.children.containsKey(currentChar)) {
    			current = current.children.get(currentChar); // set current node to current chars node
    		
    		// return false if current char not a child, means word is not there
    		} else {
    			return false;
    		}
    		
    	}
    	
    	// after loop, check if end of word is true, means word is found, return false otherwise
    	if (current.endOfWord == true) {
    		return true;
    	} else {
    		return false;
    	}
    }

    // TODO
    public void deleteWord(String word) {
    	
    deleteWordHelper(word, 0, root);
    
    return;
    	
    }

    // TODO
    public boolean deleteWordHelper(String word, int index, Node curNode) {
    	
    	// base case, reached end of word
    	if (index == word.length()) {
    		curNode.endOfWord = false; // set EOW to false
    		// since EOW now false, if node has no children, return true, false if has children
    		return curNode.children.isEmpty();
    	}
    	
    	// executes going down Trie
    	Character curChar = word.charAt(index); // get current char
    	
    	// null check next node
    	if (curNode.children.get(curChar) == null) {
    		return false;
    	}
    	
    	// recursion call
    	// curChar node in parent is removed if deleteWordHelper being called on child returns true
    	if (deleteWordHelper(word, index + 1, curNode.children.get(curChar))) {
    		curNode.children.remove(curChar);
    	}
    	
    	// executes on unwinding
    	// returns true if not EOW and hashmap of children is empty
    	return !curNode.endOfWord && curNode.children.isEmpty();
    	
    }

    // TODO
    public List<String> allWords() {
  
    	StringBuilder sb = new StringBuilder(); // init stringbuilder
    	List<String> words = new ArrayList<>(); // init list to hold all words
    	
    	allWordsHelper(root, sb, words);
    	  	
        return words;
    }

    // TODO
    public void allWordsHelper(Node node, StringBuilder accumulated, List<String> myList) {
    	
    	// base case - if EOW, add accumulated string to list
    	if (node.endOfWord == true) {
    		myList.add(accumulated.toString());
    	}
    	
    	// for every one of the current nodes children
    	for (Map.Entry<Character, Node> child : node.children.entrySet()) {
    		accumulated.append(child.getKey()); // append character to stringbuilder, as you go down trie
    		allWordsHelper(child.getValue(), accumulated, myList); // recursive call on child nodes
    		accumulated.deleteCharAt(accumulated.length() - 1); // when going back up trie, delete char to allow another path to be followed
    	}
    	
    }
}
