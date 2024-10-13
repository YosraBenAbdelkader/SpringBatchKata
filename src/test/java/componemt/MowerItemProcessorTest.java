package componemt;

import com.kata.springbatchkata.component.MowerItemProcessor;
import com.kata.springbatchkata.dto.Mower;
import com.kata.springbatchkata.dto.MowerInstruction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class MowerItemProcessorTest {

    private MowerItemProcessor processor;

    @BeforeEach
    public void setup() {
        processor = new MowerItemProcessor();
    }

    @Test
    public void testProcessMowerInstruction() throws Exception {
        MowerInstruction instruction = new MowerInstruction(1, 2, 'N', "GAGAGAGAA");

        List<Mower> result = processor.process(instruction);

        assertNotNull(result, "The processed result should not be null");
        assertEquals(1, result.size(), "There should be exactly one mower in the result");

        Mower mower = result.get(0);
        assertEquals(1, mower.getX(), "The final X position of the mower should be 1");
        assertEquals(3, mower.getY(), "The final Y position of the mower should be 3");
        assertEquals('N', mower.getOrientation(), "The final orientation of the mower should be N");
    }

    @Test
    public void testProcessSecondMowerInstruction() throws Exception {
        MowerInstruction instruction = new MowerInstruction(3, 3, 'E', "AADAADADDA");

        List<Mower> result = processor.process(instruction);

        assertNotNull(result, "The processed result should not be null");
        assertEquals(1, result.size(), "There should be exactly one mower in the result");

        Mower mower = result.get(0);
        assertEquals(5, mower.getX(), "The final X position of the mower should be 5");
        assertEquals(1, mower.getY(), "The final Y position of the mower should be 1");
        assertEquals('E', mower.getOrientation(), "The final orientation of the mower should be E");
    }
}
