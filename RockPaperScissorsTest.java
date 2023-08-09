import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RockPaperScissorsTest {

    @Test
    public void testRockIsBetterThan() {
        Rock rock = new Rock();
        assertTrue(rock.isBetterThan(new Scissors()));
        assertFalse(rock.isBetterThan(new Paper()));
        assertFalse(rock.isBetterThan(new Rock()));
    }

    @Test
    public void testPaperIsBetterThan() {
        Paper paper = new Paper();
        assertTrue(paper.isBetterThan(new Rock()));
        assertFalse(paper.isBetterThan(new Scissors()));
        assertFalse(paper.isBetterThan(new Paper()));
    }

    @Test
    public void testScissorsIsBetterThan() {
        Scissors scissors = new Scissors();
        assertTrue(scissors.isBetterThan(new Paper()));
        assertFalse(scissors.isBetterThan(new Rock()));
        assertFalse(scissors.isBetterThan(new Scissors()));
    }

    @Test
    public void testPlayerChoice() {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("rock\n".getBytes());
        System.setIn(inputStream);

        Player player = new Player();
        assertEquals("rock", player.getChoice().getName());
    }

    @Test
    public void testComputerChoiceIsValid() {
        Computer computer = new Computer();
        List<String> validChoices = Arrays.asList("rock", "paper", "scissors");

        Choice computerChoice = computer.getChoice();
        assertTrue(validChoices.contains(computerChoice.getName()));
    }

    // For testing Round and GameController, it can be a bit more challenging due to randomness and user inputs.
    // You may need to implement dependency injection or mocking techniques to better unit test those classes.
    // Here's a simple example for Round:

    @Test
    public void testRound() {
        // Mocking the Player and Computer choice for deterministic testing.
        Player mockPlayer = new Player() {
            @Override
            public Choice getChoice() {
                return new Rock();
            }
        };

        Computer mockComputer = new Computer() {
            @Override
            public Choice getChoice() {
                return new Scissors();
            }
        };

        Round round = new Round(mockPlayer, mockComputer);
        assertEquals("You win!", round.play());
    }
}
