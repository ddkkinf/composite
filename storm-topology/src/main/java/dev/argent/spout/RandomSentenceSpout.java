package dev.argent.spout;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

public class RandomSentenceSpout extends BaseRichSpout {
    private static final long serialVersionUID = 8746229559195048103L;

    private SpoutOutputCollector collector;
    private List<String> sentences;

    @Override
    @SuppressWarnings("rawtypes")
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
        sentences = Arrays.asList("the cow jumped over the moon", "an apple a day keeps the doctor away",
                                           "four score and seven years ago", "snow white and the seven dwarfs", "i am at two with nature");
    }

    @Override
    public void nextTuple() {
        Utils.sleep(100);
        Collections.shuffle(sentences);
        sentences.stream()
                 .findFirst()
                 .map(Values::new)
                 .ifPresent(collector::emit);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }

}
