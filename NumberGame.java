/* Tim Yi
 * AP Computer Science
 * 10/26/2017
 * Project Number Game
 */

package apcsjava;

import java.util.ArrayList;
//import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class NumberGame {
	
	//global variables:
	static Scanner in;
	static String username;
	static String playAgain;
	static boolean play = true;
	static boolean alwaysWin = false;
	static String modeOfGame;
	static String seeIntro;
	
	public static void gameMode1() {
		
		//local variables:
		int minimum;
		int maximum;
		int guess = Integer.MIN_VALUE;
		int numOfGuessesAllowed;
		int numOfGuessesUsed = 0;
		int randomNumber;
		
		
		
		while(true) { //taking in minimum
			System.out.println("Please give the range of the numbers you want to guess from.");
			System.out.print("Give the minimum --> ");
			while (true) {
				try {
					minimum = in.nextInt();
					break;
				} catch (InputMismatchException e) { //catch invalid input types
					System.out.println("Not a number! Try again.");
					System.out.print("Give the minimum --> ");
					in.nextLine(); // dump the bad input
				}
			}
			System.out.print("Give the maximum --> ");
			while (true) { //taking in maximum
				try {
					maximum = in.nextInt();
					break;
				} catch (InputMismatchException e) { //catch invalid input types
					System.out.println("Not a number! Try again.");
					System.out.print("Give the maximum --> ");
					in.nextLine(); // dump the bad input
				}
			}
			if (maximum < minimum) { //forbid the user to but in a maximum that is smaller than the minimum
				System.out.println("Your minimum has to be less than or equal to your maximum.");
				System.out.println("Try again please.");
			} else break;
		}
		
		System.out.println("Ok! So you are guessing from " + minimum + " to " + maximum + ".");
		
		randomNumber = (int) (Math.random() * (maximum - minimum + 1)) + minimum; //generate a random number from minimum to maximum
		
		numOfGuessesAllowed = (int) (Math.log10(maximum - minimum + 1) / Math.log10(2)) + 1; //generate number of guesses allowed accordingly to the range
		System.out.println("You have " + numOfGuessesAllowed + " chances. Give your best guess!");
		
		for (int i = numOfGuessesAllowed-1; i >= 0; i--) { //give the user "numOfGuessesAllowed" chances to guess
			try {
				guess = in.nextInt();
				if (alwaysWin) {
					numOfGuessesUsed = 1;
					break;
				} else if (guess > randomNumber) { //if the guess is larger than the number, notice the user
					System.out.println("Your guess is too big, try again.");
					System.out.println("You have " + i + " chance(s) left.");
				} else if (guess < randomNumber) { //if the guess is smaller than the number, notice the user
					System.out.println("Your guess is too small, try again.");
					System.out.println("You have " + i + " chance(s) left.");
				} else if (guess == randomNumber) { //if the user guesses the number, break and record how many guesses it took the user to win
					numOfGuessesUsed = numOfGuessesAllowed - i;
					break;
				}
			} catch (InputMismatchException e) { // if the user doesn't enter an integer, complain
					System.out.println("Not a number!  Guess you're giving up.");
					System.out.println("You have " + i + " chance(s) left.");
					in.nextLine(); // dump the bad input
			}
		}
		
		if (guess == randomNumber||alwaysWin) { //the user wins either if he/she guesses the number or if the user is tim (bonus feature)
			if (numOfGuessesUsed == 1) { //to congratulate extremely smart/lucky users
				System.out.println("You got it in " + numOfGuessesUsed + " try. You are a genius!");
			} else  { //to congratulate average IQ users
				System.out.println("You got it in " + numOfGuessesUsed + " tries. Congrats!");
			}
		} else { //to mock the not-so-smart users
			System.out.println("Omae wa mou shindeiru!");
		}
		in.nextLine(); // dump the bad input
	}
	
	public static void gameMode2() {
		
		//local variables:
		int secretNum;
		int guess = 0;
		int maximum = 100;
		int minimum = 1;
		int guessesLeft = 7;
		
		System.out.println("Please give the secret number that the computer is trying to guess.");
		System.out.print("Give the number --> ");
		while (true) {
			try {
				secretNum = in.nextInt();
				if (secretNum >= 1 && secretNum <= 100) break; //only keep going if the number given by the user is between 1 and 100 inclusive
				else {
					System.out.println("The number has to be in the range of 1 to 100."); //complain when the user give a number beyond the range allowed
					System.out.print("Give the number --> ");
				}
			} catch (InputMismatchException e) { //complain when the user gives an invalid input
				System.out.println("Not a number! Try again.");
				System.out.print("Give the number --> ");
				in.nextLine(); // dump the bad input
			}
		}
		System.out.println("The computer is going to guess now."); //notice the user that the process is about to start
		System.out.println("It will show its guessing process from here on.");
		
		while (guessesLeft > 0) {
			guessesLeft--;
			guess = (minimum + maximum)/2; //every new guess is in the middle of the minimum and the maximum
			if (guess > secretNum) { //remind the user that the computer's guess is too big
				System.out.println("My guess was " + guess + " and it was too big.");
				System.out.println("I have " + guessesLeft + " guesses left.");
				maximum = guess - 1; //set the guess as the new upper boundary of the guesses (since the guess is not correct, the computer can ignore that number)
			} else if (guess < secretNum) { //remind the user that the computer's guess is too big
				System.out.println("My guess was " + guess + " and it was too small.");
				System.out.println("I have " + guessesLeft + " guesses left.");
				minimum = guess + 1; //set the guess as the new lower boundary of the guesses (since the guess is not correct, the computer can ignore that number)
			} else if (guess == secretNum) { //breaks and proclaims victory when the computer guesses correctly
				System.out.println("I guessed your number! I did it in " + (7 - guessesLeft) + " try(ies).");
				break;
			}
		}
		
		if (guess != secretNum) { //if there are no chances left and the guess is not correct, the computer loses
			System.out.println("I run out of chances. I Lose!");
		}
		in.nextLine();
	}
	
	public static void gameMode3() {
		
		//local variables:
		int randomNumber;
		int guess;
		int minimum;
		int maximum;
		int numOfWins;
		
		int[] win = new int[100]; //create an array that holds the number of games that is won out of 100 by starting with numbers from 1 to 100
		for (int firstGuess = 1; firstGuess < 101; firstGuess++) { //numbers to start guessing with
			numOfWins = 0;
			for (int trialNum = 1; trialNum < 101; trialNum++) { //100 trials
				randomNumber = (int) (Math.random() * (100)) + 1/*trialNum*/;
				//By using "trialNum" instead of a randomly-generated number ("(int) (Math.random() * (100)) + 1"), the whole process ensures that all numbers between 1 and 100 inclusive are tested out so that any numbers that got into the list by chance will be eliminated. Therefore, I stand by my choice strongly!!!
				minimum = 1; //reset minimum
				maximum = 100; //reset maximum
				for (int guessesLeft = 7; guessesLeft > 0; guessesLeft--) { //give seven guesses to the computer
					if (guessesLeft == 7) {
						guess = firstGuess; //if the computer is guessing for the first time in a single game, it uses the "firstGuess"
					} else {
						guess = (minimum + maximum)/2; //if the computer is not guessing for the first time in a single game, it uses the middle number between the minimum and the maximum
					}
					if (guess > randomNumber) { //if the guess is larger than the number
						maximum = guess - 1; //set the guess as the new upper boundary of the guesses (since the guess is not correct, the computer can ignore that number)
					} else if (guess < randomNumber) { //if the guess is smaller than the number
						minimum = guess + 1; //set the guess as the new lower boundary of the guesses (since the guess is not correct, the computer can ignore that number)
					} else if (guess == randomNumber) {
						numOfWins++; //if the guess is correct, add 1 to the "numOfWins" counter
						break; //stop this game and enter the second for loop again to play another game
					}
				}
			}
			win[firstGuess - 1] = numOfWins; //after 100 trials of the same number, record the times of wins into the corresponding position in the array
		}
//		System.out.println(Arrays.toString(win)); //for testing purposes, please ignore
		
		ArrayList<Integer> output = new ArrayList<Integer>(); //create an array list that documents only the numbers to start with that wins 100 times out of 100 trials
		int outputPosition = 0; //the initial position to start putting in numbers
		for (int firstGuess = 1; firstGuess < 101; firstGuess++) { //loop through the array of numbers to start with
			if (win[firstGuess - 1] == 100) {
				output.add(outputPosition, firstGuess); //if the number wins 100 percent of the trials, record it in the array list from the beginning
				outputPosition++; //shift the position of number to be recorded next
			}
		}
		System.out.println("According to the experiment conducted just now,"); //return the results back to the user
		System.out.println("here is a list of numbers that you can start guessing with ...");
		System.out.println("in the first mode that will give you the best chances of winning.");
		System.out.println(output);
		System.out.println("(Disclaimer: The results might vary for different trials.)");
		System.out.println("(For more details on this disclaimer, refer to the comment in line 174.)");
	}
	
	public static void main(String[] args) {
		in = new Scanner(System.in); //set up the scanner and name it "in"
		System.out.println("Hello! What's your name?");
		username = in.nextLine(); //takes user input for user name
		if (username.equals("tim")) { //bonus feature!! (the user always wins in the first mode when he is tim!)
			alwaysWin = true;
		}
		System.out.println("Hello, " + username + "!!"); //greets the user
		System.out.println("This is the number game.");
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("Would you like to see the introduction of the game?"); //offers to explain the game
		while (true) {
			System.out.println("Type 'yes' or 'no'.");
			seeIntro = in.nextLine();
			if (seeIntro.equals("yes")) {
				System.out.println("-------------------------------------------------------------------------");
				System.out.println("There are three modes in this game.");
				System.out.println("In the first mode, you will give a range of integers,");
				System.out.println("the computer will generate a random number within that range,");
				System.out.println("and you will try to guess that number.");
				System.out.println("The number of guesses that you will have ...");
				System.out.println("is calculated base on the size of the range.");
				System.out.println("You win if you manage to guess the number ...");
				System.out.println("before you run out of chances and vise versa.");
				System.out.println("");
				System.out.println("In the second mode, you will give a secret number between 1 and 100,");
				System.out.println("and the computer will try to guess that number.");
				System.out.println("");
				System.out.println("In the third mode, the computer will generate a number between 1 and 100,");
				System.out.println("and it will try to guess it itself ...");
				System.out.println("by starting with every number from 1 to 100,");
				System.out.println("and finally try to figure out the best number(s) to start with.");
				break;
			} else if (seeIntro.equals("no")) break;
			else System.out.println("That's not a valid input."); //pointing out invalid inputs for seeing introduction
		}
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("Which mode would you like to play?");
		
		while (true) { //checks for the game mode that the user wants to play
			System.out.println("Type '1', '2' or '3'.");
			modeOfGame = in.nextLine();
			if (modeOfGame.equals("1")) {
				while (play) { //only runs the first time automatically and after that only runs if the user wants to play again
					gameMode1();
					while (true) { //checks if the user wants to play mode 1 again
						System.out.println("-------------------------------------------------------------------------");
						System.out.println("Would you like to play again?");
						System.out.println("Type 'yes' or 'no'.");
						playAgain = in.nextLine();
						if (playAgain.equals("yes")) {
							System.out.println("Let's play again!"); //breaks without setting "play" as false
							System.out.println("-------------------------------------------------------------------------");
							break;
						} else if (playAgain.equals("no")) {
							System.out.println("Okay, see you later!");
							play = false; //stops the while loop from running again
							break;
						} else System.out.println("That's not a valid input."); //pointing out invalid inputs for playing again
					}
				} break;
			} else if (modeOfGame.equals("2")) {
				while (play) { //only runs the first time automatically and after that only runs if the user wants to play again
					gameMode2();
					while (true) { //checks if the user wants to play mode 2 again
						System.out.println("-------------------------------------------------------------------------");
						System.out.println("Would you like to play again?");
						System.out.println("Type 'yes' or 'no'.");
						playAgain = in.nextLine();
						if (playAgain.equals("yes")) {
							System.out.println("Let's play again!"); //breaks without setting "play" as false
							System.out.println("-------------------------------------------------------------------------");
							break;
						} else if (playAgain.equals("no")) {
							System.out.println("Okay, see you later!");
							play = false; //stops the while loop from running again
							break;
						} else {
							System.out.println("That's not a valid imput."); //pointing out invalid inputs for playing again
						}
					}
				} break;
			} else if (modeOfGame.equals("3")) {
				while (play) { //only runs the first time automatically and after that only runs if the user wants to play again
					gameMode3();
					while (true) { //checks if the user wants to play mode 3 again
						System.out.println("-------------------------------------------------------------------------");
						System.out.println("Would you like to play again?");
						System.out.println("Type 'yes' or 'no'.");
						playAgain = in.nextLine();
						if (playAgain.equals("yes")) {
							System.out.println("Let's play again!"); //breaks without setting "play" as false
							System.out.println("-------------------------------------------------------------------------");
							break;
						} else if (playAgain.equals("no")) {
							System.out.println("Okay, see you later!");
							play = false; //stops the while loop from running again
							break;
						} else {
							System.out.println("That's not a valid imput."); //pointing out invalid inputs for playing again
						}
					}
				} break;
			} else System.out.println("That is not a valid input! Try again."); //pointing out invalid inputs for choosing game modes
		}
		in.close(); // unreachable line, but proper usage of a scanner requires closing at the end of its use
	}
}