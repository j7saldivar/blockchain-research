package com.jorgesaldivar.utils;

import com.jorgesaldivar.data.Block;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

public class BlockUtils {

    private static final List<Block> BLOCKCHAIN = new LinkedList<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(BlockUtils.class);
    private static int difficulty = 0;

    private BlockUtils() {
    }

    public static Boolean isChainValid() {

        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        for (int i = 1; i < BLOCKCHAIN.size(); i++) {

            currentBlock = BLOCKCHAIN.get(i);
            previousBlock = BLOCKCHAIN.get(i - 1);

            if (!currentBlock.getHash().equals(currentBlock.hash(currentBlock.getNonce()))) {
                LOGGER.error("The current hash is not matching the expected value");
                return false;
            }

            if (!previousBlock.getHash().equals(currentBlock.getPreviousHash())) {
                LOGGER.error("The previous hash is not matching the current previous hash");
                return false;
            }

            if (i + 1 == BLOCKCHAIN.size()
                    && !currentBlock.getHash().substring(0, difficulty).replace('\0', '0').equals(hashTarget)) {
                LOGGER.error("This block hasn't been mined yet");
                return false;
            }

        }
        return true;
    }

    public static List<Block> addBlock(String data) {
        final Block block = new Block(data, previousHash());
        block.mineBlock(++difficulty);
        BLOCKCHAIN.add(block);
        boolean validChain = isChainValid();
        LOGGER.info("BlockChain is valid: {} ", validChain);
        return BLOCKCHAIN;
    }

    private static String previousHash() {
        return CollectionUtils.isEmpty(BLOCKCHAIN) ?
                "0" : BLOCKCHAIN.get(BLOCKCHAIN.size() - 1).getHash();
    }

}
