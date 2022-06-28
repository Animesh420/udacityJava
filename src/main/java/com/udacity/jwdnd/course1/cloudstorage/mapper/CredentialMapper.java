package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface CredentialMapper {

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    void deleteCredential(int credentialid);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    ArrayList<Credential> getCredentialsByUserId(Integer userId);

    @Select("select * from CREDENTIALS where credentialid = #{credentialid}")
    Credential getCredentialWithId(int credentialid);

    @Insert("INSERT INTO CREDENTIALS (url, username, userid, decryptedPassword, password) VALUES(#{url}, #{username}, #{userid}, #{decryptedPassword}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int insertCredential(Credential credential);

    @Update("UPDATE CREDENTIALS SET url=#{url}, username =#{username}, password=#{password} WHERE credentialid =#{credentialid}")
    int updateCredential(Credential credential);

}
