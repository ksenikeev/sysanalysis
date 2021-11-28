package ru.itis.sa.arbiter.neurobc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.itis.sa.arbiter.neurobc.BlockChain;
import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

@Controller
public class NeuroUIController {

    private static Logger log = Logger.getLogger(NeuroUIController.class.getName());

    @GetMapping(value = "/blockchain/autors")
    public String chainReult(HttpServletRequest request,
                             @ModelAttribute("model") ModelMap model) {

        model.addAttribute("chain", BlockChain.chain);

        return "neuro_result";
    }

}
