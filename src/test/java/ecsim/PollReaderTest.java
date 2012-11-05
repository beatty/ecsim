package ecsim;

import junit.framework.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public class PollReaderTest {
  @Test
  public void testEc() throws IOException {
    Map<String, Integer> ec = PollReader.ec(new FileInputStream("data/polls.csv"));
    Assert.assertEquals(51, ec.size());
  }

  @Test
  public void testPolls() throws IOException {
    Iterable<PollReader.Poll> ec = PollReader.polls(new FileInputStream("data/polls.csv"));
    int count = 0;
    for (PollReader.Poll poll : ec) {
      count++;
    }
    Assert.assertEquals(count, 905);
  }
}
