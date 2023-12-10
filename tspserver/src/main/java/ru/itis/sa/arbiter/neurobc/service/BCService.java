package ru.itis.sa.arbiter.neurobc.service;

import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Block;
import org.springframework.stereotype.Component;
import ru.itis.sa.arbiter.neurobc.BlockChain;
import ru.itis.sa.arbiter.neurobc.BlockModel;
import ru.itis.sa.arbiter.neurobc.NeuralNetwork;
import ru.itis.sa.arbiter.neurobc.NewBlockResponse;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class BCService {

    private static Logger log = Logger.getLogger(BCService.class.getName());

    //256-битное число с 12 первыми нулями
    private static int[] RIGHT_PART_INEQUALITY =
            {0x00,0x0F,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,
            0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,
            0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF};

    @Autowired
    private SignService signService;

    public NewBlockResponse validateAndAddNewBlock(BlockModel block) {
        //Вычиляем хеш нашего последнего блока и сравниваем с присланным prevHash
        // должны совпадать
        int sz = BlockChain.chain.size();
        try {
            String prevHash = sz > 0 ? new String(Hex.encode(signService.getHash(BlockChain.chain.get(sz - 1)))) : null;

            if (!block.getPrevhash().equals(prevHash)) {
                log.info("Hash error. Last block has hash: " + prevHash + "(" + block.getData().getPublickey() + ")");
                return new NewBlockResponse(2, "Hash error. Last block has hash: " + prevHash, null);
            }

            if (!validateNonce(block)) {
                log.info("nonce error.");
                return new NewBlockResponse(2, "nonce error", null);
            }
        } catch (Exception e) {
            log.log(Level.SEVERE,"error",e);
            return new NewBlockResponse(2, "server error: " + e.getMessage(), null);
        }

        // Проверяем подпись данных
        try {
            String data = block.getData().toString();
            if (!signService.verifyRSAPSSSignature(block.getData().getPublickey(), data, block.getSignature())) {
                log.info("Ошибка проверки подписи данных");
                return new NewBlockResponse(3, "Ошибка проверки подписи данных ", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        double block_e = 0;
        try {
            block_e = Double.parseDouble(block.getData().getE());
        } catch (NumberFormatException e) {
            log.log(Level.SEVERE,"error: NumberFormatException "+ "(" +  block.getData().getPublickey() + ")",e);
            return new NewBlockResponse(2, "Не удалось определить ошибку ваших параметров нейросети на тестовых данных: " + e.getMessage(), null);
        }

        // если ошибка > 0.01, то такие параметры блока не принимаются
        if (block_e > 1) {
            log.info("e > 1 (" + block.getData().getPublickey() + ")");
            return new NewBlockResponse(2,"e > 1",null);
        }

        //пересчитываем ошибку сами
        NeuralNetwork nn = new NeuralNetwork(block.getData());
        double e = nn.e();

        //сравниваем ошибку блока и пересчитанную ошибку
/*
        if (Math.abs(e - block_e) > 0.1) {
            return new NewBlockResponse(2, "расчитанная ошибка " + e + ", разница с предъявленной ошибкой превышает 0.000001 (вы предъявили " + block_e + ")", null);
        }
*/

        if (e > 1) {
            return new NewBlockResponse(2, "расчитанная ошибка " + e + " превышает 1 (вы предъявили " + block_e + ")", null);
        }

        Date ts = new Date();

        block.setTs( new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SX").format(ts));

        try {
            byte[] hash = signService.getHash(block);
            byte[] sign = signService.generateRSAPSSSignature( Hex.encode(hash));
            String arbiterSignature = new String(Hex.encode(sign));
            block.setArbitersignature(arbiterSignature);
        } catch (Exception e1) {
            log.log(Level.SEVERE,"error "+ "(" + block.getData().getPublickey() + ")",e1);
            return new NewBlockResponse(2, "Ошибка формирования подписи арбитра: " + e1.getMessage(), null);
        }

        BlockChain.chain.add(block);
        BlockChain.saveBlockChain();
        log.info("block " + block.getData().getPublickey() + " added ");
        NewBlockResponse resp =  new NewBlockResponse(0,"",block);

        return resp;
    }

    public boolean validateNonce(BlockModel block) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return (compare(signService.getHash(block), RIGHT_PART_INEQUALITY) <= 0);
    }

    public static int compare(byte[] a, int[] b) {
        int r = 0;
        for (int i = 0; i < 32; ++i) {
            System.out.println((a[i] & 0xFF) + " - " + b[i]);
            r = (a[i] & 0xFF) - b[i];
            if (r != 0) return r;
        }
        return r;
    }
}
