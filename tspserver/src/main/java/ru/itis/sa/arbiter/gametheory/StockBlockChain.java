package ru.itis.sa.arbiter.gametheory;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

//@Component
public class StockBlockChain {

    private static Logger log = Logger.getLogger(StockBlockChain.class.getName());

    public List<StockBlockModel> bc;

    public static final List<StockBlockModel> chain = new ArrayList<>();

    public static final String FILE_NAME_CHAIN = "stock_block_chain.json";

    public static void readBlockChain() {
        File file = new File(FILE_NAME_CHAIN);

        if (file.exists()) {
            log.info("read stock chain from file " + FILE_NAME_CHAIN);
            ObjectMapper mapper = new ObjectMapper();
            try {
                StockBlockChain blockChain = mapper.readValue(file, StockBlockChain.class);
                if (blockChain != null && blockChain.bc != null && blockChain.bc.size() > 0) {
                    chain.clear();
                    for (StockBlockModel b : blockChain.bc) {
                        chain.add(b);
                    }
                }
            } catch (IOException e) {
                log.log(Level.SEVERE, "load chain from file ", e);

            }
        }

    }

    public static void saveBlockChain() {
        File file = new File(FILE_NAME_CHAIN);
        log.info("save stock chain to file " + FILE_NAME_CHAIN);
        ObjectMapper mapper = new ObjectMapper();
        StockBlockChain blockChain = new StockBlockChain();
        blockChain.bc = chain;
        try {
            mapper.writeValue(file, blockChain);
        } catch (IOException e) {
            log.log(Level.SEVERE, "save chain to file ", e);

        }
    }
}
