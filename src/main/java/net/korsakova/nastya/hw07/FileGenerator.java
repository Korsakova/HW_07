package net.korsakova.nastya.hw07;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileGenerator {

    private final static int LOWER_BOUND = 1;
    private final static int SENTENCE_UPPER_BOUND = 15;
    private final static int PARAGRAPH_UPPER_BOUND = 20;

    private final static int WORD_UPPER_BOUND = 15;
    private final static int LOW_A_POSITION = 97;
    private final static int CAPITAL_A_POSITION = 65;
    private final static int LOW_Z_POSITION = 122;
    private final static int CAPITAL_Z_POSITION = 90;

    private final static char WORD_DELIMITER = ' ';
    private final static String SENTENCE_DELIMITER = "\r\n";
    private final static char[] SENTENCE_TAIL = {'.', '!', '?'};

    private final static char COMMA = ',';
    private final static double COMMA_PROBABILITY = 0.05;

    private final static String FILE_CREATION_MASK = "/generated_file_%s.txt";

    public static void main(String[] args) {
        createFiles("C:\\Users\\Admin\\IdeaProjects", 10, 5, new String[]{"radish", "rabbit", "dandelion", "insect"}, 15);
    }

    public static void createFiles(String path, int n, int size, String[] words, int probability) {
        for (int i = 0; i < n; i++) {
            createFile(String.format(path + FILE_CREATION_MASK, i), size, words, probability);
        }
    }

    /**
     * Генератор файла
     *
     * @param outputFileName  название файла, в который будет производиться запись
     * @param paragraphsCount будем считать за количество абзацев, чтобы жизнь себе не усложнять
     * @param words           набор предзаданных слов
     * @param probability     вероятность того что слово будет добавлено в предложение
     */
    @SneakyThrows
    public static void createFile(String outputFileName, int paragraphsCount, String[] words, double probability) {
        final StringBuilder text = new StringBuilder();

        for (int p = 0; p < paragraphsCount; p++) {
            final int sentencesInCurrentParagraph = (int) (LOWER_BOUND + Math.random() * PARAGRAPH_UPPER_BOUND);

            for (int s = 0; s < sentencesInCurrentParagraph; s++) {
                final int wordsInCurrentSentence = (int) (LOWER_BOUND + Math.random() * SENTENCE_UPPER_BOUND);
                final int predictedWordPointer = Math.random() < (1 / probability) ? (int) (Math.random() * wordsInCurrentSentence) : -1;

                for (int w = 0; w < wordsInCurrentSentence; w++) {
                    if (predictedWordPointer == w) {
                        String word = words[(int) (Math.random() * words.length)];
                        text.append(w == 0 ? StringUtils.capitalize(word) : word);
                    } else
                        word(text, w == 0);

                    if(w == wordsInCurrentSentence - 1)
                        text.append(SENTENCE_TAIL[(int) (Math.random() * SENTENCE_TAIL.length)]);
                    else {
                        if(Math.random() < COMMA_PROBABILITY)
                            text.append(COMMA);
                        text.append(WORD_DELIMITER);
                    }
                }

                if(s == sentencesInCurrentParagraph - 1)
                    text.append(SENTENCE_DELIMITER);
                else {
                    text.append(WORD_DELIMITER);
                }
            }
        }

        final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFileName)));
        writer.write(text.toString());
        writer.flush();
    }

    private static void word(StringBuilder sb, boolean capitalized) {
        final int lettersCount = (int) (LOWER_BOUND + Math.random() * WORD_UPPER_BOUND);

        for (int i = 0; i < lettersCount; i++) {
            sb.append(i == 0 && capitalized ?
                    (char) (CAPITAL_A_POSITION + Math.random() * (CAPITAL_Z_POSITION - CAPITAL_A_POSITION)) :
                    (char) (LOW_A_POSITION + Math.random() * (LOW_Z_POSITION - LOW_A_POSITION))
            );
        }

    }
}
