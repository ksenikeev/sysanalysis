package ru.itis.sa.arbiter.neurobc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class Autors {

    private static Logger log = Logger.getLogger(Autors.class.getName());

    public List<AutorInfo> autorInfos;

    public static final Map<String, AutorInfo> autors = new HashMap<>();

    public static final String FILE_NAME_AUTORS = "n_autors.json";

    public static void readAutors() {
        File file = new File(FILE_NAME_AUTORS);

        if (file.exists()) {
            log.info("read autors from file " + FILE_NAME_AUTORS);
            ObjectMapper mapper = new ObjectMapper();
            try {
                Autors blockChain = mapper.readValue(file, Autors.class);
                if (blockChain != null && blockChain.autorInfos != null && blockChain.autorInfos.size() > 0) {
                    autors.clear();
                    for (AutorInfo b : blockChain.autorInfos) {
                        autors.put(b.getPublickey(), b);
                    }
                }
            } catch (IOException e) {
                log.log(Level.SEVERE, "load autors from file ", e);

            }
        }
    }

    public static void saveAutors(AutorInfo autorInfo) {
        autors.put(autorInfo.getPublickey(), autorInfo);
        File file = new File(FILE_NAME_AUTORS);
        log.info("save chain to file " + FILE_NAME_AUTORS);
        ObjectMapper mapper = new ObjectMapper();
        Autors blockChain = new Autors();
        blockChain.autorInfos = new ArrayList<>();
                autors.forEach((p,a) -> blockChain.autorInfos.add(a));
        try {
            mapper.writeValue(file, blockChain);
        } catch (IOException e) {
            log.log(Level.SEVERE, "save autors to file ", e);
        }
    }
}
