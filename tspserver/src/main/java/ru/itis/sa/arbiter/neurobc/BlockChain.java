package ru.itis.sa.arbiter.neurobc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class BlockChain {

    public List<BlockModel> bc;

    public static final List<BlockModel> chain = new ArrayList<>();

    public static final String FILE_NAME_CHAIN = System.getProperty("user.home") + File.separator + "block_chain.json";

    public static void readBlockChain() {
        File file = new File(FILE_NAME_CHAIN);

        if (file.exists()) {
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
                e.printStackTrace();
            }
        }
    }

    public static void saveBlockChain() {
        File file = new File(FILE_NAME_CHAIN);
        ObjectMapper mapper = new ObjectMapper();
        BlockChain blockChain = new BlockChain();
        blockChain.bc = chain;
        try {
            mapper.writeValue(file, blockChain);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
