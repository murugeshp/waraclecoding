package com.mrugesh1996.waraclecoding.service;

import com.mrugesh1996.waraclecoding.entities.Cake;
import com.mrugesh1996.waraclecoding.repositry.CakeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class CakeService {

    @Autowired
    CakeDAO cakeDAO;

    @Autowired
    RestTemplate restTemplate;

    Set<Cake> cakes;

    @PostConstruct
    public void init(){

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        //Add the Jackson Message converter
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        // Note: here we are making this converter to process any kind of response,
        // not only application/*json, which is the default behaviour
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);

        ResponseEntity<Set<Cake>> artistResponse =
                restTemplate.exchange("https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json", HttpMethod.GET, null, new ParameterizedTypeReference<Set<Cake>>(){});
        cakes = artistResponse.getBody();
        System.out.println(cakes);
        cakeDAO.saveAll(cakes);
    }


    public List<Cake> getCakes() {
        return cakeDAO.findAll();
    }

    public void createCake(Integer cakeId, String title, String desc, String image) {
        Cake cake = new Cake(title,  desc,  image);
        cakeDAO.save(cake);
    }

    public Optional<Cake> getCake(int id) {
        return cakeDAO.findById(id);
    }
}
