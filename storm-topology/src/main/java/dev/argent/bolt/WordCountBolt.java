package dev.argent.bolt;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class WordCountBolt extends BaseBasicBolt {
    private static final long serialVersionUID = 1975346725239772248L;

    Map<String, Integer> counts = new HashMap<>();

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        String word = input.getString(0);
        counts.compute(word, (k, v) -> Optional.ofNullable(v).map(i -> i++).orElse(1));
        int count = counts.getOrDefault(word, 0);
        collector.emit(new Values(word, count));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word", "count"));
    }
}
