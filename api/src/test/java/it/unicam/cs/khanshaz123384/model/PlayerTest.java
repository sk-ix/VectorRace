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

package it.unicam.cs.khanshaz123384.model;

import it.unicam.cs.khanshaz123384.api.model.HumanPlayer;
import it.unicam.cs.khanshaz123384.api.utils.ColorGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private HumanPlayer player;

    @BeforeEach
    void setUp() {
        player = new HumanPlayer("Player1", new int[]{0, 0}, new int[]{1, 0}, new ColorGenerator(), 1);
    }

    @Test
    void testGetName() {
        assertEquals("Player1", player.getName());
    }

    @Test
    void testGetColor() {
        assertNotNull(player.getColor());
    }

    @Test
    void testGetPosition() {
        assertArrayEquals(new int[]{0, 0}, player.getPosition());
    }

    @Test
    void testSetPosition() {
        player.setPosition(5, 5);
        assertArrayEquals(new int[]{5, 5}, player.getPosition());
        assertArrayEquals(new int[]{0, 0}, player.getPreviousPosition());
    }

    @Test
    void testSetPositionInvalidCoordinates() {
        assertThrows(IllegalArgumentException.class, () -> player.setPosition(-1, -1));
    }


    @Test
    void testGetVelocity() {
        assertArrayEquals(new int[]{1, 0}, player.getVelocity());
    }

    @Test
    void testSetVelocity() {
        player.setVelocity(2, 2);
        assertArrayEquals(new int[]{2, 2}, player.getVelocity());
    }

    @Test
    void testSetInvalidVelocity() {
        assertThrows(IllegalArgumentException.class, () -> player.setVelocity(-1, -1));
    }

    @Test
    void testGetRank() {
        assertEquals(1, player.getRank());
    }

}
