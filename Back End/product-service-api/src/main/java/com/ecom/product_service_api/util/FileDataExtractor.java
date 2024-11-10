package com.ecom.product_service_api.util;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.SQLException;

@Service
public class FileDataExtractor {
    public String blobToString(Blob data){
        if(data==null)return null;
        StringBuilder stringBuilder = new StringBuilder();
        try{
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(
                            data.getBinaryStream(), StandardCharsets.UTF_8
                    )
            );
            String line;
            while ((line=bufferedReader.readLine()) !=null){
                stringBuilder.append(line);
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return stringBuilder.toString();
    }
}
