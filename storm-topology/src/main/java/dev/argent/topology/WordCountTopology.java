package dev.argent.topology;

import java.util.concurrent.TimeUnit;

import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;

import dev.argent.bolt.SplitSentenceBolt;
import dev.argent.bolt.WordCountBolt;
import dev.argent.spout.RandomSentenceSpout;

public class WordCountTopology {
    public static void main(String[] args)
            throws InvalidTopologyException, AuthorizationException, AlreadyAliveException {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("spout", new RandomSentenceSpout(), 5);
        builder.setBolt("split", new SplitSentenceBolt(), 8).shuffleGrouping("spout");
        builder.setBolt("count", new WordCountBolt(), 12).shuffleGrouping("split");
        StormTopology topology = builder.createTopology();

        StormSubmitter.submitTopology("wordCount", prepareTopologyConfig(), topology);
    }

    static Config prepareTopologyConfig() {
        Config config = new Config();
        config.setDebug(true);
        config.setNumWorkers(5);
        config.setNumAckers(5);
        config.setMessageTimeoutSecs((int)TimeUnit.HOURS.toSeconds(1));
        config.put(Config.TOPOLOGY_TRANSFER_BUFFER_SIZE, 64);
        config.put(Config.TOPOLOGY_EXECUTOR_RECEIVE_BUFFER_SIZE, 16384);
        config.put(Config.TOPOLOGY_EXECUTOR_SEND_BUFFER_SIZE, 16384);
        return config;
    }
}
