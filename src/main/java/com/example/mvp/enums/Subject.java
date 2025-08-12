package com.example.mvp.enums;

public enum Subject {
    // Business Engineering
    CORP_FIN("lecge1108 - Économie politique", Major.BUSINESS_ENGINEERING),
    MARKETING("ling1108 - Marketing", Major.BUSINESS_ENGINEERING),
    MATH_ANAL("ling1114 - Introduction à la modélisation mathématiques : analyse", Major.BUSINESS_ENGINEERING),
    CHEM_ENV1("ling1115 - Chimie et environnement I", Major.BUSINESS_ENGINEERING),
    FIN_ACC("ling1107 - Comptabilité financière et analyse des états financiers", Major.BUSINESS_ENGINEERING),
    DATA_PROB("ling1113 - Analyse de données : Probabilités", Major.BUSINESS_ENGINEERING),
    MATH_ALG("ling1121 - Introduction à la modélisation mathématique : algèbre", Major.BUSINESS_ENGINEERING),
    PHYS_EN1("ling1122 - Physique et énergie I", Major.BUSINESS_ENGINEERING),
    ENGLISH1("lang1330 - Anglais niveau moyen 1ère partie", Major.BUSINESS_ENGINEERING),

    // Economic Sciences
    MACRO("lecge1212 - Macroéconomie", Major.ECONOMIC_SCIENCES),
    MICRO("lecge1222 - Microéconomie", Major.ECONOMIC_SCIENCES),
    DATA_STAT("ling1214 - Analyse de données : Statistiques", Major.ECONOMIC_SCIENCES),
    PROG_ECO("ling1225 - Programmation en économie et gestion", Major.ECONOMIC_SCIENCES),
    PHYS_EN2("ling1213 - Physique et énergie II", Major.ECONOMIC_SCIENCES),
    LAW_BUS("lespo1221 - Droit, entreprise et fiscalité", Major.ECONOMIC_SCIENCES),
    MGMT_SCI("ling1216 - Management sciences : modèles déterministes", Major.ECONOMIC_SCIENCES),
    CHEM_ENV2("ling1223 - Chimie et environnement II", Major.ECONOMIC_SCIENCES),

    // Medicine
    DIGI_ECO("lecge1303 - Digital economics", Major.MEDICINE),
    BIG_DATA("lecge1301 - Analyse de données : nouvelles méthodes (Big Data, IA)", Major.MEDICINE),
    TECH_DEV1("ling1317 - Recherche et développement technologique : énergie, électronique et télécommunications", Major.MEDICINE),
    ECO_HIST("lecge1217 - History of Economic Theory", Major.MEDICINE),
    FIN_INFO("ling1322 - Finance and information systems", Major.MEDICINE),
    TECH_DEV2("ling1327 - Recherche et développement technologique : mécanique, procédés chimiques et matériaux", Major.MEDICINE);

    private final String displayName;
    private final Major major;

    Subject(String displayName, Major major) {
        this.displayName = displayName;
        this.major = major;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Major getMajor() {
        return major;
    }
}