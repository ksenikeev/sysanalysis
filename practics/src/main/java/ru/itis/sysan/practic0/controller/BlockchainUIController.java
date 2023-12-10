package ru.itis.sysan.practic0.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.sysan.practic0.bc.BlockChain;
import ru.itis.sysan.practic0.bc.BlockModel;
import ru.itis.sysan.practic0.bc.DataModel;
import ru.itis.sysan.practic0.service.SignService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class BlockchainUIController {

    private static Logger log = Logger.getLogger(BlockchainUIController.class.getName());

    @GetMapping(value = "/")
    public String dataanalysis(HttpServletRequest request,
                               @ModelAttribute("model") ModelMap model) {
        model.addAttribute("chain", "");

        return "index";
    }

    @GetMapping(value = "/blockchain")
    public String blockchain(HttpServletRequest request,
                               @ModelAttribute("model") ModelMap model) {
        List<UIDataModel> c1 = getUIDataModelList(BlockChain.chain);

        model.addAttribute("chain", c1);

        return "blockchain";
    }

    @GetMapping(value = "/task")
    public String task(HttpServletRequest request,
                       @ModelAttribute("model") ModelMap model) {
        model.addAttribute("chain", "");

        return "task";
    }

    @GetMapping(value = "/info")
    public String info(HttpServletRequest request,
                       @ModelAttribute("model") ModelMap model) {
        model.addAttribute("chain", "");

        return "info";
    }

    public List<UIDataModel> getUIDataModelList(List<BlockModel> chain) {
        List<UIDataModel> res = new ArrayList<>();

        for (BlockModel block : chain) {
            UIDataModel dm = new UIDataModel(block);
            res.add(dm);
        }
        return res;
    }
}
