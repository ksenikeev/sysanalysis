package ru.itis.sa.arbiter.gametheory;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

//@Component
public class CurrencyBlockChain {

    private static Logger log = Logger.getLogger(CurrencyBlockChain.class.getName());

    public List<CurrencyBlockModel> bc;
    public List<CurrencyBlockModel> bc2;

    public static final List<CurrencyBlockModel> chain = new ArrayList<>();
    public static final List<CurrencyBlockModel> chain2 = new ArrayList<>();

    public static final String FILE_NAME_CHAIN1 = System.getProperty("user.home") + File.separator + "currency_block_chain.json";

    public static void readBlockChain() {
        File file = new File(FILE_NAME_CHAIN1);

        if (file.exists()) {
            log.info("read chain from file " + FILE_NAME_CHAIN1);
            ObjectMapper mapper = new ObjectMapper();
            try {
                CurrencyBlockChain blockChain = mapper.readValue(file, CurrencyBlockChain.class);
                if (blockChain != null && blockChain.bc != null && blockChain.bc.size() > 0) {
                    chain.clear();
                    for (CurrencyBlockModel b : blockChain.bc) {
                        chain.add(b);
                    }
                    chain2.clear();
                    for (CurrencyBlockModel b2 : blockChain.bc2) {
                        chain2.add(b2);
                    }
                }
            } catch (IOException e) {
                log.log(Level.SEVERE, "load chain from file ", e);

            }
        }

    }

    public static void saveBlockChain() {
        File file = new File(FILE_NAME_CHAIN1);
        log.info("save chain to file " + FILE_NAME_CHAIN1);
        ObjectMapper mapper = new ObjectMapper();
        CurrencyBlockChain blockChain = new CurrencyBlockChain();
        blockChain.bc = chain;
        blockChain.bc2 = chain2;
        try {
            mapper.writeValue(file, blockChain);
        } catch (IOException e) {
            log.log(Level.SEVERE, "save chain to file ", e);

        }
    }
}
