

package project1;

import java.util.*;

import java.lang.*;

public class Hangman {
	private static final boolean testingMode = true;
	
	//main method
	public static void main(String[] args) {
	
		RandomWord.populateWORD_ARRAY();
		
		String[] testWord = RandomWord.WORD_ARRAY;
	
		int guesses = 0;
		int spaces = 0;
		
		for(int i = 0; i < testWord.length; i++) {
		if(testingMode) {
			System.out.println(testWord[i]);
		}
		boolean valid = true;
		//have user select the difficulty
		while(valid) {
		
			valid = false;
			Scanner input = new Scanner(System.in);
			System.out.println("Enter your difficulty: (e) for easy, (i) for intermediate, or (h) for hard"); 
			String charSelect = input.next();
			
			if(charSelect.equalsIgnoreCase("e")) {
				guesses = 15;
				spaces = 4;
				
			}
			else if (charSelect.equalsIgnoreCase("i")) {
				guesses = 12;
				spaces = 3;
			}
			else if (charSelect.equalsIgnoreCase("h")) {
				guesses = 10;
				spaces = 2;
			} else {
				System.out.println("Invalid difficulty. Try again...");
				valid = true;
	
			}
		}
		String[] wordArr = testWord[i].split("");
		int[] checkerArr = new int[testWord[i].length()];
			for(int j = 0; j <= testWord[i].length()-1; j++) {
				checkerArr[j] = 0;
			
		System.out.println();
		
		boolean solved = false;
		while (guesses > 0 && solved == false) {
			guesses = guessExecution(wordArr, checkerArr, guesses, spaces, testWord[i]);
			
			for(int z = 0; z <= wordArr.length-1; z++) {
				if(checkerArr[z] != 1) {
					break;
				} else {
					solved = true;
				}
			}	
			if(solved) {
				System.out.println("You solved the puzzle!");
			}else if(guesses==0 && !solved) {
				System.out.println("You failed to guess the word... :(");
			}
		}
		continueGame();		
			}
		}
	}	
	
	//Display word for player			
	public static void printWord(String[] wordArr, int[] checkerArr) {
		for(int i = 0; i <= wordArr.length-1; i++) {
			if(checkerArr[i] == 0) {
				System.out.print("-");
			}else if (checkerArr[i] == 1) {
				System.out.print(wordArr[i]);
				
			}
		}
	}
	//Take in letter that user guesses and check spaces in array for identical letters, or let user try and solve the puzzle
	public static int guessExecution(String[] wordArr, int[] checkerArr, int numGuesses, int numSpaces, String testWord) {
		Scanner userLetter = new Scanner(System.in);
		System.out.print("Enter the letter you want to guess: "); 
		String Letter = userLetter.next();
		System.out.println();
		//user directly solve
		if(Letter.equals("solve")){
			Scanner wordGuess = new Scanner(System.in);
			System.out.print("Enter what you believe the word is: "); 
			String wordGuessed = wordGuess.next();
			System.out.println();
			if(wordGuessed.equals(testWord)){
				for(int c=0; c < wordArr.length; c++ ) {
					checkerArr[c] = 1;
				}
			}else {
				System.out.println("You did not solve the puzzle");
				numGuesses -= 1;
				System.out.println("Guesses remaining: " + numGuesses);
			}
		//User guesses letters in space
		}else {
			int correct = 0;
			boolean tooLong = false;
			while(!tooLong) {
				tooLong = true;
				String spaceString = spaceSelection(numSpaces);
				
				String[] spaces = spaceString.split("");
				int[] spaceGuessArr = new int[numSpaces];
			
				for(int i = 0; i < numSpaces; i++) {
					int x = Integer.parseInt(spaces[i]);
					spaceGuessArr[i] = x;
				}
				try {
					for(int i = 0; i < numSpaces; i++) {
						if(Letter.equals(wordArr[spaceGuessArr[i]])) {
							checkerArr[spaceGuessArr[i]] = 1;
							correct +=1;
							}
					}
				}catch(ArrayIndexOutOfBoundsException exception) {
					System.out.println("A space you selected is invalid. Try again...");
					tooLong = false;
				}	
			}
			//display based on whether user guesses correctly
			if(correct > 0) {
				System.out.println("Your guess is in the word!");
				System.out.print("The updated word is: ");
				printWord(wordArr, checkerArr);
				System.out.println("");
			}else {
				System.out.println("Your letter was not found in the spaces you provided");
				numGuesses -= 1;
			}
				
				System.out.println("Guesses remaining: " + numGuesses);	
		}
		
				
		return numGuesses;
		
	}
	//prompts user to select spaces to guess
	public static String spaceSelection(int numSpaces) { 
		String tempString = "";
		
		boolean valid = false;
		
		while(!valid) {
			if(numSpaces == 4) {
				Scanner spaceSelect = new Scanner(System.in);
				System.out.println("Enter the spaces you would like to check (separated by spaces): "); 
				String spaceString = spaceSelect.next() + spaceSelect.next() + spaceSelect.next() + spaceSelect.next();
				tempString = spaceString;
			}else if(numSpaces == 3) {
				Scanner spaceSelect = new Scanner(System.in);
				System.out.println("Enter the spaces you would like to check (separated by spaces): "); 
				String spaceString = spaceSelect.next() + spaceSelect.next() + spaceSelect.next();
				tempString = spaceString;
			}else if(numSpaces == 2) {
				Scanner spaceSelect = new Scanner(System.in);
				System.out.println("Enter the spaces you would like to check (separated by spaces): "); 
				String spaceString = spaceSelect.next() + spaceSelect.next();
				tempString = spaceString;
			}
			if (tempString.length() > numSpaces) {
				System.out.println("You entered too many spaces. Try again");
				
			}else {
				valid = true;
			}
		}
		
		return tempString;
	}

	//asks user if they want to continue playing game after a round is over
	public static void continueGame() {
		boolean validEntry = false;
		while(validEntry == false) {
			Scanner continueChoice = new Scanner(System.in);
			System.out.print("Do you want to keep playing? Enter 'y' for yes, or 'n' for no "); 
			String contChoice = continueChoice.next();
			System.out.println();
			
			
			if(contChoice.equals("n")) {
				System.out.println("Thanks for playing");
				System.exit(0);
			}else if (contChoice.equals("y")) {
				validEntry = true;
			}else {
				System.out.println("Your entry was invalid. Try again");
	}
		}
	}
	}	
	
	
		
		
	
	
	

