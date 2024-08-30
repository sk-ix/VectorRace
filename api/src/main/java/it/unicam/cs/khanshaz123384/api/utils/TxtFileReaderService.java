/*
 * MIT License
 *
 * Copyright (c) 2024  Khan Shaz
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package it.unicam.cs.khanshaz123384.api.utils;

import it.unicam.cs.khanshaz123384.api.utils.Interfaces.IFileReaderService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for reading text files (.txt).
 *
 * <p>This class implements the {@link IFileReaderService} interface to provide functionality
 * for reading text files. It verifies that the file has a .txt extension before attempting
 * to read its contents.</p>
 */
public class TxtFileReaderService implements IFileReaderService {

    /**
     * Reads the contents of a text file specified by the file path.
     *
     * <p>This method first checks if the file has a .txt extension. If it does, the file is read line
     * by line, and each line is added to a list of strings which is then returned. If the file type is
     * incorrect, an {@link IllegalArgumentException} is thrown.</p>
     *
     * @param filePath The path to the text file to be read.
     * @return A list of strings, where each string represents a line from the text file.
     * @throws IOException if an I/O error occurs while reading the file.
     * @throws IllegalArgumentException if the file does not have a .txt extension.
     */
    @Override
    public List<String> readFile(String filePath) throws IOException {
        if (!isTxtFile(filePath)) {
            throw new IllegalArgumentException("Invalid file type. Expected a .txt file.");
        }

        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    /**
     * Checks if the given file path refers to a .txt file.
     *
     * <p>This helper method extracts the file extension from the provided file path and checks if
     * it matches "txt". Case is ignored during comparison.</p>
     *
     * @param filePath The path to the file whose extension is to be checked.
     * @return {@code true} if the file has a .txt extension; {@code false} otherwise.
     */
    private boolean isTxtFile(String filePath) {
        String extension = getFileExtension(filePath);
        return "txt".equalsIgnoreCase(extension);
    }

    /**
     * Extracts the file extension from a file path.
     *
     * <p>This helper method finds the last occurrence of the dot character (.) in the file path
     * and returns the substring that follows it as the file extension. If the dot is not found or
     * if it is at the end of the file path, {@code null} is returned.</p>
     *
     * @param filePath The path to the file from which to extract the extension.
     * @return The file extension in lowercase, or {@code null} if no extension is found.
     * @throws IllegalStateException if the file path is {@code null}.
     */
    private String getFileExtension(String filePath) {
        if (filePath == null)
            throw new IllegalStateException("File path cannot be null.");


        int dotIndex = filePath.lastIndexOf('.');

        // Ensure the dot is not the first character and there is at least one character after it
        if (dotIndex > 0 && dotIndex < filePath.length() - 1)
            return filePath.substring(dotIndex + 1).toLowerCase();

        return null;
    }
}