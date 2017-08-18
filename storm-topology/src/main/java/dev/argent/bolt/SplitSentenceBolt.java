package dev.argent.bolt;

import java.util.Arrays;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SplitSentenceBolt extends BaseBasicBolt {
    private static final long serialVersionUID = -4182873975229079298L;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        String sentence = input.getString(0);
        Arrays.stream(sentence.split(" "))
              .forEach(word -> collector.emit(new Values(word)));
    }

    @Override
    public void cleanup() {
        log.info("{} cleanup.", this.getClass().getSimpleName());
    }
}
