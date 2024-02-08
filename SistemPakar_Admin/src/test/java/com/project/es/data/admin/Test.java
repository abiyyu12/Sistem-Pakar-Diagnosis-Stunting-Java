package com.project.es.data.admin;


import com.project.es.data.libary.dto.GejalaDto;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Test {

    private List<GejalaDto> penyakitGiziLebih = new ArrayList<>();

    private List<GejalaDto> penyakitMaramus = new ArrayList<>();

    private List<String> penyakitInput = List.of(
            "GJ001","GJ002","GJ003","GJ004","GJ007","GJ008"
    );

    @BeforeEach
    void setUp() {
        penyakitGiziLebih.add(new GejalaDto("GJ001","Kelebihan Berat Badan",9.0));
        penyakitGiziLebih.add(new GejalaDto("GJ002","Obesitas",9.0));
        penyakitGiziLebih.add(new GejalaDto("GJ003","Tubuh Gemuk",9.0));
        penyakitGiziLebih.add(new GejalaDto("GJ004","Badan tampak semakin kurus",9.0));
        penyakitGiziLebih.add(new GejalaDto("GJ005","Lahir Prematur",6.0));

        penyakitMaramus.add(new GejalaDto("GJ006","Pertumbuhan Tulang Melambat",0.9));
        penyakitMaramus.add(new GejalaDto("GJ007","Perkembangan Gigi Melambat",0.9));
        penyakitMaramus.add(new GejalaDto("GJ008","Rambut Rapuh dan Mudah Rontol",0.7));
    }


    @org.junit.jupiter.api.Test
    void Logika() {
        int hasilPrediksi = 0;
        int pembagi = 0;
        int hasilPrediksi2 = 0;
        int pembagi2 = 0;
        for (GejalaDto gejalaDto : penyakitGiziLebih) {
            pembagi+=gejalaDto.getBobotGejala();
        }
        for (GejalaDto gejalaDto : penyakitMaramus) {
            pembagi2+=gejalaDto.getBobotGejala();
        }
        for (String s : penyakitInput) {
            for (GejalaDto gejalaDto : penyakitGiziLebih) {
                if(s.equals(gejalaDto.getIdGejala())){
                    int tempHasil = (int) (1*gejalaDto.getBobotGejala());
                    hasilPrediksi+=tempHasil;
                }else {
                    hasilPrediksi+=0;
                }
            }
        }
        for (String s : penyakitInput) {
            for (GejalaDto gejalaDto : penyakitMaramus) {
                if(s.equals(gejalaDto.getIdGejala())){
                    int tempHasil2 = (int) (1*gejalaDto.getBobotGejala());
                    hasilPrediksi2+=tempHasil2;
                }else {
                    hasilPrediksi2+=0;
                }
            }
        }


        System.out.println(pembagi2);
        System.out.println(hasilPrediksi2);

        double hasilFinal = (double) hasilPrediksi / pembagi;
        // Convert hasilFinal to percentage
        double percentageResult = hasilFinal * 100;
        // Round the percentage result
        double roundedPercentageResult = Math.round(percentageResult);

        double percentageResult2 = (double) hasilPrediksi2 / pembagi2;
        System.out.println(percentageResult2);
        // Display the rounded percentage result
        System.out.println("Rounded Percentage Result: " + roundedPercentageResult + "%");
//        System.out.println("Rounded Percentage Result: " + roundedPersen + "%");
    }

    @org.junit.jupiter.api.Test
    void name() {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime.getDayOfMonth());
        System.out.println(localDateTime.getMonth().toString());
    }
}
