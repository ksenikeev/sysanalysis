package ru.itis.sa.arbiter.neurobc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.itis.sa.arbiter.neurobc.BlockChain;
import ru.itis.sa.arbiter.neurobc.BlockModel;
import ru.itis.sa.arbiter.neurobc.NeuralNetwork;
import ru.itis.sa.arbiter.neurobc.NewBlockResponse;
import ru.itis.sa.arbiter.neurobc.service.SignService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class NeuroRestController {

    private static Logger log = Logger.getLogger(NeuroRestController.class.getName());

    @Autowired
    private SignService signService;

    @ResponseBody
    @GetMapping(value = "/newblock")
    public NewBlockResponse  newBlockGetHandler(@RequestParam(name = "block") String blockStr) {

        log.info("new block: " + blockStr);

        Date ts = new Date();

        // Парсим присланные данные
        ObjectMapper mapper = new ObjectMapper();
        BlockModel block = null;
        try {
            block = mapper.readValue(blockStr, BlockModel.class);
        } catch (JsonProcessingException e) {
            log.log(Level.SEVERE,"error",e);
            return new NewBlockResponse(2,"mapper error: " + e.getMessage(),null);
        }

        //Вычиляем хеш нашего последнего блока и сравниваем с присланным prevHash
        // должны совпадать
        int sz = BlockChain.chain.size();
        try {
            String prevHash = sz > 0 ? new String(Hex.encode(signService.getHash(BlockChain.chain.get(sz - 1)))) : null;

            if (!block.getPrevhash().equals(prevHash)) {
                log.info("Hash error. Last block has hash: " + prevHash + "(" + block.getInfo() + ")");
                return new NewBlockResponse(2, "Hash error. Last block has hash: " + prevHash, null);
            }

        } catch (Exception e) {
            log.log(Level.SEVERE,"error",e);
            return new NewBlockResponse(2, "server error: " + e.getMessage(), null);
        }

        double block_e = 0;
        try {
            block_e = Double.parseDouble(block.getData().getE());
        } catch (NumberFormatException e) {
            log.log(Level.SEVERE,"error "+ "(" + block.getInfo() + ")",e);
            return new NewBlockResponse(2, "Не удалось определить ошибку ваших параметров нейросети на тестовых данных: " + e.getMessage(), null);
        }

        // если ошибка > 1, то такие параметры блока не принимаются
        if (block_e > 1.000001) {
            log.info("e > 1 (" + block.getInfo() + ")");
            return new NewBlockResponse(2,"e > 1",null);
        }

        //пересчитываем ошибку сами
        NeuralNetwork nn = new NeuralNetwork(block.getData());
        double e = nn.e();

        //сравниваем ошибку блока и пересчитанную ошибку
        if (Math.abs(e - block_e) > 0.000001) {
            return new NewBlockResponse(2, "разница с предъявленной ошибкой превышает 0.000001 ( рассчитали " +block_e+" , " + block.getInfo() + ")", null);
        }

        block.setTs( new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SX").format(ts));

        try {
            byte[] hash = signService.getHash(block);
            byte[] sign = signService.generateRSAPSSSignature( Hex.encode(hash));
            String arbiterSignature = new String(Hex.encode(sign));
            block.setArbitersignature(arbiterSignature);
        } catch (Exception e1) {
            log.log(Level.SEVERE,"error "+ "(" + block.getInfo() + ")",e1);
            return new NewBlockResponse(2, "Ошибка формирования подписи арбитра: " + e1.getMessage(), null);
        }

        BlockChain.chain.add(block);
        BlockChain.saveBlockChain();
        log.info("block " + block.getInfo() + " added ");
        NewBlockResponse resp =  new NewBlockResponse(0,"",block);

        return resp;
    }

    /**
     * Запрос цепочки блоков начиная с блока, следующего за тем, хеш которого совпадает с
     * параметром запроса
     * @param prevHash
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/chain")
    public List<BlockModel> newBlockPostHandler(@RequestParam(name = "hash", required = false) String prevHash) {

        List<BlockModel> lst = new ArrayList<>();

        if (prevHash == null) {
            return BlockChain.chain;
        }

        int i = 0;
        for (; i < BlockChain.chain.size(); i++) {
            if (prevHash.equals(BlockChain.chain.get(i).getPrevhash())) break;
        }

        for (; i < BlockChain.chain.size(); i++) {
            lst.add(BlockChain.chain.get(i));
        }

        return lst;
    }

}
