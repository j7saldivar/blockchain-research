package com.jorgesaldivar.data;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

public class Block {

    private static final Logger LOGGER = LoggerFactory.getLogger(Block.class);

    private String hash;
    private String previousHash;
    private String data;
    private Instant timeStamp;
    private long nonce;

    public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = Instant.now();
        this.hash = hash();
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getData() {
        return data;
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

    public long getNonce() {
        return nonce;
    }

    public String getHash() {
        return hash;
    }

    public String hash() {
        return
                DigestUtils.sha256Hex(
                        String.join("",
                                previousHash,
                                timeStamp.toString(),
                                Long.toString(nonce),
                                data));
    }

    public String hash(long nonce) {
        return
                DigestUtils.sha256Hex(
                        String.join("",
                                previousHash,
                                timeStamp.toString(),
                                Long.toString(nonce),
                                data));
    }

    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0');
        LOGGER.info("Target : {}, Nonce : {}, Hash : {}", target, nonce, hash);

        while (!hash.substring(0, difficulty).equals(target)) {

            LOGGER.info("Finding the hash with a difficulty target of {}. Nonce tried : {}, Hash : {}", target, nonce, hash);
            nonce++;
            hash = hash();

        }
        LOGGER.info("Block Mined : {}", hash);
    }


}
