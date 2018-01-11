package hu.intellicode.savethreelivestoday.ui.map;

import java.util.ArrayList;
import java.util.List;

public class DummyRepository {

    public static List<DonationPoint> getAllDonationPoints() {

        DonationPoint p1 = new DonationPoint("Országos vérellátó szolgálat",
                "Budapest, Karolina út 19-21, 1113",
                47.4796092, 19.0288294);
        DonationPoint p2 = new DonationPoint("Péterfy Sándor utcai Területi Vérellátó",
                "Budapest, Péterfy Sándor u. 14, 1076",
                47.5018107, 19.0762466);
        DonationPoint p3 = new DonationPoint("Magyar Vöröskereszt Székház",
                "Budapest, Arany János u. 31, 1051",
                47.4910036, 19.0380177);
        DonationPoint p4 = new DonationPoint("Délpesti Területi Vérellátó",
                "1204 Budapest, Köves u. 2",
                47.4247281, 19.1289568);

        DonationPoint p5 = new DonationPoint("Neumann János Szám.Tech.XIV. Bp.",
                "1144 Budapest	Kerepesi út 124.",
                47.5046743, 19.1466559);

        DonationPoint p6 = new DonationPoint("Győri Regionális Vérellátó Kp. Intézeti",
                "9023 Győr, Magyar u. 8.",
                47.674061, 17.648672);

        DonationPoint p7 = new DonationPoint("Debreceni Regionális Vérell.Kp. Intézeti",
                "4026 Debrecen, Bem tér 19/B. ",
                47.5432273, 21.6256986);

        List<DonationPoint> donationPoints = new ArrayList<>();
        donationPoints.add(p1);
        donationPoints.add(p2);
        donationPoints.add(p3);
        donationPoints.add(p4);
        donationPoints.add(p5);
        donationPoints.add(p6);
        donationPoints.add(p7);

        return donationPoints;
    }

    public static DonationPoint getADonationPoint() {
        return new DonationPoint("Országos vérellátó szolgálat",
                "Budapest, Karolina út 19-21, 1113",
                47.4796092, 19.0288294);
    }
}