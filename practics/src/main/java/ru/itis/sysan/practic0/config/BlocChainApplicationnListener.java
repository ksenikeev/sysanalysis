package ru.itis.sysan.practic0.config;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ru.itis.sysan.practic0.bc.BlockChain;
import ru.itis.sysan.practic0.bc.BlockModel;
import ru.itis.sysan.practic0.bc.DataModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Обработка события - старта приложения
 * Формируем нулевой блок
 */
@Component
public class BlocChainApplicationnListener implements ApplicationListener<ApplicationStartedEvent> {

    private static Logger log = Logger.getLogger(BlocChainApplicationnListener.class.getName());

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event)  {

        log.info("start application");
        BlockChain.readBlockChain();

        if (BlockChain.chain.size() == 0) {

            log.info("blockchain size = 0");

            DataModel dm = new DataModel();
            BlockModel block = new BlockModel();
            dm.setName("Еникеева Зульфира Аснафовна");
            dm.setGroup("Кафедра");
            block.setData(dm);

            block.setTs(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SX").format(new Date()));

            BlockChain.chain.add(block);

            BlockChain.saveBlockChain();
        }

    }
}
