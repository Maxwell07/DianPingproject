package org.imooc.service.impl;

import org.imooc.bean.Ad;
import org.imooc.dao.AdDao;
import org.imooc.dto.AdDto;
import org.imooc.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class AdServiceImpl implements AdService {

    @Autowired
    private AdDao adDao;
    @Value("${adImage.savePath}")
    private String adImgeSavePath;

    public boolean add(AdDto adDto) {
        Ad ad = new Ad();
        ad.setTitle(adDto.getTitle());
        ad.setLink(adDto.getLink());
        ad.setWeight(adDto.getWeight());
        if (adDto.getImgFile()!=null && adDto.getImgFile().getSize()>0){
            String fileName = System.currentTimeMillis()+"_"+adDto.getImgFile().getName()+".jpg";
            File file = new File(adImgeSavePath+ fileName);
            ad.setImgFileName(fileName);
            File fileFolder = new File(adImgeSavePath);
            if (!fileFolder.exists()){
                fileFolder.mkdirs();
            }
            try {
                adDto.getImgFile().transferTo(file);
                adDao.insert(ad);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }else {
            return false;
        }
    }
}
