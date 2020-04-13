package com.techprimers.db.resource;

import com.techprimers.db.model.Vote;
import com.techprimers.db.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/vote")
public class VoteResource {

    private static SecretKeySpec secretKey;
    private static byte[] key;
    final String passKey = "ssshhhhhhhhhhh!!!!";

    @Autowired
    VoteRepository voteRepository;


    @PostMapping(value = "/load")
    public Vote persist(@RequestBody final Vote vote) throws NoSuchPaddingException, NoSuchAlgorithmException {
        Vote encryptedVote = new Vote();
        //check vote
        String voter = encrypt(vote.getVoter(), passKey);
        Vote alreadyVoted = voteRepository.findByVoter(voter);
        if(alreadyVoted != null) {
            return null;
        }

        //blockchain logic
        List<Vote> allVotes = voteRepository.findAllByOrderByIdAsc();
        if(allVotes != null && allVotes.size() == 0) {
            System.out.print("First Vote");
            String party = encrypt(vote.getParty(), passKey);
            String candidate = encrypt(vote.getCandidate(), passKey);
            encryptedVote.setId(1);
            encryptedVote.setVoter(voter);
            encryptedVote.setCandidate(candidate);
            encryptedVote.setParty(party);
            String chainEnc = encrypt(String.valueOf(encryptedVote.getId()) + encryptedVote.getVoter() + encryptedVote.getCandidate(), passKey);
            System.out.println(chainEnc);
            encryptedVote.setEnc(chainEnc);

            voteRepository.save(encryptedVote);
            return encryptedVote;
        }
        //is chain valid
        if(allVotes != null && allVotes.size() > 1) {
            for(int i = 0; i < allVotes.size() ; i++) {
                if( i == 0) {
                    Vote first = allVotes.get(0);
                    String chainEnc = encrypt(String.valueOf(first.getId()) + first.getVoter() + first.getCandidate(), passKey);
                    if(!chainEnc.equals(first.getEnc())) {
                        return null;
                    }
                } else {
                    System.out.print("first valid");
                    Vote currentVote = allVotes.get(i);
                    Vote previousVote = allVotes.get(i-1);
                    String chainEnc = encrypt(previousVote.getEnc() + String.valueOf(currentVote.getId()) + currentVote.getVoter() + currentVote.getCandidate(), passKey);
                    if(!chainEnc.equals(currentVote.getEnc())) {
                        return null;
                    }

                }
            }
        } else if(allVotes.size() == 1) {
            System.out.println("2nd Vote");
            Vote first = allVotes.get(0);
            String chainEnc = encrypt(String.valueOf(first.getId()) + first.getVoter() + first.getCandidate(), passKey);
            System.out.println(chainEnc);
            System.out.println(first.getEnc());
            if(!chainEnc.equals(first.getEnc())) {
                return null;
            }
            System.out.print("secnd Vote validated");
            String party = encrypt(vote.getParty(), passKey);
            String candidate = encrypt(vote.getCandidate(), passKey);

            encryptedVote.setId(allVotes.size() + 1);
            encryptedVote.setVoter(voter);
            encryptedVote.setCandidate(candidate);
            encryptedVote.setParty(party);
            String currentVoteEnc = encrypt(chainEnc + String.valueOf(encryptedVote.getId()) + encryptedVote.getVoter() + encryptedVote.getCandidate(), passKey);
            encryptedVote.setEnc(currentVoteEnc);
            voteRepository.save(encryptedVote);
            return  encryptedVote;
        }
        //save vote
        String party = encrypt(vote.getParty(), passKey);
        String candidate = encrypt(vote.getCandidate(), passKey);

        encryptedVote.setId(allVotes.size() + 1);
        encryptedVote.setVoter(voter);
        encryptedVote.setCandidate(candidate);
        encryptedVote.setParty(party);

        Vote previousVote = allVotes.get(allVotes.size() - 1);
        String chainEnc = encrypt(previousVote.getEnc() + String.valueOf(encryptedVote.getId()) + encryptedVote.getVoter() + encryptedVote.getCandidate(), passKey);
        encryptedVote.setEnc(chainEnc);
        voteRepository.save(encryptedVote);
        return vote;
    }

    public static void setKey(String myKey)
    {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(String strToEncrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String decrypt(String strToDecrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e)
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
}
