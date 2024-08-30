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

package it.unicam.cs.khanshaz123384.app.utils;

import it.unicam.cs.khanshaz123384.api.model.TrackConfiguration;
import it.unicam.cs.khanshaz123384.api.utils.*;
import it.unicam.cs.khanshaz123384.api.utils.Interfaces.IColorGenerator;
import it.unicam.cs.khanshaz123384.api.utils.Interfaces.IConfigurationParser;
import it.unicam.cs.khanshaz123384.api.utils.Interfaces.IFileReaderService;
import it.unicam.cs.khanshaz123384.api.utils.Interfaces.IPlayerParser;

import java.io.IOException;
import java.util.List;

/**
 * Loads and parses the track configuration from a specified file.
 *
 * <p>This class provides functionality to load a track configuration from a text
 * file. It uses various service and parser classes to read the file, parse its
 * contents, and create a {@link TrackConfiguration} object.</p>
 */
public class ConfigurationLoader {

    /**
     * Loads and parses the track configuration from the specified file path.
     *
     * <p>This method reads the content of the file, parses it to create a
     * {@link TrackConfiguration} object, and returns it.</p>
     *
     * @param filePath The path to the configuration file.
     * @return A {@link TrackConfiguration} object parsed from the file.
     * @throws IOException If an error occurs while reading the file.
     * @throws IllegalArgumentException If the file content is invalid or cannot be parsed.
     * @throws NullPointerException If the provided file path is {@code null}.
     */
    public TrackConfiguration loadConfiguration(String filePath) throws IOException, IllegalArgumentException {
        if (filePath == null)
            throw new NullPointerException("File path cannot be null.");


        // Initialize services and parsers
        IFileReaderService fileReaderService = new TxtFileReaderService();
        IColorGenerator colorGenerator = new ColorGenerator();
        IPlayerParser playerParser = new PlayerParser(colorGenerator);
        IConfigurationParser parser = new TrackConfigurationParser(playerParser);

        // Read file content
        List<String> lines = fileReaderService.readFile(filePath);

        // Parse configuration
        return parser.parse(lines);
    }
}