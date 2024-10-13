package componemt;

import com.kata.springbatchkata.component.MowerItemReader;
import com.kata.springbatchkata.dto.MowerInstruction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MowerInstructionReaderTest {

    private MowerItemReader reader;

    @BeforeEach
    public void setup() {
        reader = new MowerItemReader("input.txt");
    }
    @Test
    public void testReader() throws Exception {
        MowerInstruction instruction = reader.read();
        assertNotNull(instruction, "The first MowerInstruction should not be null");
        assertEquals(1, instruction.getX(), "First MowerInstruction X should be 1");
        assertEquals(2, instruction.getY(), "First MowerInstruction Y should be 2");
        assertEquals('N', instruction.getOrientation(), "First MowerInstruction orientation should be N");
        assertEquals("GAGAGAGAA", instruction.getInstructions(), "First MowerInstruction instructions should be GAGAGAGAA");

        MowerInstruction secondInstruction = reader.read();
        assertNotNull(secondInstruction, "The second MowerInstruction should not be null");
        assertEquals(3, secondInstruction.getX(), "Second MowerInstruction X should be 3");
        assertEquals(3, secondInstruction.getY(), "Second MowerInstruction Y should be 3");
        assertEquals('E', secondInstruction.getOrientation(), "Second MowerInstruction orientation should be E");
        assertEquals("AADAADADDA", secondInstruction.getInstructions(), "Second MowerInstruction instructions should be AADAADADDA");

        MowerInstruction nullInstruction = reader.read();
        assertEquals(null, nullInstruction, "After reading all instructions, the reader should return null");
    }
}

