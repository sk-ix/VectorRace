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

package it.unicam.cs.khanshaz123384.api.utils.Interfaces;

import java.io.IOException;
import java.util.List;

/**
 * An interface for reading the contents of a file.
 * <p>
 * This interface provides a method for reading lines from a file specified by its file path.
 * Implementations of this interface should handle file I/O operations and return the contents
 * of the file as a list of strings, where each string represents a line from the file.
 * </p>
 */
public interface IFileReaderService {

    /**
     * Reads the contents of a file specified by the given file path and returns it as a list of strings.
     * <p>
     * Each element in the returned list corresponds to a line in the file. The method should handle
     * file reading operations and ensure that any potential I/O errors are appropriately managed.
     * </p>
     *
     * @param filePath The path to the file to be read. This should be a valid path to an existing file.
     * @return A list of strings, where each string represents a line of the file content.
     * @throws IOException If an I/O error occurs while reading the file, such as if the file cannot be found
     *                     or accessed.
     */
    List<String> readFile(String filePath) throws IOException;
}