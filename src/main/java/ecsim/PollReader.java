package ecsim;

import com.google.common.base.Splitter;
import com.google.common.io.CharStreams;

import java.io.*;
import java.util.*;

public class PollReader {
	public static class Poll {
    public final String state;
    public final int dem;
    public final int gop;

    public Poll(String state, int dem, int gop) {
      this.state = state;
      this.dem = dem;
      this.gop = gop;
    }

    @Override
    public String toString() {
      return "Poll{" +
          "state='" + state + '\'' +
          ", dem=" + dem +
          ", gop=" + gop +
          '}';
    }
  }

  public static Iterable<Poll> polls(InputStream in) throws IOException {
    final List<Poll> polls = new LinkedList<Poll>();

    List<String> lines = CharStreams.readLines(new InputStreamReader(in));
    int i=0;
    for (String line : lines) {
      // skip first line
      if (i++ == 0) {
        continue;
      }
      final Iterator<String> it = Splitter.on(',').split(line).iterator();

      it.next(); // ignore days
      it.next(); // ignore len
      final String state = it.next();
      it.next(); // ignore ec votes
      final int dem = Integer.parseInt(it.next());
      final int gop = Integer.parseInt(it.next());
      polls.add(new Poll(state, dem, gop));
    }
    return polls;
  }

  public static Map<String,Integer> ec(InputStream in) throws IOException {
    final HashMap<String, Integer> ecVotes = new HashMap<String, Integer>();

    List<String> lines = CharStreams.readLines(new InputStreamReader(in));
    int i=0;
    for (String line : lines) {
      // skip first line
      if (i++ == 0) {
        continue;
      }
      final Iterator<String> it = Splitter.on(',').split(line).iterator();
      it.next(); // ignore days
      it.next(); // ignore len
      final String state = it.next();
      final int votes = Integer.parseInt(it.next());
      ecVotes.put(state, votes);
    }
    return ecVotes;
  }


  public static void main(String[] args) throws IOException {
    final String filename = args[0];
    Iterable<Poll> polls = polls(new FileInputStream(filename));
    Map<String, Integer> ec = ec(new FileInputStream(filename));

    for (Poll poll : polls) {
      System.out.println(poll);
    }

    for (Map.Entry<String, Integer> state : ec.entrySet()) {
      System.out.println(state.getKey() + ": " + state.getValue());
    }
    System.out.println(ec.size());
  }
}