package com.techprimers.db.model;

import java.util.Objects;

public class Block {
    int previousHash;
    Vote vote;

    public Block(int previousHash, Vote vote) {
        this.previousHash = previousHash;
        this.vote = vote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Block block = (Block) o;
        return previousHash == block.previousHash &&
                Objects.equals(vote, block.vote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(previousHash, vote);
    }
}
