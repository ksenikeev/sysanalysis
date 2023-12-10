package ru.itis.sa.arbiter.neurobc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import ru.itis.sa.arbiter.neurobc.controller.NeuroRestController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class BlockChain {

    private static Logger log = Logger.getLogger(BlockChain.class.getName());

    public List<BlockModel> bc;

    public static final List<BlockModel> chain = new ArrayList<>();

    public static final String FILE_NAME_CHAIN = "n_block_chain.json";

    public static void readBlockChain() {
        File file = new File(FILE_NAME_CHAIN);

        if (file.exists()) {
            log.info("read chain from file " + FILE_NAME_CHAIN);
            ObjectMapper mapper = new ObjectMapper();
            try {
                BlockChain blockChain = mapper.readValue(file, BlockChain.class);
                if (blockChain != null && blockChain.bc != null && blockChain.bc.size() > 0) {
                    chain.clear();
                    for (BlockModel b : blockChain.bc) {
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
        log.info("save chain to file " + FILE_NAME_CHAIN);
        ObjectMapper mapper = new ObjectMapper();
        BlockChain blockChain = new BlockChain();
        blockChain.bc = chain;
        try {
            mapper.writeValue(file, blockChain);
        } catch (IOException e) {
            log.log(Level.SEVERE, "save chain to file ", e);

        }
    }
}
