package com.travis.stellar.stellarbot.controller;

import com.travis.stellar.stellarbot.UrlJsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;

@RestController
public class stellarController {

    private String stellarNetwork = "https://horizon.stellar.org/accounts/";
    private String coinmarketCap = "https://api.coinmarketcap.com/v2/ticker/512/";


    @Autowired
    UrlJsonUtil urlJsonUtil;

    @RequestMapping("/balance")
    public String getBalance(@RequestParam(name = "publicKey",
                                            required = true)
                                                String publicKey)
                                                    throws IOException {

        return urlJsonUtil.getStellarInfo(new URL(stellarNetwork + publicKey), false);

    }

    @RequestMapping("/price")
    public String getPrice() throws IOException{

        return urlJsonUtil.getStellarInfo(new URL(coinmarketCap), true);
    }

    @RequestMapping("/walletValue")
    public Double getValue(@RequestParam(name = "publicKey",
                                            required = true)
                                                String publicKey) throws IOException {

        return Double.parseDouble(getBalance(publicKey)) * Double.parseDouble(getPrice());

    }

}
