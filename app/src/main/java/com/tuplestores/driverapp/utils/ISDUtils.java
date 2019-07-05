/*
 * Copyright (c) 2019. TUPLE STORES .All Rights Reserved
 */

package com.tuplestores.driverapp.utils;

/*Created By Ajish Dharman on 26-June-2019
 *
 *
 */

import java.util.HashMap;
import java.util.Map;

public  class ISDUtils {


    public static String getISDCode(String key){

        Map<String, String> isdMap = new HashMap<String, String>() {{
            put("AF", "93");
            put("AE", "971");
            put("AL", "355");
            put("AN", "599");
            put("AS", "1");
            put("AD", "376");
            put("AO", "244");
            put("AI", "1");
            put("AG", "1");
            put("AR", "54");
            put("AM", "374");
            put("AW", "297");
            put("AU", "61");
            put("AT", "43");
            put("AZ", "994");
            put("BS", "1");
            put("BH", "973");
            put("BF", "226");
            put("BI", "257");
            put("BD", "880");
            put("BB", "1");
            put("BY", "375");
            put("BE", "32");
            put("BZ", "501");
            put("BJ", "229");
            put("BM", "1");
            put("BT", "975");
            put("BA", "387");
            put("BW", "267");
            put("BR", "55");
            put("BG", "359");
            put("BO", "591");
            put("BL", "590");
            put("BN", "673");
            put("CC", "61");
            put("CD", "243");
            put("CI", "225");
            put("KH", "855");
            put("CM", "237");
            put("CA", "1");
            put("CV", "238");
            put("KY", "345");
            put("CF", "236");
            put("CH", "41");
            put("CL", "56");
            put("CN", "86");
            put("CX", "61");
            put("CO", "57");
            put("KM", "269");
            put("CG", "242");
            put("CK", "682");
            put("CR", "506");
            put("CU", "53");
            put("CY", "537");
            put("CZ", "420");
            put("DE", "49");
            put("DK", "45");
            put("DJ", "253");
            put("DM", "1");
            put("DO", "1");
            put("DZ", "213");
            put("EC", "593");
            put("EG", "20");
            put("ER", "291");
            put("EE", "372");
            put("ES", "34");
            put("ET", "251");
            put("FM", "691");
            put("FK", "500");
            put("FO", "298");
            put("FJ", "679");
            put("FI", "358");
            put("FR", "33");
            put("GB", "44");
            put("GF", "594");
            put("GA", "241");
            put("GS", "500");
            put("GM", "220");
            put("GE", "995");
            put("GH", "233");
            put("GI", "350");
            put("GQ", "240");
            put("GR", "30");
            put("GG", "44");
            put("GL", "299");
            put("GD", "1");
            put("GP", "590");
            put("GU", "1");
            put("GT", "502");
            put("GN", "224");
            put("GW", "245");
            put("GY",
                    "595");
            put("HT", "509");
            put("HR", "385");
            put("HN", "504");
            put("HU", "36");
            put("HK", "852");
            put("IR", "98");
            put("IM", "44");
            put("IL",
                    "972");
            put("IO", "246");
            put("IS", "354");
            put("IN", "91");
            put("ID", "62");
            put("IQ", "964");
            put("IE", "353");
            put("IT", "39");
            put("JM", "1");
            put("JP", "81");
            put("JO", "962");
            put("JE", "44");
            put("KP", "850");
            put("KR", "82");
            put("KZ", "77");
            put("KE", "254");
            put("KI", "686");
            put("KW", "965");
            put("KG", "996");
            put("KN", "1");
            put("LC", "1");
            put("LV", "371");
            put("LB", "961");
            put("LK", "94");
            put("LS", "266");
            put("LR", "231");
            put("LI", "423");
            put("LT", "370");
            put("LU", "352");
            put("LA", "856");
            put("LY", "218");
            put("MO", "853");
            put("MK", "389");
            put("MG", "261");
            put("MW", "265");
            put("MY", "60");
            put("MV", "960");
            put("ML", "223");
            put("MT", "356");
            put("MH", "692");
            put("MQ", "596");
            put("MR", "222");
            put("MU", "230");
            put("MX", "52");
            put("MC", "377");
            put("MN", "976");
            put("ME", "382");
            put("MP", "1");
            put("MS", "1");
            put("MA", "212");
            put("MM", "95");
            put("MF", "590");
            put("MD", "373");
            put("MZ", "258");
            put("NA", "264");
            put("NR", "674");
            put("NP", "977");
            put("NL", "31");
            put("NC", "687");
            put("NZ", "64");
            put("NI", "505");
            put("NE", "227");
            put("NG", "234");
            put("NU", "683");
            put("NF", "672");
            put("NO", "47");
            put("OM", "968");
            put("PK", "92");
            put("PM", "508");
            put("PW", "680");
            put("PF", "689");
            put("PA", "507");
            put("PG", "675");
            put("PY", "595");
            put("PE", "51");
            put("PH", "63");
            put("PL", "48");
            put("PN", "872");
            put("PT", "351");
            put("PR", "1");
            put("PS", "970");
            put("QA", "974");
            put("RO", "40");
            put("RE", "262");
            put("RS", "381");
            put("RU", "7");
            put("RW", "250");
            put("SM", "378");
            put("SA", "966");
            put("SN", "221");
            put("SC", "248");
            put("SL", "232");
            put("SG", "65");
            put("SK", "421");
            put("SI", "386");
            put("SB", "677");
            put("SH", "290");
            put("SD", "249");
            put("SR", "597");
            put("SZ", "268");
            put("SE", "46");
            put("SV", "503");
            put("ST", "239");
            put("SO", "252");
            put("SJ", "47");
            put("SY", "963");
            put("TW", "886");
            put("TZ", "255");
            put("TL", "670");
            put("TD", "235");
            put("TJ", "992");
            put("TH", "66");
            put("TG", "228");
            put("TK", "690");
            put("TO", "676");
            put("TT", "1");
            put("TN", "216");
            put("TR", "90");
            put("TM", "993");
            put("TC", "1");
            put("TV", "688");
            put("UG", "256");
            put("UA", "380");
            put("US", "1");
            put("UY", "598");
            put("UZ", "998");
            put("VA", "379");
            put("VE", "58");
            put("VN", "84");
            put("VG", "1");
            put("VI", "1");
            put("VC", "1");
            put("VU", "678");
            put("WS", "685");
            put("WF", "681");
            put("YE", "967");
            put("YT", "262");
            put("ZA", "27");
            put("ZM", "260");
            put("ZW", "263");

        }};

         key = key.toUpperCase();
        return (isdMap.get(key).toString());

    }

}
