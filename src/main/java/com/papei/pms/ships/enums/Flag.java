package com.papei.pms.ships.enums;

public enum Flag {

    ALASKA("Alaska (State of)"),
    ALGERIA("Algeria (People's Democratic Republic of)"),
    ANTIGUA_BARBUDA("Antigua and Barbuda"),
    ARGENTINA("Argentine Republic"),
    AUSTRALIA("Australia"),
    BAHAMAS("Bahamas (Commonwealth of the)"),
    BARBADOS("Barbados"),
    BELGIUM("Belgium"),
    BELIZE("Belize"),
    BERMUDA("Bermuda"),
    BRITAIN("British Virgin Islands"),
    BULGARIA("Bulgaria (Republic of)"),
    CAYMAN_ISLANDS("Cayman Islands"),
    CHINA("China (People's Republic of)"),
    COOK_ISLANDS("Cook Islands"),
    CROATIA("Croatia (Republic of)"),
    CYPRUS("Cyprus (Republic of)"),
    DENMARK("Denmark"),
    DOMINICA("Dominica (Commonwealth of)"),
    EGYPT("Egypt (Arab Republic of)"),
    ESTONIA("Estonia (Republic of)"),
    FAROE_ISLANDS("Faroe Islands"),
    FINLAND("Finland"),
    FRANCE("France"),
    GERMANY("Germany (Federal Republic of)"),
    GIBRALTAR("Gibraltar"),
    GREECE("Greece"),
    HONG_KONG("Hong Kong (Special Administrative Region of China)"),
    INDIA("India (Republic of)"),
    IRELAND("Ireland"),
    ISRAEL("Israel (State of)"),
    ITALY("Italy"),
    JAPAN("Japan"),
    KOREA("Korea (Republic of)"),
    KUWAIT("Kuwait (State of)"),
    LATVIA("Latvia (Republic of)"),
    LIBERIA("Liberia (Republic of)"),
    LITHUANIA("Lithuania (Republic of)"),
    LUXEMBOURG("Luxembourg"),
    MADEIRA("Madeira"),
    MALAYSIA("Malaysia"),
    MALTA("Malta"),
    MARSHALL_ISLANDS("Marshall Islands (Republic of the)"),
    MARTINIQUE("Martinique (French Department of)"),
    MONTENEGRO("Montenegro"),
    NETHERLANDS("Netherlands (Kingdom of the)"),
    NETHERLANDS_ANTILLES("Netherlands Antilles"),
    NORWAY("Norway"),
    PALAU("Palau (Republic of)"),
    PANAMA("Panama (Republic of)"),
    PHILIPPINES("Philippines (Republic of the)"),
    POLAND("Poland (Republic of)"),
    ROMANIA("Romania"),
    RUSSIA("Russian Federation"),
    SAINT_KITTS("Saint Kitts and Nevis (Federation of)"),
    SAINT_VINCENT("Saint Vincent and the Grenadines"),
    SAUDI_ARABIA("Saudi Arabia (Kingdom of)"),
    SEYCHELLES("Seychelles (Republic of)"),
    SIERRA_LEONE("Sierra Leone"),
    SINGAPORE("Singapore (Republic of)"),
    SOCIALIST_LIBYAN("Socialist People's Libyan Arab Jamahiriya"),
    SPAIN("Spain"),
    SRI_LANKA("Sri Lanka (Democratic Socialist Republic of)"),
    SWEDEN("Sweden"),
    SWITZERLAND("Switzerland (Confederation of)"),
    TAIWAN("Taiwan (Province of China)"),
    THAILAND("Thailand"),
    TOGOLESE_REPUBLIC("Togolese Republic"),
    TURKEY("Turkey"),
    UNITED_ARAB_EMIRATES("United Arab Emirates"),
    UNITED_KINGDOM_GREAT_BRITAIN("United Kingdom of Great Britain and Northern Ireland"),
    USA("United States of America"),
    VANUATU("Vanuatu (Republic of)");

    private final String code;

    Flag(String v) {
        code = v;
    }

    public String code() {
        return code;
    }

    public static Flag fromValue(String v) {
        for (Flag c: Flag.values()) {
            if (c.code.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(String.valueOf(v));
    }
}
