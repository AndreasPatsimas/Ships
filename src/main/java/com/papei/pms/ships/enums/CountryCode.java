package com.papei.pms.ships.enums;

public enum CountryCode {

    BEL("BEL"),
    BGR("BGR"),
    CYP("CYP"),
    DEU("DEU"),
    DNK("DNK"),
    ESP("ESP"),
    EST("EST"),
    FIN("FIN"),
    FRA("FRA"),
    GBR("GBR"),
    GRC("GRC"),
    HRV("HRV"),
    IRL("IRL"),
    ITA("ITA"),
    LTU("LTU"),
    LVA("LVA"),
    MLT("MLT"),
    NLD("NLD"),
    POL("POL"),
    PRT("PRT"),
    ROM("ROM"),
    SVN("SVN"),
    SWE("SWE");

    private final String code;

    CountryCode(String v) {
        code = v;
    }

    public String code() {
        return code;
    }

    public static CountryCode fromValue(String v) {
        for (CountryCode c: CountryCode.values()) {
            if (c.code.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(String.valueOf(v));
    }
}
