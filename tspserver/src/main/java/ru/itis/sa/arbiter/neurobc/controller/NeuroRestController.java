package ru.itis.sa.arbiter.neurobc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.itis.sa.arbiter.neurobc.*;
import ru.itis.sa.arbiter.neurobc.service.BCService;
import ru.itis.sa.arbiter.neurobc.service.SignService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class NeuroRestController {

    private static Logger log = Logger.getLogger(NeuroRestController.class.getName());

    private static Map<String, LocalDateTime> address = new HashMap<>();

    @Autowired
    private BCService bcService;

    @Autowired
    private SignService signService;

    @ResponseBody
    @GetMapping(value = "/nbc/newblock")
    public NewBlockResponse  newBlockGetHandler(@RequestParam(name = "block") String blockStr,
                                                HttpServletRequest request, HttpServletResponse response) {

        log.info("new block: " + blockStr);

        String ip = request.getRemoteAddr();

        LocalDateTime t = address.get(ip);
        if (t != null) {
            long sec = ChronoUnit.SECONDS.between(t, LocalDateTime.now());
            if (sec < 30) {
                response.setStatus(403);
                return null;
            }
        }

        address.put(ip, LocalDateTime.now());

        // Парсим присланные данные
        ObjectMapper mapper = new ObjectMapper();
        BlockModel block = null;
        try {
            block = mapper.readValue(blockStr, BlockModel.class);
        } catch (JsonProcessingException e) {
            log.log(Level.SEVERE,"error",e);
            return new NewBlockResponse(2,"некорректный JSON объект: " ,null);
        }

        return bcService.validateAndAddNewBlock(block);
    }

    @ResponseBody
    @PostMapping(value = "/nbc/newblock")
    public NewBlockResponse  newBlockGetHandler(@RequestBody BlockModel block) {

        log.info("new block: " + block.getData());
        System.out.println(block.getData());

        return bcService.validateAndAddNewBlock(block);
    }

    @ResponseBody
    @PostMapping(value = "/nbc/autor")
    public AutorResponse addAutor(@RequestBody AutorInfo autorInfo) {

        log.info("new autor: " + autorInfo.getAutor());

        boolean verifidet = false;
        try {
            verifidet = signService.verifyRSAPSSSignature(autorInfo.getPublickey(), autorInfo.getAutor(), autorInfo.getSign());
        } catch (Exception e) {
            log.severe(e.getMessage());
            return new AutorResponse(1, "Ошибка проверки подписи: " + e.getMessage());
        }

        if (!verifidet) {
            log.info("Подпись не прошла проверку на предъявленном ключе: " + autorInfo.getAutor());
            return new AutorResponse(2, "Подпись не прошла проверку на предъявленном ключе");
        }

        if (Autors.autors.containsKey(autorInfo.getPublickey())) {
            return new AutorResponse(3, "Автор уже заявлен, новая информация не обработана");
        }

        Autors.saveAutors(autorInfo);

        log.info("Автор успешно добавлен: " + autorInfo.getAutor());
        return new AutorResponse(0, "Автор успешно добавлен");
    }

    /**
     * Запрос цепочки блоков начиная с блока, следующего за тем, хеш которого совпадает с
     * параметром запроса
     * @param prevHash
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/nbc/chain")
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
