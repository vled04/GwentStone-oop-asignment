package org.poo.main;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.checker.Checker;
import org.poo.checker.CheckerConstants;
import org.poo.fileio.GameInput;
import org.poo.fileio.Input;
import org.poo.game.Match;
import org.poo.game.Player;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;




/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
        ;
    }

    /**
     * DO NOT MODIFY MAIN METHOD
     * Call the checker
     *
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(CheckerConstants.TESTS_PATH);
        Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            File resultFile = new File(String.valueOf(path));
            for (File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            String filepath = CheckerConstants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getName(), filepath);
            }
        }

        Checker.calculateScore();
    }

    /**
     *
     * @param firstPath
     * @param secondPath
     * @throws IOException
     */
    public static void action(final String firstPath, final String secondPath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Input dataInput = mapper.readValue(new File(CheckerConstants.TESTS_PATH
                + firstPath), Input.class);
        ArrayNode output = mapper.createArrayNode();
        Player player1 = new Player();
        Player player2 = new Player();

        for (GameInput gameInput : dataInput.getGames()) {
            Match match = new Match(player1, player2, dataInput, gameInput, output);
            match.playGame();
        }
        ObjectWriter objectWriter = mapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(secondPath), output);
    }
}
