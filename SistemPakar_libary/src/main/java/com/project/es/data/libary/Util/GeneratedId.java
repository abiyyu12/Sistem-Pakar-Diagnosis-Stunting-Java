package com.project.es.data.libary.Util;

public  class GeneratedId {

    public static String generatedIdAdmin(String lastID){
        String nextGeneratedId = null;
        StringBuilder builder = new StringBuilder();
        builder.append("AD");
        if(lastID == null){
            nextGeneratedId = "AD001";
        }else {
            int intToCounter = Integer.parseInt(lastID.substring(2));
            intToCounter++;
            if(intToCounter >= 10 && intToCounter < 100){
                builder.append("0");
            }
            else if(intToCounter >= 1 && intToCounter < 10) {
                builder.append("00");
            }
            builder.append(intToCounter);
            nextGeneratedId = builder.toString();
        }
        return nextGeneratedId;
    }

    public static String generatedIdPasiens(String lastID){
        String nextGeneratedId = null;
        StringBuilder builder = new StringBuilder();
        builder.append("PS");
        if(lastID == null){
            nextGeneratedId = "PS001";
        }else {
            int intToCounter = Integer.parseInt(lastID.substring(2));
            intToCounter++;
            if(intToCounter >= 10 && intToCounter < 100){
                builder.append("0");
            }
            else if(intToCounter >= 1 && intToCounter < 10) {
                builder.append("00");
            }
            builder.append(intToCounter);
            nextGeneratedId = builder.toString();
        }
        return nextGeneratedId;
    }

    public static String generatedIdGejala(String lastID){
        String nextGeneratedId = null;
        StringBuilder builder = new StringBuilder();
        builder.append("GJ");
        if(lastID == null){
            nextGeneratedId = "GJ001";
        }else {
            int intToCounter = Integer.parseInt(lastID.substring(2));
            intToCounter++;
            if(intToCounter >= 10 && intToCounter < 100){
                builder.append("0");
            }
            else if(intToCounter >= 1 && intToCounter < 10) {
                builder.append("00");
            }
            builder.append(intToCounter);
            nextGeneratedId = builder.toString();
        }
        return nextGeneratedId;
    }

    public static String generatedIdPenyakit(String lastID){
        String nextGeneratedId = null;
        StringBuilder builder = new StringBuilder();
        builder.append("PK");
        if(lastID == null){
            nextGeneratedId = "PK001";
        }else {
            int intToCounter = Integer.parseInt(lastID.substring(2));
            intToCounter++;
            if(intToCounter >= 10 && intToCounter < 100){
                builder.append("0");
            }
            else if(intToCounter >= 1 && intToCounter < 10) {
                builder.append("00");
            }
            builder.append(intToCounter);
            nextGeneratedId = builder.toString();
        }
        return nextGeneratedId;
    }
}