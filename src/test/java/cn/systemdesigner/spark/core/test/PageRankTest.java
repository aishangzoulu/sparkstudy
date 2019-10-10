package cn.systemdesigner.spark.core.test;

import cn.systemdesigner.spark.core.GooglePageRank;
import org.junit.Test;

/**
 * Created by Raymond on 2016/12/18.
 */
public class PageRankTest {
    @Test
    public void testPageRank() {
        GooglePageRank googlePageRank=new GooglePageRank();
        googlePageRank.runPageRankAlgorithm();
    }
}
