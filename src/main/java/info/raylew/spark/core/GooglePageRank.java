package info.raylew.spark.core;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author srikalyc
 *         PR(Vi) = (1-d) + d*(Sum Wji * PR(Vj) / (Sum Wkj))
 *         where Vj = In(Vi) and Vk = Out(Vj)
 */
public class GooglePageRank {
    public static Double DAMPING_FACTOR;// This is d
    public static int MAX_ITERATIONS;// If this is 0 then algorithm is run
    public ConcurrentMap<String, Double> similarityGraph;
    public ConcurrentMap<Integer, Double> ranks;
    public Map<Double, Integer> sortedRankSentences;///Sorted based on rank numbers
    public Set<Integer> sortedSentences;///Sorted based on rank numbers
    public int numNodesInGraph;

    public GooglePageRank() {
        this.similarityGraph = new ConcurrentHashMap<String, Double>();// Ex: edgename=12
        this.ranks = new ConcurrentHashMap<Integer, Double>();
        this.sortedRankSentences = new TreeMap<Double, Integer>(Collections.reverseOrder());
        this.sortedSentences = new TreeSet<Integer>();
    }

    public GooglePageRank(ConcurrentMap<String, Double> similarityGraph) {
        this.similarityGraph = similarityGraph;// Example: edgename=12
        this.ranks = new ConcurrentHashMap<Integer, Double>();
        this.sortedRankSentences = new TreeMap<Double, Integer>(Collections.reverseOrder());
        this.sortedSentences = new TreeSet<Integer>();
    }

    ///////////This simply is an implementation of the page rank learning algorithm
    ///////////The graph of sentences is the input to this method//////////////
    public void runPageRankAlgorithm() {
        initializeRanks();
        if (MAX_ITERATIONS == 0) {// //////Run until convergence.
            boolean converged = false;
            while (!converged) {
            }
        } else {///////////Stop learning after a set of iterations.
            for (int cnt = 0; cnt < MAX_ITERATIONS; cnt++) {
                ConcurrentMap<Integer, Double> tmpranks = new ConcurrentHashMap<Integer, Double>();
                for (int i = 1; i <= numNodesInGraph; i++) {
                    Double sum = 0.0;
                    for (int j = 1; j <= numNodesInGraph; j++) {
                        if (j == i)
                            continue;
                        Double Wji = (j < i) ? similarityGraph.get(j + "-" + i) : similarityGraph.get(i + "-" + j);
                        Double pageRankVj = ranks.get(j);
                        Double denSum = 0.0;
                        for (int k = 1; k <= numNodesInGraph; k++) {
                            if (k == j)
                                continue;
                            Double Wjk = (j < k) ? similarityGraph.get(j + "-" + k) : similarityGraph.get(k + "-" + j);
                            denSum += Wjk;
                        }
                        sum += (Wji * pageRankVj / (double) denSum);
                    }
                    double rnk = (1 - DAMPING_FACTOR) + DAMPING_FACTOR * sum;
//          ranks.remove(i);
//          ranks.put(i, rnk);
                    tmpranks.put(i, rnk);
                }
                diplayRanks(tmpranks);
                updateRanks(tmpranks);
//        diplayRanks(ranks);
            }
        }
        for (int i = 1; i <= ranks.size(); i++) {
            sortedRankSentences.put(ranks.get(i), i);
        }
        Iterator<Integer> iter = sortedRankSentences.values().iterator();
        int max = 0;
        //Summarizer.MAX_SENTENCES=16
        while (iter.hasNext() && max++ < 16) {
            sortedSentences.add(iter.next());
        }
        System.out.println("sorted sentence size is " + sortedSentences.size());
        //Util.DIPLAYMAP(sortedRankSentences);
    }

    ////////Start with all 1's //////
    public void initializeRanks() {
        for (int i = 1; i <= numNodesInGraph; i++) {
            ranks.put(i, 1.0);
        }
    }

    //////Update all the ranks with new ones /////////////
    public void updateRanks(ConcurrentMap<Integer, Double> newRanks) {
        ranks.clear();
        for (int i = 1; i <= numNodesInGraph; i++) {
            ranks.put(i, newRanks.get(i));
        }
    }

    /////////Displays the ranks on the console/////////////
    public void diplayRanks(ConcurrentMap<Integer, Double> newRanks) {
        for (int i = 1; i <= numNodesInGraph; i++) {
            //Util.INFO(i + " " + newRanks.get(i));
        }
    }
}
