package ru.itis.sa.arbiter.config;

import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ru.itis.sa.arbiter.neurobc.BlockChain;
import ru.itis.sa.arbiter.neurobc.BlockModel;
import ru.itis.sa.arbiter.neurobc.DataModel;
import ru.itis.sa.arbiter.neurobc.controller.NeuroRestController;
import ru.itis.sa.arbiter.neurobc.service.SignService;
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

    @Autowired
    private SignService signService;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event)  {

        log.info("start application");

        BlockChain.readBlockChain();

        if (BlockChain.chain.size() == 0) {

            log.info("blockchain size = 0");

            DataModel dm = new DataModel("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "0.424685578843", "30819f300d06092a864886f70d010101050003818d0030818902818100a811365d2f3642952751029edf87c8fa2aeb6e0feafcf800190a7dd2cf750c63262f6abd8ef52b251c0e10291d5e2f7e6682de1aae1d64d4f9b242050f898744ca300a44c4d8fc8af0e7a1c7fd9b606d7bde304b29bec01fbef554df6ba1b7b1ec355e1ff68bd37f3d40fb27d1aa233fe3dd6b63f7241e734739851ce8c590f70203010001");
            BlockModel block = new BlockModel();
            block.setData(dm);
            try {
                String signature = new String(Hex.encode(signService.generateRSAPSSSignature(dm.toString().getBytes())));
                block.setSignature(signature);

                block.setTs(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SX").format(new Date()));

                //Подписываем хеш : [предыдущий хеш] + данные + подпись + метка времени

                String arbiterSignature = new String(
                        Hex.encode(signService.generateRSAPSSSignature(Hex.encode(signService.getHash(block)))));

                block.setArbitersignature(arbiterSignature);
                block.setInfo("Enikeev Kamil, first  arbiters block");
            } catch (Exception e) {
                e.printStackTrace();
            }
            BlockChain.chain.add(block);

            BlockChain.saveBlockChain();
        }
    }
}
