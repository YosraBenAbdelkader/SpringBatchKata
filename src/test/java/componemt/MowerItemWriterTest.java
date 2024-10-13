package componemt;

import com.kata.springbatchkata.component.MowerItemWriter;
import com.kata.springbatchkata.dto.Mower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.io.Resource;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class MowerItemWriterTest {

    private MowerItemWriter mowerItemWriter;
    private Resource mockResource;

    @BeforeEach
    public void setup() throws IOException {
        // Mock the Resource object for the output file
        mockResource = Mockito.mock(Resource.class);

        // Create the directory if it doesn't exist
        Path testOutputDir = Paths.get("src/test/resources");
        if (Files.notExists(testOutputDir)) {
            Files.createDirectories(testOutputDir);
        }

        when(mockResource.getFile()).thenReturn(new File("src/test/resources/output.txt"));

        mowerItemWriter = new MowerItemWriter(mockResource);

        Files.deleteIfExists(Paths.get("src/test/resources/output.txt"));
    }

    @Test
    public void testWriteMowerPositions() throws Exception {
        Mower mower1 = new Mower(1, 3, 'N');
        Mower mower2 = new Mower(5, 1, 'E');
        List<Mower> mowers = Arrays.asList(mower1, mower2);

        List<List<Mower>> mowerBatches = new ArrayList<>();
        mowerBatches.add(mowers);

        mowerItemWriter.write(mowerBatches);

        List<String> lines = Files.readAllLines(Paths.get("src/test/resources/output.txt"));

        assertTrue(lines.contains("1 3 N"), "Output should contain '1 3 N'");
        assertTrue(lines.contains("5 1 E"), "Output should contain '5 1 E'");
    }
}
