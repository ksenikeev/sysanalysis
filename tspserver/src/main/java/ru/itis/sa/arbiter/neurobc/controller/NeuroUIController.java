package ru.itis.sa.arbiter.neurobc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.itis.sa.arbiter.neurobc.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class NeuroUIController {

    private static Logger log = Logger.getLogger(NeuroUIController.class.getName());

    @GetMapping(value = "/nbc/blockchain/autors")
    public String chainReult(HttpServletRequest request, Model model) {

        List<ShowDataModel> blocks = new ArrayList<>();

        BlockChain.chain.forEach(b -> {
            ShowDataModel blockModel = new ShowDataModel();
            blockModel.setTs(b.getTs());
            NeuralNetwork nn = new NeuralNetwork(b.getData());
            //blockModel.setE(b.getData().getE());
            blockModel.setE(String.valueOf(nn.e()));

            if (Autors.autors.containsKey(b.getData().getPublickey())) {
                blockModel.setAutor(Autors.autors.get(b.getData().getPublickey()).getAutor());
            } else {
                blockModel.setAutor(
                        b.getData().getPublickey() != null && b.getData().getPublickey().length() > 15 ?
                                (b.getData().getPublickey().substring(0,15) + " ... " +
                                b.getData().getPublickey().substring(b.getData().getPublickey().length() - 15))
                        : b.getData().getPublickey());
            }

            blocks.add(blockModel);
        });

        model.addAttribute("chain", blocks);

        return "neuro_result";
    }

}
