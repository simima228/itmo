package com.example.models;

public enum MpaaRating {
    PG,
    PG_13,
    R,
    NC_17;

    public static String getRatings(){
        StringBuilder sb = new StringBuilder();
        for (MpaaRating r : MpaaRating.values()){
            sb.append(r.name()).append("\n");
        }
        return sb.substring(0, sb.length() - 1);
    }
}