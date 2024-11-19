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
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class CurrencyRestController {

    private static Logger log = Logger.getLogger(CurrencyRestController.class.getName());
    // пропорции валют в стратегиях
    private static double[][] strategy1 = new double[][]{{0.1,0.15,0.15,0.6}, {0.3,0.3,0.3,0.1},{0.6,0.1,0.1,0.2},{0.35,0.35,0.15,0.15}};

    @Autowired
    private HashService hashService;

    private CurrencyHandler handler = new CurrencyHandler();

    // http://localhost:8080/currency/add?value=%7B%22name%22:%22Еникеев%20Камиль%20Шамилевич,-%22,%22currency1%22:%22AUD%22,%22strategy%22:%22s3%22%7D
    @ResponseBody
    @RequestMapping(path = {"/currency/add","/currency"})
    public CurrencyBlockResponse newBlockGetHandler(@RequestParam(name = "value") String dataStr) {

        log.info("new currency block: " + dataStr);

        Date ts = new Date();
        CurrencyBlockModel block = new CurrencyBlockModel();

        // Парсим присланные данные
        ObjectMapper mapper = new ObjectMapper();
        CurrencyDataModel data = null;
        try {
            data = mapper.readValue(dataStr, CurrencyDataModel.class);
            block.setData(data);
        } catch (JsonProcessingException e) {
            log.log(Level.SEVERE,"error",e);
            return new CurrencyBlockResponse(2,"mapper error: " + e.getMessage(),null);
        }

        if (block.getData().getName().isEmpty()) {
            return new CurrencyBlockResponse(2,"Не указано имя",null);
        }

        if (block.getData().getCurrency1().isEmpty()) {
            return new CurrencyBlockResponse(2,"Не указан код валюты 1",null);
        }

        if (block.getData().getCurrency2().isEmpty()) {
            return new CurrencyBlockResponse(2,"Не указан код валюты 2",null);
        }

        if (block.getData().getCurrency3().isEmpty()) {
            return new CurrencyBlockResponse(2,"Не указан код валюты 3",null);
        }

        int sz = CurrencyBlockChain.chain.size();

        block.setTs( new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SX").format(ts));

        if (sz > 0) {
            try {
                byte[] hash = hashService.getHash(CurrencyBlockChain.chain.get(sz - 1));
                block.setPrevhash(new String(Hex.encode(hash)));
            } catch (Exception e1) {
                log.log(Level.SEVERE, "error " + "(" + block.getInfo() + ")", e1);
                return new CurrencyBlockResponse(2, "Ошибка формирования хеша: " + e1.getMessage(), null);
            }

 /*           for (CurrencyBlockModel bm : CurrencyBlockChain.chain) {
                if (checkEquals(bm, block)) {
                    return new CurrencyBlockResponse(2, "Такой набор валют уже загружался", null);
                }
            }
*/        }

        CurrencyBlockChain.chain.add(block);
        CurrencyBlockChain.saveBlockChain();
        log.info("block " + block.getInfo() + " added ");
        CurrencyBlockResponse resp =  new CurrencyBlockResponse(0,"",block);

        return resp;
    }

    /**
     * Запрос цепочки блоков
     */
    @GetMapping(value = "/currency/bc")
    public String getBc(@RequestParam(name = "hash", required = false) String prevHash,
                                          @ModelAttribute("model") ModelMap model) {
        model.addAttribute("chain", CurrencyBlockChain.chain);
        return "authors";
    }

    /**
     * Запрос данных блоков
     */
    @GetMapping(value = "/currency/alldata")
    public String getAllData(HttpServletRequest request,
                             @ModelAttribute("model") ModelMap model) {

        model.addAttribute("chain", handler.getUIDataModelList(CurrencyBlockChain.chain, strategy1));

        return "currency_result";
    }

    @GetMapping(value = "/currency/sortedresult")
    public String getSortwdResult(HttpServletRequest request,
                             @ModelAttribute("model") ModelMap model) {

        List<UIDataModel>  c1 = handler.getUIDataModelList(CurrencyBlockChain.chain, strategy1);

        c1.sort((o1, o2) -> o1.getItogd() < o2.getItogd() ? 1 : -1);

        model.addAttribute("chain", c1);

        return "currency_sorted_result";
    }

    private boolean checkEquals(CurrencyBlockModel block1, CurrencyBlockModel block2) {
        if (!block1.getData().getCurrency1().trim().toUpperCase().equals(block2.getData().getCurrency1().trim().toUpperCase()) ||
            !block1.getData().getCurrency2().trim().toUpperCase().equals(block2.getData().getCurrency2().trim().toUpperCase()) ||
            !block1.getData().getCurrency3().trim().toUpperCase().equals(block2.getData().getCurrency3().trim().toUpperCase()))
            return false;
        else
            return true;
    }
}
