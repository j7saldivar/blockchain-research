package com.jorgesaldivar.controller;

import com.jorgesaldivar.data.Block;
import com.jorgesaldivar.utils.BlockUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BlockController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlockController.class);

    @GetMapping("/{data}")
    public List<Block> addBlock(@PathVariable("data") String data) {

        LOGGER.info("Mining block having the following data : {}", data);
        return BlockUtils.addBlock(data);

    }


}
