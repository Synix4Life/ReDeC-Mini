package io.github.synix4life.compiler.redec_mini;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Objects;

public class Main {
    private static String[] data;

    /**
     * Method to read the stdin or the input file
     * @throws IOException from BufferedReader.readLine()
     */
    private static void read_stdin() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        StringBuilder buffer = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            buffer.append(line).append("\n");
        }

        String program = buffer.toString();
        data = program.split("\\R");
    }

    public static void main(String[] args) throws IOException, IllegalArgumentException {
        try{
            read_stdin();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        boolean debugMode = false;
        if(Objects.equals(data[0], "DEBUG")){
            debugMode = true;
            data = java.util.Arrays.copyOfRange(data, 1, data.length);
        }

        Pipeline.runPipeline(data, debugMode);
    }
}