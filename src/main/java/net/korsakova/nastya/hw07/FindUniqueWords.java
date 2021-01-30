package net.korsakova.nastya.hw07;

import lombok.SneakyThrows;

import java.io.*;
import java.util.*;

public class FindUniqueWords {

    private static final String DELIMITER = " ";

    /**
     * Написать программу, читающую текстовый файл. Программа должна составлять отсортированный по алфавиту список слов,
     * найденных в файле и сохранять его в файл-результат. Найденные слова не должны повторяться, регистр не должен
     * учитываться. Одно слово в разных падежах – это разные слова.
     * <p>
     * Перенос слов не учитывается!
     */
    @SneakyThrows
    public static void main(String[] args) {
        if (args.length != 2)
            throw new IllegalArgumentException("missed file names!");

        final String inputFileName = args[0];
        final String outputFileName = args[1];

        final BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(FindUniqueWords.class.getClassLoader().getResourceAsStream(inputFileName))
                )
        );

        final Set<String> unique = new TreeSet<>();
        final StringBuilder sb = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            char[] chars = line.toCharArray();

            for (int i = 0; i < chars.length; i++) {
                char ch = chars[i];

                if( (ch <= 9) || (ch >= 97 && ch <= 122) || (ch >= 65 && ch <= 90) ){
                    sb.append(ch);
                }else {
                    flush(sb, unique);
                }
            }
        }

        final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFileName)));
        writer.write(unique.toString());
        writer.flush();
    }

    public static void flush(StringBuilder sb, Set<String> unique){
        final String token = sb.toString().toLowerCase(Locale.ROOT);

        if(!token.isEmpty())
            unique.add(token);

        sb.setLength(0);
    }
}
