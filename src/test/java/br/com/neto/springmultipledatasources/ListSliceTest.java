package br.com.neto.springmultipledatasources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

class ListSliceTest {

    private static final Logger LOG = LogManager.getLogger();

    @Test
    void sliceTest() {
        List<Integer> intList = IntStream.rangeClosed(1, 17).boxed().toList();

        List<List<Integer>> partition = Lists.partition(intList, 5);

        try {
            LOG.info(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(partition));
            Assertions.assertEquals(4, partition.size());
        } catch (JsonProcessingException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
