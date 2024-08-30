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

package it.unicam.cs.khanshaz123384.utils;


import it.unicam.cs.khanshaz123384.api.utils.TxtFileReaderService;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TxtFileReaderServiceTest {

    private final TxtFileReaderService fileReaderService = new TxtFileReaderService();

    @Test
    public void testReadFileWithValidTxtFile() throws IOException {
        Path tempFile = Files.createTempFile("test", ".txt");
        Files.write(tempFile, List.of("Line 1", "Line 2", "Line 3"), StandardOpenOption.WRITE);

        List<String> lines = fileReaderService.readFile(tempFile.toString());

        assertEquals(List.of("Line 1", "Line 2", "Line 3"), lines);
        Files.delete(tempFile);
    }

    @Test
    public void testReadEmptyFile() throws IOException {
        Path tempFile = Files.createTempFile("test", ".txt");
        Files.write(tempFile, List.of(), StandardOpenOption.WRITE);

        List<String> lines = fileReaderService.readFile(tempFile.toString());

        assertEquals(List.of(), lines);
        Files.delete(tempFile);
    }

    @Test
    public void testReadFileWithLargeContent() throws IOException {
        Path tempFile = Files.createTempFile("test", ".txt");
        String largeContent = "Line\n".repeat(1000);
        Files.write(tempFile, largeContent.getBytes(), StandardOpenOption.WRITE);

        List<String> lines = fileReaderService.readFile(tempFile.toString());

        assertEquals(1000, lines.size());
        assertTrue(lines.getFirst().startsWith("Line"));
        Files.delete(tempFile);
    }

    @Test
    public void testReadFileWithInvalidFileType() {
        Path tempFile = null;
        try {
            tempFile = Files.createTempFile("test", ".csv");
            Path finalTempFile = tempFile;
            assertThrows(IllegalArgumentException.class, () -> fileReaderService.readFile(finalTempFile.toString()));
        } catch (IOException e) {
            fail("IOException should not be thrown");
        } finally {
            if (tempFile != null) {
                try {
                    Files.delete(tempFile);
                } catch (IOException ignored) {
                }
            }
        }
    }

    @Test
    public void testReadNonExistentFile() {
        String nonExistentFilePath = "non_existent_file.txt";
        assertThrows(IOException.class, () -> fileReaderService.readFile(nonExistentFilePath));
    }
}
