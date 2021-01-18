/* Ashmita Ravichandran
*  April 24th 2020
*  Hangman Java Game
*  Code for game
*/

package com.company;
import java.io.*;
import java.util.Scanner;
import java.util.Random;


public class hangman
{
        public static void main(String[] args) throws IOException
        {

            //1D Array used to store all dinosaur names for game
            String[] dinosaurs = {"brachiosaurus", "tyrannosaurus", "velociraptor", "xenotarsosaurus", "kentrosaurus", "jaxartosaurus", "fabrosaurus", "apatosaurus", "stegosaurus", "yangchuanosaurus"};
            int randomNumber = new Random().nextInt(dinosaurs.length);

            //Use of Scanner and User Input
            Scanner scan=new Scanner (System.in);

            String answer;
            String guess;
            String name;
            String decision;
            String dashedAnswer;
            String line = "";
            //2D Array that will be used for leaderboards
            String[][] scoresListArray;
            //1D Array used to store each line of leaderboard list (leaderboard.txt)
            String leaderboardList[];

            int guessCounter=8;
            int arrayElementIndex=0;

            //Used for scores - shortest time will be higher place
            long startTime;
            long endTime;
            long score;

            //2D Array gets its size for this run through function arrayRows
            scoresListArray = new String [arrayRows()][2];

            System.out.println("PRESS ENTER TO START!");
            scan.nextLine();
            System.out.println("What's your name?");
            name=scan.next();

            clearScreen();

            //Use of Repetition in order to display Main Menu Screen after user visits different pages
            do {
                    System.out.println("PRESS ENTER TO GO TO MAIN MENU");
                    scan.nextLine();
                    scan.nextLine();
                    mainMenuScreen();
                    decision = scan.next();

                    if (decision.equals("1"))
                    {
                        clearScreen();
                        answer = dinosaurs[randomNumber];
                        dashedAnswer = changeToDashes(answer);
                        System.out.println("Hey " + name + "!");
                        scan.nextLine();
                        System.out.println("GAME WILL START NOW! (Press ENTER to continue)");
                        scan.nextLine();
                        System.out.println("Your word is... (Press ENTER to continue)");
                        scan.nextLine();
                        System.out.println(dashedAnswer);
                        startTime=System.nanoTime();//Starts time for scores

                        //Use of repetition in order to ask user to enter their guess
                        do
                        {
                            System.out.println("Enter a letter - and only a letter! (Press ENTER to guess)");
                            guess=scan.next();
                            guess=guess.toLowerCase();

                            if (guess.length() > 1)
                            {
                                System.out.println("That's more than one letter...");
                            }

                            if (guess.length() == 1)
                            {
                                //checks if user's guess is in chosen word
                                if (answer.contains(guess))
                                {
                                    //checks each letter in chosen word
                                    for (int x = 0; x < answer.length(); x++)
                                    {
                                        String string = String.valueOf(answer.charAt(x));
                                        if (string.equals(guess))
                                        {
                                            //if letter is in word two strings are created
                                            String part1 = dashedAnswer.substring(0, x);//Creates string from begining of word to guessed letter's position
                                            String part2 = dashedAnswer.substring(x + 1);//Creates string from guesssed letter's position to end of word
                                            dashedAnswer = part1 + guess + part2;//Puts two string together and the the user's guess which is right
                                        }
                                    }
                                    System.out.println(dashedAnswer);//Puts out dashed out word with user's inputed letter
                                }

                                if (!answer.contains(guess)) {
                                    guessCounter--;
                                    System.out.println("\r\nXXXXXXXXXXXXX YOUR GUESS WAS INCORRECT XXXXXXXXXXXXXXXXXX");
                                    System.out.println("YOU HAVE " + guessCounter + " LIVES LEFT " + name + "!\r\n");
                                    System.out.println(dashedAnswer);
                                }
                            }

                        } while(dashedAnswer.contains("-") && guessCounter>0);

                        if (guessCounter==0)
                        {
                            System.out.println("That's too bad...you lost. The answer was " + answer + ". BETTER LUCK NEXT TIME " + name + "!");
                        }

                        if (!dashedAnswer.contains("-"))
                        {
                            clearScreen();
                            System.out.println("             ___________\n" +
                                    "            '._==_==_=_.'\n" +
                                    "            .-\\:      /-.\n" +
                                    "           | (|:.     |) |\n" +
                                    "            '-|:.     |-'\n" +
                                    "              \\::.    /\n" +
                                    "               '::. .'\n" +
                                    "                 ) (\n" +
                                    "               _.' '._\n" +
                                    "              `\"\"\"\"\"\"\"`");
                            System.out.println("        CONGRATS!! YOU WON " + name + "!\r\n        ");
                            endTime=System.nanoTime();//Ends time for scores
                            score=(endTime-startTime)/1000000000;//Converts time to seconds
                            System.out.println("You took " + score + " seconds to guess your word! Check your rank on the leaderboard by restarting the game!");
                            saveScores(name,score);//time is saved for leadeboards
                            System.out.println("THANKS FOR PLAYING!");
                            System.exit(0);

                        }
                    }

                    if (decision.equals("2"))
                    {
                        try
                        {
                            //Reads list of leaderboard scores and turns it into 2D Array
                            BufferedReader br = new BufferedReader(new FileReader("leaderboard.csv"));
                            while (line != null)
                            {
                                line = br.readLine();//reads each line
                                if (line != null)
                                {
                                    leaderboardList = line.split(",");//Turn line into into 1D Array by splitting at commas
                                    scoresListArray[arrayElementIndex][0] = leaderboardList[0];//Add element at index 0 (name) to 2D array at first column and "arrayElementIndex" row
                                    scoresListArray[arrayElementIndex][1] = leaderboardList[1];//Add element at index 1 (score) to 2D array at second column and "arrayElementIndex" row
                                    arrayElementIndex++;//increase arrayElementIndex by 1
                                }
                            }

                            br.close();//close doc

                            //bubble sort 2D Array for Leaderboard - gotten from STACKOVERFLOW
                            for (int y = 0; y < scoresListArray.length; y++)
                            {
                                for (int z = 1; z < (scoresListArray.length - y); z++)
                                {
                                    int numOne = 0;
                                    int numTwo = 0;
                                    int temporaryScore;
                                    String temporaryName;

                                    try
                                    {
                                        //Turns scores into numbers from string in order to compare each number (changes through for loop)
                                        numOne = Integer.parseInt(scoresListArray[z - 1][1]);
                                        numTwo = Integer.parseInt(scoresListArray[z][1]);
                                    }
                                    catch (NumberFormatException e)
                                    {
                                        System.out.println("There has been an error in processing the leaderboards.");
                                    }

                                    if (numOne > numTwo)
                                    {
                                        temporaryScore = numOne;//numberOne is stored here temporarily if it's bigger than numberTwo
                                        scoresListArray[z - 1][1] = Integer.toString(numTwo);//numberTwo's position is changed and put higher up
                                        scoresListArray[z][1] = Integer.toString(temporaryScore);//numberOne goes lower

                                        //Shuffles name back to respective score
                                        temporaryName = scoresListArray[z - 1][0];
                                        scoresListArray[z - 1][0] = scoresListArray[z][0];
                                        scoresListArray[z][0] = temporaryName;
                                    }
                                }
                            }

                            try
                            {
                                //Displays Leaderboard
                                leaderBoardScreen(scoresListArray);
                            }
                            catch (ArrayIndexOutOfBoundsException ex)
                            {
                                //If there are less people who have played that the amount that can be displayed this message is displayed
                                System.out.println("Not enough people have played yet for this place to be filled!");
                            }
                        } catch (IOException ex)
                        {
                            System.out.println("There has been an error in processing the leaderboards.");
                        }
                    }

                    if (decision.equals("3"))
                    {
                        //Prints out HOW TO PAGE
                        System.out.println(decision);
                        howToScreen(name);
                    }

                    if (!decision.equals("1") && !decision.equals("2") && !decision.equals("3") && !decision.equals("4"))
                    {
                        //If user puts in invalid options this message will appear
                        System.out.println("Please enter a valid number!");
                    }
            }while (!decision.equals("4"));

            //This message will print out if user decides to quit
            System.out.println("Oh that's too bad... See you next time " + name + "!");



        }//end of function

        /*********************************************************************************
         *********Function is used to clear screen after user enters different page*******
         ***********************NO PARAMETER AND NO RETURN VALUE**************************
         *********************************************************************************/
        static void clearScreen()
        {
            //no parameters and return value
            System.out.println("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
            //"CLEARS" console through a bunch of line breaks
        }

        /*********************************************************************************
         *****************Function is used to display Main Menu***************************
         ********************NO PARAMETER AND NO RETURN VALUE*****************************
         *********************************************************************************/
        static void mainMenuScreen()
        {
            //Prints out everything that user can access
            System.out.println("\r\n****************************************************************");
            System.out.println("****************************************************************\r\n");
            System.out.println("..     _      .-~               ~-.\n" +
                    "     //|     \\ `..~                      `.\n" +
                    "    || |      }  }              /       \\  \\\n" +
                    "(\\   \\\\ \\~^..'                 |         }  \\\n" +
                    " \\`.-~  o      /       }       |        /    \\\n" +
                    " (__          |       /        |       /      `.\n" +
                    "  `- - ~ ~ -._|      /_ - ~ ~ ^|      /- _      `.\n" +
                    "              |     /          |     /     ~-.     ~- _\n" +
                    "              |_____|          |_____|         ~ - . _ _~_-_");
            System.out.println("\r\n****************************************************************");
            System.out.println("                WELCOME TO WHAT'S THAT DINO!                ");
            System.out.println("****************************************************************\r\n");
            System.out.println("Enter a number!");
            System.out.println("Play Now ------------------ (1)");
            System.out.println("Leaderboard ------------------ (2)");
            System.out.println("How to Play ------------------ (3)");
            System.out.println("Quit ------------------ (4)");
        }

        /************************************************************************************
         Function is used to display How To Play Menu and Parameter is to display user's name
         ***********************PARAMETER AND NO RETURN VALUE********************************
         ************************************************************************************/

        static void howToScreen(String name)
        {
            System.out.println("\r\n****************************************************************");
            System.out.println("****************************************************************\r\n");
            System.out.println("                            ___......__             _\n" +
                    "                        _.-'           ~-_       _.=a~~-_\n" +
                    "--=====-.-.-_----------~   .--.       _   -.__.-~ ( ___===>\n" +
                    "              '''--...__  (    \\ \\\\\\ { )       _.-~\n" +
                    "                        =_ ~_  \\\\-~~~//~~~~-=-~\n" +
                    "                         |-=-~_ \\\\   \\\\\n" +
                    "                         |_/   =. )   ~}\n" +
                    "                         |}      ||\n" +
                    "                        //       ||\n" +
                    "                      _//        {{\n" +
                    "                   '='~'          \\\\_    =\n" +
                    "                                   ~~'");
            System.out.println("\r\n****************************************************************");
            System.out.println("             HOW TO PLAY WHAT'S THAT DINO!              ");
            System.out.println("****************************************************************\r\n");
            System.out.println("What's That Dino is similar to hangman but only dinosaur names are chosen.");
            System.out.println("A random name will be generated and it is your job to figure out the word \r\nby guessing the letters within it, but you will only have 8 lives.");
            System.out.println("You must guess the name before your lives run out! That's it " + name + "! GOOD LUCK!");
        }

        /**********************************************************************************************
        Function is used to display Leaderboard and Parameter is to use the 2D Array that stores scores
        ************************PARAMETER AND NO RETURN VALUE******************************************
        ***********************************************************************************************/
        static void leaderBoardScreen(String [][] scoresListArray)
        {
            System.out.println("\r\n****************************************************************");
            System.out.println("****************************************************************\r\n");
            System.out.println("                     /~~~~~~~~~~~~\\_\n" +
                    " _+=+_             _[~  /~~~~~~~~~~~~\\_\n" +
                    "{\"\"|\"\"}         [~~~    [~   /~~~~~~~~~\\_\n" +
                    " \"\"\":-'~[~[~\"~[~  ((++     [~  _/~~~~~~~~\\_\n" +
                    "      '=_   [    ,==, ((++    [    /~~~~~~~\\-~~~-.\n" +
                    "         ~-_ _=+-(   )/   ((++  .~~~.[~~~~(  {@} \\`.\n" +
                    "                 /   }\\ /     (     }     (   .   ''}\n" +
                    "                (  .+   \\ /  //     )    / .,  \"\"\"\"/\n" +
                    "                \\\\  \\     \\ (   .+~~\\_  /.= /'\"\"\"\"\n" +
                    "                <\"_V_\">      \\\\  \\    ~~~~~~\\\\  \\\n" +
                    "                              \\\\  \\          \\\\  \\\n" +
                    "                              <\"_V_\">        <\"_V_\">");
            System.out.println("\r\n****************************************************************");
            System.out.println("             WHAT'S THAT DINO LEADERBOARD!              ");
            System.out.println("****************************************************************\r\n");
            System.out.println("(1ST PLACE) " + scoresListArray[0][0] + " ------------------ " + scoresListArray[0][1] + " SECONDS");
            System.out.println("(2ND PLACE) " + scoresListArray[1][0] + " ------------------ " + scoresListArray[1][1] + " SECONDS");
            System.out.println("(3RD PLACE) " +scoresListArray[2][0] + " ------------------ " + scoresListArray[2][1] + " SECONDS");
            System.out.println("(4TH PLACE) " +scoresListArray[3][0] + " ------------------ " + scoresListArray[3][1] + " SECONDS");
            System.out.println("(5TH PLACE) " +scoresListArray[4][0] + " ------------------ " + scoresListArray[4][1] + " SECONDS");
        }

        /*********************************************************************************
         ******Function is used to count score entries and returns that value*************
         ********************NO PARAMETER AND RETURN VALUE********************************
         *********************************************************************************/
        static int arrayRows()
        {
            int arrayRows = 0;
            try
            {
                BufferedReader doc = new BufferedReader(new FileReader("leaderboard.csv"));
                while (doc.readLine()!= null)
                {
                    arrayRows++;
                }
            }
            catch (IOException ex)
            {
                System.out.println("ERROR ERROR");
            }
            return arrayRows;
        }

        /*********************************************************************************
         Function is used to change randomly chosen word to dashes and returns new value**
         **************************PARAMETER AND RETURN VALUE*****************************
         *********************************************************************************/
        static String changeToDashes(String str)
        {
            String result = "";
            for (int i = 0; i < str.length(); i++){
                result += "-";
            }
            return result;
        }

        /************************************************************************************
         Function is used to save user's score and Parameter is to save user's score and name
         ***********************PARAMETER AND NO RETURN VALUE********************************
         ************************************************************************************/
        static void saveScores(String name, long score) throws IOException
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter("leaderboard.csv", true));
            String userScore = name + "," + score + "\r\n"; //Make an entry
            bw.write(userScore); //Write to file
            bw.close(); //Close writer
        }

}//end of class
