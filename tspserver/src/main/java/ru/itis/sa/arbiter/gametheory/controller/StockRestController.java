package ru.itis.sa.arbiter.gametheory.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.itis.sa.arbiter.gametheory.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class StockRestController {

    private static Logger log = Logger.getLogger(StockRestController.class.getName());
    // пропорции валют в стратегиях
    private static double[][] strategy1 = new double[][]{{0.1,0.15,0.15,0.6}, {0.3,0.3,0.3,0.1},{0.6,0.1,0.1,0.2},{0.35,0.35,0.15,0.15}};

    @Autowired
    private HashService hashService;

    private StockHandler handler = new StockHandler();

    @ResponseBody
    @RequestMapping(path = {"/stock/add","/stock"})
    public StockBlockResponse newBlockGetHandler(@RequestParam(name = "value") String dataStr) {

        log.info("new stock block: " + dataStr);

        Date ts = new Date();
        StockBlockModel block = new StockBlockModel();

        // Парсим присланные данные
        ObjectMapper mapper = new ObjectMapper();
        StockDataModel data = null;
        try {
            data = mapper.readValue(dataStr, StockDataModel.class);
            block.setData(data);
        } catch (JsonProcessingException e) {
            log.log(Level.SEVERE,"error",e);
            return new StockBlockResponse(2,"mapper error: " + e.getMessage(),null);
        }

        if (block.getData().getName().isEmpty()) {
            return new StockBlockResponse(2,"Не указано имя",null);
        }

        if (block.getData().getStock1().isEmpty()) {
            return new StockBlockResponse(2,"Не указаны данные по акции 1",null);
        }

        if (block.getData().getStock2().isEmpty()) {
            return new StockBlockResponse(2,"Не указаны данные по акции 2",null);
        }

        if (block.getData().getStock3().isEmpty()) {
            return new StockBlockResponse(2,"Не указаны данные по акции 3",null);
        }

        if (block.getData().getStock4().isEmpty()) {
            return new StockBlockResponse(2,"Не указаны данные по акции 4",null);
        }

        int sz = StockBlockChain.chain.size();

        block.setTs( new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SX").format(ts));

        if (sz > 0) {
            for (StockBlockModel bm : StockBlockChain.chain) {
                if (checkEquals(bm, block)) {
                    return new StockBlockResponse(2, "Такой набор акций уже загружался", null);
                }
            }

            try {
                byte[] hash = hashService.getHash(StockBlockChain.chain.get(sz - 1));
                block.setPrevhash(new String(Hex.encode(hash)));
            } catch (Exception e1) {
                log.log(Level.SEVERE, "error " + "(" + block.getInfo() + ")", e1);
                return new StockBlockResponse(2, "Ошибка формирования хеша: " + e1.getMessage(), null);
            }
        }

        StockBlockChain.chain.add(block);
        StockBlockChain.saveBlockChain();
        log.info("block " + block.getInfo() + " added ");
        StockBlockResponse resp =  new StockBlockResponse(0,"", block);

        return resp;
    }

    /**
     * Запрос цепочки блоков
     */
    @GetMapping(value = "/stock/bc")
    public String getBc(@ModelAttribute("model") ModelMap model) {
        model.addAttribute("chain", StockBlockChain.chain);
        return "stock_authors";
    }

    /**
     * Запрос данных блоков
     */
    @GetMapping(value = "/stock/alldata")
    public String getAllData(HttpServletRequest request,
                             @ModelAttribute("model") ModelMap model) {

        //model.addAttribute("chain", handler.getUIDataModelList(StockBlockChain.chain, strategy1));

        return "currency_result";
    }

    private boolean checkEquals(StockBlockModel block1, StockBlockModel block2) {

        String[] a1 = {block1.getData().getStock1().trim(), block1.getData().getStock2().trim(),
                block1.getData().getStock3().trim(), block1.getData().getStock4().trim()};

        String[] a2 = {block2.getData().getStock1().trim(), block2.getData().getStock2().trim(),
                block2.getData().getStock3().trim(), block2.getData().getStock4().trim()};

        Arrays.sort(a1);
        Arrays.sort(a2);

        if (a1[0].equals(a2[0]) && a1[1].equals(a2[1]) && a1[2].equals(a2[2]) && a1[3].equals(a2[3])) {
            return true;
        }

        return false;
    }
}
