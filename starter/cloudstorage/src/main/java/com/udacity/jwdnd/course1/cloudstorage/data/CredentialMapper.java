package com.udacity.jwdnd.course1.cloudstorage.data;

public interface CredentialMapper {

    final String getById = "SELECT * FROM CREDENTIALS WHERE userid = #{userId}";
    final String deleteById = "DELETE from CREDENTIALS WHERE credentialid = #{credentialId}";
    final String insert = "INSERT INTO CREDENTIALS (url, username, password) VALUES(#{noteTitle},#{noteDescription},#{userId})" ;
    final String update = "UPDATE CREDENTIALS SET notetitle = #{noteTitle}, notedescription = #{noteDescription} WHERE noteid = #{noteId}";


}
