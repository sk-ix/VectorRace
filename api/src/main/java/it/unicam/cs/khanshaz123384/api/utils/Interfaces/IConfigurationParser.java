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

import it.unicam.cs.khanshaz123384.api.model.TrackConfiguration;

import java.util.List;

/**
 * An interface for parsing configuration data for a track.
 * <p>
 * This interface defines a method for parsing a list of strings into a {@link TrackConfiguration} object.
 * Implementations of this interface should convert the provided list of lines into a configuration
 * that defines the layout of the track, including grid layout, player information, and movement direction.
 * </p>
 */
public interface IConfigurationParser {

    /**
     * Parses a list of strings representing track configuration data and returns a {@link TrackConfiguration} object.
     * <p>
     * The method should process the provided lines of configuration data to create and populate a {@link TrackConfiguration}
     * instance. The specific format of the input lines should be defined by the implementation.
     * </p>
     *
     * @param lines A list of strings where each string represents a line of configuration data for the track.
     *              The format and content of these lines should be understood by the implementation to properly
     *              parse and create the {@link TrackConfiguration}.
     * @return A {@link TrackConfiguration} object that encapsulates the parsed configuration data.
     * @throws IllegalArgumentException If the provided lines do not adhere to the expected format or if there are
     *                                  issues with the data that prevent successful parsing.
     */
    TrackConfiguration parse(List<String> lines) throws IllegalArgumentException;
}