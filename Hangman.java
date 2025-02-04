import java.util.Random;
import java.util.Scanner;

public class Hangman
{
    // Initiates the scanner for the entire game
    private static Scanner scanner = new Scanner(System.in);
	
    // Flag to keep track of win/loss streak
    private static int win = 0;
    private static int lose = 0;
	
    // Store the topic the user chooses
    private static String topic = "";
	
    // Store the difficulty the user chooses
    private static String difficulty = "";
    
    // Create an instance of the Random class
    private static Random random = new Random();

    // Generate a random number within the specified range
    private static int randomNumber = 0;
	
    // Initialize the storeOptionsay which will contain the words based off topic choice and difficulty
    private static String[] storeOptions = new String[5];
	
    // Variable to keep track of rounds so that the diagram can be printed at the right time
    private static int numRounds = 0;
    
    // Variable to check if the user already guess that letter
    private static String wordGuessAlready = "";
    
    // Variable to show letters that user guessed already that are incorrect
    private static String letterWrong = ""; 
    
    // Counter keeps track of the number of times user gets a right letter
    private static int counter = 0;

    // The second counter is to ensure that the proper diagram is shown when user gets a wrong guess
    private static int counter2 = 0;
    
    // Gets length of random word
    private static int length = 0;
    
    // Checks for spaces so that its not included in length (when running the for loop)
    private static int lengthWithoutSpace = 0;
    
    // Amount of guesses user haves before they lose (face, body, left & right arms, left & right legs)
    private static int guesses = 6;	
	
    public static void main(String[] args)
    {
        mainMenu();
    }
    
    public static void mainMenu()
    {   
        // Display Game Title
        displayTitle();
        
        // Display Hangman title
        hangmanDrawingTitle();
        
        // Get user's choice from the main menu
        System.out.print("\nChose one of the options below: \n - Rules\n - Play\n\n");
        String options = scanner.nextLine().toLowerCase().trim();
        
        // Validate User Input
        while (!(options.contains("rule") || options.contains("play")))
        {
            System.out.println("\nPlease chose one of the options above: ");
            options = scanner.nextLine().toLowerCase().trim();
        }
        
        // Handle user's choice
        if (options.contains("play"))
        {
        	settings();
        	playGame();
        }
        
        if (options.contains("rule"))
        	tutorial();
    }
    
    private static void tutorial()
    {
        // Shows rules on how to play hangman
    	clearScreen();
        System.out.println("Hangman: ");
        System.out.println("\n- Hangman is a classic word guessing game.");
        System.out.println("\n- One player thinks of a word and the other player tries to guess it by suggesting letters.");
        System.out.println("\n- The word is represented by a series of dashes, each dash representing a letter in the word and a gap between dashes indicates a different word.");
        System.out.println("\n- The guessing player suggests letters one at a time.");
        System.out.println("\n- If the letter is in the word, the other player (the computer in this case) reveals its position(s) in the word(s).");
        System.out.println("\n- If the letter is not in the word, a part of a stick figure (the 'hangman') is drawn.");
        System.out.println("\n- The guessing player wins if they guess the word correctly before the hangman is fully drawn.");
        System.out.println("\n- If the hangman is completed before the word is guessed, the guessing player loses.");
        
        System.out.print("\nPress Enter to return to the main menu. ");
        scanner.nextLine();
        clearScreen();
        mainMenu();
    }
    
    private static void playGame()
    {   
       String randomWord = settings();
       
       // Gets length of random word
       length = randomWord.length();
       
       // Checks for spaces so that its not included in length (when running the for loop)
       lengthWithoutSpace = length;
       
       // Shows diagram before starting game
       hangmanDrawingZero();
       
       // Initializes word that user has to fill in 
       String[] displayWord = new String[length];
       
       // Double checks if a word(s) has a space in it  
       for (int i = 0; i < length; i++)
       {
           // If there is more than one word (to make a gap between them when displaying)
           if (randomWord.charAt(i) == ' ')
           {
               // Ensuring a space between words 
               displayWord[i] = " ";
               
               // Displays word to user
               System.out.print(displayWord[i] + " ");
               
               // Counter is incremented because the space counts as a correct letter
               counter++;
               
               // The length of the word is decremented because a space doesn't count as a letter
               lengthWithoutSpace--;
           }
           else
           {
               // Ensuring no space between letters
               displayWord[i] = "_";
               
               // Displays word to user
               System.out.print(displayWord[i] + " ");
           }
       }	
    	
       // Space doesn't count as a letter 
       System.out.println("\n\nThe random word to guess has " + lengthWithoutSpace + " letters");
       
       while (guesses > 0)
       {
    	// The if statements are for which diagram to show
           if (counter2 == 0 && numRounds >= 1) // numRounds ensure that it only prints after first round because we printed the first diagram already
        	   hangmanDrawingZero();
           if (counter2 == 1)
        	   hangmanDrawingOne(); 
           if (counter2 == 2)
        	   hangmanDrawingTwo(); 
           if (counter2 == 3)
        	   hangmanDrawingThree(); 
           if (counter2 == 4)
        	   hangmanDrawingFour(); 
           if (counter2 == 5)
        	   hangmanDrawingFive(); 
           
           // Shows the words that the user guesses wrong
           if (!(letterWrong.equals("")))
               System.out.println("\nLists of Incorrect Letters Guessed to Help: " + letterWrong + " ");
           
           // Shows amount of guesses left
           System.out.println("\nYou have " + guesses + " guesses.\nIf you want to fully guess the word, type 1.");
           
           System.out.println("\nGuess a letter from the word: ");
           String userGuess = scanner.nextLine();
           
           // If the user wants to fully guess the word
           if (userGuess.equals("1"))
           {
           	   System.out.println("\nWhat is the word (Be sure to type it correctly): ");
               userGuess = scanner.nextLine();
               
               if (userGuess.equalsIgnoreCase(randomWord))
               {
                   System.out.println("\nYou Win!!!\nThe word is " + randomWord + ".");
                   win++;
                   break;
               }
               System.out.println(userGuess + " is incorrect.");
           }
           
           // Validate User Input (User can only enter letter) 
           while (userGuess.length() != 1)
           {
           	   System.out.println("\"\\nGuess a letter from the word: \"");
               userGuess = scanner.nextLine();
           }
           
           // Seeing if user already guessed that letter
           if (wordGuessAlready.contains(userGuess)) // We use contain not equal because we are adding to WordGuessAlready for each guess that hasn't been guessed before
           {
               System.out.println("\nYou already guessed that letter.\n");
               
               // Loop to display the word that the user is guessing
               for (int i = 0; i < length; i++) 
                   System.out.print(displayWord[i] + " ");
           
               continue; // Continue breaks one iteration (in the loop) and continues to the next one
           } 
           else 
               wordGuessAlready += userGuess; // Letter hasn't been guessed before so we add it to the string
           
           // Sees if user guess is in the word
           if (randomWord.toLowerCase().contains(userGuess.toLowerCase()))
           {
               System.out.println("\nCorrect!!!\n");
               
               // For loop to fill in what letter(s) are right in each position of the word
               for (int i = 0; i < length; i++)
               {
                   // First we need to see if the user guess is equal to the letter in the word AND we need to make sure that the position is CORRECT ('_') to ensure that we don't replace a space 
                   if (Character.toString(randomWord.charAt(i)).equalsIgnoreCase(userGuess) && displayWord[i].equals("_"))
                   {
                       // We set the position in the array to be the user guess
                       displayWord[i] = Character.toString(randomWord.charAt(i));
                       
                       // Increment counter because user got a correct word
                       counter++;
                   }
               }
               
               // Loop to display the word that the user is guessing
               for (int i = 0; i < length; i++)
                   System.out.print(displayWord[i] + " ");
               
               // Checks if the user has won by comparing length of word with the counter 
               if (counter == length)
               {
                   System.out.println("\n\nYou Win!!!\nThe word is " + randomWord + ".");
                   win++;
                   break;
               }
           }
           
           else // User guess incorrect
           {
               System.out.println("\nIncorrect!!!\n");
               
               // Loop to display the word that the user is guessing
               for (int i = 0; i < length; i++)
                   System.out.print(displayWord[i] + " ");
           
               // Saves the wrong letters that user guesses
               letterWrong += userGuess + ", ";    
               
               // Subtracts the number of guesses user has by 1
               guesses--;
               
               // Increment counter to show what diagram to show when user get word wrong 
               counter2++;
               
               // Checks if the user has lost
               if (guesses == 0)
               {
                   // Display Title Because User Lost
            	   hangmanDrawingTitle();
                   System.out.println("You lose.\nThe word was " + randomWord + ".");
                   lose++;
                   break;
               }
           }
           // Increments the number of rounds by 1 because the round is over
           numRounds++;
       }
       
       System.out.println("\nWould you like to play again? Yes or no? ");
       String wantPlayAgain = scanner.nextLine().toLowerCase().trim();
       
       // Validate User Input
       while (!(wantPlayAgain.charAt(0) == 'y' || wantPlayAgain.charAt(0) == 'n'))
       {
       	System.out.println("Please choose yes or no: ");
           wantPlayAgain = scanner.nextLine().toLowerCase().trim();
       }
       
       if (wantPlayAgain.charAt(0) == 'y')
       {
    	   resetGame();
           clearScreen();
           mainMenu();
       }
       else
       {
           System.out.println("You lost: " + lose + " time(s)\nYou won: " + win + " time(s)");
           System.out.println("\nHave a great day!");
       }
    }
    
    private static String settings()
    {
    	System.out.println("Choose Topic (game, movie, or color): ");
    	topic = scanner.nextLine().toLowerCase().trim();
    	
    	// Validate User Input
        while (!(topic.contains("game") || topic.contains("movie") || topic.contains("color")))
        {
        	System.out.println("\nPlease choose game, movie, or color: ");
        	topic = scanner.nextLine().toLowerCase().trim();
        }
    	
    	System.out.println("Choose difficulty (easy, medium, or hard): ");
    	difficulty = scanner.nextLine().toLowerCase().trim();
    	
    	// Validate User Input
        while (!(difficulty.contains("easy") || difficulty.contains("medium") || difficulty.contains("hard")))
        {
        	System.out.println("\nPlease choose Easy, Medium, or Hard: ");
        	difficulty = scanner.nextLine().toLowerCase().trim();
        }
    	
    	// Topic is Games
    	if (topic.contains("game")) 
    	{
    	    if (difficulty.contains("easy")) 
    	        storeOptions = new String[]{"Minecraft", "Call of Duty", "Fortnite", "Among Us", "Roblox"};
    	    if (difficulty.contains("medium")) 
    	        storeOptions = new String[]{"Mario", "Terraria", "Chess", "Assassins Creed", "Geometry Dash"};
    	    if (difficulty.contains("hard")) 
    	        storeOptions = new String[]{"Age of Empires", "Plants Vs Zombies", "The Legend of Zelda", "Plague Inc", "God of War"};
    	}
    	// Topic is Movies
    	if (topic.contains("movie")) 
    	{
    	    if (difficulty.contains("easy")) 
    	        storeOptions = new String[]{"The Dark Knight", "Avengers Infinity War", "Oppenheimer", "Shrek", "Kung fu Panda"};
    	    if (difficulty.contains("medium")) 
    	        storeOptions = new String[]{"Interstellar", "Red Notice", "Inception", "Despicable Me", "Tenet"};
    	    if (difficulty.contains("hard")) 
    	        storeOptions = new String[]{"Home Alone", "Bee Movie", "Hercules", "HstoreOptionsy Potter", "Rise of the Planets of the Apes"};
    	}
    	// Topic is Colors
    	if (topic.contains("color")) 
    	{
    	    if (difficulty.contains("easy")) 
    	        storeOptions = new String[]{"Blue", "Green", "Orange", "Yellow", "Red"};
    	    if (difficulty.contains("medium")) 
    	        storeOptions = new String[]{"Violet", "Aqua", "Silver", "Gold", "Diamond"};
    	    if (difficulty.contains("hard")) 
    	        storeOptions = new String[]{"Navy Blue", "Maroon", "Spring Green", "Azure", "Orchid"};
    	}
    	
    	// Create an instance of the Random class
        random = new Random();

        // Generate a random number within the specified range
        randomNumber = random.nextInt(5);
    	
        // Saves random word from list chosen by user
        return storeOptions[randomNumber];
    }
    
    private static void resetGame()
    {
        // Reset the variables related to the game state
        numRounds = 0;
        wordGuessAlready = "";
        letterWrong = "";
        counter = 0;
        counter2 = 0;
        length = 0;
        lengthWithoutSpace = 0;
        guesses = 6;

        // Reinitialize the Random object and get a new random number
        random = new Random();
        randomNumber = random.nextInt(storeOptions.length);
    }
    
    public static void clearScreen()
    {
    	for (int i = 0; i < 50; i++)
    		System.out.println();
    }
    
    public static void displayTitle()
    {
    	System.out.println("\t:::    :::     :::     ::::    :::  ::::::::  ::::     ::::     :::     ::::    :::");
        System.out.println("\t:+:    :+:   :+: :+:   :+:+:   :+: :+:    :+: +:+:+: :+:+:+   :+: :+:   :+:+:   :+:");
        System.out.println("\t+:+    +:+  +:+   +:+  :+:+:+  +:+ +:+        +:+ +:+:+ +:+  +:+   +:+  :+:+:+  +:+");
        System.out.println("\t+#++:++#++ +#++:++#++: +#+ +:+ +#+ :#:        +#+  +:+  +#+ +#++:++#++: +#+ +:+ +#+");
        System.out.println("\t+#+    +#+ +#+     +#+ +#+  +#+#+# +#+   +#+# +#+       +#+ +#+     +#+ +#+  +#+#+#");
        System.out.println("\t#+#    #+# #+#     #+# #+#   #+#+# #+#    #+# #+#       #+# #+#     #+# #+#   #+#+#");
        System.out.println("\t###    ### ###     ### ###    ####  ########  ###       ### ###     ### ###    ####");
    }
    
    private static void hangmanDrawingZero()
    {
        System.out.println("\n*********************");
        System.out.println("      **       *     ");
        System.out.println("      **       *     ");
        System.out.println("      **       *     ");
        System.out.println("      **             ");
        System.out.println("      **             ");
        System.out.println("      **             ");
        System.out.println("      **             ");
        System.out.println("      **             ");
        System.out.println("      **             ");
        System.out.println("      **             ");
        System.out.println("      **             ");
        System.out.println("      **             ");
        System.out.println("      **             ");
        System.out.println("      **             ");
        System.out.println("      **             ");
        System.out.println("      **             ");
        System.out.println("      **             ");
        System.out.println("      **             ");
        System.out.println("*********************\n");
    }
    
    private static void hangmanDrawingOne()
    {
        System.out.println("\n*********************");
        System.out.println("      **       *     ");
        System.out.println("      **       *     ");
        System.out.println("      **       *     ");
        System.out.println("      **    *******  ");
        System.out.println("      **   *       * ");
        System.out.println("      **   *       * ");
        System.out.println("      **    *******  ");
        System.out.println("      **             ");
        System.out.println("      **             ");
        System.out.println("      **             ");
        System.out.println("      **             ");
        System.out.println("      **             ");
        System.out.println("      **             ");
        System.out.println("      **             ");
        System.out.println("      **             ");
        System.out.println("      **             ");
        System.out.println("      **             ");
        System.out.println("      **             ");
        System.out.println("*********************\n");
    }
    
    private static void hangmanDrawingTwo()
    {
        System.out.println("\n*********************");
        System.out.println("      **       *     ");
        System.out.println("      **       *     ");
        System.out.println("      **       *     ");
        System.out.println("      **    *******  ");
        System.out.println("      **   *       * ");
        System.out.println("      **   *       * ");
        System.out.println("      **    *******  ");
        System.out.println("      **       *     ");
        System.out.println("      **       *     ");
        System.out.println("      **       *     ");
        System.out.println("      **       *     ");
        System.out.println("      **       *     ");
        System.out.println("      **       *     ");
        System.out.println("      **       *     ");
        System.out.println("      **             ");
        System.out.println("      **             ");
        System.out.println("      **             ");
        System.out.println("      **             ");
        System.out.println("*********************\n");
    }
    
    private static void hangmanDrawingThree()
    {
        System.out.println("\n*********************");
        System.out.println("      **       *     ");
        System.out.println("      **       *     ");
        System.out.println("      **       *     ");
        System.out.println("      **    *******  ");
        System.out.println("      **   *       * ");
        System.out.println("      **   *       * ");
        System.out.println("      **    *******  ");
        System.out.println("      **       *     ");
        System.out.println("      **       *     ");
        System.out.println("      **       *  *  ");
        System.out.println("      **       * *   ");
        System.out.println("      **       **    ");
        System.out.println("      **       *     ");
        System.out.println("      **       *     ");
        System.out.println("      **             ");
        System.out.println("      **             ");
        System.out.println("      **             ");
        System.out.println("      **             ");
        System.out.println("*********************\n");
    }
    
    private static void hangmanDrawingFour()
    {
        System.out.println("\n*********************");
        System.out.println("      **       *     ");
        System.out.println("      **       *     ");
        System.out.println("      **       *     ");
        System.out.println("      **    *******  ");
        System.out.println("      **   *       * ");
        System.out.println("      **   *       * ");
        System.out.println("      **    *******  ");
        System.out.println("      **       *     ");
        System.out.println("      **       *     ");
        System.out.println("      **    *  *  *  ");
        System.out.println("      **     * * *   ");
        System.out.println("      **      ***    ");
        System.out.println("      **       *     ");
        System.out.println("      **       *     ");
        System.out.println("      **             ");
        System.out.println("      **             ");
        System.out.println("      **             ");
        System.out.println("      **             ");
        System.out.println("*********************\n");
    }
    
    private static void hangmanDrawingFive()
    {
        System.out.println("\n*********************");
        System.out.println("      **       *     ");
        System.out.println("      **       *     ");
        System.out.println("      **       *     ");
        System.out.println("      **    *******  ");
        System.out.println("      **   *       * ");
        System.out.println("      **   *       * ");
        System.out.println("      **    *******  ");
        System.out.println("      **       *     ");
        System.out.println("      **       *     ");
        System.out.println("      **    *  *  *  ");
        System.out.println("      **     * * *   ");
        System.out.println("      **      ***    ");
        System.out.println("      **       *     ");
        System.out.println("      **       *     ");
        System.out.println("      **       *     ");
        System.out.println("      **        *    ");
        System.out.println("      **         *   ");
        System.out.println("      **             ");
        System.out.println("      **             ");
        System.out.println("*********************\n");
    }
    
    private static void hangmanDrawingTitle()
    {
        System.out.println("\n*********************");
        System.out.println("      **       *     ");
        System.out.println("      **       *     ");
        System.out.println("      **       *     ");
        System.out.println("      **    *******  ");
        System.out.println("      **   *       * ");
        System.out.println("      **   *       * ");
        System.out.println("      **    *******  ");
        System.out.println("      **       *     ");
        System.out.println("      **       *     ");
        System.out.println("      **    *  *  *  ");
        System.out.println("      **     * * *   ");
        System.out.println("      **      ***    ");
        System.out.println("      **       *     ");
        System.out.println("      **       *     ");
        System.out.println("      **       *     ");
        System.out.println("      **      * *    ");
        System.out.println("      **     *   *   ");
        System.out.println("      **             ");
        System.out.println("      **             ");
        System.out.println("*********************\n");
    }
}
