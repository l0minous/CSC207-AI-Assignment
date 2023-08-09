import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

// Interface to represent choices available in the game
interface Choice {
    String getName(); // Return the name of the choice (rock, paper, or scissors)
    boolean isBetterThan(Choice otherChoice); // Method to check if one choice is better than another
}

class Rock implements Choice {
    @Override
    public String getName() {
        return "rock";
    }

    @Override
    public boolean isBetterThan(Choice otherChoice) {
        // Rock is better than Scissors only
        return otherChoice instanceof Scissors;
    }
}

class Paper implements Choice {
    @Override
    public String getName() {
        return "paper";
    }

    @Override
    public boolean isBetterThan(Choice otherChoice) {
        // Paper is better than Rock only
        return otherChoice instanceof Rock;
    }
}

class Scissors implements Choice {
    @Override
    public String getName() {
        return "scissors";
    }

    @Override
    public boolean isBetterThan(Choice otherChoice) {
        // Scissors are better than Paper only
        return otherChoice instanceof Paper;
    }
}

// Class to represent the Player in the game
class Player {
    private final Scanner scanner = new Scanner(System.in);

    public Choice getChoice() {
        System.out.println("Enter your choice (rock, paper, or scissors): ");
        String playerChoice = scanner.nextLine().toLowerCase();
        // Loop to validate the user's input
        while (!RockPaperScissors.CHOICES.containsKey(playerChoice)) {
            System.out.println("Invalid choice! Please enter 'rock', 'paper', or 'scissors': ");
            playerChoice = scanner.nextLine().toLowerCase();
        }
        return RockPaperScissors.CHOICES.get(playerChoice);
    }
}

// Class to represent the Computer's choice in the game
class Computer {
    private final Random random = new Random();

    public Choice getChoice() {
        // Randomly select a choice for the computer
        int randomIndex = random.nextInt(RockPaperScissors.CHOICES.size());
        return RockPaperScissors.CHOICES.values().toArray(new Choice[0])[randomIndex];
    }
}

// Class to represent a round in the game
class Round {
    private final Player player;
    private final Computer computer;

    public Round(Player player, Computer computer) {
        this.player = player;
        this.computer = computer;
    }

    public String play() {
        // Both player and computer make their choices
        Choice playerChoice = player.getChoice();
        Choice computerChoice = computer.getChoice();
        System.out.println("Computer chose: " + computerChoice.getName());
        return determineWinner(playerChoice, computerChoice);
    }
    // Method to determine the winner based on the choices made from the Player
    private String determineWinner(Choice playerChoice, Choice computerChoice) {
        if (playerChoice == computerChoice) {
            return "It's a tie!";
        } else if (playerChoice.isBetterThan(computerChoice)) {
            return "You win!";
        } else {
            return "Computer wins!";
        }
    }
}

// Controller class to manage the game flow
class GameController {
    private final Player player;
    private final Computer computer;

    public GameController(Player player, Computer computer) {
        this.player = player;
        this.computer = computer;
    }

    // best-of-three game feature implementation
    public void playGame() {
        System.out.println("Let's play Rock-Paper-Scissors!");
        int playerScore = 0;
        int computerScore = 0;

        // Loop to play 3 rounds
        for (int round = 1; round <= 3; round++) {
            System.out.println("Round " + round);
            Round currentRound = new Round(player, computer);
            String result = currentRound.play();
            System.out.println(result);

            // Update scores based on round results
            if (result.equals("You win!")) {
                playerScore++;
            } else if (result.equals("Computer wins!")) {
                computerScore++;
            }
        }

        // Determine and announce the final winner
        determineFinalWinner(playerScore, computerScore);
    }

    private void determineFinalWinner(int playerScore, int computerScore) {
        if (playerScore > computerScore) {
            System.out.println("You are the final winner with " + playerScore + " points!");
        } else if (computerScore > playerScore) {
            System.out.println("Computer is the final winner with " + computerScore + " points.");
        } else {
            System.out.println("The match is tied!");
        }
    }
}

// Main class to start and play the game
public class RockPaperScissors {
    public static final Map<String, Choice> CHOICES = new HashMap<>();
    
    static {
        CHOICES.put("rock", new Rock());
        CHOICES.put("paper", new Paper());
        CHOICES.put("scissors", new Scissors());
    }

    public static void main(String[] args) {
        Player player = new Player();
        Computer computer = new Computer();
        GameController gameController = new GameController(player, computer);

        // Play the game
        gameController.playGame();
    }
}
